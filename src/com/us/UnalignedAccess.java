package com.us;

import jdk.internal.misc.Unsafe;

public class UnalignedAccess {
    private static Unsafe unsafe = Unsafe.getUnsafe();

    private short s1 = 0x0102;
    private short s2 = 0x0304;


    //26 0000 0001
    //27 0000 0010
    //28 0000 0011
    //29 0000 0100

    /**
     * VM options --add-exports=java.base/jdk.internal.misc=com.us -enableassertions
     *
     * @param args
     */
    public static void main(String[] args) {
        long offset = unsafe.objectFieldOffset(UnalignedAccess.class, "s1");
        boolean isBigEndian = unsafe.isBigEndian();//false

        UnalignedAccess obj = new UnalignedAccess();
        System.out.println(unsafe.getByte(obj, offset));//2
        System.out.println(unsafe.getByte(obj, offset + 1));//1
        System.out.println(unsafe.getByte(obj, offset + 2));//4
        System.out.println(unsafe.getByte(obj, offset + 3));//3
        // 0000 0010 0000 0011

        assert unsafe.getShortUnaligned(obj, offset) == 0x0102;
        assert unsafe.getShortUnaligned(obj, offset + 1) == 0x0401;
        assert unsafe.getShortUnaligned(obj, offset + 2) == 0x0304;
    }
}
