package com.example.csci181project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.AfterViews;

import java.util.UUID;
import io.realm.Realm;

@EActivity
public class Register extends AppCompatActivity {

    @ViewById
    EditText usernameRegisterTextField;

    @ViewById
    EditText passwordRegisterTextField;

    @ViewById
    EditText confirmPasswordTextField;

    @ViewById
    Button registerButton;

    @ViewById
    Button goToLoginButton;

    Realm realm;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    @AfterViews
    public void init(){
        realm = Realm.getDefaultInstance();
    }

    @Click
    public void registerButton(){


        String userNameString = usernameRegisterTextField.getText().toString();
        String passwordString = passwordRegisterTextField.getText().toString();
        String confirmString = confirmPasswordTextField.getText().toString();


        if(userNameString.isEmpty())
        {
            toast = Toast.makeText(this, "Username must not be blank", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        else
        {

            UserObject result = realm.where(UserObject.class)
                    .equalTo("name", userNameString)
                    .findFirst();

            if(result!=null) {
                toast = Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

        }

        if(passwordString.isEmpty())
        {
            toast = Toast.makeText(this, "Password must not be blank", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        else if(!passwordString.equals(confirmString))
        {
            toast = Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        realm.beginTransaction();
        UserObject newUser = new UserObject();
        newUser.setUuid(UUID.randomUUID().toString());

        newUser.setName(userNameString);
        newUser.setPassword(passwordString);
        realm.copyToRealm((newUser));
        realm.commitTransaction();

        toast = Toast.makeText(this, "New User Registered", Toast.LENGTH_SHORT);
        toast.show();

        finish();
    }

    @Click
    public void goToLoginButton(){
        finish();
    }
}