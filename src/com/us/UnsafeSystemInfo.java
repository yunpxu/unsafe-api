package com.us;

import jdk.internal.misc.Unsafe;

public class UnsafeSystemInfo {

    private static Unsafe unsafe = Unsafe.getUnsafe();

    /**
     * VM options --add-exports=java.base/jdk.internal.misc=com.us -enableassertions
     *
     * @param args
     */
    public static void main(String[] args) {
//        [yunpxu@yunpxu-mac ~]$ uptime
//        21:26  up 10 days, 32 mins, 4 users, load averages: 1.42 1.78 1.98
        double[] loadAverages = new double[3];
        unsafe.getLoadAverage(loadAverages, 3);
        System.out.format("load averages:%.2f %.2f %.2f", loadAverages[0], loadAverages[1], loadAverages[2]);

        System.out.println(unsafe.addressSize());
        System.out.println(unsafe.pageSize());
        System.out.println(unsafe.isBigEndian());
    }
}
