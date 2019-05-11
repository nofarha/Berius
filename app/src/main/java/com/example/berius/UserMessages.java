//package com.example.berius;
//
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.ForeignKey;
//import androidx.room.PrimaryKey;
//
//
////@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "uid",
////        childColumns = "userId", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE))
//
//@Entity
//public class UserMessages {
//    @PrimaryKey (autoGenerate = true) public int id;
//
//    @ColumnInfo(name = "content")
//    public final String content;
//
//
//    public UserMessages(String content){
//        this.content = content;
//    }
//
//    public String getText(){
//        return this.content;
//    }
//
//
//
//
//}
