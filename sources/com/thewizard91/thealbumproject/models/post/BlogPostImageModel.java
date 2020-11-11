package com.thewizard91.thealbumproject.models.post;

import java.util.Date;

public class BlogPostImageModel extends BlogPostImageModelId {
    private String comments;
    private String deletePostThumbnailUri;
    private String description;
    private String imageURI;
    private String likes;
    private String location;
    private String thumbnailURI;
    private Date timestamp;
    private String userId;
    private String userImageURI;
    private String username;

    public String getUserImageURI() {
        return this.userImageURI;
    }

    public void setUserImageURI(String userImageURI2) {
        this.userImageURI = userImageURI2;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username2) {
        this.username = username2;
    }

    public String getImageURI() {
        return this.imageURI;
    }

    public void setImageURI(String imageURI2) {
        this.imageURI = imageURI2;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description2) {
        this.description = description2;
    }

    public String getThumbnailURI() {
        return this.thumbnailURI;
    }

    public void setThumbnailURI(String thumbnailURI2) {
        this.thumbnailURI = thumbnailURI2;
    }

    public String getLikes() {
        return this.likes;
    }

    public void setLikes(String likes2) {
        this.likes = likes2;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date date) {
        this.timestamp = date;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId2) {
        this.userId = userId2;
    }

    public String getDeletePostThumbnailUri() {
        return this.deletePostThumbnailUri;
    }

    public void setDeletePostThumbnailUri(String deletePostThumbnailUri2) {
        this.deletePostThumbnailUri = deletePostThumbnailUri2;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments2) {
        this.comments = comments2;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location2) {
        this.location = location2;
    }
}
