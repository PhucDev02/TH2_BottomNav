package com.example.th2_bottomnav;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.th2_bottomnav.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton=findViewById(R.id.add_fab);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        floatingActionButton.setOnClickListener(view ->{
            SongDetailFragment songDetailFragment = new SongDetailFragment();
            songDetailFragment.show(getSupportFragmentManager(), "SongDetailFragment");
            songDetailFragment.AssignList(listFragment);
        });
    }

    ListFragment listFragment=new ListFragment();
    FavoriteFragment favoriteFragment = new FavoriteFragment();
    SearchFragment searchFragment = new SearchFragment();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();
        if(itemId ==R.id.navigation_home)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,listFragment).commit();
            return true;
        }
        if(itemId ==R.id.navigation_dashboard)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,favoriteFragment).commit();
return true;
        }
        if(itemId ==R.id.navigation_notifications)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,searchFragment).commit();
        return true;
        }
        return false;
    }
}