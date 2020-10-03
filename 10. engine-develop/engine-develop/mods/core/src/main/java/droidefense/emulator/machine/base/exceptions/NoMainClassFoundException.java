package droidefense.emulator.machine.base.exceptions;

import java.io.Serializable;

/**
 * Created by sergio on 27/3/16.
 */
public class NoMainClassFoundException extends VirtualMachineRuntimeException implements Serializable {

    public NoMainClassFoundException(String message) {
        super("Droidefense could not find a valid MAIN entry point: " + message);
    }
}
