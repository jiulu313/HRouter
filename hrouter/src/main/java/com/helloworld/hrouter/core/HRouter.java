package com.helloworld.hrouter.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.helloworld.hrouter.util.ClassUtil;
import com.helloworld.hrouter.util.Consts;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HRouter {
    private static boolean sDebuggable = false;

    private static HRouter sRouter = new HRouter();


    private Map<String,Class<? extends Activity>> activityMap = new HashMap<>();
    private Context mContext;
    private boolean hasInit;
    private Handler mainHandler;

    private HRouter(){
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public static HRouter getInstance() {
        return sRouter;
    }

    public static boolean debuggable() {
        return sDebuggable;
    }

    public void init(Application application){
        if (!hasInit) {
            hasInit = true;
            mContext = application;
            try {
                //获取包名下所有的类
                Set<String> routerMap = ClassUtil.getFileNameByPackageName(mContext, Consts.ROUTE_ROOT_PAKCAGE);
                for (String className : routerMap) {
                    if (className != null && className.startsWith(Consts.ROUTE_ROOT_PAKCAGE)) {
                        IRouter router = (IRouter) (Class.forName(className).getConstructor().newInstance());
                        router.addActivity();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addActivity(String path,Class<? extends Activity> clazz){
        if (TextUtils.isEmpty(path) || clazz == null) {
            return;
        }
        activityMap.put(path,clazz);
    }

    public Fax withRouter(String path){
        if (com.helloworld.hrouter.util.TextUtils.isEmpty(path)) {
            throw new IllegalArgumentException("path must not be null or empty");
        }else {
            return new Fax(path);
        }
    }

    protected void go(Context context,Fax fax){
        if (Looper.getMainLooper() != Looper.myLooper()) {
            mainHandler.post(() -> startActivity(context,fax));
        }else {
            startActivity(context,fax);
        }
    }

    private void startActivity(Context context,Fax fax){
        Context curContext = context == null ? mContext : context;
        Intent intent = new Intent(curContext, activityMap.get(fax.getPath()));
        intent.putExtras(fax.getBundle());

        if (context == null) { //说明tmpContext 是 application
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        curContext.startActivity(intent);

        //转场动画
        if (curContext instanceof Activity) {
            if (fax.getEnterAnim() != -1 && fax.getExitAnim() != -1) {
                ((Activity)curContext).overridePendingTransition(fax.getEnterAnim(),fax.getExitAnim());
            }
        }
    }
}
