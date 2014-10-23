package com.example.whsschedule;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

	private int buttonId;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		String timeString = getArguments().getString("time");
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("h:mm a").toFormatter();
		LocalTime time = LocalTime.parse(timeString, formatter);
		buttonId = getArguments().getInt("id");
		
		return new TimePickerDialog(getActivity(), this, time.getHourOfDay(), time.getMinuteOfHour(), DateFormat.is24HourFormat(getActivity()));
	}
	
	@Override
	public void onTimeSet(TimePicker view, int hour, int minute) {
		Button timeButton = (Button)getActivity().findViewById(buttonId);
		timeButton.setText(new LocalTime(hour, minute).toString("h:mm a"));
	}
	
	

}
