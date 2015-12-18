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
import activitydialogtest.pczhu.com.customtestxutils.domain.BlackBean;
import activitydialogtest.pczhu.com.customtestxutils.domain.ProjectBean;

/**
 * 名称：CustomTestxUtils
 * 作用：
 * 描述：
 * 作者：pczhu
 * 创建时间： 15/11/25 上午9:46
 * 版本：V1.0
 * 修改历史：
 */
public class ThirdAdapter extends MyBaseAdapter{
    public ThirdAdapter(Context context, ArrayList<ProjectBean.Data> userList) {
        super(context, userList);
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
            convertView = View.inflate(mContext, R.layout.item_third,null);
            holder.tv = (TextView) convertView.findViewById(R.id.textView2);
            holder.iv = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        BlackBean.Data bean = (BlackBean.Data) userList.get(position);
        holder.tv.setText(bean.getPid()+":"+bean.getProject_name());
        x.image().bind(holder.iv, bean.getCover(), ImageOptions.DEFAULT);


        return convertView;
    }

    public class Holder {
        TextView tv;
        ImageView iv;
    }
}
