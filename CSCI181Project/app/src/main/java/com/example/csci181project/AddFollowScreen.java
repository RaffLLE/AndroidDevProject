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
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.AfterViews;

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity
public class AddFollowScreen extends AppCompatActivity implements UserObjectActivity{


    @ViewById
    Button backAddFollowButton;

    @ViewById
    RecyclerView recyclerView;

    Realm realm;

    @Extra
    String uuidString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_follow_screen);
    }

    @AfterViews
    public void init(){
        // initialize realm
        realm = Realm.getDefaultInstance();

        // initialize RecyclerView
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        refreshData();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        refreshData();
    }


    @Click
    public void backAddFollowButton(){
        finish();
    }


    public void onDestroy()
    {
        super.onDestroy();
        if (!realm.isClosed())
        {
            realm.close();
        }
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

    public void refreshData()
    {
        RealmResults<FollowPairObject> list = realm.where(FollowPairObject.class)
                .equalTo("followerUuid", uuidString)
                .findAll();

        String[] followedUuid = new String[list.size()];
        for(int i = 0; i<list.size(); i++)
        {
            followedUuid[i] = list.get(i).getFollowedUuid();
        }

        RealmResults<UserObject> finalList = realm.where(UserObject.class)
                .not()
                .in("uuid", followedUuid)
                .findAll();

        UserObjectAdapter user_adapter = new UserObjectAdapter(this, finalList, true);

        recyclerView.setAdapter(user_adapter);
    }
}