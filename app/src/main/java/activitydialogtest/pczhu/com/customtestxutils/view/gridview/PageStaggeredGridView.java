package activitydialogtest.pczhu.com.customtestxutils.view.gridview;

/**
 * 名称：CustomTestxUtils
 * 作用：
 * 描述：
 * 作者：pczhu
 * 创建时间： 15/11/20 上午11:03
 * 版本：V1.0
 * 修改历史：
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;

import com.etsy.android.grid.StaggeredGridView;

/**
 * 自定义不规则GridView
 * Created by storm on 14-5-6.
 */
public class PageStaggeredGridView extends StaggeredGridView implements AbsListView.OnScrollListener {
    private LoadingFooter mLoadingFooter;

    private OnLoadNextListener mLoadNextListener;

    public PageStaggeredGridView(Context context) {
        super(context);
        init();
    }

    public PageStaggeredGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageStaggeredGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mLoadingFooter = new LoadingFooter(getContext());
        addFooterView(mLoadingFooter.getView());
        setOnScrollListener(this);
    }

    public void setLoadNextListener(OnLoadNextListener listener) {
        mLoadNextListener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }
    /**
     * firstVisibleItem 表示在当前屏幕显示的第一个listItem在整个listView里面的位置（下标从0开始）
     * visibleItemCount表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数
     * totalItemCount表示ListView的ListItem总数
     * listView.getLastVisiblePosition()表示在现时屏幕最后一个ListItem
     * (最后ListItem要完全显示出来才算)在整个ListView的位置（下标从0开始）
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mLoadingFooter.getState() == LoadingFooter.State.Loading
                || mLoadingFooter.getState() == LoadingFooter.State.TheEnd) {
            return;
        }
        if (firstVisibleItem + visibleItemCount >= totalItemCount
                && totalItemCount != 0
                && totalItemCount != getHeaderViewsCount() + getFooterViewsCount()
                && mLoadNextListener != null) {
            mLoadingFooter.setState(LoadingFooter.State.Loading);
            mLoadNextListener.onLoadNext();
        }
    }

    public void setState(LoadingFooter.State status) {
        mLoadingFooter.setState(status);
    }

    public void setState(LoadingFooter.State status, long delay) {
        mLoadingFooter.setState(status, delay);
    }
    public LoadingFooter.State getState(){
        return mLoadingFooter.getState();
    }
}