package wyy.com.learningproject.NetUtils;

import java.util.List;

import wyy.com.learningproject.Bean.News;

/**
 * Created by weiyuyang on 16-5-28.
 */
public interface ResponseParse<T> {
    List<T> Parse(String str);

}
