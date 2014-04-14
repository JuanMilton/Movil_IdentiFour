package firstone.identi_four.movil.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import firstone.identi_four.movil.presentacion.R;
import firstone.identi_four.movil.presentacion.SettingsActivity;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class Servicio extends Service {

	private static final String TAG = "Service";
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG,"onCreate ---------->>>>> ");
	}
	
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand ---------->>>>> ");
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setAutoCancel(true)
                .setContentTitle("DJ-Android notification")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText("Hello World!");
        
        
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        Intent resultIntent = new Intent(this, SettingsActivity.class);
        
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(SettingsActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mBuilder.setDefaults(Notification.DEFAULT_ALL);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(100, mBuilder.build());
        
        return START_STICKY;
    }
	
	@Override
    public void onDestroy() {
		Log.i(TAG,"onDestroy --------------------->>>>>> ");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
