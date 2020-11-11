package com.firebase.client.collection;

import com.firebase.client.collection.LLRBNode;

public class LLRBRedValueNode<K, V> extends LLRBValueNode<K, V> {
    LLRBRedValueNode(K key, V value) {
        super(key, value, LLRBEmptyNode.getInstance(), LLRBEmptyNode.getInstance());
    }

    LLRBRedValueNode(K key, V value, LLRBNode<K, V> left, LLRBNode<K, V> right) {
        super(key, value, left, right);
    }

    /* access modifiers changed from: protected */
    public LLRBNode.Color getColor() {
        return LLRBNode.Color.RED;
    }

    public boolean isRed() {
        return true;
    }

    /* access modifiers changed from: protected */
    public LLRBValueNode<K, V> copy(K key, V value, LLRBNode<K, V> left, LLRBNode<K, V> right) {
        return new LLRBRedValueNode(key == null ? getKey() : key, value == null ? getValue() : value, left == null ? getLeft() : left, right == null ? getRight() : right);
    }
}
