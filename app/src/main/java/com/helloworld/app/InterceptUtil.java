package com.helloworld.app;

import com.helloworld.hrouter.core.HRouter;

class InterceptUtil {
    public void addIntercept(){
        HRouter.getInstance().addActivity("/app/main",com.helloworld.app.MainActivity.class);
    }
}
