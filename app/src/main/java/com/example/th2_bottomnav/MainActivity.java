package com.example.th2_bottomnav;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity{

    FloatingActionButton floatingActionButton;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);

        viewPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        // It is used to join TabLayout with ViewPager.
        tabLayout.setupWithViewPager(viewPager);

        floatingActionButton=findViewById(R.id.add_fab);

        floatingActionButton.setOnClickListener(view ->{
            SongDetailFragment songDetailFragment = new SongDetailFragment();
            songDetailFragment.show(getSupportFragmentManager(), "SongDetailFragment");
            songDetailFragment.AssignList(ListFragment.instance);
        });
    }

    ListFragment listFragment=new ListFragment();
    FavoriteFragment favoriteFragment = new FavoriteFragment();
    SearchFragment searchFragment = new SearchFragment();




}