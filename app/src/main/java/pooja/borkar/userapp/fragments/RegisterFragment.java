package pooja.borkar.userapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pooja.borkar.userapp.R;
import pooja.borkar.userapp.activities.MainActivity;
import pooja.borkar.userapp.extras.AppPreferences;
import pooja.borkar.userapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

   EditText nameInput_reg,emailInput_reg,phoneInput_reg,passwordInput_reg,genderInput_reg,dob_reg;

   Button button_reg;
    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_register, container, false);
        nameInput_reg=view.findViewById(R.id.nameInput);
        emailInput_reg=view.findViewById(R.id.emailInput);
        phoneInput_reg=view.findViewById(R.id.phoneInput);
        passwordInput_reg=view.findViewById(R.id.passwordInput);
        genderInput_reg=view.findViewById(R.id.genderInput);
        dob_reg=view.findViewById(R.id.dobInput);
        button_reg=view.findViewById(R.id.regBtn);
        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }

            private void registerUser() {
                String name=nameInput_reg.getText().toString().trim();
                String email=emailInput_reg.getText().toString().trim();
                String phone=phoneInput_reg.getText().toString().trim();
                String password=passwordInput_reg.getText().toString().trim();
                String gender=genderInput_reg.getText().toString().trim();
                String dob=dob_reg.getText().toString().trim();




                if(TextUtils.isEmpty(name)){
                    MainActivity.appPreferences.showToast("Enter your name");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    MainActivity.appPreferences.showToast("Your email is invalid");
                } else if(TextUtils.isEmpty(phone)){
                    MainActivity.appPreferences.showToast("Enter your phone");
                }
                else if(TextUtils.isEmpty(gender)){
                    MainActivity.appPreferences.showToast("Enter your phone");
                }
                else if(TextUtils.isEmpty(dob)){
                    MainActivity.appPreferences.showToast("Enter your DOF");
                } else if(TextUtils.isEmpty(password)){
                    MainActivity.appPreferences.showToast("Enter your pass");
                }  else if(password.length()<6)
                {
                    MainActivity.appPreferences.showToast("your email length will not match to patern");
                }
                else
                {
                    Call<User> userCall=MainActivity.serviceApi.doRegisteration(name,email,gender,dob,password,phone);
                    userCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(@NonNull Call<User> call,@NonNull Response<User> response) {
                            //Log.i("My Response",response.body().getResponse());
                           // Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                      // System.out.println("myway"+response.body().getResponse());
                            if(response.body().getResponse().matches("inserted"))
                            {
                                show_Message("Successfully Registered!!","Welcome "+name);
                                // Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            }else if(response.body().getResponse().matches("exists"))
                            {
                                show_Message("Already registered user!!","Welcome "+name);
                                //  Toast.makeText(getActivity(), "User already exists!!!!", Toast.LENGTH_SHORT).show();
                            }

                            Log.i("My response",response.body().getResponse());
                        }

                        private void show_Message(String title, String input) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle(title);
                            builder.setMessage(input);

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    clearText();
                                    builder.setCancelable(true);
                                }
                            });
                            builder.show();
                        }


                        private void clearText () {

                            emailInput_reg.setText("");
                            nameInput_reg.setText("");

                            passwordInput_reg.setText("");
                            phoneInput_reg.setText("");
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            System.out.println("myerror"+t.getMessage());
                            Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();

                        }
                    });



                }




            }
        });


        return  view;
    }
}