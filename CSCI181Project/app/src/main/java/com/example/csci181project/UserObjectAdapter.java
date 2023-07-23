package com.example.csci181project;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.App;

import java.io.File;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

// the parameterization <type of the RealmObject, ViewHolder type)
public class UserObjectAdapter extends RealmRecyclerViewAdapter<UserObject, UserObjectAdapter.ViewHolder> {

    // THIS DEFINES WHAT VIEWS YOU ARE FILLING IN
    public class ViewHolder extends RecyclerView.ViewHolder {

        // have a field for each one
        TextView profileName;
        TextView profileBio;

        ImageView profileImage;

        Button viewProfileButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize them from the itemView using standard style
            profileName = itemView.findViewById(R.id.usernameTextView);
            profileBio = itemView.findViewById(R.id.bioTextView);

            // initialize the buttons in the layout
            viewProfileButton = itemView.findViewById(R.id.viewProfileButton);

            // initialize image in layout
            profileImage = itemView.findViewById(R.id.userProfilePicture);
        }
    }


    // IMPORTANT
    // THE CONTAINING ACTIVITY NEEDS TO BE PASSED SO YOU CAN GET THE LayoutInflator(see below)
    AppCompatActivity activity;

    public UserObjectAdapter(AppCompatActivity activity, @Nullable OrderedRealmCollection<UserObject> data, boolean autoUpdate) {
        super(data, autoUpdate);

        // THIS IS TYPICALLY THE ACTIVITY YOUR RECYCLERVIEW IS IN
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // create the raw view for this ViewHolder
        View v = activity.getLayoutInflater().inflate(R.layout.user_layout, parent, false);  // VERY IMPORTANT TO USE THIS STYLE

        // assign view to the viewholder
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // gives you the data object at the given position
        UserObject u = getItem(position);

        // copy all the values needed to the appropriate views
        holder.profileName.setText(u.getName());
        holder.profileBio.setText(u.getBio());

        File getImageDir = activity.getExternalCacheDir();  // this method is in the Activity class

        // just a sample, normally you have a diff image name each time
        File file = new File(getImageDir, u.getUuid()+".jpeg");

        holder.profileImage.setImageResource(R.mipmap.ic_launcher);
        if (file.exists()) {
            // this will put the image saved to the file system to the imageview
            Picasso.get()
                    .load(file)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(holder.profileImage);
        }

        holder.viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserObjectActivity activity1 = (UserObjectActivity) activity;
                activity1.view(u);
            }
        });

    }

}
