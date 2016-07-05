package wyy.com.learningproject.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiyuyang on 16-6-15.
 */
public class NewsDetail {
    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private List<String> js = new ArrayList<String>();
    private List<String> css = new ArrayList<String>();

    public String getBody() {
        return body;
    }

    public String getImage_source() {
        return image_source;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getShare_url() {
        return share_url;
    }

    public List<String> getJs() {
        return js;
    }

    public List<String> getCss() {
        return css;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }
}
