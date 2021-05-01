package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Interfaces.OnCurrencyConvertRetrievedListener;
import Interfaces.OnGoodsRetrievedListener;
import Models.Auth;
import Models.CurrenciesList;
import Models.Currency;
import Models.Good;
import Models.User;
import Providers.CurrencyProvider;
import Providers.GoodProvider;
import Providers.UserProvider;

public class SettingsOfCurrencyActivity extends AppCompatActivity {
    private Spinner dropDownCurrency;
    private CurrenciesList currenciesList;
    private TextView lblCurrencyInAddGood, lblCurrencyInHistoryOfGood;






    private double valueOfCurrency=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_of_currency);

        dropDownCurrency=findViewById(R.id.currencyDropDown);
        currenciesList=new CurrenciesList();
        currenciesList.init();

        //делаю адаптер для вложенного списка
        ArrayAdapter<Currency> currencyAdapter=new ArrayAdapter<Currency>(getApplicationContext(),
                R.layout.currency_spinner_item,currenciesList.getCurrencies());

        //currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencyAdapter.setDropDownViewResource(R.layout.drop_down_item_currency);


        dropDownCurrency.setAdapter(currencyAdapter);

        //----------------------




    }


    OnGoodsRetrievedListener goodsListener=new OnGoodsRetrievedListener() {
        @Override
        public void OnRetrieved(ArrayList<Good> goods) {
            DecimalFormat decimalFormat=new DecimalFormat("#.###");
            for (Good g:goods) {
                String cost =decimalFormat.format(g.getCost()*valueOfCurrency);
                g.setCost(Double.parseDouble(cost));
                GoodProvider provider=new GoodProvider();
                provider.updateGood(g);
            }

        }
    };



    OnCurrencyConvertRetrievedListener listener=new OnCurrencyConvertRetrievedListener() {
        @Override
        public void onRetrieved(double currency) {
            valueOfCurrency=currency;

            Auth auth=new Auth();
            User currentUser=auth.getCurrentUser();
            auth.setCurrentCurrency((Currency) dropDownCurrency.getSelectedItem());
            SharedPreferences mySharedPreferences = getSharedPreferences(String.valueOf(R.string.APP_PREFERENCES),Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            editor.putInt("idOfCurrency", auth.getCurrentCurrency().getId());
            editor.apply();
            //_____

            //-----

            DecimalFormat decimalFormat=new DecimalFormat("#.###");
            String salary=decimalFormat.format(currentUser.getSalary()*valueOfCurrency);
            currentUser.setSalary(Double.parseDouble(salary));
            UserProvider provider=new UserProvider();
            provider.updateUser(currentUser);

           GoodProvider goodProvider=new GoodProvider();
           goodProvider.getGoodsFromFirebase(currentUser.getKey(),goodsListener);
        }
    };

    public void saveCurrencyClicked(View v){
        CurrencyProvider currencyProvider = new CurrencyProvider();
        Auth auth=new Auth();
        Currency currencyToConvert=(Currency) dropDownCurrency.getSelectedItem();
        Currency currentCurrency=auth.getCurrentCurrency();


        currencyProvider.setNewCurrency(listener,currentCurrency.getCode(),currencyToConvert.getCode());
    }
}