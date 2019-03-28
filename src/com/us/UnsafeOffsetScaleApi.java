package com.us;

import jdk.internal.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeOffsetScaleApi {
    private static Unsafe unsafe = Unsafe.getUnsafe();
    private static int si;
    private int i;

    /**
     * VM options --add-exports=java.base/jdk.internal.misc=com.us -enableassertions
     *
     * @param args
     * @throws NoSuchFieldException
     */
    public static void main(String[] args) throws NoSuchFieldException {
        //field
        Field iField = UnsafeOffsetScaleApi.class.getDeclaredField("i");
        Field siField = UnsafeOffsetScaleApi.class.getDeclaredField("si");

        System.out.println(unsafe.objectFieldOffset(UnsafeOffsetScaleApi.class, "i"));
        System.out.println(unsafe.objectFieldOffset(iField));

        System.out.println(unsafe.objectFieldOffset(UnsafeOffsetScaleApi.class, "si"));
        System.out.println(unsafe.staticFieldOffset(siField));
        System.out.println(unsafe.staticFieldBase(siField));


        System.out.println(unsafe.arrayBaseOffset(byte[].class));

        assert unsafe.arrayIndexScale(byte[].class) == 1;
        assert unsafe.arrayIndexScale(int[].class) == 4;
        assert unsafe.arrayIndexScale(Object[].class) == 4;
        assert unsafe.arrayIndexScale(long[].class) == 8;
    }
}
