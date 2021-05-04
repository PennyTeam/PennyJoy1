package com.example.pennyjoy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import Models.Auth;
import Models.User;
import Providers.UserProvider;

public class SetUserActivity extends AppCompatActivity {
private Button btnSaveSettings, btnDeleteAccount;
private ImageButton btnReturnToMain;
private LinearLayout btnSetPasswd;
private EditText editTextName, editTextSurname, editTextSalary, editTextLogin;
private TextView passwdStars, currencySymbol;
private   Auth auth = new Auth();
private SharedPreferences sharedPreferences;
private SharedPreferences.Editor editor;
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

    public void onClick(View v){
        int id = v.getId();
        if(id == btnSaveSettings.getId()){
            String newName = editTextName.getText().toString();
            String newSurname = editTextSurname.getText().toString();
            String newLogin = editTextLogin.getText().toString();
            String newSalary = editTextSalary.getText().toString();

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
                        Toast.makeText(getApplicationContext(), "Изменения сохранены", Toast.LENGTH_SHORT).show();
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
                    }
                });
ad.show();
            }
        }
        if(id == btnReturnToMain.getId()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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