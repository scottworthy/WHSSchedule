package com.example.whsschedule;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MySQLiteHelper extends SQLiteOpenHelper {
	
	private static MySQLiteHelper instance = null;
	private Context myContext;
	
	public static final String TABLE_CLASS_PERIODS = "classes";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DAY = "_day";
	//public static final String COLUMN_ORDER = "_order";  //No longer stored in database
	public static final String COLUMN_NAME = "_name";
	public static final String COLUMN_BEGIN = "_begin";
	public static final String COLUMN_END = "_end";
	
	private static final String DATABASE_NAME = "classperiods.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = "create table " + TABLE_CLASS_PERIODS
			+ "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_DAY + " integer, " 
			+ COLUMN_NAME + " text not null, "
			+ COLUMN_BEGIN + " text not null, "
			+ COLUMN_END + " text not null);";
	
	public MySQLiteHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		myContext = context;
	}
	
	public static MySQLiteHelper getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new MySQLiteHelper(context.getApplicationContext());
		}
		
		return instance;
	}
	
	@Override
	public void onCreate(SQLiteDatabase database)
	{
		database.execSQL(DATABASE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion +
				"to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASS_PERIODS);
		onCreate(db);
	}

}
