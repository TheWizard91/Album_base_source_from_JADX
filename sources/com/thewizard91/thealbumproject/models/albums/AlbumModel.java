package com.thewizard91.thealbumproject.models.albums;

public class AlbumModel extends AlbumId {
    private String description;
    private String imageUri;
    private String name;

    public AlbumModel() {
    }

    public AlbumModel(String name2, String description2, String imageUri2) {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getImageUri() {
        return this.imageUri;
    }

    public void setImageUri(String imageUri2) {
        this.imageUri = imageUri2;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description2) {
        this.description = description2;
    }
}
