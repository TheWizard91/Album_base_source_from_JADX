package com.google.firebase.database.core.utilities.tuple;

import com.google.firebase.database.core.Path;

public class PathAndId {

    /* renamed from: id */
    private long f1693id;
    private Path path;

    public PathAndId(Path path2, long id) {
        this.path = path2;
        this.f1693id = id;
    }

    public Path getPath() {
        return this.path;
    }

    public long getId() {
        return this.f1693id;
    }
}
