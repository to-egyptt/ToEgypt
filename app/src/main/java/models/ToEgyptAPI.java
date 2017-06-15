package models;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by root on 5/17/17.
 */

public interface ToEgyptAPI {

    @GET("/odata/users?$filter=type_id%20eq%202")
    Call<ResultUserSet> getUsers();

    @GET("/odata/users({id})/country")
    Call<country> getCountry(@Path("id") int id);

    @DELETE("/odata/users({id})")
    Call<Void> deleteUser(@Path("id") int userId);
//?$filter=username%20eq%20%27medo%20dola%27%20and%20password%20eq%20%27321%27
//    @GET("/odata/users")
//    Call<ResultUserSet> login();
@GET
Call<ResultUserSet> login(@Url String url);

    //@Path(value = "username",encoded = true) String username,@Path(value = "password",encoded = true) String password
///odata/users?$filter=username%20eq%20%27kako%20dola%27%20and%20password%20eq%20%27321%27
    //void deleteUser(@Path("id") int userId, Callback<Response> callback);

}

