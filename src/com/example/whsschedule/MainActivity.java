package com.example.whsschedule;

import org.joda.time.DateTime;

import android.support.v7.app.ActionBarActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	BroadcastReceiver broadcastReceiver;
	String[] daysOfWeekArray;
	WeeklySchedule schedule;
	TextView currentPeriodView;
	TextView nextPeriod;
	TextView timeLeft;
	TextView nextTime;
	TextView dayOfWeek;
	Resources res;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		schedule = new WeeklySchedule();
		currentPeriodView = (TextView)findViewById(R.id.current_period_textview);
		nextPeriod = (TextView)findViewById(R.id.next_period_text_view);
		timeLeft = (TextView)findViewById(R.id.time_left_text_view);
		nextTime = (TextView)findViewById(R.id.next_time_text_view);
		res = getResources();
		int dayOfWeekID = R.array.days_of_week;
		daysOfWeekArray = res.getStringArray(dayOfWeekID);
	}
	
	private void updateCurrentPeriod()
	{		
		currentPeriodView = (TextView)findViewById(R.id.current_period_textview);
		currentPeriodView.setText(schedule.dailySchedule().getClassPeriod());
	}
	
	private void updateNextPeriod()
	{
		nextPeriod = (TextView)findViewById(R.id.next_period_text_view);
		nextPeriod.setText(schedule.dailySchedule().nextClassAsString());
	}
	
	private void updateTimeLeft()
	{		
		timeLeft = (TextView)findViewById(R.id.time_left_text_view);
		timeLeft.setText(schedule.dailySchedule().getTimeLeft());
	}
	
	private void updateTimeNext()
	{
		nextTime = (TextView)findViewById(R.id.next_time_text_view);
		
		String timeNext = schedule.dailySchedule().getTimeOfNext();
		
		if (timeNext.equals(""))
		{
			timeNext = schedule.nextDaySchedule().timeFirstClass();
		}
		
		nextTime.setText(timeNext);

		//TextView timeNextPrompt = (TextView) findViewById(R.id.next_begins_prompt);

	}
	
	private void updateDayOfWeek()
	{
		String today = daysOfWeekArray[DateTime.now().getDayOfWeek()];
		
		dayOfWeek = (TextView)findViewById(R.id.day_of_week);
		dayOfWeek.setText(today);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	protected void onResume() {
		super.onResume();
		updateAll();
		
		broadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context ctx, Intent intent)
			{
				if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0)
				{
					updateAll();
				}
			}
			
		};
		registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
		
	}
	
	private void updateAll() {
		//setContentView(R.layout.activity_main);	//Seemed to be unnecessary
		updateCurrentPeriod();	//set current period text
		updateNextPeriod();		//set next period text
		updateTimeLeft();
		updateTimeNext();
		updateDayOfWeek();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		unregisterReceiver(broadcastReceiver);
	}
}
