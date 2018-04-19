package curatetechnologies.com.curate_consumer.network.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.modules.main.MainActivity;

/**
 * Created by mremondi on 4/2/18.
 */

public class CurateFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "CurateFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("HERE on message", "HERE");
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (notification != null) {
            createNotification(notification);
        }
    }

    private void createNotification(RemoteMessage.Notification notification){
        Intent intent = new Intent( this , MainActivity.class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this, "DEFAULT_CHANNEL")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel( true )
                .setSound(notificationSoundURI)
                .setPriority(4)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }
}
