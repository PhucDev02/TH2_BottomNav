package com.example.th2_bottomnav;

public class SongModel {
    private int id;
    private String name;
    private String singerName;
    private int album;
    private int genre;
    private boolean isFavorite;

    public SongModel() {
    }

    public SongModel(String name, String singerName, int album, int genre, boolean isFavorite) {
        this.name = name;
        this.singerName = singerName;
        this.album = album;
        this.genre = genre;
        this.isFavorite = isFavorite;
    }

    public SongModel(int id, String name, String singerName, int album, int genre, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.singerName = singerName;
        this.album = album;
        this.genre = genre;
        this.isFavorite = isFavorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public int getAlbum() {
        return album;
    }

    public void setAlbum(int album) {
        this.album = album;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
