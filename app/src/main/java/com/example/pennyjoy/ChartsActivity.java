package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class ChartsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        PieChart pieChart = findViewById(R.id.pieChartOfSpendings);

        ArrayList<PieEntry> students = new ArrayList<PieEntry>();
        students.add(new PieEntry(16, "2015"));
        students.add(new PieEntry(90, "2016"));
        students.add(new PieEntry(20, "2020"));
        students.add(new PieEntry(100, "2021"));
        students.add(new PieEntry(120, "2022"));
        students.add(new PieEntry(130, "2023"));

        for (PieEntry pieEntry:students) {

            if(pieEntry.getValue() == 0){
                pieEntry.setLabel("");
            }
        }

        PieDataSet pieDataSet = new PieDataSet(students,"Students");



        int[] colors=new int[]{R.color.food,R.color.travelings, R.color.transport,R.color.car
        ,R.color.cloth, R.color.debts, R.color.investments, R.color.goals,
                R.color.lodging,R.color.entertainment, R.color.health,R.color.goods
                ,R.color.smth};

        pieDataSet.setColors(colors,getApplicationContext());



        PieData pieData = new PieData(pieDataSet);

        pieDataSet.setDrawValues(false);


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

    }
}