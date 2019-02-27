package com.example.yujin.whereismyroom.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper {
    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBases.CreateDB._CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DataBases.CreateDB.TABLE_NAME);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context) {
        mCtx = context;
    }

    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create() {
        mDBHelper.onCreate(mDB);
    }

    public void close() {
        mDB.close();
    }

    public long insertColumn(String deposit, String rentMonth, String rentType, String utilities, String includedUtilities
                            , String stationName, String routeName
                            , String buildFloor, String myFloor, String direction, String roomType
                            , String roomSizeM, String roomSizeP, String option
                            , String animal, String elevator, String parking, String detail) {
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.DEPOSIT, deposit);
        values.put(DataBases.CreateDB.RENT_MONTH, rentMonth);
        values.put(DataBases.CreateDB.RENT_TYPE, rentType);
        values.put(DataBases.CreateDB.UTILITIES, utilities);
        values.put(DataBases.CreateDB.INCLUDED_UTILITIES, includedUtilities);
        values.put(DataBases.CreateDB.STATION_NAME, stationName);
        values.put(DataBases.CreateDB.ROUTE_NAME, routeName);
        values.put(DataBases.CreateDB.BUILD_FLOOR, buildFloor);
        values.put(DataBases.CreateDB.MY_FLOOR, myFloor);
        values.put(DataBases.CreateDB.DIRECTION, direction);
        values.put(DataBases.CreateDB.ROOM_TYPE, roomType);
        values.put(DataBases.CreateDB.ROOM_SIZE_M, roomSizeM);
        values.put(DataBases.CreateDB.ROOM_SIZE_P, roomSizeP);
        values.put(DataBases.CreateDB.OPTION, option);
        values.put(DataBases.CreateDB.ANIMAL, animal);
        values.put(DataBases.CreateDB.ELEVATOR, elevator);
        values.put(DataBases.CreateDB.PARKING, parking);
        values.put(DataBases.CreateDB.DETAIL, detail);
        return mDB.insert(DataBases.CreateDB.TABLE_NAME, null, values);
    }

    public Cursor selectColumns() {
        Cursor c = mDB.rawQuery("SELECT * FROM ROOM ORDER BY _ID DESC;", null);
        return c;
    }

    public boolean updateColumn(int id, String deposit, String rentMonth, String rentType, String utilities, String includedUtilities
                            , String stationName, String routeName
                            , String buildFloor, String myFloor, String direction, String roomType
                            , String roomSizeM, String roomSizeP, String option
                            , String animal, String elevator, String parking, String detail) {
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.DEPOSIT, deposit);
        values.put(DataBases.CreateDB.RENT_MONTH, rentMonth);
        values.put(DataBases.CreateDB.RENT_TYPE, rentType);
        values.put(DataBases.CreateDB.UTILITIES, utilities);
        values.put(DataBases.CreateDB.INCLUDED_UTILITIES, includedUtilities);
        values.put(DataBases.CreateDB.STATION_NAME, stationName);
        values.put(DataBases.CreateDB.ROUTE_NAME, routeName);
        values.put(DataBases.CreateDB.BUILD_FLOOR, buildFloor);
        values.put(DataBases.CreateDB.MY_FLOOR, myFloor);
        values.put(DataBases.CreateDB.DIRECTION, direction);
        values.put(DataBases.CreateDB.ROOM_TYPE, roomType);
        values.put(DataBases.CreateDB.ROOM_SIZE_M, roomSizeM);
        values.put(DataBases.CreateDB.ROOM_SIZE_P, roomSizeP);
        values.put(DataBases.CreateDB.OPTION, option);
        values.put(DataBases.CreateDB.ANIMAL, animal);
        values.put(DataBases.CreateDB.ELEVATOR, elevator);
        values.put(DataBases.CreateDB.PARKING, parking);
        values.put(DataBases.CreateDB.DETAIL, detail);
        return mDB.update(DataBases.CreateDB.TABLE_NAME, values, "_id=" + id, null) > 0;
    }

    public void deleteAllColumns() {
        mDB.delete(DataBases.CreateDB.TABLE_NAME, null, null);
    }

    public boolean deleteColumn(int id) {
        return mDB.delete(DataBases.CreateDB.TABLE_NAME, "_id=" + id, null) > 0;
    }
}
