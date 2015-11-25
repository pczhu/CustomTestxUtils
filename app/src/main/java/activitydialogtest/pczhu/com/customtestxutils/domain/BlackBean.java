package activitydialogtest.pczhu.com.customtestxutils.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 名称：CustomTestxUtils
 * 作用：
 * 描述：
 * 作者：pczhu
 * 创建时间： 15/11/25 上午9:36
 * 版本：V1.0
 * 修改历史：
 */
public class BlackBean implements Serializable {
    private String statue;
    private String msg;
    private ArrayList<Data> data;
    public class Data{
        private String pid;//项目id
        private String project_name;//项目名称
        private String cover;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
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
