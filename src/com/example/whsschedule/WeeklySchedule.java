package com.example.whsschedule;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class WeeklySchedule {
	private ArrayList<DailySchedule> schedule;
	
	//Temporarily hard coded schedule
	private String[] mondayPeriods = {"1st", "2nd", "3rd", "4th", "5th", "6th"};
	private String[] tuesdayPeriods = {"1st", "2nd", "4th", "6th"};
	private String[] wednesdayPeriods = {"2nd", "3rd", "5th", "6th"};
	private String[] thursdayPeriods = {"1st", "3rd", "4th", "5th"};
	private String[] fridayPeriods = {"1st", "2nd", "3rd", "4th", "5th", "6th"};
	
	private int[] mondayBeginTimes = {945, 1033, 1121, 1210, 1330, 1418};
	private int[] tuesdayBeginTimes = {815, 959, 1130, 1335};
	private int[] wednesdayBeginTimes = {815, 959, 1130, 1335};
	private int[] thursdayBeginTimes = {815, 959, 1130, 1335};
	private int[] fridayBeginTimes = {815, 916, 1030, 1131, 1304, 1405};

	private int[] mondayEndTimes = {1027, 1115, 1204, 1250, 1412, 1500};
	private int[] tuesdayEndTimes = {940, 1124, 1255, 1500};
	private int[] wednesdayEndTimes = {940, 1124, 1255, /*1500*/2000};
	private int[] thursdayEndTimes = {940, 1124, 1255, 1500};
	private int[] fridayEndTimes = {910, 1011, 1125, 1224, 1359, 1500};

	private String[][] periodLists = {mondayPeriods, tuesdayPeriods, wednesdayPeriods, thursdayPeriods, fridayPeriods};
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
		int today = DateTime.now().getDayOfWeek();
		if (today > schedule.size()) //If we are past the end of the week
		{
			return schedule.get(0);
		}
		return schedule.get(today-1); //Return first day of week
	}
	
	public DailySchedule nextDaySchedule()
	{
		int today = DateTime.now().getDayOfWeek();
		if (today >= schedule.size())
		{
			//If we are on the last day of the week, return the first day
			return schedule.get(0);
		}
		
		return schedule.get(today);
	}
}
