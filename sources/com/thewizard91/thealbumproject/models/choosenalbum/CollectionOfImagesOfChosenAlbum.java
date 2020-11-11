package com.thewizard91.thealbumproject.models.choosenalbum;

public class CollectionOfImagesOfChosenAlbum {
    private String[] collectAllImagesInThisAlbum;
    private String numberOfImages;

    public CollectionOfImagesOfChosenAlbum() {
    }

    public CollectionOfImagesOfChosenAlbum(String numberOfImages2, String[] collectAllImagesInThisAlbum2) {
        this.numberOfImages = numberOfImages2;
        this.collectAllImagesInThisAlbum = collectAllImagesInThisAlbum2;
    }

    public String[] getCollectAllImagesInThisAlbum() {
        return this.collectAllImagesInThisAlbum;
    }

    public void setCollectAllImagesInThisAlbum(String[] collectAllImagesInThisAlbum2) {
        this.collectAllImagesInThisAlbum = collectAllImagesInThisAlbum2;
    }

    public String getNumberOfImages() {
        return this.numberOfImages;
    }

    public void setNumberOfImages(String numberOfImages2) {
        this.numberOfImages = numberOfImages2;
    }
}
