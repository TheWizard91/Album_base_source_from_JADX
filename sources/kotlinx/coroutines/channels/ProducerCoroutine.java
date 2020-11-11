package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.channels.SendChannel;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0005\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001b\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\nH\u0014J\u0015\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\rH\u0014¢\u0006\u0002\u0010\u0013R\u0014\u0010\t\u001a\u00020\n8VX\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000b¨\u0006\u0014"}, mo33671d2 = {"Lkotlinx/coroutines/channels/ProducerCoroutine;", "E", "Lkotlinx/coroutines/channels/ChannelCoroutine;", "Lkotlinx/coroutines/channels/ProducerScope;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "channel", "Lkotlinx/coroutines/channels/Channel;", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/Channel;)V", "isActive", "", "()Z", "onCancelled", "", "cause", "", "handled", "onCompleted", "value", "(Lkotlin/Unit;)V", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: Produce.kt */
public class ProducerCoroutine<E> extends ChannelCoroutine<E> implements ProducerScope<E> {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ProducerCoroutine(CoroutineContext parentContext, Channel<E> channel) {
        super(parentContext, channel, true);
        Intrinsics.checkParameterIsNotNull(parentContext, "parentContext");
        Intrinsics.checkParameterIsNotNull(channel, "channel");
    }

    public boolean isActive() {
        return super.isActive();
    }

    /* access modifiers changed from: protected */
    public void onCompleted(Unit value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        SendChannel.DefaultImpls.close$default(get_channel(), (Throwable) null, 1, (Object) null);
    }

    /* access modifiers changed from: protected */
    public void onCancelled(Throwable cause, boolean handled) {
        Intrinsics.checkParameterIsNotNull(cause, "cause");
        if (!get_channel().close(cause) && !handled) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), cause);
        }
    }
}
