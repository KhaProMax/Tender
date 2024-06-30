package com.example.tender.fragments;

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
import java.util.List;

//public class FeedFragment extends Fragment {
//
//    View rootLayout;
//    private static final String TAG = MainActivity.class.getSimpleName();
//    private List<Post> postList;
//    private PostListAdapter mAdapter;
//    private String[] matchDates = {"3/4/2020", "3/4/2020", "3/4/2020", "3/4/2020", "3/4/2020"};
//    private int[] matchPictures = {R.drawable.cat, R.drawable.cat, R.drawable.cat, R.drawable.cat , R.drawable.cat};
//    private String[] matchNames = {"Jessica1", "Jessica2", "Jessica3", "Jessica4", "Jessica5"};
//    private String[] matchLocations = {"Da Nang: 3 km", "Da Nang: 18 km", "Hoi An: kilometre", "HCM: 4 km", "HCM: 6 km"};
//
//
//    public FeedFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        rootLayout = inflater.inflate(R.layout.fragment_feed, container, false);
//
//
//        RecyclerView recyclerView = rootLayout.findViewById(R.id.recycler_view_matchs);
//        postList = new ArrayList<>();
//        mAdapter = new PostListAdapter(getContext(), postList);
//
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
//        recyclerView.setAdapter(mAdapter);
//
//        prepareMatchList();
//
//        return rootLayout;
//    }
//
//
//    private void prepareMatchList(){
//
//        Random rand = new Random();
//        int id = rand.nextInt(100);
//        int i;
//        for(i=0; i<5; i++) {
//            Post post = new Match(id, matchNames[i], matchPictures[i], matchLocations[i], matchDates[i]);
//            postList.add(post);
//        }
//    }
//
//
//}

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view_matchs);
        firestore = FirebaseFirestore.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        postListAdapter = new PostListAdapter(getContext(), list);
        recyclerView.setAdapter(postListAdapter);

        firestore.collection("posts")
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
                            Post post = document.toObject(Post.class);
                            list.add(post);
                        }
                        postListAdapter.notifyDataSetChanged();
                    }
                });

        return rootView;
    }
}

