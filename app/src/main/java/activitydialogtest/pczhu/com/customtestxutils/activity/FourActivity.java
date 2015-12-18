package activitydialogtest.pczhu.com.customtestxutils.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import activitydialogtest.pczhu.com.customtestxutils.R;
import activitydialogtest.pczhu.com.customtestxutils.activity.base.BaseActivity;
import activitydialogtest.pczhu.com.customtestxutils.view.gridview.PageStaggeredGridView;

/**
 * 名称：${FILE_NAME}
 * 作用：
 * 描述：
 * 作者：pczhu
 * 创建时间： 15/12/18 下午3:12
 * 版本：V1.0
 * 修改历史：
 */
public class FourActivity extends BaseActivity{
    private SwipeRefreshLayout swipeRefreshLayout;
    private PageStaggeredGridView pageStaggeredGridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        pageStaggeredGridView = (PageStaggeredGridView) findViewById(R.id.grid_view);
    }

    @Override
    protected View getRootView() {
        return  ((ViewGroup)this.findViewById(R.id.ll_root)).getChildAt(1);
    }

    @Override
    public ArrayList getObjectList(Object obj) {
        return null;
    }
}
