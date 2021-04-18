package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivityNoAcc1 extends AppCompatActivity {
    int signUpNoAccRequestCode=1;
    EditText txtName, txtSurname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_no_acc1);
        txtName=findViewById(R.id.txtName);
        txtSurname=findViewById(R.id.txtSurname);
    }


    //здесь он клик для вызова след активити регистрации
    public void nextLog1(View v){
        //проверка на пустоту данных ввода
        if( !txtName.getText().toString().isEmpty() && !txtSurname.getText().toString().isEmpty()) {
            String name= txtName.getText().toString().charAt(0)+"";
            String surname= txtSurname.getText().toString().charAt(0)+"";
            if(name.matches("А-Я")  ) {
                if(name.matches("А-Я") || surname.matches("A-Z")) {
                    Intent intent = new Intent(this, SignUpActivityNoAcc2.class);
                    intent.putExtra("name", txtName.getText().toString());
                    intent.putExtra("surname", txtSurname.getText().toString());
                    startActivityForResult(intent, signUpNoAccRequestCode);
                }else{
                    txtSurname.setError("Введите фамилию корректно");
                }
            }else{
                txtName.setError("Введите имя корректно");
            }
        }else{
            Toast.makeText(getApplicationContext(),"Заполните все поля",Toast.LENGTH_LONG).show();
        }
    }

    //он клик для возвтрата на первый активити
    public void btnGoBackTOSignIn(View v){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}