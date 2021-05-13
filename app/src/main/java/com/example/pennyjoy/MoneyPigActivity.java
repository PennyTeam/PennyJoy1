package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MoneyPigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_pig);
    }

    public void addGoalClicked(View v){
        Intent intent=new Intent(this,AddGoalActivity.class);
        startActivity(intent);
    }
}