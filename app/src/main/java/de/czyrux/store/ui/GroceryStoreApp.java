package de.czyrux.store.ui;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import de.czyrux.store.R;
import de.czyrux.store.inject.DefaultDependenciesFactory;
import de.czyrux.store.inject.Injector;

public class GroceryStoreApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Injector.using(new DefaultDependenciesFactory(), this);
		showAnalyticsLogsNotification();

	}

	private void showAnalyticsLogsNotification(){
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this)
						.setSmallIcon(R.mipmap.ic_launcher)
						.setContentTitle("Analytics logging enabled")
						.setOngoing(true);

		Intent resultIntent = new Intent(this, AnalyticsLoggerActivity.class);

		// Because clicking the notification opens a new ("special") activity, there's
		// no need to create an artificial back stack.
		PendingIntent resultPendingIntent =
				PendingIntent.getActivity(
						this,
						0,
						resultIntent,
						PendingIntent.FLAG_UPDATE_CURRENT
				);
		mBuilder.setContentIntent(resultPendingIntent);
		int mNotificationId = 001;
		// Gets an instance of the NotificationManager service
		NotificationManager mNotifyMgr =
				(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
	}
}
