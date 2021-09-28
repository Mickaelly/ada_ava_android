package com.example.mickaelly.ada_ava.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mickaelly.ada_ava.model.Classroom;
import com.example.mickaelly.ada_ava.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBCommandsClassRoom {

    private SQLiteDatabase db;

    public DBCommandsClassRoom(Context context){
        AdaCore auxDB = new AdaCore(context);
        db = auxDB.getWritableDatabase();
    }

    public long insert(Classroom classroom){
        Classroom validation = selectClassroom(classroom);
        if(validation != null &&
                validation.getUrl().equals(classroom.getUrl()) &&
                validation.getIdCourse() == classroom.getIdCourse()){
            return 0;
        }else{
            ContentValues valores = new ContentValues();
            valores.put("name", classroom.getName());
            valores.put("idCourse", classroom.getIdCourse());
            valores.put("url", classroom.getUrl());
            try{
                long result = db.insert("classroom", null, valores);
                ContentValues relation = new ContentValues();
                relation.put("idClassroom", selectClassroom(classroom).getId());
                relation.put("idCourse", classroom.getIdCourse());
                db.insert("relation_course_classroom", null, relation);
                return result;
            }catch (Exception e){
                return -1;
            }
        }
    }

    public long delete(Classroom classroom){
        long result = db.delete("classroom", " _id = ?", new String[]{String.valueOf(classroom.getId())});
        return result;
    }

    public List<Classroom> selectAll(long idCourse){
        List<Classroom> classrooms = new ArrayList<Classroom>();
        String[] colunas = new String[]{"_id", "name", "idCourse", "url"};
        Cursor cursor = db.query("classroom", colunas, " idCourse = ?", new String[]{String.valueOf(idCourse)}, null, null, "name ASC");
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Classroom classroom = new Classroom();
                classroom.setId(cursor.getLong(0));
                classroom.setName(cursor.getString(1));
                classroom.setIdCourse(cursor.getLong(2));
                classroom.setUrl(cursor.getString(3));
                classrooms.add(classroom);
            }while (cursor.moveToNext());
        }
        return classrooms;
    }

    public Classroom selectClassroom(Classroom c){
        String[] colunas = new String[]{"_id", "name", "idCourse", "url"};
        Cursor cursor = db.query("classroom", colunas, " name = ?", new String[]{c.getName()}, null, null, null);
        if(cursor != null && cursor.getCount() != 0){
            Classroom classRoom = new Classroom();
            cursor.moveToFirst();
            classRoom.setId(cursor.getLong(0));
            classRoom.setName(cursor.getString(1));
            classRoom.setIdCourse(cursor.getLong(2));
            classRoom.setUrl(cursor.getString(3));
            return classRoom;
        }
        return null;
    }
}
