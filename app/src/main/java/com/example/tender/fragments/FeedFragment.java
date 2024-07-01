package com.example.tender.fragments;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;
import com.example.tender.adapters.PostListAdapter;
import com.example.tender.models.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FeedFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    PostListAdapter postListAdapter;
    List<Post> list;
    List<String> postIDList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view_matchs);
        firestore = FirebaseFirestore.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        postListAdapter = new PostListAdapter(getContext(), list,postIDList);
        recyclerView.setAdapter(postListAdapter);

        firestore.collection("post")
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
                            String postID = document.getId();
                            String username = document.getString("username");
                            String title = document.getString("title");
                            String message = document.getString("message");
                            String imageUrl = document.getString("imageUrl");
                            Date timestamp = document.getDate("timestamp");
                            Long likeCount = document.getLong("likeCount");

                            // Create a new Post instance with the data
                            Post post = new Post(username, title, message, imageUrl, timestamp,likeCount );
                            list.add(post);
                            postIDList.add(postID);
                        }
                        postListAdapter.notifyDataSetChanged();
                    }
                });

        return rootView;
    }
}

