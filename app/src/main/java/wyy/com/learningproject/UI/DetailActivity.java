package wyy.com.learningproject.UI;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wyy.com.learningproject.Bean.News;
import wyy.com.learningproject.Bean.NewsDetail;
import wyy.com.learningproject.NetUtils.NewsDetailParse;
import wyy.com.learningproject.NetUtils.NewsParse;
import wyy.com.learningproject.NetUtils.OkHttp;
import wyy.com.learningproject.NetUtils.Url;
import wyy.com.learningproject.R;
import wyy.com.learningproject.Utils.DateUtils;

/**
 * Created by weiyuyang on 16-6-15.
 */
public class DetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private WebView mWebView;
    private String id;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        id = getIntent().getExtras().getString("id");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        Request();
    }

    private void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.web_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);


        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
                return false;
            }

        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress ==100){
                    if (swipeRefreshLayout.isRefreshing())
                        swipeRefreshLayout.setRefreshing(false);
                }
            }

        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (mWebView.canGoBack()){
                mWebView.goBack();
                if (!mWebView.canGoBack()) {
                    mWebView.loadDataWithBaseURL(null,body,"text/html","UTF-8",null);
                }
                return true;
            }else{
                finish();
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    private void Request() {
        if (mWebView.canGoBack())
            mWebView.reload();
        else {
            OkHttp.get(Url.getNewsDetail(id), new OkHttp.ResultCallBack() {
                @Override
                public void onError(String str, Exception e) {
                    Toast.makeText(DetailActivity.this, str, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String str) {

                    NewsDetailParse ndp = new NewsDetailParse();
                    List<NewsDetail> parse = ndp.Parse(str);
                    NewsDetail newsDetail = parse.get(0);
                    body = newsDetail.getBody().replace("<div class=\"img-place-holder\"></div>", "");
                    body += "<link rel=\"stylesheet\" href=\"" + newsDetail.getCss().get(0) + "\" type=\"text/css\"/>";
                    mWebView.loadDataWithBaseURL(null, body, "text/html", "UTF-8", null);
//                mWebView.loadData(newsDetail.getCss().get(0),"text/css","UTF-8");

                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        }
    }

    @Override
    public void onRefresh() {
        Request();
    }
}
