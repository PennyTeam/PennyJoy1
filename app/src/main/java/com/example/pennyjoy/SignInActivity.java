package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import Interfaces.OnUserRetrievedListener;
import Models.Auth;
import Models.CurrenciesList;
import Models.Currency;
import Models.User;
import Providers.UserProvider;

public class  SignInActivity extends AppCompatActivity {
    private EditText login, passwd;
    private Button btnEnter, btnSignUp;
    final int SIGN_UP_REQUEST_CODE = 23;
    final int MAIN_REQUEST_CODE = 115;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        login = findViewById(R.id.editTextLogin);
        passwd = findViewById(R.id.editTextPassword);
        btnEnter = findViewById(R.id.buttonEnter);
        btnSignUp = findViewById(R.id.buttonToSignUp);


    }

    public void onClick(View v){


        //нажал на кнопку Войти
        if(v.getId() == btnEnter.getId()){
            String passWd = passwd.getText().toString();
            String logIn = login.getText().toString();

            //проверка
            if(checkPasswd(passWd) || checkLogin(logIn)){
                User user1 = new User();
                OnUserRetrievedListener listener = new OnUserRetrievedListener() {
                    @Override
                    //получаем юзера из UserProvider при помощи интерфейса
                    public void OnRetrieved(User user) {
//new
                        user1.setAccIsActive(user.getAccIsActive());
                        user1.setSalary(user.getSalary());
                        user1.setSurname(user.getSurname());
                        user1.setName(user.getName());
                        user1.setPasswd(user.getPasswd());
                        user1.setLogin(user.getLogin());
                        user1.setKey(user.getKey());
                        if(!passWd.equals( user1.getPasswd()) && user1.getLogin()==null){
                            Snackbar.make(v, "Логин или пароль не верны", Snackbar.LENGTH_LONG).show();
                        }
                        else {
                            if (!user1.getAccIsActive()) {
                                Snackbar.make(v, "Аккаунт был удален!", Snackbar.LENGTH_LONG).show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                //здесь сохраняю логин юзера
                                saveLogin(user1);

                                Auth auth = new Auth();
                                auth.setCurrentUser(user1);

                                Currency currency = new Currency();

                                CurrenciesList currenciesList = new CurrenciesList();
                                currenciesList.init();

                                auth.setCurrentCurrency(currenciesList.getCurrencies().get(0));


                                //pr bar
                                startActivityForResult(intent, MAIN_REQUEST_CODE);
                            }
                        }
                    }
                };
                UserProvider userProvider = new UserProvider();
                userProvider.getUserFromFirebaseByLogin(logIn,listener);
                //pr bar
            }
        }
        //нажали на кнопку "Регистрация"
        else if(v.getId() == btnSignUp.getId()){
            Intent intent = new Intent(this, SignUpActivityNoAcc1.class);
            startActivityForResult(intent, SIGN_UP_REQUEST_CODE);
        }
    }


    //Проверка грамотности ввода пароля
    public boolean checkPasswd(String passWord){
        if(passWord.length()>6 && passWord.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,10}$")){
            return true;
        }else {
            passwd.setError("Введите пароль корректно!");
            return false;
        }

    }

    //Проверка на корректность ввода логина
    public boolean checkLogin(String loginForCheck){
        if(!loginForCheck.trim().isEmpty() && loginForCheck.length()>5){
            return true;
        }
        login.setError("Заполните логин корректно!");
        return false;
    }

    //функция для сохранения логина юзера
    public void saveLogin(User user){
        sharedPreferences=getSharedPreferences(String.valueOf(R.string.APP_PREFERENCES),Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        String login=user.getLogin();
        editor.putString("loginOfTheAuthorizedUser",login);
        editor.commit();
    }

    //функция для проверки наличия юзера
   /*public void isUserExist(){

        sharedPreferences=getPreferences(MODE_PRIVATE);


        String login=sharedPreferences.getString("loginOfTheAuthorizedUser",null);

        if(login!=null){
            UserProvider provider=new UserProvider();
            OnUserRetrievedListener listener = new OnUserRetrievedListener() {
                @Override
                public void OnRetrieved(User user) {
                    if(login.equals(user.getLogin())){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        //здесь вызываю маин при совпадении логина в сп с логином из бд
                        //и настраиваю флаги

                        Auth auth=new Auth();
                        auth.setCurrentUser(user);

                        CurrenciesList currenciesList=new CurrenciesList();
                        currenciesList.init();
                        SharedPreferences mySharedPreferences = getSharedPreferences(String.valueOf(R.string.APP_PREFERENCES), Context.MODE_PRIVATE);
                        int idOfCurrency=mySharedPreferences.getInt("idOfCurrency",-1);
                        if(idOfCurrency==-1) {
                            auth.setCurrentCurrency(currenciesList.getCurrencies().get(0));
                        }else{
                            auth.setCurrentCurrency(currenciesList.getCurrencies().get(idOfCurrency));
                        }






                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        

                    }
                    else{
                        sharedPreferences.edit().remove("loginOfTheAuthorizedUser").commit();
                        return;
                    }
                }
            };
            provider.getUserFromFirebaseByLogin(login,listener);
        }else{
            sharedPreferences.edit().remove("loginOfTheAuthorizedUser").commit();
            return;
        }
    }*/

}