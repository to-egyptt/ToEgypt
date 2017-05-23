package AdminModels;

/**
 * Created by Abu-elhassan on 5/10/2017.
 */
public class model_package {


    String pckgname;
    String placesinpckg;
    String start;
    String end;
    String time;
    String price;

    public model_package(String pckgname, String placesinpckg, String start, String end, String time, String price) {
        this.pckgname = pckgname;
        this.placesinpckg = placesinpckg;
        this.start = start;
        this.end = end;
        this.time = time;
        this.price = price;
    }

    public String getPckgname() {
        return pckgname;
    }

    public void setPckgname(String pckgname) {
        this.pckgname = pckgname;
    }

    public String getPlacesinpckg() {
        return placesinpckg;
    }

    public void setPlacesinpckg(String placesinpckg) {
        this.placesinpckg = placesinpckg;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
