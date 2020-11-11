package com.thewizard91.thealbumproject.models.albums;

import com.google.firebase.database.Exclude;

public class AlbumId {
    @Exclude
    public String AlbumId;

    public <T extends AlbumId> T withId(String id) {
        this.AlbumId = id;
        return this;
    }
}
