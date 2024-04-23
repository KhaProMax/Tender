package com.example.tender.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ListView;
import android.widget.Toast;

import com.example.tender.adapters.ProfileImageAdapter;
import com.example.tender.models.ProfileImage;
import com.example.tender.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class EditProfileActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private RecyclerView rcvImage;
    private ProfileImageAdapter mImageAdapter;
    private ImageButton saveProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        rcvImage = findViewById(R.id.recycler_image);
        mImageAdapter = new ProfileImageAdapter(this);

        EditText editZodiac = findViewById(R.id.editZodiac);
        EditText editStudy = findViewById(R.id.editStudy);
        EditText editCharacter = findViewById(R.id.editCharacter);
        EditText editNickname = findViewById(R.id.editNickname);
        EditText editLiving = findViewById(R.id.editLiving);
        EditText editWork = findViewById(R.id.editWork);
        saveProfileBtn = findViewById(R.id.buttonSave);
        backBtn = findViewById(R.id.buttonBack);
        // Set onClickListener for the edit profile button
        saveProfileBtn.setOnClickListener(v -> saveProfile());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcvImage.setLayoutManager(gridLayoutManager);

        mImageAdapter.setData(getListImage());
        rcvImage.setAdapter(mImageAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Phương thức này được gọi khi người dùng nhấn nút "Lưu"
    private void saveProfile() {
        // Thực hiện các hành động để lưu hồ sơ, ví dụ: cập nhật vào cơ sở dữ liệu
        Toast.makeText(this, "Profile saved successfully", Toast.LENGTH_SHORT).show();
    }

    private List<ProfileImage> getListImage() {
        List<ProfileImage> list = new ArrayList<>();
        list.add(new ProfileImage(R.drawable.profile1));
        list.add(new ProfileImage(R.drawable.profile2));
        list.add(new ProfileImage(R.drawable.profile3));

        list.add(new ProfileImage(R.drawable.profile4));
        list.add(new ProfileImage(R.drawable.profile1));
        list.add(new ProfileImage(R.drawable.profile2));

        list.add(new ProfileImage(R.drawable.profile3));
        list.add(new ProfileImage(R.drawable.profile4));
        list.add(new ProfileImage(R.drawable.profile1));

        return list;
    }
}
