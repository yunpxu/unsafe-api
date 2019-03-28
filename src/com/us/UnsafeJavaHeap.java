package com.us;

import jdk.internal.misc.Unsafe;

public class UnsafeJavaHeap {
    private static Unsafe unsafe = Unsafe.getUnsafe();

    //putGetMemory
    private byte i;


    //setMemory
    private byte[] byteArray = new byte[4];
    private byte[] _byteArray = new byte[4];
    private short[] shortArray = new short[2];


    //putInt/putObject/putBoolean/putByte/putShort/putChar/putLong/putFloat/putDouble/putAddress(object, offset, value)
    //getInt/getObject/getBoolean/getByte/getShort/getChar/getLong/getFloat/getDouble/getAddress(object, offset)
    private static void putGetMemory() {
        UnsafeJavaHeap object = new UnsafeJavaHeap();
        long iOffset = unsafe.objectFieldOffset(UnsafeJavaHeap.class, "i");
        byte value = 1;

        unsafe.putByte(object, iOffset, value);

        assert unsafe.getByte(object, iOffset) == value;
        assert object.i == value;
    }

    //setMemory(Object o, long offset, long byteArray, byte value)
    //copyMemory(Object srcBase, long srcOffset, Object destBase, long destOffset, long byteArray)
    //copySwapMemory(Object srcBase, long srcOffset, Object destBase, long destOffset, long byteArray, long elemSize)
    private static void setMemory() {
        UnsafeJavaHeap object = new UnsafeJavaHeap();
        byte value = 1;

        //set UnsafeJavaHeap.byteArray to [1,1,1,1]
        int size = object.byteArray.length;
        unsafe.setMemory(object.byteArray, Unsafe.ARRAY_BYTE_BASE_OFFSET, Unsafe.ARRAY_BYTE_INDEX_SCALE * size, value);
        for (int i = 0; i < size; i++) {
            assert object.byteArray[i] == value;
        }

        //copy UnsafeJavaHeap.byteArray[1,1,1,1] to UnsafeJavaHeap._byteArray [1,1,1,1]
        unsafe.copyMemory(object.byteArray, Unsafe.ARRAY_BYTE_BASE_OFFSET, object._byteArray, Unsafe.ARRAY_BYTE_BASE_OFFSET, Unsafe.ARRAY_BYTE_INDEX_SCALE * size);
        for (int i = 0; i < size; i++) {
            assert object._byteArray[i] == value;
        }

        //copy UnsafeJavaHeap.byteArray[1,1,1,1] to UnsafeJavaHeap.shortArray[257,257]
        //0000 0001, 0000 0001, 0000 0001, 0000 0001
        //0000 0001 0000 0001,  0000 0001 0000 0001
        size = object.shortArray.length;
        unsafe.copySwapMemory(object.byteArray, Unsafe.ARRAY_BYTE_BASE_OFFSET, object.shortArray, Unsafe.ARRAY_SHORT_BASE_OFFSET, Unsafe.ARRAY_SHORT_INDEX_SCALE * size, size);
        for (int i = 0; i < size; i++) {
            assert object.shortArray[i] == 257;
        }
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
