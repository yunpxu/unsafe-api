package com.us;

import jdk.internal.misc.Unsafe;

public class UnsafeObjectApi {
    private static Unsafe unsafe = Unsafe.getUnsafe();

    private int i;

    public UnsafeObjectApi() {
        i = 1;
    }

    /**
     * Exception is unchecked.
     */
    private static void throwExceptionUnchecked() {
        unsafe.throwException(new Exception());
    }


    /**
     * Exception is checked.
     */
    private static void throwException() {
        try {
            throw new Exception();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * VM options --add-exports=java.base/jdk.internal.misc=com.us -enableassertions
     *
     * @param args
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable {
        byte[] byteContent = UnsafeObjectApi.class.getClassLoader().getResourceAsStream("com/us/UnsafeObjectApi.class").readAllBytes();
        Class clazz = unsafe.defineClass(null, byteContent, 0, byteContent.length, null, null);
        assert clazz.getName() == UnsafeObjectApi.class.getName();//true
        assert clazz != UnsafeObjectApi.class;//true


        UnsafeObjectApi unsafeObject = (UnsafeObjectApi) unsafe.allocateInstance(UnsafeObjectApi.class);
        assert unsafeObject.i == 0;


        int[] intArray = (int[]) unsafe.allocateUninitializedArray(Integer.TYPE, 5);

        throwExceptionUnchecked();
        throwException();

    }
}
