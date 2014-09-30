package com.example.whsschedule;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class DailySchedule {
	private ArrayList<ClassPeriod> classPeriods;
	public static final int MAX_CLASSES = 6;
	
	public DailySchedule()
	{	
		classPeriods = new ArrayList<ClassPeriod>();
		
		for(int numClasses = 0; numClasses < MAX_CLASSES; numClasses++)
		{
			classPeriods.add(new ClassPeriod(numClasses+1, new LocalTime(8+numClasses, 0), new LocalTime(9+numClasses, 0)));
		}
	}
	
	public String getClassPeriod()
	{
		int period = getPeriodNumber();
		
		if (period >= 0)
		{
			return String.format("Period %i", period);			
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
		if (classPeriods.size() < period)
		{
			return "Last class of day";
		}
		else
		{
			return "Next class is " + 
					classPeriods.get(period).getPeriodOrdinal() + "period";
		}
	}
}
