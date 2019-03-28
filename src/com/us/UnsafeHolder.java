package com.us;

import java.lang.reflect.Field;

public class UnsafeHolder {

    /**
     * module com.us {}
     * Compiler option --add-exports=java.base/jdk.internal.misc=com.us
     * VM option --add-exports=java.base/jdk.internal.misc=com.us
     *
     * @return
     */
    public static jdk.internal.misc.Unsafe getJdkMiscUnsafe() {
        return jdk.internal.misc.Unsafe.getUnsafe();
    }

    /**
     * module com.us {
     * requires jdk.unsupported;
     * }
     *
     * @return
     */
    public static sun.misc.Unsafe getSunMiscUnsafe() {
        sun.misc.Unsafe unsafe = null;
        try {
            Field unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            unsafe = (sun.misc.Unsafe) unsafeField.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return unsafe;
    }

    public static void main(String[] args) {
        System.out.println(getJdkMiscUnsafe());
        System.out.println(getSunMiscUnsafe());
    }
}
