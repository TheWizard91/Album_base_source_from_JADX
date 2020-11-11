package com.google.android.play.core.tasks;

import com.google.android.play.core.splitcompat.C3082d;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class Tasks {
    private Tasks() {
    }

    /* renamed from: a */
    public static <ResultT> Task<ResultT> m1066a(Exception exc) {
        C3173m mVar = new C3173m();
        mVar.mo44335a(exc);
        return mVar;
    }

    /* renamed from: a */
    public static <ResultT> Task<ResultT> m1067a(ResultT resultt) {
        C3173m mVar = new C3173m();
        mVar.mo44336a(resultt);
        return mVar;
    }

    /* renamed from: a */
    private static <ResultT> ResultT m1068a(Task<ResultT> task) throws ExecutionException {
        if (task.isSuccessful()) {
            return task.getResult();
        }
        throw new ExecutionException(task.getException());
    }

    /* renamed from: a */
    private static void m1069a(Task<?> task, C3174n nVar) {
        task.addOnSuccessListener(TaskExecutors.f1525a, nVar);
        task.addOnFailureListener(TaskExecutors.f1525a, nVar);
    }

    public static <ResultT> ResultT await(Task<ResultT> task) throws ExecutionException, InterruptedException {
        C3082d.m896a(task, (Object) "Task must not be null");
        if (task.isComplete()) {
            return m1068a(task);
        }
        C3174n nVar = new C3174n((byte[]) null);
        m1069a(task, nVar);
        nVar.mo44339a();
        return m1068a(task);
    }

    public static <ResultT> ResultT await(Task<ResultT> task, long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        C3082d.m896a(task, (Object) "Task must not be null");
        C3082d.m896a(timeUnit, (Object) "TimeUnit must not be null");
        if (task.isComplete()) {
            return m1068a(task);
        }
        C3174n nVar = new C3174n((byte[]) null);
        m1069a(task, nVar);
        if (nVar.mo44340a(j, timeUnit)) {
            return m1068a(task);
        }
        throw new TimeoutException("Timed out waiting for Task");
    }
}
