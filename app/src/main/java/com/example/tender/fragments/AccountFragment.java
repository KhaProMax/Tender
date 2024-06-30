package com.example.tender.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

//import com.smarteist.autoimageslider.IndicatorAnimations;
//import com.smarteist.autoimageslider.SliderAnimations;
//import com.smarteist.autoimageslider.SliderView;
import com.example.tender.R;
import com.example.tender.activities.EditProfileActivity;
import com.example.tender.activities.SplashscreenActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;
//import com.thecode.tinderclone.adapters.SliderAdapter;

public class AccountFragment extends Fragment {
    public static final int PICK_IMAGE_REQUEST = 1;
    StorageReference storageReference;
    private ImageButton editBtn;
    // For test some funcs, modify this button later
    private ImageButton uploadBtn;
    Uri image;
    View rootLayout;
//    private SliderView sliderView;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootLayout = inflater.inflate(R.layout.fragment_account, container, false);

        editBtn = rootLayout.findViewById(R.id.edit_button);
        uploadBtn = rootLayout.findViewById(R.id.upload_button);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start EditActivity when the edit button is clicked
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        uploadBtn.setOnClickListener(v -> openFile());

        return rootLayout;
    }

    private void openFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
            uploadFile(data.getData());
            updateUser();
        }
    }

    private void uploadFile(Uri imageUri) {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child("images/" + UUID.randomUUID().toString());

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_SHORT).show();
                        // Call your method to display the uploaded image if needed
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
        else {
            Toast.makeText(getActivity(), "Please choose image", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUser() {
        //From uid of user update imgUrl
    }
}
