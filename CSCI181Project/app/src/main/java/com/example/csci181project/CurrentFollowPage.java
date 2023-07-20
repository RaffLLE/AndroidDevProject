package com.example.csci181project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.AfterViews;

@EActivity
public class CurrentFollowPage extends AppCompatActivity {

    @ViewById
    Button backCurrentFollowButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_follow_page);
    }

    @AfterViews
    public void init(){

    }


    @Click
    public void backCurrentFollowButton(){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }


}