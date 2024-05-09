package com.example.th2_bottomnav;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private Spinner spinnerAlbumSearch;
    private TextView textViewGenreCount;
    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    private SongAdapter searchAdapter;
    private List<SongModel> searchResults;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        spinnerAlbumSearch = view.findViewById(R.id.spinnerAlbumSearch);
        textViewGenreCount = view.findViewById(R.id.textViewGenreCount);
        recyclerView = view.findViewById(R.id.searchRecyclerView);
        databaseHelper = new DatabaseHelper(getContext());

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.album_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAlbumSearch.setAdapter(spinnerAdapter);

        searchResults = new ArrayList<>();
        searchAdapter = new SongAdapter(searchResults,v->openSongDetailFragment(v));

        recyclerView.setAdapter(searchAdapter);

        spinnerAlbumSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int albumId = position; // (hoặc bạn có thể sử dụng một cách khác để lấy albumId)

                updateSearchResults(albumId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không cần xử lý khi không có album nào được chọn
            }
        });

        updateGenreCount();

        return view;
    }

    private void openSongDetailFragment(SongModel song) {
        SongDetailFragment songDetailFragment = new SongDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("songId", song.getId()); // Truyền ID của bài hát được chọn
        songDetailFragment.setArguments(bundle);

        // Hiển thị Fragment như một dialog
        songDetailFragment.show(getActivity().getSupportFragmentManager(), "SongDetailDialog");
    }
    // Phương thức để cập nhật kết quả tìm kiếm dựa trên album đã chọn
    private void updateSearchResults(int albumId) {
        // Lấy danh sách bài hát từ cơ sở dữ liệu dựa trên albumId
        List<SongModel> songs = databaseHelper.getSongsByAlbum(albumId);
        Log.e(null,songs.size()+"");
        searchResults.clear();

        searchResults.addAll(songs);
        searchAdapter.update(songs);

    }

    private void updateGenreCount() {
        int[] songCounts = new int[getResources().getStringArray(R.array.genre_array).length];
        String[] genreName = getResources().getStringArray(R.array.genre_array);
        for (int i = 1; i < getResources().getStringArray(R.array.genre_array).length; i++) {
            int songCount = databaseHelper.getSongCountByGenre(i);
            songCounts[i] = songCount;
        }

        // Sắp xếp mảng số lượng bài hát theo thứ tự giảm dần
        for (int i = 1; i < songCounts.length; i++) {
            for (int j = i + 1; j < songCounts.length; j++) {
                if (songCounts[i] < songCounts[j]) {
                    int temp = songCounts[i];
                    songCounts[i] = songCounts[j];
                    songCounts[j] = temp;
                    String tmp = genreName[i];
                    genreName[i]=genreName[j];
                    genreName[j]=tmp;
                }
            }
        }

        // Hiển thị số lượng bài hát theo thể loại trong TextView theo thứ tự sắp xếp
        StringBuilder countText = new StringBuilder("Thống kê số lượng bài:\n");
        for (int i = 1; i < getResources().getStringArray(R.array.genre_array).length; i++) {
            countText.append(genreName[i]).append(": ").append(songCounts[i]).append("\n");
        }

        // Hiển thị số lượng bài hát theo thể loại trong TextView
        textViewGenreCount.setText(countText.toString());
    }
}
