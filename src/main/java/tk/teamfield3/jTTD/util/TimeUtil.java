package tk.teamfield3.jTTD.util;

public class TimeUtil {

    public static final long SECOND = 1000000000L;

    private static double delta;

    public static long getTime() {
        return System.nanoTime();
    }

    public static double getDelta() {
        return delta;
    }

    public static void setDelta(double delta) {
        TimeUtil.delta = delta;
    }

}
