package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class SignUpActivityNoAcc1 extends AppCompatActivity {
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
            boolean nameIsUpper=false;
            boolean surnameIsUpper=false;

            //здесь проверки на большие первые буквы
            if( Character.isUpperCase(txtName.getText().toString().charAt(0)) ) {
                nameIsUpper=true;

            }else{
                Snackbar.make(v,"Заполните все поля корректно(имя и фамилия начинаются с большой буквы)", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
            if(Character.isUpperCase(txtSurname.getText().toString().charAt(0))) {
                surnameIsUpper=true;

            }else{
                Snackbar.make(v,"Заполните все поля корректно(имя и фамилия начинаются с большой буквы)", BaseTransientBottomBar.LENGTH_SHORT).show();
            }

            //здесь проверяем те бул переменные; отвечающие за наличие больших первых букв
            if(nameIsUpper && surnameIsUpper){
                Intent intent = new Intent(this, SignUpActivityNoAcc2.class);
                intent.putExtra("name", txtName.getText().toString());
                intent.putExtra("surname", txtSurname.getText().toString());
                startActivity(intent);
            }
        }else{
            Snackbar.make(v,"Заполните все поля", BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

}