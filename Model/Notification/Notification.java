package Model.Notification;
import java.util.Date;


public class Notification {

    int id;
    String subject;
    Date date;

    public Notification() {
        this.id = 0;
        this.subject = "";
        this.date = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
