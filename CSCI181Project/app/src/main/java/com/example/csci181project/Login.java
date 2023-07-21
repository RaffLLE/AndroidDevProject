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

import io.realm.Realm;

@EActivity
public class Login extends AppCompatActivity {


    @ViewById
    EditText usernameLoginTextField;

    @ViewById
    EditText passwordLoginTextField;

    @ViewById
    Button loginButton;

    @ViewById
    Button signupButton;

    Realm realm;
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @AfterViews
    public void init()
    {
        realm = Realm.getDefaultInstance();
    }

    @Click
    public void loginButton(){

        UserObject result = realm.where(UserObject.class)
                .equalTo("name", usernameLoginTextField.getText().toString())
                .findFirst();
        if(result==null)
        {
            Toast toast = Toast.makeText(this, "No User found", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {

            if(result.getPassword().equals(passwordLoginTextField.getText().toString()))
            {

                Intent intent = new Intent(this, ProfilePage_.class);
                startActivity(intent);
                ProfilePage_.intent(this).uuidString(result.getUuid()).start();
            }
            else
            {
                Toast toast = Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }

    @Click
    public void signupButton(){
        Intent intent = new Intent(this, Register_.class);
        startActivity(intent);
    }

}