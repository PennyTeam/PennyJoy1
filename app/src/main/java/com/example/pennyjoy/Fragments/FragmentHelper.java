package com.example.pennyjoy.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.example.pennyjoy.QuestionsActivity;
import com.example.pennyjoy.R;

import java.util.Timer;
import java.util.TimerTask;

public class FragmentHelper extends Fragment {


    private float x1, x2;
    private static int MIN_DISTANCE = 150;



    private ImageView arrow;

    private Animation arrow_animation_to_left;
    private Animation arrow_animation_to_left2;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_helper, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        arrow_animation_to_left = AnimationUtils.loadAnimation(getContext(), R.anim.arrow_animation_left);
        arrow_animation_to_left2 = AnimationUtils.loadAnimation(getContext(), R.anim.arrow_animation_left2);
        arrow=view.findViewById(R.id.arrow);

        //делаем анимацию
        arrow_animation_to_left.setRepeatCount(Animation.INFINITE);
        arrow.startAnimation(arrow_animation_to_left);

        arrow_animation_to_left.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                arrow.startAnimation(arrow_animation_to_left2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        arrow_animation_to_left2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        arrow.startAnimation(arrow_animation_to_left);
                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });






        //трэкаем свайп влево
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        float valueOfX = x2 - x1;

                        if (Math.abs(valueOfX) > MIN_DISTANCE) {
                            if (x1 > x2) {
                                Intent intent = new Intent(v.getContext(), QuestionsActivity.class);
                                startActivity(intent);
                                ((Activity) getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                getActivity().finish();
                            }
                        }


                }
                return true;
            }
        });
    }

}
