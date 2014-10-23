package com.example.whsschedule;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

public class ClassPeriod {
	private LocalTime beginTime;
	private LocalTime endTime;
	private String period;
	private int dayOfWeek;
	private long id;
	
	public ClassPeriod(int day, String periodName, LocalTime begin, LocalTime end)
	{
		beginTime = begin;
		endTime = end;
		period = periodName;
	}
	
	public ClassPeriod(int day, String periodName, String beginString, String endString)
	{
		this(day, periodName, beginString, endString, 0);
	}
	
	public ClassPeriod(int day, String periodName, String beginString, String endString, long id)
	{

		period = periodName;
		
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("h:mm a").toFormatter();
		LocalTime begin = LocalTime.parse(beginString, formatter);
		LocalTime end = LocalTime.parse(endString, formatter);

		beginTime = begin;
		endTime = end;
		dayOfWeek = day;
		this.id = id;
	}
	
	

	/* Database Support Methods */
	public long getID()
	{
		return id;
	}
	
	public void setID(long id)
	{
		this.id = id;
	}
	
	/*Other Methods*/
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

	public String toString()
	{
		return period + " " + getBeginTimeAsString() + " - " + getEndTimeAsString();
	}
}
