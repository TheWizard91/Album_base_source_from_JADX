package kotlinx.coroutines.channels;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002J\u0011\u0010\u0003\u001a\u00020\u0004H¦Bø\u0001\u0000¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0006\u001a\u00028\u0000H¦\u0002¢\u0006\u0002\u0010\u0007J\u0013\u0010\b\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\t"}, mo33671d2 = {"Lkotlinx/coroutines/channels/ChannelIterator;", "E", "", "hasNext", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "next", "()Ljava/lang/Object;", "next0", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: Channel.kt */
public interface ChannelIterator<E> {
    Object hasNext(Continuation<? super Boolean> continuation);

    E next();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.3.0, binary compatibility with versions <= 1.2.x")
    /* synthetic */ Object next(Continuation<? super E> continuation);

    @Metadata(mo33669bv = {1, 0, 3}, mo33672k = 3, mo33673mv = {1, 1, 15})
    /* compiled from: Channel.kt */
    public static final class DefaultImpls {
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:12:0x0038  */
        /* JADX WARNING: Removed duplicated region for block: B:17:0x004e  */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x0053  */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
        @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Since 1.3.0, binary compatibility with versions <= 1.2.x")
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static /* synthetic */ <E> java.lang.Object next(kotlinx.coroutines.channels.ChannelIterator<? extends E> r5, kotlin.coroutines.Continuation<? super E> r6) {
            /*
                boolean r0 = r6 instanceof kotlinx.coroutines.channels.ChannelIterator$next0$1
                if (r0 == 0) goto L_0x0014
                r0 = r6
                kotlinx.coroutines.channels.ChannelIterator$next0$1 r0 = (kotlinx.coroutines.channels.ChannelIterator$next0$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r1 = r1 & r2
                if (r1 == 0) goto L_0x0014
                int r1 = r0.label
                int r1 = r1 - r2
                r0.label = r1
                goto L_0x0019
            L_0x0014:
                kotlinx.coroutines.channels.ChannelIterator$next0$1 r0 = new kotlinx.coroutines.channels.ChannelIterator$next0$1
                r0.<init>(r5, r6)
            L_0x0019:
                java.lang.Object r1 = r0.result
                java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r3 = r0.label
                r4 = 1
                if (r3 == 0) goto L_0x0038
                if (r3 != r4) goto L_0x0030
                java.lang.Object r2 = r0.L$0
                r5 = r2
                kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
                kotlin.ResultKt.throwOnFailure(r1)
                r3 = r1
                goto L_0x0046
            L_0x0030:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0038:
                kotlin.ResultKt.throwOnFailure(r1)
                r0.L$0 = r5
                r0.label = r4
                java.lang.Object r3 = r5.hasNext(r0)
                if (r3 != r2) goto L_0x0046
                return r2
            L_0x0046:
                java.lang.Boolean r3 = (java.lang.Boolean) r3
                boolean r2 = r3.booleanValue()
                if (r2 == 0) goto L_0x0053
                java.lang.Object r2 = r5.next()
                return r2
            L_0x0053:
                kotlinx.coroutines.channels.ClosedReceiveChannelException r2 = new kotlinx.coroutines.channels.ClosedReceiveChannelException
                java.lang.String r3 = "Channel was closed"
                r2.<init>(r3)
                java.lang.Throwable r2 = (java.lang.Throwable) r2
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelIterator.DefaultImpls.next(kotlinx.coroutines.channels.ChannelIterator, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }
}
