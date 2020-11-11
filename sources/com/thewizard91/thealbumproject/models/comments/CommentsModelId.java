package com.thewizard91.thealbumproject.models.comments;

import com.google.firebase.database.Exclude;

public class CommentsModelId {
    @Exclude
    public String CommentsModelId;

    public <T extends CommentsModelId> T withId(String id) {
        this.CommentsModelId = id;
        return this;
    }
}
