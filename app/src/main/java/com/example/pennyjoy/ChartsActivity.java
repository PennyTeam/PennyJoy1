package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChartsActivity extends AppCompatActivity {

    private HorizontalBarChart horizontalBarChart;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

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

        ArrayList<PieEntry> students = new ArrayList<PieEntry>();
        students.add(new PieEntry(0.13f,food));
        students.add(new PieEntry(0.26f,travel));



        students.add(new PieEntry(0.39f,transport));

        students.add(new PieEntry(0.42f,car));
        students.add(new PieEntry(0.55f,cloth));
        students.add(new PieEntry(0.68f,loans));
        students.add(new PieEntry(0.81f,investments));
        students.add(new PieEntry(0.94f,goals));
        students.add(new PieEntry(1.07f,house));
        students.add(new PieEntry(1.20f,entertainment));
        students.add(new PieEntry(1.33f,beauty_and_health));
        students.add(new PieEntry(1.46f,shop));







        students.add(new PieEntry(1.59f,smth));


        for (PieEntry pieEntry:students) {

            if(pieEntry.getValue() <= 0.42f){
                pieEntry.setIcon(null);
            }


        }

        PieDataSet pieDataSet = new PieDataSet(students,"Students");







         colors=new int[]{(R.color.food),( R.color.travelings),(R.color.transport),
                (R.color.car),(R.color.cloth),(R.color.debts),(R.color.investments),
                (R.color.goals),R.color.lodging, R.color.entertainment, R.color.health,
                R.color.goods, (R.color.smth)};



        pieDataSet.setColors(colors,getApplicationContext());




        PieData pieData = new PieData(pieDataSet);

        pieDataSet.setDrawValues(false);



       ;

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);

        pieChart.setCenterText("Total Questions 5" );
        pieChart.setCenterTextSize(14f);
        pieChart.setCenterTextColor(Color.WHITE);


        pieChart.getLegend().setEnabled(false);
        pieChart.setHoleRadius(60f);
        pieChart.setHoleColor(Color.BLACK);

        pieChart.animateY(500, Easing.EaseInOutCubic);
        pieChart.animate();

        //конец работы с круглым графиком


        //работа с горизонталиным графиком

        horizontalBarChart=findViewById(R.id.barChartOfSpendings);

        showHorizontalBarChart();





        //конец работы с горизонтальным графиком
    }

    private void showHorizontalBarChart(){
        ArrayList<Double> spendsList=new ArrayList<>();
        ArrayList<BarEntry> entries=new ArrayList<>();

        String title="Your spends";

        //input data
        for(int i = 0; i < 6; i++){
            spendsList.add(i * 100.1);

        }

        //fit the data into bar
        for (int i = 0; i < spendsList.size(); i++) {
            BarEntry barEntry = new BarEntry(i, spendsList.get(i).floatValue()+300);
            entries.add(barEntry);
        }


        BarDataSet barDataSet=new BarDataSet(entries,title);
        barDataSet.setColors(colors,getApplicationContext());








        BarData data = new BarData(barDataSet);
        horizontalBarChart.setData(data);

        horizontalBarChart.getXAxis().setSpaceMax(1);
        horizontalBarChart.animateY(500, Easing.EaseInOutCubic);






        YAxis yAxisRight = horizontalBarChart.getAxisRight();
        YAxis yAxisLeft = horizontalBarChart.getAxisLeft();
        XAxis xAxis = horizontalBarChart.getXAxis();
        Legend l = horizontalBarChart.getLegend();

        yAxisLeft.setTextColor(Color.WHITE);
        yAxisRight.setTextColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        l.setTextColor(Color.WHITE);

        xAxis.setEnabled(false);

        YAxis yAxis = horizontalBarChart.getAxisLeft();
        yAxis.setEnabled(false);
        YAxis yAxis2 = horizontalBarChart.getAxisRight();
        yAxis2.setEnabled(false);



        horizontalBarChart.getAxisLeft().setDrawLabels(false);
        horizontalBarChart.getAxisRight().setDrawLabels(false);
        horizontalBarChart.getXAxis().setDrawLabels(false);

        horizontalBarChart.getAxisLeft().setDrawGridLines(false);
        horizontalBarChart.getXAxis().setDrawGridLines(false);
        horizontalBarChart.getAxisRight().setDrawGridLines(false);
        horizontalBarChart.getDescription().setEnabled(false);

        horizontalBarChart.setScaleY(0.45f);


        horizontalBarChart.setPinchZoom(false);
        horizontalBarChart.setDoubleTapToZoomEnabled(false);


        l.setEnabled(false);
    }
}