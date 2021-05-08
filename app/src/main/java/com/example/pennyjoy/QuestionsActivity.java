package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

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



    private int counterOfClicksForQuestions =0;
    private int counterOfClicksForAnswers=0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);


        //инициализация массивов
        questions=getResources().getStringArray(R.array.questions);
        answers=getResources().getStringArray(R.array.answers);

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











        firstAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counterOfClicksForAnswers == 16 && counterOfClicksForQuestions == 5){
                    Toast.makeText(getApplicationContext(),"END!",Toast.LENGTH_SHORT);
                }else {
                    counterOfClicksForQuestions++;
                    counterOfClicksForAnswers++;
                    startAnimationForEverything();
                }

            }
        });


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
                secondAnswerBtn.setText(answers[counterOfClicksForAnswers++]);
                thirdAnswerBtn.startAnimation(third_button_animation_to_left);
                secondAnswerBtn.startAnimation(second_button_animation_to_left2);
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
                thirdAnswerBtn.setText(answers[counterOfClicksForAnswers++]);
                fourthAnswerBtn.startAnimation(fourth_button_animation_to_left);
                thirdAnswerBtn.startAnimation(third_button_animation_to_left2);
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
                fourthAnswerBtn.setText(answers[counterOfClicksForAnswers++]);
                fourthAnswerBtn.startAnimation(fourth_button_animation_to_left2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }




}