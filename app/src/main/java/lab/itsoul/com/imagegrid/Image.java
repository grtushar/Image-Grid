package lab.itsoul.com.imagegrid;


import java.util.Date;

public class Image {
    private String photo;
    private String author;
    private Date date;

    public Image(String photo, String author, Date date) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
