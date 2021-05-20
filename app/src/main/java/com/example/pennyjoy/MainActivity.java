package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private int mainRequest=1;

    private Animation open_anim;
    private Animation close_anim;
    private Animation from_bottom_anim;
    private Animation to_bottom_anim;

    private boolean clicked=false;


    private FloatingActionButton settings;
    private FloatingActionButton settingsOfUser;
    private FloatingActionButton fabLogOut;



    //PLEEEEEAASEEEEE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        settings=findViewById(R.id.fabSettings);
        settingsOfUser=findViewById(R.id.fabSettingsOfUser);
        fabLogOut=findViewById(R.id.fabLogOut);


        open_anim= AnimationUtils.loadAnimation(this,R.anim.open_anim_for_fab1);
        close_anim= AnimationUtils.loadAnimation(this,R.anim.close_anim_for_fab1);
        from_bottom_anim= AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim_for_fab);
        to_bottom_anim= AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim_for_fab);


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

    //метод для главной кнопки настроек
    public void settingsClicked(View v){
        setVisibility(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        if(clicked==false){
            clicked=true;
        }else{
            clicked=false;
        }

    }

    //метод для настроек юзера
    public void settingsOfUserClicked(View v){
        Intent intent = new Intent(this, SetUserActivity.class);
        startActivityForResult(intent,12099);
    }


    //метод для настроек валют
    public void LogOutBtnClicked(View v){

        Intent intent = new Intent(this, SignInActivity.class);
        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.APP_PREFERENCES), Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("loginOfTheAuthorizedUser").commit();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        Toast.makeText(this, "Вы вышли", Toast.LENGTH_SHORT).show();
    }




    public void setVisibility(boolean clicked){
        if(clicked==false){
            settingsOfUser.setVisibility(View.VISIBLE);
            fabLogOut.setVisibility(View.VISIBLE);
        }else{
            settingsOfUser.setVisibility(View.INVISIBLE);
            fabLogOut.setVisibility(View.INVISIBLE);
        }

    }

    public void setAnimation(boolean clicked){
        if(clicked==false){
            settingsOfUser.startAnimation(from_bottom_anim);
            fabLogOut.startAnimation(from_bottom_anim);
            settings.startAnimation(open_anim);
        }else{
            settingsOfUser.startAnimation(to_bottom_anim);
            fabLogOut.startAnimation(to_bottom_anim);
            settings.startAnimation(close_anim);
        }

    }

    public void setClickable(boolean clicked){
        if(clicked==false){
            settingsOfUser.setClickable(true);
            fabLogOut.setClickable(true);

        }else{
            settingsOfUser.setClickable(false);
            fabLogOut.setClickable(false);
        }
    }


}