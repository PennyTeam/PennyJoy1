package com.example.pennyjoy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import Models.Auth;
import Models.User;
import Providers.UserProvider;

public class SetPasswdActivity extends AppCompatActivity {
   private EditText editTextOldPasswd, newPasswd1, newPasswd2;
   private Button btnSaveNewPasswd;
   private ImageButton btnReturnBack;
   private Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_passwd);
        auth = new Auth();
        editTextOldPasswd = findViewById(R.id.old_passwd);
        newPasswd1 = findViewById(R.id.new_password1);
        newPasswd2 = findViewById(R.id.newPasswd2);
        btnSaveNewPasswd = findViewById(R.id.save_new_passwd);
        btnReturnBack = findViewById(R.id.button_return_from_edit_password);

        btnSaveNewPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPasswd = newPasswd1.getText().toString();
                String newPasswdRepeated = newPasswd2.getText().toString();
                String oldPasswd = editTextOldPasswd.getText().toString();
                if((!checkNewPasswords(newPasswd, newPasswdRepeated) || !
                        checkOldPasswd(oldPasswd))){
                    Snackbar.make(v, "Заполните все поля корректно!", Snackbar.LENGTH_LONG).show();
                }

                else{
                    AlertDialog.Builder ad = new AlertDialog.Builder(getApplicationContext());
                    ad.setTitle("Изменение пароля");
                    ad.setMessage("Вы уверены, что хотите изменить пароль?");
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
                            User user = auth.getCurrentUser();
                            user.setPasswd(newPasswd);
                            user.setName(auth.getCurrentUser().getName());
                            auth.setCurrentUser(user);
                            UserProvider userProvider = new UserProvider();
                            userProvider.updateUser(user);
                            Toast.makeText(getApplicationContext(), "Пароль изменен", Toast.LENGTH_SHORT).show();
                            editTextOldPasswd.getText().clear();
                            newPasswd1.getText().clear();
                            newPasswd2.getText().clear();
                        }
                    });
                    ad.show();
                }
            }
        });

        btnReturnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetUserActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public boolean checkOldPasswd(String oldPasswd){
        if(auth.getCurrentUser().getPasswd().equals(oldPasswd)){
            return true;
        }
        editTextOldPasswd.setError("Старый пароль введен неверно!");
        return false;
    }
//проверяю новый пароль на криптостойкость
    public boolean checkNewPasswords(String passwd1, String repeatedPasswd){
        if(passwd1.equals(repeatedPasswd) && passwd1.length()>6 && passwd1.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,10}$")){
            return true;
        }
        return false;
    }
}