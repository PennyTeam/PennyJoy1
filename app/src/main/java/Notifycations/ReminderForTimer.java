package Notifycations;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pennyjoy.R;
import com.example.pennyjoy.SplashScreenActivity;

public class ReminderForTimer extends BroadcastReceiver {
    private int num = (int) System.currentTimeMillis();
    private PendingIntent pendingIntent;
    private String nameOfGood;



    @Override
    public void onReceive(Context context, Intent intent) {
        nameOfGood=intent.getStringExtra("nameOfGood");

        intent=new Intent(context, SplashScreenActivity.class);

        pendingIntent=PendingIntent.getActivity(context,0,intent,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"notifyTimer")

                .setSmallIcon(R.drawable.logoofpennyjoy)
                .setContentTitle("Время раздумий закончилось!")
                .setContentText("Таймер на обдумывание "+nameOfGood+ " подошел к концу.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager= NotificationManagerCompat.from(context);


        notificationManager.notify(num,builder.build());
    }


}
