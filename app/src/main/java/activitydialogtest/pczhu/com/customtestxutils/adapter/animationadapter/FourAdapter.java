package activitydialogtest.pczhu.com.customtestxutils.adapter.animationadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

import activitydialogtest.pczhu.com.customtestxutils.R;
import activitydialogtest.pczhu.com.customtestxutils.adapter.MyBaseAdapter;
import activitydialogtest.pczhu.com.customtestxutils.domain.BlackBean;
import activitydialogtest.pczhu.com.customtestxutils.domain.Results;

/**
 * 名称：${FILE_NAME}
 * 作用：
 * 描述：
 * 作者：pczhu
 * 创建时间： 15/12/18 下午3:47
 * 版本：V1.0
 * 修改历史：
 */
public class FourAdapter extends MyBaseAdapter<Results.Data> {
    ImageOptions imageOptions;
    public FourAdapter(Context context, ArrayList<Results.Data> userList) {
        super(context, userList);
        imageOptions = new ImageOptions.Builder()
               // .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
                .setRadius(DensityUtil.dip2px(5))
                        // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(false)
                        // 加载中或错误图片的ScaleType
                        //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setLoadingDrawableId(R.drawable.j)
                .setFailureDrawableId(R.drawable.j)
                .build();
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
        Results.Data bean =  userList.get(position);
        holder.tv.setText(bean.getPid()+":"+bean.getName());
        x.image().bind(holder.iv, bean.getImg_app(), imageOptions);


        return convertView;
    }


    public class Holder {
        TextView tv;
        ImageView iv;
    }
}
