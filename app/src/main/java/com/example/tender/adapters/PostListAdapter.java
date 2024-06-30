package com.example.tender.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;
import com.example.tender.models.Post;
import com.example.tender.models.User;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.MyViewHolder> {

//    private Context context;
//    private List<Post> postList;
//    private FirebaseFirestore db;
//
//    class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView name, date, location, age, message, title;
//        ImageView imgProfile, imgContent;
//
//        MyViewHolder(View view) {
//            super(view);
//            name = view.findViewById(R.id.text_name);
//            date = view.findViewById(R.id.text_date);
////            location = view.findViewById(R.id.text_location);
////            age = view.findViewById(R.id.text_age);
//            imgProfile = view.findViewById(R.id.img_profile);
//            title = view.findViewById(R.id.title);
//            message = view.findViewById(R.id.messasge);
//            imgContent = view.findViewById(R.id.img_content);
//        }
//    }
//
//    public PostListAdapter(Context context, List<Post> postList) {
//        this.context = context;
//        this.postList = postList;
//        this.db = FirebaseFirestore.getInstance();
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.adapter_layout_match, parent, false);
//
//        return new MyViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        final Post post = postList.get(position);
//        final String username = post.getUsername();
//
//        // Retrieve the user data from the Firestore database
//        db.collection("users").document(username).get();
//
//        // Change format for timestamp before display
//        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        String formattedDate = outputFormat.format(post.getTimestamp());
//        holder.date.setText(formattedDate);
//        holder.title.setText(post.getTitle());
//        holder.message.setText(post.getMessage());
////        holder.location.setText(post.getTitle());
//
//        File localImage = null;
//        try {
//            localImage = File.createTempFile("temp", "jpg");
//            Bitmap bitmap = BitmapFactory.decodeFile(localImage.getAbsolutePath());
//            // rotate image 90 degree
//            int rotation = getImageRotation(localImage.getAbsolutePath());
//            Matrix matrix = new Matrix();
//            matrix.postRotate(rotation);
//            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//
//            holder.imgContent.setImageBitmap(rotatedBitmap);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return postList.size();
//    }

    Context context;

    List<Post> list;


    public PostListAdapter(Context context, List<Post> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_layout_match,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Post post = list.get(position);
        holder.username.setText(post.getUsername());
        holder.title.setText(post.getTitle());
        holder.message.setText(post.getMessage());
//        holder.date.setDate(post.getTimestamp());
//        holder.imageView.setText(post.getAge());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView username, title, message,date;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.text_name);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.text_date);
            imageView = itemView.findViewById(R.id.img_content);

        }
    }


}