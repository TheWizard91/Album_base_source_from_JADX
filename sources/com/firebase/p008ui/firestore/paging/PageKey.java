package com.firebase.p008ui.firestore.paging;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

/* renamed from: com.firebase.ui.firestore.paging.PageKey */
public class PageKey {
    private final DocumentSnapshot mEndBefore;
    private final DocumentSnapshot mStartAfter;

    public PageKey(DocumentSnapshot startAfter, DocumentSnapshot endBefore) {
        this.mStartAfter = startAfter;
        this.mEndBefore = endBefore;
    }

    public Query getPageQuery(Query baseQuery, int size) {
        Query pageQuery = baseQuery;
        DocumentSnapshot documentSnapshot = this.mStartAfter;
        if (documentSnapshot != null) {
            pageQuery = pageQuery.startAfter(documentSnapshot);
        }
        DocumentSnapshot documentSnapshot2 = this.mEndBefore;
        if (documentSnapshot2 != null) {
            return pageQuery.endBefore(documentSnapshot2);
        }
        return pageQuery.limit((long) size);
    }

    public String toString() {
        DocumentSnapshot documentSnapshot = this.mStartAfter;
        String endBefore = null;
        String startAfter = documentSnapshot == null ? null : documentSnapshot.getId();
        DocumentSnapshot documentSnapshot2 = this.mEndBefore;
        if (documentSnapshot2 != null) {
            endBefore = documentSnapshot2.getId();
        }
        return "PageKey{StartAfter=" + startAfter + ", EndBefore=" + endBefore + '}';
    }
}
