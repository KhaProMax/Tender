package com.example.tender.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.adapters.ChatMessageAdapter;
import com.example.tender.entities.ChatMessage;
import com.example.tender.R;

import java.util.ArrayList;
import java.util.List;

// Chat Box
public class ChatBoxFragment extends Fragment {

    private TextView chatBoxTitle;
    private RecyclerView chatBoxRecyclerView;
    private ChatMessageAdapter chatMessageAdapter;
    private List<ChatMessage> chatMessages;
    private Button returnBtn;

    private String chatId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_box, container, false);

        chatBoxTitle = view.findViewById(R.id.chatBoxTitle);
        chatBoxRecyclerView = view.findViewById(R.id.chatBoxRecyclerView);

        chatMessages = new ArrayList<>();
        chatMessageAdapter = new ChatMessageAdapter(chatMessages);

        chatBoxRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatBoxRecyclerView.setAdapter(chatMessageAdapter);

        // Get the chatId from the arguments
        if (getArguments() != null) {
            chatId = getArguments().getString("chatId");
            chatBoxTitle.setText("Chat Box " + chatId);  // You can set this to the actual title
        }

        returnBtn = view.findViewById(R.id.returnButton);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle return button click (e.g., navigate back to previous fragment)
                NavController navController = NavHostFragment.findNavController(ChatBoxFragment.this);
                navController.popBackStack();
            }
        });

        // Load dummy data
        loadDummyData();

        return view;
    }

    private void loadDummyData() {
        chatMessages.add(new ChatMessage("Hello! How are you?", "user1"));
        chatMessages.add(new ChatMessage("I'm good, thanks! How about you?", "user2"));
        chatMessages.add(new ChatMessage("I'm doing great, thanks for asking!", "user1"));
        chatMessages.add(new ChatMessage("What are you up to?", "user2"));
        chatMessages.add(new ChatMessage("Just working on a project.", "user1"));

        chatMessageAdapter.notifyDataSetChanged();
    }
}

