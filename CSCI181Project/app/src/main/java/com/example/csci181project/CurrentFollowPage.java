package com.example.csci181project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import org.androidannotations.annotations.Extra;


import io.realm.Realm;
import io.realm.RealmResults;

@EActivity
public class CurrentFollowPage extends AppCompatActivity implements UserObjectActivity{

    @ViewById
    Button backCurrentFollowButton;

    @ViewById
    RecyclerView recyclerView3;

    Realm realm;

    @Extra
    String uuidString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_follow_page);
    }

    @AfterViews
    public void init(){

        realm = Realm.getDefaultInstance();

        // initialize RecyclerView
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView3.setLayoutManager(mLayoutManager);

        RealmResults<FollowPairObject> list = realm.where(FollowPairObject.class)
                .equalTo("followerUuid", uuidString)
                .findAll();

        String[] followedUuid = new String[list.size()];
        for(int i = 0; i<list.size(); i++)
        {
            followedUuid[i] = list.get(i).getFollowedUuid();
        }

        RealmResults<UserObject> finalList = realm.where(UserObject.class)
                .in("uuid", followedUuid)
                .findAll();

         // query the things to display

        UserObjectAdapter user_adapter = new UserObjectAdapter(this, finalList, true);

        recyclerView3.setAdapter(user_adapter);
    }


    @Click
    public void backCurrentFollowButton(){
        finish();
    }


    @Override
    public void view(UserObject u)
    {
        // need to check if previously deleted
        if (u.isValid())
        {
            ProfilePage_.intent(this).uuidString(u.getUuid()).start();

        }
    }


}