package com.helloworld.app;

import com.helloworld.hrouter.annotation.Intercept;
import com.helloworld.hrouter.core.Fax;
import com.helloworld.hrouter.core.IIntercept;

@Intercept
class MyIntercept implements IIntercept {
    @Override
    public boolean process(Fax fax) {
        return false;
    }
}
