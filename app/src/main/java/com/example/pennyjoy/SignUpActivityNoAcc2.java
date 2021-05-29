package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import Interfaces.OnUserRetrievedListener;
import Models.User;
import Providers.UserProvider;

public class SignUpActivityNoAcc2 extends AppCompatActivity {
    private int signUpNoAccRequestCode=2;
    private EditText txtLogin, txtPasswd, txtRepeatedPasswd;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_no_acc2);

        txtLogin=findViewById(R.id.txtLogin);
        txtPasswd=findViewById(R.id.txtPasswd);
        txtRepeatedPasswd=findViewById(R.id.txtRepeatPasswd);
        progressBar=findViewById(R.id.progressBarInSignUpNoAcc2);

    }

    //здесь он клик для вызова след активити регистрации
    public void nextLog2(View v){
        //проверка на корректность ввода

        if( !txtLogin.getText().toString().isEmpty() && !txtPasswd.getText().toString().isEmpty()
        && !txtRepeatedPasswd.getText().toString().isEmpty()
        && checkingPasswd(txtPasswd.getText().toString(), txtRepeatedPasswd.getText().toString())==true
        && txtLogin.getText().toString().length()>5 ) {
            Intent intent = new Intent(this, SignUpActivityNoAcc3.class);
            Intent intent2=getIntent();
            //получаю данные с первого активити
            String name= intent2.getExtras().getString("name");
            String surname= intent2.getExtras().getString("surname");


            OnUserRetrievedListener listener = new OnUserRetrievedListener() {
                @Override
                public void OnRetrieved(User user) {
                    progressBar.setVisibility(View.INVISIBLE);

                    if( user.getLogin()!=null){
                        txtLogin.setError("Данный логин уже используется");
                    }else{
                        intent.putExtra("login",txtLogin.getText().toString());
                        intent.putExtra("passwd",txtPasswd.getText().toString());
                        intent.putExtra("nameFromSecondAct",name);
                        intent.putExtra("surnameFromSecondAct",surname);
                        //pr bar

                        startActivityForResult(intent, signUpNoAccRequestCode);
                    }
                }
            };
            UserProvider userProvider = new UserProvider();
            progressBar.setVisibility(View.VISIBLE);
            userProvider.getUserFromFirebaseByLogin(txtLogin.getText().toString(),listener);


        }else{
            Toast.makeText(getApplicationContext(),"Заполните все поля верно",Toast.LENGTH_LONG).show();
        }
    }

    //проверяю пароль
    public boolean checkingPasswd(String passwd,String repeatedPasswd){
        if(passwd.length()>6 && passwd.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,10}$") && passwd.equals(repeatedPasswd)){
            return true;
        }
        return false;
    }




}