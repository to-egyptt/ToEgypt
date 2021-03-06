package models;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by root on 5/17/17.
 */

public interface ToEgyptAPI {

    @GET("/odata/users?$expand=country&$filter=type_id%20eq%202")
    Call<ResultUserSet> getUsers();

    @GET("/odata/users({id})?$expand=country")
    Call<user> getuser(@Path("id") int id);

    @DELETE("/odata/users({id})")
    Call<Void> deleteUser(@Path("id") int userId);

    @PUT("/odata/users({id})")
    Call<Void> updateUser(@Path("id") int userId, @Body user user);

    @GET
    Call<ResultUserSet> login(@Url String url);

    @GET("/odata/users?$expand=country&$filter=type_id%20eq%203")
    Call<ResultUserSet> getGuide();

    @GET("/odata/countries")
    Call<ResultCountrySet> getCountries();

    @GET("/odata/packages?$expand=package_detailes($expand=place),user")
    Call<ResultpakageSet> getPackages();

    @GET("/odata/packages({id})?$expand=package_detailes($expand=place),user")
    Call<packag> getPackage(@Path("id") int id);

    @DELETE("/odata/packages({id})")
    Call<Void> deletePackage(@Path("id") int id);

    @GET("/odata/places?$expand=category,governate")
    Call<ResultPlaceSet> getPlaces();

    @POST("/odata/users")
    Call<user> addUser(@Body user user);

    @GET("/odata/users/$count/?$filter=type_id%20eq%202")
    Call<Integer> getUsersCount();

    @GET("/odata/users/$count/?$filter=type_id%20eq%203")
    Call<Integer> getGuideCount();

    @GET("/odata/categories")
    Call<ResultcategorySet> getCategory();

    @GET("/odata/governates")
    Call<ArrayList<governate>> getGovernate();

    @POST("/odata/reserved_package")
    Call<reserved_package> reserve(@Body reserved_package reservedPackage);

    @GET("/odata/reserved_package?$filter=tourist_id eq 17&$expand=package")
    Call<ResultReservedPackageSet> getReserrvedPackage();
    //@GET
    //Call<ResultReservedPackageSet>getReserrvedPackage(@Url String url);
}

