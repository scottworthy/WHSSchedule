package com.example.whsschedule;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class WeeklySchedule {
	private ArrayList<DailySchedule> schedule;

	public WeeklySchedule()
	{
		schedule = new ArrayList<DailySchedule>();
		for (int daysInWeek = 7; daysInWeek > 0; daysInWeek--)
		{
			schedule.add(new DailySchedule());
		}
	}
	
	public DailySchedule dailySchedule()
	{
		return schedule.get(DateTime.now().getDayOfWeek());
	}
}
