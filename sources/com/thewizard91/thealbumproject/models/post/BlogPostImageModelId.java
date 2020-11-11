package com.thewizard91.thealbumproject.models.post;

import com.google.firebase.database.Exclude;

public class BlogPostImageModelId {
    @Exclude
    public String BlogPostImageModelId;

    public <T extends BlogPostImageModelId> T withId(String id) {
        this.BlogPostImageModelId = id;
        return this;
    }
}
