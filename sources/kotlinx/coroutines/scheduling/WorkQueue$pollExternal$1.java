package kotlinx.coroutines.scheduling;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, mo33671d2 = {"<anonymous>", "", "it", "Lkotlinx/coroutines/scheduling/Task;", "invoke"}, mo33672k = 3, mo33673mv = {1, 1, 15})
/* compiled from: WorkQueue.kt */
public final class WorkQueue$pollExternal$1 extends Lambda implements Function1<Task, Boolean> {
    public static final WorkQueue$pollExternal$1 INSTANCE = new WorkQueue$pollExternal$1();

    public WorkQueue$pollExternal$1() {
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return Boolean.valueOf(invoke((Task) obj));
    }

    public final boolean invoke(Task it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        return true;
    }
}
