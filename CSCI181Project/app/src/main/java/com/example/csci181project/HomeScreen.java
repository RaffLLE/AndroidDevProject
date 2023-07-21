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
import io.realm.RealmResults;

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

    @ViewById
    RecyclerView yourFeedRecycleView;
    @Extra
    String uuidString;

    UserObject user;

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

        yourFeedRecycleView.setAdapter(post_adapter);

        refreshData();
    }

    @Click
    public void editProfileButton(){
        EditProfile_.intent(this).uuidString(uuidString).start();
    }

    @Click
    public void addPostButton() {
        AddPost_.intent(this).uuidString(uuidString).start();
    }

    @Click
    public void followPeopleButton() {
        Intent intent = new Intent(this, AddFollowScreen_.class);
        startActivity(intent);
    }

    @Click
    public void followingButton() {
        Intent intent = new Intent(this, CurrentFollowPage_.class);
        startActivity(intent);
    }

    public void refreshData()
    {
        user = realm.where(UserObject.class)
                .equalTo("uuid", uuidString)
                .findFirst();
        userNameHomeLabel.setText(user.getName());
        userBio.setText(user.getBio());
    }
}