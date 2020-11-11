package com.facebook.imagepipeline.datasource;

import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import java.util.List;

public abstract class BaseListBitmapDataSubscriber extends BaseDataSubscriber<List<CloseableReference<CloseableImage>>> {
    /* access modifiers changed from: protected */
    public abstract void onNewResultListImpl(List<Bitmap> list);

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public void onNewResultImpl(com.facebook.datasource.DataSource<java.util.List<com.facebook.common.references.CloseableReference<com.facebook.imagepipeline.image.CloseableImage>>> r7) {
        /*
            r6 = this;
            boolean r0 = r7.isFinished()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            java.lang.Object r0 = r7.getResult()
            java.util.List r0 = (java.util.List) r0
            r1 = 0
            if (r0 != 0) goto L_0x0014
            r6.onNewResultListImpl(r1)
            return
        L_0x0014:
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x0062 }
            int r3 = r0.size()     // Catch:{ all -> 0x0062 }
            r2.<init>(r3)     // Catch:{ all -> 0x0062 }
            java.util.Iterator r3 = r0.iterator()     // Catch:{ all -> 0x0062 }
        L_0x0021:
            boolean r4 = r3.hasNext()     // Catch:{ all -> 0x0062 }
            if (r4 == 0) goto L_0x0049
            java.lang.Object r4 = r3.next()     // Catch:{ all -> 0x0062 }
            com.facebook.common.references.CloseableReference r4 = (com.facebook.common.references.CloseableReference) r4     // Catch:{ all -> 0x0062 }
            if (r4 == 0) goto L_0x0045
            java.lang.Object r5 = r4.get()     // Catch:{ all -> 0x0062 }
            boolean r5 = r5 instanceof com.facebook.imagepipeline.image.CloseableBitmap     // Catch:{ all -> 0x0062 }
            if (r5 == 0) goto L_0x0045
            java.lang.Object r5 = r4.get()     // Catch:{ all -> 0x0062 }
            com.facebook.imagepipeline.image.CloseableBitmap r5 = (com.facebook.imagepipeline.image.CloseableBitmap) r5     // Catch:{ all -> 0x0062 }
            android.graphics.Bitmap r5 = r5.getUnderlyingBitmap()     // Catch:{ all -> 0x0062 }
            r2.add(r5)     // Catch:{ all -> 0x0062 }
            goto L_0x0048
        L_0x0045:
            r2.add(r1)     // Catch:{ all -> 0x0062 }
        L_0x0048:
            goto L_0x0021
        L_0x0049:
            r6.onNewResultListImpl(r2)     // Catch:{ all -> 0x0062 }
            java.util.Iterator r1 = r0.iterator()
        L_0x0050:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0060
            java.lang.Object r2 = r1.next()
            com.facebook.common.references.CloseableReference r2 = (com.facebook.common.references.CloseableReference) r2
            com.facebook.common.references.CloseableReference.closeSafely((com.facebook.common.references.CloseableReference<?>) r2)
            goto L_0x0050
        L_0x0060:
            return
        L_0x0062:
            r1 = move-exception
            java.util.Iterator r2 = r0.iterator()
        L_0x0067:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0077
            java.lang.Object r3 = r2.next()
            com.facebook.common.references.CloseableReference r3 = (com.facebook.common.references.CloseableReference) r3
            com.facebook.common.references.CloseableReference.closeSafely((com.facebook.common.references.CloseableReference<?>) r3)
            goto L_0x0067
        L_0x0077:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.datasource.BaseListBitmapDataSubscriber.onNewResultImpl(com.facebook.datasource.DataSource):void");
    }
}
