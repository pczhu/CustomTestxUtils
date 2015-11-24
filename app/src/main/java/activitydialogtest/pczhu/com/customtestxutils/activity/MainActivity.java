package activitydialogtest.pczhu.com.customtestxutils.activity;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;

import org.xutils.common.Callback;
import org.xutils.common.Callback.CacheCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import activitydialogtest.pczhu.com.customtestxutils.R;
import activitydialogtest.pczhu.com.customtestxutils.adapter.MainAdapter;
import activitydialogtest.pczhu.com.customtestxutils.adapter.animationadapter.CardsAnimationAdapter;
import activitydialogtest.pczhu.com.customtestxutils.domain.FailBean;
import activitydialogtest.pczhu.com.customtestxutils.domain.ProjectBean;
import activitydialogtest.pczhu.com.customtestxutils.view.gridview.LoadingFooter;
import activitydialogtest.pczhu.com.customtestxutils.view.gridview.OnLoadNextListener;
import activitydialogtest.pczhu.com.customtestxutils.view.gridview.PageStaggeredGridView;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,OnLoadNextListener{
    SwipeRefreshLayout swipeRefreshLayout;
    PageStaggeredGridView pageStaggeredGridView;
    private int page = 1;
    private ArrayList<ProjectBean.Data> userlist;
    private MainAdapter mainAdapter;
    private ProjectBean project;
    private boolean isRefreshFromTop;
    private TextView tv_error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uiinit();

        System.out.println("id:" + getRootView(this).getId());
        System.out.println("name::" + getRootView(this).getClass().getName());
    }
    private void uiinit(){
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        pageStaggeredGridView = (PageStaggeredGridView) findViewById(R.id.grid_view);
        pageStaggeredGridView.setLoadNextListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        mainAdapter = new MainAdapter(this,userlist);

        AnimationAdapter animationAdapter = new CardsAnimationAdapter(mainAdapter);//添加卡片式动画
        animationAdapter.setAbsListView(pageStaggeredGridView);
        pageStaggeredGridView.setAdapter(animationAdapter);
        addErrorView();
        loadFirst();
    }
    @Override
    public void onLoadNext() {
        page++;
        loadData(page);
    }
    @Override
    public void onRefresh() {
        pageStaggeredGridView.setState(LoadingFooter.State.Idle);
        loadFirst();
    }
    private void loadFirst(){
        page = 1;
        loadData(page);

    }

    private void loadData(int page) {

        isRefreshFromTop = (1 == page);//如果是1 就是刷新了
        if (!swipeRefreshLayout.isRefreshing() && isRefreshFromTop) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                    Log.d("UI", "开启");
                }
            });
        }

        //SystemClock.sleep(2000);
        sendToHttp(page);
    }


    private Callback.Cancelable cancelable;
    private void sendToHttp(final int page) {
        RequestParams requestParams = new RequestParams("http://app.renrentou.com/star/GetInvestor");
        requestParams.addBodyParameter("page",page+"");
        requestParams.addBodyParameter("page_num", "5");

        cancelable = x.http().post(requestParams, new CacheCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                FailBean failBean = null;
                try {
                    project = gson.fromJson(s, ProjectBean.class);
                }catch (Exception e){
                    failBean = gson.fromJson(s, FailBean.class);
                }

                if(failBean == null && project!=null){
                    if(isRefreshFromTop){
                        userlist = project.getData();
                    }else{
                        userlist.addAll(project.getData());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainAdapter.notifyDataSetChanged(userlist);
                            if(isRefreshFromTop)
                                swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(false);
                                        Log.d("UI", "关闭");
                                    }
                                });
                            else
                                pageStaggeredGridView.setState(LoadingFooter.State.Idle);
                            showError(0,false);
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(isRefreshFromTop){
                                swipeRefreshLayout.setRefreshing(false);
                            }else {
                                pageStaggeredGridView.setState(LoadingFooter.State.TheEnd);
                            }
                            controlWhichShow(1);
                        }
                    });

                }


            }
            @Override
            public void onError(Throwable throwable, boolean b) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isRefreshFromTop) {
                            swipeRefreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    swipeRefreshLayout.setRefreshing(false);
                                    Log.d("UI", "关闭");
                                }
                            });
                        }else {
                            pageStaggeredGridView.setState(LoadingFooter.State.ERROR);
                        }
                        controlWhichShow(2);

                    }
                });

            }
            @Override
            public void onCancelled(CancelledException e) {
                pageStaggeredGridView.setState(LoadingFooter.State.TheEnd);
            }
            @Override
            public void onFinished() {

            }
            @Override
            public boolean onCache(String s) {
                return false;
            }
        });
    }
    private View getRootView(Activity context)
    {
        return ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * 添加错误布局
     */
    public void addErrorView(){

        View error_view = View.inflate(this,R.layout.error_layout,null);

        tv_error = (TextView) error_view.findViewById(R.id.tv_error);
        ((ViewGroup)getRootView(MainActivity.this))
                .addView(error_view,
                        new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));



    }

    /**
     * 判断如何显示
     * @param statue  状态  1没有数据 2访问失败
     */
    public void controlWhichShow(int statue){
        boolean flag = true;
        if(userlist!=null && userlist.size()!=0){
            if(statue == 1){
                Toast.makeText(MainActivity.this, "没有找到相关数据", Toast.LENGTH_LONG).show();
            }else if(statue == 2){
                Toast.makeText(MainActivity.this, "网络请求出现问题", Toast.LENGTH_LONG).show();
            }
            flag = false;
        }
        showError(statue,flag);

    }
    /**
     * 显示文字
     * @param statue
     * @param flag
     */
    public void showError(int statue,boolean flag){
        if(statue == 1){
            tv_error.setText("无数据");
            refreshView(LoadingFooter.State.TheEnd);
        }else if(statue == 2){
            tv_error.setText("访问失败");
            refreshView(LoadingFooter.State.ERROR);
        }
        if(flag){
            ((ViewGroup)getRootView(MainActivity.this))
                    .getChildAt(((ViewGroup) getRootView(MainActivity.this)).getChildCount() - 1)
                    .setVisibility(View.VISIBLE);

        }else{
            ((ViewGroup)getRootView(MainActivity.this))
                    .getChildAt(((ViewGroup) getRootView(MainActivity.this)).getChildCount() - 1)
                    .setVisibility(View.GONE);

        }
    }

    private void refreshView(LoadingFooter.State error) {
        if (!isRefreshFromTop)
            pageStaggeredGridView.setState(error);
        else
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelable.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    //    private static Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 1:
//
//                    break;
//                case 2:
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    };
}
