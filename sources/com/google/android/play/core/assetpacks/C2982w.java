package com.google.android.play.core.assetpacks;

import android.os.ParcelFileDescriptor;
import com.google.android.play.core.tasks.Task;
import java.util.List;

/* renamed from: com.google.android.play.core.assetpacks.w */
interface C2982w {
    /* renamed from: a */
    Task<List<String>> mo43918a();

    /* renamed from: a */
    Task<AssetPackStates> mo43919a(List<String> list, C2883az azVar);

    /* renamed from: a */
    Task<AssetPackStates> mo43920a(List<String> list, List<String> list2);

    /* renamed from: a */
    void mo43921a(int i);

    /* renamed from: a */
    void mo43922a(int i, String str);

    /* renamed from: a */
    void mo43923a(int i, String str, String str2, int i2);

    /* renamed from: a */
    void mo43924a(String str);

    /* renamed from: a */
    void mo43925a(List<String> list);

    /* renamed from: b */
    Task<ParcelFileDescriptor> mo43926b(int i, String str, String str2, int i2);

    /* renamed from: b */
    void mo43927b();
}
