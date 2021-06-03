package com.example.pennyjoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import Adapters.SliderAdapter;

public class SlideActivity extends AppCompatActivity {
    private LinearLayout dotsLinear;
    private ViewPager viewPager;

    private SliderAdapter sliderAdapter;

    private TextView[] dots;

    private Button closeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        closeBtn=findViewById(R.id.btnCloseSlide);

        viewPager=findViewById(R.id.viewPager);
        dotsLinear=findViewById(R.id.linearWithDots);

        sliderAdapter = new SliderAdapter(getApplicationContext());

        addDotsIndicator(0);

        viewPager.setAdapter(sliderAdapter);

        viewPager.addOnPageChangeListener(onPageChangeListener);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);

                startActivity(intent);
            }
        });


    }

    //делаем точки индикации
    public void addDotsIndicator(int position){
        dots=new TextView[4];
        dotsLinear.removeAllViews();

        for(int i=0;i<dots.length;i++){
            dots[i]=new TextView(getApplicationContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(42);
            dots[i].setTextColor(getResources().getColor(R.color.transparentWhite));

            dotsLinear.addView(dots[i]);
        }

        if(dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.white));
        }


    }

    ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);

            //если открыди последний активити мы даем возмодность использовать кнопку "Закрыть"
            if(position == 3){
                closeBtn.setVisibility(View.VISIBLE);
                closeBtn.setEnabled(true);
            }else{
                closeBtn.setVisibility(View.GONE);
                closeBtn.setEnabled(false);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}