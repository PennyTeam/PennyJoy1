package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;

import Interfaces.OnGoalRetrievedListener;
import Interfaces.OnUserRetrievedListener;
import Interfaces.OnUsersCurrencyRetrievedListener;
import Models.Auth;
import Models.CategoryList;
import Models.CurrenciesList;
import Models.Currency;
import Models.Goal;
import Models.GoalsList;
import Models.User;
import Providers.GoalProvider;
import Providers.UserProvider;
import Providers.UsersCurrencyProvider;

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
            GoalProvider goalProvider = new GoalProvider();
            OnUserRetrievedListener listener = new OnUserRetrievedListener() {
                @Override
                public void OnRetrieved(User user) {
                    if(login.equals(user.getLogin())&& user.getAccIsActive()==true){

                        //здесь вызываю маин при совпадении логина в сп с логином из бд
                        //и настраиваю флаги


                        auth.setCurrentUser(user);

                        OnGoalRetrievedListener listener1 = new OnGoalRetrievedListener() {
                            @Override
                            public void onGoalRetrieved(ArrayList<Goal> goalList) {
                                if(goalList !=null && !goalList.isEmpty()) {
                                    GoalsList goalsList = GoalsList.getInstance();
                                    auth.setCurrentGoal(goalList.get(0));
                                    for (int i = 0; i < goalList.size(); i++) {
                                        if (goalList.get(i) != auth.getCurrentGoal()) {
                                            goalsList.add(goalList.get(i));
                                        }
                                    }
                                }

                            }
                        };
                        goalProvider.getGoalsFromFirebase(user.getKey(),listener1);


                        CurrenciesList currenciesList=CurrenciesList.getInstance();
                        currenciesList.init();
                        SharedPreferences mySharedPreferences = getSharedPreferences(String.valueOf(R.string.APP_PREFERENCES), Context.MODE_PRIVATE);
                        int idOfCurrency=mySharedPreferences.getInt("idOfCurrency",-1);
                        if(idOfCurrency==-1) {
                            auth.setCurrentCurrency(currenciesList.getCurrencies().get(0));
                        }

                        else{
                            UsersCurrencyProvider usersCurrencyProvider=new UsersCurrencyProvider();
                            usersCurrencyProvider.getUsersCurrencyFromFirebase(auth.getCurrentUser().getKey(), new OnUsersCurrencyRetrievedListener() {
                                @Override
                                public void OnRetrieved(Currency currency) {
                                    auth.setCurrentCurrency(currency);
                                    CategoryList categoryList=CategoryList.getInstance();
                                    categoryList.init();

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            });
                        }


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
        }

        else{
            sharedPreferences.edit().remove("loginOfTheAuthorizedUser").commit();
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return;
        }
    }


}