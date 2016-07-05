package wyy.com.learningproject.NetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wyy.com.learningproject.Bean.News;
import wyy.com.learningproject.Bean.Theme;

/**
 * Created by weiyuyang on 16-7-1.
 */
public class ThemesParse implements ResponseParse<Theme> {
    @Override
    public List<Theme> Parse(String str) {
        List<Theme> list = new ArrayList<>();
        JSONObject jb ;
        JSONArray ja;
        try {
            jb = new JSONObject(str);
            ja = jb.getJSONArray("others");
            for (int i =0;i<ja.length();i++){
                Theme theme = new Theme();
                JSONObject j = ja.getJSONObject(i);
                theme.setId(j.getString("id"));
                theme.setDescription(j.getString("description"));
                theme.setName(j.getString("name"));
                theme.setThumbnail(j.getString("thumbnail"));
                list.add(theme);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
