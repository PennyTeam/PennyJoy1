package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Models.User;
import Models.UserProvider;

public class SignUpActivityNoAcc3 extends AppCompatActivity {
    EditText salary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_no_acc3);

        salary=findViewById(R.id.txtSalary);
    }



    //здесь он клик для вызова маина с активити регистрации
    public void saveUserClicked(View v){
        if( !salary.getText().toString().isEmpty()) {
            Intent intent = new Intent(this, MainActivity.class);

            //получаю данные со второго активити
            Intent intent2= getIntent();
            String name=intent2.getExtras().getString("nameFromSecondAct");
            String surname=intent2.getExtras().getString("surnameFromSecondAct");
            String login=intent2.getExtras().getString("login");
            String passwd=intent2.getExtras().getString("passwd");
            float salary=Float.parseFloat(this.salary.getText().toString());

            User user=new User(name,surname,login,passwd,salary, null);
            UserProvider provider=new UserProvider();
            provider.addUser(user);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Заполните все поля верно",Toast.LENGTH_LONG).show();
        }
    }


    //он клик для возвтрата на второй активити
    public void btnBackSecondClicked(View v){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}