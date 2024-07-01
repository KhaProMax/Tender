package com.example.tender.activities;

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
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentActivity extends AppCompatActivity
{
    private Button btnReturn;
    private Button btnComment;
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    CommentListAdapter commentListAdapter;
    List<Comment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comment);

        recyclerView =findViewById(R.id.CommentRecyclerView);
        firestore = FirebaseFirestore.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
        list = new ArrayList<>();
        recyclerView.setAdapter(commentListAdapter);
        String postID = getIntent().getStringExtra("postID");

        assert postID != null;
        firestore.collection("post")
                .document(postID)
                .collection("comment")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            // Handle any errors
                            return;
                        }

                        List<Comment> comments = new ArrayList<>();
                        for (QueryDocumentSnapshot document : snapshot) {
                            // Get the data from the document
                            String username = document.getString("username");
                            String message = document.getString("message");
                            Date timestamp = document.getDate("timestamp");

                            // Create a new Comment instance with the data
                            Comment comment = new Comment(username, message, timestamp);
                            comments.add(comment);
                        }
                        commentListAdapter.notifyDataSetChanged();
                    }
                });

        btnReturn=findViewById(R.id.returnButton);
        btnComment=findViewById(R.id.CommentButton);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
