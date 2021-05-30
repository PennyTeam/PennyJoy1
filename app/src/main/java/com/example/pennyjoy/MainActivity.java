package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import Adapters.LegendAdapterForMain;
import Models.Auth;
import Models.Category;
import Models.GoalsList;
import Models.Good;
import Models.GoodsList;

public class MainActivity extends AppCompatActivity {
    private int mainRequest = 1;

    private Animation open_anim;
    private Animation close_anim;
    private Animation from_bottom_anim;
    private Animation to_bottom_anim;

    private boolean clicked = false;


    private FloatingActionButton settings;
    private FloatingActionButton settingsOfUser;
    private FloatingActionButton fabLogOut;
    private ListView listView;
    private ArrayList<LegendEntry> legendEntries;
    private ArrayList<Object> dataOfCategories;
    private LegendAdapterForMain legendAdapterForMain;
    private ArrayList<PieEntry> spendsList;


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


    //сам piechart и иконки категорий к нему
    private PieChart pieChart;
    private Legend pieLegend;

    private Drawable food;
    private Drawable travel;
    private Drawable transport;
    private Drawable car;
    private Drawable cloth;
    private Drawable loans;
    private Drawable investments;
    private Drawable goals;
    private Drawable house;
    private Drawable entertainment;
    private Drawable beauty_and_health;
    private Drawable shop;
    private Drawable smth;


    private int[] colors;

    private Auth auth ;
    private ArrayList<Good>actualGoodsArrayList;
    private GoodsList goodsList;

    private ArrayList<Double>sortedListWithCategories;



    private double totalSpends;

    private double salary;

    private Dialog dialog;

    private TextView lblMainInf,lblCategoriesLabels;
    private AppCompatButton closeAD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Intent intent=getIntent();
        if(intent.getExtras() != null && intent.getExtras().getBoolean("isMonthEnded")) {

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View myView = layoutInflater.inflate(R.layout.aler_dialog_in_main_tamplate, null, false);


            lblCategoriesLabels=(TextView)myView.findViewById(R.id.lblTop4CategoriesInMonth);
            lblMainInf=(TextView)myView.findViewById(R.id.lblMainInfAboutEndOfMonth);
            closeAD=(AppCompatButton)myView.findViewById(R.id.btnCloseAD);

            lblMainInf.setText(intent.getExtras().getString("mainInf"));
            lblCategoriesLabels.setText(intent.getExtras().getString("top4CategoriesLabels"));

            dialog = new Dialog(MainActivity.this);
            dialog.setContentView(myView);
            closeAD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        auth = Auth.getInstance();
        goodsList = GoodsList.getInstance();

        settings = findViewById(R.id.fabSettings);
        settingsOfUser = findViewById(R.id.fabSettingsOfUser);
        fabLogOut = findViewById(R.id.fabLogOut);
        listView = findViewById(R.id.listViewOfLegendInMain);


        open_anim = AnimationUtils.loadAnimation(this, R.anim.open_anim_for_fab1);
        close_anim = AnimationUtils.loadAnimation(this, R.anim.close_anim_for_fab1);
        from_bottom_anim = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim_for_fab);
        to_bottom_anim = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim_for_fab);



        totalSpends = auth.getCurrentUser().getTotalSpends();
        salary = auth.getCurrentUser().getSalary();
        spendsList = new ArrayList<>();



        food = getApplicationContext().getResources().getDrawable(R.drawable.fastfood);
        travel =getApplicationContext().getResources().getDrawable(R.drawable.travel);
        transport=getApplicationContext().getResources().getDrawable(R.drawable.transport);
        car=getApplicationContext().getResources().getDrawable(R.drawable.my_car);
        cloth=getApplicationContext().getResources().getDrawable(R.drawable.clothes);
        loans=getApplicationContext().getResources().getDrawable(R.drawable.loans);
        investments=getApplicationContext().getResources().getDrawable(R.drawable.investments);
        goals=getApplicationContext().getResources().getDrawable(R.drawable.goals);
        house =getApplicationContext().getResources().getDrawable(R.drawable.house);
        entertainment=getApplicationContext().getResources().getDrawable(R.drawable.entertainment);
        beauty_and_health=getApplicationContext().getResources().getDrawable(R.drawable.beauty_and_health);
        shop=getApplicationContext().getResources().getDrawable(R.drawable.shopping);
        smth=getApplicationContext().getResources().getDrawable(R.drawable.sort);

        pieChart = findViewById(R.id.pieChartOfSpendingsInMain);
        pieLegend = pieChart.getLegend();
        colors=new int[]{(R.color.food),( R.color.travelings),(R.color.transport),
                (R.color.car),(R.color.cloth),(R.color.debts),(R.color.investments),
                (R.color.goals),R.color.lodging, R.color.entertainment, R.color.health,
                R.color.goods, (R.color.smth)};


        actualGoodsArrayList = new ArrayList<>();
        for(Good g : goodsList){
            if(g.getActual()){
                actualGoodsArrayList.add(g);
            }
        }

        initDataForPieChart(actualGoodsArrayList);
    }

    public void moneyPigBtnClicked(View v) {
        if (clicked) {
            //закрываем fab menu
            clicked = true;
            setVisibility(clicked);
            setClickable(clicked);
            setAnimation(clicked);
            clicked = false;
        }

        Intent intent = new Intent(this, MoneyPigActivity.class);
        //очищаем все



        foodCount = 0;
        travelCount = 0;
        transportCount = 0;
        carCount = 0;
        clothCount = 0;
        loansCount = 0;
        investmentsCount = 0;
        goalsCount = 0;
        houseCount = 0;
        entertainmentCount = 0;
        beauty_and_healthCount = 0;
        shopCount = 0;
        smthCount = 0;


        spendsList.clear();

        //очищаем все
        startActivity(intent);
    }

    public void addGoodClicked(View v) {
        if (clicked) {
            //закрываем fab menu
            clicked = true;
            setVisibility(clicked);
            setClickable(clicked);
            setAnimation(clicked);
            clicked = false;
        }

        Intent intent = new Intent(this, AddGoodAndOtherActivity.class);
        //очищаем все



        foodCount = 0;
        travelCount = 0;
        transportCount = 0;
        carCount = 0;
        clothCount = 0;
        loansCount = 0;
        investmentsCount = 0;
        goalsCount = 0;
        houseCount = 0;
        entertainmentCount = 0;
        beauty_and_healthCount = 0;
        shopCount = 0;
        smthCount = 0;


        spendsList.clear();

        //очищаем все
        startActivity(intent);
    }

    public void chartBtnClicked(View v) {
        if (clicked) {
            //закрываем fab menu
            clicked = true;
            setVisibility(clicked);
            setClickable(clicked);
            setAnimation(clicked);
            clicked = false;
        }

        Intent intent = new Intent(this, ChartsActivity.class);
        //очищаем все



        foodCount = 0;
        travelCount = 0;
        transportCount = 0;
        carCount = 0;
        clothCount = 0;
        loansCount = 0;
        investmentsCount = 0;
        goalsCount = 0;
        houseCount = 0;
        entertainmentCount = 0;
        beauty_and_healthCount = 0;
        shopCount = 0;
        smthCount = 0;


        spendsList.clear();

        //очищаем все
        startActivity(intent);
    }

    //метод для главной кнопки настроек
    public void settingsClicked(View v) {
        setVisibility(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        if (clicked == false) {
            clicked = true;
        } else {
            clicked = false;
        }

    }

    //метод для настроек юзера
    public void settingsOfUserClicked(View v) {
        if (clicked) {
            //закрываем fab menu
            clicked = true;
            setVisibility(clicked);
            setClickable(clicked);
            setAnimation(clicked);
            clicked = false;
        }

        Intent intent = new Intent(this, SetUserActivity.class);
        //очищаем все



        foodCount = 0;
        travelCount = 0;
        transportCount = 0;
        carCount = 0;
        clothCount = 0;
        loansCount = 0;
        investmentsCount = 0;
        goalsCount = 0;
        houseCount = 0;
        entertainmentCount = 0;
        beauty_and_healthCount = 0;
        shopCount = 0;
        smthCount = 0;


        spendsList.clear();

        //очищаем все
        startActivity(intent);
    }


    //метод для logout
    public void LogOutBtnClicked(View v) {

        Intent intent = new Intent(this, SignInActivity.class);
        SharedPreferences sharedPreferences = getSharedPreferences(String.valueOf(R.string.APP_PREFERENCES), Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("loginOfTheAuthorizedUser").commit();

        auth.setCurrentCurrency(null);
        auth.setCurrentGoal(null);
        auth.setCurrentUser(null);

        goodsList.clear();
        GoalsList goalsList =GoalsList.getInstance();
        goalsList.clear();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        Toast.makeText(this, "Вы вышли", Toast.LENGTH_SHORT).show();
    }


    public void setVisibility(boolean clicked) {
        if (clicked == false) {
            settingsOfUser.setVisibility(View.VISIBLE);
            fabLogOut.setVisibility(View.VISIBLE);
        } else {
            settingsOfUser.setVisibility(View.INVISIBLE);
            fabLogOut.setVisibility(View.INVISIBLE);
        }

    }

    public void setAnimation(boolean clicked) {
        if (clicked == false) {
            settingsOfUser.startAnimation(from_bottom_anim);
            fabLogOut.startAnimation(from_bottom_anim);
            settings.startAnimation(open_anim);
        } else {
            settingsOfUser.startAnimation(to_bottom_anim);
            fabLogOut.startAnimation(to_bottom_anim);
            settings.startAnimation(close_anim);
        }

    }

    public void setClickable(boolean clicked) {
        if (clicked == false) {
            settingsOfUser.setClickable(true);
            fabLogOut.setClickable(true);

        } else {
            settingsOfUser.setClickable(false);
            fabLogOut.setClickable(false);
        }
    }

    public void initDataForPieChart(ArrayList<Good> goods) {

        sortTop4Categories(goods);

        if (goods != null && !goods.isEmpty()) {
            spendsList.add(new PieEntry((float) (sortedListWithCategories.get(0) / totalSpends) * 100, food, 0));
            spendsList.add(new PieEntry((float) (sortedListWithCategories.get(1) / totalSpends) * 100, travel, 1));
            spendsList.add(new PieEntry((float) (sortedListWithCategories.get(2)/ totalSpends) * 100, transport, 2));
            spendsList.add(new PieEntry((float) (sortedListWithCategories.get(3) / totalSpends) * 100, car, 3));
            spendsList.add(new PieEntry((float) (sortedListWithCategories.get(4) / totalSpends) * 100, cloth, 4));
            spendsList.add(new PieEntry((float) (sortedListWithCategories.get(5) / totalSpends) * 100, loans, 5));
            spendsList.add(new PieEntry((float) (sortedListWithCategories.get(6) / totalSpends) * 100, investments, 6));
            spendsList.add(new PieEntry((float) (sortedListWithCategories.get(7) / totalSpends) * 100, goals, 7));
            spendsList.add(new PieEntry((float) (sortedListWithCategories.get(8) / totalSpends) * 100, house, 8));
            spendsList.add(new PieEntry((float) (sortedListWithCategories.get(9) / totalSpends) * 100, entertainment, 9));
            spendsList.add(new PieEntry((float) (sortedListWithCategories.get(10) / totalSpends) * 100, beauty_and_health, 10));
            spendsList.add(new PieEntry((float) (sortedListWithCategories.get(11) / totalSpends) * 100, shop, 11));
            spendsList.add(new PieEntry((float) (sortedListWithCategories.get(12) / totalSpends) * 100, smth, 12));


        } else {
            colors[0] = R.color.ifEmpty;
            spendsList.add(new PieEntry(100, -1));
        }





        for (PieEntry pieEntry : spendsList) {

            if (pieEntry.getValue() <= 2.9f) {
                pieEntry.setIcon(null);
            }


        }

        PieDataSet pieDataSet = new PieDataSet(spendsList,"");









        pieDataSet.setColors(colors,getApplicationContext());




        PieData pieData = new PieData(pieDataSet);

        pieDataSet.setDrawValues(false);



        ;

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);




        pieChart.getLegend().setEnabled(false);
        pieChart.setHoleRadius(60f);
        pieChart.setHoleColor(Color.BLACK);

        pieChart.animateY(500, Easing.EaseInOutCubic);
        pieChart.animate();


        //
        legendEntries = new ArrayList<LegendEntry>();
        dataOfCategories = new ArrayList<>();
        for (int i=0;i<spendsList.size();i++){
            if(spendsList.get(i).getValue()>0) {
                legendEntries.add(pieLegend.getEntries()[i]);
                dataOfCategories.add(spendsList.get(i).getData());
            }
        }
        legendAdapterForMain = new LegendAdapterForMain(getApplicationContext(), R.layout.legent_template_for_main,legendEntries, dataOfCategories);
        listView.setAdapter(legendAdapterForMain);

    }

    public void sortTop4Categories(ArrayList<Good>actualGoodsArrayList){
        sortedListWithCategories = new ArrayList<>();

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
}