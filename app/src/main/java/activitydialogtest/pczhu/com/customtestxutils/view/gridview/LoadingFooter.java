package activitydialogtest.pczhu.com.customtestxutils.view.gridview;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import activitydialogtest.pczhu.com.customtestxutils.R;
import activitydialogtest.pczhu.com.customtestxutils.utils.SolidToast;
import activitydialogtest.pczhu.com.customtestxutils.view.titanic.Titanic;
import activitydialogtest.pczhu.com.customtestxutils.view.titanic.TitanicTextView;

/**
 * Created by storm on 14-4-12.
 */
public class LoadingFooter {
    //private long delaytime = 0;
    private long oldtime = 0;
    private Context mContext;

    protected View mLoadingFooter;
    /**
     * 终止TheEnd
     */
   // TextView mLoadingText;
    /**
     * 波浪效果文字
     */
    //TitanicTextView mTitanicText;

    //private Titanic mTitanic;

    protected State mState = State.Idle;

    public static enum State {
        /**
         * 无Loading
         */
        Idle,
        /**
         * Loading结束终止
         */
        TheEnd,
        /**
         * Loading中
         */
        Loading,
        /**
         * 出错了
         */
        ERROR
    }

    public LoadingFooter(Context context) {
        this.mContext = context;
        mLoadingFooter = LayoutInflater.from(context).inflate(R.layout.loading_footer, null);
        mLoadingFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 屏蔽点击
            }
        });

        setState(State.Idle);
    }

    /**
     * 获取布局
     * @return
     */
    public View getView() {
        return mLoadingFooter;
    }

    /**
     * 获得状态
     * @return
     */
    public State getState() {
        return mState;
    }

    public void setState(final State state, long delay) {
        if(delay < 1000){
            delay = 1000;
        }
        mLoadingFooter.postDelayed(new Runnable() {
            @Override
            public void run() {
                setState(state);
            }
        }, delay);
    }

    public void setState(State status) {
        if (mState == status) {
            return;
        }
        mState = status;

        mLoadingFooter.setVisibility(View.VISIBLE);
        switch (status) {
            case Loading://正在进行中
                oldtime = SystemClock.currentThreadTimeMillis();
                SolidToast.make((Activity) mContext, "正在加载中", 100000,Gravity.BOTTOM)
                    .setBackgroundColorId(R.color.colorPrimaryDark)
                    .show();
                break;
            case TheEnd://终点
                SolidToast.make((Activity) mContext, "没有更多数据",Gravity.BOTTOM)
                        .setBackgroundColorId(R.color.colorPrimaryDark)
                        .show();
                break;
            case ERROR:
                SolidToast.make((Activity) mContext, "加载出错",Gravity.BOTTOM)
                        .setBackgroundColorId(R.color.colorPrimaryDark)
                        .show();
                break;
            case Idle://消失
                long delaytime = SystemClock.currentThreadTimeMillis() - oldtime;
                if(delaytime > 1000 ){
                    delaytime = 0;
                }else{
                    delaytime = 1000;
                }
                mLoadingFooter.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingFooter.setVisibility(View.GONE);
                        SolidToast.hideToastView((Activity) mContext);
                    }
                }, delaytime);
                break;
        }
    }
}
