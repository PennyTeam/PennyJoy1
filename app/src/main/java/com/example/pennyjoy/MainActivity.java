package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private int mainRequest=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void moneyPigBtnClicked(View v){
        Intent intent=new Intent(this,MoneyPigActivity.class);
        startActivityForResult(intent,mainRequest);
    }

    public void addGoodClicked(View v){
        Intent intent=new Intent(this,AddGoodAndOtherActivity.class);
        startActivityForResult(intent,mainRequest);
    }

    public void chartBtnClicked(View v){
        Intent intent=new Intent(this,ChartsActivity.class);
        startActivityForResult(intent,mainRequest);
    }
}