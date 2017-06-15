package models;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by m07amed on 28/05/2017.
 */

public class Singleton {
    private static Retrofit retrofit;

    private Singleton() {

    }

    public static synchronized Retrofit getRetrofit() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://2egyptwebservice.somee.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
