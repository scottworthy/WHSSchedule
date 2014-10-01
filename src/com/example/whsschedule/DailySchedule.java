package com.example.whsschedule;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import android.util.Log;

public class DailySchedule {
	private ArrayList<ClassPeriod> classPeriods;
	public static final int MAX_CLASSES = 6;
	
	public DailySchedule()
	{	
		classPeriods = new ArrayList<ClassPeriod>();
		
		for(int numClasses = 0; numClasses < MAX_CLASSES; numClasses++)
		{
			classPeriods.add(new ClassPeriod(numClasses+1, new LocalTime(15+numClasses, 0), new LocalTime(16+numClasses, 0)));
		}
	}
	
	public DailySchedule(int[] periods, int[] beginTimes, int[] endTimes)
	{
		for(int numClasses = 0; numClasses < periods.length; numClasses++)
		{
			classPeriods.add(new ClassPeriod(periods[numClasses],
					new LocalTime(beginTimes[numClasses]/100, beginTimes[numClasses]%100),
					new LocalTime(endTimes[numClasses]/100, endTimes[numClasses]%100)));
		}
		
		for (ClassPeriod thisClass : classPeriods)
		{
			Log.d("MYAPP", thisClass.getPeriodOrdinal() + " " + thisClass.getBeginTimeAsString() + " " + thisClass.getEndTimeAsString());
		}
	}
	
	public String getClassPeriod()
	{
		int period = getPeriodNumber();
		
		if (period >= 0)
		{
			String periodString = "Period " + period;
			int i = 0;
			return periodString;
		}
		
		return "School is out.";
	}
	
	private int getPeriodNumber()
	{
		int period = 0;
		
		for (ClassPeriod aClass : classPeriods)
		{
			period++;
			if (aClass.classInSession())
			{
				return period;
			}
		}
		return -1;
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
		ClassPeriod lastClass = classPeriods.get(classPeriods.size()-1);
		if (DateTime.now().isAfter(lastClass.getEndTime().toDateTimeToday()))
		{
			return true;
		}
		return false;
	}
	
	public String nextClass()
	{
		int period = getPeriodNumber();
		
		if (schoolIsOver())
		{
			return "School is out for today.";
		}
		if (period == -1) //School not in session yet
		{
			period = 0;		//Set next period to the first period
		}
		if (classPeriods.size()-1 < period)
		{
			return "Last class of day";
		}
		else
		{
			String nextClassText = "Next class is " + classPeriods.get(period).getPeriodOrdinal() + " period";
			int i = 0;
			return nextClassText;
		}
	}
}
