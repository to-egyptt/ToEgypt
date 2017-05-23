package AdminModels;

/**
 * Created by Abu-elhassan on 5/10/2017.
 */
public class model_guide {


    Integer image;
    String name;
    String email;

    public model_guide(Integer image, String name, String email) {
        this.image = image;
        this.name = name;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
