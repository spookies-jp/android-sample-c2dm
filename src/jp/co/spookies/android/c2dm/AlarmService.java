package jp.co.spookies.android.c2dm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;

public class AlarmService extends Service {
	private NotificationManager notificationManager;
	Vibrator vibrator;
	MediaPlayer player;

	@Override
	public void onCreate() {
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		player = MediaPlayer.create(this, uri);
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
	}

	@Override
	public void onDestroy() {
		notificationManager.cancel(R.string.app_name);
		vibrator.cancel();
		player.pause();
		player.stop();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Intent bellIntent = new Intent(this, Received.class);
		bellIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(bellIntent);
		Notification notification = new Notification(R.drawable.icon, "",
				System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				bellIntent, 0);
		notification.setLatestEventInfo(this, getText(R.string.app_name), "",
				contentIntent);
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		notificationManager.notify(R.string.app_name, notification);
		player.start();
		vibrator.vibrate(5 * 1000);
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
