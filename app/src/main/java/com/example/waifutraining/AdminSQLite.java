package com.example.waifutraining;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class AdminSQLite extends SQLiteOpenHelper {

    public AdminSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Exercises(ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE CHAR ,PUSH_UP INTEGER , SIT_UP INTEGER , SQUAT INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Ejercicios");
    }

    public String getTodayDate(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 0);
            Date date = c.getTime();
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        return formatDate.format(date);
    }
    public Boolean setInsertData(Integer squat,Integer sit_up, Integer push_up,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataRecord= new ContentValues();
            dataRecord.put("SQUAT", squat);
            dataRecord.put("PUSH_UP", push_up);
            dataRecord.put("SIT_UP", sit_up);
            dataRecord.put("DATE", date);
        db.insert("Exercises", null,dataRecord);
        return true;
    }
    public void modifyData(String date,String tipeOfExercise, int  addQuantity){
        ArrayList<Integer> data= getData(date);
        switch (tipeOfExercise){
            case "push_up":
                data.set(0, data.get(0)+addQuantity);
                break;
            case "sit_up":
                data.set(1, data.get(1)+addQuantity);
                break;
            case "squat":
                data.set(2, data.get(2)+addQuantity);
                break;
            default:
                System.out.println("Exercise don't Exists");
                break;
        }
        setData(date,data);
    }
    public ArrayList<Integer> getData(String date ){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor row = db.rawQuery("select PUSH_UP,SIT_UP,SQUAT from Exercises where DATE = '"+date+"'",null);
        ArrayList<Integer> data= new ArrayList<>();
        if(row.moveToFirst()){
            data.add(row.getInt(0));
            data.add(row.getInt(1));
            data.add(row.getInt(2));
        }else{
            data= new ArrayList<>( Arrays.asList(0,0,0));
            setInsertData(0,0,0,date);
        }
        return data;
    }
    public void setData(String dateString, ArrayList<Integer> data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataRecord= new ContentValues();
            dataRecord.put("PUSH_UP", data.get(0));
            dataRecord.put("SIT_UP", data.get(1));
            dataRecord.put("SQUAT", data.get(2));
        int cantidad = db.update("Exercises",dataRecord,"date = '"+dateString+"'",null);
    }

}