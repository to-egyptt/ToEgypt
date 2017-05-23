package AdminModels;

/**
 * Created by Abu-elhassan on 5/10/2017.
 */
public class model_user {


    Integer image;
    String name;
    String country;

    public model_user(Integer image, String name, String country) {
        this.image = image;
        this.name = name;
        this.country = country;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
