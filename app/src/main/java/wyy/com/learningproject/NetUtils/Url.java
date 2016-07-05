package wyy.com.learningproject.NetUtils;

/**
 * Created by weiyuyang on 16-5-28.
 */
public class Url {

    private static String HTTP = "http://";
    private static String HOST = "news-at.zhihu.com/";
    private static String API_LEVEL = "api/4/";

    public static String getNews(String date){
        return HTTP+HOST+API_LEVEL+"news/before/"+date;
    }

    public static String getNewsDetail(String id){
        return HTTP+HOST+API_LEVEL+"news/"+id;
    }

    public static String getThemes(){
        return HTTP+HOST+API_LEVEL+"themes";
    }

    public static String getThemesDetail(String id){
        return HTTP+HOST+API_LEVEL+"theme/"+id;
    }

    public static String getHot(){
        return HTTP+HOST+API_LEVEL+"news/hot";
    }
    public static String getHotDetail(String id){
        return getNewsDetail(id);
    }
    public static String getSection(){
        return HTTP+HOST+API_LEVEL+"sections";
    }
    public static String getSectionDetail(String id){
        return HTTP+HOST+API_LEVEL+"section/"+id;
    }
}
