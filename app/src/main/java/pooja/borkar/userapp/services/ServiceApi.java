package pooja.borkar.userapp.services;

import pooja.borkar.userapp.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApi {

    @GET("regiform.php")
    Call<User> doRegisteration(
            @Query("name") String name,
            @Query("email") String email,
            @Query("gender") String gender,
            @Query("dob") String dob,
            @Query("password") String password,
            @Query("phone") String phone
             );

    @GET("login.php")
    Call<User> doLogin(
            @Query("email") String email,
            @Query("password") String password
    );
}
