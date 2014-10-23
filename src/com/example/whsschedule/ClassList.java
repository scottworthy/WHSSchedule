package com.example.whsschedule;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

public class ClassList extends ListActivity {

	WeeklySchedule schedule;
	private ListView classList;
	private int day;
	private int selectedItem;
	private ClassListArrayAdapter adapter;
	protected Object mActionMode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_list);
		
		schedule = WeeklySchedule.getInstance();
		
		day = ClassList.this.getIntent().getIntExtra("day_id", 0);
		
		adapter = new ClassListArrayAdapter(this, R.layout.schedule_list_item, schedule.dailySchedule(day).classPeriods());
		
		classList = (ListView)findViewById(android.R.id.list);
		
		classList.setAdapter(adapter);
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		getListView().setSelector(android.R.color.darker_gray);
		
		classList.setOnItemClickListener(new OnItemClickListener(){
			


			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				if (mActionMode != null)
				{
					((ActionMode)mActionMode).finish();
					return;
				}
				
				selectedItem = position;
				
				//TODO: Get list item????
				
				mActionMode = ClassList.this.startActionMode(mActionModeCallback);
				adapter.setSelected(true);
				
				//openEditClass(position);
				return;
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
	
	private void openEditClass(int position)
	{
		Intent classDetails = new Intent(ClassList.this, ClassDetails.class);
		classDetails.putExtra("classIndex", position);
		classDetails.putExtra("dayIndex",  day);
		classDetails.putExtra("add", false);
		startActivity(classDetails);
	}
	
	private void openAddClass(int position)
	{
		Intent classDetails = new Intent(ClassList.this, ClassDetails.class);
		classDetails.putExtra("add", true);
		classDetails.putExtra("dayIndex", day);
		classDetails.putExtra("classIndex", position);
		startActivity(classDetails);
	}
	
	private void removeClass(int position)
	{
		schedule.deleteClass(day, position);
	}
	
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
		
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu)
		{
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.class_edit_menu, menu);
			return true;
		}
		
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu)
		{
			return false;
		}
		
		@Override 
		public boolean onActionItemClicked(ActionMode mode, MenuItem item)
		{
			switch (item.getItemId())
			{
				case R.id.edit_class:
					openEditClass(selectedItem);
					mode.finish();
					return true;
				case R.id.delete_class:
					removeClass(selectedItem);
					adapter.notifyDataSetChanged();
					mode.finish();
					return true;
				case R.id.add_class:
					openAddClass(selectedItem);
					mode.finish();
					return true;
					
				default:
					return false;
			}
		}
		
		@Override
		public void onDestroyActionMode(ActionMode mode)
		{
			mActionMode = null;
		}
	};
	
}
