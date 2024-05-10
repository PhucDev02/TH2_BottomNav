package com.example.th2_bottomnav;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "song_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_VE = "songs";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_START_DATE = "startdate";
    private static final String COLUMN_START_PLACE = "place";
    private static final String COLUMN_IS_SMOKE = "is_smoke";
    private static final String COLUMN_IS_BREAKFAST = "is_breakfast";
    private static final String COLUMN_IS_COFFEE = "is_coffee";
    private static final String COLUMN_IS_FAVORITE = "is_favorite";

    private static final String CREATE_TABLE_SONGS = "CREATE TABLE " + TABLE_VE +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " TEXT," +
            COLUMN_START_DATE + " TEXT," +
            COLUMN_START_PLACE + " TEXT," +
            COLUMN_IS_SMOKE + " INTEGER," +
            COLUMN_IS_BREAKFAST + " INTEGER," +
            COLUMN_IS_COFFEE + " INTEGER," +
            COLUMN_IS_FAVORITE + " INTEGER" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SONGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VE);
        onCreate(db);
    }

    public long addSong(SongModel song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, song.getName());
        values.put(COLUMN_START_DATE, song.getDateStart());
        values.put(COLUMN_START_PLACE, song.getNoiKhoiHanh());
        values.put(COLUMN_IS_SMOKE, song.isSmoke()?1:0);
        values.put(COLUMN_IS_BREAKFAST, song.isBreakfast()?1:0);
        values.put(COLUMN_IS_COFFEE, song.isCoffee()?1:0);
        values.put(COLUMN_IS_FAVORITE, song.isKyGui() ? 1 : 0);

        long id = db.insert(TABLE_VE, null, values);
        db.close();
        return id;
    }

    public int updateSong(SongModel song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, song.getName());
        values.put(COLUMN_START_DATE, song.getDateStart());
        values.put(COLUMN_START_PLACE, song.getNoiKhoiHanh());
        values.put(COLUMN_IS_SMOKE, song.isSmoke()?1:0);
        values.put(COLUMN_IS_BREAKFAST, song.isBreakfast()?1:0);
        values.put(COLUMN_IS_COFFEE, song.isCoffee()?1:0);
        values.put(COLUMN_IS_FAVORITE, song.isKyGui() ? 1 : 0);

        return db.update(TABLE_VE, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(song.getId())});
    }

    public void deleteSong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VE, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public List<SongModel> getAllSongs() {
        List<SongModel> songList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_VE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SongModel song = new SongModel();
                song.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                song.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                song.setDateStart(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE)));
                song.setNoiKhoiHanh(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_START_PLACE)));
                song.setSmoke(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_SMOKE))==1);
                song.setBreakfast(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_BREAKFAST))==1);
                song.setCoffee(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_COFFEE))==1);
                song.setKyGui(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1);
                songList.add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songList;
    }
    public SongModel getSong(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_VE, // Tên bảng
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_START_DATE, COLUMN_START_PLACE, COLUMN_IS_SMOKE,COLUMN_IS_BREAKFAST,COLUMN_IS_COFFEE, COLUMN_IS_FAVORITE}, // Các cột cần truy vấn
                COLUMN_ID + "=?", // Điều kiện WHERE
                new String[]{String.valueOf(id)}, // Tham số WHERE
                null, // GROUP BY
                null, // HAVING
                null, // ORDER BY
                null); // LIMIT

        SongModel song = null;
        if (cursor != null && cursor.moveToFirst()) {
            song = new SongModel();
            song.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            song.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            song.setDateStart(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE)));
            song.setNoiKhoiHanh(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_START_PLACE)));
            song.setSmoke(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_SMOKE))==1);
            song.setBreakfast(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_BREAKFAST))==1);
            song.setCoffee(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_COFFEE))==1);
            song.setKyGui(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1);
            cursor.close();
        }
        db.close();

        return song;
    }

    public List<SongModel> getSongsByAlbum(int albumId) {
        List<SongModel> songList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] projection = {
                    COLUMN_ID,
                    COLUMN_NAME,
                    COLUMN_START_DATE,
                    COLUMN_START_PLACE,
                    COLUMN_IS_SMOKE,
                    COLUMN_IS_FAVORITE
            };

            String selection = COLUMN_IS_FAVORITE + " = ?";
            String[] selectionArgs = { String.valueOf(albumId) };

            cursor = db.query(TABLE_VE, projection, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    SongModel song = new SongModel();
                    song.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                    song.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                    song.setDateStart(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE)));
                    song.setNoiKhoiHanh(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_START_PLACE)));
                    song.setSmoke(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_SMOKE))==1);
                    song.setBreakfast(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_BREAKFAST))==1);
                    song.setCoffee(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_COFFEE))==1);
                    song.setKyGui(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1);
                    songList.add(song);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return songList;
    }
    public int getSongCountByGenre(int genreId) {
        SQLiteDatabase db = this.getReadableDatabase();
        int songCount = 0;
        Cursor cursor = null;

        try {
            String[] projection = { COLUMN_ID };
            String selection = COLUMN_START_PLACE + " = ?";
            String[] selectionArgs = { String.valueOf(genreId) };

            cursor = db.query(TABLE_VE, projection, selection, selectionArgs, null, null, null);
            songCount = cursor.getCount();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return songCount;
    }
}
