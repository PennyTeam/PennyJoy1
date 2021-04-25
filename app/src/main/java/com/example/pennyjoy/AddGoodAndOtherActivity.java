package com.example.pennyjoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.MenuItem;

import com.example.pennyjoy.Fragments.AddGoodFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.pennyjoy.Fragments.FragmentHelper;
import com.example.pennyjoy.Fragments.FragmentTimer;

public class AddGoodAndOtherActivity extends AppCompatActivity {
    private FragmentHelper fragmentHelper;
    private FragmentTimer fragmentTimer;
    private AddGoodFragment addGoodFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_good_and_other);
        fragmentHelper = new FragmentHelper();
        fragmentTimer = new FragmentTimer();


        addGoodFragment = new AddGoodFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, addGoodFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentHelper).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentTimer).commit();

       BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = addGoodFragment;
                switch (item.getItemId()){
                    case R.id.nav_add_good:
                        fragment = addGoodFragment;
                        break;
                    case R.id.nav_adviser:
                        fragment = fragmentHelper;
                        break;
                    case R.id.nav_timer:
                        fragment = fragmentTimer;
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                return true;
            }
        });
    }
}