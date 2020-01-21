package com.sculptee.utils.exceptionhandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Web Corridor on 19-01-2018.
 */

public class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final Context myContext;
    private final Class<?> myActivityClass;
    private Activity activity;
    public DefaultExceptionHandler(Activity a, Context context, Class<?> c) {

        activity = a;
        myContext = context;
        myActivityClass = c;
    }

    public void uncaughtException(Thread thread, Throwable exception) {

        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        System.err.println(stackTrace);// You can use LogCat too
        Log.e("Crash error",stackTrace+"");
        Intent intent = new Intent(myContext, myActivityClass);

        String s = stackTrace.toString();
        //you can use this String to know what caused the exception and in which Activity
        intent.putExtra("uncaughtException", "Exception is: " + stackTrace.toString());
        intent.putExtra("stacktrace", s);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        myContext.startActivity(intent);
        //for restarting the Activity
        //Process.killProcess(Process.myPid());
        activity.finish();
        System.exit(0);
    }
}