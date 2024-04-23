package com.example.tender.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tender.R;

public class ChatActivity extends AppCompatActivity {
    private ListView messageListView;
    private EditText messageEditText;
    private Button sendButton;
    private String receiverId, receiverName;
    private String senderId, senderName;
//    private DatabaseReference messagesDatabaseReference;
//    private FirebaseListAdapter<Message> messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Initialize views
        messageListView = findViewById(R.id.messageListView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        // Initialize Firebase
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        messagesDatabaseReference = database.getReference().child("messages").child("chatRoomId");

        // Initialize message adapter
//        messageAdapter = new FirebaseListAdapter<Message>(
//                this,
//                Message.class,
//                android.R.layout.simple_list_item_1,
//                messagesDatabaseReference) {
//            @Override
//            protected void populateView(View v, Message model, int position) {
//                TextView messageTextView = v.findViewById(android.R.id.text1);
//                messageTextView.setText(model.getMessage());
//            }
//        };

//        messageListView.setAdapter(messageAdapter);

        // Set onClickListener for sendButton
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendMessage();
            }
        });
    }

//    private void sendMessage() {
//        String messageText = messageEditText.getText().toString().trim();
//        if (!messageText.isEmpty()) {
//            String messageId = messagesDatabaseReference.push().getKey();
//            Message message = new Message(messageText, "senderUserId", System.currentTimeMillis());
//            messagesDatabaseReference.child(messageId).setValue(message);
//            messageEditText.setText("");
//        }
//    }
}

