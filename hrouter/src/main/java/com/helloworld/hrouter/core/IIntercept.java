package com.helloworld.hrouter.core;

public interface IIntercept {
    /**
     * @return true:不往下传， false:往下传
     */
    boolean process(Fax fax);
}
