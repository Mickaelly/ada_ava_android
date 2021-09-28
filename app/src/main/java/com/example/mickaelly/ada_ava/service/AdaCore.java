package com.example.mickaelly.ada_ava.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdaCore extends SQLiteOpenHelper {

    private static final String NOME_BD = "ada";
    private static final int VERSAO_BD = 2;


    public AdaCore(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(_id integer primary key autoincrement, name text not null, email text not null, type text not null, password text not null);");
        db.execSQL("create table course(_id integer primary key autoincrement, name text not null, level text not null not null);");
        db.execSQL("create table relation_course_classroom(idClassroom integer not null, idCourse integer not null);");
        db.execSQL("create table classroom(_id integer primary key autoincrement, name text not null, idCourse integer not null, url text not null);");
        db.execSQL("create table forum(_id integer primary key autoincrement, nameUser text not null, date text not null, message text not null, theme text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table user;");
        db.execSQL("drop table course;");
        db.execSQL("drop table relation_course_classroom;");
        db.execSQL("drop table classroom;");
        db.execSQL("drop table forum;");
        onCreate(db);
    }
}
