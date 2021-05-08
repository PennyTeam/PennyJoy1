package com.example.pennyjoy.Fragments;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.text.Layout;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;

import com.example.pennyjoy.QuestionsActivity;
import com.example.pennyjoy.R;

public class FragmentHelper extends Fragment {


    private float x1,x2;
    private static int MIN_DISTANCE=150;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_helper, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //инициализируем детектор жестов


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1=event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2=event.getX();
                        float valueOfX=x2-x1;

                        if(Math.abs(valueOfX)>MIN_DISTANCE) {
                            if (x1 > x2) {
                                Intent intent = new Intent(v.getContext(), QuestionsActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                ((Activity) getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }
                        }


                }
                return true;
            }
        });
    }


}
