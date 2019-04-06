package com.example.berius;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    TextView textView;
    EditText editText;

    /** restoring the text from the "textView"*/
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String textViewSaved = savedInstanceState.getString("textView", "");
        this.textView.setText(textViewSaved);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textView = (TextView) findViewById(R.id.showText);
        Button buttonSend = (Button) findViewById(R.id.button);
        buttonSend.setOnClickListener(this);

    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        this.editText = (EditText) findViewById(R.id.editText);
        String message = this.editText.getText().toString();
    }

    public void onClick(View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        this.textView.setText(message);
        editText.setText("");
    }


    /**saving the current text in the "textView"*/
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (this.textView.getText() != null) {
           String textView = this.textView.getText().toString();
           savedInstanceState.putString("textView", textView);
        }

    }
}
