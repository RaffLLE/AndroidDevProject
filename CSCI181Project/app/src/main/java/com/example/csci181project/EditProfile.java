package com.example.csci181project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import java.io.FileOutputStream;
import java.io.IOException;

import io.realm.Realm;

@EActivity
public class EditProfile extends AppCompatActivity {

    @ViewById
    Button saveEditProfileButton;

    @ViewById
    Button exitEditProfileButton;

    @ViewById
    ImageView currentProfilePic;

    @ViewById
    EditText usernameEditProfileTextField;

    @ViewById
    EditText userBioEditProfileTextField;

    @Extra
    String uuidString;

    UserObject user;

    Realm realm;

    Toast toast;

    public static int REQUEST_CODE_IMAGE_SCREEN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    @AfterViews
    public void init(){

        realm = Realm.getDefaultInstance();
        user = realm.where(UserObject.class)
                .equalTo("uuid", uuidString)
                .findFirst();
        usernameEditProfileTextField.setText(user.getName());
        userBioEditProfileTextField.setText(user.getBio());

        currentProfilePic.setImageResource(R.mipmap.ic_launcher);

        // init photo
        File imageDir = getExternalCacheDir();
        File imageFile = new File(imageDir, user.getUuid()+".jpeg");

        if (imageFile.exists()) {
            refreshImageView(currentProfilePic, imageFile);
        }
    }

    @Click
    public void exitEditProfileButton(){

        finish();

    }


    @Click
    public void saveEditProfileButton(){

        if(String.valueOf(usernameEditProfileTextField.getText()).isEmpty())
        {
            toast = Toast.makeText(this, "Username must not be blank", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        realm.beginTransaction();
        user.setName(String.valueOf(usernameEditProfileTextField.getText()));
        user.setBio(String.valueOf(userBioEditProfileTextField.getText()));
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();

        toast = Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT);
        toast.show();

        finish();

    }

    @Click
    public void currentProfilePic() {
        ImageActivity_.intent(this).startForResult(REQUEST_CODE_IMAGE_SCREEN);
    }

    // SINCE WE USE startForResult(), code will trigger this once the next screen calls finish()
    public void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        super.onActivityResult(requestCode, responseCode, data);

        if (requestCode==REQUEST_CODE_IMAGE_SCREEN)
        {
            if (responseCode==ImageActivity.RESULT_CODE_IMAGE_TAKEN)
            {
                // receieve the raw JPEG data from ImageActivity
                // this can be saved to a file or save elsewhere like Realm or online
                byte[] jpeg = data.getByteArrayExtra("rawJpeg");

                try {
                    // save rawImage to file
                    File savedImage = saveFile(jpeg, uuidString+".jpeg");

                    // load file to the image view via picasso
                    refreshImageView(currentProfilePic, savedImage);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
        }
    }

    private File saveFile(byte[] jpeg, String filename) throws IOException
    {
        // this is the root directory for the images
        File getImageDir = getExternalCacheDir();

        // just a sample, normally you have a diff image name each time
        File savedImage = new File(getImageDir, filename);


        FileOutputStream fos = new FileOutputStream(savedImage);
        fos.write(jpeg);
        fos.close();
        return savedImage;
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