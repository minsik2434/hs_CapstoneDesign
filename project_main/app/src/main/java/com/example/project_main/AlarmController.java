package com.example.project_main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import java.util.Calendar;

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

        //특정 시간 설정해서 알림
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 9 - 1, 22, 14, 0);
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), alarmIntent);

    }

    public void setAlarm2(int reqCode, long timeMill) {
        Log.i(TAG, "setAlarm req : " + reqCode + ", timeMill : " + timeMill);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("requestCode", reqCode);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, reqCode, intent, PendingIntent.FLAG_IMMUTABLE);

        //특정 시간에서 +n분 후 알림
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
