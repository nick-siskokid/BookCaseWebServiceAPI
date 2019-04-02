import org.json.JSONException;
import org.json.JSONObject;

public class Book {
    int id;
    String title;
    String author;
    int published;
    String coverURL;
    //constructor
    public Book(int id, String title, String author, int published, String coverURL) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.published = published;
        this.coverURL = coverURL;
    }

    public Book(JSONObject jsonbook) throws JSONException {
        this(jsonbook.getInt("id"), jsonbook.getString("title"), jsonbook.getString("author"), jsonbook.getInt("published"), jsonbook.getString("coverURL"));
    }
    //getter and setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //getter and setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //getter and setter for author

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    //getter and setter for published

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    //getter and setter for URL

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }
}
