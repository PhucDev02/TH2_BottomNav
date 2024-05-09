package com.example.th2_bottomnav;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private List<SongModel> songList;

    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(SongModel song);
    }

    public SongAdapter(List<SongModel> songList,OnItemClickListener l) {
        this.songList = songList;
        listener =l;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SongModel song = songList.get(position);
        holder.textViewSongName.setText(song.getName());
        holder.textViewSingerName.setText(song.getSingerName());
        holder.textViewAlbum.setText("Album: " + GetAlbum(song.getAlbum()));
        holder.textViewGenre.setText("Thể loại: " + GetGenre(song.getGenre()));
        holder.checkBoxFavorite.setChecked(song.isFavorite());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(songList.get(position));
            }
        });
    }
    private String GetGenre(int index)
    {
        switch (index)
        {
            case 0:
                return "Country";
            case 1:
                return "Blues";
            case 2:
                return "Rock";
            case 3:
                return "Pop";
        }
        return null;
    }
    private String GetAlbum(int index)
    {
        switch (index)
        {
            case 0:
                return "Cho một tình yêu";
            case 1:
                return "Nỗi yêu bé dại";
            case 2:
                return "Cây lặng - gió ngừng";
            case 3:
                return "Có dừng được không";
            case 4:
                return "Đây là mơ";
            case 5:
                return "Ở giữa cuộc đời";
        }
        return null;
    }
    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSongName;
        TextView textViewSingerName;
        TextView textViewAlbum;
        TextView textViewGenre;
        CheckBox checkBoxFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSongName = itemView.findViewById(R.id.textViewSongName);
            textViewSingerName = itemView.findViewById(R.id.textViewSingerName);
            textViewAlbum = itemView.findViewById(R.id.textViewAlbum);
            textViewGenre = itemView.findViewById(R.id.textViewGenre);
            checkBoxFavorite = itemView.findViewById(R.id.checkBoxFavorite);
        }
    }
}
