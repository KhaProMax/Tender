package com.example.tender.adapters;

import static com.example.tender.activities.RetrivePost.getImageRotation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tender.R;
import com.example.tender.entities.Match;
import com.example.tender.models.Post;
import com.example.tender.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.MyViewHolder> {
    private Context context;
    private List<Post> postList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, location,age,message,title;
        ImageView imgProfile, imgContent;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.text_name);
            date = view.findViewById(R.id.text_date);
            location = view.findViewById(R.id.text_location);
            age=view.findViewById(R.id.text_age);
            imgProfile = view.findViewById(R.id.img_profile);
            title=view.findViewById(R.id.title);
            message=view.findViewById(R.id.messasge);
            imgContent = view.findViewById(R.id.img_content);
        }
    }


    public MatchListAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_layout_match, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Post post = postList.get(position);
        final String username = post.getUsername();

        // Retrieve the user data from the Firebase Realtime Database
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(username);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    holder.name.setText(user.getUsername());
//                    holder.age.setText(String.valueOf(user.getAge()));

                    File localImage = null;
                    try {
                        localImage = File.createTempFile("temp","jpg");
                        Bitmap bitmap = BitmapFactory.decodeFile(localImage.getAbsolutePath());
// rotate image 90 degree
                        int rotation = getImageRotation(localImage.getAbsolutePath());
                        Matrix matrix = new Matrix();
                        matrix.postRotate(rotation);
                        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                        holder.imgProfile.setImageBitmap(rotatedBitmap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //Change format for timestamp before display
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = outputFormat.format(post.getTimestamp());
        holder.date.setText(formattedDate);
        holder.title.setText(post.getTitle());
        holder.message.setText(post.getMessage());
        holder.location.setText(post.getTitle());

        File localImage = null;
        try {
            localImage = File.createTempFile("temp","jpg");
            Bitmap bitmap = BitmapFactory.decodeFile(localImage.getAbsolutePath());
            // rotate image 90 degree
            int rotation = getImageRotation(localImage.getAbsolutePath());
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            holder.imgContent.setImageBitmap(rotatedBitmap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

}