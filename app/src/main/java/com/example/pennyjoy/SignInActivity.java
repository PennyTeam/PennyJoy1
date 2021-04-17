package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import Interfaces.OnUserRetrievedListener;
import Models.User;
import Models.UserProvider;

public class SignInActivity extends AppCompatActivity {
private EditText login, passwd;
private Button btnEnter, btnSignUp;
final int SIGN_UP_REQUEST_CODE = 23;
final int MAIN_REQUEST_CODE = 115;
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

        if(v.getId() == btnEnter.getId()){
            String passWd = passwd.getText().toString();
            String logIn = login.getText().toString();

            if(checkPasswd(passWd) || checkLogin(logIn)){
                User user1 = new User();
                OnUserRetrievedListener listener = new OnUserRetrievedListener() {
                    @Override
                    public void OnRetrieved(User user) {
                        user1.setSalary(user.getSalary());
                        user1.setSurname(user.getSurname());
                        user1.setName(user.getName());
                        user1.setPasswd(user.getPasswd());
                        user1.setLogin(logIn);
                        user1.setKey(user.getKey());
                        if(!passWd.equals( user1.getPasswd())){
                            Snackbar.make(v, "Incorrect password", Snackbar.LENGTH_LONG).show();
                        }
                        else{
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("name", user1.getName());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivityForResult(intent, MAIN_REQUEST_CODE);
                        }
                    }
                };
                UserProvider userProvider = new UserProvider();
                userProvider.getUserFromFirebaseByLogin(logIn,listener);



            }
        }
        else if(v.getId() == btnSignUp.getId()){
            Intent intent = new Intent(this, SignUpActivityNoAcc1.class);
            startActivityForResult(intent, SIGN_UP_REQUEST_CODE);
        }
    }

    public boolean checkPasswd(String passWord){
        if(passWord.length()>6 && passWord.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,10}$")){
            return true;
        }
            passwd.setError("Fill this form correctly!");
            return false;

    }

    public boolean checkLogin(String loginForCheck){
        if(!loginForCheck.trim().isEmpty()){
            return true;
        }
        login.setError("Fill this form correctly!");
        return false;
    }
}