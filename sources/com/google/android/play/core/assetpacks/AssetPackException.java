package com.google.android.play.core.assetpacks;

import com.google.android.play.core.assetpacks.model.C2972a;
import com.google.android.play.core.tasks.C3170j;

public class AssetPackException extends C3170j {

    /* renamed from: a */
    private final int f874a;

    AssetPackException(int i) {
        super(String.format("Asset Pack Download Error(%d): %s", new Object[]{Integer.valueOf(i), C2972a.m585a(i)}));
        if (i != 0) {
            this.f874a = i;
            return;
        }
        throw new IllegalArgumentException("errorCode should not be 0.");
    }

    public int getErrorCode() {
        return this.f874a;
    }
}
