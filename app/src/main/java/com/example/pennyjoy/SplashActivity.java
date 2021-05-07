package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import Interfaces.OnUserRetrievedListener;
import Models.Auth;
import Models.CategoryList;
import Models.CurrenciesList;
import Models.User;
import Providers.UserProvider;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Auth auth=Auth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        isUserExist();
    }







    //функция для проверки наличия юзера
    public void isUserExist(){

        sharedPreferences=getSharedPreferences(String.valueOf(R.string.APP_PREFERENCES),Context.MODE_PRIVATE);


        String login=sharedPreferences.getString("loginOfTheAuthorizedUser",null);

        if(login!=null){
            UserProvider provider=new UserProvider();
            OnUserRetrievedListener listener = new OnUserRetrievedListener() {
                @Override
                public void OnRetrieved(User user) {
                    if(login.equals(user.getLogin())&& user.getAccIsActive()==true){

                        //здесь вызываю маин при совпадении логина в сп с логином из бд
                        //и настраиваю флаги


                        auth.setCurrentUser(user);

                        CurrenciesList currenciesList=CurrenciesList.getInstance();
                        currenciesList.init();
                        SharedPreferences mySharedPreferences = getSharedPreferences(String.valueOf(R.string.APP_PREFERENCES), Context.MODE_PRIVATE);
                        int idOfCurrency=mySharedPreferences.getInt("idOfCurrency",-1);
                        if(idOfCurrency==-1) {
                            auth.setCurrentCurrency(currenciesList.getCurrencies().get(0));
                        }else{
                            auth.setCurrentCurrency(currenciesList.getCurrencies().get(idOfCurrency));
                        }

                        CategoryList categoryList=CategoryList.getInstance();
                        categoryList.init();




                    }
                    else{
                        sharedPreferences.edit().remove("loginOfTheAuthorizedUser").commit();
                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return;
                    }
                }
            };
            provider.getUserFromFirebaseByLogin(login,listener);
        }else{
            sharedPreferences.edit().remove("loginOfTheAuthorizedUser").commit();
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return;
        }
    }


}