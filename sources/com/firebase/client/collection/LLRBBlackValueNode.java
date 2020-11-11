package com.firebase.client.collection;

import com.firebase.client.collection.LLRBNode;

public class LLRBBlackValueNode<K, V> extends LLRBValueNode<K, V> {
    LLRBBlackValueNode(K key, V value, LLRBNode<K, V> left, LLRBNode<K, V> right) {
        super(key, value, left, right);
    }

    /* access modifiers changed from: protected */
    public LLRBNode.Color getColor() {
        return LLRBNode.Color.BLACK;
    }

    public boolean isRed() {
        return false;
    }

    /* access modifiers changed from: protected */
    public LLRBValueNode<K, V> copy(K key, V value, LLRBNode<K, V> left, LLRBNode<K, V> right) {
        return new LLRBBlackValueNode(key == null ? getKey() : key, value == null ? getValue() : value, left == null ? getLeft() : left, right == null ? getRight() : right);
    }
}
