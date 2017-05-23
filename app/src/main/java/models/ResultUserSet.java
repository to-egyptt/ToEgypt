package models;

import java.util.List;

/**
 * Created by root on 5/17/17.
 */

public class ResultUserSet {

    private List<user> value;

    public void setUsers(List<user> users) {
        this.value = users;
    }

    public List<user> getUsers() {
        return value;
    }
}
