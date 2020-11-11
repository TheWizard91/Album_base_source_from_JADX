package com.firebase.client.utilities.tuple;

import com.firebase.client.core.Path;

public class PathAndId {

    /* renamed from: id */
    private long f100id;
    private Path path;

    public PathAndId(Path path2, long id) {
        this.path = path2;
        this.f100id = id;
    }

    public Path getPath() {
        return this.path;
    }

    public long getId() {
        return this.f100id;
    }
}
