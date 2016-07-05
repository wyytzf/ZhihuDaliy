package wyy.com.learningproject.NetUtils;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.framed.FrameReader;

/**
 * Created by weiyuyang on 16-6-14.
 */
public class OkHttp {
    private static volatile OkHttp mInstance =null;
    private OkHttpClient mOkhttpClient;
    private Handler mDeliver;

    private OkHttp(){
        mDeliver = new Handler(Looper.getMainLooper());
        mOkhttpClient = new OkHttpClient();
    }


    public static OkHttp getInstance(){
        OkHttp inst = mInstance;
        if(inst ==null){
            synchronized (OkHttp.class){
                if (inst ==null){
                    inst = new OkHttp();
                    mInstance = inst;
                }
            }
        }
        return inst;
    }


    private void _get(String url,ResultCallBack resultCallBack){

        Request request = new Request.Builder().url(url).build();
        deliverResult(request,resultCallBack);
    }


    private void deliverResult(Request request, final ResultCallBack resultCallBack){
        Call call = mOkhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverError(resultCallBack,e.toString(),e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                deliverResponse(resultCallBack,response.body().string());
            }
        });
    }



    private void deliverError(final ResultCallBack resultCallBack, final String str, final Exception e){
        mDeliver.post(new Runnable() {
            @Override
            public void run() {
                if (resultCallBack!=null){
                    resultCallBack.onError(str,e);
                }
            }
        });
    }

    private void deliverResponse(final ResultCallBack resultCallBack, final String str){
        mDeliver.post(new Runnable() {
            @Override
            public void run() {
                if (resultCallBack!=null){
                    resultCallBack.onResponse(str);
                }
            }
        });
    }


    public static void get(String url,ResultCallBack resultCallBack){
        getInstance()._get(url,resultCallBack);
    }

    public interface ResultCallBack{
        void onError(String str,Exception e);
        void onResponse(String str);
    }
}
