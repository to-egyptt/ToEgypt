package models;

/**
 * Created by m07amed on 14/06/2017.
 */

public class place {
    private int id;
    private String name;
    private String description;
    private int gvernate_id;
    private int category_id;
    private double priceTovisit;
    private category category;
    private governate governate;

    public category getCategory() {
        return category;
    }

    public void setCategory(category category) {
        this.category = category;
    }

    public governate getGovernate() {
        return governate;
    }

    public void setGovernate(governate governate) {
        this.governate = governate;
    }

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

    public int getGvernate_id() {
        return gvernate_id;
    }

    public void setGvernate_id(int gvernate_id) {
        this.gvernate_id = gvernate_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public double getPriceTovisit() {
        return priceTovisit;
    }

    public void setPriceTovisit(double priceTovisit) {
        this.priceTovisit = priceTovisit;
    }


}
