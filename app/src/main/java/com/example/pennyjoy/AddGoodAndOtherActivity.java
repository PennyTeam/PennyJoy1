package com.example.pennyjoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pennyjoy.Fragments.AddGoodFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.pennyjoy.Fragments.FragmentHelper;
import com.example.pennyjoy.Fragments.FragmentTimer;

public class AddGoodAndOtherActivity extends AppCompatActivity {
    private FragmentHelper fragmentHelper;
    private FragmentTimer fragmentTimer;
    private AddGoodFragment addGoodFragment;

    private TextView lblResult;
    private ImageView imageOfResult;

    private Dialog dialogFromQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_good_and_other);
        Intent intent = getIntent();

        if(intent.getExtras() != null && intent.getExtras().getBoolean("fromQuiz")) {
            int resultOfQuiz = intent.getIntExtra("flagOfResult", 1000);

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View myView = layoutInflater.inflate(R.layout.alert_dialog_result, null, false);


            lblResult = (TextView) myView.findViewById(R.id.lblResult);
            imageOfResult= (ImageView) myView.findViewById(R.id.image_of_result);


            switch (resultOfQuiz) {
                case 1:
                    imageOfResult.setImageDrawable(getResources().getDrawable(R.drawable.check_for_result));
                    lblResult.setText(R.string.yes);
                    break;
                case -1:
                    imageOfResult.setImageDrawable(getResources().getDrawable(R.drawable.cross_for_result));
                    lblResult.setText(R.string.no);
                    break;
                case -2:
                    imageOfResult.setImageDrawable(getResources().getDrawable(R.drawable.cross_for_result));
                    lblResult.setText(R.string.mb_no);
                    break;
                case 2:
                    imageOfResult.setImageDrawable(getResources().getDrawable(R.drawable.check_for_result));
                    lblResult.setText(R.string.mb_yes);
                    break;
            }
            dialogFromQuiz = new Dialog(AddGoodAndOtherActivity.this);
            dialogFromQuiz.setContentView(myView);
            dialogFromQuiz.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogFromQuiz.show();
        }

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


    }
}