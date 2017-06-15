package models;

import java.util.Date;

/**
 * Created by m07amed on 14/06/2017.
 */

public class private_travel {
    private int id;
    private int tourist_id;
    private int guide_id;
    private int place_id;
    private Date start_date;
    private Date end_date;
    private double cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTourist_id() {
        return tourist_id;
    }

    public void setTourist_id(int tourist_id) {
        this.tourist_id = tourist_id;
    }

    public int getGuide_id() {
        return guide_id;
    }

    public void setGuide_id(int guide_id) {
        this.guide_id = guide_id;
    }

    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
