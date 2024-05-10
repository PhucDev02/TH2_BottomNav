package com.example.th2_bottomnav;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private List<SongModel> songList = new ArrayList<>();

    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(SongModel song);
    }

    public SongAdapter(OnItemClickListener l) {
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
        holder.textViewSingerName.setText(song.getDateStart());
        holder.textViewAlbum.setText("Nơi khởi hành: " + GetAlbum(song.getNoiKhoiHanh()));
        holder.textViewGenre.setText("Dịch vụ: " + GetGenre(song));
        holder.checkBoxFavorite.setChecked(song.isKyGui());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(songList.get(position));
            }
        });
    }
    private String GetGenre(SongModel song)
    {
        String res="";
        res = res + (song.isSmoke()?"Hút thuốc":"");
        res = res + (song.isBreakfast()?" Ăn sáng":"");
        res = res + (song.isCoffee()?" Cà phê":"");
        if(res=="") res="Không";
        return res;
    }
    private String GetAlbum(int index)
    {
        switch (index)
        {
            case 1:
                return "Hà Nội";
            case 2:
                return "Đà Nẵng";
            case 3:
                return "Tp HCM";
            case 4:
                return "Huế";
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

    public void update(List<SongModel> songList){
        this.songList.clear();
        this.songList.addAll(songList);
        notifyDataSetChanged();
    }
}
