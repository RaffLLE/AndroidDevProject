package com.example.csci181project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.AfterViews;

import io.realm.Realm;

@EActivity
public class HomeScreen extends AppCompatActivity {


    @ViewById
    TextView userBio;

    @ViewById
    ImageView userProfilePic;

    @ViewById
    TextView userNameHomeLabel;

    @ViewById
    Button editProfileButton;

    @ViewById
    Button addPostButton;

    @ViewById
    Button followPeopleButton;

    @ViewById
    Button followingButton;

    @Extra
    String uuidString;

    UserObject user;

    Realm realm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    @AfterViews
    public void init(){
        
        realm = Realm.getDefaultInstance();
        user = realm.where(UserObject.class)
                .equalTo("uuid", uuidString)
                .findFirst();
        userNameHomeLabel.setText(user.getName());
        userBio.setText(user.getBio());
    }

    @Click
    public void editProfileButton(){
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
    }
}