package com.us;

import jdk.internal.misc.Unsafe;

public class UnsafePutAddress {

    private static Unsafe unsafe = Unsafe.getUnsafe();

    private byte b1;
    private byte b2;

    /**
     * VM options --add-exports=java.base/jdk.internal.misc=com.us -enableassertions
     * If you put a 2 bytes value(eg.256) to b1,
     * then the lower byte would be written to b1
     * and the higher byte would be written to b2(address next to b1)
     *
     * @param args
     */
    public static void main(String[] args) {
        UnsafePutAddress object = new UnsafePutAddress();
        //Byte.MAX_VALUE  127(1 byte)  0111 1111
        //Byte.MIN_VALUE -128(1 byte)  1000 0000

        //                  long        byte1       byte2
        //  0111 1111       127         127        0
        //  1000 0000       128        -128        0
        //  1000 0001       129        -127        0
        //  1111 1111       255        -1          0
        //1 0000 0000       256         0          1
        //1 0000 0001       257         1          1
        long offset1 = unsafe.objectFieldOffset(UnsafePutAddress.class, "b1");
        long offset2 = unsafe.objectFieldOffset(UnsafePutAddress.class, "b2");
        //       b1          b2
        //0000 0000 | 0000 0000
        assert offset2 - offset1 == 1;

        unsafe.putAddress(object, offset1, 127);
        assert unsafe.getAddress(object, offset1) == 127;
        assert unsafe.getAddress(object, offset2) == 0;
        assert object.b1 == 127;
        assert object.b2 == 0;

        unsafe.putAddress(object, offset1, 128);
        assert unsafe.getAddress(object, offset1) == 128;
        assert unsafe.getAddress(object, offset2) == 0;
        assert object.b1 == -128;
        assert object.b2 == 0;

        unsafe.putAddress(object, offset1, 129);
        assert unsafe.getAddress(object, offset1) == 129;
        assert unsafe.getAddress(object, offset2) == 0;
        assert object.b1 == -127;
        assert object.b2 == 0;

        unsafe.putAddress(object, offset1, 255);
        assert unsafe.getAddress(object, offset1) == 255;
        assert unsafe.getAddress(object, offset2) == 0;
        assert object.b1 == -1;
        assert object.b2 == 0;

        unsafe.putAddress(object, offset1, 256);
        assert unsafe.getAddress(object, offset1) == 256;
        assert unsafe.getAddress(object, offset2) == 1;
        assert object.b1 == 0;
        assert object.b2 == 1;

        unsafe.putAddress(object, offset1, 257);
        assert unsafe.getAddress(object, offset1) == 257;
        assert unsafe.getAddress(object, offset2) == 1;
        assert object.b1 == 1;
        assert object.b2 == 1;
    }
}
