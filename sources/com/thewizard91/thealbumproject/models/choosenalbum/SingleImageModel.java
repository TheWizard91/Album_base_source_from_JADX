package com.thewizard91.thealbumproject.models.choosenalbum;

public class SingleImageModel extends SingleImageModelId {
    public static final int IMAGE_TYPE0 = 0;
    public static final int IMAGE_TYPE1 = 1;
    private String imageUri;
    private int type;

    public SingleImageModel() {
    }

    public SingleImageModel(String imageUri2, int type2) {
    }

    public String getImageUri() {
        return this.imageUri;
    }

    public void setImageUri(String imageUri2) {
        this.imageUri = imageUri2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }
}
