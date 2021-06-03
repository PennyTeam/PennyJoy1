package com.example.pennyjoy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Base64;

import Models.Auth;

import Models.Goal;
import Models.GoalsList;
import Models.User;
import Providers.GoalProvider;

public class AddGoalActivity extends AppCompatActivity {
    private TextView currencyOfCostInAddGoal,lblCounterOfSymbols;
    private Auth auth;
    private final int Pick_image = 1;
    private final int Make_a_photo = 0;
    private String imageOfGoal;
    private AppCompatButton addImageOfGoalBtn;
    private ImageButton addGoalBtn;
    private EditText txtNameOfGoal, txtCostOfGoal, txtWhatFor;
    private GoalsList goalsList = GoalsList.getInstance();
    private int counterOfSymbols=0;
    private AlertDialog.Builder alertDialog;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try {

                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageOfGoal=convertBitmapToBase64(selectedImage);
                        addImageOfGoalBtn.setText("Фото выбрано");

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case Make_a_photo:
                if(resultCode == RESULT_OK && data!=null){
                    try {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        imageOfGoal = convertBitmapToBase64(bitmap);
                        addImageOfGoalBtn.setText("Фото выбрано");
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        auth=Auth.getInstance();
        currencyOfCostInAddGoal=findViewById(R.id.currencyOfCostInAddGoal);
        currencyOfCostInAddGoal.setText(auth.getCurrentCurrency().getLabel());
        addImageOfGoalBtn=findViewById(R.id.addPictureOfGoal);
        alertDialog = new AlertDialog.Builder(this);

        txtNameOfGoal=findViewById(R.id.txtNameOfGoal);
        txtCostOfGoal=findViewById(R.id.txtCostOfGoal);
        txtWhatFor=findViewById(R.id.txtWhatForYouNeedGoal);

        lblCounterOfSymbols=findViewById(R.id.counterOfTextInputAddGoal);



       addGoalBtn=findViewById(R.id.addGoalBtn);
       addGoalBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(goalsList.size() == 6){
                   Snackbar.make(v, "Количество целей не может быть больше 6", Snackbar.LENGTH_SHORT).show();
               }
               else {

                   if (!txtNameOfGoal.getText().toString().isEmpty() && !txtCostOfGoal.getText().toString().isEmpty()
                           && !txtWhatFor.getText().toString().trim().isEmpty()
                           && Double.parseDouble(txtCostOfGoal.getText().toString()) > 0
                           && counterOfSymbols >= 90
                           && !txtWhatFor.getText().toString().trim().isEmpty() && imageOfGoal != null) {

                       //получаю текущего юзера и устанавливаю кей


                       Auth auth = Auth.getInstance();


                       String keyOfUser = auth.getCurrentUser().getKey();
                       String nameOfGoal = txtNameOfGoal.getText().toString();
                       String whatFor = txtWhatFor.getText().toString();
                       double costOfGoal = Double.parseDouble(txtCostOfGoal.getText().toString());

                       Goal goal = new Goal(imageOfGoal, nameOfGoal, costOfGoal, whatFor, keyOfUser);
                       if (auth.getCurrentGoal() == null) {
                           auth.setCurrentGoal(goal);
                       }
                       GoalProvider goalProvider = new GoalProvider();
                       goalProvider.addGoal(goal);


                       goalsList.add(goal);


                       txtNameOfGoal.getText().clear();
                       txtCostOfGoal.getText().clear();
                       txtWhatFor.getText().clear();
                       addImageOfGoalBtn.setText("Выбрать фото цели");
                       Toast.makeText(getApplicationContext(), "Цель добавлена, Удачи!", Toast.LENGTH_SHORT).show();

                   } else {
                       Snackbar.make(v, "Заполните все поля корректно", Snackbar.LENGTH_SHORT).show();
                   }
               }
           }
       });

        txtWhatFor.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable s) {
                lblCounterOfSymbols.setText(s.toString().length() + "/120" );
                counterOfSymbols=s.toString().length();

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

    }


        public void chooseImageClicked(View view) {
            alertDialog.setTitle("Выбор фото");
            String []arrayOfOptions = new String[]{"Выбрать из галереи", "Сделать фото", "Закрыть"};
            alertDialog.setCancelable(false);

            alertDialog.setItems(arrayOfOptions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == 0){
                        //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        //Тип получаемых объектов - image:
                        photoPickerIntent.setType("image/*");
                        //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                        startActivityForResult(photoPickerIntent, Pick_image);
                    }
                    else if(which == 1){
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, Make_a_photo);
                    }
                    else {
                        Snackbar.make(view,"Операция отменена", BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            });
            alertDialog.show();
        }


        //здесь делаем из картинки стринг, чтобы сохранить
        public String convertBitmapToBase64(Bitmap bitmap){
            ByteArrayOutputStream stream =new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG,50,stream);

            byte[] image = stream.toByteArray();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Base64.getEncoder().encodeToString(image);
            }
            return null;
        }


}