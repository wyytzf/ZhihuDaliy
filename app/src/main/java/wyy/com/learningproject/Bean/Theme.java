package wyy.com.learningproject.Bean;

/**
 * Created by weiyuyang on 16-5-29.
 */
public class Theme {
    private String thumbnail;
    private String description;
    private String id;
    private String name;

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {

        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
