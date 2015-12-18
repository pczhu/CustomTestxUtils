package activitydialogtest.pczhu.com.customtestxutils.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.http.RequestParams;

import java.util.ArrayList;

import activitydialogtest.pczhu.com.customtestxutils.R;
import activitydialogtest.pczhu.com.customtestxutils.activity.base.BaseActivity;
import activitydialogtest.pczhu.com.customtestxutils.adapter.OneAdapter;
import activitydialogtest.pczhu.com.customtestxutils.adapter.ThirdAdapter;
import activitydialogtest.pczhu.com.customtestxutils.domain.BlackBean;
import activitydialogtest.pczhu.com.customtestxutils.domain.FailBean;
import activitydialogtest.pczhu.com.customtestxutils.domain.ProjectBean;
import activitydialogtest.pczhu.com.customtestxutils.view.gridview.PageStaggeredGridView;

/**
 * 名称：CustomTestxUtils
 * 作用：
 * 描述：
 * 作者：pczhu
 * 创建时间： 15/11/25 上午9:34
 * 版本：V1.0
 * 修改历史：
 */
public class ThirdActivity extends BaseActivity{

    SwipeRefreshLayout swipeRefreshLayout;
    PageStaggeredGridView pageStaggeredGridView;
    private ArrayList<ProjectBean.Data> userlist;
    private ThirdAdapter mainAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        pageStaggeredGridView = (PageStaggeredGridView) findViewById(R.id.grid_view);
        mainAdapter = new ThirdAdapter(this,userlist);
        RequestParams requestParams = new RequestParams("http://app.renrentou.com/user/GetBlackList");
        requestParams.addBodyParameter("type", "1");
        openListener(swipeRefreshLayout,
                pageStaggeredGridView,
                mainAdapter,
                BlackBean.class,
                FailBean.class,
                requestParams,
                new OnActionListener() {

        });
        addErrorView(this);
    }

    @Override
    protected View getRootView() {
        return ((ViewGroup)this.findViewById(R.id.ll_root)).getChildAt(1);
    }

    @Override
    public ArrayList getObjectList(Object obj) {

        return ((BlackBean)obj).getData();
    }
}
