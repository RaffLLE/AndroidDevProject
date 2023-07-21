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
import java.util.UUID;

import io.realm.Realm;

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

    String tempID;

    @Extra
    String uuidString;
    Realm realm;

    public static int REQUEST_CODE_IMAGE_SCREEN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
    }

    @AfterViews
    public void init(){
        tempID = UUID.randomUUID().toString();
        realm = Realm.getDefaultInstance();
    }

    @Click
    public void takePictureButton(){
        ImageActivity_.intent(this).startForResult(REQUEST_CODE_IMAGE_SCREEN);
    }

    @Click
    public void cancelAddPostButton(){
        //Intent intent = new Intent(this, HomeScreen.class);
        //startActivity(intent);
        finish();
    }

    @Click
    public void postButton(){
        String postString = postCaptionTextField.getText().toString();
        Boolean isPrivate = publicCheckbox.isChecked();

        realm.beginTransaction();

        PostObject newPost = new PostObject();
        newPost.setPostUuid(UUID.randomUUID().toString());
        newPost.setUserUuid(uuidString);
        newPost.setText(postString);
        newPost.setDatePosted("IDK");
        newPost.setIsPrivate(isPrivate);

        realm.copyToRealm((newPost));
        realm.commitTransaction();

        //Intent intent = new Intent(this, HomeScreen.class);
        //startActivity(intent);
        finish();
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
                    File savedImage = saveFile(jpeg, tempID+".jpeg");

                    // load file to the image view via picasso
                    refreshImageView(imageAddPost, savedImage);
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