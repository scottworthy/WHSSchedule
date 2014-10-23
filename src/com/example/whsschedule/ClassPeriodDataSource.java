package com.example.whsschedule;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class ClassPeriodDataSource {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_DAY, 
				MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_BEGIN, MySQLiteHelper.COLUMN_END };
	
	public ClassPeriodDataSource(Context context)
	{
		dbHelper = MySQLiteHelper.getInstance(context);
	}
	
	public void open() throws SQLException {
		if (database == null)
		{
			database = dbHelper.getWritableDatabase();
		}
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public ClassPeriod createClassPeriod(int day, int order, String name, String begin, String end)
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_DAY, day);
		values.put(MySQLiteHelper.COLUMN_NAME, name);
		values.put(MySQLiteHelper.COLUMN_BEGIN, begin);
		values.put(MySQLiteHelper.COLUMN_END, end);
		
		long insertId = 0;
		
		if (database.isOpen())  //!!!!Why is the database not open here????
		{
			try
		    {
				insertId = database.insertOrThrow(MySQLiteHelper.TABLE_CLASS_PERIODS, null, values);
		    }
		    catch(SQLException e)
		    {
		        
		        Log.e("Exception","SQLException"+String.valueOf(e.getMessage()));
		        e.printStackTrace();
		    }
		}
		
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_CLASS_PERIODS, allColumns, MySQLiteHelper.COLUMN_ID + " = " +
				insertId, null, null, null, null);
		cursor.moveToFirst();
		ClassPeriod newClassPeriod = cursorToClassPeriod(cursor);
		cursor.close();
		
		return newClassPeriod;
	}
	
	public void deleteClassPeriod(ClassPeriod classPeriod)
	{
		long id = classPeriod.getID();
		
		System.out.println("Class deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_CLASS_PERIODS, MySQLiteHelper.COLUMN_ID + " = " + id, null);
	}
	
	public ArrayList<ClassPeriod> getAllClasses()
	{
		ArrayList<ClassPeriod> classPeriods = new ArrayList<ClassPeriod>();
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_CLASS_PERIODS, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			ClassPeriod classPeriod = cursorToClassPeriod(cursor);
			classPeriods.add(classPeriod);
			cursor.moveToNext();
		}
		
		return classPeriods;
	}
	
	public ClassPeriod getClass(long id)
	{
		String whereClause = MySQLiteHelper.COLUMN_ID + " = ?";
		
		String[] whereArgs = {"" + id};
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_CLASS_PERIODS, allColumns, whereClause, whereArgs, null, null, null);
		
		cursor.moveToFirst();
		
		return cursorToClassPeriod(cursor);
	}
	
	public ArrayList<ClassPeriod> getClassesOnDay(int day)
	{
		ArrayList<ClassPeriod> classPeriods = new ArrayList<ClassPeriod>();
		
		String whereClause = MySQLiteHelper.COLUMN_DAY + " = " + day;
		Cursor cursor = database.query(MySQLiteHelper.TABLE_CLASS_PERIODS, allColumns, whereClause, null, null, null, null);
		if(cursor.moveToFirst())
		{
			do
			{
				ClassPeriod classPeriod = cursorToClassPeriod(cursor);
				classPeriods.add(classPeriod);
			}while (cursor.moveToNext());
		}
		return classPeriods;
	}
	
	public boolean updateClass(long id, int day, int order, String name, String begin, String end)
	{
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_DAY, day);
		values.put(MySQLiteHelper.COLUMN_NAME, name);
		values.put(MySQLiteHelper.COLUMN_BEGIN, begin);
		values.put(MySQLiteHelper.COLUMN_END, end);
		
		String whereClause = MySQLiteHelper.COLUMN_ID + "=?";
		String[] whereArgs = {Long.toString(id)};
		
		if (database.isOpen())
			database.update(MySQLiteHelper.TABLE_CLASS_PERIODS, values, whereClause, whereArgs);
		
		return true;
	}
	
	public boolean isEmpty()
	{
		Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_CLASS_PERIODS, null);
		if (cursor.moveToFirst())
		{
			return false;
		}
		
		return true;
	}
	
	private ClassPeriod cursorToClassPeriod(Cursor cursor)
	{
		ClassPeriod classPeriod = new ClassPeriod(cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getLong(0));
		
		return classPeriod;
	}
}
