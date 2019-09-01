package com.korea.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

//이게 안나온다고 하면 build.gradle 의 implementation이 잘못 되었을 수 있다.
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "service_tag::";

    //fcm으로부터 메세지를 수신받으면 호출되는 콜백 메서드
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //fcm에서 보내는 방법에 따라 2가지로 수신이 된다.
        Log.d(TAG, "message 수신 성공");
        if (remoteMessage.getNotification().getBody() != null) {
            String msg = remoteMessage.getNotification().getBody();
        } else {
            String msg = remoteMessage.getData().get("message");
            //ex) 127.0.0.1/fcm?message=data1234
            //수신받는건 여기서 끄읕
            sendNotification(msg);
        }
    }

    //메세지 수신 후에 처리를 위한 메소드
    //-공지사항
    private void sendNotification(String msg) {
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//라이프사이클- 해당 액티비티가 있다면 clear하고 액티비티 최상단으로 만들어라.

        intent.putExtra("message",msg);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //꼭 strings.xml을 통해서 만들어 줘야 한다.
        String _id = getString(R.string.default_channel_id);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder builder;
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, _id);
            NotificationChannel channel = new NotificationChannel(_id, "channel", NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(channel);
            builder= new Notification.Builder(getApplicationContext(),_id);
        } else {
            builder = new Notification.Builder(this);
        }
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("FCM message 수신");
        builder.setContentText(msg);
        builder.setAutoCancel(true);
        builder.setSound(defaultSound);
        builder.setContentIntent(pendingIntent);

        Notification notification=builder.build();
        nm.notify(0,notification);



    }
}
