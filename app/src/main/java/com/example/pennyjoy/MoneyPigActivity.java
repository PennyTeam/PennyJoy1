package com.example.pennyjoy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;

import Interfaces.OnGoalRetrievedListener;
import Models.Auth;
import Models.Goal;
import Models.GoalsList;
import Providers.GoalProvider;
import de.hdodenhof.circleimageview.CircleImageView;

public class MoneyPigActivity extends AppCompatActivity {
    private CircleImageView imageOfGoal;
    private Bitmap image;
    private Auth auth=Auth.getInstance();
    private TextView lblNameOfGoal,lblProgressOfGoal,lblCurrencyOfGoalProgress;
    private ProgressBar progressBar;
    private DecimalFormat decimalFormat;
    private AlertDialog.Builder alertDialog;
    private GoalsList goalsList = GoalsList.getInstance();
    private Goal currentGoal;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_pig);
        alertDialog = new AlertDialog.Builder(this);

    }

    //делаем так, чтобы после добавления первой цели юзера сразу все отображалось (хотели флагами исправить, но не красиов выглядило)
    @Override
    protected void onStart() {
        super.onStart();

        currentGoal = auth.getCurrentGoal();
        imageOfGoal=findViewById(R.id.imageOfCurrentGoal);
        lblNameOfGoal=findViewById(R.id.lblNameOfGoal);

        decimalFormat = new DecimalFormat( "#.###" );

        lblProgressOfGoal=findViewById(R.id.lblProgressOfGoal);
        lblCurrencyOfGoalProgress=findViewById(R.id.lblCurrencyOfGoalProgress);

        progressBar=findViewById(R.id.progressOfGoal);

        lblCurrencyOfGoalProgress.setText(auth.getCurrentCurrency().getLabel());




        if(currentGoal != null) {
            byte[] imageBytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                imageBytes = Base64.getDecoder().decode(currentGoal.getImage());
                image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imageOfGoal.setImageBitmap(image);
            }
            lblNameOfGoal.setText(currentGoal.getName());
            lblProgressOfGoal.setText(decimalFormat.format(currentGoal.getFullness()) + " / "
                    + decimalFormat.format(currentGoal.getCost()));

            progressBar.setProgress((int) ((currentGoal.getFullness() / currentGoal.getCost()) * 100));
        }
    }

    public void addGoalClicked(View v){
        if(goalsList.size() == 6){
            Snackbar.make(v, "Количество целей не может быть больше 6!", Snackbar.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, AddGoalActivity.class);
            startActivity(intent);
        }
    }

    public void listOfGoalsBtnClicked(View v){
        alertDialog.setTitle("Остальные цели");
        if( goalsList!=null && !goalsList.isEmpty()) {


            String[] goalsTitles = new String[goalsList.size()-1];
            for (int i = 0; i < goalsTitles.length; i++) {
                if(i+1 == goalsList.size()){
                    break;
                }
                goalsTitles[i] = goalsList.get(i+1).getName();
            }
            if(goalsTitles.length == 0){
                alertDialog.setMessage("У вас есть только текущая цель!");
                alertDialog.show();
            }else {
                alertDialog.setItems(goalsTitles, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialog.show();
            }
        }
        else{
            alertDialog.setMessage("У вас пока нет целей!");
            alertDialog.show();
        }
    }

}