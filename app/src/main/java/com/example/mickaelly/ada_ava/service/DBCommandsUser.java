package com.example.mickaelly.ada_ava.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mickaelly.ada_ava.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBCommandsUser {

    private SQLiteDatabase db;

    public DBCommandsUser(Context context){
        AdaCore auxDB = new AdaCore(context);
        db = auxDB.getWritableDatabase();
    }

    public long insert(User user){
        User validation = selectUser(user.getEmail());
        if(validation != null){
            return 0;
        }else{
            ContentValues valores = new ContentValues();
            valores.put("name", user.getName());
            valores.put("email", user.getEmail());
            valores.put("type", user.getTypeUser());
            valores.put("password", user.getPassword());
            try{
                long result = db.insert("user", null, valores);
                return result;
            }catch (Exception e){
                return -1;
            }
        }
    }

    public long update(User user){
        ContentValues valores = new ContentValues();
        valores.put("name", user.getName());
        valores.put("email", user.getEmail());
        valores.put("type", user.getTypeUser());
        valores.put("password", user.getPassword());
        try{
            long result = db.update("user", valores, " name = ? AND email = ? AND type = ? AND password = ?", new String[]{user.getName(), user.getEmail(), user.getTypeUser(), user.getPassword()});

            return result;
        }catch (Exception e){
            return -1;
        }
    }

    public long changePassword(String email, String password){
        User validation = selectUser(email);
        if(validation == null){
            return 0;
        }else{
            ContentValues valores = new ContentValues();
            valores.put("password", password);
            try{
                long result = db.update("user", valores, " email = ?", new String[]{email});
                return result;
            }catch (Exception e){
                return -1;
            }
        }
    }

    public long delete(User user){
        return db.delete("user", " email = ?", new String[]{user.getEmail()});
    }

    public void selectAll(){
        List<User> users = new ArrayList<User>();
        String[] colunas = new String[]{"_id", "name", "email", "type", "password"};
        Cursor cursor = db.query("user", colunas, null, null, null, null, "name ASC");
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                User user = new User();
                user.setId(cursor.getLong(0));
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setTypeUser(cursor.getString(3));
                user.setPassword(cursor.getString(4));
                users.add(user);
            }while (cursor.moveToNext());
        }
    }

    public User selectUser(String email){
        String[] colunas = new String[]{"_id", "name", "email", "type", "password"};
        Cursor cursor = db.query("user", colunas, " email = ?", new String[]{email}, null, null, null);
        if(cursor != null && cursor.getCount() != 0){
            User user = new User();
            cursor.moveToFirst();
            user.setId(cursor.getLong(0));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setTypeUser(cursor.getString(3));
            user.setPassword(cursor.getString(4));
            return user;
        }
        return null;
    }

    public User login(String login, String password){
        String[] colunas = new String[]{"_id", "name", "email", "type", "password"};
        Cursor cursor = db.query("user", colunas, " email = ?", new String[]{login}, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            User user = new User();
            user.setId(cursor.getLong(0));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setTypeUser(cursor.getString(3));
            user.setPassword(cursor.getString(4));
            if (user.getPassword().equals(password)){
                return user;
            }
            return new User();
        }
        return null;
    }
}
