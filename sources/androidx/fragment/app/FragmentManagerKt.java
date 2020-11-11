package androidx.fragment.app;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a0\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u0006¢\u0006\u0002\b\bH\b\u001a0\u0010\t\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u0006¢\u0006\u0002\b\bH\b\u001a:\u0010\n\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\u0003\u001a\u00020\u00042\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u0006¢\u0006\u0002\b\bH\b¨\u0006\f"}, mo33671d2 = {"commit", "", "Landroidx/fragment/app/FragmentManager;", "allowStateLoss", "", "body", "Lkotlin/Function1;", "Landroidx/fragment/app/FragmentTransaction;", "Lkotlin/ExtensionFunctionType;", "commitNow", "transaction", "now", "fragment-ktx_release"}, mo33672k = 2, mo33673mv = {1, 1, 15})
/* compiled from: FragmentManager.kt */
public final class FragmentManagerKt {
    public static /* synthetic */ void commit$default(FragmentManager $this$commit, boolean allowStateLoss, Function1 body, int i, Object obj) {
        if ((i & 1) != 0) {
            allowStateLoss = false;
        }
        Intrinsics.checkParameterIsNotNull($this$commit, "$this$commit");
        Intrinsics.checkParameterIsNotNull(body, "body");
        FragmentTransaction transaction = $this$commit.beginTransaction();
        Intrinsics.checkExpressionValueIsNotNull(transaction, "beginTransaction()");
        body.invoke(transaction);
        if (allowStateLoss) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    public static final void commit(FragmentManager $this$commit, boolean allowStateLoss, Function1<? super FragmentTransaction, Unit> body) {
        Intrinsics.checkParameterIsNotNull($this$commit, "$this$commit");
        Intrinsics.checkParameterIsNotNull(body, "body");
        FragmentTransaction transaction = $this$commit.beginTransaction();
        Intrinsics.checkExpressionValueIsNotNull(transaction, "beginTransaction()");
        body.invoke(transaction);
        if (allowStateLoss) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    public static /* synthetic */ void commitNow$default(FragmentManager $this$commitNow, boolean allowStateLoss, Function1 body, int i, Object obj) {
        if ((i & 1) != 0) {
            allowStateLoss = false;
        }
        Intrinsics.checkParameterIsNotNull($this$commitNow, "$this$commitNow");
        Intrinsics.checkParameterIsNotNull(body, "body");
        FragmentTransaction transaction = $this$commitNow.beginTransaction();
        Intrinsics.checkExpressionValueIsNotNull(transaction, "beginTransaction()");
        body.invoke(transaction);
        if (allowStateLoss) {
            transaction.commitNowAllowingStateLoss();
        } else {
            transaction.commitNow();
        }
    }

    public static final void commitNow(FragmentManager $this$commitNow, boolean allowStateLoss, Function1<? super FragmentTransaction, Unit> body) {
        Intrinsics.checkParameterIsNotNull($this$commitNow, "$this$commitNow");
        Intrinsics.checkParameterIsNotNull(body, "body");
        FragmentTransaction transaction = $this$commitNow.beginTransaction();
        Intrinsics.checkExpressionValueIsNotNull(transaction, "beginTransaction()");
        body.invoke(transaction);
        if (allowStateLoss) {
            transaction.commitNowAllowingStateLoss();
        } else {
            transaction.commitNow();
        }
    }

    public static /* synthetic */ void transaction$default(FragmentManager $this$transaction, boolean now, boolean allowStateLoss, Function1 body, int i, Object obj) {
        if ((i & 1) != 0) {
            now = false;
        }
        if ((i & 2) != 0) {
            allowStateLoss = false;
        }
        Intrinsics.checkParameterIsNotNull($this$transaction, "$this$transaction");
        Intrinsics.checkParameterIsNotNull(body, "body");
        FragmentTransaction transaction = $this$transaction.beginTransaction();
        Intrinsics.checkExpressionValueIsNotNull(transaction, "beginTransaction()");
        body.invoke(transaction);
        if (now) {
            if (allowStateLoss) {
                transaction.commitNowAllowingStateLoss();
            } else {
                transaction.commitNow();
            }
        } else if (allowStateLoss) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    @Deprecated(message = "Use commit { .. } or commitNow { .. } extensions")
    public static final void transaction(FragmentManager $this$transaction, boolean now, boolean allowStateLoss, Function1<? super FragmentTransaction, Unit> body) {
        Intrinsics.checkParameterIsNotNull($this$transaction, "$this$transaction");
        Intrinsics.checkParameterIsNotNull(body, "body");
        FragmentTransaction transaction = $this$transaction.beginTransaction();
        Intrinsics.checkExpressionValueIsNotNull(transaction, "beginTransaction()");
        body.invoke(transaction);
        if (now) {
            if (allowStateLoss) {
                transaction.commitNowAllowingStateLoss();
            } else {
                transaction.commitNow();
            }
        } else if (allowStateLoss) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }
}
