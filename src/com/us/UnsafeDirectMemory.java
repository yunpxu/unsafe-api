package com.us;

import jdk.internal.misc.Unsafe;

public class UnsafeDirectMemory {
    private static Unsafe unsafe = Unsafe.getUnsafe();

    //putInt/putByte/putShort/putChar/putLong/putFloat/putDouble(address, value)
    //getInt/getByte/getShort/getChar/getLong/getFloat/getDouble(address)
    private static void putGetMemory() {
        byte value = 1;
        //allocateMemory(long bytes)
        Long address = unsafe.allocateMemory(Byte.BYTES);

        unsafe.putByte(address, value);
        assert unsafe.getByte(address) == value;

        unsafe.putAddress(address, value);
        assert unsafe.getAddress(address) == value;

        //freeMemory
        unsafe.freeMemory(address);
    }

    //allocateMemory(long bytes)
    //reallocateMemory(long address, long bytes)
    //setMemory(long address, long bytes, byte value)
    //copyMemory(long srcAddress, long destAddress, long bytes)
    //copySwapMemory(long srcAddress, long destAddress, long bytes, long elemSize)
    //freeMemory(long address)
    private static void setMemory() {
        Long address1 = unsafe.allocateMemory(Byte.BYTES);
        address1 = unsafe.reallocateMemory(address1, Short.BYTES);

        unsafe.setMemory(address1, Short.BYTES, (byte) 1);//0x0101
        assert unsafe.getShort(address1) == 0x0101;

        Long address2 = unsafe.allocateMemory(Short.BYTES);
        unsafe.copyMemory(address1, address2, Short.BYTES);
        assert unsafe.getShort(address2) == 0x0101;

        Long address3 = unsafe.allocateMemory(Integer.BYTES);
        unsafe.copySwapMemory(address1, address3, Integer.BYTES, 4);
        assert unsafe.getInt(address3) == 0x01010000;

        unsafe.freeMemory(address1);
        unsafe.freeMemory(address2);
        unsafe.freeMemory(address3);
    }

    /**
     * VM options --add-exports=java.base/jdk.internal.misc=com.us -enableassertions
     *
     * @param args
     */
    public static void main(String[] args) {
        putGetMemory();
        setMemory();
    }
}
