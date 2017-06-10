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
    private int reached_total;
    private int reached_unique;
    private int likesCount;
    private int lovesCount;
    private int hahaCount;
    private int wowCount;
    private int sadCount;
    private int angryCount;

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

    public int getReached_total() {
        return reached_total;
    }

    public void setReached_total(int reached_total) {
        this.reached_total = reached_total;
    }

    public int getReached_unique() {
        return reached_unique;
    }

    public void setReached_unique(int reached_unique) {
        this.reached_unique = reached_unique;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getLovesCount() {
        return lovesCount;
    }

    public void setLovesCount(int lovesCount) {
        this.lovesCount = lovesCount;
    }

    public int getHahaCount() {
        return hahaCount;
    }

    public void setHahaCount(int hahaCount) {
        this.hahaCount = hahaCount;
    }

    public int getWowCount() {
        return wowCount;
    }

    public void setWowCount(int wowCount) {
        this.wowCount = wowCount;
    }

    public int getSadCount() {
        return sadCount;
    }

    public void setSadCount(int sadCount) {
        this.sadCount = sadCount;
    }

    public int getAngryCount() {
        return angryCount;
    }

    public void setAngryCount(int angryCount) {
        this.angryCount = angryCount;
    }
}
