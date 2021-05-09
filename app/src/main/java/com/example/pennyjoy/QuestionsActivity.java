package com.example.pennyjoy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pennyjoy.Fragments.AddGoodFragment;

public class QuestionsActivity extends AppCompatActivity {
    //массивы из вопросов и ответов
    String[] questions;
    String[] answers;


    //вопрос
    private Animation question_button_animation_to_left;
    private Animation question_button_animation_to_left2;

    private TextView lblQuestion;


    //1 кнопка
    private Animation first_button_animation_to_left;
    private Animation first_button_animation_to_left2;

    private AppCompatButton firstAnswerBtn;

    //2 кнопка
    private Animation second_button_animation_to_left;
    private Animation second_button_animation_to_left2;

    AppCompatButton secondAnswerBtn;

    //3 кнопка
    private Animation third_button_animation_to_left;
    private Animation third_button_animation_to_left2;

    AppCompatButton thirdAnswerBtn;

    //4 кнопка
    private Animation fourth_button_animation_to_left;
    private Animation fourth_button_animation_to_left2;

    AppCompatButton fourthAnswerBtn;
    //каунтеры для кол-ва кликов
    private int counterOfClicksForQuestions =0;
    private int counterOfClicksForAnswers=0;

    //массивы для получения ответа
    private int[] categorical;
    private int[]transitional;

    //декларация AlertDialog
    private AlertDialog.Builder alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);


        //инициализация массивов
        questions=getResources().getStringArray(R.array.questions);
        answers=getResources().getStringArray(R.array.answers);

        categorical=new int[5];
        transitional=new int[3];
        alertDialog = new AlertDialog.Builder(this);
        //вопрос
        lblQuestion=findViewById(R.id.lblQuestion);
        lblQuestion.setText(questions[counterOfClicksForQuestions]);
        question_button_animation_to_left=AnimationUtils.loadAnimation(this,R.anim.question_animation_to_left);
        question_button_animation_to_left2=AnimationUtils.loadAnimation(this,R.anim.question_animation_to_left2);


        //1 кнопка
        firstAnswerBtn =findViewById(R.id.answerFirst);
        firstAnswerBtn.setText(answers[counterOfClicksForAnswers++]);
        first_button_animation_to_left= AnimationUtils.loadAnimation(this,R.anim.first_button_animation_to_left);
        first_button_animation_to_left2= AnimationUtils.loadAnimation(this,R.anim.first_button_animation_to_left2);

        //2 кнопка
        secondAnswerBtn =findViewById(R.id.answerSecond);
        secondAnswerBtn.setText(answers[counterOfClicksForAnswers++]);
        second_button_animation_to_left= AnimationUtils.loadAnimation(this,R.anim.second_button_animation_to_left);
        second_button_animation_to_left2= AnimationUtils.loadAnimation(this,R.anim.second_button_animation_to_left2);

        //3 кнопка
        thirdAnswerBtn =findViewById(R.id.answerThird);
        thirdAnswerBtn.setText(answers[counterOfClicksForAnswers++]);
        third_button_animation_to_left= AnimationUtils.loadAnimation(this,R.anim.third_button_animation_to_left);
        third_button_animation_to_left2= AnimationUtils.loadAnimation(this,R.anim.third_button_animation_to_left2);

        //4 кнопка
        fourthAnswerBtn =findViewById(R.id.answerFourth);
        fourthAnswerBtn.setText(answers[counterOfClicksForAnswers++]);
        fourth_button_animation_to_left= AnimationUtils.loadAnimation(this,R.anim.fourth_button_animation_to_left);
        fourth_button_animation_to_left2= AnimationUtils.loadAnimation(this,R.anim.fourth_button_animation_to_left2);










        //положительный категоричный
        firstAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counterOfClicksForAnswers >= 16 || counterOfClicksForQuestions >= 5){
                    categorical[counterOfClicksForQuestions]=1;
                    boolean result=getTheBiggestList(categorical,transitional);
                    int counterOfMinus=0;
                    int counterOfPlus=0;
                    if (result) {
                        for (int element:categorical) {
                            if(element<0){
                                counterOfMinus++;
                            }else{
                                counterOfPlus++;
                            }
                        }
                        intentWithCategorical(counterOfMinus,counterOfPlus);
                    }else{
                        for (int element:transitional) {
                            if(element<0){
                                counterOfMinus++;
                            }else{
                                counterOfPlus++;
                            }
                        }
                        alertDialog(counterOfMinus,counterOfPlus);
                    }
                }else {
                    categorical[counterOfClicksForQuestions]=1;
                    counterOfClicksForQuestions++;
                    startAnimationForEverything();
                }

            }
        });
        //отрицательный категоричный
        secondAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counterOfClicksForAnswers >= 16 || counterOfClicksForQuestions >= 5){
                    categorical[counterOfClicksForQuestions]= -1;
                    boolean result=getTheBiggestList(categorical,transitional);
                    int counterOfMinus=0;
                    int counterOfPlus=0;
                    if (result) {
                        for (int element:categorical) {
                            if(element<0){
                                counterOfMinus++;
                            }else{
                                counterOfPlus++;
                            }
                        }
                       intentWithCategorical(counterOfMinus,counterOfPlus);
                    }else{
                        for (int element:transitional) {
                            if(element<0){
                                counterOfMinus++;
                            }else{
                                counterOfPlus++;
                            }
                        }
                        alertDialog(counterOfMinus,counterOfPlus);
                    }
                }
                else {
                    categorical[counterOfClicksForQuestions]= -1;
                    counterOfClicksForQuestions++;
                    startAnimationForEverything();
                }

            }
        });
        //положительный переходный
        thirdAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counterOfClicksForAnswers >= 16 || counterOfClicksForQuestions >= 5) {
                    boolean result = getTheBiggestList(categorical, transitional);
                    int counterOfMinus = 0;
                    int counterOfPlus = 0;
                    if (result) {
                        for (int element : categorical) {
                            if (element < 0) {
                                counterOfMinus++;
                            }
                            else {
                                counterOfPlus++;
                            }
                        }
                       intentWithCategorical(counterOfMinus, counterOfPlus);
                    }


                    //переходные
                    else {
                        for (int element : transitional) {
                            if (element < 0) {
                                counterOfMinus++;
                            } else {
                                counterOfPlus++;
                            }
                        }

                        //тут метод с ад вызываем
                        alertDialog(counterOfMinus, counterOfPlus);
                    }
                }
                else {
                    transitional[counterOfClicksForQuestions]=1;
                    counterOfClicksForQuestions++;
                    startAnimationForEverything();
                }

            }
        });
        //отрицательный переходный
        fourthAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counterOfClicksForAnswers >= 16 || counterOfClicksForQuestions >= 5){
                    boolean result=getTheBiggestList(categorical,transitional);
                    int counterOfMinus=0;
                    int counterOfPlus=0;
                    if (result) {
                        for (int element:categorical) {
                            if(element<0){
                                counterOfMinus++;
                            }else{
                                counterOfPlus++;
                            }
                        }

                        intentWithCategorical(counterOfMinus,counterOfPlus);
                    }
                    else{
                        for (int element:transitional) {
                            if(element<0){
                                counterOfMinus++;
                            }else{
                                counterOfPlus++;
                            }
                        }
                        alertDialog(counterOfMinus,counterOfPlus);
                    }
                }else {
                    transitional[counterOfClicksForQuestions]= -1;
                    counterOfClicksForQuestions++;
                    startAnimationForEverything();
                }

            }
        });


    }

    public boolean getTheBiggestList(int[]ar1,int[]ar2 ){
        int res=0;
        for (int element1:ar1) {
            res+=Math.abs(element1);
        }
        int res2=0;
        for (int element2:ar2) {
            res2+=Math.abs(element2);
        }
        if(res>res2){
            return true;
        }
        return false;
    }


    public void startAnimationForEverything(){
        lblQuestion.startAnimation(question_button_animation_to_left);
        //анимация для вопроса
        question_button_animation_to_left.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                lblQuestion.setText(questions[counterOfClicksForQuestions]);
                firstAnswerBtn.startAnimation(first_button_animation_to_left);
                lblQuestion.startAnimation(question_button_animation_to_left2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        //анимация для 1 конпки
        first_button_animation_to_left.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                firstAnswerBtn.setText(answers[counterOfClicksForAnswers++]);
                secondAnswerBtn.startAnimation(second_button_animation_to_left);
                firstAnswerBtn.startAnimation(first_button_animation_to_left2);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //анимация для 2 конпки
        second_button_animation_to_left.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(counterOfClicksForAnswers<14) {
                    secondAnswerBtn.setText(answers[counterOfClicksForAnswers++]);
                    thirdAnswerBtn.startAnimation(third_button_animation_to_left);
                    secondAnswerBtn.startAnimation(second_button_animation_to_left2);
                }else{
                    secondAnswerBtn.setText(answers[counterOfClicksForAnswers++]);
                    secondAnswerBtn.startAnimation(second_button_animation_to_left2);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //анимация для 3 конпки
        third_button_animation_to_left.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(counterOfClicksForAnswers<14) {
                    thirdAnswerBtn.setText(answers[counterOfClicksForAnswers++]);
                    fourthAnswerBtn.startAnimation(fourth_button_animation_to_left);
                    thirdAnswerBtn.startAnimation(third_button_animation_to_left2);
                }else{
                    fourthAnswerBtn.startAnimation(fourth_button_animation_to_left);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //анимация для 4 конпки
        fourth_button_animation_to_left.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(counterOfClicksForAnswers<14) {
                    fourthAnswerBtn.setText(answers[counterOfClicksForAnswers++]);
                    fourthAnswerBtn.startAnimation(fourth_button_animation_to_left2);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void alertDialog(int cMinus, int cPlus){

                alertDialog.setCancelable(false);
                alertDialog.setTitle("Внимание!").setMessage("У вас есть цели (количество)! Готовы ли вы пренебречь целями в пользу покупки?");
                alertDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), AddGoodAndOtherActivity.class);
                        intent.putExtra("flagOfResultInFragments", 3);

                        startActivity(intent);

                        finish();

                    }
                });
                alertDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(cMinus>cPlus){
                            Intent intent = new Intent(getApplicationContext(), ResultOfQuizActivity.class);
                            Toast.makeText(getApplicationContext(), "неа", Toast.LENGTH_SHORT).show();
                            intent.putExtra("flagOfResult", -2);//ОТРИЦАТЕЛЬНЫЙ ОТВЕТ (мб нет)
                            startActivity(intent);
                            finish();
                        }

                        else {
                            Intent intent = new Intent(getApplicationContext(), ResultOfQuizActivity.class);
                            intent.putExtra("flagOfResult", 2);//положительный ответ (мб да)
                            startActivity(intent);
                            finish();
                        }
                    }
                });
                alertDialog.show();
            }


            public void intentWithCategorical(int cMinus, int cPlus){
                if (cMinus > cPlus) {
                    Toast.makeText(getApplicationContext(), "НЕТ!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ResultOfQuizActivity.class);
                    intent.putExtra("flagOfResult", -1);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "ДА!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ResultOfQuizActivity.class);
                    intent.putExtra("flagOfResult", 1);
                    startActivity(intent);
                    finish();
                }

            }
    }
