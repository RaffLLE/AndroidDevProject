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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    @AfterViews
    public void init(){

    }

    @Click
    public void registerButton(){


    }

    @Click
    public void goToLoginButton(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}