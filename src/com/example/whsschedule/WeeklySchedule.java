package com.example.whsschedule;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class WeeklySchedule {
	private ArrayList<DailySchedule> schedule;
	
	//Temporarily hard coded schedule
	private int[] mondayPeriods = {1, 2, 3, 4, 5, 6};
	private int[] tuesdayPeriods = {1, 2, 4, 6};
	private int[] wednesdayPeriods = {2, 3, 5, 6};
	private int[] thursdayPeriods = {1, 2, 4, 5};
	private int[] fridayPeriods = {1, 2, 3, 4, 5, 6};
	
	private int[] mondayBeginTimes = {945, 1033, 1121, 1210, 130, 218};
	private int[] tuesdayBeginTimes = {815, 959, 1130, 135};
	private int[] wednesdayBeginTimes = {815, 959, 1130, 135};
	private int[] thursdayBeginTimes = {815, 959, 1130, 135};
	private int[] fridayBeginTimes = {815, 916, 1030, 1131, 104, 205};

	private int[] mondayEndTimes = {1027, 1115, 1204, 1250, 212, 300};
	private int[] tuesdayEndTimes = {940, 1124, 1255, 300};
	private int[] wednesdayEndTimes = {940, 1124, 1255, 300};
	private int[] thursdayEndTimes = {940, 1124, 1255, 300};
	private int[] fridayEndTimes = {910, 1011, 1125, 1224, 159, 300};

	private int[][] periodLists = {mondayPeriods, tuesdayPeriods, wednesdayPeriods, thursdayPeriods, fridayPeriods};
	private int[][] beginLists = {mondayBeginTimes, tuesdayBeginTimes, wednesdayBeginTimes, thursdayBeginTimes, fridayBeginTimes};
	private int[][] endLists = {mondayEndTimes, tuesdayEndTimes, wednesdayEndTimes, thursdayEndTimes, fridayEndTimes};
	
	
	public WeeklySchedule()
	{
		schedule = new ArrayList<DailySchedule>();
		for (int dayOfWeek = 0; dayOfWeek < 5; dayOfWeek++)
		{
			schedule.add(new DailySchedule(periodLists[dayOfWeek], beginLists[dayOfWeek], endLists[dayOfWeek]));
		}
	}
	
	public DailySchedule dailySchedule()
	{
		return schedule.get(DateTime.now().getDayOfWeek());
	}
}
