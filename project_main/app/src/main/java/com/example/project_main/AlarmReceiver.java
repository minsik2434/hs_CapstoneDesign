package com.example.project_main;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {

        int reqCode = intent.getExtras().getInt("requestCode");
        Log.i(TAG, "onrecive : " + reqCode);

        if(reqCode == 1) {
            Log.i(TAG, "recived_code : 001");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");

            builder.setSmallIcon(R.drawable.warning_icon);
            builder.setContentTitle("알림 제목");
            builder.setContentText("알람 세부 텍스트");
            // 사용자가 탭을 클릭하면 자동 제거
            builder.setAutoCancel(true);
            builder.setDefaults(Notification.DEFAULT_VIBRATE); //진동효과
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_IMMUTABLE));
            // 알림 표시
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
            }
            // id값은
            // 정의해야하는 각 알림의 고유한 int값
            notificationManager.notify(reqCode, builder.build());
        }
        else if(reqCode == 2)
        {
            Log.i(TAG, "recived_code : 002");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
            builder.setSmallIcon(R.drawable.warning_icon);
            builder.setContentTitle("혹시 기록을 하셧나요?");
            builder.setContentText("기록하고나서 12시간동안 아무것도 기록하지 않았습니다.");
            builder.setAutoCancel(true); // 사용자가 탭을 클릭하면 자동 제거
            builder.setDefaults(Notification.DEFAULT_VIBRATE); //진동효과
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT); //우선순위
            builder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_IMMUTABLE)); //눌렀을때 사라짐 효과
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); // 알림 표시
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
            }
            // id값은
            // 정의해야하는 각 알림의 고유한 int값
            notificationManager.notify(reqCode, builder.build());


        }
        else if(reqCode == 10)
        {
            Log.i(TAG, "recived_code : 010");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
            builder.setSmallIcon(R.drawable.warning_icon);
            builder.setContentTitle("주의해주세요!");
            builder.setContentText("탄수화물이 기준치를 초과하였습니다.");
            builder.setAutoCancel(true); // 사용자가 탭을 클릭하면 자동 제거
            builder.setDefaults(Notification.DEFAULT_VIBRATE); //진동효과
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT); //우선순위
            builder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_IMMUTABLE)); //눌렀을때 사라짐 효과
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); // 알림 표시
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
            }
            // id값은
            // 정의해야하는 각 알림의 고유한 int값
            notificationManager.notify(reqCode, builder.build());


        }
        else if(reqCode == 11)
        {
            Log.i(TAG, "recived_code : 011");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
            builder.setSmallIcon(R.drawable.warning_icon);
            builder.setContentTitle("주의해주세요!");
            builder.setContentText("단백질이 기준치를 초과하였습니다.");
            builder.setAutoCancel(true); // 사용자가 탭을 클릭하면 자동 제거
            builder.setDefaults(Notification.DEFAULT_VIBRATE); //진동효과
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT); //우선순위
            builder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_IMMUTABLE)); //눌렀을때 사라짐 효과
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); // 알림 표시
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
            }
            // id값은
            // 정의해야하는 각 알림의 고유한 int값
            notificationManager.notify(reqCode, builder.build());


        }
        else if(reqCode == 12)
        {
            Log.i(TAG, "recived_code : 012");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
            builder.setSmallIcon(R.drawable.warning_icon);
            builder.setContentTitle("주의해주세요!");
            builder.setContentText("지방이 기준치를 초과하였습니다.");
            builder.setAutoCancel(true); // 사용자가 탭을 클릭하면 자동 제거
            builder.setDefaults(Notification.DEFAULT_VIBRATE); //진동효과
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT); //우선순위
            builder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_IMMUTABLE)); //눌렀을때 사라짐 효과
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); // 알림 표시
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
            }
            // id값은
            // 정의해야하는 각 알림의 고유한 int값
            notificationManager.notify(reqCode, builder.build());
        }
        else if(reqCode == 13)
        {
            Log.i(TAG, "recived_code : 013");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
            builder.setSmallIcon(R.drawable.warning_icon);
            builder.setContentTitle("주의해주세요!");
            builder.setContentText("당이 기준치를 초과하였습니다.");
            builder.setAutoCancel(true); // 사용자가 탭을 클릭하면 자동 제거
            builder.setDefaults(Notification.DEFAULT_VIBRATE); //진동효과
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT); //우선순위
            builder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_IMMUTABLE)); //눌렀을때 사라짐 효과
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); // 알림 표시
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
            }
            // id값은
            // 정의해야하는 각 알림의 고유한 int값
            notificationManager.notify(reqCode, builder.build());
        }
        else if(reqCode == 14)
        {
            Log.i(TAG, "recived_code : 014");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
            builder.setSmallIcon(R.drawable.warning_icon);
            builder.setContentTitle("주의해주세요!");
            builder.setContentText("나트륨이 기준치를 초과하였습니다.");
            builder.setAutoCancel(true); // 사용자가 탭을 클릭하면 자동 제거
            builder.setDefaults(Notification.DEFAULT_VIBRATE); //진동효과
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT); //우선순위
            builder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_IMMUTABLE)); //눌렀을때 사라짐 효과
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); // 알림 표시
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
            }
            // id값은
            // 정의해야하는 각 알림의 고유한 int값
            notificationManager.notify(reqCode, builder.build());
        }
        else if(reqCode == 15)
        {
            Log.i(TAG, "recived_code : 012");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
            builder.setSmallIcon(R.drawable.warning_icon);
            builder.setContentTitle("주의해주세요!");
            builder.setContentText("콜레스테롤이 기준치를 초과하였습니다.");
            builder.setAutoCancel(true); // 사용자가 탭을 클릭하면 자동 제거
            builder.setDefaults(Notification.DEFAULT_VIBRATE); //진동효과
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT); //우선순위
            builder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_IMMUTABLE)); //눌렀을때 사라짐 효과
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); // 알림 표시
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
            }
            // id값은
            // 정의해야하는 각 알림의 고유한 int값
            notificationManager.notify(reqCode, builder.build());
        }
        else if(reqCode == 16)
        {
            Log.i(TAG, "recived_code : 016");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
            builder.setSmallIcon(R.drawable.warning_icon);
            builder.setContentTitle("주의해주세요!");
            builder.setContentText("트랜스지방이 기준치를 초과하였습니다.");
            builder.setAutoCancel(true); // 사용자가 탭을 클릭하면 자동 제거
            builder.setDefaults(Notification.DEFAULT_VIBRATE); //진동효과
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT); //우선순위
            builder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_IMMUTABLE)); //눌렀을때 사라짐 효과
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); // 알림 표시
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
            }
            // id값은
            // 정의해야하는 각 알림의 고유한 int값
            notificationManager.notify(reqCode, builder.build());
        }
        else if(reqCode == 17)
        {
            Log.i(TAG, "recived_code : 017");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
            builder.setSmallIcon(R.drawable.warning_icon);
            builder.setContentTitle("주의해주세요!");
            builder.setContentText("포화지방산이 기준치를 초과하였습니다.");
            builder.setAutoCancel(true); // 사용자가 탭을 클릭하면 자동 제거
            builder.setDefaults(Notification.DEFAULT_VIBRATE); //진동효과
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT); //우선순위
            builder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_IMMUTABLE)); //눌렀을때 사라짐 효과
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); // 알림 표시
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
            }
            // id값은
            // 정의해야하는 각 알림의 고유한 int값
            notificationManager.notify(reqCode, builder.build());
        }
        else;

    }
}
