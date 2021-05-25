package com.example.pennyjoy;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Adapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Adapters.GoodsAdapter;
import Adapters.LegendAdapter;
import Interfaces.OnGoodsRetrievedListener;
import Models.Auth;
import Models.Good;
import Models.GoodsList;
import Models.RoundedHorizontalBarChartRenderer;
import Providers.GoodProvider;

public class ChartsActivity extends AppCompatActivity {

    //переменные, отвечающие за тарты на категорию
    private double foodCount=0;
    private double travelCount=0;
    private double transportCount=0;
    private double carCount=0;
    private double clothCount=0;
    private double loansCount=0;
    private double investmentsCount=0;
    private double goalsCount=0;
    private double houseCount=0;
    private double entertainmentCount=0;
    private double beauty_and_healthCount=0;
    private double shopCount=0;
    private double smthCount=0;


    private PieChart pieChart;

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


    private TextView lblCurrency, lblPercentage, lblSpends;

    private Auth auth=Auth.getInstance();

    private ProgressBar spendsProgress;

    private DecimalFormat decimalFormat = new DecimalFormat( "#.###" );

    private ListView listView;
    private LegendAdapter legendAdapter;

    private GoodsList goodsList;
    private ArrayList<Good> goodsListWhichActual;

    private ArrayList<Double> percentageList;
    private ArrayList<Double> costList;

    private ArrayList<PieEntry> spendsList;
    private  Legend pieLegend;

    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        lblCurrency=findViewById(R.id.lblCurrencyOfSpends);
        lblPercentage=findViewById(R.id.lblPercentageOfSpends);
        lblSpends=findViewById(R.id.lblSpends);
        
        initDatePicker();

        spendsProgress=findViewById(R.id.progressOfSpends);

        colors=new int[]{(R.color.food),( R.color.travelings),(R.color.transport),
                (R.color.car),(R.color.cloth),(R.color.debts),(R.color.investments),
                (R.color.goals),R.color.lodging, R.color.entertainment, R.color.health,
                R.color.goods, (R.color.smth)};


        goodsList=GoodsList.getInstance();
        goodsListWhichActual=new ArrayList<>();
        for (Good g:goodsList) {
            if(g.getActual() == true){
                goodsListWhichActual.add(g);
            }
        }

        percentageList=new ArrayList<>();
        costList=new ArrayList<>();

        double totalSpends= auth.getCurrentUser().getTotalSpends();
        double salary=auth.getCurrentUser().getSalary();




        lblPercentage.setText(decimalFormat.format((totalSpends / salary) * 100) + "% из вашей вашей зарплаты потрачено ");
        //заполнение прогресс бара
        ObjectAnimator animation = ObjectAnimator.ofInt(spendsProgress, "progress", (int) ((totalSpends/salary)*100));
        animation.setDuration(450);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        //заполнение прогресс бара

        if((totalSpends/salary)*100 >=100){
            lblCurrency.setText("");
            lblSpends.setText("Вы потратили всю свою зарплату");
        }else {
            lblCurrency.setText(auth.getCurrentCurrency().getLabel());
            lblSpends.setText(decimalFormat.format(totalSpends )+ " из " + decimalFormat.format(salary));
        }

        // инициализирую иконки для категорий
        food=getApplicationContext().getResources().getDrawable(R.drawable.fastfood);
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

         //работа с круглым графиком
        pieChart = findViewById(R.id.pieChartOfSpendings);

         pieLegend=pieChart.getLegend();
        spendsList = new ArrayList<PieEntry>();


        initDataForPieChart(goodsListWhichActual);


        //конец работы с круглым графиком

    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month+=1;
                String date=makeDateString(year,month);
                String [] dateList=date.split("-");

                Date cDate = new Date();
                String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                String [] currentDateList=currentDate.split("-");



                if(Integer.parseInt(dateList[0]) <= Integer.parseInt(currentDateList[0])) {
                    if(!dateList[1].equals(currentDateList[1]) && Integer.parseInt(dateList[1]) < Integer.parseInt(currentDateList[1])) {
                        //System.out.println(date);
                        GoodProvider goodProvider = new GoodProvider();
                        goodProvider.getGoodsFromFirebaseByDate(auth.getCurrentUser().getKey(), new OnGoodsRetrievedListener() {
                            @Override
                            public void OnRetrieved(ArrayList<Good> goods) {
                                initDataForPieChart(goods);

                            }
                        }, date);
                    }else{
                        Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),
                                "Вы выбрали некоректную дату", Snackbar.LENGTH_SHORT).show();
                    }
                }else{
                    Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),
                            "Вы выбрали некоректную дату", Snackbar.LENGTH_SHORT).show();
                }
            }
        };

        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        int style= AlertDialog.THEME_HOLO_DARK;


        datePickerDialog=new DatePickerDialog(this,style,dateSetListener,year,month,day);
    }

    private String makeDateString(int year, int month) {
        String monthString =month+"";
        if(monthString.charAt(0) !='0'){
            monthString="0"+month;
        }


        return year+"-"+monthString;
    }

    public void initDataForPieChart(ArrayList<Good> goods){

        for (Good g:goods) {
            switch (g.getCategory()){
                case 0:
                    foodCount+=g.getCost();
                    break;
                case 1:
                    travelCount+=g.getCost();
                    break;
                case 2:
                    transportCount+=g.getCost();
                    break;
                case 3:
                    carCount+=g.getCost();
                    break;
                case 4:
                    clothCount+=g.getCost();
                    break;
                case 5:
                    loansCount+=g.getCost();
                    break;
                case 6:
                    investmentsCount+=g.getCost();
                    break;
                case 7:
                    goalsCount+=g.getCost();
                    break;
                case 8:
                    houseCount+=g.getCost();
                    break;
                case 9:
                    entertainmentCount+=g.getCost();
                    break;
                case 10:
                    beauty_and_healthCount+=g.getCost();
                    break;
                case 11:
                    shopCount+=g.getCost();
                    break;
                case 12:
                    smthCount+=g.getCost();
                    break;
            }


        }
        //добавляю элементы в costList
        costList.add(foodCount);
        costList.add(travelCount);
        costList.add(transportCount);
        costList.add(carCount);
        costList.add(clothCount);
        costList.add(loansCount);
        costList.add(investmentsCount);
        costList.add(goalsCount);
        costList.add(houseCount);
        costList.add(entertainmentCount);
        costList.add(beauty_and_healthCount);
        costList.add(shopCount);
        costList.add(smthCount);

        //добавляю элементы в percentageList
        percentageList.add((foodCount/auth.getCurrentUser().getTotalSpends())*100);
        percentageList.add((travelCount/auth.getCurrentUser().getTotalSpends())*100);
        percentageList.add((transportCount/auth.getCurrentUser().getTotalSpends())*100);
        percentageList.add((carCount/auth.getCurrentUser().getTotalSpends())*100);
        percentageList.add((clothCount/auth.getCurrentUser().getTotalSpends())*100);
        percentageList.add((loansCount/auth.getCurrentUser().getTotalSpends())*100);
        percentageList.add((investmentsCount/auth.getCurrentUser().getTotalSpends())*100);
        percentageList.add((goalsCount/auth.getCurrentUser().getTotalSpends())*100);
        percentageList.add((houseCount/auth.getCurrentUser().getTotalSpends())*100);
        percentageList.add((entertainmentCount/auth.getCurrentUser().getTotalSpends())*100);
        percentageList.add((beauty_and_healthCount/auth.getCurrentUser().getTotalSpends())*100);
        percentageList.add((shopCount/auth.getCurrentUser().getTotalSpends())*100);
        percentageList.add((smthCount/auth.getCurrentUser().getTotalSpends())*100);


        if(goods != null && !goods.isEmpty()) {
            spendsList.add(new PieEntry((float) (foodCount / auth.getCurrentUser().getTotalSpends()) * 100, food, 0));
            spendsList.add(new PieEntry((float) (travelCount / auth.getCurrentUser().getTotalSpends()) * 100, travel, 1));
            spendsList.add(new PieEntry((float) (transportCount / auth.getCurrentUser().getTotalSpends()) * 100, transport, 2));
            spendsList.add(new PieEntry((float) (carCount / auth.getCurrentUser().getTotalSpends()) * 100, car, 3));
            spendsList.add(new PieEntry((float) (clothCount / auth.getCurrentUser().getTotalSpends()) * 100, cloth, 4));
            spendsList.add(new PieEntry((float) (loansCount / auth.getCurrentUser().getTotalSpends()) * 100, loans, 5));
            spendsList.add(new PieEntry((float) (investmentsCount / auth.getCurrentUser().getTotalSpends()) * 100, investments, 6));
            spendsList.add(new PieEntry((float) (goalsCount / auth.getCurrentUser().getTotalSpends()) * 100, goals, 7));
            spendsList.add(new PieEntry((float) (houseCount / auth.getCurrentUser().getTotalSpends()) * 100, house, 8));
            spendsList.add(new PieEntry((float) (entertainmentCount / auth.getCurrentUser().getTotalSpends()) * 100, entertainment, 9));
            spendsList.add(new PieEntry((float) (beauty_and_healthCount / auth.getCurrentUser().getTotalSpends()) * 100, beauty_and_health, 10));
            spendsList.add(new PieEntry((float) (shopCount / auth.getCurrentUser().getTotalSpends()) * 100, shop, 11));
            spendsList.add(new PieEntry((float) (smthCount / auth.getCurrentUser().getTotalSpends()) * 100, smth, 12));






        }else{
            colors[0]=R.color.ifEmpty;
            spendsList.add(new PieEntry(100,-1));
        }






        listView = findViewById(R.id.listViewOfLegend);






        for (PieEntry pieEntry:spendsList) {

            if(pieEntry.getValue() <= 0.42f){
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


        ArrayList<LegendEntry> legendEntries=new ArrayList<>();
        ArrayList<Object> dataOfCategories=new ArrayList<Object>();
        for (int i=0;i<spendsList.size();i++){
            if(spendsList.get(i).getValue()>0) {
                legendEntries.add(pieLegend.getEntries()[i]);
                dataOfCategories.add(spendsList.get(i).getData());
            }
        }


        legendAdapter = new LegendAdapter(getApplicationContext(),R.layout.legend_template,  legendEntries,costList,percentageList,
                dataOfCategories);
        listView.setAdapter(legendAdapter);
        listView.setScrollContainer(false);


    }


    public void calendarClicked(View v){
        datePickerDialog.getDatePicker().findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
        datePickerDialog.show();
    }


}