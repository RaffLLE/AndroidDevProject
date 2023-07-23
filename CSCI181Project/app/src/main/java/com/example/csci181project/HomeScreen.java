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


import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.AfterViews;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity
public class HomeScreen extends AppCompatActivity implements PostObjectActivity{


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
        AddFollowScreen_.intent(this).uuidString(uuidString).start();


    }

    @Click
    public void followingButton() {
        CurrentFollowPage_.intent(this).uuidString(uuidString).start();

    }

    public void refreshData()
    {
        user = realm.where(UserObject.class)
                .equalTo("uuid", uuidString)
                .findFirst();
        userNameHomeLabel.setText(user.getName());
        userBio.setText(user.getBio());

        // init photo
        File imageDir = getExternalCacheDir();
        File imageFile = new File(imageDir, user.getUuid()+".jpeg");

        if (imageFile.exists()) {
            refreshImageView(userProfilePic, imageFile);
        }
    }

    private void refreshImageView(ImageView imageView, File savedImage) {
        // this will put the image saved to the file system to the imageview
        Picasso.get()
                .load(savedImage)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(imageView);
    }
}