package wyy.com.learningproject.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import wyy.com.learningproject.Adapter.NewsAdapter;
import wyy.com.learningproject.Adapter.OnItemClickListener;
import wyy.com.learningproject.Bean.News;
import wyy.com.learningproject.NetUtils.NewsParse;
import wyy.com.learningproject.NetUtils.OkHttp;
import wyy.com.learningproject.NetUtils.Url;
import wyy.com.learningproject.R;
import wyy.com.learningproject.Utils.DateUtils;

/**
 * Created by weiyuyang on 16-6-1.
 */
public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String URL = "url";


    // TODO: Rename and change types of parameters
    private String url;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private View view;
    private Activity activity;
    private NewsAdapter mAdapter;
    private List<News> li;



    public static NewsFragment newInstance(String url) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,null);
        this.view = view;
        activity = getActivity();
        initViews();
        Request();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(URL);
        }
    }
    @Override
    public void onRefresh() {
        Request();
    }


    public void setUrl(String url){
        this.url = url;
        Log.i("wyy",this.url);

        Request();
    }

    private void Request() {

        ///okhttp的回调不是在主线程中！！
//        url = DateUtils.getCurrentDate("yyyyMMdd");

        OkHttp.get(url, new OkHttp.ResultCallBack() {

            @Override
            public void onError(String str, Exception e) {
                Toast.makeText(activity,str,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String str) {
                NewsParse newsParse = new NewsParse();
                li = newsParse.Parse(str);
                if(mAdapter ==null)
                    Log.e("wyy","mAdapter is null");
                if(li!=null)
                    mAdapter.setDatas(li);
                if(mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void initViews() {
        Log.i("wyy","initViews");
        mAdapter = new NewsAdapter(activity);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(activity,"position:"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity,DetailActivity.class);
                intent.putExtra("id",li.get(position).getId());
                activity.startActivity(intent);
            }
        });


        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperrefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }


}
