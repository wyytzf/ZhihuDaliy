package wyy.com.learningproject.NetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wyy.com.learningproject.Bean.News;
import wyy.com.learningproject.Bean.NewsDetail;

/**
 * Created by weiyuyang on 16-6-15.
 */
public class NewsDetailParse implements ResponseParse<NewsDetail> {


    @Override
    public List<NewsDetail> Parse(String str) {
        List<NewsDetail> list = new ArrayList<NewsDetail>();
        try {
            JSONObject mJSONObject = new JSONObject(str);
            NewsDetail mNewsDetail = new NewsDetail();
            mNewsDetail.setBody(mJSONObject.getString("body"));
            if (mJSONObject.has("image"))
                mNewsDetail.setImage(mJSONObject.getString("image"));
            mNewsDetail.setShare_url(mJSONObject.getString("share_url"));
            mNewsDetail.setTitle(mJSONObject.getString("title"));
            JSONArray ja = mJSONObject.getJSONArray("js");
            for (int i = 0;i< ja.length();i++){
                String string = ja.getString(i);
                mNewsDetail.getJs().add(string);
            }
            ja = mJSONObject.getJSONArray("css");
            for (int i = 0 ; i<ja.length();i++){
                String string = ja.getString(i);
                mNewsDetail.getCss().add(string);
            }
            list.add(mNewsDetail);
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
