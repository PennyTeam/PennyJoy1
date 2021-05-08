package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

public class QuestionsActivity extends AppCompatActivity {
    private AppCompatButton testBtn;
    private Animation frist_button_animation_to_left;
    private Animation frist_button_animation_to_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        testBtn=findViewById(R.id.test);
        frist_button_animation_to_left= AnimationUtils.loadAnimation(this,R.anim.frist_button_animation_to_left);
        frist_button_animation_to_right= AnimationUtils.loadAnimation(this,R.anim.first_button_animaton_to_right);

        frist_button_animation_to_left.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                testBtn.startAnimation(frist_button_animation_to_right);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*testBtn.startAnimation(frist_button_animation_to_left);
                testBtn.setText("WORK!!");
                testBtn.startAnimation(frist_button_animation_to_right);*/
                testBtn.startAnimation(frist_button_animation_to_left);


                testBtn.setText("WORK!!");
            }
        });

    }

}