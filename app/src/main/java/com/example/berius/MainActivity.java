package com.example.berius;

import android.os.Parcelable;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private RecyclerView messageRecycle;
    private MessagesRecyclerUtils.MessageAdapter messageAdapter;
    private Parcelable listState;
    private EditText editText;
    private ArrayList<Message> currentMessages;

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
            System.out.println(messageAdapter.getMessageList().toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageRecycle = (RecyclerView) findViewById(R.id.RecyclerView_messages);
        final ArrayList<Message> messageList = new ArrayList<Message>();
        messageAdapter = new MessagesRecyclerUtils.MessageAdapter(this, messageList);
        messageRecycle.setAdapter(messageAdapter);
        messageRecycle.setLayoutManager(new LinearLayoutManager(this));
        Button button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String newText = editText.getText().toString();
                if(newText.equals("")){
                    Context context = getApplicationContext();
                    CharSequence text = "You just can't say nothing";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    Message newMessage = (Message) new Message(newText);
                    messageAdapter.addMessage(newMessage);
                    messageAdapter.notifyDataSetChanged();
                    editText.getText().clear();
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

}
