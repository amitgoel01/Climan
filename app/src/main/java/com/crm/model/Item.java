package com.crm.model;

public class Item {
    private int imageId;
    private String header;
    private String content;

    public Item(int imageId, String header, String content) {
        this.imageId = imageId;
        this.header = header;
        this.content = content;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
