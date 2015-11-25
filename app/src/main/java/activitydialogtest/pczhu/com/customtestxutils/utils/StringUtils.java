package activitydialogtest.pczhu.com.customtestxutils.utils;

/**
 * 名称：CustomTestxUtils
 * 作用：
 * 描述：
 * 作者：pczhu
 * 创建时间： 15/11/25 上午9:23
 * 版本：V1.0
 * 修改历史：
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return (str == null || "".equals(str) || "".equals(str.trim()));
    }
}
