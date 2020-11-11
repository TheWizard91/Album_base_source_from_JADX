package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000N\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\"\u0010\u0004\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u0000\u001a;\u0010\n\u001a\u00020\u000b*\u0006\u0012\u0002\b\u00030\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u000f\u001a\u00020\u000b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0011H\b\u001a.\u0010\u0012\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u00072\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00060\u00142\u0006\u0010\u0015\u001a\u00020\tH\u0000\u001a%\u0010\u0016\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u00142\u0006\u0010\u0017\u001a\u0002H\u0006H\u0000¢\u0006\u0002\u0010\u0018\u001a \u0010\u0019\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u00142\u0006\u0010\u001a\u001a\u00020\u001bH\u0000\u001a%\u0010\u001c\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u00142\u0006\u0010\u0017\u001a\u0002H\u0006H\u0000¢\u0006\u0002\u0010\u0018\u001a \u0010\u001d\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u00142\u0006\u0010\u001a\u001a\u00020\u001bH\u0000\u001a\u0010\u0010\u001e\u001a\u00020\u0005*\u0006\u0012\u0002\b\u00030\u0007H\u0002\u001a\u0019\u0010\u001f\u001a\u00020\u0005*\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u001a\u001a\u00020\u001bH\b\u001a'\u0010 \u001a\u00020\u0005*\u0006\u0012\u0002\b\u00030\u00072\u0006\u0010!\u001a\u00020\"2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0011H\b\u001a\u0012\u0010#\u001a\u00020\u000b*\b\u0012\u0004\u0012\u00020\u00050\fH\u0000\"\u0016\u0010\u0000\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0002\u0010\u0003¨\u0006$"}, mo33671d2 = {"UNDEFINED", "Lkotlinx/coroutines/internal/Symbol;", "UNDEFINED$annotations", "()V", "dispatch", "", "T", "Lkotlinx/coroutines/DispatchedTask;", "mode", "", "executeUnconfined", "", "Lkotlinx/coroutines/DispatchedContinuation;", "contState", "", "doYield", "block", "Lkotlin/Function0;", "resume", "delegate", "Lkotlin/coroutines/Continuation;", "useMode", "resumeCancellable", "value", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;)V", "resumeCancellableWithException", "exception", "", "resumeDirect", "resumeDirectWithException", "resumeUnconfined", "resumeWithStackTrace", "runUnconfinedEventLoop", "eventLoop", "Lkotlinx/coroutines/EventLoop;", "yieldUndispatched", "kotlinx-coroutines-core"}, mo33672k = 2, mo33673mv = {1, 1, 15})
/* compiled from: Dispatched.kt */
public final class DispatchedKt {
    /* access modifiers changed from: private */
    public static final Symbol UNDEFINED = new Symbol("UNDEFINED");

    private static /* synthetic */ void UNDEFINED$annotations() {
    }

    static /* synthetic */ boolean executeUnconfined$default(DispatchedContinuation $this$executeUnconfined, Object contState, int mode, boolean doYield, Function0 block, int i, Object obj) {
        if ((i & 4) != 0) {
            doYield = false;
        }
        EventLoop eventLoop = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (doYield && eventLoop.isUnconfinedQueueEmpty()) {
            return false;
        }
        if (eventLoop.isUnconfinedLoopActive()) {
            $this$executeUnconfined._state = contState;
            $this$executeUnconfined.resumeMode = mode;
            eventLoop.dispatchUnconfined($this$executeUnconfined);
            return true;
        }
        DispatchedTask $this$runUnconfinedEventLoop$iv = $this$executeUnconfined;
        eventLoop.incrementUseCount(true);
        try {
            block.invoke();
            do {
            } while (eventLoop.processUnconfinedEvent());
            InlineMarker.finallyStart(1);
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            eventLoop.decrementUseCount(true);
            InlineMarker.finallyEnd(1);
            throw th;
        }
        eventLoop.decrementUseCount(true);
        InlineMarker.finallyEnd(1);
        return false;
    }

    private static final boolean executeUnconfined(DispatchedContinuation<?> $this$executeUnconfined, Object contState, int mode, boolean doYield, Function0<Unit> block) {
        EventLoop eventLoop = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (doYield && eventLoop.isUnconfinedQueueEmpty()) {
            return false;
        }
        if (eventLoop.isUnconfinedLoopActive()) {
            $this$executeUnconfined._state = contState;
            $this$executeUnconfined.resumeMode = mode;
            eventLoop.dispatchUnconfined($this$executeUnconfined);
            return true;
        }
        DispatchedTask $this$runUnconfinedEventLoop$iv = $this$executeUnconfined;
        eventLoop.incrementUseCount(true);
        try {
            block.invoke();
            do {
            } while (eventLoop.processUnconfinedEvent());
            InlineMarker.finallyStart(1);
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            eventLoop.decrementUseCount(true);
            InlineMarker.finallyEnd(1);
            throw th;
        }
        eventLoop.decrementUseCount(true);
        InlineMarker.finallyEnd(1);
        return false;
    }

    private static final void resumeUnconfined(DispatchedTask<?> $this$resumeUnconfined) {
        EventLoop eventLoop = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (eventLoop.isUnconfinedLoopActive()) {
            eventLoop.dispatchUnconfined($this$resumeUnconfined);
            return;
        }
        DispatchedTask $this$runUnconfinedEventLoop$iv = $this$resumeUnconfined;
        eventLoop.incrementUseCount(true);
        try {
            resume($this$resumeUnconfined, $this$resumeUnconfined.getDelegate$kotlinx_coroutines_core(), 3);
            do {
            } while (eventLoop.processUnconfinedEvent());
        } catch (Throwable th) {
            eventLoop.decrementUseCount(true);
            throw th;
        }
        eventLoop.decrementUseCount(true);
    }

    /* access modifiers changed from: private */
    public static final void runUnconfinedEventLoop(DispatchedTask<?> $this$runUnconfinedEventLoop, EventLoop eventLoop, Function0<Unit> block) {
        eventLoop.incrementUseCount(true);
        try {
            block.invoke();
            do {
            } while (eventLoop.processUnconfinedEvent());
            InlineMarker.finallyStart(1);
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            eventLoop.decrementUseCount(true);
            InlineMarker.finallyEnd(1);
            throw th;
        }
        eventLoop.decrementUseCount(true);
        InlineMarker.finallyEnd(1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00cd A[Catch:{ all -> 0x00c0, all -> 0x00da }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> void resumeCancellable(kotlin.coroutines.Continuation<? super T> r21, T r22) {
        /*
            r1 = r21
            r2 = r22
            java.lang.String r0 = "$this$resumeCancellable"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r1, r0)
            boolean r0 = r1 instanceof kotlinx.coroutines.DispatchedContinuation
            if (r0 == 0) goto L_0x00f4
            r3 = r1
            kotlinx.coroutines.DispatchedContinuation r3 = (kotlinx.coroutines.DispatchedContinuation) r3
            r4 = 0
            kotlinx.coroutines.CoroutineDispatcher r0 = r3.dispatcher
            kotlin.coroutines.CoroutineContext r5 = r3.getContext()
            boolean r0 = r0.isDispatchNeeded(r5)
            r5 = 1
            if (r0 == 0) goto L_0x0031
            r3._state = r2
            r3.resumeMode = r5
            kotlinx.coroutines.CoroutineDispatcher r0 = r3.dispatcher
            kotlin.coroutines.CoroutineContext r5 = r3.getContext()
            r6 = r3
            java.lang.Runnable r6 = (java.lang.Runnable) r6
            r0.dispatch(r5, r6)
            goto L_0x00eb
        L_0x0031:
            r6 = 1
            r7 = r3
            r8 = 0
            r9 = 0
            kotlinx.coroutines.ThreadLocalEventLoop r0 = kotlinx.coroutines.ThreadLocalEventLoop.INSTANCE
            kotlinx.coroutines.EventLoop r10 = r0.getEventLoop$kotlinx_coroutines_core()
            boolean r0 = r10.isUnconfinedLoopActive()
            if (r0 == 0) goto L_0x0050
            r7._state = r2
            r7.resumeMode = r6
            r0 = r7
            kotlinx.coroutines.DispatchedTask r0 = (kotlinx.coroutines.DispatchedTask) r0
            r10.dispatchUnconfined(r0)
            r18 = r3
            goto L_0x00ea
        L_0x0050:
            r11 = r7
            kotlinx.coroutines.DispatchedTask r11 = (kotlinx.coroutines.DispatchedTask) r11
            r12 = 0
            r10.incrementUseCount(r5)
            r13 = 0
            r0 = r3
            r14 = 0
            kotlin.coroutines.CoroutineContext r15 = r0.getContext()     // Catch:{ all -> 0x00dc }
            kotlinx.coroutines.Job$Key r16 = kotlinx.coroutines.Job.Key     // Catch:{ all -> 0x00dc }
            r5 = r16
            kotlin.coroutines.CoroutineContext$Key r5 = (kotlin.coroutines.CoroutineContext.Key) r5     // Catch:{ all -> 0x00dc }
            kotlin.coroutines.CoroutineContext$Element r5 = r15.get(r5)     // Catch:{ all -> 0x00dc }
            kotlinx.coroutines.Job r5 = (kotlinx.coroutines.Job) r5     // Catch:{ all -> 0x00dc }
            if (r5 == 0) goto L_0x008c
            boolean r15 = r5.isActive()     // Catch:{ all -> 0x0088 }
            if (r15 != 0) goto L_0x008c
            java.util.concurrent.CancellationException r15 = r5.getCancellationException()     // Catch:{ all -> 0x0088 }
            java.lang.Throwable r15 = (java.lang.Throwable) r15     // Catch:{ all -> 0x0088 }
            kotlin.Result$Companion r16 = kotlin.Result.Companion     // Catch:{ all -> 0x0088 }
            java.lang.Object r15 = kotlin.ResultKt.createFailure(r15)     // Catch:{ all -> 0x0088 }
            java.lang.Object r15 = kotlin.Result.m1289constructorimpl(r15)     // Catch:{ all -> 0x0088 }
            r0.resumeWith(r15)     // Catch:{ all -> 0x0088 }
            r15 = 1
            goto L_0x008d
        L_0x0088:
            r0 = move-exception
            r18 = r3
            goto L_0x00df
        L_0x008c:
            r15 = 0
        L_0x008d:
            if (r15 != 0) goto L_0x00cd
            r5 = r22
            r14 = r3
            r15 = 0
            kotlin.coroutines.CoroutineContext r0 = r14.getContext()     // Catch:{ all -> 0x00dc }
            java.lang.Object r2 = r14.countOrElement     // Catch:{ all -> 0x00dc }
            r16 = r0
            r17 = 0
            r18 = r3
            r3 = r16
            java.lang.Object r0 = kotlinx.coroutines.internal.ThreadContextKt.updateThreadContext(r3, r2)     // Catch:{ all -> 0x00da }
            r16 = r0
            r0 = 0
            r19 = r0
            kotlin.coroutines.Continuation<T> r0 = r14.continuation     // Catch:{ all -> 0x00c4 }
            kotlin.Result$Companion r20 = kotlin.Result.Companion     // Catch:{ all -> 0x00c4 }
            r20 = r2
            java.lang.Object r2 = kotlin.Result.m1289constructorimpl(r5)     // Catch:{ all -> 0x00c0 }
            r0.resumeWith(r2)     // Catch:{ all -> 0x00c0 }
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00c0 }
            r2 = r16
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r3, r2)     // Catch:{ all -> 0x00da }
            goto L_0x00cf
        L_0x00c0:
            r0 = move-exception
            r2 = r16
            goto L_0x00c9
        L_0x00c4:
            r0 = move-exception
            r20 = r2
            r2 = r16
        L_0x00c9:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r3, r2)     // Catch:{ all -> 0x00da }
            throw r0     // Catch:{ all -> 0x00da }
        L_0x00cd:
            r18 = r3
        L_0x00cf:
        L_0x00d1:
            boolean r0 = r10.processUnconfinedEvent()     // Catch:{ all -> 0x00da }
            if (r0 != 0) goto L_0x00d9
            goto L_0x00e3
        L_0x00d9:
            goto L_0x00d1
        L_0x00da:
            r0 = move-exception
            goto L_0x00df
        L_0x00dc:
            r0 = move-exception
            r18 = r3
        L_0x00df:
            r2 = 0
            r11.handleFatalException$kotlinx_coroutines_core(r0, r2)     // Catch:{ all -> 0x00ed }
        L_0x00e3:
            r2 = 1
            r10.decrementUseCount(r2)
        L_0x00ea:
        L_0x00eb:
            goto L_0x00fd
        L_0x00ed:
            r0 = move-exception
            r2 = r0
            r3 = 1
            r10.decrementUseCount(r3)
            throw r2
        L_0x00f4:
            kotlin.Result$Companion r0 = kotlin.Result.Companion
            java.lang.Object r0 = kotlin.Result.m1289constructorimpl(r22)
            r1.resumeWith(r0)
        L_0x00fd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.DispatchedKt.resumeCancellable(kotlin.coroutines.Continuation, java.lang.Object):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00a4  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00f4 A[Catch:{ all -> 0x00e7, all -> 0x0101 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> void resumeCancellableWithException(kotlin.coroutines.Continuation<? super T> r27, java.lang.Throwable r28) {
        /*
            r1 = r27
            r2 = r28
            java.lang.String r0 = "$this$resumeCancellableWithException"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r1, r0)
            java.lang.String r0 = "exception"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r2, r0)
            boolean r0 = r1 instanceof kotlinx.coroutines.DispatchedContinuation
            if (r0 == 0) goto L_0x011b
            r3 = r1
            kotlinx.coroutines.DispatchedContinuation r3 = (kotlinx.coroutines.DispatchedContinuation) r3
            r4 = 0
            kotlin.coroutines.Continuation<T> r0 = r3.continuation
            kotlin.coroutines.CoroutineContext r5 = r0.getContext()
            kotlinx.coroutines.CompletedExceptionally r0 = new kotlinx.coroutines.CompletedExceptionally
            r6 = 0
            r7 = 2
            r8 = 0
            r0.<init>(r2, r6, r7, r8)
            r9 = r0
            kotlinx.coroutines.CoroutineDispatcher r0 = r3.dispatcher
            boolean r0 = r0.isDispatchNeeded(r5)
            r10 = 1
            if (r0 == 0) goto L_0x0042
            kotlinx.coroutines.CompletedExceptionally r0 = new kotlinx.coroutines.CompletedExceptionally
            r0.<init>(r2, r6, r7, r8)
            r3._state = r0
            r3.resumeMode = r10
            kotlinx.coroutines.CoroutineDispatcher r0 = r3.dispatcher
            r6 = r3
            java.lang.Runnable r6 = (java.lang.Runnable) r6
            r0.dispatch(r5, r6)
            goto L_0x0112
        L_0x0042:
            r7 = 1
            r11 = r3
            r12 = 0
            r13 = 0
            kotlinx.coroutines.ThreadLocalEventLoop r0 = kotlinx.coroutines.ThreadLocalEventLoop.INSTANCE
            kotlinx.coroutines.EventLoop r14 = r0.getEventLoop$kotlinx_coroutines_core()
            boolean r0 = r14.isUnconfinedLoopActive()
            if (r0 == 0) goto L_0x0061
            r11._state = r9
            r11.resumeMode = r7
            r0 = r11
            kotlinx.coroutines.DispatchedTask r0 = (kotlinx.coroutines.DispatchedTask) r0
            r14.dispatchUnconfined(r0)
            r25 = r3
            goto L_0x0111
        L_0x0061:
            r15 = r11
            kotlinx.coroutines.DispatchedTask r15 = (kotlinx.coroutines.DispatchedTask) r15
            r16 = 0
            r14.incrementUseCount(r10)
            r17 = 0
            r0 = r3
            r18 = 0
            kotlin.coroutines.CoroutineContext r6 = r0.getContext()     // Catch:{ all -> 0x0103 }
            kotlinx.coroutines.Job$Key r20 = kotlinx.coroutines.Job.Key     // Catch:{ all -> 0x0103 }
            r8 = r20
            kotlin.coroutines.CoroutineContext$Key r8 = (kotlin.coroutines.CoroutineContext.Key) r8     // Catch:{ all -> 0x0103 }
            kotlin.coroutines.CoroutineContext$Element r6 = r6.get(r8)     // Catch:{ all -> 0x0103 }
            kotlinx.coroutines.Job r6 = (kotlinx.coroutines.Job) r6     // Catch:{ all -> 0x0103 }
            if (r6 == 0) goto L_0x00a1
            boolean r8 = r6.isActive()     // Catch:{ all -> 0x009c }
            if (r8 != 0) goto L_0x00a1
            java.util.concurrent.CancellationException r8 = r6.getCancellationException()     // Catch:{ all -> 0x009c }
            java.lang.Throwable r8 = (java.lang.Throwable) r8     // Catch:{ all -> 0x009c }
            kotlin.Result$Companion r19 = kotlin.Result.Companion     // Catch:{ all -> 0x009c }
            java.lang.Object r8 = kotlin.ResultKt.createFailure(r8)     // Catch:{ all -> 0x009c }
            java.lang.Object r8 = kotlin.Result.m1289constructorimpl(r8)     // Catch:{ all -> 0x009c }
            r0.resumeWith(r8)     // Catch:{ all -> 0x009c }
            r6 = r10
            goto L_0x00a2
        L_0x009c:
            r0 = move-exception
            r25 = r3
            goto L_0x0106
        L_0x00a1:
            r6 = 0
        L_0x00a2:
            if (r6 != 0) goto L_0x00f4
            r0 = r28
            r6 = r3
            r8 = r0
            r18 = 0
            kotlin.coroutines.CoroutineContext r0 = r6.getContext()     // Catch:{ all -> 0x0103 }
            java.lang.Object r10 = r6.countOrElement     // Catch:{ all -> 0x0103 }
            r20 = r0
            r21 = 0
            r1 = r20
            java.lang.Object r0 = kotlinx.coroutines.internal.ThreadContextKt.updateThreadContext(r1, r10)     // Catch:{ all -> 0x0103 }
            r20 = r0
            r0 = 0
            r22 = r0
            kotlin.coroutines.Continuation<T> r0 = r6.continuation     // Catch:{ all -> 0x00eb }
            r23 = r8
            r24 = 0
            kotlin.Result$Companion r25 = kotlin.Result.Companion     // Catch:{ all -> 0x00eb }
            r25 = r3
            r3 = r23
            java.lang.Throwable r23 = kotlinx.coroutines.internal.StackTraceRecoveryKt.recoverStackTrace(r3, r0)     // Catch:{ all -> 0x00e7 }
            java.lang.Object r23 = kotlin.ResultKt.createFailure(r23)     // Catch:{ all -> 0x00e7 }
            r26 = r3
            java.lang.Object r3 = kotlin.Result.m1289constructorimpl(r23)     // Catch:{ all -> 0x00e7 }
            r0.resumeWith(r3)     // Catch:{ all -> 0x00e7 }
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00e7 }
            r3 = r20
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r1, r3)     // Catch:{ all -> 0x0101 }
            goto L_0x00f6
        L_0x00e7:
            r0 = move-exception
            r3 = r20
            goto L_0x00f0
        L_0x00eb:
            r0 = move-exception
            r25 = r3
            r3 = r20
        L_0x00f0:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r1, r3)     // Catch:{ all -> 0x0101 }
            throw r0     // Catch:{ all -> 0x0101 }
        L_0x00f4:
            r25 = r3
        L_0x00f6:
        L_0x00f8:
            boolean r0 = r14.processUnconfinedEvent()     // Catch:{ all -> 0x0101 }
            if (r0 != 0) goto L_0x0100
            goto L_0x010a
        L_0x0100:
            goto L_0x00f8
        L_0x0101:
            r0 = move-exception
            goto L_0x0106
        L_0x0103:
            r0 = move-exception
            r25 = r3
        L_0x0106:
            r1 = 0
            r15.handleFatalException$kotlinx_coroutines_core(r0, r1)     // Catch:{ all -> 0x0114 }
        L_0x010a:
            r1 = 1
            r14.decrementUseCount(r1)
        L_0x0111:
        L_0x0112:
            goto L_0x0130
        L_0x0114:
            r0 = move-exception
            r1 = r0
            r3 = 1
            r14.decrementUseCount(r3)
            throw r1
        L_0x011b:
            r0 = r27
            r1 = 0
            kotlin.Result$Companion r3 = kotlin.Result.Companion
            java.lang.Throwable r3 = kotlinx.coroutines.internal.StackTraceRecoveryKt.recoverStackTrace(r2, r0)
            java.lang.Object r3 = kotlin.ResultKt.createFailure(r3)
            java.lang.Object r3 = kotlin.Result.m1289constructorimpl(r3)
            r0.resumeWith(r3)
        L_0x0130:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.DispatchedKt.resumeCancellableWithException(kotlin.coroutines.Continuation, java.lang.Throwable):void");
    }

    public static final <T> void resumeDirect(Continuation<? super T> $this$resumeDirect, T value) {
        Intrinsics.checkParameterIsNotNull($this$resumeDirect, "$this$resumeDirect");
        if ($this$resumeDirect instanceof DispatchedContinuation) {
            Continuation<T> continuation = ((DispatchedContinuation) $this$resumeDirect).continuation;
            Result.Companion companion = Result.Companion;
            continuation.resumeWith(Result.m1289constructorimpl(value));
            return;
        }
        Result.Companion companion2 = Result.Companion;
        $this$resumeDirect.resumeWith(Result.m1289constructorimpl(value));
    }

    public static final <T> void resumeDirectWithException(Continuation<? super T> $this$resumeDirectWithException, Throwable exception) {
        Intrinsics.checkParameterIsNotNull($this$resumeDirectWithException, "$this$resumeDirectWithException");
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        if ($this$resumeDirectWithException instanceof DispatchedContinuation) {
            Continuation $this$resumeWithStackTrace$iv = ((DispatchedContinuation) $this$resumeDirectWithException).continuation;
            Result.Companion companion = Result.Companion;
            $this$resumeWithStackTrace$iv.resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(StackTraceRecoveryKt.recoverStackTrace(exception, $this$resumeWithStackTrace$iv))));
            return;
        }
        Continuation $this$resumeWithStackTrace$iv2 = $this$resumeDirectWithException;
        Result.Companion companion2 = Result.Companion;
        $this$resumeWithStackTrace$iv2.resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(StackTraceRecoveryKt.recoverStackTrace(exception, $this$resumeWithStackTrace$iv2))));
    }

    public static final boolean yieldUndispatched(DispatchedContinuation<? super Unit> $this$yieldUndispatched) {
        Intrinsics.checkParameterIsNotNull($this$yieldUndispatched, "$this$yieldUndispatched");
        Object contState$iv = Unit.INSTANCE;
        DispatchedContinuation<? super Unit> dispatchedContinuation = $this$yieldUndispatched;
        EventLoop eventLoop$iv = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (eventLoop$iv.isUnconfinedQueueEmpty()) {
            return false;
        }
        if (eventLoop$iv.isUnconfinedLoopActive()) {
            dispatchedContinuation._state = contState$iv;
            dispatchedContinuation.resumeMode = 1;
            eventLoop$iv.dispatchUnconfined(dispatchedContinuation);
            return true;
        }
        DispatchedTask $this$runUnconfinedEventLoop$iv$iv = dispatchedContinuation;
        eventLoop$iv.incrementUseCount(true);
        try {
            $this$yieldUndispatched.run();
            do {
            } while (eventLoop$iv.processUnconfinedEvent());
        } catch (Throwable th) {
            eventLoop$iv.decrementUseCount(true);
            throw th;
        }
        eventLoop$iv.decrementUseCount(true);
        return false;
    }

    public static /* synthetic */ void dispatch$default(DispatchedTask dispatchedTask, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 1;
        }
        dispatch(dispatchedTask, i);
    }

    public static final <T> void dispatch(DispatchedTask<? super T> $this$dispatch, int mode) {
        Intrinsics.checkParameterIsNotNull($this$dispatch, "$this$dispatch");
        Continuation<? super T> delegate$kotlinx_coroutines_core = $this$dispatch.getDelegate$kotlinx_coroutines_core();
        if (!ResumeModeKt.isDispatchedMode(mode) || !(delegate$kotlinx_coroutines_core instanceof DispatchedContinuation) || ResumeModeKt.isCancellableMode(mode) != ResumeModeKt.isCancellableMode($this$dispatch.resumeMode)) {
            resume($this$dispatch, delegate$kotlinx_coroutines_core, mode);
            return;
        }
        CoroutineDispatcher dispatcher = ((DispatchedContinuation) delegate$kotlinx_coroutines_core).dispatcher;
        CoroutineContext context = delegate$kotlinx_coroutines_core.getContext();
        if (dispatcher.isDispatchNeeded(context)) {
            dispatcher.dispatch(context, $this$dispatch);
        } else {
            resumeUnconfined($this$dispatch);
        }
    }

    public static final <T> void resume(DispatchedTask<? super T> $this$resume, Continuation<? super T> delegate, int useMode) {
        Intrinsics.checkParameterIsNotNull($this$resume, "$this$resume");
        Intrinsics.checkParameterIsNotNull(delegate, "delegate");
        Object state = $this$resume.takeState$kotlinx_coroutines_core();
        Throwable exception = $this$resume.getExceptionalResult$kotlinx_coroutines_core(state);
        if (exception != null) {
            ResumeModeKt.resumeWithExceptionMode(delegate, delegate instanceof DispatchedTask ? exception : StackTraceRecoveryKt.recoverStackTrace(exception, delegate), useMode);
        } else {
            ResumeModeKt.resumeMode(delegate, $this$resume.getSuccessfulResult$kotlinx_coroutines_core(state), useMode);
        }
    }

    public static final void resumeWithStackTrace(Continuation<?> $this$resumeWithStackTrace, Throwable exception) {
        Intrinsics.checkParameterIsNotNull($this$resumeWithStackTrace, "$this$resumeWithStackTrace");
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        Result.Companion companion = Result.Companion;
        $this$resumeWithStackTrace.resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(StackTraceRecoveryKt.recoverStackTrace(exception, $this$resumeWithStackTrace))));
    }
}
