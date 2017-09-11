package Models;

/**
 * Created by SHAHED-PC on 8/8/2017.
 */
public class Log {
    private int id;
    private String dateTime;
    private String description;

    public Log(String dateTime, String description) {
        this.dateTime = dateTime;
        this.description = description;
    }

    public Log(int id, String dateTime, String description) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
    }

    public int getId() {
        return id;
    }


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
