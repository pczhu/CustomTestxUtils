package activitydialogtest.pczhu.com.customtestxutils.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.http.RequestParams;

import java.util.ArrayList;

import activitydialogtest.pczhu.com.customtestxutils.R;
import activitydialogtest.pczhu.com.customtestxutils.activity.base.BaseActivity;
import activitydialogtest.pczhu.com.customtestxutils.adapter.OneAdapter;
import activitydialogtest.pczhu.com.customtestxutils.domain.ProjectBean;
import activitydialogtest.pczhu.com.customtestxutils.view.gridview.OnLoadNextListener;
import activitydialogtest.pczhu.com.customtestxutils.view.gridview.PageStaggeredGridView;


public class SecondActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,OnLoadNextListener{
    SwipeRefreshLayout swipeRefreshLayout;
    PageStaggeredGridView pageStaggeredGridView;
    private ArrayList<ProjectBean.Data> userlist;
    private OneAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uiinit();
    }
    private void uiinit(){
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        pageStaggeredGridView = (PageStaggeredGridView) findViewById(R.id.grid_view);
        mainAdapter = new OneAdapter(this,userlist);
        openListener(swipeRefreshLayout,
                pageStaggeredGridView,
                mainAdapter,
                ProjectBean.class,
                new RequestParams("http://app.renrentou.com/star/GetInvestor"),
                new OnActionListener() {

        });
        addErrorView(this);
    }
    protected View getRootView()
    {
        return ((ViewGroup)this.findViewById(android.R.id.content)).getChildAt(0);
    }
    @Override
    public ArrayList getObjectList(Object obj) {

        return ((ProjectBean)obj).getData();
    }


}
