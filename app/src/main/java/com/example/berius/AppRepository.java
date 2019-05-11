//package com.example.berius;
//
//
//import android.app.Application;
//
//import java.util.List;
//import android.os.AsyncTask;
//import androidx.lifecycle.LiveData;
//
//public class AppRepository {
//
//    private UserMessagesDao userMessagesDao;
//
//
//    private LiveData<List<UserMessages>> allMessages;
//    private LiveData<List<UserMessages>> allMessagesByUserIds;
//
//    AppRepository(Application application){
//        AppDatabase db = AppDatabase.getIDataBase(application);
//
//        userMessagesDao = db.userMessagesDao();
//
//
//        allMessages = userMessagesDao.getAllMessages();
//    }
//
//
//
//    LiveData<List<UserMessages>> getAllMessages(){
//        return allMessages;
//    }
//
//
//    public void insert(UserMessages userMessage){
//        new insertAsyncTask(userMessagesDao).execute(userMessage);
//    }
//
//
//    private static class insertAsyncTask extends AsyncTask<UserMessages, Void, Void> {
//
//        private UserMessagesDao mAsyncTaskDao;
//
//        insertAsyncTask(UserMessagesDao dao) {
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(final UserMessages... params) {
//            mAsyncTaskDao.insert(params[0]);
//            return null;
//        }
//    }
//
//
//
//}
