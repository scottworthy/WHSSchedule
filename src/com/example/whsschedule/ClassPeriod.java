package com.example.whsschedule;

import org.joda.time.*;

public class ClassPeriod {
	private LocalTime beginTime;
	private LocalTime endTime;
	private String period;
	
	public ClassPeriod(String periodName, LocalTime begin, LocalTime end)
	{
		beginTime = begin;
		endTime = end;
		period = periodName;
	}
	
	public boolean classesToday(DateTime today)
	{
		int dayOfWeek = today.getDayOfWeek();
		if (dayOfWeek > 5 )
		{
			return true;
		}
		return false;
	}
	
	public boolean classInSession()
	{		
		if (!beforeClass() && !afterClass())
		{
			return true;
		}
		return false;
	}
	
	public boolean beforeClass()
	{
		DateTime currentTime = new DateTime();
		
		if(beginTime.toDateTimeToday().isAfter(currentTime))
		{
			return true;
		}
		return false;
	}
	
	public boolean afterClass()
	{
		DateTime currentTime = new DateTime();
		
		if(endTime.toDateTimeToday().isBefore(currentTime))
		{
			return true;
		}
		return false;
	}
	
	public String getBeginTimeAsString()
	{
		return beginTime.toString("h:mm a");
	}
	public String getEndTimeAsString()
	{
		return endTime.toString("h:mm a");
	}
	public LocalTime getEndTime()
	{
		return endTime;
	}
	public LocalTime getBeginTime()
	{
		return beginTime;
	}
	
	public void setBeginTime(LocalTime time)
	{
		if (time != null)
			beginTime = time;
	}
	
	public void setEndTime(LocalTime time)
	{
		if (time != null)
			endTime = time;
	}
	
	public void setPeriodName(String name)
	{
		period = name;
	}
	
	public String getPeriodName()
	{
		return period;
	}
	/*public String getPeriodOrdinal()
	{
		switch (period)
		{
			case 0:
				return "0th";
			case 1:
				return "1st";
			case 2:
				return "2nd";
			case 3:
				return "3rd";
			default:
				String ordinalText = String.format("%dth", period);
				int i = 0;
				return ordinalText;
		}
	}*/
}
