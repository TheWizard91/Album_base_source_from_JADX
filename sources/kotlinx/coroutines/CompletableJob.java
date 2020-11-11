package kotlinx.coroutines;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Job;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u0007"}, mo33671d2 = {"Lkotlinx/coroutines/CompletableJob;", "Lkotlinx/coroutines/Job;", "complete", "", "completeExceptionally", "exception", "", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: CompletableJob.kt */
public interface CompletableJob extends Job {

    @Metadata(mo33669bv = {1, 0, 3}, mo33672k = 3, mo33673mv = {1, 1, 15})
    /* compiled from: CompletableJob.kt */
    public static final class DefaultImpls {
        public static <R> R fold(CompletableJob completableJob, R r, Function2<? super R, ? super CoroutineContext.Element, ? extends R> function2) {
            Intrinsics.checkParameterIsNotNull(function2, "operation");
            return Job.DefaultImpls.fold(completableJob, r, function2);
        }

        public static <E extends CoroutineContext.Element> E get(CompletableJob completableJob, CoroutineContext.Key<E> key) {
            Intrinsics.checkParameterIsNotNull(key, "key");
            return Job.DefaultImpls.get(completableJob, key);
        }

        public static CoroutineContext minusKey(CompletableJob completableJob, CoroutineContext.Key<?> key) {
            Intrinsics.checkParameterIsNotNull(key, "key");
            return Job.DefaultImpls.minusKey(completableJob, key);
        }

        public static CoroutineContext plus(CompletableJob completableJob, CoroutineContext coroutineContext) {
            Intrinsics.checkParameterIsNotNull(coroutineContext, "context");
            return Job.DefaultImpls.plus((Job) completableJob, coroutineContext);
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
        public static Job plus(CompletableJob completableJob, Job job) {
            Intrinsics.checkParameterIsNotNull(job, "other");
            return Job.DefaultImpls.plus((Job) completableJob, job);
        }
    }

    boolean complete();

    boolean completeExceptionally(Throwable th);
}
