package models;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by m07amed on 18/05/2017.
 */

public class Injector {
    public static Retrofit ProvideRetrofit(String baseURL) {
        return new Retrofit.Builder().
                baseUrl(baseURL).
                addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ToEgyptAPI toEgyptAPI() {

        return ProvideRetrofit("http://2egyptwebservice.somee.com").create(ToEgyptAPI.class);
    }
}
