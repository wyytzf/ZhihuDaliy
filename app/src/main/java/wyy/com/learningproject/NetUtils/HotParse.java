package wyy.com.learningproject.NetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wyy.com.learningproject.Bean.News;

/**
 * Created by weiyuyang on 16-6-30.
 */
public class HotParse implements ResponseParse<News> {
    @Override
    public List<News> Parse(String str) {
        List<News> list = new ArrayList<>();
        JSONObject jb ;
        JSONArray ja;
        try {
            jb = new JSONObject(str);
            ja = jb.getJSONArray("recent");
            for (int i =0;i<ja.length();i++){
                JSONObject j = ja.getJSONObject(i);
                News news = new News();
                news.setTitle(j.getString("title"));
                news.setImage(j.getString("thumbnail"));
                news.setId(j.getString("news_id"));
                list.add(news);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
