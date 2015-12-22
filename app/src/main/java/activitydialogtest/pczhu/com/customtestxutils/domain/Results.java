package activitydialogtest.pczhu.com.customtestxutils.domain;

import java.io.Serializable;
import java.util.ArrayList;

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
    private String statue;
    private String msg;
    private ArrayList<Data> data;
    public class Data {
        private String pid;//项目id
        private String img_app;
        private String name;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getImg_app() {
            return img_app;
        }

        public void setImg_app(String img_app) {
            this.img_app = img_app;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}
