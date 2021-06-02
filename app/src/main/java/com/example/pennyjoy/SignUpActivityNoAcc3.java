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

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import Models.Auth;
import Models.CurrenciesList;
import Models.Currency;
import Models.User;
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


        currencyAdapter.setDropDownViewResource(R.layout.drop_down_item_currency);


        dropDownCurrency.setAdapter(currencyAdapter);

        //----------------------
    }



    //здесь он клик для вызова маина с активити регистрации
    public void saveUserClicked(View v){
        if( !salary.getText().toString().isEmpty()) {
            Intent intent = new Intent(this, SignInActivity.class);

            intent.putExtra("isUserHereFirstTime",true);

            //получаю данные со второго активити
            Intent intent2= getIntent();
            String name=intent2.getExtras().getString("nameFromSecondAct");
            String surname=intent2.getExtras().getString("surnameFromSecondAct");
            String login=intent2.getExtras().getString("login");
            String passwd=intent2.getExtras().getString("passwd");
            double salary=Double.parseDouble(this.salary.getText().toString());

            Auth auth=Auth.getInstance();

            Date cDate = new Date();
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

            User user=new User(name,surname,login,passwd,salary,currentDate);

            user.setAccIsActive(true);
            user.setTotalSpends(0);
            user.setUsersCurrentDate(currentDate);
            user.setEfficiency(0);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            Date date = new Date();
            user.setUsersCurrentDate(dateFormat.format(date));

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




            Toast.makeText(this, "Теперь войдите в аккаунт", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }else{
            Snackbar.make(v,"Заполните все поля верно", BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }


}