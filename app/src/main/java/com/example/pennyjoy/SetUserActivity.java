package com.example.pennyjoy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

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

public class SetUserActivity extends AppCompatActivity {
    private Button btnSaveSettings, btnDeleteAccount;
    private ImageButton btnReturnToMain;
    private LinearLayout btnSetPasswd;
    private EditText editTextName, editTextSurname, editTextSalary, editTextLogin;
    private TextView passwdStars, currencySymbol;
    private   Auth auth =Auth.getInstance();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Spinner dropDownCurrency;
    private CurrenciesList currenciesList;
    private double valueOfCurrency=0;

    private String newName ;
    private String newSurname;
    private String newLogin;
    private String newSalary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user);
        btnSaveSettings = findViewById(R.id.save_settings);
        btnReturnToMain = findViewById(R.id.btn_return_home);
        btnDeleteAccount = findViewById(R.id.delete_user_btn);
        btnSetPasswd = findViewById(R.id.buttonPasswd);
        editTextName = findViewById(R.id.etext_name);
        editTextSurname = findViewById(R.id.etext_surname);
        editTextSalary = findViewById(R.id.etext_salary);
        editTextLogin = findViewById(R.id.etext_login);
        passwdStars = findViewById(R.id.text_password_for_edit);
        currencySymbol = findViewById(R.id.symbolOfCurrency);

        currencySymbol.setText(auth.getCurrentCurrency().getLabel());
        dropDownCurrency=findViewById(R.id.currencyDropDown);
        currenciesList=CurrenciesList.getInstance();
        currenciesList.init();

        //делаю адаптер для вложенного списка
        ArrayAdapter<Currency> currencyAdapter=new ArrayAdapter<Currency>(getApplicationContext(),
                R.layout.currency_spinner_item,currenciesList.getCurrencies());

        //currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencyAdapter.setDropDownViewResource(R.layout.drop_down_item_currency);


        dropDownCurrency.setAdapter(currencyAdapter);

        int positionOfCurrency = currencyAdapter.getPosition(auth.getCurrentCurrency());
        dropDownCurrency.setSelection(positionOfCurrency);

        //----------------------


        editTextName.setText(auth.getCurrentUser().getName());
        editTextSurname.setText(auth.getCurrentUser().getSurname());
        editTextLogin.setText(auth.getCurrentUser().getLogin());
        editTextSalary.setText(auth.getCurrentUser().getSalary() + "");

        String passwd = auth.getCurrentUser().getPasswd();
        String passwordStars = "";
        for(int i = 0; i<passwd.length(); i++){
            passwordStars+="*";
        }
        passwdStars.setText(passwordStars);
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
            User updatedUser = auth.getCurrentUser();
            updatedUser.setAccIsActive(auth.getCurrentUser().getAccIsActive());
            updatedUser.setLogin(newLogin);
            updatedUser.setName(newName);
            updatedUser.setSalary(Double.parseDouble(newSalary));
            updatedUser.setSurname(newSurname);



            sharedPreferences = getSharedPreferences(String.valueOf(R.string.APP_PREFERENCES), Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("loginOfTheAuthorizedUser", updatedUser.getLogin());

            editor.commit();
            currencySymbol.setText(auth.getCurrentCurrency().getLabel());
            editTextSalary.setText(auth.getCurrentUser().getSalary()+"");
            Toast.makeText(getApplicationContext(), "Изменения сохранены", Toast.LENGTH_SHORT).show();
        }
    };



    OnCurrencyConvertRetrievedListener listener=new OnCurrencyConvertRetrievedListener() {
        @Override
        public void onRetrieved(double currency) {
            valueOfCurrency=currency;


            User updatedUser=auth.getCurrentUser();
            auth.setCurrentCurrency((Currency) dropDownCurrency.getSelectedItem());
            SharedPreferences mySharedPreferences = getSharedPreferences(String.valueOf(R.string.APP_PREFERENCES),Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            editor.putInt("idOfCurrency", auth.getCurrentCurrency().getId());
            editor.apply();
            //_____

            //------

            DecimalFormat decimalFormat=new DecimalFormat("#.###");
            newSalary = decimalFormat.format(Double.parseDouble(editTextSalary.getText().toString())*valueOfCurrency);
            String salary=decimalFormat.format(Double.parseDouble(editTextSalary.getText().toString())*valueOfCurrency);
            updatedUser.setSalary(Double.parseDouble(salary));
            UserProvider provider=new UserProvider();
            provider.updateUser(updatedUser);

            auth.setCurrentUser(updatedUser);
            GoodProvider goodProvider=new GoodProvider();
            goodProvider.getGoodsFromFirebase(updatedUser.getKey(),goodsListener);
        }
    };

    public void onClick(View v){
        int id = v.getId();
        if(id == btnSaveSettings.getId()){
            newName = editTextName.getText().toString();
            newSurname = editTextSurname.getText().toString();
            newLogin = editTextLogin.getText().toString();
            newSalary = editTextSalary.getText().toString();

            if((!checkNewName(newName) || !checkNewLogin(newLogin) || !checkNewSalary(newSalary) || !checkNewSurname(newSurname))){
                Snackbar.make(v, "Заполните все поля корректно!", Snackbar.LENGTH_LONG).show();
            }
            else{
                AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setTitle("Внимание!");
                ad.setMessage("Вы уверены, что хотите изменить личные данные?");
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

                        CurrencyProvider currencyProvider = new CurrencyProvider();

                        Currency currencyToConvert=(Currency) dropDownCurrency.getSelectedItem();
                        Currency currentCurrency=auth.getCurrentCurrency();

                        if(currencyToConvert.getId() != currentCurrency.getId() ) {
                            currencyProvider.setNewCurrency(listener, currentCurrency.getCode(), currencyToConvert.getCode());
                        }else {
                            User updatedUser = auth.getCurrentUser();
                            updatedUser.setAccIsActive(auth.getCurrentUser().getAccIsActive());
                            updatedUser.setLogin(newLogin);
                            updatedUser.setName(newName);
                            updatedUser.setSalary(Double.parseDouble(newSalary));
                            updatedUser.setSurname(newSurname);

                            UserProvider userProvider = new UserProvider();
                            userProvider.updateUser(updatedUser);
                            auth.setCurrentUser(updatedUser);


                            sharedPreferences = getSharedPreferences(String.valueOf(R.string.APP_PREFERENCES), Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("loginOfTheAuthorizedUser", updatedUser.getLogin());
                            editor.commit();
                            Toast.makeText(getApplicationContext(), "Изменения сохранены", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                ad.show();
            }
        }
        if(id == btnReturnToMain.getId()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        if(v.getId() == btnDeleteAccount.getId()){
            AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
            ad.setTitle("Удаление аккаунта");
            ad.setMessage("Вы уверены, что хотите удалить аккаунт?");
            ad.setIcon(R.drawable.ic_baseline_warning_24);
            ad.setCancelable(false);
            ad.setNegativeButton("Нет, отменить", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
               Toast.makeText(getApplicationContext(), "Операция отменена", Toast.LENGTH_SHORT).show();
                }
            });
            ad.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    User user = auth.getCurrentUser();
                    user.setAccIsActive(false);
                    UserProvider up = new UserProvider();
                    up.updateUser(user);
                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            ad.show();
        }

        if(id == btnSetPasswd.getId()){
            Intent intent = new Intent(getApplicationContext(), SetPasswdActivity.class);
            startActivity(intent);
        }
    }


    public boolean checkNewName(String newName){
        if(newName.length()>=2){
            return true;
        }
        editTextName.setError("Минимум 2 символа!");
        return false;
    }

    public boolean checkNewSurname(String newSurname){
        if(newSurname.length()>=2){
            return true;
        }
        editTextSurname.setError("Минимум 2 символа!");
        return false;
    }

    public boolean checkNewLogin(String newLogin){
        if(newLogin.length()>=6){
            return true;
        }
        editTextLogin.setError("Логин должен включать в себя минимум 6 символов");
        return false;
    }

    public boolean checkNewSalary(String newSalary){
        if(!newSalary.isEmpty() && newSalary != null){
            return true;
        }
        editTextSalary.setError("Поле обязательно к заполнению");
        return false;
    }
}