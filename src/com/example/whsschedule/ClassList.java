package com.example.whsschedule;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

public class ClassList extends Activity {

	WeeklySchedule schedule;
	private ListView classList;
	private int day;
	private ClassListArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_list);
		
		schedule = WeeklySchedule.getInstance();
		
		day = ClassList.this.getIntent().getIntExtra("day_id", 0);
		
		adapter = new ClassListArrayAdapter(this, R.layout.schedule_list_item, schedule.dailySchedule(day).classPeriods());
		
		classList = (ListView)findViewById(R.id.classListView);
		
		classList.setAdapter(adapter);
		
		
		classList.setOnItemClickListener(new OnItemClickListener(){
			


			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				Intent classDetails = new Intent(ClassList.this, ClassDetails.class);
				classDetails.putExtra("classIndex", position);
				classDetails.putExtra("dayIndex",  day);
				startActivity(classDetails);
			}
		});
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		if (adapter != null)
			adapter.notifyDataSetChanged();
	}
}
