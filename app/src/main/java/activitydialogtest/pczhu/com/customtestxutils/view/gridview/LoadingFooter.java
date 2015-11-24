package activitydialogtest.pczhu.com.customtestxutils.view.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import activitydialogtest.pczhu.com.customtestxutils.R;
import activitydialogtest.pczhu.com.customtestxutils.view.titanic.Titanic;
import activitydialogtest.pczhu.com.customtestxutils.view.titanic.TitanicTextView;

/**
 * Created by storm on 14-4-12.
 */
public class LoadingFooter {
    protected View mLoadingFooter;
    /**
     * 终止TheEnd
     */
    TextView mLoadingText;
    /**
     * 波浪效果文字
     */
    TitanicTextView mTitanicText;

    private Titanic mTitanic;

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
        mLoadingFooter = LayoutInflater.from(context).inflate(R.layout.loading_footer, null);
        mLoadingFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 屏蔽点击
            }
        });
        mLoadingText = (TextView) mLoadingFooter.findViewById(R.id.textView);
        mTitanicText = (TitanicTextView) mLoadingFooter.findViewById(R.id.tv_titanic);
        mTitanic = new Titanic();
        mTitanic.start(mTitanicText);
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
                mLoadingText.setVisibility(View.GONE);
                mTitanicText.setVisibility(View.VISIBLE);
                break;
            case TheEnd://终点
                mLoadingText.setVisibility(View.GONE);
                mTitanicText.setVisibility(View.GONE);
                break;
            case ERROR:
                mLoadingText.setText("加载失败，网络出错");
                mLoadingText.setVisibility(View.VISIBLE);
                mTitanicText.setVisibility(View.GONE);
                break;
            default://消失
                mLoadingFooter.setVisibility(View.GONE);
                break;
        }
    }
}
