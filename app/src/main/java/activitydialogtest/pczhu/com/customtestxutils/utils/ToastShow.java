package activitydialogtest.pczhu.com.customtestxutils.utils;

import android.content.Context;
import android.widget.Toast;

import static android.widget.Toast.makeText;

/**
 * 名称：CustomTestxUtils
 * 作用：
 * 描述：
 * 作者：pczhu
 * 创建时间： 15/11/25 上午9:19
 * 版本：V1.0
 * 修改历史：
 */
public class ToastShow {
    private static Toast toast;
    public static void showTextToast(Context context,String msg) {
        if(StringUtils.isEmpty(msg)|| context == null){
            return;
        }
        if (toast == null) {
            toast = makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
