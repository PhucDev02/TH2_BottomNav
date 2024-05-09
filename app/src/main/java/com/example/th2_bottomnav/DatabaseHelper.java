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

    private static final String TABLE_SONGS = "songs";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SINGER_NAME = "singer_name";
    private static final String COLUMN_ALBUM = "album";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_IS_FAVORITE = "is_favorite";

    private static final String CREATE_TABLE_SONGS = "CREATE TABLE " + TABLE_SONGS +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " TEXT," +
            COLUMN_SINGER_NAME + " TEXT," +
            COLUMN_ALBUM + " TEXT," +
            COLUMN_GENRE + " TEXT," +
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        onCreate(db);
    }

    public long addSong(SongModel song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, song.getName());
        values.put(COLUMN_SINGER_NAME, song.getSingerName());
        values.put(COLUMN_ALBUM, song.getAlbum());
        values.put(COLUMN_GENRE, song.getGenre());
        values.put(COLUMN_IS_FAVORITE, song.isFavorite() ? 1 : 0);

        long id = db.insert(TABLE_SONGS, null, values);
        db.close();
        return id;
    }

    public int updateSong(SongModel song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, song.getName());
        values.put(COLUMN_SINGER_NAME, song.getSingerName());
        values.put(COLUMN_ALBUM, song.getAlbum());
        values.put(COLUMN_GENRE, song.getGenre());
        values.put(COLUMN_IS_FAVORITE, song.isFavorite() ? 1 : 0);

        return db.update(TABLE_SONGS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(song.getId())});
    }

    public void deleteSong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SONGS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public List<SongModel> getAllSongs() {
        List<SongModel> songList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SONGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SongModel song = new SongModel();
                song.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                song.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                song.setSingerName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SINGER_NAME)));
                song.setAlbum(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ALBUM)));
                song.setGenre(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GENRE)));
                song.setFavorite(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1);
                songList.add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songList;
    }
    public SongModel getSong(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SONGS, // Tên bảng
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_SINGER_NAME, COLUMN_ALBUM, COLUMN_GENRE, COLUMN_IS_FAVORITE}, // Các cột cần truy vấn
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
            song.setSingerName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SINGER_NAME)));
            song.setAlbum(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ALBUM)));
            song.setGenre(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GENRE)));
            song.setFavorite(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1);
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
                    COLUMN_SINGER_NAME,
                    COLUMN_ALBUM,
                    COLUMN_GENRE,
                    COLUMN_IS_FAVORITE
            };

            String selection = COLUMN_ALBUM + " = ?";
            String[] selectionArgs = { String.valueOf(albumId) };

            cursor = db.query(TABLE_SONGS, projection, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    SongModel song = new SongModel();
                    song.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                    song.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                    song.setSingerName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SINGER_NAME)));
                    song.setAlbum(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ALBUM)));
                    song.setGenre(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GENRE)));
                    song.setFavorite(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1);
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
}
