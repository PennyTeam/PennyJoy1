package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import Interfaces.OnUserRetrievedListener;
import Models.User;
import Models.UserProvider;

public class SignUpActivityNoAcc2 extends AppCompatActivity {
    int signUpNoAccRequestCode=2;
    EditText txtLogin, txtPasswd, txtRepeatedPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_no_acc2);

        txtLogin=findViewById(R.id.txtLogin);
        txtPasswd=findViewById(R.id.txtPasswd);
        txtRepeatedPasswd=findViewById(R.id.txtRepeatPasswd);

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

            User user1 = new User();
            OnUserRetrievedListener listener = new OnUserRetrievedListener() {
                @Override
                public void OnRetrieved(User user) {
                    user1.setSalary(user.getSalary());
                    user1.setSurname(user.getSurname());
                    user1.setName(user.getName());
                    user1.setPasswd(user.getPasswd());
                    user1.setLogin(user.getLogin());
                    user1.setKey(user.getKey());
                    if( user1.getLogin()!=null){
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
            userProvider.getUserFromFirebaseByLogin(txtLogin.getText().toString(),listener);
            //pr bar


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