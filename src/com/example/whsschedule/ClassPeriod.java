package com.example.whsschedule;

import org.joda.time.*;

public class ClassPeriod {
	private LocalTime beginTime;
	private LocalTime endTime;
	private int period;
	
	public ClassPeriod(int periodNum, LocalTime begin, LocalTime end)
	{
		beginTime = begin;
		endTime = end;
		period = periodNum;
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
		DateTime currentTime = new DateTime();
		
		if (beginTime.toDateTimeToday().isBefore(currentTime) && endTime.toDateTimeToday().isAfter(currentTime))
		{
			return true;
		}
		return false;
	}
	
	public String getBeginTimeAsString()
	{
		return beginTime.toString("h:m a");
	}
	public String getEndTimeAsString()
	{
		return beginTime.toString("h:m a");
	}
	public int getPeriod()
	{
		return period;
	}
	public String getPeriodOrdinal()
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
				return String.format("%ith", period);
		}
	}
}
