package com.example.pennyjoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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
        Intent intent = getIntent();
        int res = intent.getIntExtra("flagOfResultInFragments", 10000);
        fragmentHelper = new FragmentHelper();
        fragmentTimer = new FragmentTimer();


        addGoodFragment = new AddGoodFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentTimer).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentHelper).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, addGoodFragment).commit();



       BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_add_good);


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

if(res == 3){
    AlertDialog.Builder ad = new AlertDialog.Builder(this);
    ad.setTitle("Советник по покупкам").setMessage("Вам стоит отказаться от покупки в пользу целей!");
    ad.show();
}

    }
}