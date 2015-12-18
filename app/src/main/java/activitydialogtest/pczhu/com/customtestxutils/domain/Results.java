package activitydialogtest.pczhu.com.customtestxutils.domain;

import java.io.Serializable;

/**
 * 名称：${FILE_NAME}
 * 作用：
 * 描述：
 * 作者：pczhu
 * 创建时间： 15/12/18 下午3:19
 * 版本：V1.0
 * 修改历史：
 */
public class Results implements Serializable{
    private String resultCode;
    private String reason;
    private String error_code;
    private MyData result;
    public class MyData{
        private String name;
        private String pinyin;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public MyData getResult() {
        return result;
    }

    public void setResult(MyData result) {
        this.result = result;
    }
}
