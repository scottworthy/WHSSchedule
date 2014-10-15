package com.example.whsschedule;

import android.app.Application;

public class ScheduleApp extends Application {

	@Override
	public void onCreate()
	{
		super.onCreate();
		
		initSingletons();
	}
	
	protected void initSingletons()
	{
		WeeklySchedule.initInstance();
	}
	
}
