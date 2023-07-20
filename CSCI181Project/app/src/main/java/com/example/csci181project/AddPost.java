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
public class AddPost extends AppCompatActivity {

    @ViewById
    ImageView imageAddPost;

    @ViewById
    Button takePictureButton;

    @ViewById
    EditText postCaptionTextField;

    @ViewById
    CheckBox publicCheckbox;

    @ViewById
    Button postButton;

    @ViewById
    Button cancelAddPostButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
    }

    @AfterViews
    public void init(){


    }


    @Click
    public void takePictureButton(){

        // insert camera code here huhuhu
    }

    @Click
    public void cancelAddPostButton(){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    @Click
    public void postButton(){

        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}