package com.thewizard91.thealbumproject.models.comments;

import java.util.Date;

public class CommentsModel extends CommentsModelId {
    private String blogPostId;
    private String commentReplyImageUri;
    private String commentText;
    private String commentsCount;
    private String imageUri;
    private String likesCount;
    private String thumbsUpImageUri;
    private Date timestamp;
    private String userId;
    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username2) {
        this.username = username2;
    }

    public String getImageUri() {
        return this.imageUri;
    }

    public void setImageUri(String imageUri2) {
        this.imageUri = imageUri2;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp2) {
        this.timestamp = timestamp2;
    }

    public String getCommentText() {
        return this.commentText;
    }

    public void setCommentText(String commentText2) {
        this.commentText = commentText2;
    }

    public String getLikesCount() {
        return this.likesCount;
    }

    public void setLikesCount(String likesCount2) {
        this.likesCount = likesCount2;
    }

    public String getThumbsUpImageUri() {
        return this.thumbsUpImageUri;
    }

    public void setThumbsUpImageUri(String thumbsUpImageUri2) {
        this.thumbsUpImageUri = thumbsUpImageUri2;
    }

    public String getCommentReplyImageUri() {
        return this.commentReplyImageUri;
    }

    public void setCommentReplyImageUri(String commentReplyImageUri2) {
        this.commentReplyImageUri = commentReplyImageUri2;
    }

    public String getCommentsCount() {
        return this.commentsCount;
    }

    public void setCommentsCount(String commentsCount2) {
        this.commentsCount = commentsCount2;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId2) {
        this.userId = userId2;
    }
}
