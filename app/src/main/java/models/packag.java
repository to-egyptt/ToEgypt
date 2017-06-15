package models;

import java.util.Date;

/**
 * Created by m07amed on 14/06/2017.
 */

public class packag {
    private int id;
    private String name;
    private String description;
    private int guide_id;
    private int numberOfReserver;
    private int maxNumOfRserver;
    private Date start_date;
    private Date end_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGuide_id() {
        return guide_id;
    }

    public void setGuide_id(int guide_id) {
        this.guide_id = guide_id;
    }

    public int getNumberOfReserver() {
        return numberOfReserver;
    }

    public void setNumberOfReserver(int numberOfReserver) {
        this.numberOfReserver = numberOfReserver;
    }

    public int getMaxNumOfRserver() {
        return maxNumOfRserver;
    }

    public void setMaxNumOfRserver(int maxNumOfRserver) {
        this.maxNumOfRserver = maxNumOfRserver;
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
}
