package com.example.whsschedule;

import org.joda.time.LocalTime;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

public class ClassDetails extends Activity {
	
	private WeeklySchedule schedule;
	
	private int dayOfWeek;
	private int periodOfDay;
	private TextView className;
	private TimePicker beginTime;
	private TimePicker endTime;
	private ClassPeriod thisClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_details);
		periodOfDay = getIntent().getExtras().getInt("classIndex");
		dayOfWeek = getIntent().getExtras().getInt("dayIndex");
		
		schedule = WeeklySchedule.getInstance();
		
		className = (TextView)findViewById(R.id.class_name_text_view);
		beginTime = (TimePicker)findViewById(R.id.begin_time);
		endTime = (TimePicker)findViewById(R.id.end_time);
		
		thisClass = schedule.dailySchedule(dayOfWeek).classPeriods().get(periodOfDay);
		
		LocalTime beginningTime = thisClass.getBeginTime();
		LocalTime endingTime = thisClass.getEndTime();
		
		className.setText(thisClass.getPeriodName());
		beginTime.setCurrentHour(beginningTime.getHourOfDay());
		beginTime.setCurrentMinute(beginningTime.getMinuteOfHour());
		endTime.setCurrentHour(endingTime.getHourOfDay());
		endTime.setCurrentMinute(endingTime.getMinuteOfHour());
		
	}
	
	public void saveChanges(View view)
	{
		LocalTime changedBegin = new LocalTime(beginTime.getCurrentHour(), beginTime.getCurrentMinute());
		LocalTime changedEnd = new LocalTime(endTime.getCurrentHour(), endTime.getCurrentMinute());
		thisClass.setBeginTime(changedBegin);
		thisClass.setEndTime(changedEnd);
	}
}
