package com.fasterxml.jackson.databind.deser.impl;

import java.io.IOException;

public class ReadableObjectId {

    /* renamed from: id */
    public final Object f89id;
    public Object item;

    public ReadableObjectId(Object obj) {
        this.f89id = obj;
    }

    public void bindItem(Object obj) throws IOException {
        if (this.item == null) {
            this.item = obj;
            return;
        }
        throw new IllegalStateException("Already had POJO for id (" + this.f89id.getClass().getName() + ") [" + this.f89id + "]");
    }
}
