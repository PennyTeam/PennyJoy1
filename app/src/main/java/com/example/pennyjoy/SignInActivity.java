package com.example.pennyjoy;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import Interfaces.OnGoalRetrievedListener;
import Interfaces.OnGoodsRetrievedListener;
import Interfaces.OnUserRetrievedListener;
import Interfaces.OnUsersCurrencyRetrievedListener;
import Models.Auth;
import Models.CategoryList;
import Models.CurrenciesList;
import Models.Currency;
import Models.Goal;
import Models.GoalsList;
import Models.Good;
import Models.GoodsList;
import Models.User;
import Providers.GoalProvider;
import Providers.GoodProvider;
import Providers.UserProvider;
import Providers.UsersCurrencyProvider;

public class  SignInActivity extends AppCompatActivity {
    private EditText login, passwd;
    private Button btnEnter, btnSignUp;
    final int SIGN_UP_REQUEST_CODE = 23;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Auth auth = Auth.getInstance();
    private ProgressBar progressBar;
    private GoalsList goalsList = GoalsList.getInstance();
    private GoodsList goodsList=GoodsList.getInstance();







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        login = findViewById(R.id.editTextLogin);
        passwd = findViewById(R.id.editTextPassword);
        btnEnter = findViewById(R.id.buttonEnter);
        btnSignUp = findViewById(R.id.buttonToSignUp);
        progressBar=findViewById(R.id.progressBarInSignIn);


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


                        user1.setAccIsActive(user.getAccIsActive());
                        user1.setSalary(user.getSalary());
                        user1.setSurname(user.getSurname());
                        user1.setName(user.getName());
                        user1.setPasswd(user.getPasswd());
                        user1.setLogin(user.getLogin());
                        user1.setKey(user.getKey());
                        user1.setUsersCurrentDate(user.getUsersCurrentDate());
                        user1.setTotalSpends(user.getTotalSpends());
                        user1.setDateCreate(user.getDateCreate());
                        user1.setEfficiency(user.getEfficiency());

                        if(!passWd.equals( user1.getPasswd()) || user1.getLogin()==null){
                            Snackbar.make(v, "Логин или пароль не верны", Snackbar.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                        else {
                            if (!user1.getAccIsActive()) {
                                Snackbar.make(v, "Аккаунт был удален!", Snackbar.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Intent intentFromNoAccc3=getIntent();
                                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                if(intentFromNoAccc3.getExtras() != null && intentFromNoAccc3.getExtras().getBoolean("isUserHereFirstTime") ) {
                                 intent.setClass(getApplicationContext(), SlideActivity.class);
                                }

                                //работа с категориями
                                CategoryList categoryList=CategoryList.getInstance();
                                categoryList.init();

                                //работа с категориями
                                //здесь сохраняю логин юзера
                                saveLogin(user1);


                                auth.setCurrentUser(user1);


                                OnGoalRetrievedListener listener1 = new OnGoalRetrievedListener() {
                                    @Override
                                    public void onGoalRetrieved(ArrayList<Goal> goalList) {

                                        //сохраняю текующую цель сразу, чтобы она быстрее отображалась
                                        if(goalList !=  null && !goalList.isEmpty()) {
                                            auth.setCurrentGoal(goalList.get(0));
                                            goalsList.addAll(goalList);
                                        }

                                        //заполняем лист с товарами
                                        GoodProvider goodProvider=new GoodProvider();

                                        goodProvider.getGoodsFromFirebase(user.getKey(), new OnGoodsRetrievedListener() {
                                            @Override
                                            public void OnRetrieved(ArrayList<Good> goods) {
                                                if(goods != null && !goods.isEmpty()){
                                                    goodsList.addAll(goods);
                                                }

                                                CurrenciesList currenciesList = CurrenciesList.getInstance();
                                                currenciesList.init();
                                                if(auth.getCurrentCurrency() == null) {
                                                    UsersCurrencyProvider usersCurrencyProvider=new UsersCurrencyProvider();
                                                    usersCurrencyProvider.getUsersCurrencyFromFirebase(auth.getCurrentUser().getKey()
                                                            , new OnUsersCurrencyRetrievedListener() {
                                                                @Override
                                                                public void OnRetrieved(Currency currency) {
                                                                    auth.setCurrentCurrency(currency);
                                                                    editor.putInt("idOfCurrency",currency.getId()).commit();
                                                                    startActivity(intent);
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                }
                                                            });
                                                }else{
                                                    startActivity(intent);
                                                    Toast.makeText(getApplicationContext(),"Добро пожаловать в PennyJoy!",Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                }


                                            }
                                        });

                                    }
                                };
                                GoalProvider goalProvider = new GoalProvider();
                                goalProvider.getGoalsFromFirebase(auth.getCurrentUser().getKey(), listener1);


                            }
                        }
                    }
                };
                UserProvider userProvider = new UserProvider();
                userProvider.getUserFromFirebaseByLogin(logIn,listener);
                progressBar.setVisibility(View.VISIBLE);
            }else{
                Snackbar.make(v, "Логин или пароль не верны", Snackbar.LENGTH_LONG).show();

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
            return false;
        }

    }

    //Проверка на корректность ввода логина
    public boolean checkLogin(String loginForCheck){
        if(!loginForCheck.trim().isEmpty() && loginForCheck.length()>5){
            return true;
        }

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





}