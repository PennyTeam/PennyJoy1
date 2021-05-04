package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import Interfaces.OnCategoriesRetrievedListener;
import Interfaces.OnUserRetrievedListener;
import Models.Auth;
import Models.Category;
import Models.CategoryList;
import Models.CurrenciesList;
import Models.User;
import Providers.CategoryProvider;
import Providers.UserProvider;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        isUserExist();
    }





    OnCategoriesRetrievedListener categoriesRetrievedListener=new OnCategoriesRetrievedListener() {
        @Override
        public void OnCategoriesRetrieved(CategoryList categoryList) {
            CategoryProvider categoryProvider=new CategoryProvider();
            if(categoryList.getCategories().size() == 0){
                categoryList.init();
                for (Category category: categoryList.getCategories()){
                    categoryProvider.addCategory(category);
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else{
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }


        }
    };

    //функция для проверки наличия юзера
    public void isUserExist(){

        sharedPreferences=getSharedPreferences(String.valueOf(R.string.APP_PREFERENCES),Context.MODE_PRIVATE);


        String login=sharedPreferences.getString("loginOfTheAuthorizedUser",null);

        if(login!=null){
            UserProvider provider=new UserProvider();
            OnUserRetrievedListener listener = new OnUserRetrievedListener() {
                @Override
                public void OnRetrieved(User user) {
                    if(login.equals(user.getLogin())){
                        //Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        //здесь вызываю маин при совпадении логина в сп с логином из бд
                        //и настраиваю флаги

                        Auth auth=Auth.getInstance();
                        auth.setCurrentUser(user);

                        CurrenciesList currenciesList=new CurrenciesList();
                        currenciesList.init();
                        SharedPreferences mySharedPreferences = getSharedPreferences(String.valueOf(R.string.APP_PREFERENCES), Context.MODE_PRIVATE);
                        int idOfCurrency=mySharedPreferences.getInt("idOfCurrency",-1);
                        if(idOfCurrency==-1) {
                            auth.setCurrentCurrency(currenciesList.getCurrencies().get(0));
                        }else{
                            auth.setCurrentCurrency(currenciesList.getCurrencies().get(idOfCurrency));
                        }

                        CategoryProvider categoryProvider=new CategoryProvider();
                        categoryProvider.getCategoriesFromFirebase(auth.getCurrentUser().getKey(),categoriesRetrievedListener);


                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        //startActivity(intent);


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