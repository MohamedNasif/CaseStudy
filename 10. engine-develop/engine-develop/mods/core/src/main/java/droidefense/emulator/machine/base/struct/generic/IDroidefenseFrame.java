package droidefense.emulator.machine.base.struct.generic;

import droidefense.emulator.machine.base.AbstractDVMThread;

/**
 * Created by sergio on 25/3/16.
 */
public interface IDroidefenseFrame {

    String toString();

    void init(final IDroidefenseMethod method);

    void init(final IDroidefenseMethod method, final boolean isChangeThreadFrame);

    void setArgument(final int index, final int value);

    void setArgument(final int index, final long value);

    void setArgument(final int index, final Object value);

    void intArgument(final int index, final Object value);

    void destroy();

    AbstractDVMThread getThread();

    int[] getIntArguments();

    void setIntArguments(int[] intArguments);

    Object[] getObjectArguments();

    void setObjectArguments(Object[] objectArguments);

    int getSingleReturn();

    void setSingleReturn(int singleReturn);

    long getDoubleReturn();

    void setDoubleReturn(long doubleReturn);

    Object getObjectReturn();

    void setObjectReturn(Object objectReturn);

    Throwable getThrowableReturn();

    void setThrowableReturn(Throwable throwableReturn);

    IDroidefenseMethod getMethod();

    void setMethod(IDroidefenseMethod method);

    boolean isChangeThreadFrame();

    void setChangeThreadFrame(boolean changeThreadFrame);

    int getPc();

    int getRegisterCount();

    void setRegisterCount(int registerCount);

    boolean[] getIsObjectRegister();

    void setIsObjectRegister(boolean[] isObjectRegister);

    int[] getIntRegisters();

    void setIntRegisters(int[] intRegisters);

    Object[] getObjectRegisters();

    void setObjectRegisters(Object[] objectRegisters);

    int getArgumentCount();

    void setArgumentCount(int argumentCount);

    Object getMonitor();

    void setMonitor(Object monitor);

    int increasePc();

    int increasePc(int add);

    void resetPc(int point);
}
