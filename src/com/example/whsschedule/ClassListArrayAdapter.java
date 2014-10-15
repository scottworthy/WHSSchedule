package com.example.whsschedule;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ClassListArrayAdapter extends ArrayAdapter<ClassPeriod> {
	
	private final Context context;
	private final ArrayList<ClassPeriod> classes;
	private int layoutResourceId;

	
	public ClassListArrayAdapter(Context context, int layoutResourceId, ArrayList<ClassPeriod> classes)
	{
		super(context, layoutResourceId, classes);
		this.context = context;
		this.classes = classes;
		this.layoutResourceId = layoutResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		ClassHolder holder = null;
		
		if (row == null)
		{
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			
			row = inflater.inflate(layoutResourceId, parent, false);
			
			holder = new ClassHolder();
			holder.className = (TextView)row.findViewById(R.id.item_name);
			holder.timeString = (TextView)row.findViewById(R.id.start_end_times);
			
			//TextView className = (TextView)rowView.findViewById(R.id.item_name);
			//TextView timeString = (TextView)rowView.findViewById(R.id.start_end_times);
			
			row.setTag(holder);
		}
		else
		{
			holder = (ClassHolder)row.getTag();
		}
		
		ClassPeriod currentClass = classes.get(position);
		holder.className.setText(currentClass.getPeriodName());
		String beginEndString = currentClass.getBeginTimeAsString() + " - " + currentClass.getEndTimeAsString();
		holder.timeString.setText(beginEndString);
		
		return row;
	}

	static class ClassHolder
	{
		TextView className;
		TextView timeString;
	}
	
}
