package models;

/**
 * Created by root on 5/17/17.
 */

public class country {
    private int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String iso;

    public String getIso() {
        return this.iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String nicename;

    public String getNicename() {
        return this.nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

    private String iso3;

    public String getIso3() {
        return this.iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    private Integer numcode;

    public Integer getNumcode() {
        return this.numcode;
    }

    public void setNumcode(Integer numcode) {
        this.numcode = numcode;
    }

    private int phonecode;

    public int getPhonecode() {
        return this.phonecode;
    }

    public void setPhonecode(int phonecode) {
        this.phonecode = phonecode;
    }
}
