package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\r\b\u0007\u0018\u0000 B*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002:\u0004CBDEB\u0011\b\u0016\u0012\u0006\u0010\u0003\u001a\u00028\u0000¢\u0006\u0004\b\u0004\u0010\u0005B\u0007¢\u0006\u0004\b\u0004\u0010\u0006J?\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\b0\u00072\u0014\u0010\t\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\b\u0018\u00010\u00072\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0002¢\u0006\u0004\b\u000b\u0010\fJ\u0019\u0010\u0010\u001a\u00020\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\rH\u0017¢\u0006\u0004\b\u0010\u0010\u0011J\u001f\u0010\u0010\u001a\u00020\u00142\u000e\u0010\u000e\u001a\n\u0018\u00010\u0012j\u0004\u0018\u0001`\u0013H\u0016¢\u0006\u0004\b\u0010\u0010\u0015J\u0019\u0010\u0016\u001a\u00020\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\rH\u0016¢\u0006\u0004\b\u0016\u0010\u0011J\u001d\u0010\u0017\u001a\u00020\u00142\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0002¢\u0006\u0004\b\u0017\u0010\u0018J)\u0010\u001c\u001a\u00020\u00142\u0018\u0010\u001b\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\r\u0012\u0004\u0012\u00020\u00140\u0019j\u0002`\u001aH\u0016¢\u0006\u0004\b\u001c\u0010\u001dJ\u0019\u0010\u001e\u001a\u00020\u00142\b\u0010\u000e\u001a\u0004\u0018\u00010\rH\u0002¢\u0006\u0004\b\u001e\u0010\u001fJ\u0017\u0010!\u001a\u00020\u000f2\u0006\u0010 \u001a\u00028\u0000H\u0016¢\u0006\u0004\b!\u0010\"J\u0019\u0010$\u001a\u0004\u0018\u00010#2\u0006\u0010 \u001a\u00028\u0000H\u0002¢\u0006\u0004\b$\u0010%J\u0015\u0010'\u001a\b\u0012\u0004\u0012\u00028\u00000&H\u0016¢\u0006\u0004\b'\u0010(JX\u00101\u001a\u00020\u0014\"\u0004\b\u0001\u0010)2\f\u0010+\u001a\b\u0012\u0004\u0012\u00028\u00010*2\u0006\u0010 \u001a\u00028\u00002(\u00100\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000-\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010.\u0012\u0006\u0012\u0004\u0018\u00010/0,H\u0002ø\u0001\u0000¢\u0006\u0004\b1\u00102J?\u00103\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\b\u0018\u00010\u00072\u0012\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\b0\u00072\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0002¢\u0006\u0004\b3\u0010\fJ\u001b\u00104\u001a\u00020\u00142\u0006\u0010 \u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0004\b4\u00105R\u0016\u00106\u001a\u00020\u000f8V@\u0016X\u0004¢\u0006\u0006\u001a\u0004\b6\u00107R\u0016\u00108\u001a\u00020\u000f8V@\u0016X\u0004¢\u0006\u0006\u001a\u0004\b8\u00107R(\u0010<\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000-098V@\u0016X\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R\u0019\u0010\u0003\u001a\u00028\u00008F@\u0006¢\u0006\f\u0012\u0004\b?\u0010\u0006\u001a\u0004\b=\u0010>R\u0015\u0010A\u001a\u0004\u0018\u00018\u00008F@\u0006¢\u0006\u0006\u001a\u0004\b@\u0010>\u0002\u0004\n\u0002\b\u0019¨\u0006F"}, mo33671d2 = {"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "E", "Lkotlinx/coroutines/channels/BroadcastChannel;", "value", "<init>", "(Ljava/lang/Object;)V", "()V", "", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "list", "subscriber", "addSubscriber", "([Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;)[Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "", "cause", "", "cancel", "(Ljava/lang/Throwable;)Z", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "", "(Ljava/util/concurrent/CancellationException;)V", "close", "closeSubscriber", "(Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;)V", "Lkotlin/Function1;", "Lkotlinx/coroutines/channels/Handler;", "handler", "invokeOnClose", "(Lkotlin/jvm/functions/Function1;)V", "invokeOnCloseHandler", "(Ljava/lang/Throwable;)V", "element", "offer", "(Ljava/lang/Object;)Z", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "offerInternal", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "openSubscription", "()Lkotlinx/coroutines/channels/ReceiveChannel;", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "", "block", "registerSelectSend", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "removeSubscriber", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isClosedForSend", "()Z", "isFull", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "getValue", "()Ljava/lang/Object;", "value$annotations", "getValueOrNull", "valueOrNull", "Companion", "Closed", "State", "Subscriber", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: ConflatedBroadcastChannel.kt */
public final class ConflatedBroadcastChannel<E> implements BroadcastChannel<E> {
    private static final Closed CLOSED = new Closed((Throwable) null);
    private static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private static final State<Object> INITIAL_STATE;
    private static final Symbol UNDEFINED;
    private static final AtomicReferenceFieldUpdater _state$FU;
    private static final AtomicIntegerFieldUpdater _updating$FU;
    private static final AtomicReferenceFieldUpdater onCloseHandler$FU;
    private volatile Object _state;
    private volatile int _updating;
    private volatile Object onCloseHandler;

    public static /* synthetic */ void value$annotations() {
    }

    public ConflatedBroadcastChannel() {
        this._state = INITIAL_STATE;
        this._updating = 0;
        this.onCloseHandler = null;
    }

    public ConflatedBroadcastChannel(E value) {
        this();
        _state$FU.lazySet(this, new State(value, (Subscriber<E>[]) null));
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u00048\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002R\u0016\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0007X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\u00020\t8\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\n\u0010\u0002¨\u0006\u000b"}, mo33671d2 = {"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Companion;", "", "()V", "CLOSED", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "CLOSED$annotations", "INITIAL_STATE", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$State;", "UNDEFINED", "Lkotlinx/coroutines/internal/Symbol;", "UNDEFINED$annotations", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: ConflatedBroadcastChannel.kt */
    private static final class Companion {
        private static /* synthetic */ void CLOSED$annotations() {
        }

        private static /* synthetic */ void UNDEFINED$annotations() {
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    static {
        Class<ConflatedBroadcastChannel> cls = ConflatedBroadcastChannel.class;
        Symbol symbol = new Symbol("UNDEFINED");
        UNDEFINED = symbol;
        INITIAL_STATE = new State<>(symbol, (Subscriber<E>[]) null);
        _state$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_state");
        _updating$FU = AtomicIntegerFieldUpdater.newUpdater(cls, "_updating");
        onCloseHandler$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "onCloseHandler");
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002B%\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\u0014\u0010\u0004\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0006\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007R \u0010\u0004\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0006\u0018\u00010\u00058\u0006X\u0004¢\u0006\u0004\n\u0002\u0010\bR\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, mo33671d2 = {"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$State;", "E", "", "value", "subscribers", "", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "(Ljava/lang/Object;[Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;)V", "[Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: ConflatedBroadcastChannel.kt */
    private static final class State<E> {
        public final Subscriber<E>[] subscribers;
        public final Object value;

        public State(Object value2, Subscriber<E>[] subscribers2) {
            this.value = value2;
            this.subscribers = subscribers2;
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007¨\u0006\n"}, mo33671d2 = {"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "", "closeCause", "", "(Ljava/lang/Throwable;)V", "sendException", "getSendException", "()Ljava/lang/Throwable;", "valueException", "getValueException", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: ConflatedBroadcastChannel.kt */
    private static final class Closed {
        public final Throwable closeCause;

        public Closed(Throwable closeCause2) {
            this.closeCause = closeCause2;
        }

        public final Throwable getSendException() {
            Throwable th = this.closeCause;
            return th != null ? th : new ClosedSendChannelException(ChannelsKt.DEFAULT_CLOSE_MESSAGE);
        }

        public final Throwable getValueException() {
            Throwable th = this.closeCause;
            return th != null ? th : new IllegalStateException(ChannelsKt.DEFAULT_CLOSE_MESSAGE);
        }
    }

    public final E getValue() {
        Object state = this._state;
        if (state instanceof Closed) {
            throw ((Closed) state).getValueException();
        } else if (!(state instanceof State)) {
            throw new IllegalStateException(("Invalid state " + state).toString());
        } else if (((State) state).value != UNDEFINED) {
            return ((State) state).value;
        } else {
            throw new IllegalStateException("No value");
        }
    }

    public final E getValueOrNull() {
        Object state = this._state;
        if (state instanceof Closed) {
            return null;
        }
        if (state instanceof State) {
            Object this_$iv = UNDEFINED;
            Object value$iv = ((State) state).value;
            if (value$iv == this_$iv) {
                return null;
            }
            return value$iv;
        }
        throw new IllegalStateException(("Invalid state " + state).toString());
    }

    public boolean isClosedForSend() {
        return this._state instanceof Closed;
    }

    public boolean isFull() {
        return false;
    }

    public ReceiveChannel<E> openSubscription() {
        Object state;
        Object obj;
        Subscriber subscriber = new Subscriber(this);
        do {
            state = this._state;
            if (state instanceof Closed) {
                subscriber.close(((Closed) state).closeCause);
                return subscriber;
            } else if (state instanceof State) {
                if (((State) state).value != UNDEFINED) {
                    subscriber.offerInternal(((State) state).value);
                }
                obj = ((State) state).value;
                if (state != null) {
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.ConflatedBroadcastChannel.State<E>");
                }
            } else {
                throw new IllegalStateException(("Invalid state " + state).toString());
            }
        } while (!_state$FU.compareAndSet(this, state, new State(obj, addSubscriber(((State) state).subscribers, subscriber))));
        return subscriber;
    }

    /* access modifiers changed from: private */
    public final void closeSubscriber(Subscriber<E> subscriber) {
        Object state;
        Object obj;
        Subscriber<E>[] subscriberArr;
        do {
            state = this._state;
            if (!(state instanceof Closed)) {
                if (state instanceof State) {
                    obj = ((State) state).value;
                    if (state != null) {
                        subscriberArr = ((State) state).subscribers;
                        if (subscriberArr == null) {
                            Intrinsics.throwNpe();
                        }
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.ConflatedBroadcastChannel.State<E>");
                    }
                } else {
                    throw new IllegalStateException(("Invalid state " + state).toString());
                }
            } else {
                return;
            }
        } while (!_state$FU.compareAndSet(this, state, new State(obj, removeSubscriber(subscriberArr, subscriber))));
    }

    private final Subscriber<E>[] addSubscriber(Subscriber<E>[] list, Subscriber<E> subscriber) {
        if (list != null) {
            return (Subscriber[]) ArraysKt.plus((T[]) list, subscriber);
        }
        Subscriber<E>[] subscriberArr = new Subscriber[1];
        for (int i = 0; i < 1; i++) {
            int i2 = i;
            subscriberArr[i] = subscriber;
        }
        return subscriberArr;
    }

    private final Subscriber<E>[] removeSubscriber(Subscriber<E>[] list, Subscriber<E> subscriber) {
        int n = list.length;
        int i = ArraysKt.indexOf((T[]) list, subscriber);
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(i >= 0)) {
                throw new AssertionError();
            }
        }
        if (n == 1) {
            return null;
        }
        Subscriber<E>[] subscriberArr = new Subscriber[(n - 1)];
        Subscriber<E>[] subscriberArr2 = list;
        Subscriber<E>[] subscriberArr3 = subscriberArr;
        ArraysKt.copyInto$default((Object[]) subscriberArr2, (Object[]) subscriberArr3, 0, 0, i, 6, (Object) null);
        ArraysKt.copyInto$default((Object[]) subscriberArr2, (Object[]) subscriberArr3, i, i + 1, 0, 8, (Object) null);
        return subscriberArr;
    }

    /* renamed from: close */
    public boolean cancel(Throwable cause) {
        Object state;
        int i;
        do {
            state = this._state;
            if (state instanceof Closed) {
                return false;
            }
            if (!(state instanceof State)) {
                throw new IllegalStateException(("Invalid state " + state).toString());
            }
        } while (!_state$FU.compareAndSet(this, state, cause == null ? CLOSED : new Closed(cause)));
        if (state != null) {
            Subscriber[] subscriberArr = ((State) state).subscribers;
            if (subscriberArr != null) {
                for (Subscriber it : subscriberArr) {
                    it.close(cause);
                }
            }
            invokeOnCloseHandler(cause);
            return true;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.ConflatedBroadcastChannel.State<E>");
    }

    private final void invokeOnCloseHandler(Throwable cause) {
        Object handler = this.onCloseHandler;
        if (handler != null && handler != AbstractChannelKt.HANDLER_INVOKED && onCloseHandler$FU.compareAndSet(this, handler, AbstractChannelKt.HANDLER_INVOKED)) {
            ((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(handler, 1)).invoke(cause);
        }
    }

    public void invokeOnClose(Function1<? super Throwable, Unit> handler) {
        Intrinsics.checkParameterIsNotNull(handler, "handler");
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = onCloseHandler$FU;
        if (!atomicReferenceFieldUpdater.compareAndSet(this, (Object) null, handler)) {
            Object value = this.onCloseHandler;
            if (value == AbstractChannelKt.HANDLER_INVOKED) {
                throw new IllegalStateException("Another handler was already registered and successfully invoked");
            }
            throw new IllegalStateException("Another handler was already registered: " + value);
        }
        Object state = this._state;
        if ((state instanceof Closed) && atomicReferenceFieldUpdater.compareAndSet(this, handler, AbstractChannelKt.HANDLER_INVOKED)) {
            handler.invoke(((Closed) state).closeCause);
        }
    }

    public void cancel(CancellationException cause) {
        cancel(cause);
    }

    public Object send(E element, Continuation<? super Unit> $completion) {
        Closed it = offerInternal(element);
        if (it == null) {
            return Unit.INSTANCE;
        }
        throw it.getSendException();
    }

    public boolean offer(E element) {
        Closed it = offerInternal(element);
        if (it == null) {
            return true;
        }
        throw it.getSendException();
    }

    private final Closed offerInternal(E element) {
        Object state;
        if (!_updating$FU.compareAndSet(this, 0, 1)) {
            return null;
        }
        do {
            try {
                state = this._state;
                if (state instanceof Closed) {
                    Closed closed = (Closed) state;
                    this._updating = 0;
                    return closed;
                } else if (!(state instanceof State)) {
                    throw new IllegalStateException(("Invalid state " + state).toString());
                } else if (state != null) {
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.ConflatedBroadcastChannel.State<E>");
                }
            } finally {
                this._updating = 0;
            }
        } while (!_state$FU.compareAndSet(this, state, new State(element, ((State) state).subscribers)));
        Subscriber[] subscriberArr = ((State) state).subscribers;
        if (subscriberArr != null) {
            for (Subscriber it : subscriberArr) {
                it.offerInternal(element);
            }
        }
        return null;
    }

    public SelectClause2<E, SendChannel<E>> getOnSend() {
        return new ConflatedBroadcastChannel$onSend$1(this);
    }

    /* access modifiers changed from: private */
    public final <R> void registerSelectSend(SelectInstance<? super R> select, E element, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> block) {
        if (select.trySelect((Object) null)) {
            Closed it = offerInternal(element);
            if (it != null) {
                select.resumeSelectCancellableWithException(it.getSendException());
            } else {
                UndispatchedKt.startCoroutineUnintercepted(block, this, select.getCompletion());
            }
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0017\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0010¢\u0006\u0002\b\u000bJ\u0015\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u000fR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, mo33671d2 = {"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "E", "Lkotlinx/coroutines/channels/ConflatedChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "broadcastChannel", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "(Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;)V", "cancelInternal", "", "cause", "", "cancelInternal$kotlinx_coroutines_core", "offerInternal", "", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: ConflatedBroadcastChannel.kt */
    private static final class Subscriber<E> extends ConflatedChannel<E> implements ReceiveChannel<E> {
        private final ConflatedBroadcastChannel<E> broadcastChannel;

        public Subscriber(ConflatedBroadcastChannel<E> broadcastChannel2) {
            Intrinsics.checkParameterIsNotNull(broadcastChannel2, "broadcastChannel");
            this.broadcastChannel = broadcastChannel2;
        }

        public boolean cancelInternal$kotlinx_coroutines_core(Throwable cause) {
            boolean closed = close(cause);
            if (closed) {
                this.broadcastChannel.closeSubscriber(this);
            }
            return closed;
        }

        public Object offerInternal(E element) {
            return super.offerInternal(element);
        }
    }
}
