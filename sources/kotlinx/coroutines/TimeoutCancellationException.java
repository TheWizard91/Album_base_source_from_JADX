package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u000f\b\u0010\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0019\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0000X\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, mo33671d2 = {"Lkotlinx/coroutines/TimeoutCancellationException;", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "message", "", "(Ljava/lang/String;)V", "coroutine", "Lkotlinx/coroutines/Job;", "(Ljava/lang/String;Lkotlinx/coroutines/Job;)V", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: Timeout.kt */
public final class TimeoutCancellationException extends CancellationException {
    public final Job coroutine;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public TimeoutCancellationException(String message, Job coroutine2) {
        super(message);
        Intrinsics.checkParameterIsNotNull(message, "message");
        this.coroutine = coroutine2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public TimeoutCancellationException(String message) {
        this(message, (Job) null);
        Intrinsics.checkParameterIsNotNull(message, "message");
    }
}
