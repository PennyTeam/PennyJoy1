package Notifycations;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pennyjoy.R;

public class ReminderForTimer extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"notifyTimer")
                //здесь должен быть наш лого!!!!!!!!!!!!!!!!!!!!!
                .setSmallIcon(R.drawable.lamp)
                .setContentTitle("Время раздумий закончилось!")
                .setContentText("Таймер на обдумывание покупки (здесь название товара) подошел к концу.")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManager= NotificationManagerCompat.from(context);

        //здесь уникальный айдишник должен быть для каждого нотификатиона, это как кей
        notificationManager.notify(121,builder.build());
    }
}
