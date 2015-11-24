package activitydialogtest.pczhu.com.customtestxutils.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 名称：CustomTestxUtils
 * 作用：
 * 描述：
 * 作者：pczhu
 * 创建时间： 15/11/20 下午6:03
 * 版本：V1.0
 * 修改历史：
 */
public class ProjectBean implements Serializable {

    private String msg;
    private String status;
    private ArrayList<Data> data;
    public class Data{
            private String id;
            private String name;


        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        private String face;
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}
