package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        //проверка на пустоту данных ввода и пароль


        if( !txtLogin.getText().toString().isEmpty() && !txtPasswd.getText().toString().isEmpty()
        && !txtRepeatedPasswd.getText().toString().isEmpty()
        && checkingPasswd(txtPasswd.getText().toString(), txtRepeatedPasswd.getText().toString())==true) {
            Intent intent = new Intent(this, SignUpActivityNoAcc3.class);
            Intent intent2=getIntent();
            //получаю данные с первого активити
            String name= intent2.getExtras().getString("name");
            String surname= intent2.getExtras().getString("surname");


            intent.putExtra("login",txtLogin.getText().toString());
            intent.putExtra("passwd",txtPasswd.getText().toString());
            intent.putExtra("nameFromSecondAct",name);
            intent.putExtra("surnameFromSecondAct",surname);

            startActivityForResult(intent, signUpNoAccRequestCode);
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

    //он клик для возвтрата на первый активити
    public void btnBackFirstClicked(View v){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}