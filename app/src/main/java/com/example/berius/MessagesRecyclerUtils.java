package com.example.berius;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MessagesRecyclerUtils {

    public static class MessageAdapter extends RecyclerView.Adapter{

        private Context context;
        private ArrayList<Message> messageList;

        public MessageAdapter(Context context, ArrayList<Message> messageList){
            System.out.println(messageList);
            this.context = context;
            this.messageList = messageList;
            notifyDataSetChanged();// run everything on send
        }

        public void addMessage(Message newMessage){
            this.messageList.add(newMessage);
            System.out.println(newMessage);
            notifyItemInserted(messageList.size()); // might be minus one

        }

        public void setList(ArrayList<Message> messageList){
            this.messageList = messageList;
        }


        public ArrayList<Message> getMessageList(){
            return this.messageList;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message, parent, false);
            return new MessageHolder(view);
        }

        @Override
        public int getItemCount() {
            return this.messageList.size();
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Message message = (Message) this.messageList.get(position);
            ((MessageHolder) holder).bind(message);
        }

        private class MessageHolder extends RecyclerView.ViewHolder{
            TextView messageText;
            TextView time;

            public MessageHolder(View itemView) {
                super(itemView);
                messageText = (TextView) itemView.findViewById(R.id.text_message_body);
                time = (TextView) itemView.findViewById(R.id.text_message_time);

            }

            void bind(Message message){
                messageText.setText(message.getText());
                String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                String minutes = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
                time.setText(hour+":"+minutes);
            }

            }

        }
    }






