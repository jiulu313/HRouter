package com.helloworld.hrouter;

import android.app.Application;

import com.helloworld.hrouter.core.HRouter;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HRouter.getInstance().init(this);
    }
}
