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
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Extra;

import io.realm.Realm;
@EActivity
public class ProfilePage extends AppCompatActivity {

    @ViewById
    Button backProfilePageButton;

    @ViewById
    Button followProfilePageButton;

    @ViewById
    TextView profilePageTitle;

    @ViewById
    TextView userProfilePageBio;

    @ViewById
    TextView userFeedProfilePageLabel;

    @Extra
    String uuidString;

    UserObject user;

    Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
    }

    @AfterViews
    public void init()
    {
        realm = Realm.getDefaultInstance();
        user = realm.where(UserObject.class)
                .equalTo("uuid", uuidString)
                .findFirst();
        profilePageTitle.setText(user.getName() + "'s Page");
        userProfilePageBio.setText(user.getBio());
        userFeedProfilePageLabel.setText(user.getName() + "'s Posts");
    }


    @Click
    public void backProfilePageButton(){
        
    }

}