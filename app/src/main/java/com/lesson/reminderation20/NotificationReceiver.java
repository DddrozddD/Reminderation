package com.lesson.reminderation20;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.lesson.reminderation20.AddRemindActivity;
import com.lesson.reminderation20.R;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ShowNotification(context, intent);
    }

    private void ShowNotification(Context context, Intent intent){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, AddRemindActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.favicon)
                .setContentTitle(intent.getStringExtra("title"))
                .setContentText(intent.getStringExtra("desc"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(AddRemindActivity.NOTIFICATION_ID,builder.build());
    }
}
