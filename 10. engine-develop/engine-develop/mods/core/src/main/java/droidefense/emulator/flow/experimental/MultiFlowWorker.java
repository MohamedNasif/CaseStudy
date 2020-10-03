package droidefense.emulator.flow.experimental;

import droidefense.emulator.flow.stable.SimpleFlowWorker;
import droidefense.emulator.machine.base.AbstractDVMThread;
import droidefense.emulator.machine.base.exceptions.NoMainClassFoundException;
import droidefense.emulator.machine.base.struct.generic.IDroidefenseClass;
import droidefense.emulator.machine.base.struct.generic.IDroidefenseFrame;
import droidefense.emulator.machine.base.struct.generic.IDroidefenseMethod;
import droidefense.emulator.machine.inst.DalvikInstruction;
import droidefense.emulator.machine.inst.InstructionReturn;
import droidefense.emulator.machine.reader.DexClassReader;
import droidefense.log4j.Log;
import droidefense.log4j.LoggerType;
import droidefense.rulengine.map.BasicCFGFlowMap;
import droidefense.rulengine.nodes.ConditionalNode;
import droidefense.rulengine.nodes.EntryPointNode;
import droidefense.sdk.model.base.DroidefenseProject;

import java.util.ArrayList;

public final strictfp class MultiFlowWorker extends SimpleFlowWorker {

    public MultiFlowWorker(DroidefenseProject project) {
        super(project);
        flowMap = new BasicCFGFlowMap();
        this.name = "MultiFlowWorker";
    }

    @Override
    public void run() {
        //get main class and load
        if (currentProject.hasMainClass()) {
            DexClassReader.getInstance().load(currentProject.getMainClassName());
            try {
                execute(true);
            } catch (Throwable throwable) {
                Log.write(LoggerType.ERROR, throwable.getLocalizedMessage());
            }
        } else {
            throw new NoMainClassFoundException(currentProject.getProjectName() + " >> check main class manually");
        }
    }

    @Override
    public void finish() {
        //save multiflow worker flowmap
        currentProject.setMultiFlowMap(flowMap);
        Log.write(LoggerType.DEBUG, "WORKER: MultiFlow FINISHED!");
    }

    @Override
    public IDroidefenseMethod[] getInitialMethodToRun(IDroidefenseClass clazz) {
        ArrayList<IDroidefenseMethod> list = new ArrayList<>();
        /*IDroidefenseMethod[] l0 = clazz.getMethod("<init>");
        for (IDroidefenseMethod m : l0) {
            list.add(m);
        }*/
        list.add(clazz.getMethod("onCreate", "(Landroid/os/Bundle;)V", true));
        return list.toArray(new IDroidefenseMethod[list.size()]);
    }

    @Override
    public IDroidefenseClass[] getInitialDVMClass() {
        //get all
        if (currentProject.hasMainClass())
            return new IDroidefenseClass[]{currentProject.getInternalInfo().getDexClass(currentProject.getMainClassName())};
        else {
            //else, return all reveivers, services,...
            return currentProject.getDeveloperClasses();
        }
    }

    @Override
    public strictfp void execute(boolean endless) throws Throwable {

        IDroidefenseFrame frame = getCurrentFrame();
        IDroidefenseMethod method = frame.getMethod();

        int[] lowerCodes = method.getOpcodes();
        int[] upperCodes = method.getRegisterOpcodes();
        int[] codes = method.getIndex();

        fromNode = EntryPointNode.builder();

        while (endless) {
            try {
                //1 ask if we have more inst to execute
                if (frame.getPc() >= lowerCodes.length || getFrames() == null || getFrames().isEmpty())
                    break;
                //skip sdk classes for faster execution
                if (method.isFake()) {
                    popFrame();
                    frame = getCurrentFrame();
                    if (frame != null) {
                        method = frame.getMethod();
                        if (method != null) {
                            upperCodes = method.getRegisterOpcodes();
                            lowerCodes = method.getOpcodes();
                            codes = method.getIndex();
                            continue;
                        }
                    }
                    break;
                }
                int instVal = lowerCodes[frame.getPc()];
                System.out.println("DalvikInstruction: 0x" + Integer.toHexString(instVal));
                DalvikInstruction currentInstruction = AbstractDVMThread.instructions[instVal];
                InstructionReturn returnValue = currentInstruction.execute(flowMap, frame, lowerCodes, upperCodes, codes, DalvikInstruction.CFG_EXECUTION);
                //multiflow worker IS AWARE of DalvikInstruction return value
                if (returnValue != null) {
                    //first check for errors in DalvikInstruction execution
                    if (returnValue.getError() != null) {
                        throw returnValue.getError();
                    }
                    //first thing after errors is to check if its a conditional node
                    if (returnValue.getNode() != null && returnValue.getNode() instanceof ConditionalNode) {
                        //Duplicate current state
                    }
                    //if no errors, update values
                    frame = returnValue.getFrame();
                    method = returnValue.getMethod();
                    upperCodes = returnValue.getUpperCodes();
                    lowerCodes = returnValue.getLowerCodes();
                    codes = returnValue.getCodes();
                    toNode = returnValue.getNode();
                    //save node connection if DalvikInstruction returned a node
                    if (fromNode != null && toNode != null) {
                        createNewConnection(fromNode, toNode, currentInstruction);
                        //update current node if DalvikInstruction is an invoke type
                        if (isInvokeInstruction(currentInstruction))
                            fromNode = toNode;
                    }
                }
            } catch (Throwable e) {
                frame = handleThrowable(e, frame);
                method = frame.getMethod();
                lowerCodes = method.getOpcodes();
                upperCodes = method.getRegisterOpcodes();
                codes = method.getIndex();
            }
        }
    }

    private boolean isInvokeInstruction(DalvikInstruction currentInstruction) {
        //TODO replace by enumeration contained different DalvikInstruction types
        switch (currentInstruction.code()) {
            case "0x28":
                //goto
                return true;
            case "0x29":
                //goto
                return true;
            case "0x2A":
                //goto
                return true;
            case "0x6E":
                //goto
                return true;
            case "0x6F":
                //goto
                return true;
            case "0x70":
                //goto
                return true;
            case "0x71":
                //goto
                return true;
            case "0x72":
                //goto
                return true;
            case "0x74":
                //goto
                return true;
            case "0x75":
                //goto
                return true;
            case "0x76":
                //goto
                return true;
            case "0x77":
                //goto
                return true;
            case "0x78":
                //goto
                return true;
            default:
                return false;
        }
    }
}