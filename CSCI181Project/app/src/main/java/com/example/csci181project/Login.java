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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }



    @AfterViews
    public void init()
    {

    }

    @Click
    public void loginButton(){

    }

    @Click
    public void signupButton(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

}