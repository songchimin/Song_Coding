package com.study.android.mytry.chat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.study.android.mytry.R;
import com.study.android.mytry.main.MainActivity;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "lecture";
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private int count = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived() 호출됨.");

        // 앱이 실행 중일 때 알림 메시지가 올 경우 처리
        // 메시지 안에 payload 데이터가 있을 경우 : 가령 message:value
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            Log.d(TAG, "Message data payload: " + data.toString());

            String contents = data.get("message");

            processMsg(contents);
        }

        // 시스템 알림으로 오는 제목과 내용을 후킹할 수 있다.
        // 그러나 앱이 실행되고 있을 때는 굳이 처리할 필요가 없다.

        if (remoteMessage.getNotification() != null) {
            String notiTitle = remoteMessage.getNotification().getTitle();
            String notiBody = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message Notification Title: " + notiTitle);
            Log.d(TAG, "Message Notification Body: " + notiBody);
            NotificationSomethings(notiTitle, notiBody);

        }

    }


    private void processMsg(String contents) {
        // 메시지가 왔을 때의 처리를 만든다.
        // 이 예제에서는 메시지 내용을 메인으로 보낸다.
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("message", contents);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        getApplicationContext().startActivity(intent);
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }



    public void NotificationSomethings(String title, String text) {


        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra("notificationId", count); //전달할 값
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,  PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground)) //BitMap 이미지 요구
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_try_round))
                .setContentTitle(title)
                .setContentText(text)
                // 더 많은 내용이라서 일부만 보여줘야 하는 경우 아래 주석을 제거하면 setContentText에 있는 문자열 대신 아래 문자열을 보여줌
                //.setStyle(new NotificationCompat.BigTextStyle().bigText("더 많은 내용을 보여줘야 하는 경우..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent) // 사용자가 노티피케이션을 탭시 ResultActivity로 이동하도록 설정
                .setAutoCancel(true);

        //OREO API 26 이상에서는 채널 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.fcm); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남
            CharSequence channelName  = "노티페케이션 채널";
            String description = "오레오 이상을 위한 것임";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName , importance);
            channel.setDescription(description);

            // 노티피케이션 채널을 시스템에 등록
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);

        }else builder.setSmallIcon(R.mipmap.ic_launcher_try_round); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남

        assert notificationManager != null;
        notificationManager.notify(1234, builder.build()); // 고유숫자로 노티피케이션 동작시킴

    }

}
