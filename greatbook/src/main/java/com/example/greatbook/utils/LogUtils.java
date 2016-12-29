package com.example.greatbook.utils;

import android.util.Log;

/**
 * Created by MBENBEN on 2016/12/6.
 */

public class LogUtils {
    /**
     * debug or not
     * when release, set debug 'false'
     */
    private static boolean debug = true;



    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (sts == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            return "[" + Thread.currentThread().getName() + "(" + Thread.currentThread().getId()
                    + "): " + st.getClassName() +"]";
        }

        return null;
    }

    private static String createMessage(String msg) {
        String functionName = getFunctionName();
        String message = (functionName == null ? msg : (functionName + " - " + msg));
        return message;
    }

    /**
     * log.i
     */
    public static void i(String tag, String msg) {
        if (debug) {
            String message = createMessage(msg);
            Log.i(tag, message);
        }
    }

    /**
     * log.v
     */
    public static void v(String tag, String msg) {
        if (debug) {
            String message = createMessage(msg);
            Log.v(tag, message);
        }
    }

    /**
     * log.d
     */
    public static void d(String tag, String msg) {
        if (debug) {
            String message = createMessage(msg);
            Log.d(tag, message);
        }
    }

    /**
     * log.e
     */
    public static void e(String tag, String msg) {
        if (debug) {
            String message = createMessage(msg);
            Log.e(tag, message);
        }
    }
    /**
     * log.error
     */
    public static void error(String tag, Exception e){
        if(debug){
            StringBuffer sb = new StringBuffer();
            String name = getFunctionName();
            StackTraceElement[] sts = e.getStackTrace();

            if (name != null) {
                sb.append(name+" - "+e+"\r\n");
            } else {
                sb.append(e+"\r\n");
            }
            if (sts != null && sts.length > 0) {
                for (StackTraceElement st:sts) {
                    if (st != null) {
                        sb.append("[ "+st.getFileName()+":"+st.getLineNumber()+" ]\r\n");
                    }
                }
            }
            Log.e(tag,sb.toString());
        }
    }
    /**
     * log.w
     */
    public static void w(String tag, String msg) {
        if (debug) {
            String message = createMessage(msg);
            Log.w(tag, message);
        }
    }

    /**
     * set debug false
     */
    public static void setDebug(boolean d) {
        debug = d;
    }

}