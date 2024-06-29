//package com.example.tender.adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.tender.models.Post;
//
//import java.util.ArrayList;
//
//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
//
//    Context context;
//
//    ArrayList<Post> list;
//
//
//    public MyAdapter(Context context, ArrayList<Post> list) {
//        this.context = context;
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(context).inflate(R.layout.adapter_layout_match,parent,false);
//        return  new MyViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//
//        Post post = list.get(position);
//        holder.firstName.setText(post.getFirstName());
//        holder.lastName.setText(post.getLastName());
//        holder.age.setText(post.getAge());
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder{
//
//        TextView firstName, lastName, age;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            firstName = itemView.findViewById(R.id.tvfirstName);
//            lastName = itemView.findViewById(R.id.tvlastName);
//            age = itemView.findViewById(R.id.tvage);
//
//        }
//    }
//
//}