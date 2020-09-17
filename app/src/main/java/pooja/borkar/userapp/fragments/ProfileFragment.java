package pooja.borkar.userapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pooja.borkar.userapp.R;
import pooja.borkar.userapp.activities.MainActivity;
import pooja.borkar.userapp.services.MyInterface;

public class ProfileFragment extends Fragment {
TextView name,email;
Button logoutbutton;
MyInterface myInterface_profile;


    public ProfileFragment() {
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

       View view=inflater.inflate(R.layout.fragment_profile, container, false);
   email=view.findViewById(R.id.email);
        name=view.findViewById(R.id.name);
        String Name="Hi->"+ MainActivity.appPreferences.getDiaplayName();
        name.setText(Name);
      String Email="Registred Email->"+ MainActivity.appPreferences.getDiaplayEmail()+"\nRegister_At->"+ MainActivity.appPreferences.getDiaplayDate();
      email.setText(Email);
       // name.setText(("Hi,"+ MainActivity.appPreferences.getDiaplayName()));
        //email.setText(("Registred On,"+ MainActivity.appPreferences.getDiaplayEmail()));
        logoutbutton=view.findViewById(R.id.logoutBtn);
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myInterface_profile.logout();
            }
        });
        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity= (Activity) context;
        myInterface_profile= (MyInterface) activity;
    }

}