package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import Interfaces.OnGoodsRetrievedListener;
import Models.Good;
import Models.GoodsAdapter;
import Models.User;
import Providers.GoodProvider;

public class HistoryActivity extends AppCompatActivity {

    private ListView listViewHistoryOfGoods;
    private ArrayList<Good> goodArrayList;
    private GoodsAdapter goodsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
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
        User user = new User();
        String userKey = user.getCurrentUser().getKey();
        goodProvider.getGoodsFromFirebase(userKey,listener);

    }
}