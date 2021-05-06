package com.example.pennyjoy.Fragments;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.pennyjoy.R;

import java.util.ArrayList;

import Models.Category;
import Notifycations.ReminderForTimer;

public class FragmentTimer extends Fragment {
    private Spinner dropDownMonth,dropDownDay,dropDownHour;
    private AppCompatButton setTimerBtn;
    private EditText txtNameOfGoodForTimer;


    private String[] months=new String[]{"0 месяцев","1 месяц","2 месяца","3 месяца","4 месяца"
            ,"5 месяцев","6 месяцев","7 месяцев","8 месяцев","9 месяцев","10 месяцев"
            ,"11 месяцев","12 месяцев"};
    private String[] days=new String[]{"0 дней","1 день","2 дня","3 дня","4 дня"
            ,"5 дней","6 дней","7 дней","8 дней","9 дней","10 дней"
            ,"11 дней","12 дней","13 дней","14 дней","15 дней","16 дней"
            ,"17 дней","18 дней","19 дней","20 дней","21 день","22 дня"
            ,"23 дня","24 дня", "25 дней","26 дней","27 дней","28 дней"
            ,"29 дней","30 дней","31 день",};
    private String[] hours=new String[]{"0 часов","1 час","2 часа","3 часа","4 часа"
            ,"5 часов","6 часов","7 часов","8 часов","9 часов","10 часов"
            ,"11 часов","12 часов","13 часов","14 часов","15 часов","16 часов"
            ,"17 часов","18 часов","19 часов","20 часов","21 час","22 часа"
            ,"23 часа","24 часа"};

    private int month,day,hour;

    private long finalBettaMonth, finalMonth,finalDay,finalHour,finalDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.timer_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        createNotificationChannel();

        txtNameOfGoodForTimer=view.findViewById(R.id.txtNameOfGoodForTimer);

        setTimerBtn=view.findViewById(R.id.setTimerBtn);



        dropDownMonth=view.findViewById(R.id.monthDropDown);


        //делаю адаптер для вложенного списка
        ArrayAdapter<String> monthAdapter=new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item,months);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropDownMonth.setAdapter(monthAdapter);
        //----------------------




        dropDownDay=view.findViewById(R.id.dayDropDown);


        //делаю адаптер для вложенного списка
        ArrayAdapter<String> dayAdapter=new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item,days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropDownDay.setAdapter(dayAdapter);
        //----------------------



        dropDownHour=view.findViewById(R.id.hourDropDown);


        //делаю адаптер для вложенного списка
        ArrayAdapter<String> hourAdapter=new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item,hours);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropDownHour.setAdapter(hourAdapter);
        //----------------------




        setTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtNameOfGoodForTimer.getText().toString().isEmpty() && !dropDownMonth.getSelectedItem().toString().equals("0 месяцев")
                || !dropDownDay.getSelectedItem().toString().equals("0 дней") || !dropDownHour.getSelectedItem().toString().equals("0 часов") ) {
                    makingIntDate();
                    makingLongData();

                    Toast.makeText(getContext(), "Таймер установлен!", Toast.LENGTH_SHORT).show();

                    txtNameOfGoodForTimer.getText().clear();
                    dropDownMonth.setSelection(0);
                    dropDownDay.setSelection(0);
                    dropDownHour.setSelection(0);

                    Intent intent = new Intent(getContext(),ReminderForTimer.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

                    long timeAtButtonClicked = System.currentTimeMillis();
                    long timeForCheck=1000* 10;

                    //надо юзать finalDate
                    alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClicked + timeForCheck, pendingIntent);

                }else{
                    Toast.makeText(getContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void makingIntDate(){
        //делаю число из селекта
        String selectedMonthString=dropDownMonth.getSelectedItem().toString();
        String countOfMoth="";
        for(int i=0;i<selectedMonthString.length();i++){
            if(selectedMonthString.charAt(i)==' '){
                break;
            }
            countOfMoth+=selectedMonthString.charAt(i);
        }
        month=Integer.parseInt(countOfMoth);
        //--------------------------------

        //делаю число из селекта
        String selectedDayString=dropDownDay.getSelectedItem().toString();
        String countOfDay="";
        for(int i=0;i<selectedDayString.length();i++){
            if(selectedDayString.charAt(i)==' '){
                break;
            }
            countOfDay+=selectedDayString.charAt(i);
        }
        day=Integer.parseInt(countOfDay);
        //--------------------------------


        //делаю число из селекта
        String selectedHourString=dropDownHour.getSelectedItem().toString();
        String countOfHour="";
        for(int i=0;i<selectedHourString.length();i++){
            if(selectedHourString.charAt(i)==' '){
                break;
            }
            countOfHour+=selectedHourString.charAt(i);
        }
        hour=Integer.parseInt(countOfHour);
        //--------------------------------
    }

    public void makingLongData(){
        //я сделал с finalBettaMonth  потому что некорректно считает с одним finalMonth
        finalBettaMonth=1000* 60 * 60 * 24;
        finalMonth=finalBettaMonth*(31*month);

        finalDay=1000* 60 * 60 * (24* day);
        finalHour=1000* 60 * (60* hour);
        finalDate=finalMonth+finalDay+finalHour;

    }

    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.O){
            String name="TimerReminderChannel";
            String description="Channel for timer reminder";
            int importance= NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel=new NotificationChannel("notifyTimer",name,importance);
            channel.setDescription(description);


            NotificationManager notificationManager=getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
    }

}
