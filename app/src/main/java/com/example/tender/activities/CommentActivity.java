package com.example.tender.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;
import com.example.tender.adapters.CommentListAdapter;
import com.example.tender.models.Comment;
import com.example.tender.models.Post;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class CommentActivity extends AppCompatActivity {
    private Button btnReturn;
    private Button btnComment;
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    CommentListAdapter commentListAdapter;
    List<Comment> list;
    String postID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comment);

        btnReturn = findViewById(R.id.returnButton);
        btnComment = findViewById(R.id.CommentButton);

        postID = getIntent().getStringExtra("postID");

        recyclerView = findViewById(R.id.CommentRecyclerView);
        firestore = FirebaseFirestore.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this));

        list = new ArrayList<>();
        commentListAdapter = new CommentListAdapter(CommentActivity.this, list);
        recyclerView.setAdapter(commentListAdapter);

        loadComments();

        setupButtonListeners();
    }

    private void loadComments() {
        firestore.collection("post").document(postID)
                .collection("comment")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            // Handle any errors
                            return;
                        }

                        list.clear();
                        for (QueryDocumentSnapshot document : snapshot) {
                            // Get the data from the document
                            String username = document.getString("username");
                            String message = document.getString("message");
                            Date timestamp = document.getDate("timestamp");

                            // Create a new Comment instance with the data
                            Comment comment = new Comment(username, message, timestamp);
                            list.add(comment);
                        }
                        commentListAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void setupButtonListeners() {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This will return to the previous activity
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement comment posting logic
                // For now, it just returns to MainActivity
                Intent intent = new Intent(CommentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
