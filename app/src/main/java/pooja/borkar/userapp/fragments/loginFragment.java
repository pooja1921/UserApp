package pooja.borkar.userapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pooja.borkar.userapp.R;
import pooja.borkar.userapp.activities.MainActivity;
import pooja.borkar.userapp.model.User;
import pooja.borkar.userapp.services.MyInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginFragment extends Fragment {
    MyInterface myInterface_login;
Button loginbtn;
EditText emailInput,passwordInput;
TextView registerTV;
public loginFragment()
{

}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
   emailInput=view.findViewById(R.id.emailInput);
   passwordInput=view.findViewById(R.id.passwordInput);
   loginbtn=view.findViewById(R.id.loginBtn);
        registerTV=view.findViewById(R.id.registerTV);
   loginbtn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           //Toast.makeText(getActivity(), "Login", Toast.LENGTH_SHORT).show();
       loginUser();
       }
   });
   registerTV.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Toast.makeText(getActivity(), "Register", Toast.LENGTH_SHORT).show();
           myInterface_login.register();
       }
   });
        return view;


    }

    private void loginUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            MainActivity.appPreferences.showToast("Your email is invalid");
        } else if (TextUtils.isEmpty(password)) {
            MainActivity.appPreferences.showToast("Enter your pass");
        } else if (password.length() < 6) {
            MainActivity.appPreferences.showToast("your password length will not match to patern");
        } else {
            Call<User> userCall = MainActivity.serviceApi.doLogin( email, password);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if(response.body().getResponse().equals("data"))
                {
                    MainActivity.appPreferences.setLoginStatus(true);
                    myInterface_login.login(response.body().getName(),response.body().getEmail(),response.body().getCreatedAt());
                    Toast.makeText(getActivity(), "login successfull", Toast.LENGTH_SHORT).show();
                }
                else if(response.body().getResponse().equals("login_failed"))
                {
                    Toast.makeText(getActivity(), "login_failed", Toast.LENGTH_SHORT).show();
                  emailInput.setText("");
                    passwordInput.setText("");
                }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    System.out.println("myerror" + t.getMessage());
                    Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity= (Activity) context;
        myInterface_login= (MyInterface) activity;
    }


}