package com.example.mickaelly.ada_ava.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mickaelly.ada_ava.model.MessageForum;
import com.example.mickaelly.ada_ava.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBCommandsForum {

    private SQLiteDatabase db;

    public DBCommandsForum(Context context){
        AdaCore auxDB = new AdaCore(context);
        db = auxDB.getWritableDatabase();
    }

    public long insert(MessageForum message){

        ContentValues valores = new ContentValues();
        valores.put("nameUser", message.getNameUser());
        valores.put("date", message.getDate());
        valores.put("message", message.getMessage());
        valores.put("theme", message.getTheme());
        try{
            long result = db.insert("forum", null, valores);
            return result;
        }catch (Exception e){
            return -1;
        }
    }

    public List<MessageForum> selectAll(String theme){
        List<MessageForum> messages = new ArrayList<MessageForum>();
        String[] colunas = new String[]{"_id", "nameUser", "date", "message", "theme"};
        Cursor cursor = db.query("forum", colunas, " theme = ?", new String[]{theme}, null, null, "date DESC", null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                MessageForum messageForum = new MessageForum();
                messageForum.setId(cursor.getLong(0));
                messageForum.setNameUser(cursor.getString(1));
                messageForum.setDate(cursor.getString(2));
                messageForum.setMessage(cursor.getString(3));
                messageForum.setTheme(cursor.getString(4));
                messages.add(messageForum);
            }while (cursor.moveToNext());
        }
        return messages;
    }

    public List<String> selectTopics(){
        List<String> topics = new ArrayList<String>();
        List<String> topicsAux = new ArrayList<String>();
        String[] colunas = new String[]{"theme"};
        Cursor cursor = db.query("forum", colunas, null, null, null, null, "theme ASC");
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                topicsAux.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        if(!topicsAux.isEmpty()){
            String t = "";
            for(int i=0;i<topicsAux.size();i++){
                if(!(i>=topicsAux.size())){
                    if (!t.equals(topicsAux.get(i))){
                        t = topicsAux.get(i);
                        topics.add(t);
                    }
                }
            }
        }
        return topics;
    }
}
