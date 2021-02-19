package com.example.doitapplication3;

public class Note {
    int _id;
    String weather;
    String address;
    String locationX;
    String locationY;
    String title;
    String contents;
    String mood;
    String picture;
    String createDateStr;
    String createWeekStr;
    String writeDateStr;



    public Note(int _id, String weather, String address, String locationX, String locationY, String contents, String mood, String picture, String createDateStr) {
        this._id = _id;
        this.weather = weather;
        this.address = address;
        this.locationX = locationX;
        this.locationY = locationY;
        this.contents = contents;
        this.mood = mood;
        this.picture = picture;
        this.createDateStr = createDateStr;

    }

    public Note(int _id, String weather, String address, String locationX, String locationY, String contents, String mood, String picture, String createDateStr, String createWeekStr) {
        this._id = _id;
        this.weather = weather;
        this.address = address;
        this.locationX = locationX;
        this.locationY = locationY;
        this.contents = contents;
        this.mood = mood;
        this.picture = picture;
        this.createDateStr = createDateStr;
        this.createWeekStr = createWeekStr;
    }

    public Note(int _id, String weather, String address, String locationX, String locationY, String title, String contents, String mood, String picture, String createDateStr, String createWeekStr) {
        this._id = _id;
        this.weather = weather;
        this.address = address;
        this.locationX = locationX;
        this.locationY = locationY;
        this.title = title;
        this.contents = contents;
        this.mood = mood;
        this.picture = picture;
        this.createDateStr = createDateStr;
        this.createWeekStr = createWeekStr;
    }

    public Note(int _id, String weather, String address, String locationX, String locationY, String title, String contents, String mood, String picture, String createDateStr, String createWeekStr, String writeDateStr) {
        this._id = _id;
        this.weather = weather;
        this.address = address;
        this.locationX = locationX;
        this.locationY = locationY;
        this.title = title;
        this.contents = contents;
        this.mood = mood;
        this.picture = picture;
        this.createDateStr = createDateStr;
        this.createWeekStr = createWeekStr;
        this.writeDateStr = writeDateStr;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocationX() {
        return locationX;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getWriteDateStr() {
        return writeDateStr;
    }

    public void setWriteDateStr(String writeDateStr) {
        this.writeDateStr = writeDateStr;
    }

    public String getCreateWeekStr() {
        return createWeekStr;
    }

    public void setCreateWeekStr(String createWeekStr) {
        this.createWeekStr = createWeekStr;
    }

    @Override
    public String toString() {
        return "Note{" +
                "_id=" + _id +
                ", weather='" + weather + '\'' +
                ", address='" + address + '\'' +
                ", locationX='" + locationX + '\'' +
                ", locationY='" + locationY + '\'' +
                ", contents='" + contents + '\'' +
                ", mood='" + mood + '\'' +
                ", picture='" + picture + '\'' +
                ", createDateStr='" + createDateStr + '\'' +
                '}';
    }
}
