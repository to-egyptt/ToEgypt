package AdminModels;

/**
 * Created by Abu-elhassan on 5/11/2017.
 */

public class model_place {
    Integer image;
    String placeName;
    String placeDescription;
    String placeGovernate;
    String placeCategory;

    public model_place(Integer image, String placeName, String placeDescription, String placeGovernate, String placeCategory) {

        this.image = image;
        this.placeName = placeName;
        this.placeDescription = placeDescription;
        this.placeGovernate = placeGovernate;
        this.placeCategory = placeCategory;
    }

    public String getPlaceGovernate() {
        return placeGovernate;
    }

    public void setPlaceGovernate(String placeGovernate) {
        this.placeGovernate = placeGovernate;
    }

    public String getPlaceCategory() {
        return placeCategory;
    }

    public void setPlaceCategory(String placeCategory) {
        this.placeCategory = placeCategory;
    }


    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }
}
