package com.thewizard91.thealbumproject.models.choosenalbum;

import com.google.firebase.database.Exclude;

public class SingleImageModelId {
    @Exclude
    public String SingleImageModelId;

    public <T extends SingleImageModelId> T withId(String id) {
        this.SingleImageModelId = id;
        return this;
    }
}
