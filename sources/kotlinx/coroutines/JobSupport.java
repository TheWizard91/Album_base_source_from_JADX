package kotlinx.coroutines;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.ConcurrentKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause0;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000Ú\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0001\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0013\b\u0017\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0006Ð\u0001Ñ\u0001Ò\u0001B\u000f\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bJ+\u0010\u000f\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\rH\u0002¢\u0006\u0004\b\u000f\u0010\u0010J%\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u00112\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00110\u0013H\u0002¢\u0006\u0004\b\u0016\u0010\u0017J!\u0010\u001b\u001a\u00020\u00152\b\u0010\u0018\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001a\u001a\u00020\u0019H\u0014¢\u0006\u0004\b\u001b\u0010\u001cJ\u0015\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u001d\u001a\u00020\u0002¢\u0006\u0004\b\u001f\u0010 J\u0015\u0010#\u001a\u0004\u0018\u00010\tH@ø\u0001\u0000¢\u0006\u0004\b!\u0010\"J\u0015\u0010$\u001a\u0004\u0018\u00010\tH@ø\u0001\u0000¢\u0006\u0004\b$\u0010\"J\u0019\u0010&\u001a\u00020\u00052\b\u0010%\u001a\u0004\u0018\u00010\u0011H\u0017¢\u0006\u0004\b&\u0010'J\u001f\u0010&\u001a\u00020\u00152\u000e\u0010%\u001a\n\u0018\u00010(j\u0004\u0018\u0001`)H\u0016¢\u0006\u0004\b&\u0010*J\u0017\u0010+\u001a\u00020\u00052\b\u0010%\u001a\u0004\u0018\u00010\u0011¢\u0006\u0004\b+\u0010'J\u0019\u0010.\u001a\u00020\u00052\b\u0010%\u001a\u0004\u0018\u00010\tH\u0000¢\u0006\u0004\b,\u0010-J\u0019\u0010/\u001a\u00020\u00052\b\u0010%\u001a\u0004\u0018\u00010\u0011H\u0016¢\u0006\u0004\b/\u0010'J\u0019\u00100\u001a\u00020\u00052\b\u0010%\u001a\u0004\u0018\u00010\tH\u0002¢\u0006\u0004\b0\u0010-J\u0017\u00101\u001a\u00020\u00052\u0006\u0010%\u001a\u00020\u0011H\u0002¢\u0006\u0004\b1\u0010'J\u0017\u00102\u001a\u00020\u00052\u0006\u0010%\u001a\u00020\u0011H\u0016¢\u0006\u0004\b2\u0010'J)\u00105\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u0002032\b\u00104\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001a\u001a\u00020\u0019H\u0002¢\u0006\u0004\b5\u00106J)\u0010;\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u0002072\u0006\u00109\u001a\u0002082\b\u0010:\u001a\u0004\u0018\u00010\tH\u0002¢\u0006\u0004\b;\u0010<J\u0019\u0010=\u001a\u00020\u00112\b\u0010%\u001a\u0004\u0018\u00010\tH\u0002¢\u0006\u0004\b=\u0010>J\u000f\u0010@\u001a\u00020?H\u0002¢\u0006\u0004\b@\u0010AJ\u0019\u0010B\u001a\u0004\u0018\u0001082\u0006\u0010\u0018\u001a\u000203H\u0002¢\u0006\u0004\bB\u0010CJ\u0011\u0010D\u001a\u00060(j\u0002`)¢\u0006\u0004\bD\u0010EJ\u0013\u0010F\u001a\u00060(j\u0002`)H\u0016¢\u0006\u0004\bF\u0010EJ\u0011\u0010I\u001a\u0004\u0018\u00010\tH\u0000¢\u0006\u0004\bG\u0010HJ\u000f\u0010J\u001a\u0004\u0018\u00010\u0011¢\u0006\u0004\bJ\u0010KJ'\u0010L\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0018\u001a\u0002072\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00110\u0013H\u0002¢\u0006\u0004\bL\u0010MJ\u0019\u0010N\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0018\u001a\u000203H\u0002¢\u0006\u0004\bN\u0010OJ\u0017\u0010Q\u001a\u00020\u00052\u0006\u0010P\u001a\u00020\u0011H\u0014¢\u0006\u0004\bQ\u0010'J\u0017\u0010T\u001a\u00020\u00152\u0006\u0010P\u001a\u00020\u0011H\u0010¢\u0006\u0004\bR\u0010SJ\u0019\u0010X\u001a\u00020\u00152\b\u0010U\u001a\u0004\u0018\u00010\u0001H\u0000¢\u0006\u0004\bV\u0010WJF\u0010a\u001a\u00020`2\u0006\u0010Y\u001a\u00020\u00052\u0006\u0010Z\u001a\u00020\u00052'\u0010_\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0011¢\u0006\f\b\\\u0012\b\b]\u0012\u0004\b\b(%\u0012\u0004\u0012\u00020\u00150[j\u0002`^¢\u0006\u0004\ba\u0010bJ6\u0010a\u001a\u00020`2'\u0010_\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0011¢\u0006\f\b\\\u0012\b\b]\u0012\u0004\b\b(%\u0012\u0004\u0012\u00020\u00150[j\u0002`^¢\u0006\u0004\ba\u0010cJ\u0013\u0010d\u001a\u00020\u0015H@ø\u0001\u0000¢\u0006\u0004\bd\u0010\"J\u000f\u0010e\u001a\u00020\u0005H\u0002¢\u0006\u0004\be\u0010fJ\u0013\u0010g\u001a\u00020\u0015H@ø\u0001\u0000¢\u0006\u0004\bg\u0010\"J&\u0010j\u001a\u00020i2\u0014\u0010h\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\u00150[H\b¢\u0006\u0004\bj\u0010kJ\u0019\u0010l\u001a\u00020\u00052\b\u0010%\u001a\u0004\u0018\u00010\tH\u0002¢\u0006\u0004\bl\u0010-J\u0019\u0010n\u001a\u00020\u00052\b\u0010:\u001a\u0004\u0018\u00010\tH\u0000¢\u0006\u0004\bm\u0010-J!\u0010q\u001a\u00020\u00052\b\u0010:\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001a\u001a\u00020\u0019H\u0000¢\u0006\u0004\bo\u0010pJD\u0010r\u001a\u0006\u0012\u0002\b\u00030\r2'\u0010_\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0011¢\u0006\f\b\\\u0012\b\b]\u0012\u0004\b\b(%\u0012\u0004\u0012\u00020\u00150[j\u0002`^2\u0006\u0010Y\u001a\u00020\u0005H\u0002¢\u0006\u0004\br\u0010sJ\u000f\u0010w\u001a\u00020tH\u0010¢\u0006\u0004\bu\u0010vJ\u001f\u0010x\u001a\u00020\u00152\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010%\u001a\u00020\u0011H\u0002¢\u0006\u0004\bx\u0010yJ2\u0010{\u001a\u00020\u0015\"\u000e\b\u0000\u0010z\u0018\u0001*\u0006\u0012\u0002\b\u00030\r2\u0006\u0010\f\u001a\u00020\u000b2\b\u0010%\u001a\u0004\u0018\u00010\u0011H\b¢\u0006\u0004\b{\u0010yJ\u0019\u0010Y\u001a\u00020\u00152\b\u0010%\u001a\u0004\u0018\u00010\u0011H\u0014¢\u0006\u0004\bY\u0010SJ\u0019\u0010|\u001a\u00020\u00152\b\u0010\u0018\u001a\u0004\u0018\u00010\tH\u0014¢\u0006\u0004\b|\u0010}J\u0010\u0010\u0001\u001a\u00020\u0015H\u0010¢\u0006\u0004\b~\u0010J\u0019\u0010\u0001\u001a\u00020\u00152\u0007\u0010\u0001\u001a\u00020\u0003¢\u0006\u0006\b\u0001\u0010\u0001J\u001b\u0010\u0001\u001a\u00020\u00152\u0007\u0010\u0018\u001a\u00030\u0001H\u0002¢\u0006\u0006\b\u0001\u0010\u0001J\u001e\u0010\u0001\u001a\u00020\u00152\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\rH\u0002¢\u0006\u0006\b\u0001\u0010\u0001JI\u0010\u0001\u001a\u00020\u0015\"\u0005\b\u0000\u0010\u00012\u000e\u0010\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000\u00012\u001d\u0010h\u001a\u0019\b\u0001\u0012\u000b\u0012\t\u0012\u0004\u0012\u00028\u00000\u0001\u0012\u0006\u0012\u0004\u0018\u00010\t0[ø\u0001\u0000¢\u0006\u0006\b\u0001\u0010\u0001JX\u0010\u0001\u001a\u00020\u0015\"\u0004\b\u0000\u0010z\"\u0005\b\u0001\u0010\u00012\u000e\u0010\u0001\u001a\t\u0012\u0004\u0012\u00028\u00010\u00012$\u0010h\u001a \b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\u000b\u0012\t\u0012\u0004\u0012\u00028\u00010\u0001\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0001H\u0000ø\u0001\u0000¢\u0006\u0006\b\u0001\u0010\u0001J\u001e\u0010\u0001\u001a\u00020\u00152\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\rH\u0000¢\u0006\u0006\b\u0001\u0010\u0001JX\u0010\u0001\u001a\u00020\u0015\"\u0004\b\u0000\u0010z\"\u0005\b\u0001\u0010\u00012\u000e\u0010\u0001\u001a\t\u0012\u0004\u0012\u00028\u00010\u00012$\u0010h\u001a \b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\u000b\u0012\t\u0012\u0004\u0012\u00028\u00010\u0001\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0001H\u0000ø\u0001\u0000¢\u0006\u0006\b\u0001\u0010\u0001J\u000f\u0010\u0001\u001a\u00020\u0005¢\u0006\u0005\b\u0001\u0010fJ\u001c\u0010\u0001\u001a\u00020\u00192\b\u0010\u0018\u001a\u0004\u0018\u00010\tH\u0002¢\u0006\u0006\b\u0001\u0010\u0001J\u001c\u0010\u0001\u001a\u00020t2\b\u0010\u0018\u001a\u0004\u0018\u00010\tH\u0002¢\u0006\u0006\b\u0001\u0010\u0001J\u0011\u0010\u0001\u001a\u00020tH\u0007¢\u0006\u0005\b\u0001\u0010vJ\u0011\u0010\u0001\u001a\u00020tH\u0016¢\u0006\u0005\b\u0001\u0010vJ,\u0010\u0001\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u0002072\b\u0010:\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001a\u001a\u00020\u0019H\u0002¢\u0006\u0006\b\u0001\u0010\u0001J,\u0010 \u0001\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u0002032\b\u00104\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001a\u001a\u00020\u0019H\u0002¢\u0006\u0006\b \u0001\u0010¡\u0001J\"\u0010¢\u0001\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u0002032\u0006\u0010\u0012\u001a\u00020\u0011H\u0002¢\u0006\u0006\b¢\u0001\u0010£\u0001J.\u0010¤\u0001\u001a\u00020\u00192\b\u0010\u0018\u001a\u0004\u0018\u00010\t2\b\u0010:\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001a\u001a\u00020\u0019H\u0002¢\u0006\u0006\b¤\u0001\u0010¥\u0001J,\u0010¦\u0001\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u0002032\b\u0010:\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001a\u001a\u00020\u0019H\u0002¢\u0006\u0006\b¦\u0001\u0010§\u0001J-\u0010¨\u0001\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u0002072\u0006\u0010\u001d\u001a\u0002082\b\u0010:\u001a\u0004\u0018\u00010\tH\u0010¢\u0006\u0006\b¨\u0001\u0010©\u0001J\u0019\u0010«\u0001\u001a\u0004\u0018\u000108*\u00030ª\u0001H\u0002¢\u0006\u0006\b«\u0001\u0010¬\u0001J\u001f\u0010­\u0001\u001a\u00020\u0015*\u00020\u000b2\b\u0010%\u001a\u0004\u0018\u00010\u0011H\u0002¢\u0006\u0005\b­\u0001\u0010yJ'\u0010¯\u0001\u001a\u00060(j\u0002`)*\u00020\u00112\u000b\b\u0002\u0010®\u0001\u001a\u0004\u0018\u00010tH\u0004¢\u0006\u0006\b¯\u0001\u0010°\u0001R\u001d\u0010´\u0001\u001a\t\u0012\u0004\u0012\u00020\u00010±\u00018F@\u0006¢\u0006\b\u001a\u0006\b²\u0001\u0010³\u0001R\u001a\u0010¶\u0001\u001a\u0004\u0018\u00010\u00118D@\u0004X\u0004¢\u0006\u0007\u001a\u0005\bµ\u0001\u0010KR\u0018\u0010¸\u0001\u001a\u00020\u00058D@\u0004X\u0004¢\u0006\u0007\u001a\u0005\b·\u0001\u0010fR\u0018\u0010º\u0001\u001a\u00020\u00058P@\u0010X\u0004¢\u0006\u0007\u001a\u0005\b¹\u0001\u0010fR\u0018\u0010»\u0001\u001a\u00020\u00058V@\u0016X\u0004¢\u0006\u0007\u001a\u0005\b»\u0001\u0010fR\u0015\u0010¼\u0001\u001a\u00020\u00058F@\u0006¢\u0006\u0007\u001a\u0005\b¼\u0001\u0010fR\u0015\u0010½\u0001\u001a\u00020\u00058F@\u0006¢\u0006\u0007\u001a\u0005\b½\u0001\u0010fR\u0015\u0010¾\u0001\u001a\u00020\u00058F@\u0006¢\u0006\u0007\u001a\u0005\b¾\u0001\u0010fR\u0018\u0010¿\u0001\u001a\u00020\u00058T@\u0014X\u0004¢\u0006\u0007\u001a\u0005\b¿\u0001\u0010fR\u001b\u0010Ã\u0001\u001a\u0007\u0012\u0002\b\u00030À\u00018F@\u0006¢\u0006\b\u001a\u0006\bÁ\u0001\u0010Â\u0001R\u0018\u0010Å\u0001\u001a\u00020\u00058P@\u0010X\u0004¢\u0006\u0007\u001a\u0005\bÄ\u0001\u0010fR\u0016\u0010È\u0001\u001a\u00020\u00048F@\u0006¢\u0006\b\u001a\u0006\bÆ\u0001\u0010Ç\u0001R\u001b\u0010É\u0001\u001a\u0004\u0018\u00010\u001e8\u0000@\u0000X\u000e¢\u0006\b\n\u0006\bÉ\u0001\u0010Ê\u0001R\u0019\u0010\u0018\u001a\u0004\u0018\u00010\t8@@\u0000X\u0004¢\u0006\u0007\u001a\u0005\bË\u0001\u0010HR \u0010Í\u0001\u001a\u0004\u0018\u00010\u0011*\u0004\u0018\u00010\t8B@\u0002X\u0004¢\u0006\u0007\u001a\u0005\bÌ\u0001\u0010>R\u001d\u0010Î\u0001\u001a\u00020\u0005*\u0002038B@\u0002X\u0004¢\u0006\b\u001a\u0006\bÎ\u0001\u0010Ï\u0001\u0002\u0004\n\u0002\b\u0019¨\u0006Ó\u0001"}, mo33671d2 = {"Lkotlinx/coroutines/JobSupport;", "Lkotlinx/coroutines/Job;", "Lkotlinx/coroutines/ChildJob;", "Lkotlinx/coroutines/ParentJob;", "Lkotlinx/coroutines/selects/SelectClause0;", "", "active", "<init>", "(Z)V", "", "expect", "Lkotlinx/coroutines/NodeList;", "list", "Lkotlinx/coroutines/JobNode;", "node", "addLastAtomic", "(Ljava/lang/Object;Lkotlinx/coroutines/NodeList;Lkotlinx/coroutines/JobNode;)Z", "", "rootCause", "", "exceptions", "", "addSuppressedExceptions", "(Ljava/lang/Throwable;Ljava/util/List;)V", "state", "", "mode", "afterCompletionInternal", "(Ljava/lang/Object;I)V", "child", "Lkotlinx/coroutines/ChildHandle;", "attachChild", "(Lkotlinx/coroutines/ChildJob;)Lkotlinx/coroutines/ChildHandle;", "awaitInternal$kotlinx_coroutines_core", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "awaitInternal", "awaitSuspend", "cause", "cancel", "(Ljava/lang/Throwable;)Z", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "(Ljava/util/concurrent/CancellationException;)V", "cancelCoroutine", "cancelImpl$kotlinx_coroutines_core", "(Ljava/lang/Object;)Z", "cancelImpl", "cancelInternal", "cancelMakeCompleting", "cancelParent", "childCancelled", "Lkotlinx/coroutines/Incomplete;", "update", "completeStateFinalization", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Object;I)V", "Lkotlinx/coroutines/JobSupport$Finishing;", "Lkotlinx/coroutines/ChildHandleNode;", "lastChild", "proposedUpdate", "continueCompleting", "(Lkotlinx/coroutines/JobSupport$Finishing;Lkotlinx/coroutines/ChildHandleNode;Ljava/lang/Object;)V", "createCauseException", "(Ljava/lang/Object;)Ljava/lang/Throwable;", "Lkotlinx/coroutines/JobCancellationException;", "createJobCancellationException", "()Lkotlinx/coroutines/JobCancellationException;", "firstChild", "(Lkotlinx/coroutines/Incomplete;)Lkotlinx/coroutines/ChildHandleNode;", "getCancellationException", "()Ljava/util/concurrent/CancellationException;", "getChildJobCancellationCause", "getCompletedInternal$kotlinx_coroutines_core", "()Ljava/lang/Object;", "getCompletedInternal", "getCompletionExceptionOrNull", "()Ljava/lang/Throwable;", "getFinalRootCause", "(Lkotlinx/coroutines/JobSupport$Finishing;Ljava/util/List;)Ljava/lang/Throwable;", "getOrPromoteCancellingList", "(Lkotlinx/coroutines/Incomplete;)Lkotlinx/coroutines/NodeList;", "exception", "handleJobException", "handleOnCompletionException$kotlinx_coroutines_core", "(Ljava/lang/Throwable;)V", "handleOnCompletionException", "parent", "initParentJobInternal$kotlinx_coroutines_core", "(Lkotlinx/coroutines/Job;)V", "initParentJobInternal", "onCancelling", "invokeImmediately", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "handler", "Lkotlinx/coroutines/DisposableHandle;", "invokeOnCompletion", "(ZZLkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/DisposableHandle;", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/DisposableHandle;", "join", "joinInternal", "()Z", "joinSuspend", "block", "", "loopOnState", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Void;", "makeCancelling", "makeCompleting$kotlinx_coroutines_core", "makeCompleting", "makeCompletingOnce$kotlinx_coroutines_core", "(Ljava/lang/Object;I)Z", "makeCompletingOnce", "makeNode", "(Lkotlin/jvm/functions/Function1;Z)Lkotlinx/coroutines/JobNode;", "", "nameString$kotlinx_coroutines_core", "()Ljava/lang/String;", "nameString", "notifyCancelling", "(Lkotlinx/coroutines/NodeList;Ljava/lang/Throwable;)V", "T", "notifyHandlers", "onCompletionInternal", "(Ljava/lang/Object;)V", "onStartInternal$kotlinx_coroutines_core", "()V", "onStartInternal", "parentJob", "parentCancelled", "(Lkotlinx/coroutines/ParentJob;)V", "Lkotlinx/coroutines/Empty;", "promoteEmptyToNodeList", "(Lkotlinx/coroutines/Empty;)V", "promoteSingleToNodeList", "(Lkotlinx/coroutines/JobNode;)V", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/coroutines/Continuation;", "registerSelectClause0", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function1;)V", "Lkotlin/Function2;", "registerSelectClause1Internal$kotlinx_coroutines_core", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "registerSelectClause1Internal", "removeNode$kotlinx_coroutines_core", "removeNode", "selectAwaitCompletion$kotlinx_coroutines_core", "selectAwaitCompletion", "start", "startInternal", "(Ljava/lang/Object;)I", "stateString", "(Ljava/lang/Object;)Ljava/lang/String;", "toDebugString", "toString", "tryFinalizeFinishingState", "(Lkotlinx/coroutines/JobSupport$Finishing;Ljava/lang/Object;I)Z", "tryFinalizeSimpleState", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Object;I)Z", "tryMakeCancelling", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Throwable;)Z", "tryMakeCompleting", "(Ljava/lang/Object;Ljava/lang/Object;I)I", "tryMakeCompletingSlowPath", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Object;I)I", "tryWaitForChild", "(Lkotlinx/coroutines/JobSupport$Finishing;Lkotlinx/coroutines/ChildHandleNode;Ljava/lang/Object;)Z", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "nextChild", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Lkotlinx/coroutines/ChildHandleNode;", "notifyCompletion", "message", "toCancellationException", "(Ljava/lang/Throwable;Ljava/lang/String;)Ljava/util/concurrent/CancellationException;", "Lkotlin/sequences/Sequence;", "getChildren", "()Lkotlin/sequences/Sequence;", "children", "getCompletionCause", "completionCause", "getCompletionCauseHandled", "completionCauseHandled", "getHandlesException$kotlinx_coroutines_core", "handlesException", "isActive", "isCancelled", "isCompleted", "isCompletedExceptionally", "isScopedCoroutine", "Lkotlin/coroutines/CoroutineContext$Key;", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "key", "getOnCancelComplete$kotlinx_coroutines_core", "onCancelComplete", "getOnJoin", "()Lkotlinx/coroutines/selects/SelectClause0;", "onJoin", "parentHandle", "Lkotlinx/coroutines/ChildHandle;", "getState$kotlinx_coroutines_core", "getExceptionOrNull", "exceptionOrNull", "isCancelling", "(Lkotlinx/coroutines/Incomplete;)Z", "AwaitContinuation", "ChildCompletion", "Finishing", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
@Deprecated(level = DeprecationLevel.ERROR, message = "This is internal API and may be removed in the future releases")
/* compiled from: JobSupport.kt */
public class JobSupport implements Job, ChildJob, ParentJob, SelectClause0 {
    private static final AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(JobSupport.class, Object.class, "_state");
    private volatile Object _state;
    public volatile ChildHandle parentHandle;

    public JobSupport(boolean active) {
        this._state = active ? JobSupportKt.EMPTY_ACTIVE : JobSupportKt.EMPTY_NEW;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public /* synthetic */ void cancel() {
        cancel((CancellationException) null);
    }

    public <R> R fold(R initial, Function2<? super R, ? super CoroutineContext.Element, ? extends R> operation) {
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        return Job.DefaultImpls.fold(this, initial, operation);
    }

    public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        return Job.DefaultImpls.get(this, key);
    }

    public CoroutineContext minusKey(CoroutineContext.Key<?> key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        return Job.DefaultImpls.minusKey(this, key);
    }

    public CoroutineContext plus(CoroutineContext context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        return Job.DefaultImpls.plus((Job) this, context);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
    public Job plus(Job other) {
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Job.DefaultImpls.plus((Job) this, other);
    }

    public final CoroutineContext.Key<?> getKey() {
        return Job.Key;
    }

    public final void initParentJobInternal$kotlinx_coroutines_core(Job parent) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(this.parentHandle == null)) {
                throw new AssertionError();
            }
        }
        if (parent == null) {
            this.parentHandle = NonDisposableHandle.INSTANCE;
            return;
        }
        parent.start();
        ChildHandle handle = parent.attachChild(this);
        this.parentHandle = handle;
        if (isCompleted()) {
            handle.dispose();
            this.parentHandle = NonDisposableHandle.INSTANCE;
        }
    }

    public final Object getState$kotlinx_coroutines_core() {
        while (true) {
            Object state = this._state;
            if (!(state instanceof OpDescriptor)) {
                return state;
            }
            ((OpDescriptor) state).perform(this);
        }
    }

    private final Void loopOnState(Function1<Object, Unit> block) {
        while (true) {
            block.invoke(getState$kotlinx_coroutines_core());
        }
    }

    public boolean isActive() {
        Object state = getState$kotlinx_coroutines_core();
        return (state instanceof Incomplete) && ((Incomplete) state).isActive();
    }

    public final boolean isCompleted() {
        return !(getState$kotlinx_coroutines_core() instanceof Incomplete);
    }

    public final boolean isCancelled() {
        Object state = getState$kotlinx_coroutines_core();
        return (state instanceof CompletedExceptionally) || ((state instanceof Finishing) && ((Finishing) state).isCancelling());
    }

    private final boolean tryFinalizeFinishingState(Finishing state, Object proposedUpdate, int mode) {
        boolean wasCancelling;
        Throwable finalCause;
        boolean handled = false;
        if (!(getState$kotlinx_coroutines_core() == state)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        } else if (!(!state.isSealed())) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        } else if (state.isCompleting) {
            CompletedExceptionally completedExceptionally = (CompletedExceptionally) (!(proposedUpdate instanceof CompletedExceptionally) ? null : proposedUpdate);
            Throwable proposedException = completedExceptionally != null ? completedExceptionally.cause : null;
            synchronized (state) {
                wasCancelling = state.isCancelling();
                List exceptions = state.sealLocked(proposedException);
                finalCause = getFinalRootCause(state, exceptions);
                if (finalCause != null) {
                    addSuppressedExceptions(finalCause, exceptions);
                }
            }
            Throwable finalException = finalCause;
            Object finalState = (finalException == null || finalException == proposedException) ? proposedUpdate : new CompletedExceptionally(finalException, false, 2, (DefaultConstructorMarker) null);
            if (finalException != null) {
                if (cancelParent(finalException) || handleJobException(finalException)) {
                    handled = true;
                }
                if (handled) {
                    if (finalState != null) {
                        ((CompletedExceptionally) finalState).makeHandled();
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.CompletedExceptionally");
                    }
                }
            }
            if (!wasCancelling) {
                onCancelling(finalException);
            }
            onCompletionInternal(finalState);
            if (_state$FU.compareAndSet(this, state, JobSupportKt.boxIncomplete(finalState))) {
                completeStateFinalization(state, finalState, mode);
                return true;
            }
            throw new IllegalArgumentException(("Unexpected state: " + this._state + ", expected: " + state + ", update: " + finalState).toString());
        } else {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: java.lang.Throwable} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Throwable getFinalRootCause(kotlinx.coroutines.JobSupport.Finishing r9, java.util.List<? extends java.lang.Throwable> r10) {
        /*
            r8 = this;
            boolean r0 = r10.isEmpty()
            r1 = 0
            if (r0 == 0) goto L_0x0015
            boolean r0 = r9.isCancelling()
            if (r0 == 0) goto L_0x0014
            kotlinx.coroutines.JobCancellationException r0 = r8.createJobCancellationException()
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            return r0
        L_0x0014:
            return r1
        L_0x0015:
            r0 = r10
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            r2 = 0
            java.util.Iterator r3 = r0.iterator()
        L_0x001d:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0033
            java.lang.Object r4 = r3.next()
            r5 = r4
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            r6 = 0
            boolean r7 = r5 instanceof java.util.concurrent.CancellationException
            r5 = r7 ^ 1
            if (r5 == 0) goto L_0x001d
            r1 = r4
            goto L_0x0034
        L_0x0033:
        L_0x0034:
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            if (r1 == 0) goto L_0x0039
            goto L_0x0041
        L_0x0039:
            r0 = 0
            java.lang.Object r0 = r10.get(r0)
            r1 = r0
            java.lang.Throwable r1 = (java.lang.Throwable) r1
        L_0x0041:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.getFinalRootCause(kotlinx.coroutines.JobSupport$Finishing, java.util.List):java.lang.Throwable");
    }

    private final void addSuppressedExceptions(Throwable rootCause, List<? extends Throwable> exceptions) {
        if (exceptions.size() > 1) {
            Set seenExceptions = ConcurrentKt.identitySet(exceptions.size());
            Throwable unwrappedCause = StackTraceRecoveryKt.unwrap(rootCause);
            for (Throwable exception : exceptions) {
                Throwable unwrapped = StackTraceRecoveryKt.unwrap(exception);
                if (unwrapped != rootCause && unwrapped != unwrappedCause && !(unwrapped instanceof CancellationException) && seenExceptions.add(unwrapped)) {
                    ExceptionsKt.addSuppressed(rootCause, unwrapped);
                }
            }
        }
    }

    private final boolean tryFinalizeSimpleState(Incomplete state, Object update, int mode) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((((state instanceof Empty) || (state instanceof JobNode)) ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        if (DebugKt.getASSERTIONS_ENABLED() && ((update instanceof CompletedExceptionally) ^ 1) == 0) {
            throw new AssertionError();
        } else if (!_state$FU.compareAndSet(this, state, JobSupportKt.boxIncomplete(update))) {
            return false;
        } else {
            onCancelling((Throwable) null);
            onCompletionInternal(update);
            completeStateFinalization(state, update, mode);
            return true;
        }
    }

    private final void completeStateFinalization(Incomplete state, Object update, int mode) {
        ChildHandle it = this.parentHandle;
        if (it != null) {
            it.dispose();
            this.parentHandle = NonDisposableHandle.INSTANCE;
        }
        Throwable th = null;
        CompletedExceptionally completedExceptionally = (CompletedExceptionally) (!(update instanceof CompletedExceptionally) ? null : update);
        if (completedExceptionally != null) {
            th = completedExceptionally.cause;
        }
        Throwable cause = th;
        if (state instanceof JobNode) {
            try {
                ((JobNode) state).invoke(cause);
            } catch (Throwable ex) {
                handleOnCompletionException$kotlinx_coroutines_core(new CompletionHandlerException("Exception in completion handler " + state + " for " + this, ex));
            }
        } else {
            NodeList list = state.getList();
            if (list != null) {
                notifyCompletion(list, cause);
            }
        }
        afterCompletionInternal(update, mode);
    }

    private final void notifyCancelling(NodeList list, Throwable cause) {
        Throwable th = cause;
        onCancelling(th);
        Throwable th2 = null;
        NodeList exception$iv = list;
        Object next = exception$iv.getNext();
        if (next != null) {
            Throwable $this$apply$iv = th2;
            for (LockFreeLinkedListNode cur$iv$iv = (LockFreeLinkedListNode) next; !Intrinsics.areEqual((Object) cur$iv$iv, (Object) exception$iv); cur$iv$iv = cur$iv$iv.getNextNode()) {
                if (cur$iv$iv instanceof JobCancellingNode) {
                    JobNode node$iv = (JobNode) cur$iv$iv;
                    try {
                        node$iv.invoke(th);
                    } catch (Throwable th3) {
                        Throwable ex$iv = th3;
                        if ($this$apply$iv != null) {
                            ExceptionsKt.addSuppressed($this$apply$iv, ex$iv);
                            if ($this$apply$iv != null) {
                            }
                        }
                        $this$apply$iv = (Throwable) new CompletionHandlerException("Exception in completion handler " + node$iv + " for " + this, ex$iv);
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }
            if ($this$apply$iv != null) {
                handleOnCompletionException$kotlinx_coroutines_core($this$apply$iv);
            }
            cancelParent(th);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
    }

    private final boolean cancelParent(Throwable cause) {
        if (isScopedCoroutine()) {
            return true;
        }
        boolean isCancellation = cause instanceof CancellationException;
        ChildHandle parent = this.parentHandle;
        if (parent == null || parent == NonDisposableHandle.INSTANCE) {
            return isCancellation;
        }
        if (parent.childCancelled(cause) || isCancellation) {
            return true;
        }
        return false;
    }

    private final void notifyCompletion(NodeList $this$notifyCompletion, Throwable cause) {
        Throwable th = null;
        NodeList exception$iv = $this$notifyCompletion;
        Object next = exception$iv.getNext();
        if (next != null) {
            Throwable $this$apply$iv = th;
            for (LockFreeLinkedListNode cur$iv$iv = (LockFreeLinkedListNode) next; !Intrinsics.areEqual((Object) cur$iv$iv, (Object) exception$iv); cur$iv$iv = cur$iv$iv.getNextNode()) {
                if (cur$iv$iv instanceof JobNode) {
                    JobNode node$iv = (JobNode) cur$iv$iv;
                    try {
                        node$iv.invoke(cause);
                    } catch (Throwable th2) {
                        Throwable ex$iv = th2;
                        if ($this$apply$iv != null) {
                            ExceptionsKt.addSuppressed($this$apply$iv, ex$iv);
                            if ($this$apply$iv != null) {
                            }
                        }
                        $this$apply$iv = (Throwable) new CompletionHandlerException("Exception in completion handler " + node$iv + " for " + this, ex$iv);
                        Unit unit = Unit.INSTANCE;
                    }
                } else {
                    Throwable th3 = cause;
                }
            }
            Throwable th4 = cause;
            if ($this$apply$iv != null) {
                handleOnCompletionException$kotlinx_coroutines_core($this$apply$iv);
                return;
            }
            return;
        }
        Throwable th5 = cause;
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
    }

    private final /* synthetic */ <T extends JobNode<?>> void notifyHandlers(NodeList list, Throwable cause) {
        Throwable $this$apply = null;
        NodeList nodeList = list;
        Object next = nodeList.getNext();
        if (next != null) {
            for (LockFreeLinkedListNode cur$iv = (LockFreeLinkedListNode) next; !Intrinsics.areEqual((Object) cur$iv, (Object) nodeList); cur$iv = cur$iv.getNextNode()) {
                Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
                if (cur$iv instanceof LockFreeLinkedListNode) {
                    JobNode node = (JobNode) cur$iv;
                    try {
                        node.invoke(cause);
                    } catch (Throwable ex) {
                        if ($this$apply != null) {
                            ExceptionsKt.addSuppressed($this$apply, ex);
                            if ($this$apply != null) {
                            }
                        }
                        $this$apply = (Throwable) new CompletionHandlerException("Exception in completion handler " + node + " for " + this, ex);
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }
            if ($this$apply != null) {
                handleOnCompletionException$kotlinx_coroutines_core($this$apply);
                return;
            }
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
    }

    public final boolean start() {
        int startInternal;
        do {
            startInternal = startInternal(getState$kotlinx_coroutines_core());
            if (startInternal == 0) {
                return false;
            }
        } while (startInternal != 1);
        return true;
    }

    private final int startInternal(Object state) {
        if (state instanceof Empty) {
            if (((Empty) state).isActive()) {
                return 0;
            }
            if (!_state$FU.compareAndSet(this, state, JobSupportKt.EMPTY_ACTIVE)) {
                return -1;
            }
            onStartInternal$kotlinx_coroutines_core();
            return 1;
        } else if (!(state instanceof InactiveNodeList)) {
            return 0;
        } else {
            if (!_state$FU.compareAndSet(this, state, ((InactiveNodeList) state).getList())) {
                return -1;
            }
            onStartInternal$kotlinx_coroutines_core();
            return 1;
        }
    }

    public void onStartInternal$kotlinx_coroutines_core() {
    }

    public final CancellationException getCancellationException() {
        CancellationException cancellationException;
        Object state = getState$kotlinx_coroutines_core();
        if (state instanceof Finishing) {
            Throwable th = ((Finishing) state).rootCause;
            if (th != null && (cancellationException = toCancellationException(th, DebugStringsKt.getClassSimpleName(this) + " is cancelling")) != null) {
                return cancellationException;
            }
            throw new IllegalStateException(("Job is still new or active: " + this).toString());
        } else if (state instanceof Incomplete) {
            throw new IllegalStateException(("Job is still new or active: " + this).toString());
        } else if (state instanceof CompletedExceptionally) {
            return toCancellationException$default(this, ((CompletedExceptionally) state).cause, (String) null, 1, (Object) null);
        } else {
            return new JobCancellationException(DebugStringsKt.getClassSimpleName(this) + " has completed normally", (Throwable) null, this);
        }
    }

    public static /* synthetic */ CancellationException toCancellationException$default(JobSupport jobSupport, Throwable th, String str, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                str = null;
            }
            return jobSupport.toCancellationException(th, str);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toCancellationException");
    }

    /* access modifiers changed from: protected */
    public final CancellationException toCancellationException(Throwable $this$toCancellationException, String message) {
        Intrinsics.checkParameterIsNotNull($this$toCancellationException, "$this$toCancellationException");
        CancellationException cancellationException = (CancellationException) (!($this$toCancellationException instanceof CancellationException) ? null : $this$toCancellationException);
        if (cancellationException != null) {
            return cancellationException;
        }
        return new JobCancellationException(message != null ? message : DebugStringsKt.getClassSimpleName($this$toCancellationException) + " was cancelled", $this$toCancellationException, this);
    }

    /* access modifiers changed from: protected */
    public final Throwable getCompletionCause() {
        Object state = getState$kotlinx_coroutines_core();
        if (state instanceof Finishing) {
            Throwable th = ((Finishing) state).rootCause;
            if (th != null) {
                return th;
            }
            throw new IllegalStateException(("Job is still new or active: " + this).toString());
        } else if (state instanceof Incomplete) {
            throw new IllegalStateException(("Job is still new or active: " + this).toString());
        } else if (state instanceof CompletedExceptionally) {
            return ((CompletedExceptionally) state).cause;
        } else {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public final boolean getCompletionCauseHandled() {
        Object it = getState$kotlinx_coroutines_core();
        return (it instanceof CompletedExceptionally) && ((CompletedExceptionally) it).getHandled();
    }

    public final DisposableHandle invokeOnCompletion(Function1<? super Throwable, Unit> handler) {
        Intrinsics.checkParameterIsNotNull(handler, "handler");
        return invokeOnCompletion(false, true, handler);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00a6, code lost:
        r4 = r14;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlinx.coroutines.DisposableHandle invokeOnCompletion(boolean r18, boolean r19, kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> r20) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r3 = r20
            java.lang.String r0 = "handler"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r3, r0)
            r0 = 0
            r4 = r0
            kotlinx.coroutines.JobNode r4 = (kotlinx.coroutines.JobNode) r4
            r5 = r17
            r6 = 0
        L_0x0012:
            java.lang.Object r7 = r5.getState$kotlinx_coroutines_core()
            r8 = 0
            boolean r9 = r7 instanceof kotlinx.coroutines.Empty
            if (r9 == 0) goto L_0x004d
            r9 = r7
            kotlinx.coroutines.Empty r9 = (kotlinx.coroutines.Empty) r9
            boolean r9 = r9.isActive()
            if (r9 == 0) goto L_0x0045
            if (r4 == 0) goto L_0x002a
            r9 = r4
            goto L_0x0036
        L_0x002a:
            kotlinx.coroutines.JobNode r9 = r1.makeNode(r3, r2)
            r10 = r9
            r11 = 0
            r4 = r10
            r16 = r9
            r9 = r4
            r4 = r16
        L_0x0036:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r10 = _state$FU
            boolean r10 = r10.compareAndSet(r1, r7, r4)
            if (r10 == 0) goto L_0x0042
            r0 = r4
            kotlinx.coroutines.DisposableHandle r0 = (kotlinx.coroutines.DisposableHandle) r0
            return r0
        L_0x0042:
            r4 = r9
            goto L_0x00e7
        L_0x0045:
            r9 = r7
            kotlinx.coroutines.Empty r9 = (kotlinx.coroutines.Empty) r9
            r1.promoteEmptyToNodeList(r9)
            goto L_0x00e7
        L_0x004d:
            boolean r9 = r7 instanceof kotlinx.coroutines.Incomplete
            if (r9 == 0) goto L_0x00eb
            r9 = r7
            kotlinx.coroutines.Incomplete r9 = (kotlinx.coroutines.Incomplete) r9
            kotlinx.coroutines.NodeList r9 = r9.getList()
            if (r9 != 0) goto L_0x006c
            if (r7 == 0) goto L_0x0064
            r10 = r7
            kotlinx.coroutines.JobNode r10 = (kotlinx.coroutines.JobNode) r10
            r1.promoteSingleToNodeList(r10)
            goto L_0x00e7
        L_0x0064:
            kotlin.TypeCastException r0 = new kotlin.TypeCastException
            java.lang.String r10 = "null cannot be cast to non-null type kotlinx.coroutines.JobNode<*>"
            r0.<init>(r10)
            throw r0
        L_0x006c:
            r10 = r0
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            kotlinx.coroutines.NonDisposableHandle r11 = kotlinx.coroutines.NonDisposableHandle.INSTANCE
            kotlinx.coroutines.DisposableHandle r11 = (kotlinx.coroutines.DisposableHandle) r11
            if (r2 == 0) goto L_0x00bf
            boolean r12 = r7 instanceof kotlinx.coroutines.JobSupport.Finishing
            if (r12 == 0) goto L_0x00bf
            r12 = 0
            monitor-enter(r7)
            r13 = 0
            r14 = r7
            kotlinx.coroutines.JobSupport$Finishing r14 = (kotlinx.coroutines.JobSupport.Finishing) r14     // Catch:{ all -> 0x00bc }
            java.lang.Throwable r14 = r14.rootCause     // Catch:{ all -> 0x00bc }
            r10 = r14
            if (r10 == 0) goto L_0x0092
            r14 = r20
            r15 = 0
            boolean r0 = r14 instanceof kotlinx.coroutines.ChildHandleNode     // Catch:{ all -> 0x00bc }
            if (r0 == 0) goto L_0x00b4
            r0 = r7
            kotlinx.coroutines.JobSupport$Finishing r0 = (kotlinx.coroutines.JobSupport.Finishing) r0     // Catch:{ all -> 0x00bc }
            boolean r0 = r0.isCompleting     // Catch:{ all -> 0x00bc }
            if (r0 != 0) goto L_0x00b4
        L_0x0092:
            if (r4 == 0) goto L_0x0096
            r14 = r4
            goto L_0x009e
        L_0x0096:
            kotlinx.coroutines.JobNode r0 = r1.makeNode(r3, r2)     // Catch:{ all -> 0x00bc }
            r14 = r0
            r15 = 0
            r4 = r14
            r4 = r0
        L_0x009e:
            r0 = r4
            boolean r4 = r1.addLastAtomic(r7, r9, r0)     // Catch:{ all -> 0x00b9 }
            if (r4 != 0) goto L_0x00a8
            monitor-exit(r7)
            r4 = r14
            goto L_0x00e7
        L_0x00a8:
            if (r10 != 0) goto L_0x00af
            r4 = r0
            kotlinx.coroutines.DisposableHandle r4 = (kotlinx.coroutines.DisposableHandle) r4     // Catch:{ all -> 0x00b9 }
            monitor-exit(r7)
            return r4
        L_0x00af:
            r4 = r0
            kotlinx.coroutines.DisposableHandle r4 = (kotlinx.coroutines.DisposableHandle) r4     // Catch:{ all -> 0x00b9 }
            r11 = r4
            r4 = r14
        L_0x00b4:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00bc }
            monitor-exit(r7)
            goto L_0x00bf
        L_0x00b9:
            r0 = move-exception
            r4 = r14
            goto L_0x00bd
        L_0x00bc:
            r0 = move-exception
        L_0x00bd:
            monitor-exit(r7)
            throw r0
        L_0x00bf:
            if (r10 == 0) goto L_0x00cb
            if (r19 == 0) goto L_0x00ca
            r0 = r10
            r12 = r20
            r13 = 0
            r12.invoke(r0)
        L_0x00ca:
            return r11
        L_0x00cb:
            if (r4 == 0) goto L_0x00cf
            r0 = r4
            goto L_0x00db
        L_0x00cf:
            kotlinx.coroutines.JobNode r0 = r1.makeNode(r3, r2)
            r12 = r0
            r13 = 0
            r4 = r12
            r16 = r4
            r4 = r0
            r0 = r16
        L_0x00db:
            boolean r12 = r1.addLastAtomic(r7, r9, r4)
            if (r12 == 0) goto L_0x00e5
            r12 = r4
            kotlinx.coroutines.DisposableHandle r12 = (kotlinx.coroutines.DisposableHandle) r12
            return r12
        L_0x00e5:
            r4 = r0
        L_0x00e7:
            r0 = 0
            goto L_0x0012
        L_0x00eb:
            if (r19 == 0) goto L_0x0102
            boolean r0 = r7 instanceof kotlinx.coroutines.CompletedExceptionally
            if (r0 != 0) goto L_0x00f3
            r0 = 0
            goto L_0x00f4
        L_0x00f3:
            r0 = r7
        L_0x00f4:
            kotlinx.coroutines.CompletedExceptionally r0 = (kotlinx.coroutines.CompletedExceptionally) r0
            if (r0 == 0) goto L_0x00fb
            java.lang.Throwable r0 = r0.cause
            goto L_0x00fc
        L_0x00fb:
            r0 = 0
        L_0x00fc:
            r9 = r20
            r10 = 0
            r9.invoke(r0)
        L_0x0102:
            kotlinx.coroutines.NonDisposableHandle r0 = kotlinx.coroutines.NonDisposableHandle.INSTANCE
            kotlinx.coroutines.DisposableHandle r0 = (kotlinx.coroutines.DisposableHandle) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.invokeOnCompletion(boolean, boolean, kotlin.jvm.functions.Function1):kotlinx.coroutines.DisposableHandle");
    }

    private final JobNode<?> makeNode(Function1<? super Throwable, Unit> handler, boolean onCancelling) {
        boolean z = true;
        JobNode jobNode = null;
        if (onCancelling) {
            if (handler instanceof JobCancellingNode) {
                jobNode = handler;
            }
            JobCancellingNode it = jobNode;
            if (it != null) {
                if (it.job != this) {
                    z = false;
                }
                if (!z) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                } else if (it != null) {
                    return it;
                }
            }
            return new InvokeOnCancelling(this, handler);
        }
        if (handler instanceof JobNode) {
            jobNode = handler;
        }
        JobNode jobNode2 = jobNode;
        if (jobNode2 != null) {
            JobNode it2 = jobNode2;
            if (it2.job != this || (it2 instanceof JobCancellingNode)) {
                z = false;
            }
            if (!z) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            } else if (jobNode2 != null) {
                return jobNode2;
            }
        }
        return new InvokeOnCompletion(this, handler);
    }

    private final boolean addLastAtomic(Object expect, NodeList list, JobNode<?> node) {
        int tryCondAddNext;
        NodeList nodeList = list;
        NodeList nodeList2 = nodeList;
        LockFreeLinkedListNode.CondAddOp condAdd$iv = new JobSupport$addLastAtomic$$inlined$addLastIf$1(node, node, this, expect);
        do {
            Object prev = nodeList.getPrev();
            if (prev != null) {
                tryCondAddNext = ((LockFreeLinkedListNode) prev).tryCondAddNext(node, nodeList, condAdd$iv);
                if (tryCondAddNext == 1) {
                    return true;
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
            }
        } while (tryCondAddNext != 2);
        return false;
    }

    private final void promoteEmptyToNodeList(Empty state) {
        NodeList list = new NodeList();
        _state$FU.compareAndSet(this, state, state.isActive() ? list : new InactiveNodeList(list));
    }

    private final void promoteSingleToNodeList(JobNode<?> state) {
        state.addOneIfEmpty(new NodeList());
        _state$FU.compareAndSet(this, state, state.getNextNode());
    }

    public final Object join(Continuation<? super Unit> $completion) {
        if (joinInternal()) {
            return joinSuspend($completion);
        }
        YieldKt.checkCompletion($completion.getContext());
        return Unit.INSTANCE;
    }

    private final boolean joinInternal() {
        Object state;
        do {
            state = getState$kotlinx_coroutines_core();
            if (!(state instanceof Incomplete)) {
                return false;
            }
        } while (startInternal(state) < 0);
        return true;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object joinSuspend(Continuation<? super Unit> $completion) {
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        CancellableContinuation cont = cancellable$iv;
        CancellableContinuationKt.disposeOnCancellation(cont, invokeOnCompletion(new ResumeOnCompletion(this, cont)));
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result;
    }

    public final SelectClause0 getOnJoin() {
        return this;
    }

    public final <R> void registerSelectClause0(SelectInstance<? super R> select, Function1<? super Continuation<? super R>, ? extends Object> block) {
        Object state;
        Intrinsics.checkParameterIsNotNull(select, "select");
        Intrinsics.checkParameterIsNotNull(block, "block");
        do {
            state = getState$kotlinx_coroutines_core();
            if (!select.isSelected()) {
                if (!(state instanceof Incomplete)) {
                    if (select.trySelect((Object) null)) {
                        UndispatchedKt.startCoroutineUnintercepted(block, select.getCompletion());
                        return;
                    }
                    return;
                }
            } else {
                return;
            }
        } while (startInternal(state) != 0);
        select.disposeOnSelect(invokeOnCompletion(new SelectJoinOnCompletion(this, select, block)));
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0024 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x0012 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void removeNode$kotlinx_coroutines_core(kotlinx.coroutines.JobNode<?> r7) {
        /*
            r6 = this;
            java.lang.String r0 = "node"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r7, r0)
            r0 = r6
            r1 = 0
        L_0x0007:
            java.lang.Object r2 = r0.getState$kotlinx_coroutines_core()
            r3 = 0
            boolean r4 = r2 instanceof kotlinx.coroutines.JobNode
            if (r4 == 0) goto L_0x0024
            if (r2 == r7) goto L_0x0015
            return
        L_0x0015:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r4 = _state$FU
            kotlinx.coroutines.Empty r5 = kotlinx.coroutines.JobSupportKt.EMPTY_ACTIVE
            boolean r4 = r4.compareAndSet(r6, r2, r5)
            if (r4 == 0) goto L_0x0022
            return
        L_0x0022:
            goto L_0x0007
        L_0x0024:
            boolean r4 = r2 instanceof kotlinx.coroutines.Incomplete
            if (r4 == 0) goto L_0x0035
            r4 = r2
            kotlinx.coroutines.Incomplete r4 = (kotlinx.coroutines.Incomplete) r4
            kotlinx.coroutines.NodeList r4 = r4.getList()
            if (r4 == 0) goto L_0x0034
            r7.remove()
        L_0x0034:
            return
        L_0x0035:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.removeNode$kotlinx_coroutines_core(kotlinx.coroutines.JobNode):void");
    }

    public boolean getOnCancelComplete$kotlinx_coroutines_core() {
        return false;
    }

    public void cancel(CancellationException cause) {
        cancel(cause);
    }

    /* renamed from: cancelInternal */
    public boolean cancel(Throwable cause) {
        return cancelImpl$kotlinx_coroutines_core(cause) && getHandlesException$kotlinx_coroutines_core();
    }

    public final void parentCancelled(ParentJob parentJob) {
        Intrinsics.checkParameterIsNotNull(parentJob, "parentJob");
        cancelImpl$kotlinx_coroutines_core(parentJob);
    }

    public boolean childCancelled(Throwable cause) {
        Intrinsics.checkParameterIsNotNull(cause, "cause");
        if (cause instanceof CancellationException) {
            return true;
        }
        if (!cancelImpl$kotlinx_coroutines_core(cause) || !getHandlesException$kotlinx_coroutines_core()) {
            return false;
        }
        return true;
    }

    public final boolean cancelCoroutine(Throwable cause) {
        return cancelImpl$kotlinx_coroutines_core(cause);
    }

    public final boolean cancelImpl$kotlinx_coroutines_core(Object cause) {
        if (!getOnCancelComplete$kotlinx_coroutines_core() || !cancelMakeCompleting(cause)) {
            return makeCancelling(cause);
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0044, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean cancelMakeCompleting(java.lang.Object r10) {
        /*
            r9 = this;
            r0 = r9
            r1 = 0
        L_0x0002:
            java.lang.Object r2 = r0.getState$kotlinx_coroutines_core()
            r3 = 0
            boolean r4 = r2 instanceof kotlinx.coroutines.Incomplete
            r5 = 0
            if (r4 == 0) goto L_0x0044
            boolean r4 = r2 instanceof kotlinx.coroutines.JobSupport.Finishing
            if (r4 == 0) goto L_0x0019
            r4 = r2
            kotlinx.coroutines.JobSupport$Finishing r4 = (kotlinx.coroutines.JobSupport.Finishing) r4
            boolean r4 = r4.isCompleting
            if (r4 == 0) goto L_0x0019
            goto L_0x0044
        L_0x0019:
            kotlinx.coroutines.CompletedExceptionally r4 = new kotlinx.coroutines.CompletedExceptionally
            java.lang.Throwable r6 = r9.createCauseException(r10)
            r7 = 0
            r8 = 2
            r4.<init>(r6, r5, r8, r7)
            int r6 = r9.tryMakeCompleting(r2, r4, r5)
            if (r6 == 0) goto L_0x0043
            r5 = 1
            if (r6 == r5) goto L_0x0042
            if (r6 == r8) goto L_0x0042
            r5 = 3
            if (r6 != r5) goto L_0x0034
            goto L_0x0002
        L_0x0034:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "unexpected result"
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            throw r5
        L_0x0042:
            return r5
        L_0x0043:
            return r5
        L_0x0044:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.cancelMakeCompleting(java.lang.Object):boolean");
    }

    private final JobCancellationException createJobCancellationException() {
        return new JobCancellationException("Job was cancelled", (Throwable) null, this);
    }

    public CancellationException getChildJobCancellationCause() {
        Throwable rootCause;
        Object state = getState$kotlinx_coroutines_core();
        CancellationException cancellationException = null;
        if (state instanceof Finishing) {
            rootCause = ((Finishing) state).rootCause;
        } else if (state instanceof CompletedExceptionally) {
            rootCause = ((CompletedExceptionally) state).cause;
        } else if (!(state instanceof Incomplete)) {
            rootCause = null;
        } else {
            throw new IllegalStateException(("Cannot be cancelling child in this state: " + state).toString());
        }
        if (rootCause instanceof CancellationException) {
            cancellationException = rootCause;
        }
        CancellationException cancellationException2 = cancellationException;
        return cancellationException2 != null ? cancellationException2 : new JobCancellationException("Parent job is " + stateString(state), rootCause, this);
    }

    private final Throwable createCauseException(Object cause) {
        if (cause != null ? cause instanceof Throwable : true) {
            return cause != null ? (Throwable) cause : createJobCancellationException();
        }
        if (cause != null) {
            return ((ParentJob) cause).getChildJobCancellationCause();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.ParentJob");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0054, code lost:
        if (r0 == null) goto L_0x0062;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0056, code lost:
        notifyCancelling(((kotlinx.coroutines.JobSupport.Finishing) r5).getList(), r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0062, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean makeCancelling(java.lang.Object r17) {
        /*
            r16 = this;
            r1 = r16
            r0 = 0
            r2 = r0
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            r3 = r16
            r4 = 0
        L_0x0009:
            java.lang.Object r5 = r3.getState$kotlinx_coroutines_core()
            r6 = 0
            boolean r7 = r5 instanceof kotlinx.coroutines.JobSupport.Finishing
            r8 = 1
            r9 = 0
            if (r7 == 0) goto L_0x0069
            r7 = 0
            monitor-enter(r5)
            r10 = 0
            r11 = r5
            kotlinx.coroutines.JobSupport$Finishing r11 = (kotlinx.coroutines.JobSupport.Finishing) r11     // Catch:{ all -> 0x0066 }
            boolean r11 = r11.isSealed()     // Catch:{ all -> 0x0066 }
            if (r11 == 0) goto L_0x0024
            monitor-exit(r5)
            return r9
        L_0x0024:
            r11 = r5
            kotlinx.coroutines.JobSupport$Finishing r11 = (kotlinx.coroutines.JobSupport.Finishing) r11     // Catch:{ all -> 0x0066 }
            boolean r11 = r11.isCancelling()     // Catch:{ all -> 0x0066 }
            if (r17 != 0) goto L_0x002f
            if (r11 != 0) goto L_0x0044
        L_0x002f:
            if (r2 == 0) goto L_0x0033
            r12 = r2
            goto L_0x003d
        L_0x0033:
            java.lang.Throwable r12 = r16.createCauseException(r17)     // Catch:{ all -> 0x0066 }
            r13 = r12
            r14 = 0
            r2 = r13
            r15 = r12
            r12 = r2
            r2 = r15
        L_0x003d:
            r13 = r5
            kotlinx.coroutines.JobSupport$Finishing r13 = (kotlinx.coroutines.JobSupport.Finishing) r13     // Catch:{ all -> 0x0063 }
            r13.addExceptionLocked(r2)     // Catch:{ all -> 0x0063 }
            r2 = r12
        L_0x0044:
            r12 = r5
            kotlinx.coroutines.JobSupport$Finishing r12 = (kotlinx.coroutines.JobSupport.Finishing) r12     // Catch:{ all -> 0x0066 }
            java.lang.Throwable r12 = r12.rootCause     // Catch:{ all -> 0x0066 }
            r13 = r12
            r14 = 0
            if (r11 != 0) goto L_0x004e
            r9 = r8
        L_0x004e:
            if (r9 == 0) goto L_0x0051
            r0 = r12
        L_0x0051:
            monitor-exit(r5)
            if (r0 == 0) goto L_0x0062
            r7 = r0
            r9 = 0
            r10 = r5
            kotlinx.coroutines.JobSupport$Finishing r10 = (kotlinx.coroutines.JobSupport.Finishing) r10
            kotlinx.coroutines.NodeList r10 = r10.getList()
            r1.notifyCancelling(r10, r7)
        L_0x0062:
            return r8
        L_0x0063:
            r0 = move-exception
            r2 = r12
            goto L_0x0067
        L_0x0066:
            r0 = move-exception
        L_0x0067:
            monitor-exit(r5)
            throw r0
        L_0x0069:
            boolean r7 = r5 instanceof kotlinx.coroutines.Incomplete
            if (r7 == 0) goto L_0x00d4
            if (r2 == 0) goto L_0x0071
            r7 = r2
            goto L_0x007b
        L_0x0071:
            java.lang.Throwable r7 = r16.createCauseException(r17)
            r10 = r7
            r11 = 0
            r2 = r10
            r15 = r7
            r7 = r2
            r2 = r15
        L_0x007b:
            r10 = r5
            kotlinx.coroutines.Incomplete r10 = (kotlinx.coroutines.Incomplete) r10
            boolean r10 = r10.isActive()
            if (r10 == 0) goto L_0x008e
            r9 = r5
            kotlinx.coroutines.Incomplete r9 = (kotlinx.coroutines.Incomplete) r9
            boolean r9 = r1.tryMakeCancelling(r9, r2)
            if (r9 == 0) goto L_0x00a2
            return r8
        L_0x008e:
            kotlinx.coroutines.CompletedExceptionally r10 = new kotlinx.coroutines.CompletedExceptionally
            r11 = 2
            r10.<init>(r2, r9, r11, r0)
            int r9 = r1.tryMakeCompleting(r5, r10, r9)
            if (r9 == 0) goto L_0x00b5
            if (r9 == r8) goto L_0x00b4
            if (r9 == r11) goto L_0x00b4
            r8 = 3
            if (r9 != r8) goto L_0x00a6
        L_0x00a2:
            r2 = r7
            goto L_0x0009
        L_0x00a6:
            java.lang.String r0 = "unexpected result"
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = r0.toString()
            r8.<init>(r0)
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            throw r8
        L_0x00b4:
            return r8
        L_0x00b5:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r8 = "Cannot happen in "
            java.lang.StringBuilder r0 = r0.append(r8)
            java.lang.StringBuilder r0 = r0.append(r5)
            java.lang.String r0 = r0.toString()
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = r0.toString()
            r8.<init>(r0)
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            throw r8
        L_0x00d4:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.makeCancelling(java.lang.Object):boolean");
    }

    private final NodeList getOrPromoteCancellingList(Incomplete state) {
        NodeList list = state.getList();
        if (list != null) {
            return list;
        }
        if (state instanceof Empty) {
            return new NodeList();
        }
        if (state instanceof JobNode) {
            promoteSingleToNodeList((JobNode) state);
            return null;
        }
        throw new IllegalStateException(("State should have list: " + state).toString());
    }

    private final boolean tryMakeCancelling(Incomplete state, Throwable rootCause) {
        if (DebugKt.getASSERTIONS_ENABLED() && ((state instanceof Finishing) ^ 1) == 0) {
            throw new AssertionError();
        } else if (!DebugKt.getASSERTIONS_ENABLED() || state.isActive() != 0) {
            NodeList list = getOrPromoteCancellingList(state);
            if (list == null) {
                return false;
            }
            if (!_state$FU.compareAndSet(this, state, new Finishing(list, false, rootCause))) {
                return false;
            }
            notifyCancelling(list, rootCause);
            return true;
        } else {
            throw new AssertionError();
        }
    }

    public final boolean makeCompleting$kotlinx_coroutines_core(Object proposedUpdate) {
        int tryMakeCompleting;
        do {
            tryMakeCompleting = tryMakeCompleting(getState$kotlinx_coroutines_core(), proposedUpdate, 0);
            if (tryMakeCompleting == 0) {
                return false;
            }
            if (tryMakeCompleting == 1 || tryMakeCompleting == 2) {
                return true;
            }
        } while (tryMakeCompleting == 3);
        throw new IllegalStateException("unexpected result".toString());
    }

    public final boolean makeCompletingOnce$kotlinx_coroutines_core(Object proposedUpdate, int mode) {
        int tryMakeCompleting;
        do {
            tryMakeCompleting = tryMakeCompleting(getState$kotlinx_coroutines_core(), proposedUpdate, mode);
            if (tryMakeCompleting == 0) {
                throw new IllegalStateException("Job " + this + " is already complete or completing, " + "but is being completed with " + proposedUpdate, getExceptionOrNull(proposedUpdate));
            } else if (tryMakeCompleting == 1) {
                return true;
            } else {
                if (tryMakeCompleting == 2) {
                    return false;
                }
            }
        } while (tryMakeCompleting == 3);
        throw new IllegalStateException("unexpected result".toString());
    }

    private final int tryMakeCompleting(Object state, Object proposedUpdate, int mode) {
        if (!(state instanceof Incomplete)) {
            return 0;
        }
        if ((!(state instanceof Empty) && !(state instanceof JobNode)) || (state instanceof ChildHandleNode) || (proposedUpdate instanceof CompletedExceptionally)) {
            return tryMakeCompletingSlowPath((Incomplete) state, proposedUpdate, mode);
        }
        if (!tryFinalizeSimpleState((Incomplete) state, proposedUpdate, mode)) {
            return 3;
        }
        return 1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0069, code lost:
        if (r9 == null) goto L_0x0070;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x006b, code lost:
        notifyCancelling(r5, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0070, code lost:
        r0 = firstChild(r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0074, code lost:
        if (r0 == null) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x007a, code lost:
        if (tryWaitForChild(r8, r0, r3) == false) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x007c, code lost:
        return 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0084, code lost:
        if (tryFinalizeFinishingState(r8, r3, r19) == false) goto L_0x0087;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0086, code lost:
        return 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0087, code lost:
        return 3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int tryMakeCompletingSlowPath(kotlinx.coroutines.Incomplete r17, java.lang.Object r18, int r19) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r3 = r18
            kotlinx.coroutines.NodeList r0 = r16.getOrPromoteCancellingList(r17)
            r4 = 3
            if (r0 == 0) goto L_0x009f
            r5 = r0
            boolean r0 = r2 instanceof kotlinx.coroutines.JobSupport.Finishing
            r6 = 0
            if (r0 != 0) goto L_0x0015
            r0 = r6
            goto L_0x0016
        L_0x0015:
            r0 = r2
        L_0x0016:
            kotlinx.coroutines.JobSupport$Finishing r0 = (kotlinx.coroutines.JobSupport.Finishing) r0
            r7 = 0
            if (r0 == 0) goto L_0x001c
            goto L_0x0021
        L_0x001c:
            kotlinx.coroutines.JobSupport$Finishing r0 = new kotlinx.coroutines.JobSupport$Finishing
            r0.<init>(r5, r7, r6)
        L_0x0021:
            r8 = r0
            r9 = r6
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            r10 = 0
            monitor-enter(r8)
            r0 = 0
            boolean r11 = r8.isCompleting     // Catch:{ all -> 0x009a }
            if (r11 == 0) goto L_0x002e
            monitor-exit(r8)
            return r7
        L_0x002e:
            r11 = 1
            r8.isCompleting = r11     // Catch:{ all -> 0x009a }
            if (r8 == r2) goto L_0x003d
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r12 = _state$FU     // Catch:{ all -> 0x009a }
            boolean r12 = r12.compareAndSet(r1, r2, r8)     // Catch:{ all -> 0x009a }
            if (r12 != 0) goto L_0x003d
            monitor-exit(r8)
            return r4
        L_0x003d:
            boolean r12 = r8.isSealed()     // Catch:{ all -> 0x009a }
            r12 = r12 ^ r11
            if (r12 == 0) goto L_0x0088
            boolean r12 = r8.isCancelling()     // Catch:{ all -> 0x009a }
            boolean r13 = r3 instanceof kotlinx.coroutines.CompletedExceptionally     // Catch:{ all -> 0x009a }
            if (r13 != 0) goto L_0x004e
            r13 = r6
            goto L_0x004f
        L_0x004e:
            r13 = r3
        L_0x004f:
            kotlinx.coroutines.CompletedExceptionally r13 = (kotlinx.coroutines.CompletedExceptionally) r13     // Catch:{ all -> 0x009a }
            if (r13 == 0) goto L_0x0059
            r14 = 0
            java.lang.Throwable r15 = r13.cause     // Catch:{ all -> 0x009a }
            r8.addExceptionLocked(r15)     // Catch:{ all -> 0x009a }
        L_0x0059:
            java.lang.Throwable r13 = r8.rootCause     // Catch:{ all -> 0x009a }
            r14 = r13
            r15 = 0
            if (r12 != 0) goto L_0x0060
            r7 = r11
        L_0x0060:
            if (r7 == 0) goto L_0x0063
            r6 = r13
        L_0x0063:
            r9 = r6
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x009a }
            monitor-exit(r8)
            if (r9 == 0) goto L_0x0070
            r0 = r9
            r6 = 0
            r1.notifyCancelling(r5, r0)
        L_0x0070:
            kotlinx.coroutines.ChildHandleNode r0 = r16.firstChild(r17)
            if (r0 == 0) goto L_0x007e
            boolean r6 = r1.tryWaitForChild(r8, r0, r3)
            if (r6 == 0) goto L_0x007e
            r4 = 2
            return r4
        L_0x007e:
            r6 = r19
            boolean r7 = r1.tryFinalizeFinishingState(r8, r3, r6)
            if (r7 == 0) goto L_0x0087
            return r11
        L_0x0087:
            return r4
        L_0x0088:
            r6 = r19
            java.lang.String r4 = "Failed requirement."
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x0098 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0098 }
            r7.<init>(r4)     // Catch:{ all -> 0x0098 }
            java.lang.Throwable r7 = (java.lang.Throwable) r7     // Catch:{ all -> 0x0098 }
            throw r7     // Catch:{ all -> 0x0098 }
        L_0x0098:
            r0 = move-exception
            goto L_0x009d
        L_0x009a:
            r0 = move-exception
            r6 = r19
        L_0x009d:
            monitor-exit(r8)
            throw r0
        L_0x009f:
            r6 = r19
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.tryMakeCompletingSlowPath(kotlinx.coroutines.Incomplete, java.lang.Object, int):int");
    }

    private final Throwable getExceptionOrNull(Object $this$exceptionOrNull) {
        CompletedExceptionally completedExceptionally = (CompletedExceptionally) (!($this$exceptionOrNull instanceof CompletedExceptionally) ? null : $this$exceptionOrNull);
        if (completedExceptionally != null) {
            return completedExceptionally.cause;
        }
        return null;
    }

    private final ChildHandleNode firstChild(Incomplete state) {
        ChildHandleNode childHandleNode = (ChildHandleNode) (!(state instanceof ChildHandleNode) ? null : state);
        if (childHandleNode != null) {
            return childHandleNode;
        }
        NodeList list = state.getList();
        if (list != null) {
            return nextChild(list);
        }
        return null;
    }

    private final boolean tryWaitForChild(Finishing state, ChildHandleNode child, Object proposedUpdate) {
        while (Job.DefaultImpls.invokeOnCompletion$default(child.childJob, false, false, new ChildCompletion(this, state, child, proposedUpdate), 1, (Object) null) == NonDisposableHandle.INSTANCE) {
            ChildHandleNode nextChild = nextChild(child);
            if (nextChild == null) {
                return false;
            }
            child = nextChild;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public final void continueCompleting(Finishing state, ChildHandleNode lastChild, Object proposedUpdate) {
        if (getState$kotlinx_coroutines_core() == state) {
            ChildHandleNode waitChild = nextChild(lastChild);
            if ((waitChild != null && tryWaitForChild(state, waitChild, proposedUpdate)) || !tryFinalizeFinishingState(state, proposedUpdate, 0)) {
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    private final ChildHandleNode nextChild(LockFreeLinkedListNode $this$nextChild) {
        LockFreeLinkedListNode cur = $this$nextChild;
        while (cur.isRemoved()) {
            cur = cur.getPrevNode();
        }
        while (true) {
            cur = cur.getNextNode();
            if (!cur.isRemoved()) {
                if (cur instanceof ChildHandleNode) {
                    return (ChildHandleNode) cur;
                }
                if (cur instanceof NodeList) {
                    return null;
                }
            }
        }
    }

    public final Sequence<Job> getChildren() {
        return SequencesKt.sequence(new JobSupport$children$1(this, (Continuation) null));
    }

    public final ChildHandle attachChild(ChildJob child) {
        Intrinsics.checkParameterIsNotNull(child, "child");
        DisposableHandle invokeOnCompletion$default = Job.DefaultImpls.invokeOnCompletion$default(this, true, false, new ChildHandleNode(this, child), 2, (Object) null);
        if (invokeOnCompletion$default != null) {
            return (ChildHandle) invokeOnCompletion$default;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.ChildHandle");
    }

    public void handleOnCompletionException$kotlinx_coroutines_core(Throwable exception) {
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        throw exception;
    }

    /* access modifiers changed from: protected */
    public void onCancelling(Throwable cause) {
    }

    /* access modifiers changed from: protected */
    public boolean isScopedCoroutine() {
        return false;
    }

    public boolean getHandlesException$kotlinx_coroutines_core() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean handleJobException(Throwable exception) {
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        return false;
    }

    /* access modifiers changed from: protected */
    public void onCompletionInternal(Object state) {
    }

    /* access modifiers changed from: protected */
    public void afterCompletionInternal(Object state, int mode) {
    }

    public String toString() {
        return toDebugString() + '@' + DebugStringsKt.getHexAddress(this);
    }

    public final String toDebugString() {
        return nameString$kotlinx_coroutines_core() + '{' + stateString(getState$kotlinx_coroutines_core()) + '}';
    }

    public String nameString$kotlinx_coroutines_core() {
        return DebugStringsKt.getClassSimpleName(this);
    }

    private final String stateString(Object state) {
        if (state instanceof Finishing) {
            if (((Finishing) state).isCancelling()) {
                return "Cancelling";
            }
            if (((Finishing) state).isCompleting) {
                return "Completing";
            }
            return "Active";
        } else if (state instanceof Incomplete) {
            if (((Incomplete) state).isActive()) {
                return "Active";
            }
            return "New";
        } else if (state instanceof CompletedExceptionally) {
            return "Cancelled";
        } else {
            return "Completed";
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0003\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u00022\u00020\u0003B\u001f\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\tJ\u0018\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u00020\t0\u0016j\b\u0012\u0004\u0012\u00020\t`\u0017H\u0002J\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\tJ\b\u0010\u001b\u001a\u00020\u001cH\u0016R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0001X\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\u00078VX\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\rR\u0012\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\u0014\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\b\u001a\u0004\u0018\u00010\t8\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"}, mo33671d2 = {"Lkotlinx/coroutines/JobSupport$Finishing;", "", "Lkotlinx/coroutines/internal/SynchronizedObject;", "Lkotlinx/coroutines/Incomplete;", "list", "Lkotlinx/coroutines/NodeList;", "isCompleting", "", "rootCause", "", "(Lkotlinx/coroutines/NodeList;ZLjava/lang/Throwable;)V", "_exceptionsHolder", "isActive", "()Z", "isCancelling", "isSealed", "getList", "()Lkotlinx/coroutines/NodeList;", "addExceptionLocked", "", "exception", "allocateList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "sealLocked", "", "proposedException", "toString", "", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: JobSupport.kt */
    private static final class Finishing implements Incomplete {
        private volatile Object _exceptionsHolder;
        public volatile boolean isCompleting;
        private final NodeList list;
        public volatile Throwable rootCause;

        public Finishing(NodeList list2, boolean isCompleting2, Throwable rootCause2) {
            Intrinsics.checkParameterIsNotNull(list2, "list");
            this.list = list2;
            this.isCompleting = isCompleting2;
            this.rootCause = rootCause2;
        }

        public NodeList getList() {
            return this.list;
        }

        public final boolean isSealed() {
            return this._exceptionsHolder == JobSupportKt.SEALED;
        }

        public final boolean isCancelling() {
            return this.rootCause != null;
        }

        public boolean isActive() {
            return this.rootCause == null;
        }

        public final List<Throwable> sealLocked(Throwable proposedException) {
            ArrayList it;
            Object eh = this._exceptionsHolder;
            if (eh == null) {
                it = allocateList();
            } else if (eh instanceof Throwable) {
                it = allocateList();
                it.add(eh);
            } else if (eh instanceof ArrayList) {
                it = (ArrayList) eh;
            } else {
                throw new IllegalStateException(("State is " + eh).toString());
            }
            ArrayList list2 = it;
            Throwable rootCause2 = this.rootCause;
            if (rootCause2 != null) {
                list2.add(0, rootCause2);
            }
            if (proposedException != null && (!Intrinsics.areEqual((Object) proposedException, (Object) rootCause2))) {
                list2.add(proposedException);
            }
            this._exceptionsHolder = JobSupportKt.SEALED;
            return list2;
        }

        public final void addExceptionLocked(Throwable exception) {
            Intrinsics.checkParameterIsNotNull(exception, "exception");
            Throwable rootCause2 = this.rootCause;
            if (rootCause2 == null) {
                this.rootCause = exception;
            } else if (exception != rootCause2) {
                Object eh = this._exceptionsHolder;
                if (eh == null) {
                    this._exceptionsHolder = exception;
                } else if (eh instanceof Throwable) {
                    if (exception != eh) {
                        ArrayList allocateList = allocateList();
                        ArrayList $this$apply = allocateList;
                        $this$apply.add(eh);
                        $this$apply.add(exception);
                        this._exceptionsHolder = allocateList;
                    }
                } else if (eh instanceof ArrayList) {
                    ((ArrayList) eh).add(exception);
                } else {
                    throw new IllegalStateException(("State is " + eh).toString());
                }
            }
        }

        private final ArrayList<Throwable> allocateList() {
            return new ArrayList<>(4);
        }

        public String toString() {
            return "Finishing[cancelling=" + isCancelling() + ", completing=" + this.isCompleting + ", rootCause=" + this.rootCause + ", exceptions=" + this._exceptionsHolder + ", list=" + getList() + ']';
        }
    }

    private final boolean isCancelling(Incomplete $this$isCancelling) {
        return ($this$isCancelling instanceof Finishing) && ((Finishing) $this$isCancelling).isCancelling();
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B'\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, mo33671d2 = {"Lkotlinx/coroutines/JobSupport$ChildCompletion;", "Lkotlinx/coroutines/JobNode;", "Lkotlinx/coroutines/Job;", "parent", "Lkotlinx/coroutines/JobSupport;", "state", "Lkotlinx/coroutines/JobSupport$Finishing;", "child", "Lkotlinx/coroutines/ChildHandleNode;", "proposedUpdate", "", "(Lkotlinx/coroutines/JobSupport;Lkotlinx/coroutines/JobSupport$Finishing;Lkotlinx/coroutines/ChildHandleNode;Ljava/lang/Object;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: JobSupport.kt */
    private static final class ChildCompletion extends JobNode<Job> {
        private final ChildHandleNode child;
        private final JobSupport parent;
        private final Object proposedUpdate;
        private final Finishing state;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ChildCompletion(JobSupport parent2, Finishing state2, ChildHandleNode child2, Object proposedUpdate2) {
            super(child2.childJob);
            Intrinsics.checkParameterIsNotNull(parent2, "parent");
            Intrinsics.checkParameterIsNotNull(state2, "state");
            Intrinsics.checkParameterIsNotNull(child2, "child");
            this.parent = parent2;
            this.state = state2;
            this.child = child2;
            this.proposedUpdate = proposedUpdate2;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return Unit.INSTANCE;
        }

        public void invoke(Throwable cause) {
            this.parent.continueCompleting(this.state, this.child, this.proposedUpdate);
        }

        public String toString() {
            return "ChildCompletion[" + this.child + ", " + this.proposedUpdate + ']';
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, mo33671d2 = {"Lkotlinx/coroutines/JobSupport$AwaitContinuation;", "T", "Lkotlinx/coroutines/CancellableContinuationImpl;", "delegate", "Lkotlin/coroutines/Continuation;", "job", "Lkotlinx/coroutines/JobSupport;", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/JobSupport;)V", "getContinuationCancellationCause", "", "parent", "Lkotlinx/coroutines/Job;", "nameString", "", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: JobSupport.kt */
    private static final class AwaitContinuation<T> extends CancellableContinuationImpl<T> {
        private final JobSupport job;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public AwaitContinuation(Continuation<? super T> delegate, JobSupport job2) {
            super(delegate, 1);
            Intrinsics.checkParameterIsNotNull(delegate, "delegate");
            Intrinsics.checkParameterIsNotNull(job2, "job");
            this.job = job2;
        }

        public Throwable getContinuationCancellationCause(Job parent) {
            Throwable it;
            Intrinsics.checkParameterIsNotNull(parent, "parent");
            Object state = this.job.getState$kotlinx_coroutines_core();
            if ((state instanceof Finishing) && (it = ((Finishing) state).rootCause) != null) {
                return it;
            }
            if (state instanceof CompletedExceptionally) {
                return ((CompletedExceptionally) state).cause;
            }
            return parent.getCancellationException();
        }

        /* access modifiers changed from: protected */
        public String nameString() {
            return "AwaitContinuation";
        }
    }

    public final boolean isCompletedExceptionally() {
        return getState$kotlinx_coroutines_core() instanceof CompletedExceptionally;
    }

    public final Throwable getCompletionExceptionOrNull() {
        Object state = getState$kotlinx_coroutines_core();
        if (!(state instanceof Incomplete)) {
            return getExceptionOrNull(state);
        }
        throw new IllegalStateException("This job has not completed yet".toString());
    }

    public final Object getCompletedInternal$kotlinx_coroutines_core() {
        Object state = getState$kotlinx_coroutines_core();
        if (!(!(state instanceof Incomplete))) {
            throw new IllegalStateException("This job has not completed yet".toString());
        } else if (!(state instanceof CompletedExceptionally)) {
            return JobSupportKt.unboxState(state);
        } else {
            throw ((CompletedExceptionally) state).cause;
        }
    }

    public final Object awaitInternal$kotlinx_coroutines_core(Continuation<Object> $completion) {
        Object state;
        do {
            state = getState$kotlinx_coroutines_core();
            if (!(state instanceof Incomplete)) {
                if (!(state instanceof CompletedExceptionally)) {
                    return JobSupportKt.unboxState(state);
                }
                Throwable exception$iv = ((CompletedExceptionally) state).cause;
                if (DebugKt.getRECOVER_STACK_TRACES()) {
                    InlineMarker.mark(0);
                    Continuation<Object> continuation = $completion;
                    if (!(continuation instanceof CoroutineStackFrame)) {
                        throw exception$iv;
                    }
                    throw StackTraceRecoveryKt.recoverFromStackFrame(exception$iv, (CoroutineStackFrame) continuation);
                }
                throw exception$iv;
            }
        } while (startInternal(state) < 0);
        return awaitSuspend($completion);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object awaitSuspend(Continuation<Object> $completion) {
        AwaitContinuation cont = new AwaitContinuation(IntrinsicsKt.intercepted($completion), this);
        CancellableContinuationKt.disposeOnCancellation(cont, invokeOnCompletion(new ResumeAwaitOnCompletion(this, cont)));
        Object result = cont.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result;
    }

    public final <T, R> void registerSelectClause1Internal$kotlinx_coroutines_core(SelectInstance<? super R> select, Function2<? super T, ? super Continuation<? super R>, ? extends Object> block) {
        Object state;
        Intrinsics.checkParameterIsNotNull(select, "select");
        Intrinsics.checkParameterIsNotNull(block, "block");
        do {
            state = getState$kotlinx_coroutines_core();
            if (!select.isSelected()) {
                if (!(state instanceof Incomplete)) {
                    if (!select.trySelect((Object) null)) {
                        return;
                    }
                    if (state instanceof CompletedExceptionally) {
                        select.resumeSelectCancellableWithException(((CompletedExceptionally) state).cause);
                        return;
                    } else {
                        UndispatchedKt.startCoroutineUnintercepted(block, JobSupportKt.unboxState(state), select.getCompletion());
                        return;
                    }
                }
            } else {
                return;
            }
        } while (startInternal(state) != 0);
        select.disposeOnSelect(invokeOnCompletion(new SelectAwaitOnCompletion(this, select, block)));
    }

    public final <T, R> void selectAwaitCompletion$kotlinx_coroutines_core(SelectInstance<? super R> select, Function2<? super T, ? super Continuation<? super R>, ? extends Object> block) {
        Intrinsics.checkParameterIsNotNull(select, "select");
        Intrinsics.checkParameterIsNotNull(block, "block");
        Object state = getState$kotlinx_coroutines_core();
        if (state instanceof CompletedExceptionally) {
            select.resumeSelectCancellableWithException(((CompletedExceptionally) state).cause);
        } else {
            CancellableKt.startCoroutineCancellable(block, JobSupportKt.unboxState(state), select.getCompletion());
        }
    }
}
