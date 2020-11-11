package com.thewizard91.thealbumproject.models.albums;

public class AlbumCollectionModel {
    private String[] albumCollection;
    private String numberOfAlbums;

    public String getNumberOfAlbums() {
        return this.numberOfAlbums;
    }

    public void setNumberOfAlbums(String numberOfAlbums2) {
        this.numberOfAlbums = numberOfAlbums2;
    }

    public String[] getAlbumCollection() {
        return this.albumCollection;
    }

    public void setAlbumCollection(String[] albumCollection2) {
        this.albumCollection = albumCollection2;
    }
}
