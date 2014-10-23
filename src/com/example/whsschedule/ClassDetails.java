package com.example.whsschedule;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ClassDetails extends Activity {
	
	private WeeklySchedule schedule;
	
	private int dayOfWeek;
	private int periodOfDay;
	private EditText className;
	private Button beginTime;
	private Button endTime;
	private Button addButton;
	private ClassPeriod thisClass;
	private ClassPeriodDataSource datasource;
	private boolean changed;	//keep track of whether data has changed
	private boolean adding;
	private String beginButtonText;
	private String endButtonText;
	private	String nameValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_details);
		//Get passed in data from previous view
		adding = getIntent().getExtras().getBoolean("add");
		if (!adding)
		{
			periodOfDay = getIntent().getExtras().getInt("classIndex");
		}
		dayOfWeek = getIntent().getExtras().getInt("dayIndex");
		
		schedule = WeeklySchedule.getInstance();
		
		datasource = new ClassPeriodDataSource(this);
		datasource.open();
		changed = false;
		
		className = (EditText)findViewById(R.id.class_name_text_edit);
		beginTime = (Button)findViewById(R.id.begin_time_button);
		endTime = (Button)findViewById(R.id.end_time_button);
		addButton = (Button)findViewById(R.id.add_button);
		
		thisClass = schedule.dailySchedule(dayOfWeek).classPeriods().get(periodOfDay);
		
		//Save beginning values to compare later
		nameValue = thisClass.getPeriodName();
		beginButtonText = thisClass.getBeginTimeAsString();
		endButtonText = thisClass.getEndTimeAsString();
		
		//Set starting values for field and buttons
		if (!adding)
		{
			className.setText(nameValue);
			addButton.setVisibility(View.GONE);
		}
		else
		{
			className.setText("");
			
		}
		beginTime.setText(beginButtonText);
		endTime.setText(endButtonText);
	}
	
	public void addButtonPressed(View view)
	{
		String currentNameValue = className.getText().toString();
		String currentBeginButtonText = (String)beginTime.getText();
		String currentEndButtonText = (String)endTime.getText();

		DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("h:mm a").toFormatter();
		LocalTime changedBegin = LocalTime.parse(currentBeginButtonText, formatter);
		LocalTime changedEnd = LocalTime.parse(currentEndButtonText, formatter);

		schedule.addClass(dayOfWeek, currentNameValue, changedBegin, changedEnd);
		//Go back to the class list when done adding class
		onBackPressed();
	}
	
	private void saveChanges()
	{
		if(!adding)
		{
			String currentNameValue = className.getText().toString();
			String currentBeginButtonText = (String)beginTime.getText();
			String currentEndButtonText = (String)endTime.getText();
			
			if (!currentNameValue.equals(nameValue) || !currentBeginButtonText.equals(beginButtonText) || !currentEndButtonText.equals(endButtonText))
			{
				DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("h:mm a").toFormatter();
				LocalTime changedBegin = LocalTime.parse(currentBeginButtonText, formatter);
				LocalTime changedEnd = LocalTime.parse(currentEndButtonText, formatter);
		
				thisClass.setBeginTime(changedBegin);
				thisClass.setEndTime(changedEnd);
				thisClass.setPeriodName(currentNameValue);
			
				datasource.updateClass(thisClass.getID(), dayOfWeek, periodOfDay, currentNameValue, (String)beginTime.getText(), (String)endTime.getText());
			}
		}
}
	
	@Override
	public void onPause()
	{
		saveChanges();
		//datasource.close();
		super.onPause();

	}
	
	@Override
	public void onResume()
	{
		//datasource.open();
		super.onResume();
	}
	
	public void showTimePickerDialog(View view)
	{
		Button clickedButton = (Button)view;
		DialogFragment newPicker = new TimePickerFragment();
		Bundle args = new Bundle();
		args.putInt("id", view.getId());
		args.putString("time", (String) clickedButton.getText());
		newPicker.setArguments(args);
		newPicker.show(getFragmentManager(), "timepicker");
	}
}
