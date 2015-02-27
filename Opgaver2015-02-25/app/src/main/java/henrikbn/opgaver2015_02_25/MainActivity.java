package henrikbn.opgaver2015_02_25;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;


public class MainActivity extends ActionBarActivity
{
	private Random random;
	private ImageView[] dice;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		random = new Random();

		dice = new ImageView[5];
		dice[0] = (ImageView) findViewById(R.id.dice1);
		dice[1] = (ImageView) findViewById(R.id.dice2);
		dice[2] = (ImageView) findViewById(R.id.dice3);
		dice[3] = (ImageView) findViewById(R.id.dice4);
		dice[4] = (ImageView) findViewById(R.id.dice5);

		Button rollButton = (Button) findViewById(R.id.rollButton);
		rollButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				for (ImageView diceView : dice)
					diceView.setImageResource(R.drawable.dice);

				Thread thread = new Thread(null, makeWork, "loldontcare");
				thread.start();
			}
		});
	}

	private int[] rollDices()
	{
		int minimum = 1;
		int maximum = 6;
		int[] numbers = new int[6];
		for (int i = 0; i < 6; i++)
		{
			int randomNumber = random.nextInt(maximum) + minimum;
			numbers[i] = randomNumber;
		}
		return numbers;
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

	private Runnable makeWork = new Runnable()
	{
		@Override
		public void run()
		{
			try
			{
				Thread.sleep(2000);
			}
			catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}
			runOnUiThread(new Runnable()
			{

				@Override
				public void run()
				{
					int[] numbers = rollDices();
					for (int i = 0; i < 5; i++)
					{
						int number = numbers[i];
						ImageView diceView = dice[i];

						switch (number)
						{
							case (1):
								diceView.setImageResource(R.drawable.dice1);
								break;
							case (2):
								diceView.setImageResource(R.drawable.dice2);
								break;
							case (3):
								diceView.setImageResource(R.drawable.dice3);
								break;
							case (4):
								diceView.setImageResource(R.drawable.dice4);
								break;
							case (5):
								diceView.setImageResource(R.drawable.dice5);
								break;
							case (6):
								diceView.setImageResource(R.drawable.dice6);
								break;
						}
					}
				}
			});
		}
	};
}
