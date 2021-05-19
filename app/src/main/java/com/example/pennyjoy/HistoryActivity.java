package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;




import Models.Auth;

import Adapters.GoodsAdapter;
import Models.GoodsList;


public class HistoryActivity extends AppCompatActivity {

    private ListView listViewHistoryOfGoods;
    private GoodsAdapter goodsAdapter;
    private Auth auth;
    private TextView lblEmpty;
    private GoodsList goodsList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        auth=Auth.getInstance();
        goodsList=GoodsList.getInstance();

        lblEmpty=findViewById(R.id.lblEmptyInHistory);



        listViewHistoryOfGoods = findViewById(R.id.listViewOfGoods);


        if(goodsList != null && !goodsList.isEmpty()){
            lblEmpty.setVisibility(View.GONE);
            goodsAdapter = new GoodsAdapter(getApplicationContext(), R.layout.good_template, goodsList);
            listViewHistoryOfGoods.setAdapter(goodsAdapter);
        }else{
            lblEmpty.setVisibility(View.VISIBLE);
        }






    }
}