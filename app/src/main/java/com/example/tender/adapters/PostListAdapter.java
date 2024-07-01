package com.example.tender.adapters;

import static android.content.Intent.getIntent;
import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;
import com.example.tender.activities.CommentActivity;
import com.example.tender.models.Post;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;


import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.MyViewHolder> {
    Context context;
    List<Post> list;
    List<String> postIDList;
    private FirebaseFirestore firestore;

    public PostListAdapter(Context context, List<Post> list, List<String> postIDList) {
        this.context = context;
        this.list = list;
        this.postIDList = postIDList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_layout_match, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Post post = list.get(position);
        holder.username.setText(post.getUsername());
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(post.getTitle());
        messageBuilder.append("\n");
        messageBuilder.append(post.getMessage());
        holder.message.setText(messageBuilder.toString());
        holder.date.setText(post.getTimestamp().toString());
        holder.likeCount.setText(String.valueOf(post.getLikecount()));

        Picasso.get()
                .load(post.getImageUrl())
                .into(holder.imageView);

        holder.imgChat.setOnClickListener(v -> {
            // Start the CommentActivity
            Intent intent = new Intent(context, CommentActivity.class);
            intent.putExtra("postID", postIDList.get(position));
            context.startActivity(intent);
        });

        holder.likeCount.setOnClickListener(v -> {
            String postId = postIDList.get(position);
            incrementLikeCount(postId, post, holder, 1);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username, title, message, date, likeCount;
        ImageView imageView, profile, imgChat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.text_name);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.text_date);
            imageView = itemView.findViewById(R.id.img_content);
            profile = itemView.findViewById(R.id.profile_image);
            imgChat = itemView.findViewById(R.id.img_chat);
            likeCount = itemView.findViewById(R.id.like_count);
        }
    }

    private void incrementLikeCount(String postId, Post post, MyViewHolder holder, int incrementBy) {
        firestore.collection("post")
                .document(postId)
                .update("likeCount", FieldValue.increment(incrementBy))
                .addOnSuccessListener(aVoid -> {
                    // Likecount updated successfully
                    // Update the UI to reflect the new like count
                    Long newLikeCount = post.getLikecount() + incrementBy;
                    holder.likeCount.setText(String.valueOf(newLikeCount));
                })
                .addOnFailureListener(e -> {
                    // Handle error
                    Log.e(TAG, "Error updating like count: " + e.getMessage());
                });
    }
}