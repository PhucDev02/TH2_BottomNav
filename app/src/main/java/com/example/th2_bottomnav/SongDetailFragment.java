package com.example.th2_bottomnav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class SongDetailFragment extends DialogFragment {
    private EditText editTextSongName, editTextArtist;
    private Spinner spinnerAlbum, spinnerGenre;
    private CheckBox checkBoxFavorite;
    private Button buttonCancel, buttonSave;
    private DatabaseHelper databaseHelper;
    private ListFragment listFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songdetail, container, false);

        editTextSongName = view.findViewById(R.id.editTextSongName);
        editTextArtist = view.findViewById(R.id.editTextArtist);
        spinnerAlbum = view.findViewById(R.id.spinnerAlbum);
        spinnerGenre = view.findViewById(R.id.spinnerGenre);
        checkBoxFavorite = view.findViewById(R.id.checkBoxFavorite);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonSave = view.findViewById(R.id.buttonSave);
        databaseHelper = new DatabaseHelper(getContext());

        buttonCancel.setOnClickListener(v -> dismiss());

        buttonSave.setOnClickListener(v -> saveSong());

        Bundle bundle = getArguments();
        if (bundle != null) {
            int songId = bundle.getInt("songId");
            // Lấy thông tin chi tiết của bài hát từ cơ sở dữ liệu
            SongModel selectedSong = databaseHelper.getSong(songId);
            if (selectedSong != null) {
                // Hiển thị thông tin của bài hát được chọn trong các thành phần UI
                editTextSongName.setText(selectedSong.getName());
                editTextArtist.setText(selectedSong.getSingerName());
                spinnerAlbum.setSelection(selectedSong.getAlbum());
                spinnerGenre.setSelection(selectedSong.getGenre());
                checkBoxFavorite.setChecked(selectedSong.isFavorite());
                buttonCancel.setOnClickListener(v->deleteSong(selectedSong));
                buttonSave.setOnClickListener(v->updateSong(selectedSong));
                buttonCancel.setText("Xoá");
                buttonSave.setText("Update");
                buttonSave.setTextSize(12);
            }
        }
        else
        {
            buttonCancel.setOnClickListener(v -> dismiss());
            buttonSave.setOnClickListener(v -> saveSong());

            buttonCancel.setText("Huỷ");
            buttonSave.setText("Thêm");
        }

        return view;
    }

    private void updateSong(SongModel selectedSong) {
        SongModel   newSong = AssignNewSong(selectedSong);
        if (newSong==null)
        {
            return;
        }
        databaseHelper.updateSong(newSong);

        Toast.makeText(getContext(), "Cập nhật bài hát thành công", Toast.LENGTH_SHORT).show();
        listFragment.updateSongList();
        dismiss();
    }

    private SongModel AssignNewSong(SongModel model) {
        String songName = editTextSongName.getText().toString().trim();
        String artist = editTextArtist.getText().toString().trim();
        int albumPosition = spinnerAlbum.getSelectedItemPosition();
        int genrePosition = spinnerGenre.getSelectedItemPosition();
        boolean isFavorite = checkBoxFavorite.isChecked();

        if (songName.isEmpty() || artist.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return null;
        }

        SongModel newSong = new SongModel();
        newSong.setId(model.getId()); // Giữ nguyên ID của bài hát
        newSong.setName(songName);
        newSong.setSingerName(artist);
        newSong.setAlbum(albumPosition);
        newSong.setGenre(genrePosition);
        newSong.setFavorite(isFavorite);
        return newSong;
    }

    private void deleteSong(SongModel selectedSong) {
        databaseHelper.deleteSong(selectedSong.getId());
        Toast.makeText(getContext(), "Xoá bài hát thành công", Toast.LENGTH_SHORT).show();
        listFragment.updateSongList();
        dismiss();
    }

    public void SetSong(SongModel song)
    {
        editTextSongName.setText(song.getName());
        editTextArtist.setText(song.getSingerName());
        spinnerAlbum.setSelection(song.getAlbum());
        spinnerGenre.setSelection(song.getGenre());
        checkBoxFavorite.setChecked(song.isFavorite());
    }
    public void AssignList(ListFragment frm)
    {
        listFragment=frm;
    }
    private void saveSong() {
        String songName = editTextSongName.getText().toString().trim();
        String artist = editTextArtist.getText().toString().trim();
        int albumPosition = spinnerAlbum.getSelectedItemPosition();
        int genrePosition = spinnerGenre.getSelectedItemPosition();
        boolean isFavorite = checkBoxFavorite.isChecked();

        if (songName.isEmpty() || artist.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        SongModel song = new SongModel();
        song.setName(songName);
        song.setSingerName(artist);
        song.setAlbum(albumPosition); // Sử dụng vị trí của album trong spinner
        song.setGenre(genrePosition); // Sử dụng vị trí của thể loại trong spinner
        song.setFavorite(isFavorite);

        long result;
        if (song.getId() == 0) {
            result = databaseHelper.addSong(song);
        } else {
            result = databaseHelper.updateSong(song);
        }

        if (result > 0) {
            Toast.makeText(getContext(), "Lưu bài hát thành công", Toast.LENGTH_SHORT).show();
            listFragment.updateSongList();
            dismiss();
        } else {
            Toast.makeText(getContext(), "Lưu bài hát thất bại", Toast.LENGTH_SHORT).show();
        }
    }

}
