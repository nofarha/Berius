//package com.example.berius;
//
//import android.content.Context;
//
//import androidx.room.ColumnInfo;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
///**To access the data from the database you used Dao (Data Access Object).
//In Dao interface, you declare all the methods needed to work with the database**/
//
//@Database(entities = {UserMessages.class}, version = 1)
//public abstract class AppDatabase extends RoomDatabase {
//
//    private static AppDatabase INSTANCE;
//
//    public static AppDatabase getIDataBase(Context context){
//        if (INSTANCE == null){
//            synchronized (AppDatabase.class){
//                if (INSTANCE == null){
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "appDatabase").build();
//                }
//
//            }
//        }
//        return INSTANCE;
//    }
//
//    public abstract UserMessagesDao userMessagesDao();
//
//
//
//
//}
