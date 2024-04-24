package com.example.tender.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.tender.R;
//import com.example.tender.Utils;
import com.example.tender.adapters.ViewPagerAdapter;
//import com.example.tender.models.TinderCard;
import com.example.tender.fragments.AccountFragment;
import com.example.tender.fragments.ChatFragment;
import com.example.tender.fragments.FeedFragment;
import com.example.tender.fragments.SwipeViewFragment;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);

        ArrayList<Fragment> fragList = new ArrayList<>();
        fragList.add(new SwipeViewFragment());
        fragList.add(new FeedFragment());
        fragList.add(new ChatFragment());
        fragList.add(new AccountFragment());
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(fragList, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    if (menuItem.getItemId() == R.id.fire) {
                        viewPager.setCurrentItem(0);
                    }  else if (menuItem.getItemId() == R.id.feed) {
                        viewPager.setCurrentItem(1);
                    }
                     else if (menuItem.getItemId() == R.id.chat) {
                        viewPager.setCurrentItem(2);
                    } else if (menuItem.getItemId() == R.id.account) {
                        viewPager.setCurrentItem(3);
                    }
                    return true;
            }
        });
    }
}