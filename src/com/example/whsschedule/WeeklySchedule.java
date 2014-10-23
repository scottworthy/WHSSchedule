package com.example.whsschedule;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class WeeklySchedule {
	
	private static WeeklySchedule instance;
	
	private ArrayList<DailySchedule> schedule;
	
	ClassPeriodDataSource datasource;
	
	//Temporarily hard coded schedule
	private String[] mondayPeriods = {"1st", "2nd", "3rd", "4th","Lunch", "5th", "6th"};
	private String[] tuesdayPeriods = {"1st", "Break", "2nd", "4th", "Lunch", "6th"};
	private String[] wednesdayPeriods = {"2nd", "Break", "3rd", "5th","Lunch", "6th"};
	private String[] thursdayPeriods = {"1st","Break", "3rd", "4th","Lunch", "5th"};
	private String[] fridayPeriods = {"1st", "2nd","Break", "3rd", "4th", "Lunch", "5th", "6th"};
	
	private int[] mondayBeginTimes = {945, 1033, 1121, 1210, 1250, 1330, 1418};
	private int[] tuesdayBeginTimes = {815, 940, 959, 1130, 1255, 1335};
	private int[] wednesdayBeginTimes = {815, 940, 959, 1130, 1255, 1335};
	private int[] thursdayBeginTimes = {815, 940, 959, 1130, 1255, 1335};
	private int[] fridayBeginTimes = {815, 916, 1011, 1030, 1131, 1224, 1304, 1405};

	private int[] mondayEndTimes = {1027, 1115, 1204, 1250, 1324, 1412, 1500};
	private int[] tuesdayEndTimes = {940, 953, 1124, 1255, 1329, 1500};
	private int[] wednesdayEndTimes = {940, 953, 1124, 1255, 1329, 1500};
	private int[] thursdayEndTimes = {940, 953, 1124, 1255, 1329, 1500};
	private int[] fridayEndTimes = {910, 1011, 1024, 1125, 1224, 1258, 1359, 1500};

	private String[][] periodLists = {mondayPeriods, tuesdayPeriods, wednesdayPeriods, thursdayPeriods, fridayPeriods};
	private int[][] beginLists = {mondayBeginTimes, tuesdayBeginTimes, wednesdayBeginTimes, thursdayBeginTimes, fridayBeginTimes};
	private int[][] endLists = {mondayEndTimes, tuesdayEndTimes, wednesdayEndTimes, thursdayEndTimes, fridayEndTimes};
	
	private WeeklySchedule()
	{
		schedule = null;
	}
	
	private WeeklySchedule(ClassPeriodDataSource datasource)
	{
		setDataSource(datasource);
	}
	
	private void createFromDB(ClassPeriodDataSource datasource)
	{
		for (int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++)
		{
			ArrayList<ClassPeriod> classes = datasource.getClassesOnDay(dayOfWeek);
			DailySchedule todaySched = new DailySchedule(dayOfWeek, classes);
			
			schedule.add(todaySched);
		}
	}
	
	private void buildDefaultSchedule(ClassPeriodDataSource datasource)
	{
		//Use my WHS schedule as default schedule and create initial database
		for (int dayOfWeek = 0; dayOfWeek < periodLists.length; dayOfWeek++)
		{
			for(int period = 0; period < periodLists[dayOfWeek].length; period++)
			{
				LocalTime beginTime = new LocalTime(beginLists[dayOfWeek][period]/100, beginLists[dayOfWeek][period]%100);
				LocalTime endTime = new LocalTime(endLists[dayOfWeek][period]/100, endLists[dayOfWeek][period]%100);
				
				String begin = beginTime.toString("h:mm a");
				String end = endTime.toString("h:mm a");
				datasource.createClassPeriod(dayOfWeek, period, periodLists[dayOfWeek][period], begin, end);
			}
		}
		
	}
	
	//Uses singleton design pattern, instance initialized only once
	public static void initInstance()
	{
		if (instance == null)
		{
			instance = new WeeklySchedule();
		}
	}
	
	public void setDataSource(ClassPeriodDataSource datasource)
	{
		if (datasource.isEmpty())
		{
			buildDefaultSchedule(datasource);
			
			schedule = new ArrayList<DailySchedule>();
			for (int dayOfWeek = 0; dayOfWeek < periodLists.length; dayOfWeek++)
			{
				schedule.add(new DailySchedule(dayOfWeek, periodLists[dayOfWeek], beginLists[dayOfWeek], endLists[dayOfWeek]));
			}
			
		}
		else
		{
			schedule = new ArrayList<DailySchedule>();
			createFromDB(datasource);
		}
		
		this.datasource = datasource;
	}
	
	public static WeeklySchedule getInstance()
	{
		return instance;
	}
	
	public DailySchedule dailySchedule()
	{
		if (schedule == null)
			return null;
		
		int today = DateTime.now().getDayOfWeek()-1;
		while (today < schedule.size()) //Look for the next day with classes (including this day)
		{
			if (schedule.get(today).classPeriods().size()>0) //If there are classes today
			{
				return schedule.get(today);
			}
			today++;
		}
		return schedule.get(0);
	}
	
	public DailySchedule dailySchedule(int index)
	{
		if (schedule == null)
			return null;
		
		if (index > schedule.size())
		{
			return schedule.get(0);
		}
		return schedule.get(index);
	}
	
	public DailySchedule nextDaySchedule()
	{
		if (schedule == null)
			return null;
		
		int today = DateTime.now().getDayOfWeek();
		while (today < schedule.size()) //Look for the next day with classes
		{
			if (schedule.get(today).classPeriods().size()>0) //If there are classes today
			{
				return schedule.get(today);
			}
			today++;
		}
		return schedule.get(0);
	}
	
	public void addClass(int day, String name, LocalTime beginTime, LocalTime endTime)
	{
		String begin = beginTime.toString("h:mm a");
		String end = endTime.toString("h:mm a");
		//Add class to database
		if (datasource != null)
		{
			int order = schedule.get(day).getLocation(beginTime);
			
			ClassPeriod newClass = datasource.createClassPeriod(day, order, name, begin, end);
			//Add class locally
			schedule.get(day).classPeriods().add(order, newClass);
		}
	}
	
	public void deleteClass(int day, int order)
	{
		//remove from db
		datasource.deleteClassPeriod(schedule.get(day).classPeriods().get(order));
		//remove from local data
		schedule.get(day).removeClass(order);
	}
}
