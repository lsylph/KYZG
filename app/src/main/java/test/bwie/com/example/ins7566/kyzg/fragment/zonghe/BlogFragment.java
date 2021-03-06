package test.bwie.com.example.ins7566.kyzg.fragment.zonghe;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import test.bwie.com.example.ins7566.kyzg.R;
import test.bwie.com.example.ins7566.kyzg.adapter.BlogAdapter;
import test.bwie.com.example.ins7566.kyzg.base.BaseFragment;
import test.bwie.com.example.ins7566.kyzg.bean.BloglistBean;
import test.bwie.com.example.ins7566.kyzg.http.INewsModel;
import test.bwie.com.example.ins7566.kyzg.http.NewsModelImpl;
import test.bwie.com.example.ins7566.kyzg.http.callback.MyCallback;

/**
 * Created by INS7566 on 2017/5/10.
 */

public class BlogFragment extends BaseFragment {
    @BindView(R.id.lunbo_PullRecycler)
    PullToRefreshRecyclerView lunboPullRecycler;
    Unbinder unbinder;
    private BlogAdapter adapter;
    private INewsModel modle;
    private List<BloglistBean.BlogBean> mList;
    private int Index = 0;

    @Override
    protected int layoutId() {
        return R.layout.lunbotu_recycler;
    }

    @Override
    protected void initView(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        lunboPullRecycler.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lunboPullRecycler.setLayoutManager(linearLayoutManager);
        lunboPullRecycler.setPullRefreshEnabled(true);
        lunboPullRecycler.setLoadingMoreEnabled(true);
        lunboPullRecycler.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
            lunboPullRecycler.post(new Runnable() {
                @Override
                public void run() {
                    lunboPullRecycler.setRefreshComplete();
                    mList.clear();
                  loadData();
                }
            });
            }

            @Override
            public void onLoadMore() {
                    lunboPullRecycler.post(new Runnable() {
                        @Override
                        public void run() {
                      lunboPullRecycler.setLoadMoreComplete();
                            Index++;
                            loadData();
                        }
                    });
            }
        });

    }

    @Override
    protected void initData() {
        modle=new NewsModelImpl();
        mList=new ArrayList<>();
        adapter=new BlogAdapter(getActivity(),mList);
        lunboPullRecycler.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {
        modle.Blog("latest", String.valueOf(Index), "10", new MyCallback() {
            @Override
            public void onSuccess(String response) {
                XStream xStream=new XStream();
                xStream.alias("oschina",BloglistBean.class);
                xStream.alias("blog",BloglistBean.BlogBean.class);
                BloglistBean bloglistBean= (BloglistBean) xStream.fromXML(response);
                mList.addAll(bloglistBean.getBlogs());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void onHiddn() {
        unTitleBar();

    }

    @Override
    protected void show() {
        unTitleBar();
    }

    @Override
    protected void unTitleBar() {

    }

    @Override
    public void setParams(Bundle bundle) {

    }
}
