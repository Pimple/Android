package henrikbn.opgaver2015_02_27;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by HenrikBN on 04-03-2015.
 */
public class JodaApplication extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		JodaTimeAndroid.init(this);
	}
}
