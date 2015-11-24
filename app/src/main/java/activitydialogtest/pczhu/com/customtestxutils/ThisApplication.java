package activitydialogtest.pczhu.com.customtestxutils;

import android.app.Application;

import activitydialogtest.pczhu.com.customtestxutils.utils.singInstance.InitEveryDependence;


/**
 * 名称：CustomTestxUtils
 * 作用：
 * 描述：
 * 作者：pczhu
 * 创建时间： 15/11/20 上午9:14
 * 版本：V1.0
 * 修改历史：
 */
public class ThisApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InitEveryDependence.getInstance(getApplicationContext(), this).start();
    }


}
