package com.example.berius;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.content.Context;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView messageRecycle;
    private MessagesRecyclerUtils.MessageAdapter messageAdapter;
    private Parcelable listState;
    private EditText editText;
    private ArrayList<Message> currentMessages = new ArrayList<>();



    /** restoring the text from the "textView"*/
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable("LIST_STATE");
            messageRecycle.getLayoutManager().onRestoreInstanceState(listState);
            editText.setText(savedInstanceState.get("EDIT_TEXT").toString());
            currentMessages = (ArrayList<Message>) savedInstanceState.getSerializable("CUR_MES");
            messageAdapter.setList(currentMessages);
            messageAdapter.notifyDataSetChanged();
            Log.d("list length", "list length is:"+ messageAdapter.getItemCount());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        Button button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);

        //final ArrayList<Message> arrMessagePackage;
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // retrieving messages from SP
        Gson gson = new Gson();
        String json = sp.getString("Set","");
        if (json.isEmpty()){
            db.collection("messagesDB").orderBy("time").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("yes", document.getId() + " => " + document.getData());
                                    String content = document.get("content").toString();
                                    String id = document.get("id").toString();
                                    String time = document.get("time").toString();
                                    Message newMessage = new Message(content,id,time);
                                    currentMessages.add(newMessage);
                                }
                            } else {
                                Log.d("no", "Error getting documents: ", task.getException());
                                Toast.makeText(MainActivity.this,"No messages to show",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else{
            Type type = new TypeToken<ArrayList<Message>>(){}.getType();
            currentMessages = gson.fromJson(json, type);
        }


        messageRecycle = (RecyclerView) findViewById(R.id.RecyclerView_messages);
        messageAdapter = new MessagesRecyclerUtils.MessageAdapter(this, currentMessages, db);
        messageRecycle.setAdapter(messageAdapter);
        messageRecycle.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter.notifyDataSetChanged();

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String newText = editText.getText().toString();
                if(newText.equals("")){
                    Context context = getApplicationContext();
                    CharSequence text = "You just can't say nothing";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    // add message to remote db
                    SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                    final String ts = s.format(new Date());
                    Map<String, Object> messageRDB = new HashMap<>();
                    messageRDB.put("content", newText);
                    messageRDB.put("time", ts);
                    final String id = UUID.randomUUID().toString();
                    messageRDB.put("id", id);

                    // Add a new document with a generated ID
                    db.collection("messagesDB")
                            .document(id).set(messageRDB)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("yes", "DocumentSnapshot successfully written!");
                                    Message newMessage = (Message) new Message(newText, id, ts);
                                    messageAdapter.addMessage(newMessage);
                                    messageAdapter.notifyDataSetChanged();
                                    editText.getText().clear();

                                    // add message to local db
                                    Gson gson = new Gson();
                                    String json = gson.toJson(currentMessages);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("Set", json);
                                    editor.commit();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("no", "Error writing document", e);
                                }
                            });
                }
            }
        });
    }

    /**saving the current text in the "textView"*/
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Parcelable listState = messageRecycle.getLayoutManager().onSaveInstanceState();
        savedInstanceState.putParcelable("LIST_STATE",listState);
        savedInstanceState.putString("EDIT_TEXT", editText.getText().toString());
        savedInstanceState.putSerializable("CUR_MES", currentMessages = messageAdapter.getMessageList());
    }

    public void sendMesssage(View view) {
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }



}

