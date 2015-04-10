package nope.opgaver2015_04_08;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by HenrikBN on 08-04-2015.
 */
public class James extends Service
{
	private AlarmManager alarmManager;
	private PendingIntent alarmIntent;

	@Override
	public void onCreate()
	{
		// super.onCreate();
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent("AlarmAction");
		alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// return super.onStartCommand(intent, flags, startId);
		Context context = getApplicationContext();

		int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;


		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
}
