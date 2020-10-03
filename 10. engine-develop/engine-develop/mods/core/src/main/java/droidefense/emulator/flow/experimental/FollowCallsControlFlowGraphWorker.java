package droidefense.emulator.flow.experimental;

import droidefense.emulator.flow.stable.SimpleFlowWorker;
import droidefense.emulator.machine.base.struct.fake.DVMTaintClass;
import droidefense.emulator.machine.base.struct.fake.DVMTaintMethod;
import droidefense.emulator.machine.base.struct.generic.IDroidefenseClass;
import droidefense.emulator.machine.base.struct.generic.IDroidefenseFrame;
import droidefense.emulator.machine.base.struct.generic.IDroidefenseMethod;
import droidefense.emulator.machine.inst.DalvikInstruction;
import droidefense.emulator.machine.inst.InstructionReturn;
import droidefense.log4j.Log;
import droidefense.log4j.LoggerType;
import droidefense.rulengine.base.AbstractAtomNode;
import droidefense.rulengine.map.BasicCFGFlowMap;
import droidefense.rulengine.nodes.EntryPointNode;
import droidefense.sdk.model.base.DroidefenseProject;

import java.util.Vector;

public final strictfp class FollowCallsControlFlowGraphWorker extends SimpleFlowWorker {

    private int[] lowerCodes;
    private int[] upperCodes;
    private int[] codes;

    public FollowCallsControlFlowGraphWorker(DroidefenseProject project) {
        super(project);
        fromNode = null;
        flowMap = new BasicCFGFlowMap();
        this.name = "FollowCallsControlFlowGraphWorker";
    }

    @Override
    public void finish() {
        currentProject.setFollowCallsMap(flowMap);
        Log.write(LoggerType.DEBUG, "WORKER: FollowCallsControlFlowGraphWorker FINISHED!");
    }

    @Override
    public strictfp void execute(boolean keepScanning) throws Throwable {

        IDroidefenseFrame frame = getCurrentFrame();
        IDroidefenseMethod method = frame.getMethod();

        lowerCodes = method.getOpcodes();
        upperCodes = method.getRegisterOpcodes();
        codes = method.getIndex();

        keepScanning = true;

        fromNode = EntryPointNode.builder();
        toNode = buildMethodNode(DalvikInstruction.DALVIK_0x0, frame, method);
        createNewConnection(fromNode, toNode, DalvikInstruction.DALVIK_0x0);
        fromNode = toNode;
        toNode = null;

        while (keepScanning) {
            int currentPc = frame.getPc();
            int inst;

            //1 ask if we have more inst to execute
            if (currentPc >= lowerCodes.length || getFrames() == null || getFrames().isEmpty())
                break;

            //skip sdk methods
            if (getFrames().size() > 1) {
                keepScanning = goBack(0);
                continue;
            }

            inst = lowerCodes[currentPc];
            DalvikInstruction currentInstruction = instructions[inst];
            Log.write(LoggerType.TRACE, currentInstruction.name() + " " + currentInstruction.description());

            try {
                if (inst >= 0x44 && inst <= 0x6D) {
                    //GETTER SETTER
                    //do not execute that DalvikInstruction. just act like if it was executed incrementing pc value properly
                    InstructionReturn ret = currentInstruction.execute(flowMap, frame, lowerCodes, upperCodes, codes, DalvikInstruction.CFG_EXECUTION);
                    //create node
                    toNode = builNormalNode(currentInstruction);
                } else if ((inst >= 0x6E && inst <= 0x78) || (inst == 0xF0) || (inst >= 0xF8 && inst <= 0xFB)) {
                    //CALLS
                    InstructionReturn fakeCallReturn = fakeMethodCall(frame);
                    toNode = buildMethodNode(currentInstruction, frame, fakeCallReturn.getMethod());
                } else if (inst == 0x00) {
                    //NOP
                    //nop of increases pc by one
                    frame.increasePc(1);
                    toNode = builNormalNode(currentInstruction, "op", "NOP");
                } else if (inst >= 0xE && inst <= 0x11) {
                    //return-void
                    //nop of increases pc by one
                    frame.increasePc(1);
                    toNode = builNormalNode(currentInstruction, "return", "void");
                } else {
                    //OTHER INST
                    //do not execute that DalvikInstruction. just act like if it was executed incrementing pc value properly
                    InstructionReturn ret = currentInstruction.execute(flowMap, frame, lowerCodes, upperCodes, codes, DalvikInstruction.CFG_EXECUTION);
                    AbstractAtomNode node = ret.getNode();
                    if (node == null) {
                        node = builNormalNode(currentInstruction);
                    }
                    toNode = node;
                }
                //create the connection
                createNewConnection(fromNode, toNode, currentInstruction);
                fromNode = toNode;
                toNode = null;
                //check if there are more instructions to execute
                if (frame.getPc() >= lowerCodes.length) {
                    //method instructions are all executed. this method is ended. stop loop
                    keepScanning = false;
                    //keepScanning = goBack(1);
                }
            } catch (Exception e) {
                Log.write(LoggerType.ERROR, "Excepcion during observation", e, e.getLocalizedMessage());
            }
        }
    }

    private boolean goBack(int fakePc) {

        //remove last frame and set the new one the last one
        IDroidefenseFrame supposedPreviousFrame = null;
        Vector list = getCurrentFrame().getThread().getFrames();
        if (list != null && !list.isEmpty()) {
            list.remove(list.size() - 1);
            if (!list.isEmpty()) {
                //set current frame list lastone
                supposedPreviousFrame = (IDroidefenseFrame) list.get(list.size() - 1);
            } else {
                //no las frame, set null;
                supposedPreviousFrame = null;
            }
        }

        if (supposedPreviousFrame != null) {
            //set as current frame
            replaceCurrentFrame(supposedPreviousFrame);
            //reload method
            getCurrentFrame().setMethod(supposedPreviousFrame.getMethod());

            //restore codes
            lowerCodes = getCurrentFrame().getMethod().getOpcodes();
            upperCodes = getCurrentFrame().getMethod().getRegisterOpcodes();
            codes = getCurrentFrame().getMethod().getIndex();
            getCurrentFrame().increasePc(fakePc);
            return true;
        }

        return false;
    }

    private InstructionReturn fakeMethodCall(IDroidefenseFrame frame) {

        IDroidefenseMethod method = frame.getMethod();

        // invoke-virtual {vD, vE, vF, vG, vA}, meth@CCCC
        int registers = upperCodes[frame.increasePc()] << 16;
        int methodIndex = codes[frame.increasePc()];
        registers |= codes[frame.increasePc()];

        //Todo fix null pointer when calling this method using tainted classes

        String clazzName;
        String methodName;
        String methodDescriptor;
        if (method.isFake()) {
            clazzName = method.getOwnerClass().getName();
            methodName = method.getName();
            methodDescriptor = method.getDescriptor();
        } else {
            clazzName = method.getMethodClasses()[methodIndex];
            methodName = method.getMethodNames()[methodIndex];
            methodDescriptor = method.getMethodTypes()[methodIndex];
        }

        IDroidefenseClass cls = new DVMTaintClass(clazzName);
        return getInstructionReturn(clazzName, methodName, methodDescriptor, cls);
    }

    private InstructionReturn getInstructionReturn(String clazzName, String methodName, String methodDescriptor, IDroidefenseClass cls) {
        IDroidefenseMethod methodToCall = cls.getMethod(methodName, methodDescriptor, false);
        //if class is an interface, It will not have the method to be called
        if (methodToCall == null) {
            methodToCall = new DVMTaintMethod(methodName, clazzName);
            methodToCall.setDescriptor(methodDescriptor);
            methodToCall.setOwnerClass(cls);
        }
        IDroidefenseFrame frame = callMethod(false, methodToCall, getCurrentFrame());
        int[] lowerCodes = methodToCall.getOpcodes();
        int[] upperCodes = methodToCall.getRegisterOpcodes();
        int[] codes = methodToCall.getIndex();
        return new InstructionReturn(frame, methodToCall, lowerCodes, upperCodes, codes, null);
    }
}