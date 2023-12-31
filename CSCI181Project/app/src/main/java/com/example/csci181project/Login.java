package com.example.csci181project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Extra;

import java.util.UUID;

import io.realm.Realm;

@EActivity
public class Login extends AppCompatActivity {


    @ViewById
    EditText usernameLoginTextField;

    @ViewById
    EditText passwordLoginTextField;

    @ViewById
    Button loginButton;

    @ViewById
    TextView signupClickHere;

    Realm realm;
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @AfterViews
    public void init()
    {
        realm = Realm.getDefaultInstance();
        //UserObject result = realm.where(UserObject.class)
        //        .findFirst();
        //if (result == null) {
        realm.beginTransaction();
        realm.deleteAll();

        UserObject newUserA = new UserObject();
        newUserA.setUuid(UUID.randomUUID().toString());
        newUserA.setName("a");
        newUserA.setPassword("a");
        realm.copyToRealm((newUserA));

        UserObject newUserB = new UserObject();
        newUserB.setUuid(UUID.randomUUID().toString());
        newUserB.setName("b");
        newUserB.setPassword("b");
        realm.copyToRealm((newUserB));

        UserObject newUserC = new UserObject();
        newUserC.setUuid(UUID.randomUUID().toString());
        newUserC.setName("c");
        newUserC.setPassword("c");
        realm.copyToRealm((newUserC));

        realm.commitTransaction();

        toast = Toast.makeText(this, "Made some users a, b and c", Toast.LENGTH_SHORT);
        toast.show();
        //}
    }

    @Click
    public void loginButton(){

        SharedPreferences userid = getSharedPreferences("userid", MODE_PRIVATE);

        UserObject result = realm.where(UserObject.class)
                .equalTo("name", usernameLoginTextField.getText().toString())
                .findFirst();
        if(result==null)
        {
            Toast toast = Toast.makeText(this, "No User found", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {

            if(result.getPassword().equals(passwordLoginTextField.getText().toString()))
            {
                SharedPreferences.Editor editor = userid.edit();
                editor.putString("userid", result.getUuid());
                editor.apply();

                String uuid = userid.getString("userid",null);

                HomeScreen_.intent(this).uuidString(uuid).start();
            }
            else
            {
                Toast toast = Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }
    @Click
    public void signupClickHere() {
        Register_.intent(this).start();
    }

    public void onDestroy() {
        super.onDestroy();

        if (!realm.isClosed()) {
            realm.close();
        }
    }

}