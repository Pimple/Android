package henrikbn.opgaver2015_01_30;

import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity
{
	private int daysMade;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		daysMade = 0;

		Button button = (Button)findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View _view)
			{
				TextView text = (TextView) findViewById(R.id.textView);
				switch (daysMade)
				{
					case (0):
						text.setText(R.string.firstday);
						daysMade++;
						break;
					case (1):
						text.setText(R.string.secondday);
						daysMade++;
						break;
					default:
						daysMade++;
						Resources res = getResources();
						text.setText(res.getString(R.string.makemyday1) + " " + daysMade + " " + res.getString(R.string.makemyday2));
						break;
				}
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
