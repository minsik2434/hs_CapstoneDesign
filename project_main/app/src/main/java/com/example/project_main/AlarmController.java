package com.example.project_main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class AlarmController {
    private static final String TAG = "AlarmController";
    private Context context;

    private static AlarmController sInstance;
    private AlarmManager alarmMgr;

    public AlarmController(Context context) {
        this.context = context;
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(int reqCode, long timeMill) {
        Log.i(TAG, "setAlarm req : " + reqCode + ", timeMill : " + timeMill);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("requestCode", reqCode);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, reqCode, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        60* 1000 *timeMill, alarmIntent);
    }

    public void cancelAlarm(int reqCode) {
        Log.i(TAG, "cancelAlarm req : " + reqCode);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, reqCode, intent, PendingIntent.FLAG_IMMUTABLE);

        if (alarmMgr != null) {
            alarmMgr.cancel(alarmIntent);
        }
    }
}
