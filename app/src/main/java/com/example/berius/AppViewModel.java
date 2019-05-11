//package com.example.berius;
//
//import android.app.Application;
//import android.os.AsyncTask;
//
//import java.util.List;
//
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//
//public class AppViewModel extends AndroidViewModel {
//
//    private final LiveData<List<UserMessages>> allMessagesList;
//    private AppDatabase appDB;
//
//    public AppViewModel(Application application){
//        super(application);
//        appDB = AppDatabase.getIDataBase(this.getApplication());
//        allMessagesList = appDB.userMessagesDao().getAllMessages();
//    }
//
//    public LiveData<List<UserMessages>> getAllMessages(){
//        return allMessagesList;
//    }
//
//    public void deleteMessage(UserMessages userMessage) {
//        new deleteAsyncTask(appDB).execute(userMessage);
//    }
//
//    private static class deleteAsyncTask extends AsyncTask<UserMessages, Void, Void> {
//
//        private AppDatabase db;
//
//        deleteAsyncTask(AppDatabase appDatabase) {
//            db = appDatabase;
//        }
//
//        @Override
//        protected Void doInBackground(final UserMessages... params) {
//            db.userMessagesDao().deleteMessage(params[0]);
//            return null;
//        }
//
//    }
//
//}
