package com.example.theproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private TextView userName, userEmail;
    Switch nightModeSwitch;
    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SettingsFragment () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_settings, container, false);

        nightModeSwitch = v.findViewById(R.id.setting_one_switch);

        sharedPreferences = getActivity().getSharedPreferences("night", 0);
        nightMode = sharedPreferences.getBoolean("night_mode", true);

        if(nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            nightModeSwitch.setChecked(true);
        }

        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(nightModeSwitch.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    nightModeSwitch.setChecked(true);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode",true);
                    editor.commit();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    nightModeSwitch.setChecked(false);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode",false);
                    editor.commit();
                }
            }
        });

        //We used SharedPreferences to Save Mode If we Exit the App.
        /*sharedPreferences = getContext().getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night", false); //Light Mode is Default

        if(nightMode){
            nightModeSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        nightModeSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (nightMode){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", true);
                }
            }
        }); */

        userName = v.findViewById(R.id.user_profile_name);
        userEmail = v.findViewById(R.id.user_profile_email);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        /*
         * If a user is still authenticated through FireBase this activity will remain, if not the user will be
         * redirected to the MainActivity (Login Page).
         */
        if (user == null){
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
        } else {
            if(user.getDisplayName().isEmpty()){
                userName.setText(user.getEmail());
                userEmail.setVisibility(v.GONE);
            } else {
                userName.setText(user.getDisplayName());
                userEmail.setText(user.getEmail());
            }

        }

        return v;
    }

}