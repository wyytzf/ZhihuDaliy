package wyy.com.learningproject.NetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wyy.com.learningproject.Bean.News;

/**
 * Created by weiyuyang on 16-6-10.
 */
public class NewsParse implements ResponseParse<News> {

    @Override
    public List<News> Parse(String str) {
        List<News> list = new ArrayList<>();
        JSONObject jb ;
        JSONArray ja;
        try {
            jb = new JSONObject(str);
            ja = jb.getJSONArray("stories");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject j = (JSONObject) ja.get(i);
                News news = new News();
                news.setId(j.getString("id"));
                news.setTitle(j.getString("title"));
                if (j.has("images"))
                    news.setImage((String) j.getJSONArray("images").get(0));
                list.add(news);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
