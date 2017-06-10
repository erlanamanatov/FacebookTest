package erkprog.com.fbstats;

/**
 * Created by erlan on 10.06.2017.
 */

public class Post {
    private String name;
    private String message;
    private int sharesCount;
    private String id;
    private String url;

    public Post(){

    }

    public Post(String name, String message, int sharesCount, String id, String url) {
        this.name = name;
        this.message = message;
        this.sharesCount = sharesCount;
        this.id = id;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSharesCount() {
        return sharesCount;
    }

    public void setSharesCount(int sharesCount) {
        this.sharesCount = sharesCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
