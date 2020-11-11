package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.Job;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005H\n¢\u0006\u0002\b\u0006"}, mo33671d2 = {"<anonymous>", "", "T", "count", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke"}, mo33672k = 3, mo33673mv = {1, 1, 15})
/* compiled from: SafeCollector.kt */
final class SafeCollector$checkContext$result$1 extends Lambda implements Function2<Integer, CoroutineContext.Element, Integer> {
    final /* synthetic */ SafeCollector this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SafeCollector$checkContext$result$1(SafeCollector safeCollector) {
        super(2);
        this.this$0 = safeCollector;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return Integer.valueOf(invoke(((Number) obj).intValue(), (CoroutineContext.Element) obj2));
    }

    public final int invoke(int count, CoroutineContext.Element element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        CoroutineContext.Key key = element.getKey();
        CoroutineContext.Element collectElement = this.this$0.collectContext.get(key);
        if (key == Job.Key) {
            Job collectJob = (Job) collectElement;
            Job emissionParentJob = this.this$0.transitiveCoroutineParent((Job) element, collectJob);
            if (emissionParentJob == collectJob) {
                return collectJob == null ? count : count + 1;
            }
            throw new IllegalStateException(("Flow invariant is violated:\n\t\tEmission from another coroutine is detected.\n" + "\t\tChild of " + emissionParentJob + ", expected child of " + collectJob + ".\n" + "\t\tFlowCollector is not thread-safe and concurrent emissions are prohibited.\n" + "\t\tTo mitigate this restriction please use 'channelFlow' builder instead of 'flow'").toString());
        } else if (element != collectElement) {
            return Integer.MIN_VALUE;
        } else {
            return count + 1;
        }
    }
}
