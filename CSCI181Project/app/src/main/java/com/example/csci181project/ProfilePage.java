package com.example.csci181project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Extra;

import java.io.File;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity
public class ProfilePage extends AppCompatActivity implements PostObjectActivity{

    @ViewById
    Button backProfilePageButton;

    @ViewById
    Button followProfilePageButton;

    @ViewById
    ImageView imageView2;

    @ViewById
    TextView profilePageTitle;

    @ViewById
    TextView userProfilePageBio;

    @ViewById
    TextView userFeedProfilePageLabel;

    @ViewById
    RecyclerView recyclerView2;

    @Extra
    String uuidString;

    UserObject user;

    Realm realm;

    Boolean ownPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
    }

    @AfterViews
    public void init()
    {
        realm = Realm.getDefaultInstance();

        user = realm.where(UserObject.class)
                .equalTo("uuid", uuidString)
                .findFirst();
        profilePageTitle.setText(user.getName() + "'s Page");
        userProfilePageBio.setText(user.getBio());
        userFeedProfilePageLabel.setText(user.getName() + "'s Posts");

        SharedPreferences userid = getSharedPreferences("userid", MODE_PRIVATE);
        String uuid = userid.getString("userid",null);

        if (uuid.equals(uuidString)) {
            ownPage = true;
            followProfilePageButton.setVisibility(View.INVISIBLE);
        }
        else
        {
            ownPage = false;
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView2.setLayoutManager(mLayoutManager);

        refreshData();

    }


    @Click
    public void backProfilePageButton(){
        finish();
    }

    @Click void followProfilePageButton() {
        SharedPreferences userid = getSharedPreferences("userid", MODE_PRIVATE);
        String uuid = userid.getString("userid",null);
        realm = Realm.getDefaultInstance();

        FollowPairObject result = realm.where(FollowPairObject.class)
                .equalTo("followerUuid", uuid)
                .equalTo("followedUuid", uuidString)
                .findFirst();
        if (result == null) {

            realm.beginTransaction();

            FollowPairObject newPair = new FollowPairObject();
            newPair.setPairUuid(UUID.randomUUID().toString());
            newPair.setFollowedUuid(uuidString);
            newPair.setFollowerUuid(uuid);

            realm.copyToRealm((newPair));
            realm.commitTransaction();

            Toast toast = Toast.makeText(this, "You followed: " + user.getName(), Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            Toast toast = Toast.makeText(this, "Already Following", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void onDestroy() {
        super.onDestroy();

        if (!realm.isClosed()) {
            realm.close();
        }
    }

    public void refreshData()
    {
        File imageDir = getExternalCacheDir();
        File imageFile = new File(imageDir, user.getUuid()+".jpeg");

        if (imageFile.exists()) {
            refreshImageView(imageView2, imageFile);
        }
        RealmResults<PostObject> posts;
        if(ownPage) {
            posts = realm.where(PostObject.class)
                    .equalTo("userUuid", uuidString)
                    .findAll();
        }
        else
        {
            posts = realm.where(PostObject.class)
                    .equalTo("userUuid", uuidString)
                    .and()
                    .equalTo("isPrivate", Boolean.FALSE)
                    .findAll();
        }

        PostObjectAdapter post_adapter = new PostObjectAdapter(this, posts, true);

        recyclerView2.setAdapter(post_adapter);
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