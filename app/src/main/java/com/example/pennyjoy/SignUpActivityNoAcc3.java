package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import Providers.UserProvider;

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

            Currency currency=(Currency) dropDownCurrency.getSelectedItem();
            Auth auth=Auth.getInstance();
            auth.setCurrentCurrency(currency);


            User user=new User(name,surname,login,passwd,salary, null);
            UserProvider provider=new UserProvider();
            provider.addUser(user);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Toast.makeText(this, "Теперь войдите в аккаунт", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Заполните все поля верно",Toast.LENGTH_LONG).show();
        }
    }


}