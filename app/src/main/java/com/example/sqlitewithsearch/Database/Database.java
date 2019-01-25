package com.example.sqlitewithsearch.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.sqlitewithsearch.Model.Friend;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import static android.app.DownloadManager.COLUMN_ID;

public class Database extends SQLiteAssetHelper {

   // private static final String DB_NAME = "friend.db"; //database name
   private static final String DB_NAME = "friend.db"; //database name
    private static final int DB_VER = 19; //version number


    //constructor
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }


    //to insert new friend
    public void insertFriend(Friend friend){
   // public void insertFriend(String Name, String Address,String Email,String Phone){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.putNull(String.valueOf(friend));
        values.put("Name",friend.getName());
        values.put("Address",friend.getAddress());
        values.put("Email",friend.getEmail());
        values.put("Phone",friend.getPhone());
        values.put("personImage",friend.getPersonImage());
      //  values.get(String.valueOf(friend));
      db.insert("Friends",null,values);

        db.close();
    }

    //to delete friend
    public void deleteFriend(String Name){
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs = {Name};
        db.delete("Friends","Name" + " LIKE ? ", whereArgs);
    }

    //to update friend
    public void updateFriend(String oldName,Friend friend){
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Name",friend.getName());
            values.put("Address",friend.getAddress());
            values.put("Email",friend.getEmail());
            values.put("Phone",friend.getPhone());
            values.put("personImage",friend.getPersonImage());
            String[] whereArgs = {oldName};
            db.update("Friends", values, "Name"    + " LIKE ? ", whereArgs);
        }



    //function get all friends
    public List<Friend> getFriends(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"id","Name","Address","Phone","Email","personImage"} ; //as database table
        String tableName = "Friends"; //table name

        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);
        List<Friend> result = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                Friend friend = new Friend();
                friend.setId(cursor.getInt(cursor.getColumnIndex("id")));
                friend.setName(cursor.getString(cursor.getColumnIndex("Name")));
                friend.setAddress(cursor.getString(cursor.getColumnIndex("Address")));
                friend.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
                friend.setPhone(cursor.getString(cursor.getColumnIndex("Phone")));
                friend.setPersonImage(cursor.getBlob(cursor.getColumnIndex("personImage")));

                result.add(friend);
            }while (cursor.moveToNext());
        }
        return result;
    }


    //function get all friend's name
    public List<String> getNames(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Name"} ; //as database table
        String tableName = "Friends"; //table name

        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);
        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                result.add(cursor.getString(cursor.getColumnIndex("Name")));
            }while (cursor.moveToNext());
        }
        return result;
    }

    //function get friend by name
    public List<Friend> getFriendByName(String name){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"id","Name","Address","Phone","Email","personImage"} ; //as database table
        String tableName = "Friends"; //table name

        qb.setTables(tableName);
        //this will like query : select * from Friends where name LIKE %pattern%
        Cursor cursor = qb.query(db,sqlSelect,"Name LIKE ?",new String[]{"%"+name+"%"},null,null,null);
        //if we need to get extract name ,just change
       // Cursor cursor = qb.query(db,sqlSelect,"Name = ?",new String[]{name},null,null,null);


        List<Friend> result = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                Friend friend = new Friend();
                friend.setId(cursor.getInt(cursor.getColumnIndex("id")));
                friend.setName(cursor.getString(cursor.getColumnIndex("Name")));
                friend.setAddress(cursor.getString(cursor.getColumnIndex("Address")));
                friend.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
                friend.setPhone(cursor.getString(cursor.getColumnIndex("Phone")));
                friend.setPersonImage(cursor.getBlob(cursor.getColumnIndex("personImage")));

                result.add(friend);
            }while (cursor.moveToNext());
        }
        return result;
    }

}
