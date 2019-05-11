package com.example.berius;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MessagesRecyclerUtils {

    public static class MessageAdapter extends RecyclerView.Adapter {

        private Context context;
        private FirebaseFirestore db;
        private ArrayList<Message> messagesList;



        public MessageAdapter(Context context, ArrayList<Message> messageList, FirebaseFirestore db){
            System.out.println(messageList);
            this.context = context;
            this.messagesList = messageList;
            this.db = db;
            notifyDataSetChanged();// run everything on send
        }

        public void addMessage(Message newMessageList){
            this.messagesList.add(newMessageList);
            notifyDataSetChanged();
        }

        public void deleteMessage(Message message){
            this.messagesList.remove(message);
            notifyDataSetChanged();
        }


        public void setList(ArrayList<Message> messageList){
            this.messagesList = messageList;
        }


        public ArrayList<Message> getMessageList(){
            return this.messagesList;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View itemView;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message, parent, false);
            return new MessageHolder(itemView);
        }

        @Override
        public int getItemCount() {

            return this.messagesList.size();
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
            final Message message = (Message) this.messagesList.get(position);
            ((MessageHolder) holder).bind(message);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(context).setMessage("Are you sure you want to delete?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            db.collection("messagesDB").document(message.getId()).delete()

                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("tag", "DocumentSnapshot successfully deleted!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("tag", "Error deleting document", e);
                                        }
                                    });
                            deleteMessage(message);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return true;
                }
            });



        }

        public class MessageHolder extends RecyclerView.ViewHolder{
            TextView messageText;
            TextView time;

            public MessageHolder(final View itemView) {
                super(itemView);
                itemView.findViewById(R.id.RecyclerView_messages);
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






