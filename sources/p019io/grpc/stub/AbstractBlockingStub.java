package p019io.grpc.stub;

import javax.annotation.CheckReturnValue;
import p019io.grpc.CallOptions;
import p019io.grpc.Channel;
import p019io.grpc.stub.AbstractBlockingStub;
import p019io.grpc.stub.AbstractStub;
import p019io.grpc.stub.ClientCalls;

@CheckReturnValue
/* renamed from: io.grpc.stub.AbstractBlockingStub */
public abstract class AbstractBlockingStub<S extends AbstractBlockingStub<S>> extends AbstractStub<S> {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    protected AbstractBlockingStub(Channel channel, CallOptions callOptions) {
        super(channel, callOptions);
    }

    public static <T extends AbstractStub<T>> T newStub(AbstractStub.StubFactory<T> factory, Channel channel) {
        return newStub(factory, channel, CallOptions.DEFAULT);
    }

    public static <T extends AbstractStub<T>> T newStub(AbstractStub.StubFactory<T> factory, Channel channel, CallOptions callOptions) {
        T stub = factory.newStub(channel, callOptions.withOption(ClientCalls.STUB_TYPE_OPTION, ClientCalls.StubType.BLOCKING));
        if (stub instanceof AbstractBlockingStub) {
            return stub;
        }
        throw new AssertionError(String.format("Expected AbstractBlockingStub, but got %s.", new Object[]{stub.getClass()}));
    }
}
