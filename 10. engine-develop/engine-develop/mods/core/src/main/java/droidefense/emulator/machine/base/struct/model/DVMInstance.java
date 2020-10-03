package droidefense.emulator.machine.base.struct.model;


import droidefense.emulator.machine.base.struct.generic.IDroidefenseClass;
import droidefense.emulator.machine.base.struct.generic.IDroidefenseField;
import droidefense.emulator.machine.base.struct.generic.IDroidefenseInstance;
import droidefense.emulator.machine.reader.DexClassReader;

import java.io.Serializable;
import java.util.Hashtable;

public final class DVMInstance implements IDroidefenseInstance, Serializable {

    private final Hashtable<String, Hashtable<String, IDroidefenseField>> fieldsOfClasses = new Hashtable<>();

    private final IDroidefenseClass ownerClass;
    private Object parentInstance;

    public DVMInstance(final IDroidefenseClass cls) {
        this.ownerClass = cls;

        IDroidefenseClass current = cls;
        do {
            Hashtable<String, IDroidefenseField> fields = new Hashtable<>();
            IDroidefenseField[] currentFields = current.getInstanceFields();
            if (currentFields != null) {
                for (IDroidefenseField field : currentFields) {
                    fields.put(field.getName(), field.copy());
                }
                fieldsOfClasses.put(current.getName(), fields);
            }
            //stop condition: class is fake or class represents a reflected java object class with no parent
            if (current.isFake() || current.getSuperClass() == null)
                break;
            else
                current = DexClassReader.getInstance().load(current.getSuperClass());
        } while (current != null);
    }

    public String toString() {
        return ownerClass.getName() + "@" + Integer.toHexString(hashCode());
    }

    public IDroidefenseField getField(final String className, final String fieldName) {
        String currentClassName = className;
        while (true) {
            Hashtable<String, IDroidefenseField> fields = fieldsOfClasses.get(currentClassName);
            if (fields == null) {
                return null;
            }
            IDroidefenseField field = fields.get(fieldName);
            if (field != null) {
                return field;
            }
            IDroidefenseClass currentClazz = DexClassReader.getInstance().load(currentClassName);
            if (currentClazz == null) {
                return null;
            }
            currentClassName = currentClazz.getSuperClass();
        }
    }

    //GETTERS AND SETTERS


    public IDroidefenseClass getOwnerClass() {
        return ownerClass;
    }

    public Hashtable getFieldsOfClasses() {
        return fieldsOfClasses;
    }

    public Object getParentInstance() {
        return parentInstance;
    }

    public void setParentInstance(Object parentInstance) {
        this.parentInstance = parentInstance;
    }
}
