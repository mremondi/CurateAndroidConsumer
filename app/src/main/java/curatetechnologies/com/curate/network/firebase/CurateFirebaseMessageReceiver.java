package curatetechnologies.com.curate.network.firebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by mremondi on 4/2/18.
 */

public class CurateFirebaseMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(CurateFirebaseMessagingService.class.getName()));

    }
}
