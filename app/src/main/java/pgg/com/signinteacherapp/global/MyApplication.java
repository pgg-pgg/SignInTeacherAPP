package pgg.com.signinteacherapp.global;

import android.app.Activity;
import android.app.Application;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by PDD on 2018/3/23.
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    //记录栈中所有的Activity
    private List<Activity> activities_all = new ArrayList<>();
    //记录需要关闭的Activity
    private List<Activity> activities_close = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * 获得实例
     *
     * @return
     */
    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 添加当前Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities_all.add(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            this.activities_all.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 给临时列表添加当前Activity
     *
     * @param activity
     */
    public void addTemActivity(Activity activity) {
        activities_close.add(activity);
    }

    /**
     * 在临时列表中结束指定的Activity
     *
     * @param activity
     */
    public void finishTemActivity(Activity activity) {
        if (activity != null) {
            this.activities_close.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束关闭的Activity列表中的所有Activity
     */
    public void exitActivity() {
        for (Activity activity : activities_close) {
            if (activity != null) {
                activity.finish();
                activity = null;
            }
        }
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        for (Activity activity : activities_all) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }

}
