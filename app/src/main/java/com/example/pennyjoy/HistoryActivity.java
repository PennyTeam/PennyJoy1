package com.example.pennyjoy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import Models.Auth;

import Adapters.GoodsAdapter;
import Models.Good;
import Models.GoodsList;
import Models.User;
import Providers.GoodProvider;
import Providers.UserProvider;


public class HistoryActivity extends AppCompatActivity {

    private ListView listViewHistoryOfGoods;
    private GoodsAdapter goodsAdapter;
    private Auth auth;
    private TextView lblEmpty;
    private GoodsList goodsList;
    private ArrayList<Good> goodsListWhichActualForHistory=new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        auth=Auth.getInstance();
        goodsList=GoodsList.getInstance();

        lblEmpty=findViewById(R.id.lblEmptyInHistory);



        listViewHistoryOfGoods = findViewById(R.id.listViewOfGoods);


        if(goodsList != null && !goodsList.isEmpty()) {
            //создаю лист, который содержит не удаленные из истории товары
            for (Good g : goodsList) {
                if (g.getActiveForHistory() == true) {
                    goodsListWhichActualForHistory.add(g);
                }
            }
        }
        else{
            lblEmpty.setVisibility(View.VISIBLE);
        }
        if(goodsListWhichActualForHistory != null && !goodsListWhichActualForHistory.isEmpty() ) {
            lblEmpty.setVisibility(View.GONE);
            goodsAdapter = new GoodsAdapter(getApplicationContext(), R.layout.good_template, goodsListWhichActualForHistory);
            listViewHistoryOfGoods.setAdapter(goodsAdapter);
        }else{
            lblEmpty.setVisibility(View.VISIBLE);
        }





    }

    public void clearHistoryClicked(View view){
        if(goodsListWhichActualForHistory != null && !goodsListWhichActualForHistory.isEmpty() ) {
            AlertDialog.Builder ad = new AlertDialog.Builder(view.getContext());
            ad.setTitle("Очистка истории");
            ad.setMessage("Вы уверены, что хотите очистить всю историю?");
            ad.setIcon(R.drawable.ic_baseline_warning_24);
            ad.setCancelable(false);
            ad.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Операция отменена", Toast.LENGTH_SHORT).show();
                }
            });
            ad.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    GoodProvider goodProvider = new GoodProvider();
                    for (Good g : goodsListWhichActualForHistory) {
                        g.setActiveForHistory(false);
                        goodProvider.updateGood(g);
                    }

                    goodsAdapter.clear();
                    lblEmpty.setVisibility(View.VISIBLE);



                    Toast.makeText(getApplicationContext(), "История очищена", Toast.LENGTH_SHORT).show();
                }
            });
            ad.show();
        }else{
            Toast.makeText(getApplicationContext(), "История пуста", Toast.LENGTH_SHORT).show();
        }
// is it ???

    }
}