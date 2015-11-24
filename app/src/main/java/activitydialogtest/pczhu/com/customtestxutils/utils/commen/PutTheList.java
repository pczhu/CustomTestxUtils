package activitydialogtest.pczhu.com.customtestxutils.utils.commen;

import java.util.ArrayList;

import activitydialogtest.pczhu.com.customtestxutils.domain.User;

/**
 * 名称：CustomTestxUtils
 * 作用：
 * 描述：
 * 作者：pczhu
 * 创建时间： 15/11/20 下午12:12
 * 版本：V1.0
 * 修改历史：
 */
public class PutTheList {
    public static ArrayList<User> userArrayList(int n,int page){
        ArrayList<User> userlist = new ArrayList<User>();
        for (int i= 0 ; i < n; i++){
            User user = new User(page * n,"user"+n,"userage"+n);
            userlist.add(user);
        }
        return userlist;
    }

}
