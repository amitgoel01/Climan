package com.crm.model;

public class DataModel {

//    public int position;
    private String text;
    private int icon;
    private int color;
    private int action;

    public DataModel(String text, int icon, int color, int action) {
        this.text = text;
        this.icon = icon;
        this.color = color;
        this.action = action;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}