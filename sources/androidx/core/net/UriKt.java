package androidx.core.net;

import android.net.Uri;
import com.facebook.common.util.UriUtil;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\r\u0010\u0003\u001a\u00020\u0002*\u00020\u0001H\b\u001a\r\u0010\u0003\u001a\u00020\u0002*\u00020\u0004H\b¨\u0006\u0005"}, mo33671d2 = {"toFile", "Ljava/io/File;", "Landroid/net/Uri;", "toUri", "", "core-ktx_release"}, mo33672k = 2, mo33673mv = {1, 1, 15})
/* compiled from: Uri.kt */
public final class UriKt {
    public static final Uri toUri(String $this$toUri) {
        Intrinsics.checkParameterIsNotNull($this$toUri, "$this$toUri");
        Uri parse = Uri.parse($this$toUri);
        Intrinsics.checkExpressionValueIsNotNull(parse, "Uri.parse(this)");
        return parse;
    }

    public static final Uri toUri(File $this$toUri) {
        Intrinsics.checkParameterIsNotNull($this$toUri, "$this$toUri");
        Uri fromFile = Uri.fromFile($this$toUri);
        Intrinsics.checkExpressionValueIsNotNull(fromFile, "Uri.fromFile(this)");
        return fromFile;
    }

    public static final File toFile(Uri $this$toFile) {
        Intrinsics.checkParameterIsNotNull($this$toFile, "$this$toFile");
        if (Intrinsics.areEqual((Object) $this$toFile.getScheme(), (Object) UriUtil.LOCAL_FILE_SCHEME)) {
            return new File($this$toFile.getPath());
        }
        throw new IllegalArgumentException(("Uri lacks 'file' scheme: " + $this$toFile).toString());
    }
}
