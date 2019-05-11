//package com.example.berius;
//
//import java.util.List;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.OnConflictStrategy;
//import androidx.room.Query;
//
//@Dao
//public interface UserMessagesDao {
//    @Query("SELECT * FROM UserMessages")
//    LiveData<List<UserMessages>> getAllMessages();
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void addMessage(UserMessages userMessages);
//
//    @Delete
//    void deleteMessage(UserMessages userMessages);
//
//}
