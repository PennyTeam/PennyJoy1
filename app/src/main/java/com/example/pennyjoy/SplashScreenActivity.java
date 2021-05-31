package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import Interfaces.OnGoalRetrievedListener;
import Interfaces.OnGoodsRetrievedListener;
import Interfaces.OnUserRetrievedListener;
import Interfaces.OnUsersCurrencyRetrievedListener;
import Models.Auth;
import Models.CategoryList;
import Models.CurrenciesList;
import Models.Currency;
import Models.Goal;
import Models.GoalsList;
import Models.Good;
import Models.GoodsList;
import Models.User;
import Providers.GoalProvider;
import Providers.GoodProvider;
import Providers.UserProvider;
import Providers.UsersCurrencyProvider;

public class SplashScreenActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Auth auth=Auth.getInstance();
    private GoalsList goalsList = GoalsList.getInstance();
    private GoodsList goodsList=GoodsList.getInstance();



    //переменные, отвечающие за тарты на категорию
    private double foodCount = 0;
    private double travelCount = 0;
    private double transportCount = 0;
    private double carCount = 0;
    private double clothCount = 0;
    private double loansCount = 0;
    private double investmentsCount = 0;
    private double goalsCount = 0;
    private double houseCount = 0;
    private double entertainmentCount = 0;
    private double beauty_and_healthCount = 0;
    private double shopCount = 0;
    private double smthCount = 0;

    private ArrayList<Double>sortedListWithCategories;

    private ArrayList<Good> actualGoodsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // убедитесь, что вызываете до super.onCreate()
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);

        isUserExist();
    }



    //функция для проверки наличия юзера
    public void isUserExist(){

        sharedPreferences=getSharedPreferences(String.valueOf(R.string.APP_PREFERENCES), Context.MODE_PRIVATE);


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
                                    //сохраняю текущую цель в auth, чтобы она быстрее отображалась
                                    auth.setCurrentGoal(goalList.get(0));
                                    goalsList.addAll(goalList);

                                }

                                //заполняем лист с товарами
                                GoodProvider goodProvider=new GoodProvider();

                                goodProvider.getGoodsFromFirebase(user.getKey(), new OnGoodsRetrievedListener() {
                                    @Override
                                    public void OnRetrieved(ArrayList<Good> goods) {
                                        if(goods != null && !goods.isEmpty()){
                                            goodsList.addAll(goods);
                                        }
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



                                                    Date cDate = new Date();
                                                    String currentDate = new SimpleDateFormat("yyyy-MM").format(cDate);

                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                                    if(!auth.getCurrentUser().getUsersCurrentDate().equals(currentDate)){
                                                        int efficiency= countEfficiency();
                                                        String inf="";
                                                        String categoriesLabels="";
                                                        if(efficiency>=100){
                                                            inf=getResources().getString(R.string.goodJobForMonth);
                                                        }else{
                                                            inf=getResources().getString(R.string.badJobForMonth);
                                                        }
                                                        categoriesLabels=initCategoriesLabels();
                                                        intent.putExtra("isMonthEnded",true);
                                                        intent.putExtra("mainInf",inf);
                                                        intent.putExtra("top4CategoriesLabels",categoriesLabels);

                                                        //очищаем юзера
                                                        auth.getCurrentUser().setEfficiency(0);
                                                        auth.getCurrentUser().setTotalSpends(0);
                                                        auth.getCurrentUser().setUsersCurrentDate(currentDate);

                                                        provider.updateUser(auth.getCurrentUser());

                                                        //очищаем товары
                                                        for (Good g:goodsList){
                                                            if(g.getActual()){
                                                                g.setActual(false);
                                                                goodProvider.updateGood(g);
                                                            }
                                                        }


                                                    }else{
                                                        intent.putExtra("isMonthEnded",false);
                                                    }

                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        };
                        goalProvider.getGoalsFromFirebase(user.getKey(),listener1);

                    }
                    else{
                        sharedPreferences.edit().remove("loginOfTheAuthorizedUser").commit();
                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);

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

            startActivity(intent);
            return;
        }
    }

    public int countEfficiency(){

        int efficiency=auth.getCurrentUser().getEfficiency();

        if(auth.getCurrentGoal() != null && (auth.getCurrentGoal().getFullness()/auth.getCurrentGoal().getCost())*100
                >= 70){
            efficiency+=20;
        }

        actualGoodsArrayList = new ArrayList<>();
        for(Good g : goodsList){
            if(g.getActual()){
                actualGoodsArrayList.add(g);
            }
        }

        initSpendsOfCategories();
        if(investmentsCount != 0){
            if(investmentsCount > entertainmentCount){
                efficiency+=100;
            }
        }

        return efficiency;

    }

    public void initSpendsOfCategories(){
        for (Good g : actualGoodsArrayList) {
            switch (g.getCategory()) {
                case 0:
                    foodCount += g.getCost();
                    break;
                case 1:
                    travelCount += g.getCost();
                    break;
                case 2:
                    transportCount += g.getCost();
                    break;
                case 3:
                    carCount += g.getCost();
                    break;
                case 4:
                    clothCount += g.getCost();
                    break;
                case 5:
                    loansCount += g.getCost();
                    break;
                case 6:
                    investmentsCount += g.getCost();
                    break;
                case 7:
                    goalsCount += g.getCost();
                    break;
                case 8:
                    houseCount += g.getCost();
                    break;
                case 9:
                    entertainmentCount += g.getCost();
                    break;
                case 10:
                    beauty_and_healthCount += g.getCost();
                    break;
                case 11:
                    shopCount += g.getCost();
                    break;
                case 12:
                    smthCount += g.getCost();
                    break;
            }

        }
    }
    public void sortTop4Categories(ArrayList<Good>actualGoodsArrayList){


        sortedListWithCategories = new ArrayList<>();



        sortedListWithCategories.add(foodCount);
        sortedListWithCategories.add(travelCount);
        sortedListWithCategories.add(transportCount);
        sortedListWithCategories.add(carCount);
        sortedListWithCategories.add(clothCount);
        sortedListWithCategories.add(loansCount);
        sortedListWithCategories.add(investmentsCount);
        sortedListWithCategories.add(goalsCount);
        sortedListWithCategories.add(houseCount);
        sortedListWithCategories.add(entertainmentCount);
        sortedListWithCategories.add(beauty_and_healthCount);
        sortedListWithCategories.add(shopCount);
        sortedListWithCategories.add(smthCount);



        ArrayList<Double> listWithBetaCategoriesCount = new ArrayList<>();
        for (double d : sortedListWithCategories) {
            listWithBetaCategoriesCount.add(d);
        }

        Collections.sort(listWithBetaCategoriesCount);
        Collections.reverse(listWithBetaCategoriesCount);




        ArrayList<Integer> num = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < sortedListWithCategories.size(); j++) {
                if(listWithBetaCategoriesCount.get(i).doubleValue() == 0.0){
                    i++;
                    break;
                }
                else if (listWithBetaCategoriesCount.get(i).doubleValue() == sortedListWithCategories.get(j).doubleValue()) {
                    num.add(j);

                }
            }
        }




        Collections.sort(num);




        for (int i=0;i<num.size();i++) {
            for(int j=0;j<sortedListWithCategories.size();j++){
                if(i<num.size()) {
                    if (num.get(i) != j) {
                        sortedListWithCategories.set(j, 0.0);
                    } else {
                        i++;
                    }
                }else{
                    for(;j<sortedListWithCategories.size();j++) {
                        sortedListWithCategories.set(j, 0.0);
                    }
                }
            }
        }



    }

    public String initCategoriesLabels(){
        String res="";
        sortTop4Categories(actualGoodsArrayList);

        for (int i=0;i<sortedListWithCategories.size();i++) {
            if(sortedListWithCategories.get(i).doubleValue() !=0){
                switch (i) {
                    case 0:
                        res += "-Продукты\n";
                        break;
                    case 1:

                        res += "-Путешествия\n";
                        break;
                    case 2:

                        res += "-Транспорт\n";
                        break;
                    case 3:

                        res += "-Автомобиль\n";
                        break;
                    case 4:

                        res += "`Одежда\n";
                        break;
                    case 5:

                        res += "-Долги\n";
                        break;
                    case 6:

                        res += "-Инвестиции\n";
                        break;
                    case 7:
                        res += "-Цели\n";
                        break;
                    case 8:

                        res += "-Жилье\n";
                        break;
                    case 9:
                        res += "-Развлечения и досуг\n";
                        break;
                    case 10:

                        res += "-Красота и здоровье\n";
                        break;
                    case 11:

                        res = "-Покупки\n";
                        break;
                    case 12:

                        res += "-Прочее\n";
                        break;
                }
            }
        }

        return res;
    }
}