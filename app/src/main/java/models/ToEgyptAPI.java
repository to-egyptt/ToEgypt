package models;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by root on 5/17/17.
 */

public interface ToEgyptAPI {

    @GET("/odata/users?$filter=type_id%20eq%202")
    Call<ResultUserSet> getUsers();

    @GET("/odata/users({id})")
    Call<user> getuser(@Path("id") int id);

    @DELETE("/odata/users({id})")
    Call<Void> deleteUser(@Path("id") int userId);

    @PUT("/odata/users({id})")
    Call<Void> updateUser(@Path("id") int userId, @Body user user);

    @GET
    Call<ResultUserSet> login(@Url String url);

    @GET("/odata/users?$filter=type_id%20eq%203")
    Call<ResultUserSet> getGuide();

    @GET("/odata/users({id})/country")
    Call<country> getCountry(@Path("id") int id);
}

