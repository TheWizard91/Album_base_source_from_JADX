package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import kotlin.coroutines.Continuation;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Utils;

abstract class HttpServiceMethod<ResponseT, ReturnT> extends ServiceMethod<ReturnT> {
    private final Call.Factory callFactory;
    private final RequestFactory requestFactory;
    private final Converter<ResponseBody, ResponseT> responseConverter;

    /* access modifiers changed from: protected */
    @Nullable
    public abstract ReturnT adapt(Call<ResponseT> call, Object[] objArr);

    static <ResponseT, ReturnT> HttpServiceMethod<ResponseT, ReturnT> parseAnnotations(Retrofit retrofit, Method method, RequestFactory requestFactory2) {
        Type adapterType;
        Annotation[] annotations;
        boolean continuationWantsResponse;
        Retrofit retrofit3 = retrofit;
        Method method2 = method;
        RequestFactory requestFactory3 = requestFactory2;
        boolean isKotlinSuspendFunction = requestFactory3.isKotlinSuspendFunction;
        boolean continuationWantsResponse2 = false;
        Annotation[] annotations2 = method.getAnnotations();
        if (isKotlinSuspendFunction) {
            Type[] parameterTypes = method.getGenericParameterTypes();
            Type responseType = Utils.getParameterLowerBound(0, (ParameterizedType) parameterTypes[parameterTypes.length - 1]);
            if (Utils.getRawType(responseType) == Response.class && (responseType instanceof ParameterizedType)) {
                responseType = Utils.getParameterUpperBound(0, (ParameterizedType) responseType);
                continuationWantsResponse2 = true;
            }
            Type adapterType2 = new Utils.ParameterizedTypeImpl((Type) null, Call.class, responseType);
            continuationWantsResponse = continuationWantsResponse2;
            annotations = SkipCallbackExecutorImpl.ensurePresent(annotations2);
            adapterType = adapterType2;
        } else {
            continuationWantsResponse = false;
            annotations = annotations2;
            adapterType = method.getGenericReturnType();
        }
        CallAdapter<ResponseT, ReturnT> callAdapter = createCallAdapter(retrofit3, method2, adapterType, annotations);
        Type responseType2 = callAdapter.responseType();
        if (responseType2 == Response.class) {
            throw Utils.methodError(method2, "'" + Utils.getRawType(responseType2).getName() + "' is not a valid response body type. Did you mean ResponseBody?", new Object[0]);
        } else if (responseType2 == Response.class) {
            throw Utils.methodError(method2, "Response must include generic type (e.g., Response<String>)", new Object[0]);
        } else if (!requestFactory3.httpMethod.equals("HEAD") || Void.class.equals(responseType2)) {
            Converter<ResponseBody, ResponseT> responseConverter2 = createResponseConverter(retrofit3, method2, responseType2);
            Call.Factory callFactory2 = retrofit3.callFactory;
            if (!isKotlinSuspendFunction) {
                return new CallAdapted(requestFactory3, callFactory2, responseConverter2, callAdapter);
            }
            if (continuationWantsResponse) {
                return new SuspendForResponse(requestFactory3, callFactory2, responseConverter2, callAdapter);
            }
            Call.Factory factory = callFactory2;
            Converter<ResponseBody, ResponseT> converter = responseConverter2;
            return new SuspendForBody(requestFactory2, callFactory2, responseConverter2, callAdapter, false);
        } else {
            throw Utils.methodError(method2, "HEAD method must use Void as response type.", new Object[0]);
        }
    }

    private static <ResponseT, ReturnT> CallAdapter<ResponseT, ReturnT> createCallAdapter(Retrofit retrofit, Method method, Type returnType, Annotation[] annotations) {
        try {
            return retrofit.callAdapter(returnType, annotations);
        } catch (RuntimeException e) {
            throw Utils.methodError(method, e, "Unable to create call adapter for %s", returnType);
        }
    }

    private static <ResponseT> Converter<ResponseBody, ResponseT> createResponseConverter(Retrofit retrofit, Method method, Type responseType) {
        try {
            return retrofit.responseBodyConverter(responseType, method.getAnnotations());
        } catch (RuntimeException e) {
            throw Utils.methodError(method, e, "Unable to create converter for %s", responseType);
        }
    }

    HttpServiceMethod(RequestFactory requestFactory2, Call.Factory callFactory2, Converter<ResponseBody, ResponseT> responseConverter2) {
        this.requestFactory = requestFactory2;
        this.callFactory = callFactory2;
        this.responseConverter = responseConverter2;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final ReturnT invoke(Object[] args) {
        return adapt(new OkHttpCall<>(this.requestFactory, args, this.callFactory, this.responseConverter), args);
    }

    static final class CallAdapted<ResponseT, ReturnT> extends HttpServiceMethod<ResponseT, ReturnT> {
        private final CallAdapter<ResponseT, ReturnT> callAdapter;

        CallAdapted(RequestFactory requestFactory, Call.Factory callFactory, Converter<ResponseBody, ResponseT> responseConverter, CallAdapter<ResponseT, ReturnT> callAdapter2) {
            super(requestFactory, callFactory, responseConverter);
            this.callAdapter = callAdapter2;
        }

        /* JADX WARNING: type inference failed for: r2v0, types: [retrofit2.Call, retrofit2.Call<ResponseT>] */
        /* access modifiers changed from: protected */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public ReturnT adapt(retrofit2.Call<ResponseT> r2, java.lang.Object[] r3) {
            /*
                r1 = this;
                retrofit2.CallAdapter<ResponseT, ReturnT> r0 = r1.callAdapter
                java.lang.Object r0 = r0.adapt(r2)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: retrofit2.HttpServiceMethod.CallAdapted.adapt(retrofit2.Call, java.lang.Object[]):java.lang.Object");
        }
    }

    static final class SuspendForResponse<ResponseT> extends HttpServiceMethod<ResponseT, Object> {
        private final CallAdapter<ResponseT, Call<ResponseT>> callAdapter;

        SuspendForResponse(RequestFactory requestFactory, Call.Factory callFactory, Converter<ResponseBody, ResponseT> responseConverter, CallAdapter<ResponseT, Call<ResponseT>> callAdapter2) {
            super(requestFactory, callFactory, responseConverter);
            this.callAdapter = callAdapter2;
        }

        /* access modifiers changed from: protected */
        public Object adapt(Call<ResponseT> call, Object[] args) {
            return KotlinExtensions.awaitResponse(this.callAdapter.adapt(call), args[args.length - 1]);
        }
    }

    static final class SuspendForBody<ResponseT> extends HttpServiceMethod<ResponseT, Object> {
        private final CallAdapter<ResponseT, Call<ResponseT>> callAdapter;
        private final boolean isNullable;

        SuspendForBody(RequestFactory requestFactory, Call.Factory callFactory, Converter<ResponseBody, ResponseT> responseConverter, CallAdapter<ResponseT, Call<ResponseT>> callAdapter2, boolean isNullable2) {
            super(requestFactory, callFactory, responseConverter);
            this.callAdapter = callAdapter2;
            this.isNullable = isNullable2;
        }

        /* access modifiers changed from: protected */
        public Object adapt(Call<ResponseT> call, Object[] args) {
            Call<ResponseT> call2 = this.callAdapter.adapt(call);
            Continuation<ResponseT> continuation = args[args.length - 1];
            try {
                if (this.isNullable) {
                    return KotlinExtensions.awaitNullable(call2, continuation);
                }
                return KotlinExtensions.await(call2, continuation);
            } catch (Exception e) {
                return KotlinExtensions.yieldAndThrow(e, continuation);
            }
        }
    }
}
