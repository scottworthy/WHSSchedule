package com.example.whsschedule;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import android.util.Log;

public class DailySchedule {
	private ArrayList<ClassPeriod> classPeriods;
	public static final int MAX_CLASSES = 6;
	private int day;
	
	public DailySchedule()
	{	
		classPeriods = new ArrayList<ClassPeriod>();
		
		for(int numClasses = 0; numClasses < MAX_CLASSES; numClasses++)
		{
			classPeriods.add(new ClassPeriod(0, ""+numClasses+1, new LocalTime(15+numClasses, 0), new LocalTime(16+numClasses, 0)));
		}
	}
	
	public DailySchedule(int day, ArrayList<ClassPeriod> list)
	{
		this.day = day;
		classPeriods = new ArrayList<ClassPeriod>();
		//Go through the list and add them in order
		//(corrects any wrong ordering in database which doesn't really care about order anyway)
		for (ClassPeriod aClass : list)
		{
			classPeriods.add(getLocation(aClass.getBeginTime()), aClass);
		}
	}
	
	public DailySchedule(int day, String[] periods, int[] beginTimes, int[] endTimes)
	{
		classPeriods = new ArrayList<ClassPeriod>();
		this.day = day;
		
		for(int numClasses = 0; numClasses < periods.length; numClasses++)
		{
			classPeriods.add(new ClassPeriod(day, periods[numClasses],
					new LocalTime(beginTimes[numClasses]/100, beginTimes[numClasses]%100),
					new LocalTime(endTimes[numClasses]/100, endTimes[numClasses]%100)));
		}
		
		for (ClassPeriod thisClass : classPeriods)
		{
			Log.d("WHSSched", thisClass.getPeriodName() + " " + thisClass.getBeginTimeAsString() + " " + thisClass.getEndTimeAsString());
		}
	}
	
	public ArrayList<ClassPeriod> classPeriods()
	{
		return classPeriods;
	}
	
	public String getClassPeriod()
	{
		String period = currentPeriodName();
		
		if (!period.equals(""))
		{
			String periodString = period;
			return periodString;
		}
		else if (betweenClasses())
		{
			return "Between classes";
		}
		
		return "School is out.";
	}
	
	private String currentPeriodName()
	{		
		for (ClassPeriod aClass : classPeriods)
		{
			if (aClass.classInSession())
			{
				return aClass.getPeriodName();
			}
		}
		return "";
	}
	
	public boolean betweenClasses()
	{
		for (int period = 0; period < classPeriods.size()-1; period++)
		{
			if (classPeriods.get(period).afterClass() && classPeriods.get(period+1).beforeClass())
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean classMeetsToday(int period)
	{
		ClassPeriod thisClass = classPeriods.get(period);
		
		if (thisClass != null)
		{
			return thisClass.classesToday(DateTime.now());
		}
		return false;
	}
	
	private boolean schoolIsOver()
	{
		if (classPeriods.size() > 0)
		{
			ClassPeriod lastClass = classPeriods.get(classPeriods.size()-1);
			if (DateTime.now().isAfter(lastClass.getEndTime().toDateTimeToday()))
			{
				return true;
			}
		}
		else
		{
			return true;
		}
		return false;
	}
	
	public String nextClassAsString()
	{
		String nextClassText = "";
		
		if (schoolIsOver())
		{
			return "School is out for today.";
		}
		
		DateTime currentTime = new DateTime();
		
		//Look for a class that begins after current time
		ClassPeriod nextClass = nextClass();
		if (nextClass != null)
		{
			nextClassText = nextClass.getPeriodName();
		}
		else	//If no later beginning class was found
		{
			//Check to see if we are still in the last class of the day
			if (currentTime.isBefore(classPeriods.get(classPeriods.size()-1).getEndTime().toDateTimeToday()))
			{
				nextClassText = "Last class of day";
			}
		}

		return nextClassText;
	}
	
	public ClassPeriod nextClass()
	{
		DateTime currentTime = new DateTime();
		ClassPeriod nextClass = null;
		
		for (ClassPeriod aClass : classPeriods)
		{
			if (currentTime.isBefore(aClass.getBeginTime().toDateTimeToday()))
			{
				nextClass = aClass;
				break;
			}
		}
		return nextClass;
	}
	
	public String getTimeLeft()
	{
		String timeString = "0:00";
		
		for (ClassPeriod aClass : classPeriods)
		{
			if (aClass.classInSession())
			{
				Minutes diff = Minutes.minutesBetween(DateTime.now(), aClass.getEndTime().toDateTimeToday());
				timeString = String.format("%d:%02d", diff.getMinutes()/60, diff.getMinutes()%60);
			}
		}
		return timeString;
	}

	public String getTimeTillNext()
	{
		String timeString = "0:00";
		ClassPeriod nextClass = nextClass();
		
		if (nextClass != null)
		{
			Minutes diff = Minutes.minutesBetween(DateTime.now(), nextClass.getBeginTime().toDateTimeToday());
			timeString = String.format("%d:%02d", diff.getMinutes()/60, diff.getMinutes()%60);
		}
		
		/*for (int period = 0; period < classPeriods.size()-1; period++)
		{
			aClass = classPeriods.get(period);
			if (aClass.classInSession())
			{
				nextClass = classPeriods.get(period+1);
				
				if (nextClass != null)
				{
					Minutes diff = Minutes.minutesBetween(DateTime.now(), nextClass.getBeginTime().toDateTimeToday());
					timeString = String.format("%d:%02d", diff.getMinutes()/60, diff.getMinutes()%60);
				}
			}
		}*/
		return timeString;
	}
	
	public String getTimeOfNext()
	{
		String timeString = "";
		//ClassPeriod aClass;
		ClassPeriod nextClass = nextClass();
		
		if (nextClass != null)
		{
			timeString = nextClass.getBeginTimeAsString();
		}
		/*for (int period = 0; period < classPeriods.size()-1; period++)
		{
			aClass = classPeriods.get(period);
			if (aClass.classInSession())
			{
				nextClass = classPeriods.get(period+1);
				
				if (nextClass != null)
				{
					timeString = nextClass.getBeginTimeAsString();
				}
			}
		}*/
		return timeString;
	}
	
	public String timeFirstClass()
	{
		return classPeriods.get(0).getBeginTimeAsString();
	}
	
	public ClassPeriod addClass(String className, LocalTime beginTime, LocalTime endTime)
	{
		int order = getLocation(beginTime);
		classPeriods.add(order, new ClassPeriod(day, className, beginTime, endTime));
		return classPeriods.get(order);
	}
	
	public void removeClass(int order)
	{
		classPeriods.remove(order);
	}

	public ClassPeriod addClass(String className, String beginTime, String endTime)
	{
		//Change String version of times to LocalTime version
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("h:mm a").toFormatter();
		LocalTime begin = LocalTime.parse(beginTime, formatter);
		LocalTime end = LocalTime.parse(endTime, formatter);
		
		int order = getLocation(begin);
		classPeriods.add(order, new ClassPeriod(day, className, begin, end));
		return classPeriods.get(order);
	}
	
	public int getLocation(LocalTime beginTime)
	{
		int location = 0;
		
		for (ClassPeriod aClass : classPeriods)
		{
			if (aClass.getBeginTime().isBefore(beginTime))
			{
				location++;
			}
		}
		
		return location;
	}
}
