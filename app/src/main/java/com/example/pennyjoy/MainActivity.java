package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Models.Auth;
import Models.Category;
import Models.CategoryList;
import Providers.CategoryProvider;

public class MainActivity extends AppCompatActivity {
    private int mainRequest=1;

    private Animation open_anim;
    private Animation close_anim;
    private Animation from_bottom_anim;
    private Animation to_bottom_anim;

    private boolean clicked=false;


    private FloatingActionButton settings;
    private FloatingActionButton settingsOfUser;
    private FloatingActionButton settingsOfCategory;
    private FloatingActionButton settingsOfCurrencies;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        settings=findViewById(R.id.fabSettings);
        settingsOfUser=findViewById(R.id.fabSettingsOfUser);
        settingsOfCategory=findViewById(R.id.fabSettingsOfCategory);
        settingsOfCurrencies=findViewById(R.id.fabSettingsOfCurrency);


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
    //метод для настроек категорий
    public void settingsOfCategoryClicked(View v){
        //здесб сделал добавление
        Auth auth=Auth.getInstance();
        CategoryList categoryList=CategoryList.getInstance();
        Category category=new Category(categoryList.getCategories().size(), "defGoodThatIMade",auth.getCurrentUser().getKey());

        categoryList.addCategory(category);
        CategoryProvider categoryProvider=new CategoryProvider();
        //надо апдейт юзать вместо обычного адд
        categoryProvider.addCategory(category);
    }

    //метод для настроек валют
    public void settingsOfCurrenciesClicked(View v){

    }




    public void setVisibility(boolean clicked){
        if(clicked==false){
            settingsOfUser.setVisibility(View.VISIBLE);
            settingsOfCategory.setVisibility(View.VISIBLE);
            settingsOfCurrencies.setVisibility(View.VISIBLE);
        }else{
            settingsOfUser.setVisibility(View.INVISIBLE);
            settingsOfCategory.setVisibility(View.INVISIBLE);
            settingsOfCurrencies.setVisibility(View.INVISIBLE);
        }

    }

    public void setAnimation(boolean clicked){
        if(clicked==false){
            settingsOfUser.startAnimation(from_bottom_anim);
            settingsOfCategory.startAnimation(from_bottom_anim);
            settingsOfCurrencies.startAnimation(from_bottom_anim);
            settings.startAnimation(open_anim);
        }else{
            settingsOfUser.startAnimation(to_bottom_anim);
            settingsOfCategory.startAnimation(to_bottom_anim);
            settingsOfCurrencies.startAnimation(to_bottom_anim);
            settings.startAnimation(close_anim);
        }

    }

    public void setClickable(boolean clicked){
        if(clicked==false){
            settingsOfUser.setClickable(true);
            settingsOfCategory.setClickable(true);
            settingsOfCurrencies.setClickable(true);

        }else{
            settingsOfUser.setClickable(false);
            settingsOfCategory.setClickable(false);
            settingsOfCurrencies.setClickable(false);
        }
    }


}