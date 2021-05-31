package Notifycations;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.pennyjoy.AddGoodAndOtherActivity;
import com.example.pennyjoy.R;
import com.example.pennyjoy.SplashScreenActivity;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class ReminderForTimer extends BroadcastReceiver {
    private int num = (int) System.currentTimeMillis();
    private PendingIntent pendingIntent;
    private String nameOfGood;




    @Override
    public void onReceive(Context context, Intent intent) {

        nameOfGood=intent.getStringExtra("nameOfGood");





        ActivityManager m = (ActivityManager) context.getSystemService( ACTIVITY_SERVICE );
        List<ActivityManager.RunningTaskInfo> runningTaskInfoList =  m.getRunningTasks(10);
        Iterator<ActivityManager.RunningTaskInfo> itr = runningTaskInfoList.iterator();
        int n=0;
        while(itr.hasNext()){
            n++;
            itr.next();
        }
        if(n==1 ){ // приложение закрыто
            intent.setClass(context, SplashScreenActivity.class);
        } else {// приложение открыто и находится либо в истории, либо прям открыто

            intent.setClass(context, AddGoodAndOtherActivity.class);
        }


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
