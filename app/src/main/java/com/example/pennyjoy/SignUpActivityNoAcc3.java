package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import Models.Auth;
import Models.CurrenciesList;
import Models.Currency;
import Models.User;
import Providers.CurrencyProvider;
import Providers.UserProvider;
import Providers.UsersCurrencyProvider;

public class SignUpActivityNoAcc3 extends AppCompatActivity {
    EditText salary;


    private Spinner dropDownCurrency;
    private CurrenciesList currenciesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_no_acc3);

        salary=findViewById(R.id.txtSalary);

        dropDownCurrency=findViewById(R.id.currencyDropDown);
        currenciesList=CurrenciesList.getInstance();
        currenciesList.init();

        //делаю адаптер для вложенного списка
        ArrayAdapter<Currency> currencyAdapter=new ArrayAdapter<Currency>(getApplicationContext(),
                R.layout.currency_spinner_item,currenciesList.getCurrencies());

        //currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencyAdapter.setDropDownViewResource(R.layout.drop_down_item_currency);


        dropDownCurrency.setAdapter(currencyAdapter);

        //----------------------
    }



    //здесь он клик для вызова маина с активити регистрации
    public void saveUserClicked(View v){
        if( !salary.getText().toString().isEmpty()) {
            Intent intent = new Intent(this, SignInActivity.class);

            //получаю данные со второго активити
            Intent intent2= getIntent();
            String name=intent2.getExtras().getString("nameFromSecondAct");
            String surname=intent2.getExtras().getString("surnameFromSecondAct");
            String login=intent2.getExtras().getString("login");
            String passwd=intent2.getExtras().getString("passwd");
            float salary=Float.parseFloat(this.salary.getText().toString());

            Auth auth=Auth.getInstance();

            User user=new User(name,surname,login,passwd,salary);
            UserProvider provider=new UserProvider();
            provider.addUser(user);
            auth.setCurrentUser(user);

            Currency currency=(Currency) dropDownCurrency.getSelectedItem();
            currency.setUserKey(auth.getCurrentUser().getKey());

            auth.setCurrentCurrency(currency);
            UsersCurrencyProvider usersCurrencyProvider=new UsersCurrencyProvider();
            usersCurrencyProvider.addCurrency(currency);

            SharedPreferences mySharedPreferences = getSharedPreferences(String.valueOf(R.string.APP_PREFERENCES), Context.MODE_PRIVATE);
            mySharedPreferences.edit().putInt("idOfCurrency",currency.getId()).commit();



            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Toast.makeText(this, "Теперь войдите в аккаунт", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Заполните все поля верно",Toast.LENGTH_LONG).show();
        }
    }


}