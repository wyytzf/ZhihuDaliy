package wyy.com.learningproject.UI;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import okhttp3.Request;
import wyy.com.learningproject.Adapter.NewsAdapter;
import wyy.com.learningproject.Adapter.OnItemClickListener;
import wyy.com.learningproject.Bean.News;
import wyy.com.learningproject.Bean.Theme;
import wyy.com.learningproject.NetUtils.NewsParse;
import wyy.com.learningproject.NetUtils.OkHttp;
import wyy.com.learningproject.NetUtils.Url;
import wyy.com.learningproject.R;

public class ThemeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private String id;
    private List<News> li;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        id = getIntent().getExtras().getString("id");
        initViews();
        Request();
    }

    private void initViews() {
        mAdapter = new NewsAdapter(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ThemeActivity.this,DetailActivity.class);
                intent.putExtra("id",li.get(position).getId());
                startActivity(intent);
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        Request();
    }


    private void Request(){
        OkHttp.get(Url.getThemesDetail(id), new OkHttp.ResultCallBack() {
            @Override
            public void onError(String str, Exception e) {

            }

            @Override
            public void onResponse(String str) {
                Log.i("wyy",str);
                NewsParse newsparse = new NewsParse();
                li=newsparse.Parse(str);
                mAdapter.setDatas(li);
                if(mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
}
