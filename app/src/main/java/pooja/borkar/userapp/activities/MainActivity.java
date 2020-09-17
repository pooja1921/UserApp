package pooja.borkar.userapp.activities;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;


import pooja.borkar.userapp.R;
import pooja.borkar.userapp.canstants.Constants;
import pooja.borkar.userapp.extras.AppPreferences;
import pooja.borkar.userapp.fragments.ProfileFragment;
import pooja.borkar.userapp.fragments.RegisterFragment;
import pooja.borkar.userapp.fragments.loginFragment;
import pooja.borkar.userapp.services.MyInterface;
import pooja.borkar.userapp.services.RetrofitClient;
import pooja.borkar.userapp.services.ServiceApi;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements MyInterface {
    public static AppPreferences appPreferences;
    public static ServiceApi serviceApi;
    FrameLayout container_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container_fragment=findViewById(R.id.fragment_container);
        appPreferences=new AppPreferences(this);
        serviceApi = RetrofitClient.getApiClient(Constants.baseUrl.Base_URL).create(ServiceApi.class);
        if(container_fragment!=null)
        {
            if(savedInstanceState!=null)
            {
                return;
            }
            if(appPreferences.getLoginStatus())
            {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new loginFragment()).commit();

            }
            else{
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new loginFragment()).commit();

            }
        }




    }

    @Override
    public void register() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new RegisterFragment())
                .addToBackStack(null)
                .commit();


    }

    @Override
    public void login(String name, String email, String created_at) {
        appPreferences.setDiaplayEmail(email);
        appPreferences.setDiaplayName(name);
        appPreferences.setDiaplayDate(created_at);



        Intent intent=new Intent(this, Home.class);
        startActivity(intent);


        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment())
               // .addToBackStack(null)
                //.commit();

    }

    @Override
    public void logout() {
        appPreferences.setLoginStatus(false);
        appPreferences.setDiaplayName("Name");
        appPreferences.setDiaplayEmail("Email");

        appPreferences.setDiaplayDate("Date");

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new loginFragment())
                .addToBackStack(null)
                .commit();

    }
}