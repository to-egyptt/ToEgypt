package models;

import java.sql.Time;

/**
 * Created by m07amed on 14/06/2017.
 */

public class article {
    private int id;
    private String title;
    private String content;
    private int numberOfViews;
    private int place_id;
    private Time publised_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }

    public Time getPublised_time() {
        return publised_time;
    }

    public void setPublised_time(Time publised_time) {
        this.publised_time = publised_time;
    }
}
