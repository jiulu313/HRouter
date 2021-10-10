package com.helloworld.app;

import android.util.Log;

import com.helloworld.hrouter.annotation.Intercept;
import com.helloworld.hrouter.core.Fax;
import com.helloworld.hrouter.core.IIntercept;

@Intercept
public class MyIntercept implements IIntercept {
    @Override
    public boolean process(Fax fax) {
        Log.e("zh88","path=" + fax.getPath());
        return true;
    }
}
