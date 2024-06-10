package com.example.tender.fragments;


import android.content.Intent;
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

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

//import com.smarteist.autoimageslider.IndicatorAnimations;
//import com.smarteist.autoimageslider.SliderAnimations;
//import com.smarteist.autoimageslider.SliderView;
import com.example.tender.R;
import com.example.tender.activities.EditProfileActivity;
import com.example.tender.activities.SplashscreenActivity;
//import com.thecode.tinderclone.adapters.SliderAdapter;

public class AccountFragment extends Fragment {

    private ImageButton editBtn;
    // For test some funcs, modify this button later
    private ImageButton uploadBtn;
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
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start EditActivity when the edit button is clicked
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TExt","Called");
                NavHostFragment.findNavController(AccountFragment.this)
                        .navigate(R.id.action_chatListFragment_to_chatBoxFragment);
            }
        });

        return rootLayout;
    }

}
