package com.example.mickaelly.ada_ava.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mickaelly.ada_ava.model.Course;
import com.example.mickaelly.ada_ava.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBCommandsCourse {

    private SQLiteDatabase db;

    public DBCommandsCourse(Context context){
        AdaCore auxDB = new AdaCore(context);
        db = auxDB.getWritableDatabase();
    }

    public long insert(Course course){
        ContentValues valores = new ContentValues();
        valores.put("name", course.getName());
        valores.put("level", course.getLevel());
        try{
            long result = db.insert("course", null, valores);
            return result;
        }catch (Exception e){
            return -1;
        }
    }

    public void update(Course course){
        ContentValues valores = new ContentValues();
        valores.put("name", course.getName());
        valores.put("type", course.getLevel());
        db.update("course", valores, " name = ? AND type = ?", new String[]{course.getName(), course.getLevel()});
    }

    public void delete(Course course){
        db.delete("course", " _id = ?", new String[]{String.valueOf(course.getId())});
    }

    public List<Course> selectAll(){

        List<Course> courses = new ArrayList<Course>();
        String[] colunas = new String[]{"_id", "name", "level"};
        Cursor cursor = db.query("course", colunas, null, null, null, null, "name ASC");
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Course course = new Course();
                course.setId(cursor.getLong(0));
                course.setName(cursor.getString(1));
                course.setLevel(cursor.getString(2));
                courses.add(course);
            }while (cursor.moveToNext());
        }
        return courses;
    }

    public Course selectCourse(String name){
        String[] colunas = new String[]{"_id", "name", "type"};
        Cursor cursor = db.query("course", colunas, " name = ?", new String[]{name}, null, null, null);
        Course course = new Course();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                course.setId(cursor.getLong(0));
                course.setName(cursor.getString(1));
                course.setLevel(cursor.getString(2));
            }while (cursor.moveToNext());
        }
        return course;
    }
}
