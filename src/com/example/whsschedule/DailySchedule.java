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
		int period = 0;
		
		for (ClassPeriod aClass : classPeriods)
		{
			period++;
			if (aClass.classInSession())
			{
				return String.format("Period %i", period);
			}
		}
		
		return "School is out.";
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
	
	public String nextClass(int period)
	{
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
