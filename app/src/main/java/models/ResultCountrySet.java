package models;

import java.util.List;

/**
 * Created by root on 5/17/17.
 */

public class ResultCountrySet {
    private List<country> value;

    public void setCountries(List<country> countries) {
        this.value = countries;
    }

    public List<country> getCountries() {
        return value;
    }
}
