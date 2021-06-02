package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.pennyjoy.R;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private  LayoutInflater layoutInflater;

    //List-ы для иконок и текста
    private int[] icons=new int[]{
            R.drawable.lamp_for_slider,
            R.drawable.clock_icon_for_slider,
            R.drawable.pig_for_slider,
            R.drawable.money_for_slider
    };

    private String [] descs=new String[]{
            "Получите уверенность в срочных покупках вместе с “Советником”",
            "Обдумайте значимые покупки с помощью “Таймера”",
            "Научитесь откладывать на свои цели используя “Копилку” ",
            "Распоряжайтесь своим бюджетом правильно вместе с PennyJoy"

    };
    public SliderAdapter(Context context){
        this.context=context;
    }




    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==  object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //делаем инфлейтер
      layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
      //получили вью
      View view = layoutInflater.inflate(R.layout.slide_template,container,false);

        ImageView iconOfSlide=view.findViewById(R.id.iconOfSlide);
        TextView descOfSlide=view.findViewById(R.id.lblOfSlide);

        iconOfSlide.setImageResource(icons[position]);
        descOfSlide.setText(descs[position]);

        container.addView(view);

      return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
