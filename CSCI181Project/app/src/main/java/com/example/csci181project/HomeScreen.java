package com.example.csci181project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
<<<<<<< HEAD
import io.realm.RealmResults;
=======
>>>>>>> 834d19b54338391bf5b74b81cd0a33d1581f75c8

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

<<<<<<< HEAD
    @ViewById
    RecyclerView yourFeedRecycleView;
=======
    @Extra
    String uuidString;

    UserObject user;

    Realm realm;

>>>>>>> 834d19b54338391bf5b74b81cd0a33d1581f75c8

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    @Override
    protected void onResume(){
        super.onResume();
        refreshData();
    }

    @AfterViews
    public void init(){
        // initialize realm
        realm = Realm.getDefaultInstance();

        // initialize RecyclerView
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        yourFeedRecycleView.setLayoutManager(mLayoutManager);

        // query the things to display
        RealmResults<PostObject> list = realm.where(PostObject.class).findAll();
        PostObjectAdapter post_adapter = new PostObjectAdapter(this, list, true);

<<<<<<< HEAD
        yourFeedRecycleView.setAdapter(post_adapter);
=======
        realm = Realm.getDefaultInstance();
        refreshData();
>>>>>>> 834d19b54338391bf5b74b81cd0a33d1581f75c8
    }

    @Click
    public void editProfileButton(){
<<<<<<< HEAD
        Intent intent = new Intent(this, EditProfile_.class);
        startActivity(intent);
    }

    @Click
    public void addPostButton() {
        Intent intent = new Intent(this, AddPost_.class);
        startActivity(intent);
=======

        EditProfile_.intent(this).uuidString(user.getUuid()).start();

    }

    public void refreshData()
    {
        user = realm.where(UserObject.class)
                .equalTo("uuid", uuidString)
                .findFirst();
        userNameHomeLabel.setText(user.getName());
        userBio.setText(user.getBio());
>>>>>>> 834d19b54338391bf5b74b81cd0a33d1581f75c8
    }
}