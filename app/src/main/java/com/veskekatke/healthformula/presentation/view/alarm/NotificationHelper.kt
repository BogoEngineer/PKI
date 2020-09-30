package com.veskekatke.healthformula.presentation.view.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.provider.Settings
import androidx.core.app.NotificationCompat
import com.veskekatke.healthformula.R
import com.veskekatke.healthformula.presentation.view.activities.MainActivity

class NotificationHelper(context: Context) {
    private var mContext = context
    private val NOTIFICATION_CHANNEL_ID = "10001";

    fun createNotification() {
        val intent = Intent(mContext , MainActivity::class.java);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        val resultPendingIntent = PendingIntent.getActivity(mContext,
        0 /* Request code */, intent,
        PendingIntent.FLAG_UPDATE_CURRENT);


        val mBuilder = NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_pill);
        mBuilder.setContentTitle(mContext.getString(R.string.reminder))
            .setContentText(mContext.getString(R.string.notification_text))
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(mContext.getString(R.string.notification_text)))
            .setAutoCancel(false)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setContentIntent(resultPendingIntent)

        val mNotificationManager = (mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            val importance = NotificationManager.IMPORTANCE_HIGH;
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.lightColor = Color.RED;
            notificationChannel.enableVibration(true);
            notificationChannel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400);
            //mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        //mNotificationManager != null;
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());
    }
}