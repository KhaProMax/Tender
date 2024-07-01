package com.example.tender.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tender.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.example.tender.models.Post;
import com.google.gson.Gson;

public class UploadImage extends AppCompatActivity {

    Uri imageUri;
    ImageView FirebaseImage;
    Button btnSelect;
    Button btnUpload;
    EditText ETmessage;
    EditText ETtitle;
    TextView TVuser;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    FirebaseFirestore db= FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);
        btnSelect=findViewById(R.id.select_btn);
        btnUpload=findViewById(R.id.retrive_btn);
        FirebaseImage=findViewById(R.id.firebaseimage);
        ETmessage=findViewById(R.id.messasge);
        ETtitle=findViewById(R.id.title);

        String username = getIntent().getStringExtra("username");

        // Display the username, for example, in a TextView
        TVuser = findViewById(R.id.username_text_view);
        TVuser.setText(username);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
                Date now = new Date();
                String time = formatter.format(now);

                uploadImage(time).addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String title = ETtitle.getText().toString();
                        String message = ETmessage.getText().toString();
                        String user = TVuser.getText().toString();

                        // Generate a unique document ID for the post
                        String postId = db.collection("post").document().getId();

                        Post post = new Post(user, title, message, uri.toString(), now);
                        Map<String, Object> postData = post.toJson();

                        DocumentReference postRef = db.collection("post").document(postId);
                        postRef.set(postData)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(UploadImage.this, "Post uploaded successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(UploadImage.this, RetrivePost.class);
                                    intent.putExtra("username", user);
                                    startActivity(intent);

                                    // Clear the EditText fields
                                    ETtitle.setText(null);
                                    ETmessage.setText(null);

                                    // Reset the imageUri
                                    imageUri = null;
                                    FirebaseImage.setImageURI(null);
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(UploadImage.this, "Failed to upload post: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.e("UploadImage", "Failed to upload post: ", e);
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadImage.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private Task<Uri> uploadImage(String time) {
        Bitmap compressedBitmap = compressImage(imageUri);
        if (compressedBitmap != null) {
            // Convert the compressed bitmap to a ByteArray
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] data = baos.toByteArray();

            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading File...");
            progressDialog.show();

            // Use the byte array for upload
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/" + time);
            return storageReference.putBytes(data)
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Get the download URL of the uploaded image
                            return storageReference.getDownloadUrl();
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FirebaseImage.setImageURI(null);
                            Toast.makeText(UploadImage.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(UploadImage.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Handle the case where the compression failed
            Toast.makeText(UploadImage.this, "Failed to compress the image", Toast.LENGTH_SHORT).show();
            return Tasks.forResult(null);
        }
    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            FirebaseImage.setImageURI(imageUri);

        }
    }

    private Bitmap compressImage(Uri imageUri) {
        try {
            // Decode the image from the URI
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

            // Compress the bitmap
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); // Set the compression quality as per your requirement

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

//    StorageReference postsRef = FirebaseStorage.getInstance().getReference("posts");
//postsRef.listAll()
//        .addOnSuccessListener(new OnSuccessListener<ListResult>() {
//@Override
//public void onSuccess(ListResult listResult) {
//        List<StorageReference> items = listResult.getItems();
//        List<String> usernames = new ArrayList<>();
//        List<String> dates = new ArrayList<>();
//
//        // Iterate through the list of files
//        for (StorageReference item : items) {
//        String[] parts = item.getPath().split("/");
//        String username = parts[1].split("_")[0];
//        String date = parts[1].split("_")[1];
//        usernames.add(username);
//        dates.add(date);
//        }
//
//        // Now you have two lists: usernames and dates
//        // You can use these lists to process the files further
//        // For example, you can print them:
//        for (int i = 0; i < usernames.size(); i++) {
//        System.out.println("Username: " + usernames.get(i) + ", Date: " + dates.get(i));
//        }
//        }
//        })
//        .addOnFailureListener(new OnFailureListener() {
//@Override
//public void onFailure(@NonNull Exception e) {
//        // Handle the error
//        }
//        });