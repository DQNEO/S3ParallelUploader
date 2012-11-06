package jp.dqneo.amazons3;

import java.io.PrintStream;

public class Logger {

    private static boolean verbose;

    /**
    * @param verbose
    */
    public static void setVerbose(boolean verbose) {
        Logger.verbose = verbose;
    }

    public static void log(String msg) {
        if (verbose) {
            System.out.println(msg);
        }
    }

    public static PrintStream printf(String format, Object ... args) {
        if (verbose) {
            return System.out.format(format, args);
        } else {
            return null;
        }
    }
}
