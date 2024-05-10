package com.example.th2_bottomnav;

public class SongModel {
    private int id;
    private String name;
    private String dateStart;
    private int noiKhoiHanh;
    private boolean smoke,breakfast,coffee;
    private boolean kyGui;

    public SongModel(String name, String dateStart, int noiKhoiHanh, boolean smoke, boolean breakfast, boolean coffee, boolean kyGui) {
        this.name = name;
        this.dateStart = dateStart;
        this.noiKhoiHanh = noiKhoiHanh;
        this.smoke = smoke;
        this.breakfast = breakfast;
        this.coffee = coffee;
        this.kyGui = kyGui;
    }

    public SongModel() {
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

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public int getNoiKhoiHanh() {
        return noiKhoiHanh;
    }

    public void setNoiKhoiHanh(int noiKhoiHanh) {
        this.noiKhoiHanh = noiKhoiHanh;
    }

    public boolean isSmoke() {
        return smoke;
    }

    public void setSmoke(boolean smoke) {
        this.smoke = smoke;
    }

    public boolean isBreakfast() {
        return breakfast;
    }

    public void setBreakfast(boolean breakfast) {
        this.breakfast = breakfast;
    }

    public boolean isCoffee() {
        return coffee;
    }

    public void setCoffee(boolean coffee) {
        this.coffee = coffee;
    }

    public boolean isKyGui() {
        return kyGui;
    }

    public void setKyGui(boolean kyGui) {
        this.kyGui = kyGui;
    }
}
