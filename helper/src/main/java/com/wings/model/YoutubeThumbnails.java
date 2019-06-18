package com.wings.model;

public class YoutubeThumbnails {
    private String thumbnailUrl;
    private String thumbnailHeight;
    private String thumbnailWidth;

    public YoutubeThumbnails(String thumbnailUrl, String thumbnailWidth, String thumbnailHeight) {
        this.thumbnailUrl = thumbnailUrl;
        this.thumbnailHeight = thumbnailHeight;
        this.thumbnailWidth = thumbnailWidth;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getThumbnailHeight() {
        return thumbnailHeight;
    }

    public String getThumbnailWidth() {
        return thumbnailWidth;
    }
}
