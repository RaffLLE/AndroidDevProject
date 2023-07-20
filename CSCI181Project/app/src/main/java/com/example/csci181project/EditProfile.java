package com.example.csci181project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.AfterViews;

@EActivity
public class EditProfile extends AppCompatActivity {

    @ViewById
    Button saveEditProfileButton;

    @ViewById
    Button exitEditProfileButton;

    @ViewById
    ImageButton changeProfilePicButton;

    @ViewById
    EditText usernameEditProfileTextField;

    @ViewById
    EditText userBioEditProfileTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    @AfterViews
    public void init(){

    }

    @Click
    public void exitEditProfileButton(){

        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);

    }
}