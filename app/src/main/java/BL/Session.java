package BL;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by m07amed on 15/06/2017.
 */

public class Session {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    String MyPREFERENCES = "MyPrefs";

    public Session(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void setEditor(int id, int type_id, boolean logged) {
        editor.putBoolean("isLogged", logged);
        editor.putInt("id", id);
        editor.putInt("typeId", type_id);
        editor.commit();
    }

    public void setLoggedIn(boolean logged) {
        editor.putBoolean("isLogged", logged);
        editor.commit();
    }

    public boolean loggedIn() {
        return sharedPreferences.getBoolean("isLogged", false);
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    public int getUserId() {
        return sharedPreferences.getInt("id", 0);
    }

    public int getUserType() {
        return sharedPreferences.getInt("typeId", 0);
    }
}
