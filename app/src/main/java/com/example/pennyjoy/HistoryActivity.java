package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Interfaces.OnGoodsRetrievedListener;
import Models.Auth;
import Models.Good;
import Adapters.GoodsAdapter;
import Models.User;
import Providers.GoodProvider;

public class HistoryActivity extends AppCompatActivity {

    private ListView listViewHistoryOfGoods;
    private ArrayList<Good> goodArrayList;
    private GoodsAdapter goodsAdapter;
    private Auth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        auth=Auth.getInstance();


        listViewHistoryOfGoods = findViewById(R.id.listViewOfGoods);



        goodArrayList = new ArrayList<>();
        goodsAdapter = new GoodsAdapter(getApplicationContext(),R.layout.good_template, goodArrayList);

        listViewHistoryOfGoods.setAdapter(goodsAdapter);





        OnGoodsRetrievedListener listener = new OnGoodsRetrievedListener() {
            @Override
            public void OnRetrieved(ArrayList<Good> goods) {
                goodArrayList.clear();
                goodArrayList.addAll(goods);
                goodsAdapter.notifyDataSetChanged();

            }
        };
        GoodProvider goodProvider = new GoodProvider();


        String userKey = auth.getCurrentUser().getKey();
        goodProvider.getGoodsFromFirebase(userKey,listener);

    }
}