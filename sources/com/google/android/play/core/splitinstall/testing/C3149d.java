package com.google.android.play.core.splitinstall.testing;

import com.google.android.play.core.splitinstall.SplitInstallException;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import java.util.ArrayList;

/* renamed from: com.google.android.play.core.splitinstall.testing.d */
final class C3149d implements C3154i {

    /* renamed from: a */
    final /* synthetic */ SplitInstallRequest f1498a;

    C3149d(SplitInstallRequest splitInstallRequest) {
        this.f1498a = splitInstallRequest;
    }

    /* renamed from: a */
    public final SplitInstallSessionState mo44301a(SplitInstallSessionState splitInstallSessionState) {
        if (splitInstallSessionState == null || splitInstallSessionState.hasTerminalStatus()) {
            int i = 1;
            if (splitInstallSessionState != null) {
                i = 1 + splitInstallSessionState.sessionId();
            }
            return SplitInstallSessionState.create(i, 1, 0, 0, 0, this.f1498a.getModuleNames(), new ArrayList());
        }
        throw new SplitInstallException(-1);
    }
}
