package ctec.soundandvideo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.media.MediaPlayer;
import android.content.Intent;

public class SoundActivity extends AppCompatActivity implements Runnable
{
	private Button startButton;
	private Button stopButton;
	private Button pauseButton;
	private Button videoButton;
	private MediaPlayer soundPlayer;
	private SeekBar soundSeekBar;
	private Thread soundThread;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound);

		startButton = (Button) findViewById(R.id.playButton);
		pauseButton = (Button) findViewById(R.id.pauseButton);
		stopButton = (Button) findViewById(R.id.stopButton);
		soundSeekBar = (SeekBar) findViewById(R.id.soundSeekBar);
		videoButton = (Button) findViewById(R.id.videoButton);
		soundPlayer = MediaPlayer.create(this.getBaseContext(), R.raw.music);

		setupListeners();

		soundThread = new Thread(this);
		soundThread.start();


	}

	private void setupListeners()
	{
		startButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View vacant)
			{
				soundPlayer.start();
			}
		});

		pauseButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View vacant)
			{
				soundPlayer.pause();
			}
		});

		stopButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View currentView)
			{
				soundPlayer.stop();
				soundPlayer = MediaPlayer.create(getBaseContext(), R.raw.music);
			}
		});

		videoButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View currentView)
			{
				Intent myIntent = new Intent(currentView.getContext(),VideoActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});

		soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onStopTrackingTouch(SeekBar soundSeekBar)
			{}
			@Override
			public void onStartTrackingTouch(SeekBar soundSeekBar)
			{}
			@Override
			public void onProgressChanged(SeekBar soundSeekBar, int progress, boolean fromUser)
			{
				if(fromUser)
				{
					soundPlayer.seekTo(progress);
				}
			}
		});
	}

	@Override
	public void run()
	{
		int currentPosition =0;
		int soundTotal = soundPlayer.getDuration();
		soundSeekBar.setMax(soundTotal);

		while (soundPlayer != null && currentPosition < soundTotal)
		{
			try
			{
				Thread.sleep(300);
				currentPosition = soundPlayer.getCurrentPosition();
			}
			catch (InterruptedException soundException)
			{
				return;
			}
			catch(Exception otherException)
			{
				return;
			}
			soundSeekBar.setProgress(currentPosition);
		}

	}


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_sound, menu);
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
