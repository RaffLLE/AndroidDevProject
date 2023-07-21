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
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.AfterViews;

import io.realm.Realm;


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

    @Extra
    String uuidString;

    UserObject user;

    Realm realm;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    @AfterViews
    public void init(){

        realm = Realm.getDefaultInstance();
        user = realm.where(UserObject.class)
                .equalTo("uuid", uuidString)
                .findFirst();
        usernameEditProfileTextField.setText(user.getName());
        userBioEditProfileTextField.setText(user.getBio());

    }

    @Click
    public void exitEditProfileButton(){

        finish();

    }

    public void saveEditProfileButton(){

        realm.beginTransaction();
        user.setName(String.valueOf(usernameEditProfileTextField.getText()));
        user.setBio(String.valueOf(userBioEditProfileTextField.getText()));
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();

        toast = Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT);
        toast.show();

        finish();

    }
}