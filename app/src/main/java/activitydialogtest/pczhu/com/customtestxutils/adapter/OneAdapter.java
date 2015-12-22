package activitydialogtest.pczhu.com.customtestxutils.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

import activitydialogtest.pczhu.com.customtestxutils.R;
import activitydialogtest.pczhu.com.customtestxutils.adapter.MyBaseAdapter;
import activitydialogtest.pczhu.com.customtestxutils.domain.ProjectBean;

/**
 * 名称：CustomTestxUtils
 * 作用：
 * 描述：
 * 作者：pczhu
 * 创建时间： 15/11/25 上午8:54
 * 版本：V1.0
 * 修改历史：
 */
public class OneAdapter extends MyBaseAdapter {
    public OneAdapter(Context context, ArrayList<?> userList) {
        super(context, userList);
    }

    @Override
    public int getCount() {
        return userList!= null?userList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView == null){
            holder = new Holder();
            convertView = View.inflate(mContext, R.layout.item_layout,null);
            holder.tv = (TextView) convertView.findViewById(R.id.tv_caption);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv_normal);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        ProjectBean.Data bean = (ProjectBean.Data) userList.get(position);
        holder.tv.setText(bean.getId()+":"+bean.getName());
        x.image().bind(holder.iv, bean.getFace(), ImageOptions.DEFAULT);


        return convertView;
    }

    public class Holder {
        TextView tv;
        ImageView iv;
    }
}
