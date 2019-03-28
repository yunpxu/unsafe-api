package com.us;

import jdk.internal.misc.Unsafe;

public class ObjectMemoryAddress {
    private static Unsafe unsafe = Unsafe.getUnsafe();

    public static String addressOf(Object o) {
        Object[] array = new Object[]{o};

        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        switch (addressSize) {
//            case 4:
//                objectAddress = unsafe.getInt(array, baseOffset);
//                break;
            case 8:
                objectAddress = unsafe.getLong(array, baseOffset) * 8;
                break;
            default:
                throw new Error("unsupported address size: " + addressSize);
        }
        return "0x" + Long.toHexString(objectAddress);
    }
}
