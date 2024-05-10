package com.example.th2_bottomnav;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    public static ListFragment instance;
    private RecyclerView recyclerView;
    private SongAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        instance=this;
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SongAdapter(song -> openSongDetailFragment(song));
        recyclerView.setAdapter(adapter);
        databaseHelper = new DatabaseHelper(getContext());
        loadSongs();
        super.onViewCreated(view, savedInstanceState);
    }

    private void openSongDetailFragment(SongModel song) {
        SongDetailFragment songDetailFragment = new SongDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("songId", song.getId()); // Truyền ID của bài hát được chọn
        songDetailFragment.setArguments(bundle);

        // Hiển thị Fragment như một dialog
        songDetailFragment.show(getChildFragmentManager(), "SongDetailDialog");
        songDetailFragment.AssignList(this);
    }
    public void loadSongs() {
        adapter.update(databaseHelper.getAllSongs());
        try{
        }
        catch ( Exception e){
            e.printStackTrace();
        }
    }
}
