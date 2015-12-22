package activitydialogtest.pczhu.com.customtestxutils.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import activitydialogtest.pczhu.com.customtestxutils.R;
import activitydialogtest.pczhu.com.customtestxutils.adapter.animationadapter.CardsAnimationAdapter;
import activitydialogtest.pczhu.com.customtestxutils.adapter.MyBaseAdapter;
import activitydialogtest.pczhu.com.customtestxutils.domain.FailBean;
import activitydialogtest.pczhu.com.customtestxutils.utils.ToastShow;
import activitydialogtest.pczhu.com.customtestxutils.view.gridview.LoadingFooter;
import activitydialogtest.pczhu.com.customtestxutils.view.gridview.OnLoadNextListener;
import activitydialogtest.pczhu.com.customtestxutils.view.gridview.PageStaggeredGridView;

/**
 * 名称：CustomTestxUtils
 * 作用：
 * 描述：
 * 作者：pczhu
 * 创建时间： 15/11/24 下午12:06
 * 版本：V1.0
 * 修改历史：
 */
public abstract class BaseActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OnLoadNextListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private PageStaggeredGridView pageStaggeredGridView;
    protected int page = 1;
    protected MyBaseAdapter mainAdapter;
    private OnActionListener onActionListener;
    protected boolean isRefreshFromTop;
    protected TextView tv_error;
    protected Class<?> clz;
    protected Object obj;
    protected ArrayList userlist;
    protected RequestParams requestParams;

    //private Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onLoadNext() {

        page++;
        loadData(page);
    }
    @Override
    public void onRefresh() {
        //pageStaggeredGridView.setState(LoadingFooter.State.Idle);
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

    public interface OnActionListener{

    }
//    public void start(OnActionListener onActionListener){
//        this.onActionListener = onActionListener;
//    }
    public void openListener( SwipeRefreshLayout swipeRefreshLayout,
                              PageStaggeredGridView pageStaggeredGridView,
                              MyBaseAdapter adapter,
                              Class<?> clz,

                              RequestParams requestParams,
                              OnActionListener onActionListener){
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.pageStaggeredGridView = pageStaggeredGridView;
        this.mainAdapter = adapter;
        this.clz = clz;
        this.onActionListener = onActionListener;
        this.requestParams = requestParams;

        try {
            obj = clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        swipeRefreshLayout.setOnRefreshListener(this);
        pageStaggeredGridView.setLoadNextListener(this);
        AnimationAdapter animationAdapter = new CardsAnimationAdapter(mainAdapter);//添加卡片式动画
        animationAdapter.setAbsListView(pageStaggeredGridView);
        pageStaggeredGridView.setAdapter(animationAdapter);

        loadFirst();
    }
    protected abstract View getRootView();

    private Callback.Cancelable cancelable;
    private void sendToHttp(final int page) {
        RequestParams requestParams = getRequestParams(page);
        cancelable = x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(final String s) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("JSON数据","请求");
                            getData(s);
                        }
                    });
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
                Log.i("缓存数据","进入缓存");
                getData(s);
                return true;
            }
        });
    }
    private void getData(String s){

        Gson gson = new Gson();
        FailBean failBean = null;

        try {
            obj = gson.fromJson(s, clz);
        }catch (Exception e){
            failBean =gson.fromJson(s, FailBean.class);
        }

        if(failBean == null && obj!=null){
            if(isRefreshFromTop){
                // userlist = project.getData();
                userlist = getObjectList(obj);
            }else{
                //userlist.addAll(project.getData());
                userlist.addAll(getObjectList(obj));
            }
            mainAdapter.notifyDataSetChanged(userlist);
            if(isRefreshFromTop)
                swipeRefreshLayout.post(
                        new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                                Log.d("UI", "关闭");
                                if (pageStaggeredGridView.getState() == LoadingFooter.State.TheEnd){
                                    pageStaggeredGridView.setState(LoadingFooter.State.Idle);
                                }}
                        });
            else
                pageStaggeredGridView.setState(LoadingFooter.State.Idle);

            showError(0,false);//如果已经有错误布局 。删除

        }else{
            if(failBean.getStatus().equals("-103")){
                if(isRefreshFromTop){
                    swipeRefreshLayout.setRefreshing(false);
                }else {
                    pageStaggeredGridView.setState(LoadingFooter.State.Idle);
                }
                controlWhichShow(1);
            }else{
                if(isRefreshFromTop){
                    swipeRefreshLayout.setRefreshing(false);
                }else {
                    pageStaggeredGridView.setState(LoadingFooter.State.ERROR);
                }
                controlWhichShow(2);
            }
        }
    }
    @NonNull
    private RequestParams getRequestParams(int page) {
        //RequestParams requestParams = new RequestParams("http://app.renrentou.com/star/GetInvestor");
        requestParams.addBodyParameter("page",page+"");
        requestParams.addBodyParameter("page_num", "5");
        return requestParams;
    }

    /**
     * 添加错误布局
     */
    public void addErrorView(Activity activity){

        View error_view = View.inflate(this, R.layout.error_layout,null);

        tv_error = (TextView) error_view.findViewById(R.id.tv_error);
        ((ViewGroup)getRootView())
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
                ToastShow.showTextToast(this, "没有找到相关数据");
            }else if(statue == 2){
                ToastShow.showTextToast(this, "网络请求出现问题");
            }
            flag = false;
        }
        showError(statue, flag);

    }
    /**
     * 显示文字
     * @param statue
     * @param flag
     *              true 显示错误布局 false 不显示错误布局
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
            ((ViewGroup)getRootView())
                    .getChildAt(((ViewGroup) getRootView()).getChildCount() - 1)
                    .setVisibility(View.VISIBLE);

        }else{
            ((ViewGroup)getRootView())
                    .getChildAt(((ViewGroup) getRootView()).getChildCount() - 1)
                    .setVisibility(View.GONE);

        }
    }

    private void refreshView(LoadingFooter.State error) {
        if (!isRefreshFromTop)
            pageStaggeredGridView.setState(error);
        else {
            swipeRefreshLayout.setRefreshing(false);
            if (pageStaggeredGridView.getState() == LoadingFooter.State.TheEnd){
                pageStaggeredGridView.setState(LoadingFooter.State.Idle);
            }
        }
    }

    /**
     * 拿到集合类型
     * @param obj
     * @return
     */
    public abstract ArrayList getObjectList(Object obj);

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // activity = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(cancelable!=null)
            cancelable.cancel();
    }
}
