package com.example.th2_bottomnav;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ListFragment extends Fragment {
    private RecyclerView recyclerView;
    private SongAdapter adapter;
    private List<SongModel> songList;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        songList = new ArrayList<>();
        adapter = new SongAdapter(songList, song -> openSongDetailFragment(song));
        recyclerView.setAdapter(adapter);

        databaseHelper = new DatabaseHelper(getContext());
        loadSongs();


        return view;
    }
    private void openSongDetailFragment(SongModel song) {
        SongDetailFragment songDetailFragment = new SongDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("songId", song.getId()); // Truyền ID của bài hát được chọn
        songDetailFragment.setArguments(bundle);

        // Hiển thị Fragment như một dialog
        songDetailFragment.show(getActivity().getSupportFragmentManager(), "SongDetailDialog");
        songDetailFragment.AssignList(this);
    }
    private void loadSongs() {
        songList.clear();
        songList.addAll(databaseHelper.getAllSongs());
        adapter.notifyDataSetChanged();
    }
    public void updateSongList() {
        loadSongs();
    }
}
