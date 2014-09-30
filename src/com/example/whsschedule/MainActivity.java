package com.example.whsschedule;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	String[] currentPeriodText;
	DailySchedule schedule;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		currentPeriodText = getResources(). getStringArray(R.array.current_period);
		schedule = new DailySchedule();
	}
	
	private void updateCurrentPeriod()
	{
		TextView currentPeriodView = (TextView)findViewById(R.id.current_period_textview);
		
		currentPeriodView.setText(schedule.getClassPeriod());
	}
	
	private void updateNextPeriod()
	{
		TextView nextPeriod = (TextView)findViewById(R.id.next_period_text);
		
		nextPeriod.setText(schedule.nextClass());
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
		updateCurrentPeriod();
		updateNextPeriod();
	}
}
