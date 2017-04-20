package com.base.resultset.base;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Application的基类 开发过程中有需要可以复制或者继承
 *
 * @author Administrator
 */
public class BaseApplication extends Application {
    private RequestQueue mRequestQueue;
    private static BaseApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        // 自定义崩溃日志类
        /*
         * CrashHandler crashHandler = CrashHandler.getInstance();
		 * crashHandler.init(getApplicationContext());
		 */

    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized BaseApplication getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

}
