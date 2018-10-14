package lab.itsoul.com.imagegrid;

public class Image {
    private String photo;
    private String author;
    private String date;

    public Image(String photo, String author, String date) {
        this.photo = photo;
        this.author = author;
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
