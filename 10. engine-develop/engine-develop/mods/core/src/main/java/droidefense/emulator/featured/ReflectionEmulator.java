package droidefense.emulator.featured;

import droidefense.emulator.featured.base.AbstractAndroidEmulator;
import droidefense.emulator.machine.base.struct.fake.EncapsulatedClass;
import droidefense.emulator.machine.base.struct.generic.IDroidefenseClass;
import droidefense.emulator.machine.reader.DexClassReader;

/**
 * Created by sergio on 3/2/16.
 */
public class ReflectionEmulator extends AbstractAndroidEmulator {

    public static final String JAVA_REFLECTION_CLASS = "java/lang/Class";

    private String classname;
    private Object reflectedObject;

    public ReflectionEmulator(String classname) {
        this.classname = classname;
    }

    public Object getReflectedObject() {
        return reflectedObject;
    }

    public void setReflectedObject(Object reflectedObject) {
        this.reflectedObject = reflectedObject;
    }

    //TODO check this
    @Override
    public void emulate() {
        IDroidefenseClass resultClass = DexClassReader.getInstance().load(this.classname);
        //TODO check this
        if (resultClass instanceof EncapsulatedClass) {
            EncapsulatedClass taint = (EncapsulatedClass) resultClass;
            setReflectedObject(taint.getJavaObject());
        }
        setReflectedObject(resultClass);
        /*if (counter.containsKey(classname)) {
            int count = counter.get(classname);
            count++;
            counter.replace(classname, count);
        } else {
            counter.put(classname, 1);
        }
        return DexClassReader.getInstance().load(classname);
        */
    }
}
