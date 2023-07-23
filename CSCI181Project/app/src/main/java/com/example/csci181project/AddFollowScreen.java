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

import io.realm.Realm;
import io.realm.RealmResults;

@EActivity
public class AddFollowScreen extends AppCompatActivity implements UserObjectActivity{


    @ViewById
    Button backAddFollowButton;

    @ViewById
    RecyclerView recyclerView;

    Realm realm;

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

        // query the things to display
        RealmResults<UserObject> list = realm.where(UserObject.class).findAll();
        UserObjectAdapter user_adapter = new UserObjectAdapter(this, list, true);

        recyclerView.setAdapter(user_adapter);
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
}