package com.example.th2_bottomnav;

import android.app.DatePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SongDetailFragment extends DialogFragment {
    private EditText tenNguoiDat;
    private Button datePick;
    private Spinner spinnerStart;
    private CheckBox smoke,breakfast,coffee, kyGui;
    private Button buttonCancel, buttonSave;
    private DatabaseHelper databaseHelper;
    private ListFragment listFragment;

    private Calendar selectedDate = Calendar.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songdetail, container, false);

        tenNguoiDat = view.findViewById(R.id.nameTicket);
        datePick=view.findViewById(R.id.datePicker);
        spinnerStart = view.findViewById(R.id.spinnerAlbum);

        smoke=view.findViewById(R.id.radioSmoke);
        breakfast=view.findViewById(R.id.radioBreakfast);
        coffee=view.findViewById(R.id.radioCoffee);

        kyGui = view.findViewById(R.id.checkBoxFavorite);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonSave = view.findViewById(R.id.buttonSave);
        databaseHelper = new DatabaseHelper(getContext());

        buttonCancel.setOnClickListener(v -> dismiss());

        buttonSave.setOnClickListener(v -> saveSong());

        datePick.setOnClickListener(v -> showDatePickerDialog());
        Bundle bundle = getArguments();
        if (bundle != null) {
            int songId = bundle.getInt("songId");
            // Lấy thông tin chi tiết của bài hát từ cơ sở dữ liệu
            SongModel selectedSong = databaseHelper.getSong(songId);
            if (selectedSong != null) {
                // Hiển thị thông tin của bài hát được chọn trong các thành phần UI
                tenNguoiDat.setText(selectedSong.getName());
                spinnerStart.setSelection(selectedSong.getNoiKhoiHanh());
                kyGui.setChecked(selectedSong.isKyGui());
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

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    // Lưu ngày được chọn vào biến selectedDate
                    selectedDate.set(year, month, dayOfMonth);
                    // Cập nhật văn bản của nút datepicker với ngày được chọn
                    datePick.setText(formatDate(selectedDate));
                },
                selectedDate.get(Calendar.YEAR), // Năm hiện tại
                selectedDate.get(Calendar.MONTH), // Tháng hiện tại
                selectedDate.get(Calendar.DAY_OF_MONTH) // Ngày hiện tại
        );

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }
    private String formatDate(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(calendar.getTime());
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
        String songName = tenNguoiDat.getText().toString().trim();
        int albumPosition = spinnerStart.getSelectedItemPosition();
        boolean isFavorite = kyGui.isChecked();

        if (songName.isEmpty() ) {
            Toast.makeText(getContext(), "Chưa điền tên bài hát", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (albumPosition==0) {
            Toast.makeText(getContext(), "Chưa chọn album", Toast.LENGTH_SHORT).show();
            return null;
        }

        SongModel newSong = new SongModel();
        newSong.setId(model.getId()); // Giữ nguyên ID của bài hát
        newSong.setName(songName);
        newSong.setNoiKhoiHanh(albumPosition);
        newSong.setKyGui(isFavorite);
        return newSong;
    }

    private void deleteSong(SongModel selectedSong) {
        databaseHelper.deleteSong(selectedSong.getId());
        Toast.makeText(getContext(), "Xoá bài hát thành công", Toast.LENGTH_SHORT).show();
        if(listFragment!=null)
            listFragment.updateSongList();
        dismiss();
    }

    public void SetSong(SongModel song)
    {
        tenNguoiDat.setText(song.getName());
        spinnerStart.setSelection(song.getNoiKhoiHanh());
        kyGui.setChecked(song.isKyGui());
    }
    public void AssignList(ListFragment frm)
    {
        listFragment=frm;
    }
    private void saveSong() {
        String username = tenNguoiDat.getText().toString().trim();
        String date= datePick.getText().toString();
        int noiKhoiHanh = spinnerStart.getSelectedItemPosition();
        boolean isFavorite = kyGui.isChecked();

        if (username.isEmpty() ) {
            Toast.makeText(getContext(), "Chưa điền tên người đặt", Toast.LENGTH_SHORT).show();
            return;
        }
        if (date=="Chọn ngày") {
            Toast.makeText(getContext(), "Chưa chọn ngày", Toast.LENGTH_SHORT).show();
            return;
        }
        if (noiKhoiHanh==0) {
            Toast.makeText(getContext(), "Chưa chọn nơi khởi hành", Toast.LENGTH_SHORT).show();
            return;
        }

        SongModel song = new SongModel();
        song.setName(username);
        song.setDateStart(date);
        song.setNoiKhoiHanh(noiKhoiHanh);
        song.setBreakfast(breakfast.isChecked());
        song.setSmoke(smoke.isChecked());
        song.setBreakfast(coffee.isChecked());
        song.setKyGui(isFavorite);

        long result;
        if (song.getId() == 0) {
            result = databaseHelper.addSong(song);
        } else {
            result = databaseHelper.updateSong(song);
        }

        if (result > 0) {
            Toast.makeText(getContext(), "Lưu vé thành công", Toast.LENGTH_SHORT).show();
            listFragment.updateSongList();
            dismiss();
        } else {
            Toast.makeText(getContext(), "Lưu vé thất bại", Toast.LENGTH_SHORT).show();
        }
    }

}
