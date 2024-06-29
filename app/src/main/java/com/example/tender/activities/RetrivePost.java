package com.example.tender.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tender.R;
import com.example.tender.models.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RetrivePost extends AppCompatActivity {

    Uri imageUri;
    ImageView FirebaseImage;
    Button btnRetrive;
    TextView TVmessage;
    TextView TVtitle;
    TextView TVuser;
    EditText ETpostID;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrive_post);
        btnRetrive=findViewById(R.id.retrive_btn);
        FirebaseImage=findViewById(R.id.firebaseimage);
        TVmessage =findViewById(R.id.messasge);
        TVtitle =findViewById(R.id.title);
        ETpostID=findViewById(R.id.postId);
        // Retrieve the username from the intent extra
        String username = getIntent().getStringExtra("username");
        TVuser = findViewById(R.id.username_text_view);
        TVuser.setText(username);

        btnRetrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog=new ProgressDialog(RetrivePost.this);
                progressDialog.setMessage("Fetching Post");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String postID=ETpostID.getText().toString();

                //get image from firebase storage and rotate 90 degree to display
                StorageReference storageRef = FirebaseStorage.getInstance().getReference("posts/" + postID+ "/image.jpg");
                try {
                    File localImage = File.createTempFile("temp","jpg");
                    storageRef.getFile(localImage).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            if( progressDialog.isShowing() )
                            {
                                progressDialog.dismiss();
                                Bitmap bitmap = BitmapFactory.decodeFile(localImage.getAbsolutePath());

// rotate image 90 degree
                                int rotation = getImageRotation(localImage.getAbsolutePath());
                                Matrix matrix = new Matrix();
                                matrix.postRotate(rotation);
                                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

// Set image to image view
                                FirebaseImage.setImageBitmap(rotatedBitmap);
                                // Retrieve the post.json
                                StorageReference postRef = FirebaseStorage.getInstance().getReference("posts/" + postID + "/post.json");
                                retrievePostData(postRef);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if( progressDialog.isShowing() )
                            {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(RetrivePost.this, "Failed to retrive post", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    // get data from json file and set it to text view
    private void retrievePostData(StorageReference postRef) {
        try {
            File localFile = File.createTempFile("temp", ".json");
            postRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    // Parse the post.json data
                    String jsonData = readJsonFromFile(localFile);
                    Post post = new Gson().fromJson(jsonData, Post.class);

                    // Update the TextViews
                    TVtitle.setText(post.getTitle());
                    TVmessage.setText(post.getMessage());
                    //TVtimestamp.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(post.getTimestamp()));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(RetrivePost.this, "Failed to retrieve post data", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readJsonFromFile(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static  int getImageRotation(String imagePath) {
        ExifInterface exif;
        try {
            exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
