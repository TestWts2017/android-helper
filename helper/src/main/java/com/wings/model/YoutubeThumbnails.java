package com.wings.model;

/**
 * Purpose: Model class for youtube thumbnails
 *
 * @author NikunjD
 * Created on June 18, 2019
 * Modified on June 19, 2019
 */
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
