package retrofit2;

import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.Method;
import kotlin.KotlinNullPointerException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001e\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J$\u0010\b\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\nH\u0016¨\u0006\u000b"}, mo33671d2 = {"retrofit2/KotlinExtensions$await$2$2", "Lretrofit2/Callback;", "onFailure", "", "call", "Lretrofit2/Call;", "t", "", "onResponse", "response", "Lretrofit2/Response;", "retrofit"}, mo33672k = 1, mo33673mv = {1, 1, 13})
/* compiled from: KotlinExtensions.kt */
public final class KotlinExtensions$await$2$2 implements Callback<T> {
    final /* synthetic */ CancellableContinuation $continuation;

    KotlinExtensions$await$2$2(CancellableContinuation $captured_local_variable$0) {
        this.$continuation = $captured_local_variable$0;
    }

    public void onResponse(Call<T> call, Response<T> response) {
        Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
        Intrinsics.checkParameterIsNotNull(response, "response");
        if (response.isSuccessful()) {
            Object body = response.body();
            if (body == null) {
                Object tag = call.request().tag(Invocation.class);
                if (tag == null) {
                    Intrinsics.throwNpe();
                }
                Intrinsics.checkExpressionValueIsNotNull(tag, "call.request().tag(Invocation::class.java)!!");
                Method method = ((Invocation) tag).method();
                StringBuilder append = new StringBuilder().append("Response from ");
                Intrinsics.checkExpressionValueIsNotNull(method, FirebaseAnalytics.Param.METHOD);
                Class<?> declaringClass = method.getDeclaringClass();
                Intrinsics.checkExpressionValueIsNotNull(declaringClass, "method.declaringClass");
                KotlinNullPointerException e = new KotlinNullPointerException(append.append(declaringClass.getName()).append('.').append(method.getName()).append(" was null but response body type was declared as non-null").toString());
                Result.Companion companion = Result.Companion;
                this.$continuation.resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(e)));
                return;
            }
            Result.Companion companion2 = Result.Companion;
            this.$continuation.resumeWith(Result.m1289constructorimpl(body));
            return;
        }
        Result.Companion companion3 = Result.Companion;
        this.$continuation.resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(new HttpException(response))));
    }

    public void onFailure(Call<T> call, Throwable t) {
        Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
        Intrinsics.checkParameterIsNotNull(t, "t");
        Result.Companion companion = Result.Companion;
        this.$continuation.resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(t)));
    }
}
