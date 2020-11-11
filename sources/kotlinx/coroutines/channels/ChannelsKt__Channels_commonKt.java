package kotlinx.coroutines.channels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.selects.SelectClause1;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000Ø\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010 \n\u0000\n\u0002\u0010!\n\u0002\b\u0011\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010#\n\u0000\n\u0002\u0010\"\n\u0002\b\u0006\u001aJ\u0010\u0002\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0004¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0003j\u0002`\t2\u001a\u0010\n\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\f0\u000b\"\u0006\u0012\u0002\b\u00030\fH\u0007¢\u0006\u0002\u0010\r\u001a5\u0010\u000e\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a!\u0010\u0013\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a5\u0010\u0013\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aY\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u0002H\u00180\u0016\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u0017\"\u0004\b\u0002\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00100\f2\u001e\u0010\u0019\u001a\u001a\u0012\u0004\u0012\u0002H\u0010\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u0002H\u00180\u001a0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aG\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u0002H\u00100\u0016\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u0017*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00170\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aa\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u0002H\u00180\u0016\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u0017\"\u0004\b\u0002\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00170\u00032\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00180\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u001e\u001a]\u0010\u001f\u001a\u0002H \"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u0017\"\u0018\b\u0002\u0010 *\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0017\u0012\u0006\b\u0000\u0012\u0002H\u00100!*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002H 2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00170\u0003HHø\u0001\u0000¢\u0006\u0002\u0010#\u001aw\u0010\u001f\u001a\u0002H \"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u0017\"\u0004\b\u0002\u0010\u0018\"\u0018\b\u0003\u0010 *\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0017\u0012\u0006\b\u0000\u0012\u0002H\u00180!*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002H 2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00170\u00032\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00180\u0003HHø\u0001\u0000¢\u0006\u0002\u0010$\u001ao\u0010%\u001a\u0002H \"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u0017\"\u0004\b\u0002\u0010\u0018\"\u0018\b\u0003\u0010 *\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0017\u0012\u0006\b\u0000\u0012\u0002H\u00180!*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002H 2\u001e\u0010\u0019\u001a\u001a\u0012\u0004\u0012\u0002H\u0010\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u0002H\u00180\u001a0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010#\u001a\u001a\u0010&\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\f2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004H\u0001\u001aC\u0010'\u001a\u0002H(\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010(*\b\u0012\u0004\u0012\u0002H\u00100)2\u001d\u0010*\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100\f\u0012\u0004\u0012\u0002H(0\u0003¢\u0006\u0002\b+H\b¢\u0006\u0002\u0010,\u001aC\u0010'\u001a\u0002H(\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010(*\b\u0012\u0004\u0012\u0002H\u00100\f2\u001d\u0010*\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100\f\u0012\u0004\u0012\u0002H(0\u0003¢\u0006\u0002\b+H\b¢\u0006\u0002\u0010-\u001a5\u0010.\u001a\u00020\b\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100)2\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\b0\u0003HHø\u0001\u0000¢\u0006\u0002\u00100\u001a5\u0010.\u001a\u00020\b\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\b0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a;\u00101\u001a\u00020\b\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0018\u0010/\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001002\u0012\u0004\u0012\u00020\b0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a1\u00103\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0004¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0003j\u0002`\t*\u0006\u0012\u0002\b\u00030\fH\u0007\u001a!\u00104\u001a\u000205\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a5\u00104\u001a\u000205\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a\u001e\u00106\u001a\b\u0012\u0004\u0012\u0002H\u00100\f\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\fH\u0007\u001aZ\u00107\u001a\b\u0012\u0004\u0012\u0002H\u00100\f\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u0017*\b\u0012\u0004\u0012\u0002H\u00100\f2\b\b\u0002\u00108\u001a\u0002092\"\u0010:\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00170<\u0012\u0006\u0012\u0004\u0018\u00010=0;H\u0007ø\u0001\u0000¢\u0006\u0002\u0010>\u001a0\u0010?\u001a\b\u0012\u0004\u0012\u0002H\u00100\f\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010@\u001a\u0002052\b\b\u0002\u00108\u001a\u000209H\u0007\u001aT\u0010A\u001a\b\u0012\u0004\u0012\u0002H\u00100\f\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\b\b\u0002\u00108\u001a\u0002092\"\u0010\u0011\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0<\u0012\u0006\u0012\u0004\u0018\u00010=0;H\u0007ø\u0001\u0000¢\u0006\u0002\u0010>\u001a)\u0010B\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010C\u001a\u000205H@ø\u0001\u0000¢\u0006\u0002\u0010D\u001a=\u0010E\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010C\u001a\u0002052\u0012\u0010F\u001a\u000e\u0012\u0004\u0012\u000205\u0012\u0004\u0012\u0002H\u00100\u0003HHø\u0001\u0000¢\u0006\u0002\u0010G\u001a+\u0010H\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010C\u001a\u000205H@ø\u0001\u0000¢\u0006\u0002\u0010D\u001aT\u0010I\u001a\b\u0012\u0004\u0012\u0002H\u00100\f\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\b\b\u0002\u00108\u001a\u0002092\"\u0010\u0011\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0<\u0012\u0006\u0012\u0004\u0018\u00010=0;H\u0007ø\u0001\u0000¢\u0006\u0002\u0010>\u001ai\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00100\f\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\b\b\u0002\u00108\u001a\u00020927\u0010\u0011\u001a3\b\u0001\u0012\u0013\u0012\u001105¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(C\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0<\u0012\u0006\u0012\u0004\u0018\u00010=0KH\u0007ø\u0001\u0000¢\u0006\u0002\u0010L\u001ad\u0010M\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\u0010\b\u0001\u0010N*\n\u0012\u0006\b\u0000\u0012\u0002H\u00100O*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HN2'\u0010\u0011\u001a#\u0012\u0013\u0012\u001105¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(C\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0;HHø\u0001\u0000¢\u0006\u0002\u0010P\u001ab\u0010M\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\u000e\b\u0001\u0010N*\b\u0012\u0004\u0012\u0002H\u00100Q*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HN2'\u0010\u0011\u001a#\u0012\u0013\u0012\u001105¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(C\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0;HHø\u0001\u0000¢\u0006\u0002\u0010R\u001aT\u0010S\u001a\b\u0012\u0004\u0012\u0002H\u00100\f\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\b\b\u0002\u00108\u001a\u0002092\"\u0010\u0011\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0<\u0012\u0006\u0012\u0004\u0018\u00010=0;H\u0007ø\u0001\u0000¢\u0006\u0002\u0010>\u001a$\u0010T\u001a\b\u0012\u0004\u0012\u0002H\u00100\f\"\b\b\u0000\u0010\u0010*\u00020=*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00100\fH\u0007\u001aA\u0010U\u001a\u0002HN\"\b\b\u0000\u0010\u0010*\u00020=\"\u0010\b\u0001\u0010N*\n\u0012\u0006\b\u0000\u0012\u0002H\u00100O*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00100\f2\u0006\u0010\"\u001a\u0002HNH@ø\u0001\u0000¢\u0006\u0002\u0010V\u001a?\u0010U\u001a\u0002HN\"\b\b\u0000\u0010\u0010*\u00020=\"\u000e\b\u0001\u0010N*\b\u0012\u0004\u0012\u0002H\u00100Q*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00100\f2\u0006\u0010\"\u001a\u0002HNH@ø\u0001\u0000¢\u0006\u0002\u0010W\u001aO\u0010X\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\u0010\b\u0001\u0010N*\n\u0012\u0006\b\u0000\u0012\u0002H\u00100O*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HN2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010Y\u001aM\u0010X\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\u000e\b\u0001\u0010N*\b\u0012\u0004\u0012\u0002H\u00100Q*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HN2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010Z\u001aO\u0010[\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\u0010\b\u0001\u0010N*\n\u0012\u0006\b\u0000\u0012\u0002H\u00100O*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HN2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010Y\u001aM\u0010[\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\u000e\b\u0001\u0010N*\b\u0012\u0004\u0012\u0002H\u00100Q*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HN2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010Z\u001a7\u0010\\\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a7\u0010]\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a!\u0010^\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a5\u0010^\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a#\u0010_\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a7\u0010_\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a`\u0010`\u001a\b\u0012\u0004\u0012\u0002H(0\f\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010(*\b\u0012\u0004\u0012\u0002H\u00100\f2\b\b\u0002\u00108\u001a\u0002092(\u0010\u0019\u001a$\b\u0001\u0012\u0004\u0012\u0002H\u0010\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H(0\f0<\u0012\u0006\u0012\u0004\u0018\u00010=0;H\u0007ø\u0001\u0000¢\u0006\u0002\u0010>\u001aX\u0010a\u001a\u0002H(\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010(*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010b\u001a\u0002H(2'\u0010c\u001a#\u0012\u0013\u0012\u0011H(¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(d\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H(0;HHø\u0001\u0000¢\u0006\u0002\u0010e\u001am\u0010f\u001a\u0002H(\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010(*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010b\u001a\u0002H(2<\u0010c\u001a8\u0012\u0013\u0012\u001105¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(C\u0012\u0013\u0012\u0011H(¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(d\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H(0KHHø\u0001\u0000¢\u0006\u0002\u0010g\u001aM\u0010h\u001a\u0014\u0012\u0004\u0012\u0002H\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100i0\u0016\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u0017*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00170\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001ag\u0010h\u001a\u0014\u0012\u0004\u0012\u0002H\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180i0\u0016\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u0017\"\u0004\b\u0002\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00170\u00032\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00180\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u001e\u001aa\u0010j\u001a\u0002H \"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u0017\"\u001c\b\u0002\u0010 *\u0016\u0012\u0006\b\u0000\u0012\u0002H\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100k0!*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002H 2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00170\u0003HHø\u0001\u0000¢\u0006\u0002\u0010#\u001a{\u0010j\u001a\u0002H \"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u0017\"\u0004\b\u0002\u0010\u0018\"\u001c\b\u0003\u0010 *\u0016\u0012\u0006\b\u0000\u0012\u0002H\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180k0!*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002H 2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00170\u00032\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u00180\u0003HHø\u0001\u0000¢\u0006\u0002\u0010$\u001a)\u0010l\u001a\u000205\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010m\u001a\u0002H\u0010H@ø\u0001\u0000¢\u0006\u0002\u0010n\u001a5\u0010o\u001a\u000205\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a5\u0010p\u001a\u000205\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a!\u0010q\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a5\u0010q\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a)\u0010r\u001a\u000205\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010m\u001a\u0002H\u0010H@ø\u0001\u0000¢\u0006\u0002\u0010n\u001a#\u0010s\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a7\u0010s\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aZ\u0010t\u001a\b\u0012\u0004\u0012\u0002H(0\f\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010(*\b\u0012\u0004\u0012\u0002H\u00100\f2\b\b\u0002\u00108\u001a\u0002092\"\u0010\u0019\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H(0<\u0012\u0006\u0012\u0004\u0018\u00010=0;H\u0007ø\u0001\u0000¢\u0006\u0002\u0010>\u001ao\u0010u\u001a\b\u0012\u0004\u0012\u0002H(0\f\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010(*\b\u0012\u0004\u0012\u0002H\u00100\f2\b\b\u0002\u00108\u001a\u00020927\u0010\u0019\u001a3\b\u0001\u0012\u0013\u0012\u001105¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(C\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H(0<\u0012\u0006\u0012\u0004\u0018\u00010=0KH\u0007ø\u0001\u0000¢\u0006\u0002\u0010L\u001au\u0010v\u001a\b\u0012\u0004\u0012\u0002H(0\f\"\u0004\b\u0000\u0010\u0010\"\b\b\u0001\u0010(*\u00020=*\b\u0012\u0004\u0012\u0002H\u00100\f2\b\b\u0002\u00108\u001a\u00020929\u0010\u0019\u001a5\b\u0001\u0012\u0013\u0012\u001105¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(C\u0012\u0004\u0012\u0002H\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H(0<\u0012\u0006\u0012\u0004\u0018\u00010=0KH\u0007ø\u0001\u0000¢\u0006\u0002\u0010L\u001ap\u0010w\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\b\b\u0001\u0010(*\u00020=\"\u0010\b\u0002\u0010N*\n\u0012\u0006\b\u0000\u0012\u0002H(0O*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HN2)\u0010\u0019\u001a%\u0012\u0013\u0012\u001105¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(C\u0012\u0004\u0012\u0002H\u0010\u0012\u0006\u0012\u0004\u0018\u0001H(0;HHø\u0001\u0000¢\u0006\u0002\u0010P\u001an\u0010w\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\b\b\u0001\u0010(*\u00020=\"\u000e\b\u0002\u0010N*\b\u0012\u0004\u0012\u0002H(0Q*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HN2)\u0010\u0019\u001a%\u0012\u0013\u0012\u001105¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(C\u0012\u0004\u0012\u0002H\u0010\u0012\u0006\u0012\u0004\u0018\u0001H(0;HHø\u0001\u0000¢\u0006\u0002\u0010R\u001aj\u0010x\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010(\"\u0010\b\u0002\u0010N*\n\u0012\u0006\b\u0000\u0012\u0002H(0O*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HN2'\u0010\u0019\u001a#\u0012\u0013\u0012\u001105¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(C\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H(0;HHø\u0001\u0000¢\u0006\u0002\u0010P\u001ah\u0010x\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010(\"\u000e\b\u0002\u0010N*\b\u0012\u0004\u0012\u0002H(0Q*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HN2'\u0010\u0019\u001a#\u0012\u0013\u0012\u001105¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(C\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H(0;HHø\u0001\u0000¢\u0006\u0002\u0010R\u001a`\u0010y\u001a\b\u0012\u0004\u0012\u0002H(0\f\"\u0004\b\u0000\u0010\u0010\"\b\b\u0001\u0010(*\u00020=*\b\u0012\u0004\u0012\u0002H\u00100\f2\b\b\u0002\u00108\u001a\u0002092$\u0010\u0019\u001a \b\u0001\u0012\u0004\u0012\u0002H\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H(0<\u0012\u0006\u0012\u0004\u0018\u00010=0;H\u0007ø\u0001\u0000¢\u0006\u0002\u0010>\u001a[\u0010z\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\b\b\u0001\u0010(*\u00020=\"\u0010\b\u0002\u0010N*\n\u0012\u0006\b\u0000\u0012\u0002H(0O*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HN2\u0014\u0010\u0019\u001a\u0010\u0012\u0004\u0012\u0002H\u0010\u0012\u0006\u0012\u0004\u0018\u0001H(0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010Y\u001aY\u0010z\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\b\b\u0001\u0010(*\u00020=\"\u000e\b\u0002\u0010N*\b\u0012\u0004\u0012\u0002H(0Q*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HN2\u0014\u0010\u0019\u001a\u0010\u0012\u0004\u0012\u0002H\u0010\u0012\u0006\u0012\u0004\u0018\u0001H(0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010Z\u001aU\u0010{\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010(\"\u0010\b\u0002\u0010N*\n\u0012\u0006\b\u0000\u0012\u0002H(0O*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HN2\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H(0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010Y\u001aS\u0010{\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010(\"\u000e\b\u0002\u0010N*\b\u0012\u0004\u0012\u0002H(0Q*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HN2\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H(0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010Z\u001aG\u0010|\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0010\"\u000e\b\u0001\u0010(*\b\u0012\u0004\u0012\u0002H(0}*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H(0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aB\u0010~\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u001c\u0010\u001a\u0018\u0012\u0006\b\u0000\u0012\u0002H\u00100\u0001j\u000b\u0012\u0006\b\u0000\u0012\u0002H\u0010`\u0001H@ø\u0001\u0000¢\u0006\u0003\u0010\u0001\u001aH\u0010\u0001\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0010\"\u000e\b\u0001\u0010(*\b\u0012\u0004\u0012\u0002H(0}*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H(0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aC\u0010\u0001\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u001c\u0010\u001a\u0018\u0012\u0006\b\u0000\u0012\u0002H\u00100\u0001j\u000b\u0012\u0006\b\u0000\u0012\u0002H\u0010`\u0001H@ø\u0001\u0000¢\u0006\u0003\u0010\u0001\u001a\"\u0010\u0001\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a6\u0010\u0001\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a&\u0010\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u0001H\u00100\u0001\"\b\b\u0000\u0010\u0010*\u00020=*\b\u0012\u0004\u0012\u0002H\u00100\fH\u0007\u001aN\u0010\u0001\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100i\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100i0\u001a\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a(\u0010\u0001\u001a\u0004\u0018\u0001H\u0010\"\b\b\u0000\u0010\u0010*\u00020=*\b\u0012\u0004\u0012\u0002H\u00100\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a[\u0010\u0001\u001a\u0003H\u0001\"\u0005\b\u0000\u0010\u0001\"\t\b\u0001\u0010\u0010*\u0003H\u0001*\b\u0012\u0004\u0012\u0002H\u00100\f2)\u0010c\u001a%\u0012\u0014\u0012\u0012H\u0001¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(d\u0012\u0004\u0012\u0002H\u0010\u0012\u0005\u0012\u0003H\u00010;HHø\u0001\u0000¢\u0006\u0003\u0010\u0001\u001ap\u0010\u0001\u001a\u0003H\u0001\"\u0005\b\u0000\u0010\u0001\"\t\b\u0001\u0010\u0010*\u0003H\u0001*\b\u0012\u0004\u0012\u0002H\u00100\f2>\u0010c\u001a:\u0012\u0013\u0012\u001105¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(C\u0012\u0014\u0012\u0012H\u0001¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(d\u0012\u0004\u0012\u0002H\u0010\u0012\u0005\u0012\u0003H\u00010KHHø\u0001\u0000¢\u0006\u0003\u0010\u0001\u001a%\u0010\u0001\u001a\b\u0012\u0004\u0012\u0002H\u00100\f\"\b\b\u0000\u0010\u0010*\u00020=*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00100\fH\u0007\u001a\"\u0010\u0001\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a6\u0010\u0001\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a$\u0010\u0001\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a8\u0010\u0001\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u000f0\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a6\u0010\u0001\u001a\u000205\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002050\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a8\u0010\u0001\u001a\u00030\u0001\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0013\u0010:\u001a\u000f\u0012\u0004\u0012\u0002H\u0010\u0012\u0005\u0012\u00030\u00010\u0003HHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a1\u0010\u0001\u001a\b\u0012\u0004\u0012\u0002H\u00100\f\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010@\u001a\u0002052\b\b\u0002\u00108\u001a\u000209H\u0007\u001aU\u0010\u0001\u001a\b\u0012\u0004\u0012\u0002H\u00100\f\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\b\b\u0002\u00108\u001a\u0002092\"\u0010\u0011\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0<\u0012\u0006\u0012\u0004\u0018\u00010=0;H\u0007ø\u0001\u0000¢\u0006\u0002\u0010>\u001a:\u0010\u0001\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\u000e\b\u0001\u0010N*\b\u0012\u0004\u0012\u0002H\u00100Q*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HNH@ø\u0001\u0000¢\u0006\u0002\u0010W\u001a<\u0010\u0001\u001a\u0002HN\"\u0004\b\u0000\u0010\u0010\"\u0010\b\u0001\u0010N*\n\u0012\u0006\b\u0000\u0012\u0002H\u00100O*\b\u0012\u0004\u0012\u0002H\u00100\f2\u0006\u0010\"\u001a\u0002HNH@ø\u0001\u0000¢\u0006\u0002\u0010V\u001a(\u0010\u0001\u001a\b\u0012\u0004\u0012\u0002H\u00100i\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a@\u0010\u0001\u001a\u000e\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u0002H\u00180\u0016\"\u0004\b\u0000\u0010\u0017\"\u0004\b\u0001\u0010\u0018*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u0002H\u00180\u001a0\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001aW\u0010\u0001\u001a\u0002H \"\u0004\b\u0000\u0010\u0017\"\u0004\b\u0001\u0010\u0018\"\u0018\b\u0002\u0010 *\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0017\u0012\u0006\b\u0000\u0012\u0002H\u00180!*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u0002H\u00180\u001a0\f2\u0006\u0010\"\u001a\u0002H H@ø\u0001\u0000¢\u0006\u0003\u0010\u0001\u001a(\u0010\u0001\u001a\b\u0012\u0004\u0012\u0002H\u00100k\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a)\u0010\u0001\u001a\t\u0012\u0004\u0012\u0002H\u00100\u0001\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a)\u0010\u0001\u001a\t\u0012\u0004\u0012\u0002H\u00100 \u0001\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a/\u0010¡\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u0010020\f\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\f2\b\b\u0002\u00108\u001a\u000209H\u0007\u001aA\u0010¢\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H(0\u001a0\f\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010(*\b\u0012\u0004\u0012\u0002H\u00100\f2\r\u0010£\u0001\u001a\b\u0012\u0004\u0012\u0002H(0\fH\u0004\u001a~\u0010¢\u0001\u001a\b\u0012\u0004\u0012\u0002H\u00180\f\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010(\"\u0004\b\u0002\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00100\f2\r\u0010£\u0001\u001a\b\u0012\u0004\u0012\u0002H(0\f2\b\b\u0002\u00108\u001a\u00020928\u0010\u0019\u001a4\u0012\u0014\u0012\u0012H\u0010¢\u0006\r\b\u0005\u0012\t\b\u0006\u0012\u0005\b\b(¤\u0001\u0012\u0014\u0012\u0012H(¢\u0006\r\b\u0005\u0012\t\b\u0006\u0012\u0005\b\b(¥\u0001\u0012\u0004\u0012\u0002H\u00180;H\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006¦\u0001"}, mo33671d2 = {"DEFAULT_CLOSE_MESSAGE", "", "consumesAll", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "channels", "", "Lkotlinx/coroutines/channels/ReceiveChannel;", "([Lkotlinx/coroutines/channels/ReceiveChannel;)Lkotlin/jvm/functions/Function1;", "all", "", "E", "predicate", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "any", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "associate", "", "K", "V", "transform", "Lkotlin/Pair;", "associateBy", "keySelector", "valueTransform", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "associateByTo", "M", "", "destination", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Map;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Map;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "associateTo", "cancelConsumed", "consume", "R", "Lkotlinx/coroutines/channels/BroadcastChannel;", "block", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/channels/BroadcastChannel;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "consumeEach", "action", "(Lkotlinx/coroutines/channels/BroadcastChannel;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "consumeEachIndexed", "Lkotlin/collections/IndexedValue;", "consumes", "count", "", "distinct", "distinctBy", "context", "Lkotlin/coroutines/CoroutineContext;", "selector", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/ReceiveChannel;", "drop", "n", "dropWhile", "elementAt", "index", "(Lkotlinx/coroutines/channels/ReceiveChannel;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "elementAtOrElse", "defaultValue", "(Lkotlinx/coroutines/channels/ReceiveChannel;ILkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "elementAtOrNull", "filter", "filterIndexed", "Lkotlin/Function3;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/channels/ReceiveChannel;", "filterIndexedTo", "C", "", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Collection;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/SendChannel;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlinx/coroutines/channels/SendChannel;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "filterNot", "filterNotNull", "filterNotNullTo", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Collection;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlinx/coroutines/channels/SendChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "filterNotTo", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlinx/coroutines/channels/SendChannel;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "filterTo", "find", "findLast", "first", "firstOrNull", "flatMap", "fold", "initial", "operation", "acc", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "foldIndexed", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "groupBy", "", "groupByTo", "", "indexOf", "element", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "indexOfFirst", "indexOfLast", "last", "lastIndexOf", "lastOrNull", "map", "mapIndexed", "mapIndexedNotNull", "mapIndexedNotNullTo", "mapIndexedTo", "mapNotNull", "mapNotNullTo", "mapTo", "maxBy", "", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Comparator;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "minBy", "minWith", "none", "onReceiveOrNull", "Lkotlinx/coroutines/selects/SelectClause1;", "partition", "receiveOrNull", "reduce", "S", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reduceIndexed", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "requireNoNulls", "single", "singleOrNull", "sumBy", "sumByDouble", "", "take", "takeWhile", "toChannel", "toCollection", "toList", "toMap", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toMutableList", "toMutableSet", "", "toSet", "", "withIndex", "zip", "other", "a", "b", "kotlinx-coroutines-core"}, mo33672k = 5, mo33673mv = {1, 1, 15}, mo33676xs = "kotlinx/coroutines/channels/ChannelsKt")
/* compiled from: Channels.common.kt */
final /* synthetic */ class ChannelsKt__Channels_commonKt {
    public static final <E, R> R consume(BroadcastChannel<E> $this$consume, Function1<? super ReceiveChannel<? extends E>, ? extends R> block) {
        Intrinsics.checkParameterIsNotNull($this$consume, "$this$consume");
        Intrinsics.checkParameterIsNotNull(block, "block");
        ReceiveChannel channel = $this$consume.openSubscription();
        try {
            return block.invoke(channel);
        } finally {
            InlineMarker.finallyStart(1);
            ReceiveChannel.DefaultImpls.cancel$default(channel, (CancellationException) null, 1, (Object) null);
            InlineMarker.finallyEnd(1);
        }
    }

    public static final <E> Object receiveOrNull(ReceiveChannel<? extends E> $this$receiveOrNull, Continuation<? super E> $completion) {
        if ($this$receiveOrNull != null) {
            return $this$receiveOrNull.receiveOrNull($completion);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.ReceiveChannel<E?>");
    }

    public static final <E> SelectClause1<E> onReceiveOrNull(ReceiveChannel<? extends E> $this$onReceiveOrNull) {
        Intrinsics.checkParameterIsNotNull($this$onReceiveOrNull, "$this$onReceiveOrNull");
        return $this$onReceiveOrNull.getOnReceiveOrNull();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v9, resolved type: kotlinx.coroutines.channels.BroadcastChannel<E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00bd A[Catch:{ all -> 0x00db }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object consumeEach(kotlinx.coroutines.channels.BroadcastChannel<E> r17, kotlin.jvm.functions.Function1<? super E, kotlin.Unit> r18, kotlin.coroutines.Continuation<? super kotlin.Unit> r19) {
        /*
            r1 = r19
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L_0x0073
            if (r4 != r6) goto L_0x006b
            r4 = r5
            r7 = r5
            r8 = 0
            r9 = r8
            r10 = r8
            r11 = r5
            java.lang.Object r12 = r2.L$5
            kotlinx.coroutines.channels.ChannelIterator r12 = (kotlinx.coroutines.channels.ChannelIterator) r12
            java.lang.Object r13 = r2.L$4
            r11 = r13
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r13 = r2.L$3
            r4 = r13
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r13 = r2.L$2
            r7 = r13
            kotlinx.coroutines.channels.BroadcastChannel r7 = (kotlinx.coroutines.channels.BroadcastChannel) r7
            java.lang.Object r13 = r2.L$1
            kotlin.jvm.functions.Function1 r13 = (kotlin.jvm.functions.Function1) r13
            java.lang.Object r14 = r2.L$0
            kotlinx.coroutines.channels.BroadcastChannel r14 = (kotlinx.coroutines.channels.BroadcastChannel) r14
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x005f }
            r15 = r12
            r12 = r9
            r9 = r4
            r4 = r1
            r1 = r14
            r14 = r11
            r11 = r8
            r8 = r3
            r16 = r7
            r7 = r2
            r2 = r13
            r13 = r10
            r10 = r16
            goto L_0x00b5
        L_0x005f:
            r0 = move-exception
            r9 = r7
            r12 = r10
            r7 = r3
            r10 = r8
            r3 = r1
            r8 = r4
            r1 = r14
            r4 = r2
            r2 = r13
            goto L_0x00f2
        L_0x006b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0073:
            kotlin.ResultKt.throwOnFailure(r3)
            r10 = 0
            r7 = r17
            r8 = 0
            kotlinx.coroutines.channels.ReceiveChannel r4 = r7.openSubscription()
            r9 = r4
            r11 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r9.iterator()     // Catch:{ all -> 0x00e6 }
            r13 = r9
            r14 = r12
            r9 = r7
            r12 = r10
            r7 = r3
            r10 = r8
            r3 = r1
            r8 = r4
            r1 = r17
            r4 = r2
            r2 = r18
        L_0x0092:
            r4.L$0 = r1     // Catch:{ all -> 0x00e4 }
            r4.L$1 = r2     // Catch:{ all -> 0x00e4 }
            r4.L$2 = r9     // Catch:{ all -> 0x00e4 }
            r4.L$3 = r8     // Catch:{ all -> 0x00e4 }
            r4.L$4 = r13     // Catch:{ all -> 0x00e4 }
            r4.L$5 = r14     // Catch:{ all -> 0x00e4 }
            r4.label = r6     // Catch:{ all -> 0x00e4 }
            java.lang.Object r15 = r14.hasNext(r4)     // Catch:{ all -> 0x00e4 }
            if (r15 != r0) goto L_0x00a7
            return r0
        L_0x00a7:
            r16 = r4
            r4 = r3
            r3 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r16
        L_0x00b5:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x00db }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x00db }
            if (r3 == 0) goto L_0x00cf
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x00db }
            r2.invoke(r3)     // Catch:{ all -> 0x00db }
            r3 = r4
            r4 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            goto L_0x0092
        L_0x00cf:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00db }
            kotlin.jvm.internal.InlineMarker.finallyStart(r6)
            kotlinx.coroutines.channels.ReceiveChannel.DefaultImpls.cancel$default((kotlinx.coroutines.channels.ReceiveChannel) r9, (java.util.concurrent.CancellationException) r5, (int) r6, (java.lang.Object) r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r6)
            return r0
        L_0x00db:
            r0 = move-exception
            r3 = r4
            r4 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r12 = r13
            goto L_0x00f2
        L_0x00e4:
            r0 = move-exception
            goto L_0x00f2
        L_0x00e6:
            r0 = move-exception
            r9 = r7
            r12 = r10
            r7 = r3
            r10 = r8
            r3 = r1
            r8 = r4
            r1 = r17
            r4 = r2
            r2 = r18
        L_0x00f2:
            kotlin.jvm.internal.InlineMarker.finallyStart(r6)
            kotlinx.coroutines.channels.ReceiveChannel.DefaultImpls.cancel$default((kotlinx.coroutines.channels.ReceiveChannel) r8, (java.util.concurrent.CancellationException) r5, (int) r6, (java.lang.Object) r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.consumeEach(kotlinx.coroutines.channels.BroadcastChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final Object consumeEach$$forInline(BroadcastChannel $this$consumeEach, Function1 action, Continuation continuation) {
        ReceiveChannel channel$iv = $this$consumeEach.openSubscription();
        try {
            ChannelIterator it = channel$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(1);
                if (!((Boolean) hasNext).booleanValue()) {
                    return Unit.INSTANCE;
                }
                action.invoke(it.next());
            }
        } finally {
            InlineMarker.finallyStart(1);
            ReceiveChannel.DefaultImpls.cancel$default(channel$iv, (CancellationException) null, 1, (Object) null);
            InlineMarker.finallyEnd(1);
        }
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final Function1<Throwable, Unit> consumes(ReceiveChannel<?> $this$consumes) {
        Intrinsics.checkParameterIsNotNull($this$consumes, "$this$consumes");
        return new ChannelsKt__Channels_commonKt$consumes$1($this$consumes);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.util.concurrent.CancellationException} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.util.concurrent.CancellationException} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.util.concurrent.CancellationException} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: java.util.concurrent.CancellationException} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: java.util.concurrent.CancellationException} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void cancelConsumed(kotlinx.coroutines.channels.ReceiveChannel<?> r4, java.lang.Throwable r5) {
        /*
            java.lang.String r0 = "$this$cancelConsumed"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r4, r0)
            r0 = 0
            if (r5 == 0) goto L_0x001b
            r1 = r5
            r2 = 0
            boolean r3 = r1 instanceof java.util.concurrent.CancellationException
            if (r3 != 0) goto L_0x000f
            goto L_0x0010
        L_0x000f:
            r0 = r1
        L_0x0010:
            java.util.concurrent.CancellationException r0 = (java.util.concurrent.CancellationException) r0
            if (r0 == 0) goto L_0x0015
            goto L_0x001b
        L_0x0015:
            java.lang.String r0 = "Channel was consumed, consumer had failed"
            java.util.concurrent.CancellationException r0 = kotlinx.coroutines.ExceptionsKt.CancellationException(r0, r1)
        L_0x001b:
            r4.cancel((java.util.concurrent.CancellationException) r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.cancelConsumed(kotlinx.coroutines.channels.ReceiveChannel, java.lang.Throwable):void");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final Function1<Throwable, Unit> consumesAll(ReceiveChannel<?>... channels) {
        Intrinsics.checkParameterIsNotNull(channels, "channels");
        return new ChannelsKt__Channels_commonKt$consumesAll$1(channels);
    }

    public static final <E, R> R consume(ReceiveChannel<? extends E> $this$consume, Function1<? super ReceiveChannel<? extends E>, ? extends R> block) {
        Throwable cause;
        Intrinsics.checkParameterIsNotNull($this$consume, "$this$consume");
        Intrinsics.checkParameterIsNotNull(block, "block");
        Throwable cause2 = null;
        try {
            R invoke = block.invoke($this$consume);
            InlineMarker.finallyStart(1);
            ChannelsKt.cancelConsumed($this$consume, cause2);
            InlineMarker.finallyEnd(1);
            return invoke;
        } catch (Throwable e) {
            InlineMarker.finallyStart(1);
            ChannelsKt.cancelConsumed($this$consume, cause);
            InlineMarker.finallyEnd(1);
            throw e;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v3, resolved type: kotlin.jvm.functions.Function1<? super E, kotlin.Unit>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v3, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0096 A[Catch:{ all -> 0x00b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object consumeEach(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r13, kotlin.jvm.functions.Function1<? super E, kotlin.Unit> r14, kotlin.coroutines.Continuation<? super kotlin.Unit> r15) {
        /*
            boolean r0 = r15 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$3
            if (r0 == 0) goto L_0x0014
            r0 = r15
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$3) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$3
            r0.<init>(r15)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 0
            r5 = 1
            if (r3 == 0) goto L_0x005e
            if (r3 != r5) goto L_0x0056
            r3 = r4
            r6 = r4
            r7 = 0
            r8 = r7
            r9 = r7
            java.lang.Object r10 = r0.L$5
            kotlinx.coroutines.channels.ChannelIterator r10 = (kotlinx.coroutines.channels.ChannelIterator) r10
            java.lang.Object r11 = r0.L$4
            r4 = r11
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r11 = r0.L$3
            r3 = r11
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            java.lang.Object r11 = r0.L$2
            r6 = r11
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r11 = r0.L$1
            r14 = r11
            kotlin.jvm.functions.Function1 r14 = (kotlin.jvm.functions.Function1) r14
            java.lang.Object r11 = r0.L$0
            r13 = r11
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x00bc }
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            goto L_0x008e
        L_0x0056:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x005e:
            kotlin.ResultKt.throwOnFailure(r1)
            r9 = 0
            r6 = r13
            r7 = 0
            r3 = r4
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            r4 = r6
            r8 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r4.iterator()     // Catch:{ all -> 0x00bc }
        L_0x006e:
            r0.L$0 = r13     // Catch:{ all -> 0x00bc }
            r0.L$1 = r14     // Catch:{ all -> 0x00bc }
            r0.L$2 = r6     // Catch:{ all -> 0x00bc }
            r0.L$3 = r3     // Catch:{ all -> 0x00bc }
            r0.L$4 = r4     // Catch:{ all -> 0x00bc }
            r0.L$5 = r10     // Catch:{ all -> 0x00bc }
            r0.label = r5     // Catch:{ all -> 0x00bc }
            java.lang.Object r11 = r10.hasNext(r0)     // Catch:{ all -> 0x00bc }
            if (r11 != r2) goto L_0x0083
            return r2
        L_0x0083:
            r12 = r2
            r2 = r1
            r1 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r4
            r4 = r3
            r3 = r12
        L_0x008e:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x00b3 }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x00b3 }
            if (r1 == 0) goto L_0x00a7
            java.lang.Object r1 = r11.next()     // Catch:{ all -> 0x00b3 }
            r14.invoke(r1)     // Catch:{ all -> 0x00b3 }
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            goto L_0x006e
        L_0x00a7:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00b3 }
            kotlin.jvm.internal.InlineMarker.finallyStart(r5)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r4)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r5)
            return r1
        L_0x00b3:
            r1 = move-exception
            r3 = r4
            r6 = r7
            r7 = r8
            r9 = r10
            r12 = r2
            r2 = r1
            r1 = r12
            goto L_0x00bd
        L_0x00bc:
            r2 = move-exception
        L_0x00bd:
            r3 = r2
            throw r2     // Catch:{ all -> 0x00c0 }
        L_0x00c0:
            r2 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r5)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r5)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.consumeEach(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final Object consumeEach$$forInline(ReceiveChannel $this$consumeEach, Function1 action, Continuation continuation) {
        Throwable cause$iv;
        ReceiveChannel $this$consume$iv = $this$consumeEach;
        Throwable cause$iv2 = null;
        try {
            ChannelIterator it = $this$consume$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    action.invoke(it.next());
                } else {
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv, cause$iv2);
                    InlineMarker.finallyEnd(1);
                    return unit;
                }
            }
        } catch (Throwable e$iv) {
            InlineMarker.finallyStart(1);
            ChannelsKt.cancelConsumed($this$consume$iv, cause$iv);
            InlineMarker.finallyEnd(1);
            throw e$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v11, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v12, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v10, resolved type: kotlin.jvm.internal.Ref$IntRef} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00e1 A[Catch:{ all -> 0x012e }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object consumeEachIndexed(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r19, kotlin.jvm.functions.Function1<? super kotlin.collections.IndexedValue<? extends E>, kotlin.Unit> r20, kotlin.coroutines.Continuation<? super kotlin.Unit> r21) {
        /*
            r1 = r21
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEachIndexed$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEachIndexed$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEachIndexed$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEachIndexed$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEachIndexed$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 0
            r7 = 1
            if (r4 == 0) goto L_0x0081
            if (r4 != r7) goto L_0x0079
            r4 = r5
            r8 = r6
            r9 = r5
            r10 = r6
            r11 = r5
            r12 = r6
            r13 = r6
            java.lang.Object r14 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r14 = (kotlinx.coroutines.channels.ChannelIterator) r14
            java.lang.Object r15 = r2.L$6
            r12 = r15
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r15 = r2.L$5
            r10 = r15
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r15 = r2.L$4
            r13 = r15
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            java.lang.Object r15 = r2.L$3
            r6 = r15
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r15 = r2.L$2
            r8 = r15
            kotlin.jvm.internal.Ref$IntRef r8 = (kotlin.jvm.internal.Ref.IntRef) r8
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            java.lang.Object r7 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006c }
            r16 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r4
            r4 = r1
            r1 = r7
            r7 = r5
            r5 = r2
            r2 = r15
            r15 = r13
            r13 = r11
            r11 = r9
            r9 = r6
            r6 = r3
            goto L_0x00d9
        L_0x006c:
            r0 = move-exception
            r11 = r9
            r12 = r10
            r9 = r6
            r10 = r8
            r8 = r4
            r6 = r5
            r4 = r2
            r5 = r3
            r2 = r15
            r3 = r1
            goto L_0x015d
        L_0x0079:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0081:
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = 0
            kotlin.jvm.internal.Ref$IntRef r7 = new kotlin.jvm.internal.Ref$IntRef
            r7.<init>()
            r7.element = r5
            r8 = r7
            r5 = r19
            r7 = 0
            r13 = r5
            r9 = 0
            r10 = r6
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r6 = r13
            r11 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r6.iterator()     // Catch:{ all -> 0x014f }
            r15 = r12
            r14 = r13
            r13 = r6
            r12 = r11
            r6 = r4
            r11 = r10
            r4 = r2
            r10 = r9
            r2 = r20
            r9 = r8
            r8 = r5
            r5 = r3
            r3 = r1
            r1 = r19
        L_0x00ac:
            r4.L$0 = r1     // Catch:{ all -> 0x0143 }
            r4.L$1 = r2     // Catch:{ all -> 0x0143 }
            r4.L$2 = r9     // Catch:{ all -> 0x0143 }
            r4.L$3 = r8     // Catch:{ all -> 0x0143 }
            r4.L$4 = r14     // Catch:{ all -> 0x0143 }
            r4.L$5 = r11     // Catch:{ all -> 0x0143 }
            r4.L$6 = r13     // Catch:{ all -> 0x0143 }
            r4.L$7 = r15     // Catch:{ all -> 0x0143 }
            r19 = r1
            r1 = 1
            r4.label = r1     // Catch:{ all -> 0x0139 }
            java.lang.Object r1 = r15.hasNext(r4)     // Catch:{ all -> 0x0139 }
            if (r1 != r0) goto L_0x00c8
            return r0
        L_0x00c8:
            r16 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r19
        L_0x00d9:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x012e }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x012e }
            if (r3 == 0) goto L_0x0113
            java.lang.Object r3 = r16.next()     // Catch:{ all -> 0x012e }
            r19 = r3
            r17 = 0
            r20 = r0
            kotlin.collections.IndexedValue r0 = new kotlin.collections.IndexedValue     // Catch:{ all -> 0x012e }
            r21 = r1
            int r1 = r10.element     // Catch:{ all -> 0x0125 }
            r18 = r3
            int r3 = r1 + 1
            r10.element = r3     // Catch:{ all -> 0x0125 }
            r3 = r19
            r0.<init>(r1, r3)     // Catch:{ all -> 0x0125 }
            r2.invoke(r0)     // Catch:{ all -> 0x0125 }
            r0 = r20
            r1 = r21
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r15 = r16
            goto L_0x00ac
        L_0x0113:
            r21 = r1
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0125 }
            r1 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0125:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r13 = r15
            r7 = r21
            goto L_0x015d
        L_0x012e:
            r0 = move-exception
            r21 = r1
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r13 = r15
            r7 = r21
            goto L_0x015d
        L_0x0139:
            r0 = move-exception
            r12 = r11
            r13 = r14
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r19
            goto L_0x015d
        L_0x0143:
            r0 = move-exception
            r19 = r1
            r12 = r11
            r13 = r14
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r19
            goto L_0x015d
        L_0x014f:
            r0 = move-exception
            r6 = r4
            r11 = r9
            r12 = r10
            r4 = r2
            r9 = r5
            r10 = r8
            r2 = r20
            r5 = r3
            r8 = r7
            r7 = r19
            r3 = r1
        L_0x015d:
            r1 = r0
            throw r0     // Catch:{ all -> 0x0160 }
        L_0x0160:
            r0 = move-exception
            r12 = r0
            r14 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r14)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r13, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r14)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.consumeEachIndexed(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object consumeEachIndexed$$forInline(ReceiveChannel $this$consumeEachIndexed, Function1 action, Continuation continuation) {
        int index = 0;
        ReceiveChannel $this$consume$iv$iv = $this$consumeEachIndexed;
        Throwable cause$iv$iv = null;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    int index2 = index + 1;
                    try {
                        try {
                            action.invoke(new IndexedValue(index, it.next()));
                            index = index2;
                        } catch (Throwable th) {
                            e$iv$iv = th;
                            int i = index2;
                            Throwable cause$iv$iv2 = e$iv$iv;
                            try {
                                throw e$iv$iv;
                            } catch (Throwable e$iv$iv) {
                                Throwable th2 = e$iv$iv;
                                InlineMarker.finallyStart(1);
                                ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                                InlineMarker.finallyEnd(1);
                                throw th2;
                            }
                        }
                    } catch (Throwable th3) {
                        e$iv$iv = th3;
                        Function1 function1 = action;
                        int i2 = index2;
                        Throwable cause$iv$iv22 = e$iv$iv;
                        throw e$iv$iv;
                    }
                } else {
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return Unit.INSTANCE;
                }
            }
        } catch (Throwable th4) {
            e$iv$iv = th4;
            Throwable cause$iv$iv222 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00cb A[Catch:{ all -> 0x0112 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002c  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object elementAt(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r19, int r20, kotlin.coroutines.Continuation<? super E> r21) {
        /*
            r1 = r20
            r2 = r21
            boolean r0 = r2 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$elementAt$1
            if (r0 == 0) goto L_0x0018
            r0 = r2
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$elementAt$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$elementAt$1) r0
            int r3 = r0.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r3 & r4
            if (r3 == 0) goto L_0x0018
            int r3 = r0.label
            int r3 = r3 - r4
            r0.label = r3
            goto L_0x001d
        L_0x0018:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$elementAt$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$elementAt$1
            r0.<init>(r2)
        L_0x001d:
            r3 = r0
            java.lang.Object r4 = r3.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r5 = r3.label
            java.lang.String r7 = "ReceiveChannel doesn't contain element at index "
            r8 = 1
            r9 = 0
            if (r5 == 0) goto L_0x0076
            if (r5 != r8) goto L_0x006e
            r5 = 0
            r10 = r5
            r11 = r5
            r12 = r5
            r13 = r9
            r14 = r9
            r15 = r9
            int r12 = r3.I$1
            java.lang.Object r6 = r3.L$5
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r8 = r3.L$4
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r13 = r3.L$3
            java.lang.Throwable r13 = (java.lang.Throwable) r13
            java.lang.Object r15 = r3.L$2
            r9 = r15
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r15 = r3.L$1
            r14 = r15
            kotlinx.coroutines.channels.ReceiveChannel r14 = (kotlinx.coroutines.channels.ReceiveChannel) r14
            int r1 = r3.I$0
            java.lang.Object r15 = r3.L$0
            kotlinx.coroutines.channels.ReceiveChannel r15 = (kotlinx.coroutines.channels.ReceiveChannel) r15
            kotlin.ResultKt.throwOnFailure(r4)     // Catch:{ all -> 0x0067 }
            r16 = r14
            r14 = r11
            r11 = r9
            r9 = r5
            r5 = r3
            r3 = r2
            r2 = r1
            r1 = r15
            r15 = r13
            r13 = r12
            r12 = r10
            r10 = r8
            r8 = r6
            r6 = r4
            goto L_0x00c3
        L_0x0067:
            r0 = move-exception
            r5 = r3
            r3 = r2
            r2 = r1
            r1 = r15
            goto L_0x0169
        L_0x006e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r3 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r3)
            throw r0
        L_0x0076:
            kotlin.ResultKt.throwOnFailure(r4)
            r14 = r19
            r11 = 0
            r5 = r14
            r10 = 0
            r13 = r9
            java.lang.Throwable r13 = (java.lang.Throwable) r13
            r6 = r5
            r8 = 0
            if (r1 < 0) goto L_0x013f
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r6.iterator()     // Catch:{ all -> 0x0137 }
            r15 = r14
            r14 = r13
            r13 = r9
            r9 = r6
            r6 = r12
            r12 = r11
            r11 = r10
            r10 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r19
        L_0x0099:
            r4.L$0 = r1     // Catch:{ all -> 0x0129 }
            r4.I$0 = r2     // Catch:{ all -> 0x0129 }
            r4.L$1 = r15     // Catch:{ all -> 0x0129 }
            r4.L$2 = r10     // Catch:{ all -> 0x0129 }
            r4.L$3 = r14     // Catch:{ all -> 0x0129 }
            r4.L$4 = r9     // Catch:{ all -> 0x0129 }
            r4.L$5 = r6     // Catch:{ all -> 0x0129 }
            r4.I$1 = r13     // Catch:{ all -> 0x0129 }
            r19 = r1
            r1 = 1
            r4.label = r1     // Catch:{ all -> 0x011b }
            java.lang.Object r1 = r6.hasNext(r4)     // Catch:{ all -> 0x011b }
            if (r1 != r0) goto L_0x00b5
            return r0
        L_0x00b5:
            r16 = r15
            r15 = r14
            r14 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r6
            r6 = r5
            r5 = r4
            r4 = r1
            r1 = r19
        L_0x00c3:
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ all -> 0x0112 }
            boolean r4 = r4.booleanValue()     // Catch:{ all -> 0x0112 }
            if (r4 == 0) goto L_0x00e6
            java.lang.Object r4 = r8.next()     // Catch:{ all -> 0x0112 }
            int r17 = r13 + 1
            if (r2 != r13) goto L_0x00d8
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r15)
            return r4
        L_0x00d8:
            r4 = r5
            r5 = r6
            r6 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r14
            r14 = r15
            r15 = r16
            r13 = r17
            goto L_0x0099
        L_0x00e6:
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r2)     // Catch:{ all -> 0x0112 }
            java.lang.Number r0 = (java.lang.Number) r0     // Catch:{ all -> 0x0112 }
            int r0 = r0.intValue()     // Catch:{ all -> 0x0112 }
            r4 = 0
            java.lang.IndexOutOfBoundsException r8 = new java.lang.IndexOutOfBoundsException     // Catch:{ all -> 0x0112 }
            r19 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0112 }
            r0.<init>()     // Catch:{ all -> 0x0112 }
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ all -> 0x0112 }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0112 }
            r7 = 46
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ all -> 0x0112 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0112 }
            r8.<init>(r0)     // Catch:{ all -> 0x0112 }
            java.lang.Throwable r8 = (java.lang.Throwable) r8     // Catch:{ all -> 0x0112 }
            throw r8     // Catch:{ all -> 0x0112 }
        L_0x0112:
            r0 = move-exception
            r4 = r6
            r9 = r11
            r10 = r12
            r11 = r14
            r13 = r15
            r14 = r16
            goto L_0x0169
        L_0x011b:
            r0 = move-exception
            r1 = r19
            r9 = r10
            r10 = r11
            r11 = r12
            r13 = r14
            r14 = r15
            r18 = r5
            r5 = r4
            r4 = r18
            goto L_0x0169
        L_0x0129:
            r0 = move-exception
            r19 = r1
            r9 = r10
            r10 = r11
            r11 = r12
            r13 = r14
            r14 = r15
            r18 = r5
            r5 = r4
            r4 = r18
            goto L_0x0169
        L_0x0137:
            r0 = move-exception
            r9 = r5
            r5 = r3
            r3 = r2
            r2 = r1
            r1 = r19
            goto L_0x0169
        L_0x013f:
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r20)     // Catch:{ all -> 0x0137 }
            java.lang.Number r0 = (java.lang.Number) r0     // Catch:{ all -> 0x0137 }
            int r0 = r0.intValue()     // Catch:{ all -> 0x0137 }
            r9 = 0
            java.lang.IndexOutOfBoundsException r12 = new java.lang.IndexOutOfBoundsException     // Catch:{ all -> 0x0137 }
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ all -> 0x0137 }
            r15.<init>()     // Catch:{ all -> 0x0137 }
            java.lang.StringBuilder r7 = r15.append(r7)     // Catch:{ all -> 0x0137 }
            java.lang.StringBuilder r7 = r7.append(r1)     // Catch:{ all -> 0x0137 }
            r15 = 46
            java.lang.StringBuilder r7 = r7.append(r15)     // Catch:{ all -> 0x0137 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0137 }
            r12.<init>(r7)     // Catch:{ all -> 0x0137 }
            java.lang.Throwable r12 = (java.lang.Throwable) r12     // Catch:{ all -> 0x0137 }
            throw r12     // Catch:{ all -> 0x0137 }
        L_0x0169:
            r6 = r0
            throw r0     // Catch:{ all -> 0x016c }
        L_0x016c:
            r0 = move-exception
            r7 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.elementAt(kotlinx.coroutines.channels.ReceiveChannel, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v4, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00e5 A[Catch:{ all -> 0x0113 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object elementAtOrElse(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r20, int r21, kotlin.jvm.functions.Function1<? super java.lang.Integer, ? extends E> r22, kotlin.coroutines.Continuation<? super E> r23) {
        /*
            r1 = r23
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$elementAtOrElse$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$elementAtOrElse$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$elementAtOrElse$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$elementAtOrElse$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$elementAtOrElse$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L_0x007f
            if (r4 != r6) goto L_0x0077
            r4 = 0
            r7 = r4
            r8 = r4
            r9 = r5
            r10 = r4
            r11 = r5
            java.lang.Object r12 = r2.L$5
            kotlinx.coroutines.channels.ChannelIterator r12 = (kotlinx.coroutines.channels.ChannelIterator) r12
            int r4 = r2.I$1
            java.lang.Object r13 = r2.L$4
            r9 = r13
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r13 = r2.L$3
            r11 = r13
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            java.lang.Object r13 = r2.L$2
            r5 = r13
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r13 = r2.L$1
            kotlin.jvm.functions.Function1 r13 = (kotlin.jvm.functions.Function1) r13
            int r14 = r2.I$0
            java.lang.Object r15 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r15 = (kotlinx.coroutines.channels.ReceiveChannel) r15
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006b }
            r6 = r3
            r17 = r4
            r4 = r1
            r1 = r15
            r15 = r12
            r12 = r17
            r18 = r5
            r5 = r2
            r2 = r14
            r14 = r11
            r11 = r9
            r9 = r8
            r8 = r7
            r7 = r18
            r19 = r13
            r13 = r10
            r10 = r19
            goto L_0x00dd
        L_0x006b:
            r0 = move-exception
            r4 = r2
            r9 = r8
            r10 = r13
            r2 = r14
            r8 = r7
            r7 = r5
            r5 = r3
            r3 = r1
            r1 = r15
            goto L_0x0127
        L_0x0077:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x007f:
            kotlin.ResultKt.throwOnFailure(r3)
            r8 = 0
            r4 = r20
            r7 = 0
            r11 = r5
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            r5 = r4
            r9 = 0
            if (r21 >= 0) goto L_0x00a8
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r21)     // Catch:{ all -> 0x00a3 }
            r10 = r22
            java.lang.Object r0 = r10.invoke(r0)     // Catch:{ all -> 0x011c }
            r5 = 4
            kotlin.jvm.internal.InlineMarker.finallyStart(r5)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r11)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r5)
            return r0
        L_0x00a3:
            r0 = move-exception
            r10 = r22
            goto L_0x011d
        L_0x00a8:
            r10 = r22
            r12 = 0
            kotlinx.coroutines.channels.ChannelIterator r13 = r5.iterator()     // Catch:{ all -> 0x011c }
            r14 = r11
            r15 = r13
            r11 = r5
            r13 = r9
            r5 = r3
            r9 = r8
            r3 = r1
            r8 = r7
            r1 = r20
            r7 = r4
            r4 = r2
            r2 = r21
        L_0x00bd:
            r4.L$0 = r1     // Catch:{ all -> 0x0119 }
            r4.I$0 = r2     // Catch:{ all -> 0x0119 }
            r4.L$1 = r10     // Catch:{ all -> 0x0119 }
            r4.L$2 = r7     // Catch:{ all -> 0x0119 }
            r4.L$3 = r14     // Catch:{ all -> 0x0119 }
            r4.L$4 = r11     // Catch:{ all -> 0x0119 }
            r4.I$1 = r12     // Catch:{ all -> 0x0119 }
            r4.L$5 = r15     // Catch:{ all -> 0x0119 }
            r4.label = r6     // Catch:{ all -> 0x0119 }
            java.lang.Object r6 = r15.hasNext(r4)     // Catch:{ all -> 0x0119 }
            if (r6 != r0) goto L_0x00d6
            return r0
        L_0x00d6:
            r17 = r4
            r4 = r3
            r3 = r6
            r6 = r5
            r5 = r17
        L_0x00dd:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0113 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0113 }
            if (r3 == 0) goto L_0x0100
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x0113 }
            int r16 = r12 + 1
            if (r2 != r12) goto L_0x00f9
            r0 = 3
            kotlin.jvm.internal.InlineMarker.finallyStart(r0)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r14)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r0)
            return r3
        L_0x00f9:
            r3 = r4
            r4 = r5
            r5 = r6
            r12 = r16
            r6 = 1
            goto L_0x00bd
        L_0x0100:
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r2)     // Catch:{ all -> 0x0113 }
            java.lang.Object r0 = r10.invoke(r0)     // Catch:{ all -> 0x0113 }
            r3 = 2
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r14)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r0
        L_0x0113:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r5 = r6
            r11 = r14
            goto L_0x0127
        L_0x0119:
            r0 = move-exception
            r11 = r14
            goto L_0x0127
        L_0x011c:
            r0 = move-exception
        L_0x011d:
            r5 = r3
            r9 = r8
            r3 = r1
            r8 = r7
            r1 = r20
            r7 = r4
            r4 = r2
            r2 = r21
        L_0x0127:
            r6 = r0
            throw r0     // Catch:{ all -> 0x012a }
        L_0x012a:
            r0 = move-exception
            r11 = r0
            r12 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r12)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r12)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.elementAtOrElse(kotlinx.coroutines.channels.ReceiveChannel, int, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r14 = r13.invoke(java.lang.Integer.valueOf(r12));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0055, code lost:
        r2 = 2;
        kotlin.jvm.internal.InlineMarker.finallyStart(2);
     */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final java.lang.Object elementAtOrElse$$forInline(kotlinx.coroutines.channels.ReceiveChannel r11, int r12, kotlin.jvm.functions.Function1 r13, kotlin.coroutines.Continuation r14) {
        /*
            r0 = 0
            r1 = r11
            r2 = 0
            r3 = 0
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            r4 = r1
            r5 = 0
            r6 = 1
            if (r12 >= 0) goto L_0x001f
            java.lang.Integer r14 = java.lang.Integer.valueOf(r12)     // Catch:{ all -> 0x005a }
            java.lang.Object r14 = r13.invoke(r14)     // Catch:{ all -> 0x005a }
            r2 = 4
            kotlin.jvm.internal.InlineMarker.finallyStart(r2)
        L_0x0018:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r2)
            return r14
        L_0x001f:
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r4.iterator()     // Catch:{ all -> 0x005a }
        L_0x0024:
            r9 = 0
            kotlin.jvm.internal.InlineMarker.mark((int) r9)     // Catch:{ all -> 0x005a }
            java.lang.Object r9 = r8.hasNext(r14)     // Catch:{ all -> 0x005a }
            kotlin.jvm.internal.InlineMarker.mark((int) r6)     // Catch:{ all -> 0x005a }
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch:{ all -> 0x005a }
            boolean r9 = r9.booleanValue()     // Catch:{ all -> 0x005a }
            if (r9 == 0) goto L_0x004d
            java.lang.Object r9 = r8.next()     // Catch:{ all -> 0x005a }
            int r10 = r7 + 1
            if (r12 != r7) goto L_0x004b
            r14 = 3
            kotlin.jvm.internal.InlineMarker.finallyStart(r14)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r14)
            return r9
        L_0x004b:
            r7 = r10
            goto L_0x0024
        L_0x004d:
            java.lang.Integer r14 = java.lang.Integer.valueOf(r12)     // Catch:{ all -> 0x005a }
            java.lang.Object r14 = r13.invoke(r14)     // Catch:{ all -> 0x005a }
            r2 = 2
            kotlin.jvm.internal.InlineMarker.finallyStart(r2)
            goto L_0x0018
        L_0x005a:
            r14 = move-exception
            r3 = r14
            throw r14     // Catch:{ all -> 0x005e }
        L_0x005e:
            r14 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r6)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r6)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.elementAtOrElse$$forInline(kotlinx.coroutines.channels.ReceiveChannel, int, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00af  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00c4 A[Catch:{ all -> 0x00e2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object elementAtOrNull(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r19, int r20, kotlin.coroutines.Continuation<? super E> r21) {
        /*
            r1 = r21
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$elementAtOrNull$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$elementAtOrNull$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$elementAtOrNull$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$elementAtOrNull$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$elementAtOrNull$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x0072
            if (r4 != r5) goto L_0x006a
            r4 = r6
            r7 = 0
            r8 = r7
            r9 = r7
            r10 = r6
            r11 = r6
            java.lang.Object r12 = r2.L$4
            kotlinx.coroutines.channels.ChannelIterator r12 = (kotlinx.coroutines.channels.ChannelIterator) r12
            int r9 = r2.I$1
            java.lang.Object r13 = r2.L$3
            r4 = r13
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r13 = r2.L$2
            r11 = r13
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            java.lang.Object r13 = r2.L$1
            r10 = r13
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            int r13 = r2.I$0
            java.lang.Object r14 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r14 = (kotlinx.coroutines.channels.ReceiveChannel) r14
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0062 }
            r15 = r12
            r12 = r8
            r8 = r3
            r17 = r4
            r4 = r1
            r1 = r14
            r14 = r11
            r11 = r9
            r9 = r17
            r18 = r7
            r7 = r2
            r2 = r13
            r13 = r10
            r10 = r18
            goto L_0x00bc
        L_0x0062:
            r0 = move-exception
            r4 = r2
            r7 = r3
            r2 = r13
            r3 = r1
            r1 = r14
            goto L_0x00f7
        L_0x006a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0072:
            kotlin.ResultKt.throwOnFailure(r3)
            r10 = r19
            r8 = 0
            r11 = r6
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            r4 = r10
            r7 = 0
            if (r20 >= 0) goto L_0x0085
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r11)
            return r6
        L_0x0085:
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r4.iterator()     // Catch:{ all -> 0x00ef }
            r13 = r11
            r14 = r12
            r11 = r9
            r12 = r10
            r9 = r7
            r10 = r8
            r7 = r3
            r8 = r4
            r3 = r1
            r4 = r2
            r1 = r19
            r2 = r20
        L_0x0098:
            r4.L$0 = r1     // Catch:{ all -> 0x00ea }
            r4.I$0 = r2     // Catch:{ all -> 0x00ea }
            r4.L$1 = r12     // Catch:{ all -> 0x00ea }
            r4.L$2 = r13     // Catch:{ all -> 0x00ea }
            r4.L$3 = r8     // Catch:{ all -> 0x00ea }
            r4.I$1 = r11     // Catch:{ all -> 0x00ea }
            r4.L$4 = r14     // Catch:{ all -> 0x00ea }
            r4.label = r5     // Catch:{ all -> 0x00ea }
            java.lang.Object r15 = r14.hasNext(r4)     // Catch:{ all -> 0x00ea }
            if (r15 != r0) goto L_0x00af
            return r0
        L_0x00af:
            r17 = r4
            r4 = r3
            r3 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r17
        L_0x00bc:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x00e2 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x00e2 }
            if (r3 == 0) goto L_0x00dd
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x00e2 }
            int r16 = r11 + 1
            if (r2 != r11) goto L_0x00d1
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r13, r14)
            return r3
        L_0x00d1:
            r3 = r4
            r4 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r11 = r16
            goto L_0x0098
        L_0x00dd:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r13, r14)
            return r6
        L_0x00e2:
            r0 = move-exception
            r3 = r4
            r4 = r7
            r7 = r8
            r8 = r12
            r10 = r13
            r11 = r14
            goto L_0x00f7
        L_0x00ea:
            r0 = move-exception
            r8 = r10
            r10 = r12
            r11 = r13
            goto L_0x00f7
        L_0x00ef:
            r0 = move-exception
            r4 = r2
            r7 = r3
            r2 = r20
            r3 = r1
            r1 = r19
        L_0x00f7:
            r5 = r0
            throw r0     // Catch:{ all -> 0x00fa }
        L_0x00fa:
            r0 = move-exception
            r6 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.elementAtOrNull(kotlinx.coroutines.channels.ReceiveChannel, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00e2 A[Catch:{ all -> 0x011a }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object find(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r22, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r23, kotlin.coroutines.Continuation<? super E> r24) {
        /*
            r1 = r24
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$find$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$find$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$find$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$find$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$find$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x0086
            if (r4 != r5) goto L_0x007e
            r4 = r6
            r7 = r6
            r8 = 0
            r9 = r8
            r10 = r6
            r11 = r8
            r12 = r6
            r13 = r6
            r14 = r8
            r15 = r8
            java.lang.Object r5 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r2.L$6
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r12 = r2.L$5
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            java.lang.Object r13 = r2.L$4
            r10 = r13
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r13 = r2.L$3
            r4 = r13
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r13 = r2.L$2
            r7 = r13
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r13 = r2.L$1
            kotlin.jvm.functions.Function1 r13 = (kotlin.jvm.functions.Function1) r13
            r17 = r0
            java.lang.Object r0 = r2.L$0
            r18 = r0
            kotlinx.coroutines.channels.ReceiveChannel r18 = (kotlinx.coroutines.channels.ReceiveChannel) r18
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0077 }
            r16 = r14
            r0 = 0
            r14 = r11
            r11 = r8
            r8 = r6
            r6 = r3
            r21 = r4
            r4 = r1
            r1 = r18
            r18 = r15
            r15 = r12
            r12 = r9
            r9 = r5
            r5 = r2
            r2 = r13
            r13 = r10
            r10 = r7
            r7 = r21
            goto L_0x00da
        L_0x0077:
            r0 = move-exception
            r5 = r2
            r6 = r3
            r2 = r13
            r3 = r1
            goto L_0x014b
        L_0x007e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0086:
            r17 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r14 = 0
            r7 = r22
            r8 = 0
            r4 = r7
            r15 = 0
            r10 = r4
            r11 = 0
            r0 = 0
            r12 = r0
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            r5 = r10
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r9 = r5.iterator()     // Catch:{ all -> 0x0143 }
            r13 = r10
            r16 = r14
            r10 = r7
            r14 = r11
            r7 = r4
            r11 = r8
            r4 = r17
            r8 = r5
            r17 = r15
            r5 = r2
            r15 = r12
            r2 = r23
            r12 = r6
            r6 = r3
            r3 = r1
            r1 = r22
        L_0x00b3:
            r5.L$0 = r1     // Catch:{ all -> 0x0135 }
            r5.L$1 = r2     // Catch:{ all -> 0x0135 }
            r5.L$2 = r10     // Catch:{ all -> 0x0135 }
            r5.L$3 = r7     // Catch:{ all -> 0x0135 }
            r5.L$4 = r13     // Catch:{ all -> 0x0135 }
            r5.L$5 = r15     // Catch:{ all -> 0x0135 }
            r5.L$6 = r8     // Catch:{ all -> 0x0135 }
            r5.L$7 = r9     // Catch:{ all -> 0x0135 }
            r18 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x0129 }
            java.lang.Object r1 = r9.hasNext(r5)     // Catch:{ all -> 0x0129 }
            if (r1 != r4) goto L_0x00cf
            return r4
        L_0x00cf:
            r21 = r3
            r3 = r1
            r1 = r18
            r18 = r17
            r17 = r4
            r4 = r21
        L_0x00da:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x011a }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x011a }
            if (r3 == 0) goto L_0x010b
            java.lang.Object r3 = r9.next()     // Catch:{ all -> 0x011a }
            r22 = r3
            r19 = 0
            r0 = r22
            java.lang.Object r20 = r2.invoke(r0)     // Catch:{ all -> 0x011a }
            java.lang.Boolean r20 = (java.lang.Boolean) r20     // Catch:{ all -> 0x011a }
            boolean r20 = r20.booleanValue()     // Catch:{ all -> 0x011a }
            if (r20 == 0) goto L_0x0103
            r3 = 2
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r13, r15)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            goto L_0x0119
        L_0x0103:
            r3 = r4
            r4 = r17
            r17 = r18
            r0 = 0
            goto L_0x00b3
        L_0x010b:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x011a }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r13, r15)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            r0 = 0
        L_0x0119:
            return r0
        L_0x011a:
            r0 = move-exception
            r3 = r4
            r4 = r7
            r7 = r10
            r8 = r11
            r10 = r13
            r11 = r14
            r12 = r15
            r14 = r16
            r15 = r18
            r18 = r1
            goto L_0x014b
        L_0x0129:
            r0 = move-exception
            r4 = r7
            r7 = r10
            r8 = r11
            r10 = r13
            r11 = r14
            r12 = r15
            r14 = r16
            r15 = r17
            goto L_0x014b
        L_0x0135:
            r0 = move-exception
            r18 = r1
            r4 = r7
            r7 = r10
            r8 = r11
            r10 = r13
            r11 = r14
            r12 = r15
            r14 = r16
            r15 = r17
            goto L_0x014b
        L_0x0143:
            r0 = move-exception
            r18 = r22
            r5 = r2
            r6 = r3
            r2 = r23
            r3 = r1
        L_0x014b:
            r1 = r0
            throw r0     // Catch:{ all -> 0x014e }
        L_0x014e:
            r0 = move-exception
            r9 = r0
            r12 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r12)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r12)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.find(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object find$$forInline(ReceiveChannel $this$find, Function1 predicate, Continuation continuation) {
        ReceiveChannel $this$consume$iv$iv$iv = $this$find;
        Throwable cause$iv$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it$iv = it.next();
                    try {
                        if (((Boolean) predicate.invoke(it$iv)).booleanValue()) {
                            InlineMarker.finallyStart(2);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv);
                            InlineMarker.finallyEnd(2);
                            return it$iv;
                        }
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv$iv = th;
                        Throwable cause$iv$iv$iv2 = e$iv$iv$iv;
                        try {
                            throw e$iv$iv$iv;
                        } catch (Throwable e$iv$iv$iv) {
                            Throwable th2 = e$iv$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return null;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv$iv22 = e$iv$iv$iv;
            throw e$iv$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: kotlin.jvm.internal.Ref$ObjectRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00e8  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00fd A[Catch:{ all -> 0x0133 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object findLast(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r23, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r24, kotlin.coroutines.Continuation<? super E> r25) {
        /*
            r1 = r25
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$findLast$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$findLast$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$findLast$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$findLast$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$findLast$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x0096
            if (r4 != r5) goto L_0x008e
            r4 = r6
            r7 = r6
            r8 = r6
            r9 = 0
            r10 = r9
            r11 = r9
            r12 = r6
            r13 = r6
            r14 = r9
            r15 = r9
            java.lang.Object r5 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            r16 = r0
            java.lang.Object r0 = r2.L$7
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            java.lang.Object r8 = r2.L$6
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            java.lang.Object r13 = r2.L$5
            r12 = r13
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r13 = r2.L$4
            r6 = r13
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r13 = r2.L$3
            r4 = r13
            kotlin.jvm.internal.Ref$ObjectRef r4 = (kotlin.jvm.internal.Ref.ObjectRef) r4
            java.lang.Object r13 = r2.L$2
            r7 = r13
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r13 = r2.L$1
            kotlin.jvm.functions.Function1 r13 = (kotlin.jvm.functions.Function1) r13
            r17 = r0
            java.lang.Object r0 = r2.L$0
            r18 = r0
            kotlinx.coroutines.channels.ReceiveChannel r18 = (kotlinx.coroutines.channels.ReceiveChannel) r18
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0082 }
            r0 = r17
            r17 = r14
            r14 = r11
            r11 = r8
            r8 = r6
            r6 = r3
            r21 = r4
            r4 = r1
            r1 = r18
            r18 = r15
            r15 = r12
            r12 = r9
            r9 = r7
            r7 = r21
            r22 = r5
            r5 = r2
            r2 = r13
            r13 = r10
            r10 = r22
            goto L_0x00f5
        L_0x0082:
            r0 = move-exception
            r5 = r2
            r2 = r13
            r16 = r14
            r14 = r11
            r11 = r8
            r8 = r6
            r6 = r3
            r3 = r1
            goto L_0x015f
        L_0x008e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0096:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r15 = 0
            r7 = r23
            r9 = 0
            kotlin.jvm.internal.Ref$ObjectRef r0 = new kotlin.jvm.internal.Ref$ObjectRef
            r0.<init>()
            r0.element = r6
            r4 = r0
            r5 = r7
            r11 = 0
            r12 = r5
            r14 = 0
            r8 = r6
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            r0 = r12
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r0.iterator()     // Catch:{ all -> 0x0152 }
            r13 = r6
            r17 = r15
            r6 = r3
            r15 = r12
            r3 = r1
            r12 = r9
            r1 = r23
            r9 = r7
            r7 = r4
            r4 = r16
            r16 = r14
            r14 = r11
            r11 = r8
            r8 = r5
            r5 = r2
            r2 = r24
        L_0x00ca:
            r5.L$0 = r1     // Catch:{ all -> 0x0148 }
            r5.L$1 = r2     // Catch:{ all -> 0x0148 }
            r5.L$2 = r9     // Catch:{ all -> 0x0148 }
            r5.L$3 = r7     // Catch:{ all -> 0x0148 }
            r5.L$4 = r8     // Catch:{ all -> 0x0148 }
            r5.L$5 = r15     // Catch:{ all -> 0x0148 }
            r5.L$6 = r11     // Catch:{ all -> 0x0148 }
            r5.L$7 = r0     // Catch:{ all -> 0x0148 }
            r5.L$8 = r10     // Catch:{ all -> 0x0148 }
            r18 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x0140 }
            java.lang.Object r1 = r10.hasNext(r5)     // Catch:{ all -> 0x0140 }
            if (r1 != r4) goto L_0x00e8
            return r4
        L_0x00e8:
            r21 = r3
            r3 = r1
            r1 = r18
            r18 = r17
            r17 = r16
            r16 = r4
            r4 = r21
        L_0x00f5:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0133 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0133 }
            if (r3 == 0) goto L_0x0121
            java.lang.Object r3 = r10.next()     // Catch:{ all -> 0x0133 }
            r23 = r3
            r19 = 0
            r24 = r0
            r0 = r23
            java.lang.Object r20 = r2.invoke(r0)     // Catch:{ all -> 0x0133 }
            java.lang.Boolean r20 = (java.lang.Boolean) r20     // Catch:{ all -> 0x0133 }
            boolean r20 = r20.booleanValue()     // Catch:{ all -> 0x0133 }
            if (r20 == 0) goto L_0x0117
            r7.element = r0     // Catch:{ all -> 0x0133 }
        L_0x0117:
            r0 = r24
            r3 = r4
            r4 = r16
            r16 = r17
            r17 = r18
            goto L_0x00ca
        L_0x0121:
            r24 = r0
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0133 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r11)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            T r0 = r7.element
            return r0
        L_0x0133:
            r0 = move-exception
            r3 = r4
            r4 = r7
            r7 = r9
            r9 = r12
            r12 = r15
            r16 = r17
            r15 = r18
            r18 = r1
            goto L_0x015f
        L_0x0140:
            r0 = move-exception
            r4 = r7
            r7 = r9
            r9 = r12
            r12 = r15
            r15 = r17
            goto L_0x015f
        L_0x0148:
            r0 = move-exception
            r18 = r1
            r4 = r7
            r7 = r9
            r9 = r12
            r12 = r15
            r15 = r17
            goto L_0x015f
        L_0x0152:
            r0 = move-exception
            r18 = r23
            r6 = r3
            r16 = r14
            r3 = r1
            r14 = r11
            r11 = r8
            r8 = r5
            r5 = r2
            r2 = r24
        L_0x015f:
            r1 = r0
            throw r0     // Catch:{ all -> 0x0162 }
        L_0x0162:
            r0 = move-exception
            r10 = r0
            r11 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r11)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r11)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.findLast(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object findLast$$forInline(ReceiveChannel $this$findLast, Function1 predicate, Continuation continuation) {
        Object last$iv = null;
        ReceiveChannel $this$consume$iv$iv$iv = $this$findLast;
        Throwable cause$iv$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it$iv = it.next();
                    try {
                        if (((Boolean) predicate.invoke(it$iv)).booleanValue()) {
                            last$iv = it$iv;
                        }
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv$iv = th;
                        Throwable cause$iv$iv$iv2 = e$iv$iv$iv;
                        try {
                            throw e$iv$iv$iv;
                        } catch (Throwable e$iv$iv$iv) {
                            Throwable th2 = e$iv$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return last$iv;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv$iv22 = e$iv$iv$iv;
            throw e$iv$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0083 A[Catch:{ all -> 0x004b }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x008b A[SYNTHETIC, Splitter:B:28:0x008b] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object first(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r10, kotlin.coroutines.Continuation<? super E> r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$first$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$first$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$first$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$first$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$first$1
            r0.<init>(r11)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            r5 = 0
            if (r3 == 0) goto L_0x0055
            if (r3 != r4) goto L_0x004d
            r2 = r5
            r3 = 0
            r4 = r3
            r6 = r5
            r7 = r5
            java.lang.Object r8 = r0.L$4
            r7 = r8
            kotlinx.coroutines.channels.ChannelIterator r7 = (kotlinx.coroutines.channels.ChannelIterator) r7
            java.lang.Object r8 = r0.L$3
            r5 = r8
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r8 = r0.L$2
            r6 = r8
            java.lang.Throwable r6 = (java.lang.Throwable) r6
            java.lang.Object r8 = r0.L$1
            r2 = r8
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            java.lang.Object r8 = r0.L$0
            r10 = r8
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x004b }
            r8 = r4
            r4 = r1
            goto L_0x007b
        L_0x004b:
            r4 = move-exception
            goto L_0x0099
        L_0x004d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0055:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r10
            r6 = 0
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            r7 = r3
            r8 = 0
            kotlinx.coroutines.channels.ChannelIterator r9 = r7.iterator()     // Catch:{ all -> 0x0095 }
            r0.L$0 = r10     // Catch:{ all -> 0x0095 }
            r0.L$1 = r3     // Catch:{ all -> 0x0095 }
            r0.L$2 = r5     // Catch:{ all -> 0x0095 }
            r0.L$3 = r7     // Catch:{ all -> 0x0095 }
            r0.L$4 = r9     // Catch:{ all -> 0x0095 }
            r0.label = r4     // Catch:{ all -> 0x0095 }
            java.lang.Object r4 = r9.hasNext(r0)     // Catch:{ all -> 0x0095 }
            if (r4 != r2) goto L_0x0076
            return r2
        L_0x0076:
            r2 = r3
            r3 = r6
            r6 = r5
            r5 = r7
            r7 = r9
        L_0x007b:
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ all -> 0x004b }
            boolean r4 = r4.booleanValue()     // Catch:{ all -> 0x004b }
            if (r4 == 0) goto L_0x008b
            java.lang.Object r4 = r7.next()     // Catch:{ all -> 0x004b }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r6)
            return r4
        L_0x008b:
            java.util.NoSuchElementException r4 = new java.util.NoSuchElementException     // Catch:{ all -> 0x004b }
            java.lang.String r9 = "ReceiveChannel is empty."
            r4.<init>(r9)     // Catch:{ all -> 0x004b }
            java.lang.Throwable r4 = (java.lang.Throwable) r4     // Catch:{ all -> 0x004b }
            throw r4     // Catch:{ all -> 0x004b }
        L_0x0095:
            r4 = move-exception
            r2 = r3
            r3 = r6
            r6 = r5
        L_0x0099:
            r5 = r4
            throw r4     // Catch:{ all -> 0x009c }
        L_0x009c:
            r4 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.first(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v9, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00c7 A[Catch:{ all -> 0x0109 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object first(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r20, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r21, kotlin.coroutines.Continuation<? super E> r22) {
        /*
            r1 = r22
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$first$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$first$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$first$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$first$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$first$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L_0x007e
            if (r4 != r6) goto L_0x0076
            r4 = r5
            r7 = r5
            r8 = r5
            r9 = 0
            r10 = r9
            r11 = r9
            r12 = r9
            java.lang.Object r13 = r2.L$6
            kotlinx.coroutines.channels.ChannelIterator r13 = (kotlinx.coroutines.channels.ChannelIterator) r13
            java.lang.Object r14 = r2.L$5
            r5 = r14
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r14 = r2.L$4
            r8 = r14
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            java.lang.Object r14 = r2.L$3
            r4 = r14
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r14 = r2.L$2
            r7 = r14
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r14 = r2.L$1
            kotlin.jvm.functions.Function1 r14 = (kotlin.jvm.functions.Function1) r14
            java.lang.Object r15 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r15 = (kotlinx.coroutines.channels.ReceiveChannel) r15
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006a }
            r6 = r3
            r18 = r4
            r4 = r1
            r1 = r15
            r15 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r18
            r19 = r5
            r5 = r2
            r2 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r19
            goto L_0x00bf
        L_0x006a:
            r0 = move-exception
            r5 = r3
            r13 = r11
            r3 = r1
            r11 = r9
            r1 = r15
            r9 = r7
            r7 = r4
            r4 = r2
            r2 = r14
            goto L_0x0120
        L_0x0076:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x007e:
            kotlin.ResultKt.throwOnFailure(r3)
            r11 = 0
            r7 = r20
            r9 = 0
            r4 = r7
            r12 = 0
            r8 = r5
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            r5 = r4
            r10 = 0
            kotlinx.coroutines.channels.ChannelIterator r13 = r5.iterator()     // Catch:{ all -> 0x0114 }
            r14 = r12
            r15 = r13
            r12 = r10
            r13 = r11
            r10 = r8
            r11 = r9
            r8 = r5
            r9 = r7
            r5 = r3
            r7 = r4
            r3 = r1
            r4 = r2
            r1 = r20
            r2 = r21
        L_0x00a1:
            r4.L$0 = r1     // Catch:{ all -> 0x0110 }
            r4.L$1 = r2     // Catch:{ all -> 0x0110 }
            r4.L$2 = r9     // Catch:{ all -> 0x0110 }
            r4.L$3 = r7     // Catch:{ all -> 0x0110 }
            r4.L$4 = r10     // Catch:{ all -> 0x0110 }
            r4.L$5 = r8     // Catch:{ all -> 0x0110 }
            r4.L$6 = r15     // Catch:{ all -> 0x0110 }
            r4.label = r6     // Catch:{ all -> 0x0110 }
            java.lang.Object r6 = r15.hasNext(r4)     // Catch:{ all -> 0x0110 }
            if (r6 != r0) goto L_0x00b8
            return r0
        L_0x00b8:
            r18 = r4
            r4 = r3
            r3 = r6
            r6 = r5
            r5 = r18
        L_0x00bf:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0109 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0109 }
            if (r3 == 0) goto L_0x00f2
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x0109 }
            r20 = r3
            r16 = 0
            r21 = r0
            r0 = r20
            java.lang.Object r17 = r2.invoke(r0)     // Catch:{ all -> 0x0109 }
            java.lang.Boolean r17 = (java.lang.Boolean) r17     // Catch:{ all -> 0x0109 }
            boolean r17 = r17.booleanValue()     // Catch:{ all -> 0x0109 }
            if (r17 == 0) goto L_0x00ea
            r3 = 2
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r10)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r0
        L_0x00ea:
            r0 = r21
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = 1
            goto L_0x00a1
        L_0x00f2:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0109 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r10)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            java.util.NoSuchElementException r0 = new java.util.NoSuchElementException
            java.lang.String r3 = "ReceiveChannel contains no element matching the predicate."
            r0.<init>(r3)
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            throw r0
        L_0x0109:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r5 = r6
            r8 = r10
            r12 = r14
            goto L_0x0120
        L_0x0110:
            r0 = move-exception
            r8 = r10
            r12 = r14
            goto L_0x0120
        L_0x0114:
            r0 = move-exception
            r5 = r3
            r13 = r11
            r3 = r1
            r11 = r9
            r1 = r20
            r9 = r7
            r7 = r4
            r4 = r2
            r2 = r21
        L_0x0120:
            r6 = r0
            throw r0     // Catch:{ all -> 0x0123 }
        L_0x0123:
            r0 = move-exception
            r8 = r0
            r10 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.first(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object first$$forInline(ReceiveChannel $this$first, Function1 predicate, Continuation continuation) {
        Object it;
        ReceiveChannel $this$consume$iv$iv = $this$first;
        Throwable cause$iv$iv = null;
        try {
            ChannelIterator it2 = $this$consume$iv$iv.iterator();
            do {
                InlineMarker.mark(0);
                Object hasNext = it2.hasNext(continuation);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    it = it2.next();
                    try {
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    throw new NoSuchElementException("ReceiveChannel contains no element matching the predicate.");
                }
            } while (!((Boolean) predicate.invoke(it)).booleanValue());
            InlineMarker.finallyStart(2);
            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
            InlineMarker.finallyEnd(2);
            return it;
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0089 A[SYNTHETIC, Splitter:B:27:0x0089] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object firstOrNull(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r11, kotlin.coroutines.Continuation<? super E> r12) {
        /*
            boolean r0 = r12 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$firstOrNull$1
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$firstOrNull$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$firstOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$firstOrNull$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$firstOrNull$1
            r0.<init>(r12)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            r5 = 0
            if (r3 == 0) goto L_0x0056
            if (r3 != r4) goto L_0x004e
            r2 = r5
            r3 = 0
            r4 = r3
            r6 = r5
            r7 = r5
            r8 = r5
            java.lang.Object r9 = r0.L$4
            r7 = r9
            kotlinx.coroutines.channels.ChannelIterator r7 = (kotlinx.coroutines.channels.ChannelIterator) r7
            java.lang.Object r9 = r0.L$3
            r8 = r9
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r9 = r0.L$2
            r6 = r9
            java.lang.Throwable r6 = (java.lang.Throwable) r6
            java.lang.Object r9 = r0.L$1
            r2 = r9
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            java.lang.Object r9 = r0.L$0
            r11 = r9
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x004c }
            r9 = r4
            r4 = r1
            goto L_0x007c
        L_0x004c:
            r4 = move-exception
            goto L_0x0095
        L_0x004e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0056:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r11
            r6 = 0
            r7 = r5
            java.lang.Throwable r7 = (java.lang.Throwable) r7
            r8 = r3
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r8.iterator()     // Catch:{ all -> 0x0091 }
            r0.L$0 = r11     // Catch:{ all -> 0x0091 }
            r0.L$1 = r3     // Catch:{ all -> 0x0091 }
            r0.L$2 = r7     // Catch:{ all -> 0x0091 }
            r0.L$3 = r8     // Catch:{ all -> 0x0091 }
            r0.L$4 = r10     // Catch:{ all -> 0x0091 }
            r0.label = r4     // Catch:{ all -> 0x0091 }
            java.lang.Object r4 = r10.hasNext(r0)     // Catch:{ all -> 0x0091 }
            if (r4 != r2) goto L_0x0078
            return r2
        L_0x0078:
            r2 = r3
            r3 = r6
            r6 = r7
            r7 = r10
        L_0x007c:
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ all -> 0x004c }
            boolean r4 = r4.booleanValue()     // Catch:{ all -> 0x004c }
            if (r4 != 0) goto L_0x0089
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r6)
            return r5
        L_0x0089:
            java.lang.Object r4 = r7.next()     // Catch:{ all -> 0x004c }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r6)
            return r4
        L_0x0091:
            r4 = move-exception
            r2 = r3
            r3 = r6
            r6 = r7
        L_0x0095:
            r5 = r4
            throw r4     // Catch:{ all -> 0x0098 }
        L_0x0098:
            r4 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.firstOrNull(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b9  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00c8 A[Catch:{ all -> 0x0102 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object firstOrNull(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r19, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r20, kotlin.coroutines.Continuation<? super E> r21) {
        /*
            r1 = r21
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$firstOrNull$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$firstOrNull$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$firstOrNull$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$firstOrNull$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$firstOrNull$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x007e
            if (r4 != r5) goto L_0x0076
            r4 = r6
            r7 = r6
            r8 = r6
            r9 = r6
            r10 = 0
            r11 = r10
            r12 = r10
            r13 = r10
            java.lang.Object r14 = r2.L$6
            kotlinx.coroutines.channels.ChannelIterator r14 = (kotlinx.coroutines.channels.ChannelIterator) r14
            java.lang.Object r15 = r2.L$5
            r9 = r15
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r15 = r2.L$4
            r8 = r15
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            java.lang.Object r15 = r2.L$3
            r4 = r15
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r15 = r2.L$2
            r7 = r15
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            java.lang.Object r5 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0068 }
            r6 = r3
            r18 = r4
            r4 = r1
            r1 = r5
            r5 = r2
            r2 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r18
            goto L_0x00c0
        L_0x0068:
            r0 = move-exception
            r9 = r8
            r8 = r7
            r7 = r4
            r4 = r2
            r2 = r15
            r18 = r3
            r3 = r1
            r1 = r5
            r5 = r18
            goto L_0x011a
        L_0x0076:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x007e:
            kotlin.ResultKt.throwOnFailure(r3)
            r12 = 0
            r7 = r19
            r10 = 0
            r4 = r7
            r13 = 0
            r8 = r6
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            r5 = r4
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r11 = r5.iterator()     // Catch:{ all -> 0x010f }
            r15 = r11
            r14 = r13
            r11 = r10
            r13 = r12
            r10 = r5
            r12 = r9
            r5 = r3
            r9 = r8
            r3 = r1
            r8 = r7
            r1 = r19
            r7 = r4
            r4 = r2
            r2 = r20
        L_0x00a1:
            r4.L$0 = r1     // Catch:{ all -> 0x010a }
            r4.L$1 = r2     // Catch:{ all -> 0x010a }
            r4.L$2 = r8     // Catch:{ all -> 0x010a }
            r4.L$3 = r7     // Catch:{ all -> 0x010a }
            r4.L$4 = r9     // Catch:{ all -> 0x010a }
            r4.L$5 = r10     // Catch:{ all -> 0x010a }
            r4.L$6 = r15     // Catch:{ all -> 0x010a }
            r6 = 1
            r4.label = r6     // Catch:{ all -> 0x010a }
            java.lang.Object r6 = r15.hasNext(r4)     // Catch:{ all -> 0x010a }
            if (r6 != r0) goto L_0x00b9
            return r0
        L_0x00b9:
            r18 = r4
            r4 = r3
            r3 = r6
            r6 = r5
            r5 = r18
        L_0x00c0:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0102 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0102 }
            if (r3 == 0) goto L_0x00f3
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x0102 }
            r19 = r3
            r16 = 0
            r20 = r0
            r0 = r19
            java.lang.Object r17 = r2.invoke(r0)     // Catch:{ all -> 0x0102 }
            java.lang.Boolean r17 = (java.lang.Boolean) r17     // Catch:{ all -> 0x0102 }
            boolean r17 = r17.booleanValue()     // Catch:{ all -> 0x0102 }
            if (r17 == 0) goto L_0x00eb
            r3 = 2
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r9)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r0
        L_0x00eb:
            r0 = r20
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = 0
            goto L_0x00a1
        L_0x00f3:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0102 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r9)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            r0 = 0
            return r0
        L_0x0102:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r5 = r6
            r10 = r11
            r12 = r13
            r13 = r14
            goto L_0x011a
        L_0x010a:
            r0 = move-exception
            r10 = r11
            r12 = r13
            r13 = r14
            goto L_0x011a
        L_0x010f:
            r0 = move-exception
            r5 = r3
            r9 = r8
            r3 = r1
            r8 = r7
            r1 = r19
            r7 = r4
            r4 = r2
            r2 = r20
        L_0x011a:
            r6 = r0
            throw r0     // Catch:{ all -> 0x011d }
        L_0x011d:
            r0 = move-exception
            r9 = r0
            r11 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r11)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r11)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.firstOrNull(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object firstOrNull$$forInline(ReceiveChannel $this$firstOrNull, Function1 predicate, Continuation continuation) {
        Object it;
        ReceiveChannel $this$consume$iv$iv = $this$firstOrNull;
        Throwable cause$iv$iv = null;
        try {
            ChannelIterator it2 = $this$consume$iv$iv.iterator();
            do {
                InlineMarker.mark(0);
                Object hasNext = it2.hasNext(continuation);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    it = it2.next();
                    try {
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return null;
                }
            } while (!((Boolean) predicate.invoke(it)).booleanValue());
            InlineMarker.finallyStart(2);
            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
            InlineMarker.finallyEnd(2);
            return it;
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: kotlin.jvm.internal.Ref$IntRef} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00cd A[Catch:{ all -> 0x0106 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object indexOf(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r20, E r21, kotlin.coroutines.Continuation<? super java.lang.Integer> r22) {
        /*
            r1 = r22
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$indexOf$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$indexOf$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$indexOf$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$indexOf$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$indexOf$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            r7 = 0
            if (r4 == 0) goto L_0x007f
            if (r4 != r5) goto L_0x0077
            r4 = r7
            r8 = r7
            r9 = r7
            r10 = r7
            r11 = r6
            r12 = r6
            java.lang.Object r13 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r13 = (kotlinx.coroutines.channels.ChannelIterator) r13
            java.lang.Object r14 = r2.L$6
            r10 = r14
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r14 = r2.L$5
            r9 = r14
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            java.lang.Object r14 = r2.L$4
            r4 = r14
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r14 = r2.L$3
            r8 = r14
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r14 = r2.L$2
            r7 = r14
            kotlin.jvm.internal.Ref$IntRef r7 = (kotlin.jvm.internal.Ref.IntRef) r7
            java.lang.Object r14 = r2.L$1
            java.lang.Object r15 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r15 = (kotlinx.coroutines.channels.ReceiveChannel) r15
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006a }
            r5 = r2
            r2 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r7
            r7 = r6
            r6 = r3
            r19 = r4
            r4 = r1
            r1 = r15
            r15 = r13
            r13 = r11
            r11 = r9
            r9 = r19
            goto L_0x00c5
        L_0x006a:
            r0 = move-exception
            r10 = r8
            r11 = r9
            r9 = r4
            r8 = r7
            r4 = r2
            r7 = r6
            r2 = r14
            r6 = r3
            r3 = r1
            r1 = r15
            goto L_0x011a
        L_0x0077:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x007f:
            kotlin.ResultKt.throwOnFailure(r3)
            kotlin.jvm.internal.Ref$IntRef r4 = new kotlin.jvm.internal.Ref$IntRef
            r4.<init>()
            r4.element = r6
            r8 = r20
            r6 = 0
            r9 = r8
            r12 = 0
            java.lang.Throwable r7 = (java.lang.Throwable) r7
            r10 = r9
            r11 = 0
            kotlinx.coroutines.channels.ChannelIterator r13 = r10.iterator()     // Catch:{ all -> 0x010e }
            r14 = r12
            r15 = r13
            r12 = r10
            r13 = r11
            r11 = r7
            r10 = r8
            r8 = r4
            r7 = r6
            r4 = r2
            r6 = r3
            r2 = r21
            r3 = r1
            r1 = r20
        L_0x00a6:
            r4.L$0 = r1     // Catch:{ all -> 0x010b }
            r4.L$1 = r2     // Catch:{ all -> 0x010b }
            r4.L$2 = r8     // Catch:{ all -> 0x010b }
            r4.L$3 = r10     // Catch:{ all -> 0x010b }
            r4.L$4 = r9     // Catch:{ all -> 0x010b }
            r4.L$5 = r11     // Catch:{ all -> 0x010b }
            r4.L$6 = r12     // Catch:{ all -> 0x010b }
            r4.L$7 = r15     // Catch:{ all -> 0x010b }
            r4.label = r5     // Catch:{ all -> 0x010b }
            java.lang.Object r5 = r15.hasNext(r4)     // Catch:{ all -> 0x010b }
            if (r5 != r0) goto L_0x00bf
            return r0
        L_0x00bf:
            r19 = r4
            r4 = r3
            r3 = r5
            r5 = r19
        L_0x00c5:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0106 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0106 }
            if (r3 == 0) goto L_0x00fa
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x0106 }
            r20 = r3
            r17 = 0
            r21 = r0
            r0 = r20
            boolean r18 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r2, (java.lang.Object) r0)     // Catch:{ all -> 0x0106 }
            if (r18 == 0) goto L_0x00e9
            int r15 = r8.element     // Catch:{ all -> 0x0106 }
            java.lang.Integer r15 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r15)     // Catch:{ all -> 0x0106 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r11)
            return r15
        L_0x00e9:
            r18 = r0
            int r0 = r8.element     // Catch:{ all -> 0x0106 }
            r16 = 1
            int r0 = r0 + 1
            r8.element = r0     // Catch:{ all -> 0x0106 }
            r0 = r21
            r3 = r4
            r4 = r5
            r5 = r16
            goto L_0x00a6
        L_0x00fa:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0106 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r11)
            r0 = -1
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)
            return r0
        L_0x0106:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r12 = r14
            goto L_0x011a
        L_0x010b:
            r0 = move-exception
            r12 = r14
            goto L_0x011a
        L_0x010e:
            r0 = move-exception
            r11 = r7
            r10 = r8
            r8 = r4
            r7 = r6
            r4 = r2
            r6 = r3
            r2 = r21
            r3 = r1
            r1 = r20
        L_0x011a:
            r5 = r0
            throw r0     // Catch:{ all -> 0x011d }
        L_0x011d:
            r0 = move-exception
            r11 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r5)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.indexOf(kotlinx.coroutines.channels.ReceiveChannel, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v10, resolved type: kotlin.jvm.internal.Ref$IntRef} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c9  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00e2 A[Catch:{ all -> 0x013b }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object indexOfFirst(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r23, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r24, kotlin.coroutines.Continuation<? super java.lang.Integer> r25) {
        /*
            r1 = r25
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$indexOfFirst$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$indexOfFirst$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$indexOfFirst$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$indexOfFirst$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$indexOfFirst$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 0
            r7 = 1
            if (r4 == 0) goto L_0x0082
            if (r4 != r7) goto L_0x007a
            r4 = r5
            r8 = r6
            r9 = r5
            r10 = r6
            r11 = r5
            r12 = r6
            r13 = r6
            java.lang.Object r14 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r14 = (kotlinx.coroutines.channels.ChannelIterator) r14
            java.lang.Object r15 = r2.L$6
            r12 = r15
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r15 = r2.L$5
            r10 = r15
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r15 = r2.L$4
            r13 = r15
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            java.lang.Object r15 = r2.L$3
            r6 = r15
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r15 = r2.L$2
            r8 = r15
            kotlin.jvm.internal.Ref$IntRef r8 = (kotlin.jvm.internal.Ref.IntRef) r8
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            java.lang.Object r7 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006c }
            r17 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r4
            r4 = r1
            r1 = r7
            r7 = r5
            r5 = r2
            r2 = r15
            r15 = r13
            r13 = r11
            r11 = r9
            r9 = r6
            r6 = r3
            goto L_0x00da
        L_0x006c:
            r0 = move-exception
            r11 = r9
            r9 = r6
            r6 = r5
            r5 = r2
            r2 = r15
            r20 = r4
            r4 = r1
            r1 = r7
            r7 = r20
            goto L_0x0177
        L_0x007a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0082:
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = 0
            kotlin.jvm.internal.Ref$IntRef r7 = new kotlin.jvm.internal.Ref$IntRef
            r7.<init>()
            r7.element = r5
            r8 = r7
            r5 = r23
            r7 = 0
            r13 = r5
            r9 = 0
            r10 = r6
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r6 = r13
            r11 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r6.iterator()     // Catch:{ all -> 0x016d }
            r15 = r12
            r14 = r13
            r13 = r6
            r12 = r11
            r6 = r4
            r11 = r10
            r4 = r2
            r10 = r9
            r2 = r24
            r9 = r8
            r8 = r5
            r5 = r3
            r3 = r1
            r1 = r23
        L_0x00ad:
            r4.L$0 = r1     // Catch:{ all -> 0x0158 }
            r4.L$1 = r2     // Catch:{ all -> 0x0158 }
            r4.L$2 = r9     // Catch:{ all -> 0x0158 }
            r4.L$3 = r8     // Catch:{ all -> 0x0158 }
            r4.L$4 = r14     // Catch:{ all -> 0x0158 }
            r4.L$5 = r11     // Catch:{ all -> 0x0158 }
            r4.L$6 = r13     // Catch:{ all -> 0x0158 }
            r4.L$7 = r15     // Catch:{ all -> 0x0158 }
            r23 = r1
            r1 = 1
            r4.label = r1     // Catch:{ all -> 0x0143 }
            java.lang.Object r1 = r15.hasNext(r4)     // Catch:{ all -> 0x0143 }
            if (r1 != r0) goto L_0x00c9
            return r0
        L_0x00c9:
            r17 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r23
        L_0x00da:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x013b }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x013b }
            if (r3 == 0) goto L_0x0128
            java.lang.Object r3 = r17.next()     // Catch:{ all -> 0x013b }
            r23 = r3
            r18 = 0
            r24 = r0
            r0 = r23
            java.lang.Object r19 = r2.invoke(r0)     // Catch:{ all -> 0x013b }
            java.lang.Boolean r19 = (java.lang.Boolean) r19     // Catch:{ all -> 0x013b }
            boolean r19 = r19.booleanValue()     // Catch:{ all -> 0x013b }
            if (r19 == 0) goto L_0x010d
            r19 = r0
            int r0 = r10.element     // Catch:{ all -> 0x013b }
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)     // Catch:{ all -> 0x013b }
            r3 = 2
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r0
        L_0x010d:
            r19 = r0
            int r0 = r10.element     // Catch:{ all -> 0x013b }
            r16 = 1
            int r0 = r0 + 1
            r10.element = r0     // Catch:{ all -> 0x013b }
            r0 = r24
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r15 = r17
            goto L_0x00ad
        L_0x0128:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x013b }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            r0 = -1
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)
            return r0
        L_0x013b:
            r0 = move-exception
            r3 = r6
            r6 = r7
            r7 = r8
            r8 = r10
            r10 = r12
            r13 = r15
            goto L_0x0177
        L_0x0143:
            r0 = move-exception
            r1 = r23
            r13 = r14
            r20 = r4
            r4 = r3
            r3 = r5
            r5 = r20
            r21 = r9
            r9 = r8
            r8 = r21
            r22 = r11
            r11 = r10
            r10 = r22
            goto L_0x0177
        L_0x0158:
            r0 = move-exception
            r23 = r1
            r13 = r14
            r20 = r4
            r4 = r3
            r3 = r5
            r5 = r20
            r21 = r9
            r9 = r8
            r8 = r21
            r22 = r11
            r11 = r10
            r10 = r22
            goto L_0x0177
        L_0x016d:
            r0 = move-exception
            r6 = r4
            r11 = r9
            r4 = r1
            r9 = r5
            r1 = r23
            r5 = r2
            r2 = r24
        L_0x0177:
            r10 = r0
            throw r0     // Catch:{ all -> 0x017a }
        L_0x017a:
            r0 = move-exception
            r12 = r0
            r14 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r14)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r13, r10)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r14)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.indexOfFirst(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object indexOfFirst$$forInline(ReceiveChannel $this$indexOfFirst, Function1 predicate, Continuation continuation) {
        int index = 0;
        ReceiveChannel $this$consume$iv$iv = $this$indexOfFirst;
        Throwable cause$iv$iv = null;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    try {
                        if (((Boolean) predicate.invoke(it.next())).booleanValue()) {
                            Integer valueOf = Integer.valueOf(index);
                            InlineMarker.finallyStart(2);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                            InlineMarker.finallyEnd(2);
                            return valueOf;
                        }
                        index++;
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return -1;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v9, resolved type: kotlin.jvm.internal.Ref$IntRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v8, resolved type: kotlin.jvm.internal.Ref$IntRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v2, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00e1  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00ef A[Catch:{ all -> 0x0136 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object indexOfLast(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r22, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r23, kotlin.coroutines.Continuation<? super java.lang.Integer> r24) {
        /*
            r1 = r24
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$indexOfLast$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$indexOfLast$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$indexOfLast$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$indexOfLast$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$indexOfLast$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x0086
            if (r4 != r6) goto L_0x007e
            r4 = r7
            r8 = r7
            r9 = r5
            r10 = r7
            r11 = r5
            r12 = r7
            r13 = r7
            r14 = r5
            java.lang.Object r15 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r15 = (kotlinx.coroutines.channels.ChannelIterator) r15
            java.lang.Object r6 = r2.L$7
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r12 = r2.L$6
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            java.lang.Object r13 = r2.L$5
            r10 = r13
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r13 = r2.L$4
            r4 = r13
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r13 = r2.L$3
            r7 = r13
            kotlin.jvm.internal.Ref$IntRef r7 = (kotlin.jvm.internal.Ref.IntRef) r7
            java.lang.Object r13 = r2.L$2
            r8 = r13
            kotlin.jvm.internal.Ref$IntRef r8 = (kotlin.jvm.internal.Ref.IntRef) r8
            java.lang.Object r13 = r2.L$1
            kotlin.jvm.functions.Function1 r13 = (kotlin.jvm.functions.Function1) r13
            r17 = r0
            java.lang.Object r0 = r2.L$0
            r18 = r0
            kotlinx.coroutines.channels.ReceiveChannel r18 = (kotlinx.coroutines.channels.ReceiveChannel) r18
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0076 }
            r0 = r17
            r17 = r18
            r18 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r6
            r6 = r4
            r4 = r2
            r2 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r5
            r5 = r3
            goto L_0x00e7
        L_0x0076:
            r0 = move-exception
            r6 = r4
            r17 = r18
            r4 = r2
            r2 = r13
            goto L_0x0167
        L_0x007e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0086:
            r17 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r14 = 0
            kotlin.jvm.internal.Ref$IntRef r0 = new kotlin.jvm.internal.Ref$IntRef
            r0.<init>()
            r4 = -1
            r0.element = r4
            r8 = r0
            kotlin.jvm.internal.Ref$IntRef r0 = new kotlin.jvm.internal.Ref$IntRef
            r0.<init>()
            r0.element = r5
            r4 = r0
            r5 = r22
            r6 = 0
            r10 = r5
            r11 = 0
            r12 = r7
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            r0 = r10
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r9 = r0.iterator()     // Catch:{ all -> 0x015b }
            r13 = r11
            r15 = r14
            r11 = r7
            r14 = r12
            r7 = r6
            r12 = r10
            r6 = r5
            r10 = r8
            r8 = r0
            r5 = r3
            r3 = r9
            r0 = r17
            r9 = r4
            r4 = r2
            r2 = r23
            r21 = r1
            r1 = r22
            r22 = r21
        L_0x00c3:
            r4.L$0 = r1     // Catch:{ all -> 0x014d }
            r4.L$1 = r2     // Catch:{ all -> 0x014d }
            r4.L$2 = r10     // Catch:{ all -> 0x014d }
            r4.L$3 = r9     // Catch:{ all -> 0x014d }
            r4.L$4 = r6     // Catch:{ all -> 0x014d }
            r4.L$5 = r12     // Catch:{ all -> 0x014d }
            r4.L$6 = r14     // Catch:{ all -> 0x014d }
            r4.L$7 = r8     // Catch:{ all -> 0x014d }
            r4.L$8 = r3     // Catch:{ all -> 0x014d }
            r17 = r1
            r1 = 1
            r4.label = r1     // Catch:{ all -> 0x0141 }
            java.lang.Object r1 = r3.hasNext(r4)     // Catch:{ all -> 0x0141 }
            if (r1 != r0) goto L_0x00e1
            return r0
        L_0x00e1:
            r18 = r15
            r15 = r3
            r3 = r1
            r1 = r22
        L_0x00e7:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0136 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0136 }
            if (r3 == 0) goto L_0x0122
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x0136 }
            r22 = r3
            r19 = 0
            r23 = r0
            r0 = r22
            java.lang.Object r20 = r2.invoke(r0)     // Catch:{ all -> 0x0136 }
            java.lang.Boolean r20 = (java.lang.Boolean) r20     // Catch:{ all -> 0x0136 }
            boolean r20 = r20.booleanValue()     // Catch:{ all -> 0x0136 }
            if (r20 == 0) goto L_0x010e
            r20 = r0
            int r0 = r9.element     // Catch:{ all -> 0x0136 }
            r10.element = r0     // Catch:{ all -> 0x0136 }
            goto L_0x0110
        L_0x010e:
            r20 = r0
        L_0x0110:
            int r0 = r9.element     // Catch:{ all -> 0x0136 }
            r16 = 1
            int r0 = r0 + 1
            r9.element = r0     // Catch:{ all -> 0x0136 }
            r0 = r23
            r22 = r1
            r3 = r15
            r1 = r17
            r15 = r18
            goto L_0x00c3
        L_0x0122:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0136 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r14)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            int r0 = r10.element
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)
            return r0
        L_0x0136:
            r0 = move-exception
            r3 = r5
            r5 = r7
            r7 = r9
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r14
            r14 = r18
            goto L_0x0167
        L_0x0141:
            r0 = move-exception
            r1 = r22
            r3 = r5
            r5 = r7
            r7 = r9
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r14
            r14 = r15
            goto L_0x0167
        L_0x014d:
            r0 = move-exception
            r17 = r1
            r1 = r22
            r3 = r5
            r5 = r7
            r7 = r9
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r14
            r14 = r15
            goto L_0x0167
        L_0x015b:
            r0 = move-exception
            r17 = r22
            r7 = r4
            r4 = r2
            r2 = r23
            r21 = r6
            r6 = r5
            r5 = r21
        L_0x0167:
            r9 = r0
            throw r0     // Catch:{ all -> 0x016a }
        L_0x016a:
            r0 = move-exception
            r12 = r0
            r13 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r13)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r9)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r13)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.indexOfLast(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object indexOfLast$$forInline(ReceiveChannel $this$indexOfLast, Function1 predicate, Continuation continuation) {
        int lastIndex = -1;
        int index = 0;
        ReceiveChannel $this$consume$iv$iv = $this$indexOfLast;
        Throwable cause$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    try {
                        if (((Boolean) predicate.invoke(it.next())).booleanValue()) {
                            lastIndex = index;
                        }
                        index++;
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return Integer.valueOf(lastIndex);
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v8, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v9, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v4, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v13, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v12, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00af A[Catch:{ all -> 0x0104 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00de A[Catch:{ all -> 0x00f2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00fa A[SYNTHETIC, Splitter:B:42:0x00fa] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object last(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r13, kotlin.coroutines.Continuation<? super E> r14) {
        /*
            boolean r0 = r14 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$last$1
            if (r0 == 0) goto L_0x0014
            r0 = r14
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$last$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$last$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$last$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$last$1
            r0.<init>(r14)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 2
            r5 = 1
            r6 = 0
            if (r3 == 0) goto L_0x0085
            r7 = 0
            if (r3 == r5) goto L_0x0061
            if (r3 != r4) goto L_0x0059
            r3 = r6
            r5 = r7
            r8 = r6
            r9 = r6
            r10 = r6
            java.lang.Object r9 = r0.L$5
            java.lang.Object r11 = r0.L$4
            r10 = r11
            kotlinx.coroutines.channels.ChannelIterator r10 = (kotlinx.coroutines.channels.ChannelIterator) r10
            java.lang.Object r11 = r0.L$3
            r6 = r11
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r11 = r0.L$2
            r8 = r11
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            java.lang.Object r11 = r0.L$1
            r3 = r11
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            java.lang.Object r11 = r0.L$0
            r13 = r11
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0104 }
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r3
            r3 = r2
            r2 = r1
            goto L_0x00d6
        L_0x0059:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0061:
            r3 = r6
            r5 = r7
            r8 = r6
            r9 = r6
            java.lang.Object r10 = r0.L$4
            r9 = r10
            kotlinx.coroutines.channels.ChannelIterator r9 = (kotlinx.coroutines.channels.ChannelIterator) r9
            java.lang.Object r10 = r0.L$3
            r6 = r10
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r10 = r0.L$2
            r8 = r10
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            java.lang.Object r10 = r0.L$1
            r3 = r10
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            java.lang.Object r10 = r0.L$0
            r13 = r10
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0104 }
            r10 = r9
            r9 = r5
            r5 = r1
            goto L_0x00a7
        L_0x0085:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r13
            r7 = 0
            r8 = r6
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            r6 = r3
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r6.iterator()     // Catch:{ all -> 0x0104 }
            r0.L$0 = r13     // Catch:{ all -> 0x0104 }
            r0.L$1 = r3     // Catch:{ all -> 0x0104 }
            r0.L$2 = r8     // Catch:{ all -> 0x0104 }
            r0.L$3 = r6     // Catch:{ all -> 0x0104 }
            r0.L$4 = r10     // Catch:{ all -> 0x0104 }
            r0.label = r5     // Catch:{ all -> 0x0104 }
            java.lang.Object r5 = r10.hasNext(r0)     // Catch:{ all -> 0x0104 }
            if (r5 != r2) goto L_0x00a7
            return r2
        L_0x00a7:
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ all -> 0x0104 }
            boolean r5 = r5.booleanValue()     // Catch:{ all -> 0x0104 }
            if (r5 == 0) goto L_0x00fa
            java.lang.Object r5 = r10.next()     // Catch:{ all -> 0x0104 }
            r12 = r9
            r9 = r5
            r5 = r12
        L_0x00b6:
            r0.L$0 = r13     // Catch:{ all -> 0x0104 }
            r0.L$1 = r3     // Catch:{ all -> 0x0104 }
            r0.L$2 = r8     // Catch:{ all -> 0x0104 }
            r0.L$3 = r6     // Catch:{ all -> 0x0104 }
            r0.L$4 = r10     // Catch:{ all -> 0x0104 }
            r0.L$5 = r9     // Catch:{ all -> 0x0104 }
            r0.label = r4     // Catch:{ all -> 0x0104 }
            java.lang.Object r11 = r10.hasNext(r0)     // Catch:{ all -> 0x0104 }
            if (r11 != r2) goto L_0x00cb
            return r2
        L_0x00cb:
            r12 = r2
            r2 = r1
            r1 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r3
            r3 = r12
        L_0x00d6:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x00f2 }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x00f2 }
            if (r1 == 0) goto L_0x00ed
            java.lang.Object r1 = r11.next()     // Catch:{ all -> 0x00f2 }
            r10 = r11
            r12 = r9
            r9 = r1
            r1 = r2
            r2 = r3
            r3 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r12
            goto L_0x00b6
        L_0x00ed:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r9)
            return r10
        L_0x00f2:
            r1 = move-exception
            r3 = r5
            r7 = r8
            r8 = r9
            r12 = r2
            r2 = r1
            r1 = r12
            goto L_0x0105
        L_0x00fa:
            java.util.NoSuchElementException r2 = new java.util.NoSuchElementException     // Catch:{ all -> 0x0104 }
            java.lang.String r4 = "ReceiveChannel is empty."
            r2.<init>(r4)     // Catch:{ all -> 0x0104 }
            java.lang.Throwable r2 = (java.lang.Throwable) r2     // Catch:{ all -> 0x0104 }
            throw r2     // Catch:{ all -> 0x0104 }
        L_0x0104:
            r2 = move-exception
        L_0x0105:
            r4 = r2
            throw r2     // Catch:{ all -> 0x0108 }
        L_0x0108:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r4)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.last(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v10, resolved type: kotlin.jvm.internal.Ref$BooleanRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v9, resolved type: kotlin.jvm.internal.Ref$ObjectRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00ee A[Catch:{ all -> 0x0146 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x012b  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x012f  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object last(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r21, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r22, kotlin.coroutines.Continuation<? super E> r23) {
        /*
            r1 = r23
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$last$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$last$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$last$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$last$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$last$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x0086
            if (r4 != r6) goto L_0x007e
            r4 = r7
            r8 = r7
            r9 = r5
            r10 = r7
            r11 = r5
            r12 = r7
            r13 = r7
            r14 = r5
            java.lang.Object r15 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r15 = (kotlinx.coroutines.channels.ChannelIterator) r15
            java.lang.Object r6 = r2.L$7
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r12 = r2.L$6
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            java.lang.Object r13 = r2.L$5
            r10 = r13
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r13 = r2.L$4
            r4 = r13
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r13 = r2.L$3
            r7 = r13
            kotlin.jvm.internal.Ref$BooleanRef r7 = (kotlin.jvm.internal.Ref.BooleanRef) r7
            java.lang.Object r13 = r2.L$2
            r8 = r13
            kotlin.jvm.internal.Ref$ObjectRef r8 = (kotlin.jvm.internal.Ref.ObjectRef) r8
            java.lang.Object r13 = r2.L$1
            kotlin.jvm.functions.Function1 r13 = (kotlin.jvm.functions.Function1) r13
            r16 = r0
            java.lang.Object r0 = r2.L$0
            r17 = r0
            kotlinx.coroutines.channels.ReceiveChannel r17 = (kotlinx.coroutines.channels.ReceiveChannel) r17
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0076 }
            r0 = r16
            r16 = r17
            r17 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r6
            r6 = r4
            r4 = r2
            r2 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r5
            r5 = r3
            goto L_0x00e6
        L_0x0076:
            r0 = move-exception
            r6 = r4
            r16 = r17
            r4 = r2
            r2 = r13
            goto L_0x0179
        L_0x007e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0086:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r14 = 0
            kotlin.jvm.internal.Ref$ObjectRef r0 = new kotlin.jvm.internal.Ref$ObjectRef
            r0.<init>()
            r0.element = r7
            r8 = r0
            kotlin.jvm.internal.Ref$BooleanRef r0 = new kotlin.jvm.internal.Ref$BooleanRef
            r0.<init>()
            r0.element = r5
            r4 = r0
            r5 = r21
            r6 = 0
            r10 = r5
            r11 = 0
            r12 = r7
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            r0 = r10
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r9 = r0.iterator()     // Catch:{ all -> 0x016d }
            r13 = r11
            r15 = r14
            r11 = r7
            r14 = r12
            r7 = r6
            r12 = r10
            r6 = r5
            r10 = r8
            r8 = r0
            r5 = r3
            r3 = r9
            r0 = r16
            r9 = r4
            r4 = r2
            r2 = r22
            r20 = r1
            r1 = r21
            r21 = r20
        L_0x00c2:
            r4.L$0 = r1     // Catch:{ all -> 0x015f }
            r4.L$1 = r2     // Catch:{ all -> 0x015f }
            r4.L$2 = r10     // Catch:{ all -> 0x015f }
            r4.L$3 = r9     // Catch:{ all -> 0x015f }
            r4.L$4 = r6     // Catch:{ all -> 0x015f }
            r4.L$5 = r12     // Catch:{ all -> 0x015f }
            r4.L$6 = r14     // Catch:{ all -> 0x015f }
            r4.L$7 = r8     // Catch:{ all -> 0x015f }
            r4.L$8 = r3     // Catch:{ all -> 0x015f }
            r16 = r1
            r1 = 1
            r4.label = r1     // Catch:{ all -> 0x0153 }
            java.lang.Object r1 = r3.hasNext(r4)     // Catch:{ all -> 0x0153 }
            if (r1 != r0) goto L_0x00e0
            return r0
        L_0x00e0:
            r17 = r15
            r15 = r3
            r3 = r1
            r1 = r21
        L_0x00e6:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0146 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0146 }
            if (r3 == 0) goto L_0x0118
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x0146 }
            r21 = r3
            r18 = 0
            r22 = r0
            r0 = r21
            java.lang.Object r19 = r2.invoke(r0)     // Catch:{ all -> 0x0146 }
            java.lang.Boolean r19 = (java.lang.Boolean) r19     // Catch:{ all -> 0x0146 }
            boolean r19 = r19.booleanValue()     // Catch:{ all -> 0x0146 }
            if (r19 == 0) goto L_0x010e
            r10.element = r0     // Catch:{ all -> 0x0146 }
            r21 = r1
            r1 = 1
            r9.element = r1     // Catch:{ all -> 0x0139 }
            goto L_0x0110
        L_0x010e:
            r21 = r1
        L_0x0110:
            r0 = r22
            r3 = r15
            r1 = r16
            r15 = r17
            goto L_0x00c2
        L_0x0118:
            r21 = r1
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0139 }
            r1 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r14)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            boolean r0 = r9.element
            if (r0 == 0) goto L_0x012f
            T r0 = r10.element
            return r0
        L_0x012f:
            java.util.NoSuchElementException r0 = new java.util.NoSuchElementException
            java.lang.String r1 = "ReceiveChannel contains no element matching the predicate."
            r0.<init>(r1)
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            throw r0
        L_0x0139:
            r0 = move-exception
            r1 = r21
            r3 = r5
            r5 = r7
            r7 = r9
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r14
            r14 = r17
            goto L_0x0179
        L_0x0146:
            r0 = move-exception
            r21 = r1
            r3 = r5
            r5 = r7
            r7 = r9
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r14
            r14 = r17
            goto L_0x0179
        L_0x0153:
            r0 = move-exception
            r1 = r21
            r3 = r5
            r5 = r7
            r7 = r9
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r14
            r14 = r15
            goto L_0x0179
        L_0x015f:
            r0 = move-exception
            r16 = r1
            r1 = r21
            r3 = r5
            r5 = r7
            r7 = r9
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r14
            r14 = r15
            goto L_0x0179
        L_0x016d:
            r0 = move-exception
            r16 = r21
            r7 = r4
            r4 = r2
            r2 = r22
            r20 = r6
            r6 = r5
            r5 = r20
        L_0x0179:
            r9 = r0
            throw r0     // Catch:{ all -> 0x017c }
        L_0x017c:
            r0 = move-exception
            r12 = r0
            r13 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r13)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r9)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r13)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.last(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object last$$forInline(ReceiveChannel $this$last, Function1 predicate, Continuation continuation) {
        Object last = null;
        boolean found = false;
        ReceiveChannel $this$consume$iv$iv = $this$last;
        Throwable cause$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (!((Boolean) hasNext).booleanValue()) {
                    break;
                }
                Object it2 = it.next();
                try {
                    if (((Boolean) predicate.invoke(it2)).booleanValue()) {
                        last = it2;
                        found = true;
                    }
                    i = 1;
                } catch (Throwable th) {
                    e$iv$iv = th;
                    Throwable cause$iv$iv2 = e$iv$iv;
                    try {
                        throw e$iv$iv;
                    } catch (Throwable e$iv$iv) {
                        Throwable th2 = e$iv$iv;
                        InlineMarker.finallyStart(1);
                        ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                        InlineMarker.finallyEnd(1);
                        throw th2;
                    }
                }
            }
            Function1 function1 = predicate;
            Unit unit = Unit.INSTANCE;
            InlineMarker.finallyStart(1);
            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
            InlineMarker.finallyEnd(1);
            if (found) {
                return last;
            }
            throw new NoSuchElementException("ReceiveChannel contains no element matching the predicate.");
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v10, resolved type: kotlin.jvm.internal.Ref$IntRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v10, resolved type: kotlin.jvm.internal.Ref$IntRef} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00d7  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00f0 A[Catch:{ all -> 0x0131 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object lastIndexOf(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r21, E r22, kotlin.coroutines.Continuation<? super java.lang.Integer> r23) {
        /*
            r1 = r23
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$lastIndexOf$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$lastIndexOf$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$lastIndexOf$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$lastIndexOf$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$lastIndexOf$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            r7 = 0
            if (r4 == 0) goto L_0x0086
            if (r4 != r5) goto L_0x007e
            r4 = r6
            r8 = r7
            r9 = r6
            r10 = r7
            r11 = r7
            r12 = r7
            r13 = r7
            java.lang.Object r14 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r14 = (kotlinx.coroutines.channels.ChannelIterator) r14
            java.lang.Object r15 = r2.L$7
            r11 = r15
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r15 = r2.L$6
            r10 = r15
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r15 = r2.L$5
            r13 = r15
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            java.lang.Object r15 = r2.L$4
            r7 = r15
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r15 = r2.L$3
            r8 = r15
            kotlin.jvm.internal.Ref$IntRef r8 = (kotlin.jvm.internal.Ref.IntRef) r8
            java.lang.Object r15 = r2.L$2
            r12 = r15
            kotlin.jvm.internal.Ref$IntRef r12 = (kotlin.jvm.internal.Ref.IntRef) r12
            java.lang.Object r15 = r2.L$1
            java.lang.Object r5 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0072 }
            r17 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r6
            r6 = r3
            r20 = r4
            r4 = r1
            r1 = r5
            r5 = r2
            r2 = r15
            r15 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r20
            goto L_0x00e8
        L_0x0072:
            r0 = move-exception
            r6 = r4
            r4 = r2
            r2 = r15
            r20 = r3
            r3 = r1
            r1 = r5
            r5 = r20
            goto L_0x015b
        L_0x007e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0086:
            kotlin.ResultKt.throwOnFailure(r3)
            kotlin.jvm.internal.Ref$IntRef r4 = new kotlin.jvm.internal.Ref$IntRef
            r4.<init>()
            r5 = -1
            r4.element = r5
            r12 = r4
            kotlin.jvm.internal.Ref$IntRef r4 = new kotlin.jvm.internal.Ref$IntRef
            r4.<init>()
            r4.element = r6
            r8 = r4
            r4 = r21
            r5 = 0
            r13 = r4
            r9 = 0
            r10 = r7
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r6 = r13
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r11 = r6.iterator()     // Catch:{ all -> 0x0151 }
            r15 = r11
            r14 = r13
            r11 = r10
            r13 = r12
            r12 = r6
            r10 = r9
            r6 = r5
            r9 = r8
            r5 = r3
            r8 = r4
            r3 = r1
            r4 = r2
            r1 = r21
            r2 = r22
        L_0x00b9:
            r4.L$0 = r1     // Catch:{ all -> 0x0147 }
            r4.L$1 = r2     // Catch:{ all -> 0x0147 }
            r4.L$2 = r13     // Catch:{ all -> 0x0147 }
            r4.L$3 = r9     // Catch:{ all -> 0x0147 }
            r4.L$4 = r8     // Catch:{ all -> 0x0147 }
            r4.L$5 = r14     // Catch:{ all -> 0x0147 }
            r4.L$6 = r11     // Catch:{ all -> 0x0147 }
            r4.L$7 = r12     // Catch:{ all -> 0x0147 }
            r4.L$8 = r15     // Catch:{ all -> 0x0147 }
            r21 = r1
            r1 = 1
            r4.label = r1     // Catch:{ all -> 0x013d }
            java.lang.Object r1 = r15.hasNext(r4)     // Catch:{ all -> 0x013d }
            if (r1 != r0) goto L_0x00d7
            return r0
        L_0x00d7:
            r17 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r21
        L_0x00e8:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0131 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0131 }
            if (r3 == 0) goto L_0x0124
            java.lang.Object r3 = r17.next()     // Catch:{ all -> 0x0131 }
            r21 = r3
            r18 = 0
            r22 = r0
            r0 = r21
            boolean r19 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r2, (java.lang.Object) r0)     // Catch:{ all -> 0x0131 }
            if (r19 == 0) goto L_0x0109
            r19 = r0
            int r0 = r10.element     // Catch:{ all -> 0x0131 }
            r14.element = r0     // Catch:{ all -> 0x0131 }
            goto L_0x010b
        L_0x0109:
            r19 = r0
        L_0x010b:
            int r0 = r10.element     // Catch:{ all -> 0x0131 }
            r16 = 1
            int r0 = r0 + 1
            r10.element = r0     // Catch:{ all -> 0x0131 }
            r0 = r22
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r15 = r17
            goto L_0x00b9
        L_0x0124:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0131 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r12)
            int r0 = r14.element
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)
            return r0
        L_0x0131:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r9
            r8 = r10
            r9 = r11
            r10 = r12
            r12 = r14
            r13 = r15
            goto L_0x015b
        L_0x013d:
            r0 = move-exception
            r1 = r21
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r12 = r13
            r13 = r14
            goto L_0x015b
        L_0x0147:
            r0 = move-exception
            r21 = r1
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r12 = r13
            r13 = r14
            goto L_0x015b
        L_0x0151:
            r0 = move-exception
            r7 = r4
            r6 = r5
            r4 = r2
            r5 = r3
            r2 = r22
            r3 = r1
            r1 = r21
        L_0x015b:
            r10 = r0
            throw r0     // Catch:{ all -> 0x015e }
        L_0x015e:
            r0 = move-exception
            r11 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r13, r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.lastIndexOf(kotlinx.coroutines.channels.ReceiveChannel, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v8, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v9, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v4, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v11, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v12, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b5 A[SYNTHETIC, Splitter:B:30:0x00b5] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00d3  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00e6 A[Catch:{ all -> 0x00fa }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object lastOrNull(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r13, kotlin.coroutines.Continuation<? super E> r14) {
        /*
            boolean r0 = r14 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$lastOrNull$1
            if (r0 == 0) goto L_0x0014
            r0 = r14
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$lastOrNull$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$lastOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$lastOrNull$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$lastOrNull$1
            r0.<init>(r14)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 2
            r5 = 1
            r6 = 0
            if (r3 == 0) goto L_0x0086
            r7 = 0
            if (r3 == r5) goto L_0x0061
            if (r3 != r4) goto L_0x0059
            r3 = r6
            r5 = r7
            r8 = r6
            r9 = r6
            r10 = r6
            java.lang.Object r9 = r0.L$5
            java.lang.Object r11 = r0.L$4
            r10 = r11
            kotlinx.coroutines.channels.ChannelIterator r10 = (kotlinx.coroutines.channels.ChannelIterator) r10
            java.lang.Object r11 = r0.L$3
            r6 = r11
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r11 = r0.L$2
            r8 = r11
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            java.lang.Object r11 = r0.L$1
            r3 = r11
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            java.lang.Object r11 = r0.L$0
            r13 = r11
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0102 }
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r3
            r3 = r2
            r2 = r1
            goto L_0x00de
        L_0x0059:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0061:
            r3 = r6
            r5 = r7
            r8 = r6
            r9 = r6
            r10 = r6
            java.lang.Object r11 = r0.L$4
            r9 = r11
            kotlinx.coroutines.channels.ChannelIterator r9 = (kotlinx.coroutines.channels.ChannelIterator) r9
            java.lang.Object r11 = r0.L$3
            r10 = r11
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r11 = r0.L$2
            r8 = r11
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            java.lang.Object r11 = r0.L$1
            r3 = r11
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            java.lang.Object r11 = r0.L$0
            r13 = r11
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0102 }
            r11 = r9
            r9 = r5
            r5 = r1
            goto L_0x00a8
        L_0x0086:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r13
            r7 = 0
            r8 = r6
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            r10 = r3
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r11 = r10.iterator()     // Catch:{ all -> 0x0102 }
            r0.L$0 = r13     // Catch:{ all -> 0x0102 }
            r0.L$1 = r3     // Catch:{ all -> 0x0102 }
            r0.L$2 = r8     // Catch:{ all -> 0x0102 }
            r0.L$3 = r10     // Catch:{ all -> 0x0102 }
            r0.L$4 = r11     // Catch:{ all -> 0x0102 }
            r0.label = r5     // Catch:{ all -> 0x0102 }
            java.lang.Object r5 = r11.hasNext(r0)     // Catch:{ all -> 0x0102 }
            if (r5 != r2) goto L_0x00a8
            return r2
        L_0x00a8:
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ all -> 0x0102 }
            boolean r5 = r5.booleanValue()     // Catch:{ all -> 0x0102 }
            if (r5 != 0) goto L_0x00b5
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r8)
            return r6
        L_0x00b5:
            java.lang.Object r5 = r11.next()     // Catch:{ all -> 0x0102 }
            r6 = r10
            r10 = r11
            r12 = r9
            r9 = r5
            r5 = r12
        L_0x00be:
            r0.L$0 = r13     // Catch:{ all -> 0x0102 }
            r0.L$1 = r3     // Catch:{ all -> 0x0102 }
            r0.L$2 = r8     // Catch:{ all -> 0x0102 }
            r0.L$3 = r6     // Catch:{ all -> 0x0102 }
            r0.L$4 = r10     // Catch:{ all -> 0x0102 }
            r0.L$5 = r9     // Catch:{ all -> 0x0102 }
            r0.label = r4     // Catch:{ all -> 0x0102 }
            java.lang.Object r11 = r10.hasNext(r0)     // Catch:{ all -> 0x0102 }
            if (r11 != r2) goto L_0x00d3
            return r2
        L_0x00d3:
            r12 = r2
            r2 = r1
            r1 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r3
            r3 = r12
        L_0x00de:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x00fa }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x00fa }
            if (r1 == 0) goto L_0x00f5
            java.lang.Object r1 = r11.next()     // Catch:{ all -> 0x00fa }
            r10 = r11
            r12 = r9
            r9 = r1
            r1 = r2
            r2 = r3
            r3 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r12
            goto L_0x00be
        L_0x00f5:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r9)
            return r10
        L_0x00fa:
            r1 = move-exception
            r3 = r5
            r7 = r8
            r8 = r9
            r12 = r2
            r2 = r1
            r1 = r12
            goto L_0x0103
        L_0x0102:
            r2 = move-exception
        L_0x0103:
            r4 = r2
            throw r2     // Catch:{ all -> 0x0106 }
        L_0x0106:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r4)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.lastOrNull(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v9, resolved type: kotlin.jvm.internal.Ref$ObjectRef} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00d0  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00e9 A[Catch:{ all -> 0x0124 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object lastOrNull(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r20, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r21, kotlin.coroutines.Continuation<? super E> r22) {
        /*
            r1 = r22
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$lastOrNull$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$lastOrNull$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$lastOrNull$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$lastOrNull$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$lastOrNull$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x0088
            if (r4 != r5) goto L_0x0080
            r4 = 0
            r7 = r4
            r8 = r6
            r9 = r4
            r10 = r6
            r11 = r4
            r12 = r6
            r13 = r6
            java.lang.Object r14 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r14 = (kotlinx.coroutines.channels.ChannelIterator) r14
            java.lang.Object r15 = r2.L$6
            r12 = r15
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r15 = r2.L$5
            r10 = r15
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r15 = r2.L$4
            r13 = r15
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            java.lang.Object r15 = r2.L$3
            r6 = r15
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r15 = r2.L$2
            r8 = r15
            kotlin.jvm.internal.Ref$ObjectRef r8 = (kotlin.jvm.internal.Ref.ObjectRef) r8
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            java.lang.Object r5 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006f }
            r16 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r6
            r6 = r3
            r19 = r4
            r4 = r1
            r1 = r5
            r5 = r2
            r2 = r15
            r15 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r19
            goto L_0x00e1
        L_0x006f:
            r0 = move-exception
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r4
            r4 = r2
            r2 = r15
            r19 = r3
            r3 = r1
            r1 = r5
            r5 = r19
            goto L_0x0148
        L_0x0080:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0088:
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = 0
            kotlin.jvm.internal.Ref$ObjectRef r5 = new kotlin.jvm.internal.Ref$ObjectRef
            r5.<init>()
            r5.element = r6
            r8 = r5
            r5 = r20
            r7 = 0
            r13 = r5
            r9 = 0
            r10 = r6
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r6 = r13
            r11 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r6.iterator()     // Catch:{ all -> 0x013a }
            r15 = r12
            r14 = r13
            r13 = r6
            r12 = r11
            r6 = r4
            r11 = r10
            r4 = r2
            r10 = r9
            r2 = r21
            r9 = r8
            r8 = r7
            r7 = r5
            r5 = r3
            r3 = r1
            r1 = r20
        L_0x00b4:
            r4.L$0 = r1     // Catch:{ all -> 0x0135 }
            r4.L$1 = r2     // Catch:{ all -> 0x0135 }
            r4.L$2 = r9     // Catch:{ all -> 0x0135 }
            r4.L$3 = r7     // Catch:{ all -> 0x0135 }
            r4.L$4 = r14     // Catch:{ all -> 0x0135 }
            r4.L$5 = r11     // Catch:{ all -> 0x0135 }
            r4.L$6 = r13     // Catch:{ all -> 0x0135 }
            r4.L$7 = r15     // Catch:{ all -> 0x0135 }
            r20 = r1
            r1 = 1
            r4.label = r1     // Catch:{ all -> 0x0130 }
            java.lang.Object r1 = r15.hasNext(r4)     // Catch:{ all -> 0x0130 }
            if (r1 != r0) goto L_0x00d0
            return r0
        L_0x00d0:
            r16 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r20
        L_0x00e1:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0124 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0124 }
            if (r3 == 0) goto L_0x0114
            java.lang.Object r3 = r16.next()     // Catch:{ all -> 0x0124 }
            r20 = r3
            r17 = 0
            r21 = r0
            r0 = r20
            java.lang.Object r18 = r2.invoke(r0)     // Catch:{ all -> 0x0124 }
            java.lang.Boolean r18 = (java.lang.Boolean) r18     // Catch:{ all -> 0x0124 }
            boolean r18 = r18.booleanValue()     // Catch:{ all -> 0x0124 }
            if (r18 == 0) goto L_0x0103
            r10.element = r0     // Catch:{ all -> 0x0124 }
        L_0x0103:
            r0 = r21
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r15 = r16
            goto L_0x00b4
        L_0x0114:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0124 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            T r0 = r10.element
            return r0
        L_0x0124:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r13 = r15
            goto L_0x0148
        L_0x0130:
            r0 = move-exception
            r1 = r20
            r13 = r14
            goto L_0x0148
        L_0x0135:
            r0 = move-exception
            r20 = r1
            r13 = r14
            goto L_0x0148
        L_0x013a:
            r0 = move-exception
            r6 = r4
            r11 = r10
            r4 = r2
            r10 = r9
            r2 = r21
            r9 = r8
            r8 = r7
            r7 = r5
            r5 = r3
            r3 = r1
            r1 = r20
        L_0x0148:
            r11 = r0
            throw r0     // Catch:{ all -> 0x014b }
        L_0x014b:
            r0 = move-exception
            r12 = r0
            r14 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r14)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r13, r11)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r14)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.lastOrNull(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object lastOrNull$$forInline(ReceiveChannel $this$lastOrNull, Function1 predicate, Continuation continuation) {
        Object last = null;
        ReceiveChannel $this$consume$iv$iv = $this$lastOrNull;
        Throwable cause$iv$iv = null;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it2 = it.next();
                    try {
                        if (((Boolean) predicate.invoke(it2)).booleanValue()) {
                            last = it2;
                        }
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return last;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v3, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v14, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ba A[Catch:{ all -> 0x00fb }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00df  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00e4 A[SYNTHETIC, Splitter:B:42:0x00e4] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00f1 A[SYNTHETIC, Splitter:B:47:0x00f1] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object single(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r12, kotlin.coroutines.Continuation<? super E> r13) {
        /*
            boolean r0 = r13 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$single$1
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$single$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$single$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$single$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$single$1
            r0.<init>(r13)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 2
            r5 = 1
            r6 = 0
            if (r3 == 0) goto L_0x008d
            r7 = 0
            if (r3 == r5) goto L_0x005f
            if (r3 != r4) goto L_0x0057
            r2 = r6
            r3 = r7
            r4 = r6
            r5 = r6
            r8 = r6
            java.lang.Object r5 = r0.L$5
            java.lang.Object r9 = r0.L$4
            r8 = r9
            kotlinx.coroutines.channels.ChannelIterator r8 = (kotlinx.coroutines.channels.ChannelIterator) r8
            java.lang.Object r9 = r0.L$3
            r6 = r9
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r9 = r0.L$2
            r4 = r9
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            java.lang.Object r9 = r0.L$1
            r2 = r9
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            java.lang.Object r9 = r0.L$0
            r12 = r9
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0054 }
            r10 = r8
            r8 = r6
            r6 = r4
            r4 = r1
            goto L_0x00d7
        L_0x0054:
            r3 = move-exception
            goto L_0x0107
        L_0x0057:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x005f:
            r3 = r6
            r5 = r7
            r8 = r6
            r9 = r6
            java.lang.Object r10 = r0.L$4
            r9 = r10
            kotlinx.coroutines.channels.ChannelIterator r9 = (kotlinx.coroutines.channels.ChannelIterator) r9
            java.lang.Object r10 = r0.L$3
            r6 = r10
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r10 = r0.L$2
            r8 = r10
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            java.lang.Object r10 = r0.L$1
            r3 = r10
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            java.lang.Object r10 = r0.L$0
            r12 = r10
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0086 }
            r10 = r9
            r9 = r7
            r7 = r1
            r11 = r8
            r8 = r6
            r6 = r11
            goto L_0x00b2
        L_0x0086:
            r2 = move-exception
            r4 = r8
            r11 = r3
            r3 = r2
            r2 = r11
            goto L_0x0107
        L_0x008d:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r12
            r7 = 0
            java.lang.Throwable r6 = (java.lang.Throwable) r6
            r8 = r3
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r8.iterator()     // Catch:{ all -> 0x0102 }
            r0.L$0 = r12     // Catch:{ all -> 0x0102 }
            r0.L$1 = r3     // Catch:{ all -> 0x0102 }
            r0.L$2 = r6     // Catch:{ all -> 0x0102 }
            r0.L$3 = r8     // Catch:{ all -> 0x0102 }
            r0.L$4 = r10     // Catch:{ all -> 0x0102 }
            r0.label = r5     // Catch:{ all -> 0x0102 }
            java.lang.Object r5 = r10.hasNext(r0)     // Catch:{ all -> 0x0102 }
            if (r5 != r2) goto L_0x00ae
            return r2
        L_0x00ae:
            r11 = r7
            r7 = r5
            r5 = r9
            r9 = r11
        L_0x00b2:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ all -> 0x00fb }
            boolean r7 = r7.booleanValue()     // Catch:{ all -> 0x00fb }
            if (r7 == 0) goto L_0x00f1
            java.lang.Object r7 = r10.next()     // Catch:{ all -> 0x00fb }
            r0.L$0 = r12     // Catch:{ all -> 0x00fb }
            r0.L$1 = r3     // Catch:{ all -> 0x00fb }
            r0.L$2 = r6     // Catch:{ all -> 0x00fb }
            r0.L$3 = r8     // Catch:{ all -> 0x00fb }
            r0.L$4 = r10     // Catch:{ all -> 0x00fb }
            r0.L$5 = r7     // Catch:{ all -> 0x00fb }
            r0.label = r4     // Catch:{ all -> 0x00fb }
            java.lang.Object r4 = r10.hasNext(r0)     // Catch:{ all -> 0x00fb }
            if (r4 != r2) goto L_0x00d3
            return r2
        L_0x00d3:
            r2 = r3
            r3 = r5
            r5 = r7
            r7 = r9
        L_0x00d7:
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ all -> 0x00ee }
            boolean r4 = r4.booleanValue()     // Catch:{ all -> 0x00ee }
            if (r4 != 0) goto L_0x00e4
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r6)
            return r5
        L_0x00e4:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x00ee }
            java.lang.String r9 = "ReceiveChannel has more than one element."
            r4.<init>(r9)     // Catch:{ all -> 0x00ee }
            java.lang.Throwable r4 = (java.lang.Throwable) r4     // Catch:{ all -> 0x00ee }
            throw r4     // Catch:{ all -> 0x00ee }
        L_0x00ee:
            r3 = move-exception
            r4 = r6
            goto L_0x0107
        L_0x00f1:
            java.util.NoSuchElementException r2 = new java.util.NoSuchElementException     // Catch:{ all -> 0x00fb }
            java.lang.String r4 = "ReceiveChannel is empty."
            r2.<init>(r4)     // Catch:{ all -> 0x00fb }
            java.lang.Throwable r2 = (java.lang.Throwable) r2     // Catch:{ all -> 0x00fb }
            throw r2     // Catch:{ all -> 0x00fb }
        L_0x00fb:
            r2 = move-exception
            r4 = r6
            r7 = r9
            r11 = r3
            r3 = r2
            r2 = r11
            goto L_0x0107
        L_0x0102:
            r2 = move-exception
            r4 = r6
            r11 = r3
            r3 = r2
            r2 = r11
        L_0x0107:
            r4 = r3
            throw r3     // Catch:{ all -> 0x010a }
        L_0x010a:
            r3 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.single(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v10, resolved type: kotlin.jvm.internal.Ref$BooleanRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v9, resolved type: kotlin.jvm.internal.Ref$ObjectRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00ee A[Catch:{ all -> 0x0154 }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0139  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x013d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object single(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r21, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r22, kotlin.coroutines.Continuation<? super E> r23) {
        /*
            r1 = r23
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$single$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$single$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$single$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$single$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$single$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x0086
            if (r4 != r6) goto L_0x007e
            r4 = r7
            r8 = r7
            r9 = r5
            r10 = r7
            r11 = r5
            r12 = r7
            r13 = r7
            r14 = r5
            java.lang.Object r15 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r15 = (kotlinx.coroutines.channels.ChannelIterator) r15
            java.lang.Object r6 = r2.L$7
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r12 = r2.L$6
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            java.lang.Object r13 = r2.L$5
            r10 = r13
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r13 = r2.L$4
            r4 = r13
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r13 = r2.L$3
            r7 = r13
            kotlin.jvm.internal.Ref$BooleanRef r7 = (kotlin.jvm.internal.Ref.BooleanRef) r7
            java.lang.Object r13 = r2.L$2
            r8 = r13
            kotlin.jvm.internal.Ref$ObjectRef r8 = (kotlin.jvm.internal.Ref.ObjectRef) r8
            java.lang.Object r13 = r2.L$1
            kotlin.jvm.functions.Function1 r13 = (kotlin.jvm.functions.Function1) r13
            r16 = r0
            java.lang.Object r0 = r2.L$0
            r17 = r0
            kotlinx.coroutines.channels.ReceiveChannel r17 = (kotlinx.coroutines.channels.ReceiveChannel) r17
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0076 }
            r0 = r16
            r16 = r17
            r17 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r6
            r6 = r4
            r4 = r2
            r2 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r5
            r5 = r3
            goto L_0x00e6
        L_0x0076:
            r0 = move-exception
            r6 = r4
            r16 = r17
            r4 = r2
            r2 = r13
            goto L_0x0187
        L_0x007e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0086:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r14 = 0
            kotlin.jvm.internal.Ref$ObjectRef r0 = new kotlin.jvm.internal.Ref$ObjectRef
            r0.<init>()
            r0.element = r7
            r8 = r0
            kotlin.jvm.internal.Ref$BooleanRef r0 = new kotlin.jvm.internal.Ref$BooleanRef
            r0.<init>()
            r0.element = r5
            r4 = r0
            r5 = r21
            r6 = 0
            r10 = r5
            r11 = 0
            r12 = r7
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            r0 = r10
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r9 = r0.iterator()     // Catch:{ all -> 0x017b }
            r13 = r11
            r15 = r14
            r11 = r7
            r14 = r12
            r7 = r6
            r12 = r10
            r6 = r5
            r10 = r8
            r8 = r0
            r5 = r3
            r3 = r9
            r0 = r16
            r9 = r4
            r4 = r2
            r2 = r22
            r20 = r1
            r1 = r21
            r21 = r20
        L_0x00c2:
            r4.L$0 = r1     // Catch:{ all -> 0x016d }
            r4.L$1 = r2     // Catch:{ all -> 0x016d }
            r4.L$2 = r10     // Catch:{ all -> 0x016d }
            r4.L$3 = r9     // Catch:{ all -> 0x016d }
            r4.L$4 = r6     // Catch:{ all -> 0x016d }
            r4.L$5 = r12     // Catch:{ all -> 0x016d }
            r4.L$6 = r14     // Catch:{ all -> 0x016d }
            r4.L$7 = r8     // Catch:{ all -> 0x016d }
            r4.L$8 = r3     // Catch:{ all -> 0x016d }
            r16 = r1
            r1 = 1
            r4.label = r1     // Catch:{ all -> 0x0161 }
            java.lang.Object r1 = r3.hasNext(r4)     // Catch:{ all -> 0x0161 }
            if (r1 != r0) goto L_0x00e0
            return r0
        L_0x00e0:
            r17 = r15
            r15 = r3
            r3 = r1
            r1 = r21
        L_0x00e6:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0154 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0154 }
            if (r3 == 0) goto L_0x0126
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x0154 }
            r21 = r3
            r18 = 0
            r22 = r0
            r0 = r21
            java.lang.Object r19 = r2.invoke(r0)     // Catch:{ all -> 0x0154 }
            java.lang.Boolean r19 = (java.lang.Boolean) r19     // Catch:{ all -> 0x0154 }
            boolean r19 = r19.booleanValue()     // Catch:{ all -> 0x0154 }
            if (r19 == 0) goto L_0x011c
            r21 = r1
            boolean r1 = r9.element     // Catch:{ all -> 0x0147 }
            if (r1 != 0) goto L_0x0112
            r10.element = r0     // Catch:{ all -> 0x0147 }
            r1 = 1
            r9.element = r1     // Catch:{ all -> 0x0147 }
            goto L_0x011e
        L_0x0112:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x0147 }
            java.lang.String r15 = "ReceiveChannel contains more than one matching element."
            r1.<init>(r15)     // Catch:{ all -> 0x0147 }
            java.lang.Throwable r1 = (java.lang.Throwable) r1     // Catch:{ all -> 0x0147 }
            throw r1     // Catch:{ all -> 0x0147 }
        L_0x011c:
            r21 = r1
        L_0x011e:
            r0 = r22
            r3 = r15
            r1 = r16
            r15 = r17
            goto L_0x00c2
        L_0x0126:
            r21 = r1
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0147 }
            r1 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r14)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            boolean r0 = r9.element
            if (r0 == 0) goto L_0x013d
            T r0 = r10.element
            return r0
        L_0x013d:
            java.util.NoSuchElementException r0 = new java.util.NoSuchElementException
            java.lang.String r1 = "ReceiveChannel contains no element matching the predicate."
            r0.<init>(r1)
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            throw r0
        L_0x0147:
            r0 = move-exception
            r1 = r21
            r3 = r5
            r5 = r7
            r7 = r9
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r14
            r14 = r17
            goto L_0x0187
        L_0x0154:
            r0 = move-exception
            r21 = r1
            r3 = r5
            r5 = r7
            r7 = r9
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r14
            r14 = r17
            goto L_0x0187
        L_0x0161:
            r0 = move-exception
            r1 = r21
            r3 = r5
            r5 = r7
            r7 = r9
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r14
            r14 = r15
            goto L_0x0187
        L_0x016d:
            r0 = move-exception
            r16 = r1
            r1 = r21
            r3 = r5
            r5 = r7
            r7 = r9
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r14
            r14 = r15
            goto L_0x0187
        L_0x017b:
            r0 = move-exception
            r16 = r21
            r7 = r4
            r4 = r2
            r2 = r22
            r20 = r6
            r6 = r5
            r5 = r20
        L_0x0187:
            r9 = r0
            throw r0     // Catch:{ all -> 0x018a }
        L_0x018a:
            r0 = move-exception
            r12 = r0
            r13 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r13)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r9)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r13)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.single(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object single$$forInline(ReceiveChannel $this$single, Function1 predicate, Continuation continuation) {
        Object single = null;
        boolean found = false;
        ReceiveChannel $this$consume$iv$iv = $this$single;
        Throwable cause$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it2 = it.next();
                    try {
                        if (((Boolean) predicate.invoke(it2)).booleanValue()) {
                            if (!found) {
                                single = it2;
                                found = true;
                            } else {
                                throw new IllegalArgumentException("ReceiveChannel contains more than one matching element.");
                            }
                        }
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    if (found) {
                        return single;
                    }
                    throw new NoSuchElementException("ReceiveChannel contains no element matching the predicate.");
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v2, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00b8 A[SYNTHETIC, Splitter:B:33:0x00b8] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00e3  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object singleOrNull(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r13, kotlin.coroutines.Continuation<? super E> r14) {
        /*
            boolean r0 = r14 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$singleOrNull$1
            if (r0 == 0) goto L_0x0014
            r0 = r14
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$singleOrNull$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$singleOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$singleOrNull$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$singleOrNull$1
            r0.<init>(r14)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 2
            r5 = 1
            r6 = 0
            if (r3 == 0) goto L_0x0084
            r7 = 0
            if (r3 == r5) goto L_0x005f
            if (r3 != r4) goto L_0x0057
            r2 = r6
            r3 = r7
            r4 = r6
            r5 = r6
            r8 = r6
            r9 = r6
            java.lang.Object r5 = r0.L$5
            java.lang.Object r10 = r0.L$4
            r8 = r10
            kotlinx.coroutines.channels.ChannelIterator r8 = (kotlinx.coroutines.channels.ChannelIterator) r8
            java.lang.Object r10 = r0.L$3
            r9 = r10
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r10 = r0.L$2
            r4 = r10
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            java.lang.Object r10 = r0.L$1
            r2 = r10
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            java.lang.Object r10 = r0.L$0
            r13 = r10
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0054 }
            r11 = r8
            r8 = r4
            r4 = r1
            goto L_0x00d6
        L_0x0054:
            r3 = move-exception
            goto L_0x00f7
        L_0x0057:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x005f:
            r3 = r6
            r5 = r7
            r8 = r6
            r9 = r6
            r10 = r6
            java.lang.Object r11 = r0.L$4
            r9 = r11
            kotlinx.coroutines.channels.ChannelIterator r9 = (kotlinx.coroutines.channels.ChannelIterator) r9
            java.lang.Object r11 = r0.L$3
            r10 = r11
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r11 = r0.L$2
            r8 = r11
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            java.lang.Object r11 = r0.L$1
            r3 = r11
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            java.lang.Object r11 = r0.L$0
            r13 = r11
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x00f2 }
            r11 = r9
            r9 = r7
            r7 = r1
            goto L_0x00ab
        L_0x0084:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r13
            r7 = 0
            r8 = r6
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            r9 = r3
            r10 = 0
            kotlinx.coroutines.channels.ChannelIterator r11 = r9.iterator()     // Catch:{ all -> 0x00f2 }
            r0.L$0 = r13     // Catch:{ all -> 0x00f2 }
            r0.L$1 = r3     // Catch:{ all -> 0x00f2 }
            r0.L$2 = r8     // Catch:{ all -> 0x00f2 }
            r0.L$3 = r9     // Catch:{ all -> 0x00f2 }
            r0.L$4 = r11     // Catch:{ all -> 0x00f2 }
            r0.label = r5     // Catch:{ all -> 0x00f2 }
            java.lang.Object r5 = r11.hasNext(r0)     // Catch:{ all -> 0x00f2 }
            if (r5 != r2) goto L_0x00a6
            return r2
        L_0x00a6:
            r12 = r7
            r7 = r5
            r5 = r10
            r10 = r9
            r9 = r12
        L_0x00ab:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ all -> 0x00eb }
            boolean r7 = r7.booleanValue()     // Catch:{ all -> 0x00eb }
            if (r7 != 0) goto L_0x00b8
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r8)
            return r6
        L_0x00b8:
            java.lang.Object r7 = r11.next()     // Catch:{ all -> 0x00eb }
            r0.L$0 = r13     // Catch:{ all -> 0x00eb }
            r0.L$1 = r3     // Catch:{ all -> 0x00eb }
            r0.L$2 = r8     // Catch:{ all -> 0x00eb }
            r0.L$3 = r10     // Catch:{ all -> 0x00eb }
            r0.L$4 = r11     // Catch:{ all -> 0x00eb }
            r0.L$5 = r7     // Catch:{ all -> 0x00eb }
            r0.label = r4     // Catch:{ all -> 0x00eb }
            java.lang.Object r4 = r11.hasNext(r0)     // Catch:{ all -> 0x00eb }
            if (r4 != r2) goto L_0x00d1
            return r2
        L_0x00d1:
            r2 = r3
            r3 = r5
            r5 = r7
            r7 = r9
            r9 = r10
        L_0x00d6:
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ all -> 0x00e8 }
            boolean r4 = r4.booleanValue()     // Catch:{ all -> 0x00e8 }
            if (r4 == 0) goto L_0x00e3
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r8)
            return r6
        L_0x00e3:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r8)
            return r5
        L_0x00e8:
            r3 = move-exception
            r4 = r8
            goto L_0x00f7
        L_0x00eb:
            r2 = move-exception
            r4 = r8
            r7 = r9
            r12 = r3
            r3 = r2
            r2 = r12
            goto L_0x00f7
        L_0x00f2:
            r2 = move-exception
            r4 = r8
            r12 = r3
            r3 = r2
            r2 = r12
        L_0x00f7:
            r4 = r3
            throw r3     // Catch:{ all -> 0x00fa }
        L_0x00fa:
            r3 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.singleOrNull(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v12, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v11, resolved type: kotlin.jvm.internal.Ref$ObjectRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v26, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00e6  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0101 A[Catch:{ all -> 0x0171 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x015a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x015c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object singleOrNull(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r20, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r21, kotlin.coroutines.Continuation<? super E> r22) {
        /*
            r1 = r22
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$singleOrNull$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$singleOrNull$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$singleOrNull$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$singleOrNull$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$singleOrNull$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x008c
            if (r4 != r6) goto L_0x0084
            r4 = r7
            r8 = r7
            r9 = r5
            r10 = r7
            r11 = r5
            r12 = r7
            r13 = r7
            r14 = r5
            r15 = r7
            java.lang.Object r6 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r7 = r2.L$7
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r12 = r2.L$6
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            java.lang.Object r13 = r2.L$5
            r10 = r13
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r13 = r2.L$4
            r4 = r13
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r13 = r2.L$3
            kotlin.jvm.internal.Ref$BooleanRef r13 = (kotlin.jvm.internal.Ref.BooleanRef) r13
            java.lang.Object r15 = r2.L$2
            r8 = r15
            kotlin.jvm.internal.Ref$ObjectRef r8 = (kotlin.jvm.internal.Ref.ObjectRef) r8
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            r16 = r0
            java.lang.Object r0 = r2.L$0
            r17 = r0
            kotlinx.coroutines.channels.ReceiveChannel r17 = (kotlinx.coroutines.channels.ReceiveChannel) r17
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x007b }
            r0 = r16
            r20 = r17
            r16 = r14
            r14 = r11
            r11 = r8
            r8 = r5
            r5 = r2
            r2 = r15
            r15 = r12
            r12 = r9
            r9 = r6
            r6 = r3
            r19 = r4
            r4 = r1
            r1 = r13
            r13 = r10
            r10 = r7
            r7 = r19
            goto L_0x00f9
        L_0x007b:
            r0 = move-exception
            r6 = r4
            r7 = r5
            r4 = r2
            r5 = r3
            r2 = r15
            r3 = r1
            goto L_0x01a1
        L_0x0084:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x008c:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r14 = 0
            kotlin.jvm.internal.Ref$ObjectRef r0 = new kotlin.jvm.internal.Ref$ObjectRef
            r0.<init>()
            r4 = 0
            r0.element = r4
            r8 = r0
            kotlin.jvm.internal.Ref$BooleanRef r0 = new kotlin.jvm.internal.Ref$BooleanRef
            r0.<init>()
            r0.element = r5
            r13 = r0
            r4 = r20
            r5 = 0
            r10 = r4
            r11 = 0
            r0 = 0
            r12 = r0
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            r0 = r10
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r0.iterator()     // Catch:{ all -> 0x0197 }
            r9 = r0
            r15 = r13
            r0 = r16
            r13 = r11
            r16 = r14
            r11 = r6
            r14 = r12
            r6 = r4
            r12 = r10
            r4 = r2
            r10 = r8
            r2 = r21
            r8 = r7
            r7 = r5
            r5 = r3
            r3 = r1
            r1 = r20
        L_0x00c8:
            r4.L$0 = r1     // Catch:{ all -> 0x018c }
            r4.L$1 = r2     // Catch:{ all -> 0x018c }
            r4.L$2 = r10     // Catch:{ all -> 0x018c }
            r4.L$3 = r15     // Catch:{ all -> 0x018c }
            r4.L$4 = r6     // Catch:{ all -> 0x018c }
            r4.L$5 = r12     // Catch:{ all -> 0x018c }
            r4.L$6 = r14     // Catch:{ all -> 0x018c }
            r4.L$7 = r9     // Catch:{ all -> 0x018c }
            r4.L$8 = r8     // Catch:{ all -> 0x018c }
            r17 = r1
            r1 = 1
            r4.label = r1     // Catch:{ all -> 0x0183 }
            java.lang.Object r1 = r8.hasNext(r4)     // Catch:{ all -> 0x0183 }
            if (r1 != r0) goto L_0x00e6
            return r0
        L_0x00e6:
            r20 = r17
            r19 = r3
            r3 = r1
            r1 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r19
        L_0x00f9:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0171 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0171 }
            if (r3 == 0) goto L_0x0147
            java.lang.Object r3 = r9.next()     // Catch:{ all -> 0x0171 }
            r21 = r3
            r17 = 0
            r22 = r0
            r0 = r21
            java.lang.Object r18 = r2.invoke(r0)     // Catch:{ all -> 0x0171 }
            java.lang.Boolean r18 = (java.lang.Boolean) r18     // Catch:{ all -> 0x0171 }
            boolean r18 = r18.booleanValue()     // Catch:{ all -> 0x0171 }
            if (r18 == 0) goto L_0x0131
            r18 = r2
            boolean r2 = r1.element     // Catch:{ all -> 0x015f }
            if (r2 == 0) goto L_0x012b
            r0 = 2
            kotlin.jvm.internal.InlineMarker.finallyStart(r0)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r13, r15)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r0)
            r0 = 0
            return r0
        L_0x012b:
            r11.element = r0     // Catch:{ all -> 0x015f }
            r2 = 1
            r1.element = r2     // Catch:{ all -> 0x015f }
            goto L_0x0133
        L_0x0131:
            r18 = r2
        L_0x0133:
            r0 = r22
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r2 = r18
            r15 = r1
            r1 = r20
            goto L_0x00c8
        L_0x0147:
            r18 = r2
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x015f }
            r2 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r2)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r13, r15)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r2)
            boolean r0 = r1.element
            if (r0 != 0) goto L_0x015c
            r0 = 0
            return r0
        L_0x015c:
            T r0 = r11.element
            return r0
        L_0x015f:
            r0 = move-exception
            r17 = r20
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r11
            r10 = r13
            r11 = r14
            r12 = r15
            r14 = r16
            r2 = r18
            r13 = r1
            goto L_0x01a1
        L_0x0171:
            r0 = move-exception
            r18 = r2
            r17 = r20
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r11
            r10 = r13
            r11 = r14
            r12 = r15
            r14 = r16
            r13 = r1
            goto L_0x01a1
        L_0x0183:
            r0 = move-exception
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r14
            r13 = r15
            r14 = r16
            goto L_0x01a1
        L_0x018c:
            r0 = move-exception
            r17 = r1
            r8 = r10
            r10 = r12
            r11 = r13
            r12 = r14
            r13 = r15
            r14 = r16
            goto L_0x01a1
        L_0x0197:
            r0 = move-exception
            r17 = r20
            r6 = r4
            r7 = r5
            r4 = r2
            r5 = r3
            r2 = r21
            r3 = r1
        L_0x01a1:
            r1 = r0
            throw r0     // Catch:{ all -> 0x01a4 }
        L_0x01a4:
            r0 = move-exception
            r9 = r0
            r12 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r12)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r12)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.singleOrNull(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004f, code lost:
        r11 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r9 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0053, code lost:
        kotlin.jvm.internal.InlineMarker.finallyStart(1);
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r8);
        kotlin.jvm.internal.InlineMarker.finallyEnd(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005e, code lost:
        if (r3 != false) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0060, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0061, code lost:
        return r2;
     */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final java.lang.Object singleOrNull$$forInline(kotlinx.coroutines.channels.ReceiveChannel r18, kotlin.jvm.functions.Function1 r19, kotlin.coroutines.Continuation r20) {
        /*
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = r18
            r5 = 0
            r6 = r4
            r7 = 0
            r0 = 0
            r8 = r0
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            r9 = r6
            r10 = 0
            r11 = 1
            kotlinx.coroutines.channels.ChannelIterator r12 = r9.iterator()     // Catch:{ all -> 0x0064 }
        L_0x0014:
            r13 = 0
            kotlin.jvm.internal.InlineMarker.mark((int) r13)     // Catch:{ all -> 0x0064 }
            r13 = r20
            java.lang.Object r14 = r12.hasNext(r13)     // Catch:{ all -> 0x0064 }
            kotlin.jvm.internal.InlineMarker.mark((int) r11)     // Catch:{ all -> 0x0064 }
            java.lang.Boolean r14 = (java.lang.Boolean) r14     // Catch:{ all -> 0x0064 }
            boolean r14 = r14.booleanValue()     // Catch:{ all -> 0x0064 }
            if (r14 == 0) goto L_0x004f
            java.lang.Object r14 = r12.next()     // Catch:{ all -> 0x0064 }
            r15 = r14
            r16 = 0
            r11 = r19
            java.lang.Object r17 = r11.invoke(r15)     // Catch:{ all -> 0x0062 }
            java.lang.Boolean r17 = (java.lang.Boolean) r17     // Catch:{ all -> 0x0062 }
            boolean r17 = r17.booleanValue()     // Catch:{ all -> 0x0062 }
            if (r17 == 0) goto L_0x004d
            if (r3 == 0) goto L_0x004b
            r4 = 2
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r8)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            return r0
        L_0x004b:
            r2 = r15
            r3 = 1
        L_0x004d:
            r11 = 1
            goto L_0x0014
        L_0x004f:
            r11 = r19
            kotlin.Unit r9 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0062 }
            r9 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r9)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r8)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r9)
            if (r3 != 0) goto L_0x0061
            return r0
        L_0x0061:
            return r2
        L_0x0062:
            r0 = move-exception
            goto L_0x0067
        L_0x0064:
            r0 = move-exception
            r11 = r19
        L_0x0067:
            r8 = r0
            throw r0     // Catch:{ all -> 0x006a }
        L_0x006a:
            r0 = move-exception
            r9 = r0
            r10 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r8)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.singleOrNull$$forInline(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ ReceiveChannel drop$default(ReceiveChannel receiveChannel, int i, CoroutineContext coroutineContext, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.drop(receiveChannel, i, coroutineContext);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> drop(ReceiveChannel<? extends E> $this$drop, int n, CoroutineContext context) {
        Intrinsics.checkParameterIsNotNull($this$drop, "$this$drop");
        Intrinsics.checkParameterIsNotNull(context, "context");
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, ChannelsKt.consumes($this$drop), new ChannelsKt__Channels_commonKt$drop$1($this$drop, n, (Continuation) null), 2, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel dropWhile$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.dropWhile(receiveChannel, coroutineContext, function2);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> dropWhile(ReceiveChannel<? extends E> $this$dropWhile, CoroutineContext context, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        Intrinsics.checkParameterIsNotNull($this$dropWhile, "$this$dropWhile");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, ChannelsKt.consumes($this$dropWhile), new ChannelsKt__Channels_commonKt$dropWhile$1($this$dropWhile, predicate, (Continuation) null), 2, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel filter$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.filter(receiveChannel, coroutineContext, function2);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> filter(ReceiveChannel<? extends E> $this$filter, CoroutineContext context, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        Intrinsics.checkParameterIsNotNull($this$filter, "$this$filter");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, ChannelsKt.consumes($this$filter), new ChannelsKt__Channels_commonKt$filter$1($this$filter, predicate, (Continuation) null), 2, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel filterIndexed$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.filterIndexed(receiveChannel, coroutineContext, function3);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> filterIndexed(ReceiveChannel<? extends E> $this$filterIndexed, CoroutineContext context, Function3<? super Integer, ? super E, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        Intrinsics.checkParameterIsNotNull($this$filterIndexed, "$this$filterIndexed");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, ChannelsKt.consumes($this$filterIndexed), new ChannelsKt__Channels_commonKt$filterIndexed$1($this$filterIndexed, predicate, (Continuation) null), 2, (Object) null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v11, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v2, resolved type: kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super E, java.lang.Boolean>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v26, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v1, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0108  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x012b A[Catch:{ all -> 0x01c2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, C extends java.util.Collection<? super E>> java.lang.Object filterIndexedTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r28, C r29, kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super E, java.lang.Boolean> r30, kotlin.coroutines.Continuation<? super C> r31) {
        /*
            r1 = r31
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterIndexedTo$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterIndexedTo$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterIndexedTo$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterIndexedTo$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterIndexedTo$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            r7 = 0
            if (r4 == 0) goto L_0x009e
            if (r4 != r5) goto L_0x0096
            r4 = r6
            r8 = r6
            r9 = r6
            r10 = r7
            r11 = r7
            r12 = r7
            r13 = r6
            r14 = r6
            r15 = r7
            r16 = r7
            r17 = r7
            java.lang.Object r5 = r2.L$9
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r7 = r2.L$8
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r11 = r2.L$7
            r10 = r11
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r11 = r2.L$6
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r15 = r2.L$5
            r12 = r15
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r15 = r2.L$4
            kotlin.jvm.internal.Ref$IntRef r15 = (kotlin.jvm.internal.Ref.IntRef) r15
            java.lang.Object r6 = r2.L$3
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            r17 = r0
            java.lang.Object r0 = r2.L$2
            r19 = r0
            kotlin.jvm.functions.Function2 r19 = (kotlin.jvm.functions.Function2) r19
            java.lang.Object r0 = r2.L$1
            r20 = r0
            java.util.Collection r20 = (java.util.Collection) r20
            java.lang.Object r0 = r2.L$0
            r21 = r0
            kotlinx.coroutines.channels.ReceiveChannel r21 = (kotlinx.coroutines.channels.ReceiveChannel) r21
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0089 }
            r28 = r1
            r1 = r10
            r0 = r17
            r18 = r21
            r16 = 0
            r10 = r6
            r17 = r9
            r6 = r19
            r9 = r5
            r19 = r13
            r5 = r3
            r13 = r7
            r7 = r2
            r2 = r20
            r20 = r14
            r14 = r8
            r8 = 0
            goto L_0x0123
        L_0x0089:
            r0 = move-exception
            r7 = r2
            r5 = r15
            r2 = r20
            r18 = r21
            r15 = r10
            r10 = r6
            r6 = r19
            goto L_0x020a
        L_0x0096:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x009e:
            r17 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r8 = 0
            r6 = r28
            r4 = 0
            kotlin.jvm.internal.Ref$IntRef r0 = new kotlin.jvm.internal.Ref$IntRef
            r0.<init>()
            r5 = 0
            r0.element = r5
            r15 = r0
            r12 = r6
            r13 = 0
            r11 = r12
            r14 = 0
            r0 = 0
            r10 = r0
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r7 = r11
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r16 = r7.iterator()     // Catch:{ all -> 0x01ff }
            r24 = r1
            r1 = r28
            r28 = r24
            r25 = r2
            r2 = r29
            r29 = r3
            r3 = r30
            r30 = r4
            r4 = r11
            r11 = r7
            r7 = r15
            r15 = r10
            r10 = r5
            r5 = r25
            r26 = r12
            r12 = r0
            r0 = r17
            r17 = r14
            r14 = r9
            r9 = r6
            r6 = r26
            r27 = r13
            r13 = r8
            r8 = r16
            r16 = r27
        L_0x00e8:
            r5.L$0 = r1     // Catch:{ all -> 0x01e7 }
            r5.L$1 = r2     // Catch:{ all -> 0x01e7 }
            r5.L$2 = r3     // Catch:{ all -> 0x01e7 }
            r5.L$3 = r9     // Catch:{ all -> 0x01e7 }
            r5.L$4 = r7     // Catch:{ all -> 0x01e7 }
            r5.L$5 = r6     // Catch:{ all -> 0x01e7 }
            r5.L$6 = r4     // Catch:{ all -> 0x01e7 }
            r5.L$7 = r15     // Catch:{ all -> 0x01e7 }
            r5.L$8 = r11     // Catch:{ all -> 0x01e7 }
            r5.L$9 = r8     // Catch:{ all -> 0x01e7 }
            r18 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x01d1 }
            java.lang.Object r1 = r8.hasNext(r5)     // Catch:{ all -> 0x01d1 }
            if (r1 != r0) goto L_0x0108
            return r0
        L_0x0108:
            r19 = r16
            r20 = r17
            r16 = r10
            r17 = r14
            r10 = r9
            r14 = r13
            r9 = r8
            r13 = r11
            r8 = r12
            r11 = r4
            r12 = r6
            r4 = r30
            r6 = r3
            r3 = r29
            r24 = r5
            r5 = r1
            r1 = r15
            r15 = r7
            r7 = r24
        L_0x0123:
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ all -> 0x01c2 }
            boolean r5 = r5.booleanValue()     // Catch:{ all -> 0x01c2 }
            if (r5 == 0) goto L_0x019f
            java.lang.Object r5 = r9.next()     // Catch:{ all -> 0x01c2 }
            r29 = r5
            r21 = 0
            r30 = r0
            kotlin.collections.IndexedValue r0 = new kotlin.collections.IndexedValue     // Catch:{ all -> 0x01c2 }
            r31 = r3
            int r3 = r15.element     // Catch:{ all -> 0x018f }
            r22 = r4
            int r4 = r3 + 1
            r15.element = r4     // Catch:{ all -> 0x01b2 }
            r4 = r29
            r0.<init>(r3, r4)     // Catch:{ all -> 0x01b2 }
            r3 = r8
            r8 = r16
            r16 = 0
            int r23 = r0.component1()     // Catch:{ all -> 0x01b2 }
            r8 = r23
            java.lang.Object r23 = r0.component2()     // Catch:{ all -> 0x01b2 }
            r3 = r23
            r29 = r0
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r8)     // Catch:{ all -> 0x01b2 }
            java.lang.Object r0 = r6.invoke(r0, r3)     // Catch:{ all -> 0x01b2 }
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x01b2 }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x01b2 }
            if (r0 == 0) goto L_0x016c
            r2.add(r3)     // Catch:{ all -> 0x01b2 }
        L_0x016c:
            r0 = r30
            r29 = r31
            r5 = r7
            r4 = r11
            r11 = r13
            r13 = r14
            r7 = r15
            r14 = r17
            r16 = r19
            r17 = r20
            r30 = r22
            r15 = r1
            r1 = r18
            r24 = r12
            r12 = r3
            r3 = r6
            r6 = r24
            r25 = r10
            r10 = r8
            r8 = r9
            r9 = r25
            goto L_0x00e8
        L_0x018f:
            r0 = move-exception
            r22 = r4
            r3 = r31
            r8 = r14
            r5 = r15
            r13 = r19
            r14 = r20
            r15 = r1
            r1 = r28
            goto L_0x020a
        L_0x019f:
            r31 = r3
            r22 = r4
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x01b2 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r2
        L_0x01b2:
            r0 = move-exception
            r3 = r31
            r8 = r14
            r5 = r15
            r13 = r19
            r14 = r20
            r4 = r22
            r15 = r1
            r1 = r28
            goto L_0x020a
        L_0x01c2:
            r0 = move-exception
            r31 = r3
            r22 = r4
            r8 = r14
            r5 = r15
            r13 = r19
            r14 = r20
            r15 = r1
            r1 = r28
            goto L_0x020a
        L_0x01d1:
            r0 = move-exception
            r1 = r28
            r11 = r4
            r12 = r6
            r10 = r9
            r8 = r13
            r13 = r16
            r14 = r17
            r4 = r30
            r6 = r3
            r3 = r29
            r24 = r7
            r7 = r5
            r5 = r24
            goto L_0x020a
        L_0x01e7:
            r0 = move-exception
            r18 = r1
            r1 = r28
            r11 = r4
            r12 = r6
            r10 = r9
            r8 = r13
            r13 = r16
            r14 = r17
            r4 = r30
            r6 = r3
            r3 = r29
            r24 = r7
            r7 = r5
            r5 = r24
            goto L_0x020a
        L_0x01ff:
            r0 = move-exception
            r18 = r28
            r7 = r2
            r5 = r15
            r2 = r29
            r15 = r10
            r10 = r6
            r6 = r30
        L_0x020a:
            r9 = r0
            throw r0     // Catch:{ all -> 0x020d }
        L_0x020d:
            r0 = move-exception
            r15 = r0
            r16 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r16)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r9)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r16)
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.filterIndexedTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object filterIndexedTo$$forInline(ReceiveChannel $this$filterIndexedTo, Collection destination, Function2 predicate, Continuation continuation) {
        int index;
        Object element;
        ReceiveChannel $this$consumeEachIndexed$iv;
        Collection collection = destination;
        int $i$f$filterIndexedTo = 0;
        ReceiveChannel $this$consumeEachIndexed$iv2 = $this$filterIndexedTo;
        int index$iv = 0;
        ReceiveChannel $this$consume$iv$iv$iv = $this$consumeEachIndexed$iv2;
        Throwable cause$iv$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv$iv.iterator();
            int i2 = 0;
            int i3 = 0;
            while (true) {
                InlineMarker.mark(i2);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    int index$iv2 = index$iv + 1;
                    int $i$f$filterIndexedTo2 = $i$f$filterIndexedTo;
                    Object it$iv = it.next();
                    try {
                        IndexedValue $dstr$index$element = new IndexedValue(index$iv, it$iv);
                        int i4 = i3;
                        index = $dstr$index$element.component1();
                        element = $dstr$index$element.component2();
                        Object obj = it$iv;
                        $this$consumeEachIndexed$iv = $this$consumeEachIndexed$iv2;
                    } catch (Throwable th) {
                        e$iv$iv$iv = th;
                        ReceiveChannel receiveChannel = $this$consumeEachIndexed$iv2;
                        Function2 function2 = predicate;
                        int i5 = index$iv2;
                        Throwable cause$iv$iv$iv2 = e$iv$iv$iv;
                        try {
                            throw e$iv$iv$iv;
                        } catch (Throwable e$iv$iv$iv) {
                            Throwable th2 = e$iv$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                    try {
                        if (((Boolean) predicate.invoke(Integer.valueOf(index), element)).booleanValue()) {
                            collection.add(element);
                        }
                        i3 = index;
                        index$iv = index$iv2;
                        $i$f$filterIndexedTo = $i$f$filterIndexedTo2;
                        $this$consumeEachIndexed$iv2 = $this$consumeEachIndexed$iv;
                        i = 1;
                        i2 = 0;
                    } catch (Throwable th3) {
                        e$iv$iv$iv = th3;
                        int i6 = index$iv2;
                        Throwable cause$iv$iv$iv22 = e$iv$iv$iv;
                        throw e$iv$iv$iv;
                    }
                } else {
                    ReceiveChannel receiveChannel2 = $this$consumeEachIndexed$iv2;
                    Function2 function22 = predicate;
                    try {
                        Unit unit = Unit.INSTANCE;
                        InlineMarker.finallyStart(1);
                        ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv);
                        InlineMarker.finallyEnd(1);
                        return collection;
                    } catch (Throwable th4) {
                        e$iv$iv$iv = th4;
                        Throwable cause$iv$iv$iv222 = e$iv$iv$iv;
                        throw e$iv$iv$iv;
                    }
                }
            }
        } catch (Throwable th5) {
            e$iv$iv$iv = th5;
            ReceiveChannel receiveChannel3 = $this$consumeEachIndexed$iv2;
            Function2 function23 = predicate;
            Throwable cause$iv$iv$iv2222 = e$iv$iv$iv;
            throw e$iv$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v14, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v11, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v12, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v27, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v5, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v20, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v36, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v17, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v38, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v17, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v39, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v12, resolved type: kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super E, java.lang.Boolean>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v41, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v4, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0128  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x018d  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x01b0 A[Catch:{ all -> 0x02e8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002a  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, C extends kotlinx.coroutines.channels.SendChannel<? super E>> java.lang.Object filterIndexedTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r29, C r30, kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super E, java.lang.Boolean> r31, kotlin.coroutines.Continuation<? super C> r32) {
        /*
            r1 = r32
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterIndexedTo$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterIndexedTo$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterIndexedTo$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterIndexedTo$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterIndexedTo$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 2
            r6 = 1
            r7 = 0
            r8 = 0
            if (r4 == 0) goto L_0x0128
            if (r4 == r6) goto L_0x00b5
            if (r4 != r5) goto L_0x00ad
            r4 = r7
            r9 = r7
            r10 = r7
            r11 = r8
            r12 = r8
            r13 = r8
            r14 = r7
            r15 = r7
            r16 = r8
            r17 = r8
            r18 = r8
            r19 = r8
            r20 = r8
            r21 = r7
            r22 = r8
            r23 = r7
            java.lang.Object r5 = r2.L$13
            int r7 = r2.I$0
            java.lang.Object r6 = r2.L$12
            kotlin.collections.IndexedValue r6 = (kotlin.collections.IndexedValue) r6
            java.lang.Object r8 = r2.L$11
            r20 = r0
            java.lang.Object r0 = r2.L$10
            r19 = r0
            java.lang.Object r0 = r2.L$9
            kotlinx.coroutines.channels.ChannelIterator r0 = (kotlinx.coroutines.channels.ChannelIterator) r0
            r24 = r0
            java.lang.Object r0 = r2.L$8
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            java.lang.Object r12 = r2.L$7
            r11 = r12
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            java.lang.Object r12 = r2.L$6
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            r16 = r0
            java.lang.Object r0 = r2.L$5
            r13 = r0
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            java.lang.Object r0 = r2.L$4
            r17 = r0
            kotlin.jvm.internal.Ref$IntRef r17 = (kotlin.jvm.internal.Ref.IntRef) r17
            java.lang.Object r0 = r2.L$3
            r18 = r0
            kotlinx.coroutines.channels.ReceiveChannel r18 = (kotlinx.coroutines.channels.ReceiveChannel) r18
            java.lang.Object r0 = r2.L$2
            r25 = r0
            kotlin.jvm.functions.Function2 r25 = (kotlin.jvm.functions.Function2) r25
            java.lang.Object r0 = r2.L$1
            r26 = r0
            kotlinx.coroutines.channels.SendChannel r26 = (kotlinx.coroutines.channels.SendChannel) r26
            java.lang.Object r0 = r2.L$0
            r27 = r0
            kotlinx.coroutines.channels.ReceiveChannel r27 = (kotlinx.coroutines.channels.ReceiveChannel) r27
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x009c }
            r0 = r16
            r16 = r23
            r23 = r10
            r10 = r8
            r8 = r24
            goto L_0x0243
        L_0x009c:
            r0 = move-exception
            r19 = r15
            r6 = r18
            r15 = r12
            r18 = r14
            r12 = r25
            r25 = r27
            r14 = r13
            r13 = r26
            goto L_0x0338
        L_0x00ad:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x00b5:
            r20 = r0
            r4 = r7
            r9 = r7
            r0 = r7
            r5 = r8
            r6 = r8
            r10 = r8
            r14 = r7
            r15 = r7
            r11 = r8
            r12 = r8
            r13 = r8
            java.lang.Object r8 = r2.L$9
            kotlinx.coroutines.channels.ChannelIterator r8 = (kotlinx.coroutines.channels.ChannelIterator) r8
            java.lang.Object r7 = r2.L$8
            r6 = r7
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r2.L$7
            r5 = r7
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            java.lang.Object r7 = r2.L$6
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r11 = r2.L$5
            r10 = r11
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r11 = r2.L$4
            kotlin.jvm.internal.Ref$IntRef r11 = (kotlin.jvm.internal.Ref.IntRef) r11
            java.lang.Object r12 = r2.L$3
            r18 = r12
            kotlinx.coroutines.channels.ReceiveChannel r18 = (kotlinx.coroutines.channels.ReceiveChannel) r18
            java.lang.Object r12 = r2.L$2
            kotlin.jvm.functions.Function2 r12 = (kotlin.jvm.functions.Function2) r12
            java.lang.Object r13 = r2.L$1
            kotlinx.coroutines.channels.SendChannel r13 = (kotlinx.coroutines.channels.SendChannel) r13
            r19 = r0
            java.lang.Object r0 = r2.L$0
            r21 = r0
            kotlinx.coroutines.channels.ReceiveChannel r21 = (kotlinx.coroutines.channels.ReceiveChannel) r21
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0118 }
            r29 = r1
            r30 = r3
            r0 = r6
            r6 = r18
            r1 = r21
            r16 = 0
            r17 = 0
            r18 = r14
            r14 = r10
            r10 = r19
            r3 = r20
            r19 = r15
            r15 = r7
            r7 = r4
            r4 = r30
            r28 = r5
            r5 = r2
            r2 = r11
            r11 = r28
            goto L_0x01a8
        L_0x0118:
            r0 = move-exception
            r17 = r11
            r19 = r15
            r6 = r18
            r25 = r21
            r11 = r5
            r15 = r7
            r18 = r14
            r14 = r10
            goto L_0x0338
        L_0x0128:
            r20 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r9 = 0
            r18 = r29
            r4 = 0
            kotlin.jvm.internal.Ref$IntRef r0 = new kotlin.jvm.internal.Ref$IntRef
            r0.<init>()
            r5 = 0
            r0.element = r5
            r17 = r0
            r13 = r18
            r14 = 0
            r12 = r13
            r15 = 0
            r0 = 0
            r11 = r0
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            r6 = r12
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r6.iterator()     // Catch:{ all -> 0x0329 }
            r10 = r5
            r16 = r14
            r5 = r2
            r14 = r11
            r2 = r30
            r30 = r3
            r11 = r8
            r3 = r31
            r31 = r4
            r8 = r6
            r4 = r13
            r6 = r17
            r13 = r9
            r17 = r15
            r9 = r7
            r15 = r12
            r7 = r18
            r12 = r0
            r0 = r20
            r28 = r1
            r1 = r29
            r29 = r28
        L_0x016d:
            r5.L$0 = r1     // Catch:{ all -> 0x0310 }
            r5.L$1 = r2     // Catch:{ all -> 0x0310 }
            r5.L$2 = r3     // Catch:{ all -> 0x0310 }
            r5.L$3 = r7     // Catch:{ all -> 0x0310 }
            r5.L$4 = r6     // Catch:{ all -> 0x0310 }
            r5.L$5 = r4     // Catch:{ all -> 0x0310 }
            r5.L$6 = r15     // Catch:{ all -> 0x0310 }
            r5.L$7 = r14     // Catch:{ all -> 0x0310 }
            r5.L$8 = r8     // Catch:{ all -> 0x0310 }
            r5.L$9 = r11     // Catch:{ all -> 0x0310 }
            r18 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x02f9 }
            java.lang.Object r1 = r11.hasNext(r5)     // Catch:{ all -> 0x02f9 }
            if (r1 != r0) goto L_0x018d
            return r0
        L_0x018d:
            r19 = r17
            r17 = r10
            r10 = r9
            r9 = r13
            r13 = r2
            r2 = r6
            r6 = r7
            r7 = r31
            r28 = r3
            r3 = r0
            r0 = r8
            r8 = r11
            r11 = r14
            r14 = r4
            r4 = r1
            r1 = r18
            r18 = r16
            r16 = r12
            r12 = r28
        L_0x01a8:
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ all -> 0x02e8 }
            boolean r4 = r4.booleanValue()     // Catch:{ all -> 0x02e8 }
            if (r4 == 0) goto L_0x02c1
            java.lang.Object r4 = r8.next()     // Catch:{ all -> 0x02e8 }
            r31 = r4
            r21 = 0
            r32 = r7
            kotlin.collections.IndexedValue r7 = new kotlin.collections.IndexedValue     // Catch:{ all -> 0x02b1 }
            r20 = r9
            int r9 = r2.element     // Catch:{ all -> 0x02a1 }
            r23 = r10
            int r10 = r9 + 1
            r2.element = r10     // Catch:{ all -> 0x02a1 }
            r10 = r31
            r7.<init>(r9, r10)     // Catch:{ all -> 0x02a1 }
            r9 = r16
            r16 = r17
            r17 = 0
            int r24 = r7.component1()     // Catch:{ all -> 0x02a1 }
            r31 = r24
            java.lang.Object r16 = r7.component2()     // Catch:{ all -> 0x02a1 }
            r9 = r16
            r16 = r3
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r31)     // Catch:{ all -> 0x02a1 }
            java.lang.Object r3 = r12.invoke(r3, r9)     // Catch:{ all -> 0x02a1 }
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x02a1 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x02a1 }
            if (r3 == 0) goto L_0x0261
            r5.L$0 = r1     // Catch:{ all -> 0x02a1 }
            r5.L$1 = r13     // Catch:{ all -> 0x02a1 }
            r5.L$2 = r12     // Catch:{ all -> 0x02a1 }
            r5.L$3 = r6     // Catch:{ all -> 0x02a1 }
            r5.L$4 = r2     // Catch:{ all -> 0x02a1 }
            r5.L$5 = r14     // Catch:{ all -> 0x02a1 }
            r5.L$6 = r15     // Catch:{ all -> 0x02a1 }
            r5.L$7 = r11     // Catch:{ all -> 0x02a1 }
            r5.L$8 = r0     // Catch:{ all -> 0x02a1 }
            r5.L$9 = r8     // Catch:{ all -> 0x02a1 }
            r5.L$10 = r4     // Catch:{ all -> 0x02a1 }
            r5.L$11 = r10     // Catch:{ all -> 0x02a1 }
            r5.L$12 = r7     // Catch:{ all -> 0x02a1 }
            r3 = r31
            r5.I$0 = r3     // Catch:{ all -> 0x02a1 }
            r5.L$13 = r9     // Catch:{ all -> 0x02a1 }
            r24 = r0
            r0 = 2
            r5.label = r0     // Catch:{ all -> 0x02a1 }
            java.lang.Object r0 = r13.send(r9, r5)     // Catch:{ all -> 0x02a1 }
            r25 = r1
            r1 = r16
            if (r0 != r1) goto L_0x021f
            return r1
        L_0x021f:
            r26 = r13
            r13 = r14
            r16 = r17
            r14 = r18
            r0 = r24
            r27 = r25
            r17 = r2
            r2 = r5
            r18 = r6
            r6 = r7
            r5 = r9
            r25 = r12
            r12 = r15
            r15 = r19
            r9 = r20
            r20 = r1
            r7 = r3
            r19 = r4
            r1 = r29
            r3 = r30
            r4 = r32
        L_0x0243:
            r16 = r11
            r6 = r17
            r11 = r7
            r17 = r12
            r7 = r18
            r12 = r25
            r18 = r14
            r14 = r8
            r8 = r0
            r0 = r20
            r20 = r9
            r9 = r23
            r23 = r21
            r21 = r15
            r15 = r5
            r5 = r2
            r2 = r26
            goto L_0x0287
        L_0x0261:
            r3 = r31
            r24 = r0
            r25 = r1
            r1 = r16
            r0 = r1
            r7 = r6
            r16 = r11
            r17 = r15
            r27 = r25
            r1 = r29
            r6 = r2
            r11 = r3
            r15 = r9
            r2 = r13
            r13 = r14
            r9 = r23
            r3 = r30
            r14 = r8
            r23 = r21
            r8 = r24
            r21 = r19
            r19 = r4
            r4 = r32
        L_0x0287:
            r29 = r1
            r30 = r3
            r31 = r4
            r10 = r11
            r3 = r12
            r4 = r13
            r11 = r14
            r12 = r15
            r14 = r16
            r15 = r17
            r16 = r18
            r13 = r20
            r17 = r21
            r1 = r27
            goto L_0x016d
        L_0x02a1:
            r0 = move-exception
            r25 = r1
            r1 = r29
            r3 = r30
            r4 = r32
            r17 = r2
            r2 = r5
            r9 = r20
            goto L_0x0338
        L_0x02b1:
            r0 = move-exception
            r25 = r1
            r20 = r9
            r1 = r29
            r3 = r30
            r4 = r32
            r17 = r2
            r2 = r5
            goto L_0x0338
        L_0x02c1:
            r24 = r0
            r25 = r1
            r32 = r7
            r20 = r9
            r23 = r10
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x02da }
            r1 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r11)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            return r13
        L_0x02da:
            r0 = move-exception
            r1 = r29
            r3 = r30
            r4 = r32
            r17 = r2
            r2 = r5
            r9 = r20
            goto L_0x0338
        L_0x02e8:
            r0 = move-exception
            r25 = r1
            r32 = r7
            r20 = r9
            r1 = r29
            r3 = r30
            r4 = r32
            r17 = r2
            r2 = r5
            goto L_0x0338
        L_0x02f9:
            r0 = move-exception
            r1 = r29
            r12 = r3
            r9 = r13
            r11 = r14
            r19 = r17
            r25 = r18
            r3 = r30
            r13 = r2
            r14 = r4
            r2 = r5
            r17 = r6
            r6 = r7
            r18 = r16
            r4 = r31
            goto L_0x0338
        L_0x0310:
            r0 = move-exception
            r18 = r1
            r1 = r29
            r12 = r3
            r9 = r13
            r11 = r14
            r19 = r17
            r25 = r18
            r3 = r30
            r13 = r2
            r14 = r4
            r2 = r5
            r17 = r6
            r6 = r7
            r18 = r16
            r4 = r31
            goto L_0x0338
        L_0x0329:
            r0 = move-exception
            r25 = r29
            r19 = r15
            r6 = r18
            r15 = r12
            r18 = r14
            r12 = r31
            r14 = r13
            r13 = r30
        L_0x0338:
            r5 = r0
            throw r0     // Catch:{ all -> 0x033b }
        L_0x033b:
            r0 = move-exception
            r7 = r0
            r8 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r8)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.filterIndexedTo(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object filterIndexedTo$$forInline(ReceiveChannel $this$filterIndexedTo, SendChannel destination, Function2 predicate, Continuation continuation) {
        int index;
        Object element;
        ReceiveChannel $this$consumeEachIndexed$iv;
        SendChannel sendChannel = destination;
        Continuation continuation2 = continuation;
        int $i$f$filterIndexedTo = 0;
        ReceiveChannel $this$consumeEachIndexed$iv2 = $this$filterIndexedTo;
        int index$iv = 0;
        ReceiveChannel $this$consume$iv$iv$iv = $this$consumeEachIndexed$iv2;
        Throwable cause$iv$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv$iv.iterator();
            int i2 = 0;
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation2);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    int index$iv2 = index$iv + 1;
                    int $i$f$filterIndexedTo2 = $i$f$filterIndexedTo;
                    Object it$iv = it.next();
                    try {
                        IndexedValue $dstr$index$element = new IndexedValue(index$iv, it$iv);
                        int i3 = i2;
                        index = $dstr$index$element.component1();
                        element = $dstr$index$element.component2();
                        Object obj = it$iv;
                        $this$consumeEachIndexed$iv = $this$consumeEachIndexed$iv2;
                    } catch (Throwable th) {
                        e$iv$iv$iv = th;
                        ReceiveChannel receiveChannel = $this$consumeEachIndexed$iv2;
                        Function2 function2 = predicate;
                        int i4 = index$iv2;
                        Throwable cause$iv$iv$iv2 = e$iv$iv$iv;
                        try {
                            throw e$iv$iv$iv;
                        } catch (Throwable e$iv$iv$iv) {
                            Throwable th2 = e$iv$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                    try {
                        if (((Boolean) predicate.invoke(Integer.valueOf(index), element)).booleanValue()) {
                            InlineMarker.mark(0);
                            sendChannel.send(element, continuation2);
                            InlineMarker.mark(2);
                            InlineMarker.mark(1);
                        }
                        i2 = index;
                        index$iv = index$iv2;
                        $i$f$filterIndexedTo = $i$f$filterIndexedTo2;
                        $this$consumeEachIndexed$iv2 = $this$consumeEachIndexed$iv;
                        i = 1;
                    } catch (Throwable th3) {
                        e$iv$iv$iv = th3;
                        int i5 = index$iv2;
                        Throwable cause$iv$iv$iv22 = e$iv$iv$iv;
                        throw e$iv$iv$iv;
                    }
                } else {
                    ReceiveChannel receiveChannel2 = $this$consumeEachIndexed$iv2;
                    Function2 function22 = predicate;
                    try {
                        Unit unit = Unit.INSTANCE;
                        InlineMarker.finallyStart(1);
                        ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv);
                        InlineMarker.finallyEnd(1);
                        return sendChannel;
                    } catch (Throwable th4) {
                        e$iv$iv$iv = th4;
                        Throwable cause$iv$iv$iv222 = e$iv$iv$iv;
                        throw e$iv$iv$iv;
                    }
                }
            }
        } catch (Throwable th5) {
            e$iv$iv$iv = th5;
            ReceiveChannel receiveChannel3 = $this$consumeEachIndexed$iv2;
            Function2 function23 = predicate;
            Throwable cause$iv$iv$iv2222 = e$iv$iv$iv;
            throw e$iv$iv$iv;
        }
    }

    public static /* synthetic */ ReceiveChannel filterNot$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.filterNot(receiveChannel, coroutineContext, function2);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> filterNot(ReceiveChannel<? extends E> $this$filterNot, CoroutineContext context, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        Intrinsics.checkParameterIsNotNull($this$filterNot, "$this$filterNot");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        return ChannelsKt.filter($this$filterNot, context, new ChannelsKt__Channels_commonKt$filterNot$1(predicate, (Continuation) null));
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> filterNotNull(ReceiveChannel<? extends E> $this$filterNotNull) {
        Intrinsics.checkParameterIsNotNull($this$filterNotNull, "$this$filterNotNull");
        ReceiveChannel<E> filter$default = filter$default($this$filterNotNull, (CoroutineContext) null, new ChannelsKt__Channels_commonKt$filterNotNull$1((Continuation) null), 1, (Object) null);
        if (filter$default != null) {
            return filter$default;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.ReceiveChannel<E>");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00c7 A[Catch:{ all -> 0x00ea }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00e3 A[Catch:{ all -> 0x00ea }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, C extends java.util.Collection<? super E>> java.lang.Object filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r18, C r19, kotlin.coroutines.Continuation<? super C> r20) {
        /*
            r1 = r20
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotNullTo$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotNullTo$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotNullTo$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotNullTo$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotNullTo$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x0079
            if (r4 != r5) goto L_0x0071
            r4 = 0
            r7 = r4
            r8 = r4
            r9 = r6
            r10 = r6
            r11 = r6
            java.lang.Object r12 = r2.L$6
            kotlinx.coroutines.channels.ChannelIterator r12 = (kotlinx.coroutines.channels.ChannelIterator) r12
            java.lang.Object r13 = r2.L$5
            r9 = r13
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r13 = r2.L$4
            r11 = r13
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            java.lang.Object r13 = r2.L$3
            r6 = r13
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r13 = r2.L$2
            r10 = r13
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r13 = r2.L$1
            java.util.Collection r13 = (java.util.Collection) r13
            java.lang.Object r14 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r14 = (kotlinx.coroutines.channels.ReceiveChannel) r14
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0065 }
            r15 = r12
            r12 = r9
            r9 = r7
            r7 = r3
            r17 = r4
            r4 = r1
            r1 = r14
            r14 = r11
            r11 = r8
            r8 = r6
            r6 = r2
            r2 = r13
            r13 = r10
            r10 = r17
            goto L_0x00bf
        L_0x0065:
            r0 = move-exception
            r4 = r2
            r12 = r10
            r2 = r13
            r10 = r8
            r8 = r7
            r7 = r6
            r6 = r3
            r3 = r1
            r1 = r14
            goto L_0x0103
        L_0x0071:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0079:
            kotlin.ResultKt.throwOnFailure(r3)
            r10 = r18
            r8 = 0
            r4 = r10
            r7 = 0
            r11 = r6
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            r6 = r4
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r6.iterator()     // Catch:{ all -> 0x00f7 }
            r13 = r11
            r14 = r12
            r11 = r6
            r12 = r10
            r6 = r3
            r10 = r8
            r3 = r1
            r8 = r7
            r1 = r18
            r7 = r4
            r4 = r2
            r2 = r19
        L_0x0099:
            r4.L$0 = r1     // Catch:{ all -> 0x00f4 }
            r4.L$1 = r2     // Catch:{ all -> 0x00f4 }
            r4.L$2 = r12     // Catch:{ all -> 0x00f4 }
            r4.L$3 = r7     // Catch:{ all -> 0x00f4 }
            r4.L$4 = r13     // Catch:{ all -> 0x00f4 }
            r4.L$5 = r11     // Catch:{ all -> 0x00f4 }
            r4.L$6 = r14     // Catch:{ all -> 0x00f4 }
            r4.label = r5     // Catch:{ all -> 0x00f4 }
            java.lang.Object r15 = r14.hasNext(r4)     // Catch:{ all -> 0x00f4 }
            if (r15 != r0) goto L_0x00b0
            return r0
        L_0x00b0:
            r17 = r4
            r4 = r3
            r3 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r17
        L_0x00bf:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x00ea }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x00ea }
            if (r3 == 0) goto L_0x00e3
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x00ea }
            r18 = r3
            r16 = 0
            r5 = r18
            if (r5 == 0) goto L_0x00d6
            r2.add(r5)     // Catch:{ all -> 0x00ea }
        L_0x00d6:
            r3 = r4
            r4 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r5 = 1
            goto L_0x0099
        L_0x00e3:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00ea }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r14)
            return r2
        L_0x00ea:
            r0 = move-exception
            r3 = r4
            r4 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r10 = r11
            r12 = r13
            r11 = r14
            goto L_0x0103
        L_0x00f4:
            r0 = move-exception
            r11 = r13
            goto L_0x0103
        L_0x00f7:
            r0 = move-exception
            r6 = r3
            r12 = r10
            r3 = r1
            r10 = r8
            r1 = r18
            r8 = r7
            r7 = r4
            r4 = r2
            r2 = r19
        L_0x0103:
            r5 = r0
            throw r0     // Catch:{ all -> 0x0106 }
        L_0x0106:
            r0 = move-exception
            r9 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r5)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v6, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v16, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v5, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v19, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ef  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00fd A[Catch:{ all -> 0x0158 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, C extends kotlinx.coroutines.channels.SendChannel<? super E>> java.lang.Object filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r20, C r21, kotlin.coroutines.Continuation<? super C> r22) {
        /*
            r1 = r22
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotNullTo$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotNullTo$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotNullTo$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotNullTo$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotNullTo$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 2
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x00b5
            r8 = 0
            if (r4 == r6) goto L_0x0072
            if (r4 != r5) goto L_0x006a
            r4 = r8
            r9 = r8
            r10 = r7
            r11 = r8
            r12 = r7
            r13 = r7
            r14 = r7
            r15 = r7
            java.lang.Object r10 = r2.L$8
            java.lang.Object r13 = r2.L$7
            java.lang.Object r5 = r2.L$6
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r2.L$5
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r12 = r2.L$4
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            java.lang.Object r15 = r2.L$3
            r7 = r15
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r15 = r2.L$2
            r14 = r15
            kotlinx.coroutines.channels.ReceiveChannel r14 = (kotlinx.coroutines.channels.ReceiveChannel) r14
            java.lang.Object r15 = r2.L$1
            kotlinx.coroutines.channels.SendChannel r15 = (kotlinx.coroutines.channels.SendChannel) r15
            r17 = r0
            java.lang.Object r0 = r2.L$0
            r18 = r0
            kotlinx.coroutines.channels.ReceiveChannel r18 = (kotlinx.coroutines.channels.ReceiveChannel) r18
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0063 }
            r0 = r17
            goto L_0x0133
        L_0x0063:
            r0 = move-exception
            r5 = r2
            r6 = r3
            r2 = r15
            r3 = r1
            goto L_0x016f
        L_0x006a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0072:
            r17 = r0
            r4 = r8
            r9 = r8
            r0 = r7
            r5 = r8
            r6 = r7
            r8 = r7
            java.lang.Object r10 = r2.L$6
            kotlinx.coroutines.channels.ChannelIterator r10 = (kotlinx.coroutines.channels.ChannelIterator) r10
            java.lang.Object r11 = r2.L$5
            r0 = r11
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            java.lang.Object r11 = r2.L$4
            r12 = r11
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            java.lang.Object r8 = r2.L$3
            r7 = r8
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r8 = r2.L$2
            r14 = r8
            kotlinx.coroutines.channels.ReceiveChannel r14 = (kotlinx.coroutines.channels.ReceiveChannel) r14
            java.lang.Object r6 = r2.L$1
            kotlinx.coroutines.channels.SendChannel r6 = (kotlinx.coroutines.channels.SendChannel) r6
            java.lang.Object r8 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x00ac }
            r15 = r3
            r11 = r9
            r9 = r10
            r13 = 1
            r3 = r1
            r10 = r7
            r1 = r8
            r7 = r0
            r8 = r5
            r0 = r17
            r5 = r2
            r2 = r6
            r6 = r15
            goto L_0x00f5
        L_0x00ac:
            r0 = move-exception
            r5 = r2
            r2 = r6
            r18 = r8
            r6 = r3
            r3 = r1
            goto L_0x016f
        L_0x00b5:
            r17 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r14 = r20
            r9 = 0
            r4 = r14
            r5 = 0
            r12 = r7
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            r0 = r4
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r0.iterator()     // Catch:{ all -> 0x0165 }
            r10 = r4
            r8 = r6
            r11 = r9
            r4 = r17
            r6 = r3
            r9 = r7
            r3 = r1
            r7 = r5
            r1 = r20
            r5 = r2
            r2 = r21
        L_0x00d7:
            r5.L$0 = r1     // Catch:{ all -> 0x015e }
            r5.L$1 = r2     // Catch:{ all -> 0x015e }
            r5.L$2 = r14     // Catch:{ all -> 0x015e }
            r5.L$3 = r10     // Catch:{ all -> 0x015e }
            r5.L$4 = r12     // Catch:{ all -> 0x015e }
            r5.L$5 = r0     // Catch:{ all -> 0x015e }
            r5.L$6 = r9     // Catch:{ all -> 0x015e }
            r13 = 1
            r5.label = r13     // Catch:{ all -> 0x015e }
            java.lang.Object r15 = r9.hasNext(r5)     // Catch:{ all -> 0x015e }
            if (r15 != r4) goto L_0x00ef
            return r4
        L_0x00ef:
            r19 = r7
            r7 = r0
            r0 = r4
            r4 = r19
        L_0x00f5:
            java.lang.Boolean r15 = (java.lang.Boolean) r15     // Catch:{ all -> 0x0158 }
            boolean r15 = r15.booleanValue()     // Catch:{ all -> 0x0158 }
            if (r15 == 0) goto L_0x014b
            java.lang.Object r15 = r9.next()     // Catch:{ all -> 0x0158 }
            r20 = r15
            r16 = 0
            r13 = r20
            if (r13 == 0) goto L_0x0141
            r5.L$0 = r1     // Catch:{ all -> 0x0158 }
            r5.L$1 = r2     // Catch:{ all -> 0x0158 }
            r5.L$2 = r14     // Catch:{ all -> 0x0158 }
            r5.L$3 = r10     // Catch:{ all -> 0x0158 }
            r5.L$4 = r12     // Catch:{ all -> 0x0158 }
            r5.L$5 = r7     // Catch:{ all -> 0x0158 }
            r5.L$6 = r9     // Catch:{ all -> 0x0158 }
            r5.L$7 = r15     // Catch:{ all -> 0x0158 }
            r5.L$8 = r13     // Catch:{ all -> 0x0158 }
            r18 = r1
            r1 = 2
            r5.label = r1     // Catch:{ all -> 0x0154 }
            java.lang.Object r1 = r2.send(r13, r5)     // Catch:{ all -> 0x0154 }
            if (r1 != r0) goto L_0x0127
            return r0
        L_0x0127:
            r1 = r3
            r3 = r6
            r6 = r7
            r7 = r10
            r10 = r13
            r13 = r15
            r15 = r2
            r2 = r5
            r5 = r9
            r9 = r11
            r11 = r16
        L_0x0133:
            r10 = r7
            r11 = r9
            r7 = r4
            r9 = r5
            r4 = r0
            r5 = r2
            r0 = r6
            r2 = r15
            r6 = r3
            r15 = r13
            r3 = r1
            r1 = r18
            goto L_0x0149
        L_0x0141:
            r18 = r1
            r19 = r4
            r4 = r0
            r0 = r7
            r7 = r19
        L_0x0149:
            goto L_0x00d7
        L_0x014b:
            r18 = r1
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0154 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r12)
            return r2
        L_0x0154:
            r0 = move-exception
            r7 = r10
            r9 = r11
            goto L_0x016f
        L_0x0158:
            r0 = move-exception
            r18 = r1
            r7 = r10
            r9 = r11
            goto L_0x016f
        L_0x015e:
            r0 = move-exception
            r18 = r1
            r4 = r7
            r7 = r10
            r9 = r11
            goto L_0x016f
        L_0x0165:
            r0 = move-exception
            r18 = r20
            r6 = r3
            r7 = r4
            r4 = r5
            r3 = r1
            r5 = r2
            r2 = r21
        L_0x016f:
            r1 = r0
            throw r0     // Catch:{ all -> 0x0172 }
        L_0x0172:
            r0 = move-exception
            r8 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r1)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.filterNotNullTo(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00d1 A[Catch:{ all -> 0x0101 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, C extends java.util.Collection<? super E>> java.lang.Object filterNotTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r20, C r21, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r22, kotlin.coroutines.Continuation<? super C> r23) {
        /*
            r1 = r23
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotTo$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotTo$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotTo$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotTo$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotTo$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L_0x0081
            if (r4 != r6) goto L_0x0079
            r4 = 0
            r7 = r4
            r8 = r4
            r9 = r4
            r10 = r5
            r11 = r5
            r12 = r5
            java.lang.Object r13 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r13 = (kotlinx.coroutines.channels.ChannelIterator) r13
            java.lang.Object r14 = r2.L$6
            r11 = r14
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r14 = r2.L$5
            r10 = r14
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r14 = r2.L$4
            r12 = r14
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r14 = r2.L$3
            r5 = r14
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r14 = r2.L$2
            kotlin.jvm.functions.Function1 r14 = (kotlin.jvm.functions.Function1) r14
            java.lang.Object r15 = r2.L$1
            java.util.Collection r15 = (java.util.Collection) r15
            java.lang.Object r6 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006c }
            r16 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r4
            r4 = r1
            r1 = r6
            r6 = r3
            r19 = r5
            r5 = r2
            r2 = r15
            r15 = r12
            r12 = r10
            r10 = r8
            r8 = r19
            goto L_0x00c9
        L_0x006c:
            r0 = move-exception
            r4 = r1
            r1 = r6
            r6 = r3
            r3 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r5
            r5 = r2
            r2 = r15
            goto L_0x0121
        L_0x0079:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0081:
            kotlin.ResultKt.throwOnFailure(r3)
            r8 = 0
            r4 = r20
            r7 = 0
            r12 = r4
            r9 = 0
            r10 = r5
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r5 = r12
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r11 = r5.iterator()     // Catch:{ all -> 0x0113 }
            r13 = r5
            r15 = r11
            r14 = r12
            r5 = r2
            r11 = r9
            r12 = r10
            r2 = r21
            r9 = r7
            r10 = r8
            r8 = r4
            r7 = r6
            r4 = r1
            r6 = r3
            r1 = r20
            r3 = r22
        L_0x00a6:
            r5.L$0 = r1     // Catch:{ all -> 0x010d }
            r5.L$1 = r2     // Catch:{ all -> 0x010d }
            r5.L$2 = r3     // Catch:{ all -> 0x010d }
            r5.L$3 = r8     // Catch:{ all -> 0x010d }
            r5.L$4 = r14     // Catch:{ all -> 0x010d }
            r5.L$5 = r12     // Catch:{ all -> 0x010d }
            r5.L$6 = r13     // Catch:{ all -> 0x010d }
            r5.L$7 = r15     // Catch:{ all -> 0x010d }
            r20 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x0107 }
            java.lang.Object r1 = r15.hasNext(r5)     // Catch:{ all -> 0x0107 }
            if (r1 != r0) goto L_0x00c2
            return r0
        L_0x00c2:
            r16 = r15
            r15 = r14
            r14 = r3
            r3 = r1
            r1 = r20
        L_0x00c9:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0101 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0101 }
            if (r3 == 0) goto L_0x00f3
            java.lang.Object r3 = r16.next()     // Catch:{ all -> 0x0101 }
            r20 = r3
            r17 = 0
            r21 = r0
            r0 = r20
            java.lang.Object r18 = r14.invoke(r0)     // Catch:{ all -> 0x0101 }
            java.lang.Boolean r18 = (java.lang.Boolean) r18     // Catch:{ all -> 0x0101 }
            boolean r18 = r18.booleanValue()     // Catch:{ all -> 0x0101 }
            if (r18 != 0) goto L_0x00ec
            r2.add(r0)     // Catch:{ all -> 0x0101 }
        L_0x00ec:
            r0 = r21
            r3 = r14
            r14 = r15
            r15 = r16
            goto L_0x00a6
        L_0x00f3:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0101 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r2
        L_0x0101:
            r0 = move-exception
            r7 = r9
            r9 = r11
            r3 = r14
            r14 = r15
            goto L_0x0121
        L_0x0107:
            r0 = move-exception
            r1 = r20
            r7 = r9
            r9 = r11
            goto L_0x0121
        L_0x010d:
            r0 = move-exception
            r20 = r1
            r7 = r9
            r9 = r11
            goto L_0x0121
        L_0x0113:
            r0 = move-exception
            r5 = r2
            r6 = r3
            r14 = r12
            r2 = r21
            r3 = r22
            r12 = r10
            r10 = r8
            r8 = r4
            r4 = r1
            r1 = r20
        L_0x0121:
            r11 = r0
            throw r0     // Catch:{ all -> 0x0124 }
        L_0x0124:
            r0 = move-exception
            r12 = r0
            r13 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r13)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r14, r11)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r13)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.filterNotTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object filterNotTo$$forInline(ReceiveChannel $this$filterNotTo, Collection destination, Function1 predicate, Continuation continuation) {
        Collection collection = destination;
        ReceiveChannel $this$consume$iv$iv = $this$filterNotTo;
        Throwable cause$iv$iv = null;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it2 = it.next();
                    try {
                        if (!((Boolean) predicate.invoke(it2)).booleanValue()) {
                            collection.add(it2);
                        }
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return collection;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v13, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v18, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v12, resolved type: kotlin.jvm.functions.Function1<? super E, java.lang.Boolean>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v25, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v17, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v17, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v29, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v21, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v13, resolved type: kotlin.jvm.functions.Function1<? super E, java.lang.Boolean>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v32, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v2, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x012b A[Catch:{ all -> 0x01dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, C extends kotlinx.coroutines.channels.SendChannel<? super E>> java.lang.Object filterNotTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r21, C r22, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r23, kotlin.coroutines.Continuation<? super C> r24) {
        /*
            r1 = r24
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotTo$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotTo$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotTo$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotTo$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterNotTo$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 2
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x00d2
            r8 = 0
            if (r4 == r6) goto L_0x007f
            if (r4 != r5) goto L_0x0077
            r4 = r8
            r9 = r8
            r10 = r8
            r11 = r7
            r12 = r7
            r13 = r8
            r14 = r7
            r15 = r7
            r16 = r7
            java.lang.Object r11 = r2.L$9
            java.lang.Object r12 = r2.L$8
            java.lang.Object r5 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r2.L$6
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r15 = r2.L$5
            r14 = r15
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            java.lang.Object r15 = r2.L$4
            kotlinx.coroutines.channels.ReceiveChannel r15 = (kotlinx.coroutines.channels.ReceiveChannel) r15
            r16 = r0
            java.lang.Object r0 = r2.L$3
            r7 = r0
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r0 = r2.L$2
            r17 = r0
            kotlin.jvm.functions.Function1 r17 = (kotlin.jvm.functions.Function1) r17
            java.lang.Object r0 = r2.L$1
            r18 = r0
            kotlinx.coroutines.channels.SendChannel r18 = (kotlinx.coroutines.channels.SendChannel) r18
            java.lang.Object r0 = r2.L$0
            r19 = r0
            kotlinx.coroutines.channels.ReceiveChannel r19 = (kotlinx.coroutines.channels.ReceiveChannel) r19
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006d }
            r0 = r16
            goto L_0x017b
        L_0x006d:
            r0 = move-exception
            r6 = r2
            r8 = r4
            r2 = r18
            r12 = r19
            r4 = r1
            goto L_0x020b
        L_0x0077:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x007f:
            r16 = r0
            r4 = r8
            r9 = r8
            r13 = r8
            r0 = r7
            r5 = r8
            r6 = r7
            r8 = r7
            java.lang.Object r10 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r10 = (kotlinx.coroutines.channels.ChannelIterator) r10
            java.lang.Object r11 = r2.L$6
            r6 = r11
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r11 = r2.L$5
            r14 = r11
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            java.lang.Object r0 = r2.L$4
            r15 = r0
            kotlinx.coroutines.channels.ReceiveChannel r15 = (kotlinx.coroutines.channels.ReceiveChannel) r15
            java.lang.Object r0 = r2.L$3
            r7 = r0
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r0 = r2.L$2
            r8 = r0
            kotlin.jvm.functions.Function1 r8 = (kotlin.jvm.functions.Function1) r8
            java.lang.Object r0 = r2.L$1
            r11 = r0
            kotlinx.coroutines.channels.SendChannel r11 = (kotlinx.coroutines.channels.SendChannel) r11
            java.lang.Object r0 = r2.L$0
            r12 = r0
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x00c9 }
            r21 = r1
            r1 = r15
            r0 = r16
            r15 = r14
            r14 = r13
            r13 = r9
            r9 = r6
            r6 = r2
            r2 = r11
            r11 = r7
            r7 = r3
            r3 = r8
            r8 = r4
            r4 = r7
            r20 = r10
            r10 = r5
            r5 = r20
            goto L_0x0123
        L_0x00c9:
            r0 = move-exception
            r6 = r2
            r17 = r8
            r2 = r11
            r8 = r4
            r4 = r1
            goto L_0x020b
        L_0x00d2:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r9 = 0
            r4 = r21
            r5 = 0
            r15 = r4
            r13 = 0
            r14 = r7
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            r0 = r15
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r0.iterator()     // Catch:{ all -> 0x0200 }
            r11 = r4
            r8 = r5
            r10 = r7
            r12 = r9
            r5 = r16
            r4 = r1
            r7 = r3
            r9 = r6
            r1 = r21
            r3 = r23
            r6 = r2
            r2 = r22
        L_0x00f7:
            r6.L$0 = r1     // Catch:{ all -> 0x01f5 }
            r6.L$1 = r2     // Catch:{ all -> 0x01f5 }
            r6.L$2 = r3     // Catch:{ all -> 0x01f5 }
            r6.L$3 = r11     // Catch:{ all -> 0x01f5 }
            r6.L$4 = r15     // Catch:{ all -> 0x01f5 }
            r6.L$5 = r14     // Catch:{ all -> 0x01f5 }
            r6.L$6 = r0     // Catch:{ all -> 0x01f5 }
            r6.L$7 = r10     // Catch:{ all -> 0x01f5 }
            r16 = r1
            r1 = 1
            r6.label = r1     // Catch:{ all -> 0x01ec }
            java.lang.Object r1 = r10.hasNext(r6)     // Catch:{ all -> 0x01ec }
            if (r1 != r5) goto L_0x0113
            return r5
        L_0x0113:
            r21 = r4
            r4 = r1
            r1 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r16
            r20 = r9
            r9 = r0
            r0 = r5
            r5 = r10
            r10 = r20
        L_0x0123:
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ all -> 0x01dd }
            boolean r4 = r4.booleanValue()     // Catch:{ all -> 0x01dd }
            if (r4 == 0) goto L_0x01c0
            java.lang.Object r4 = r5.next()     // Catch:{ all -> 0x01dd }
            r22 = r4
            r16 = 0
            r23 = r7
            r7 = r22
            java.lang.Object r17 = r3.invoke(r7)     // Catch:{ all -> 0x01b2 }
            java.lang.Boolean r17 = (java.lang.Boolean) r17     // Catch:{ all -> 0x01b2 }
            boolean r17 = r17.booleanValue()     // Catch:{ all -> 0x01b2 }
            if (r17 != 0) goto L_0x0194
            r6.L$0 = r12     // Catch:{ all -> 0x01b2 }
            r6.L$1 = r2     // Catch:{ all -> 0x01b2 }
            r6.L$2 = r3     // Catch:{ all -> 0x01b2 }
            r6.L$3 = r11     // Catch:{ all -> 0x01b2 }
            r6.L$4 = r1     // Catch:{ all -> 0x01b2 }
            r6.L$5 = r15     // Catch:{ all -> 0x01b2 }
            r6.L$6 = r9     // Catch:{ all -> 0x01b2 }
            r6.L$7 = r5     // Catch:{ all -> 0x01b2 }
            r6.L$8 = r4     // Catch:{ all -> 0x01b2 }
            r6.L$9 = r7     // Catch:{ all -> 0x01b2 }
            r17 = r3
            r3 = 2
            r6.label = r3     // Catch:{ all -> 0x01d2 }
            java.lang.Object r3 = r2.send(r7, r6)     // Catch:{ all -> 0x01d2 }
            if (r3 != r0) goto L_0x0163
            return r0
        L_0x0163:
            r3 = r23
            r18 = r2
            r2 = r6
            r6 = r9
            r19 = r12
            r9 = r13
            r13 = r14
            r14 = r15
            r15 = r1
            r12 = r4
            r4 = r8
            r8 = r10
            r10 = r16
            r1 = r21
            r20 = r11
            r11 = r7
            r7 = r20
        L_0x017b:
            r11 = r5
            r10 = r8
            r16 = r15
            r5 = r1
            r8 = r3
            r15 = r14
            r3 = r17
            r1 = r19
            r14 = r13
            r13 = r9
            r9 = r4
            r4 = r12
            r12 = r7
            r7 = r2
            r2 = r18
            r20 = r6
            r6 = r0
            r0 = r20
            goto L_0x01a3
        L_0x0194:
            r17 = r3
            r16 = r1
            r7 = r6
            r1 = r12
            r6 = r0
            r0 = r9
            r12 = r11
            r11 = r5
            r9 = r8
            r5 = r21
            r8 = r23
        L_0x01a3:
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r15 = r16
            goto L_0x00f7
        L_0x01b2:
            r0 = move-exception
            r17 = r3
            r4 = r21
            r3 = r23
            r7 = r11
            r9 = r13
            r13 = r14
            r14 = r15
            r15 = r1
            goto L_0x020b
        L_0x01c0:
            r17 = r3
            r23 = r7
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x01d2 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r15)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r2
        L_0x01d2:
            r0 = move-exception
            r4 = r21
            r3 = r23
            r7 = r11
            r9 = r13
            r13 = r14
            r14 = r15
            r15 = r1
            goto L_0x020b
        L_0x01dd:
            r0 = move-exception
            r17 = r3
            r23 = r7
            r4 = r21
            r3 = r23
            r7 = r11
            r9 = r13
            r13 = r14
            r14 = r15
            r15 = r1
            goto L_0x020b
        L_0x01ec:
            r0 = move-exception
            r17 = r3
            r3 = r7
            r7 = r11
            r9 = r12
            r12 = r16
            goto L_0x020b
        L_0x01f5:
            r0 = move-exception
            r16 = r1
            r17 = r3
            r3 = r7
            r7 = r11
            r9 = r12
            r12 = r16
            goto L_0x020b
        L_0x0200:
            r0 = move-exception
            r12 = r21
            r17 = r23
            r6 = r2
            r7 = r4
            r8 = r5
            r2 = r22
            r4 = r1
        L_0x020b:
            r1 = r0
            throw r0     // Catch:{ all -> 0x020e }
        L_0x020e:
            r0 = move-exception
            r5 = r0
            r10 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.filterNotTo(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object filterNotTo$$forInline(ReceiveChannel $this$filterNotTo, SendChannel destination, Function1 predicate, Continuation continuation) {
        SendChannel sendChannel = destination;
        Continuation continuation2 = continuation;
        ReceiveChannel $this$consume$iv$iv = $this$filterNotTo;
        Throwable cause$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation2);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it2 = it.next();
                    try {
                        if (!((Boolean) predicate.invoke(it2)).booleanValue()) {
                            InlineMarker.mark(0);
                            sendChannel.send(it2, continuation2);
                            InlineMarker.mark(2);
                            InlineMarker.mark(1);
                        }
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return sendChannel;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00d1 A[Catch:{ all -> 0x0101 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, C extends java.util.Collection<? super E>> java.lang.Object filterTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r20, C r21, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r22, kotlin.coroutines.Continuation<? super C> r23) {
        /*
            r1 = r23
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterTo$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterTo$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterTo$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterTo$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterTo$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L_0x0081
            if (r4 != r6) goto L_0x0079
            r4 = 0
            r7 = r4
            r8 = r4
            r9 = r4
            r10 = r5
            r11 = r5
            r12 = r5
            java.lang.Object r13 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r13 = (kotlinx.coroutines.channels.ChannelIterator) r13
            java.lang.Object r14 = r2.L$6
            r11 = r14
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r14 = r2.L$5
            r10 = r14
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r14 = r2.L$4
            r12 = r14
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r14 = r2.L$3
            r5 = r14
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r14 = r2.L$2
            kotlin.jvm.functions.Function1 r14 = (kotlin.jvm.functions.Function1) r14
            java.lang.Object r15 = r2.L$1
            java.util.Collection r15 = (java.util.Collection) r15
            java.lang.Object r6 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006c }
            r16 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r4
            r4 = r1
            r1 = r6
            r6 = r3
            r19 = r5
            r5 = r2
            r2 = r15
            r15 = r12
            r12 = r10
            r10 = r8
            r8 = r19
            goto L_0x00c9
        L_0x006c:
            r0 = move-exception
            r4 = r1
            r1 = r6
            r6 = r3
            r3 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r5
            r5 = r2
            r2 = r15
            goto L_0x0121
        L_0x0079:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0081:
            kotlin.ResultKt.throwOnFailure(r3)
            r8 = 0
            r4 = r20
            r7 = 0
            r12 = r4
            r9 = 0
            r10 = r5
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r5 = r12
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r11 = r5.iterator()     // Catch:{ all -> 0x0113 }
            r13 = r5
            r15 = r11
            r14 = r12
            r5 = r2
            r11 = r9
            r12 = r10
            r2 = r21
            r9 = r7
            r10 = r8
            r8 = r4
            r7 = r6
            r4 = r1
            r6 = r3
            r1 = r20
            r3 = r22
        L_0x00a6:
            r5.L$0 = r1     // Catch:{ all -> 0x010d }
            r5.L$1 = r2     // Catch:{ all -> 0x010d }
            r5.L$2 = r3     // Catch:{ all -> 0x010d }
            r5.L$3 = r8     // Catch:{ all -> 0x010d }
            r5.L$4 = r14     // Catch:{ all -> 0x010d }
            r5.L$5 = r12     // Catch:{ all -> 0x010d }
            r5.L$6 = r13     // Catch:{ all -> 0x010d }
            r5.L$7 = r15     // Catch:{ all -> 0x010d }
            r20 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x0107 }
            java.lang.Object r1 = r15.hasNext(r5)     // Catch:{ all -> 0x0107 }
            if (r1 != r0) goto L_0x00c2
            return r0
        L_0x00c2:
            r16 = r15
            r15 = r14
            r14 = r3
            r3 = r1
            r1 = r20
        L_0x00c9:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0101 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0101 }
            if (r3 == 0) goto L_0x00f3
            java.lang.Object r3 = r16.next()     // Catch:{ all -> 0x0101 }
            r20 = r3
            r17 = 0
            r21 = r0
            r0 = r20
            java.lang.Object r18 = r14.invoke(r0)     // Catch:{ all -> 0x0101 }
            java.lang.Boolean r18 = (java.lang.Boolean) r18     // Catch:{ all -> 0x0101 }
            boolean r18 = r18.booleanValue()     // Catch:{ all -> 0x0101 }
            if (r18 == 0) goto L_0x00ec
            r2.add(r0)     // Catch:{ all -> 0x0101 }
        L_0x00ec:
            r0 = r21
            r3 = r14
            r14 = r15
            r15 = r16
            goto L_0x00a6
        L_0x00f3:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0101 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r2
        L_0x0101:
            r0 = move-exception
            r7 = r9
            r9 = r11
            r3 = r14
            r14 = r15
            goto L_0x0121
        L_0x0107:
            r0 = move-exception
            r1 = r20
            r7 = r9
            r9 = r11
            goto L_0x0121
        L_0x010d:
            r0 = move-exception
            r20 = r1
            r7 = r9
            r9 = r11
            goto L_0x0121
        L_0x0113:
            r0 = move-exception
            r5 = r2
            r6 = r3
            r14 = r12
            r2 = r21
            r3 = r22
            r12 = r10
            r10 = r8
            r8 = r4
            r4 = r1
            r1 = r20
        L_0x0121:
            r11 = r0
            throw r0     // Catch:{ all -> 0x0124 }
        L_0x0124:
            r0 = move-exception
            r12 = r0
            r13 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r13)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r14, r11)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r13)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.filterTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object filterTo$$forInline(ReceiveChannel $this$filterTo, Collection destination, Function1 predicate, Continuation continuation) {
        Collection collection = destination;
        ReceiveChannel $this$consume$iv$iv = $this$filterTo;
        Throwable cause$iv$iv = null;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it2 = it.next();
                    try {
                        if (((Boolean) predicate.invoke(it2)).booleanValue()) {
                            collection.add(it2);
                        }
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return collection;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v13, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v18, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v12, resolved type: kotlin.jvm.functions.Function1<? super E, java.lang.Boolean>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v25, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v17, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v17, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v29, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v21, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v13, resolved type: kotlin.jvm.functions.Function1<? super E, java.lang.Boolean>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v32, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v2, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x012b A[Catch:{ all -> 0x01dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, C extends kotlinx.coroutines.channels.SendChannel<? super E>> java.lang.Object filterTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r21, C r22, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r23, kotlin.coroutines.Continuation<? super C> r24) {
        /*
            r1 = r24
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterTo$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterTo$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterTo$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterTo$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$filterTo$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 2
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x00d2
            r8 = 0
            if (r4 == r6) goto L_0x007f
            if (r4 != r5) goto L_0x0077
            r4 = r8
            r9 = r8
            r10 = r8
            r11 = r7
            r12 = r7
            r13 = r8
            r14 = r7
            r15 = r7
            r16 = r7
            java.lang.Object r11 = r2.L$9
            java.lang.Object r12 = r2.L$8
            java.lang.Object r5 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r2.L$6
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r15 = r2.L$5
            r14 = r15
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            java.lang.Object r15 = r2.L$4
            kotlinx.coroutines.channels.ReceiveChannel r15 = (kotlinx.coroutines.channels.ReceiveChannel) r15
            r16 = r0
            java.lang.Object r0 = r2.L$3
            r7 = r0
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r0 = r2.L$2
            r17 = r0
            kotlin.jvm.functions.Function1 r17 = (kotlin.jvm.functions.Function1) r17
            java.lang.Object r0 = r2.L$1
            r18 = r0
            kotlinx.coroutines.channels.SendChannel r18 = (kotlinx.coroutines.channels.SendChannel) r18
            java.lang.Object r0 = r2.L$0
            r19 = r0
            kotlinx.coroutines.channels.ReceiveChannel r19 = (kotlinx.coroutines.channels.ReceiveChannel) r19
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006d }
            r0 = r16
            goto L_0x017b
        L_0x006d:
            r0 = move-exception
            r6 = r2
            r8 = r4
            r2 = r18
            r12 = r19
            r4 = r1
            goto L_0x020b
        L_0x0077:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x007f:
            r16 = r0
            r4 = r8
            r9 = r8
            r13 = r8
            r0 = r7
            r5 = r8
            r6 = r7
            r8 = r7
            java.lang.Object r10 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r10 = (kotlinx.coroutines.channels.ChannelIterator) r10
            java.lang.Object r11 = r2.L$6
            r6 = r11
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r11 = r2.L$5
            r14 = r11
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            java.lang.Object r0 = r2.L$4
            r15 = r0
            kotlinx.coroutines.channels.ReceiveChannel r15 = (kotlinx.coroutines.channels.ReceiveChannel) r15
            java.lang.Object r0 = r2.L$3
            r7 = r0
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r0 = r2.L$2
            r8 = r0
            kotlin.jvm.functions.Function1 r8 = (kotlin.jvm.functions.Function1) r8
            java.lang.Object r0 = r2.L$1
            r11 = r0
            kotlinx.coroutines.channels.SendChannel r11 = (kotlinx.coroutines.channels.SendChannel) r11
            java.lang.Object r0 = r2.L$0
            r12 = r0
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x00c9 }
            r21 = r1
            r1 = r15
            r0 = r16
            r15 = r14
            r14 = r13
            r13 = r9
            r9 = r6
            r6 = r2
            r2 = r11
            r11 = r7
            r7 = r3
            r3 = r8
            r8 = r4
            r4 = r7
            r20 = r10
            r10 = r5
            r5 = r20
            goto L_0x0123
        L_0x00c9:
            r0 = move-exception
            r6 = r2
            r17 = r8
            r2 = r11
            r8 = r4
            r4 = r1
            goto L_0x020b
        L_0x00d2:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r9 = 0
            r4 = r21
            r5 = 0
            r15 = r4
            r13 = 0
            r14 = r7
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            r0 = r15
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r0.iterator()     // Catch:{ all -> 0x0200 }
            r11 = r4
            r8 = r5
            r10 = r7
            r12 = r9
            r5 = r16
            r4 = r1
            r7 = r3
            r9 = r6
            r1 = r21
            r3 = r23
            r6 = r2
            r2 = r22
        L_0x00f7:
            r6.L$0 = r1     // Catch:{ all -> 0x01f5 }
            r6.L$1 = r2     // Catch:{ all -> 0x01f5 }
            r6.L$2 = r3     // Catch:{ all -> 0x01f5 }
            r6.L$3 = r11     // Catch:{ all -> 0x01f5 }
            r6.L$4 = r15     // Catch:{ all -> 0x01f5 }
            r6.L$5 = r14     // Catch:{ all -> 0x01f5 }
            r6.L$6 = r0     // Catch:{ all -> 0x01f5 }
            r6.L$7 = r10     // Catch:{ all -> 0x01f5 }
            r16 = r1
            r1 = 1
            r6.label = r1     // Catch:{ all -> 0x01ec }
            java.lang.Object r1 = r10.hasNext(r6)     // Catch:{ all -> 0x01ec }
            if (r1 != r5) goto L_0x0113
            return r5
        L_0x0113:
            r21 = r4
            r4 = r1
            r1 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r16
            r20 = r9
            r9 = r0
            r0 = r5
            r5 = r10
            r10 = r20
        L_0x0123:
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ all -> 0x01dd }
            boolean r4 = r4.booleanValue()     // Catch:{ all -> 0x01dd }
            if (r4 == 0) goto L_0x01c0
            java.lang.Object r4 = r5.next()     // Catch:{ all -> 0x01dd }
            r22 = r4
            r16 = 0
            r23 = r7
            r7 = r22
            java.lang.Object r17 = r3.invoke(r7)     // Catch:{ all -> 0x01b2 }
            java.lang.Boolean r17 = (java.lang.Boolean) r17     // Catch:{ all -> 0x01b2 }
            boolean r17 = r17.booleanValue()     // Catch:{ all -> 0x01b2 }
            if (r17 == 0) goto L_0x0194
            r6.L$0 = r12     // Catch:{ all -> 0x01b2 }
            r6.L$1 = r2     // Catch:{ all -> 0x01b2 }
            r6.L$2 = r3     // Catch:{ all -> 0x01b2 }
            r6.L$3 = r11     // Catch:{ all -> 0x01b2 }
            r6.L$4 = r1     // Catch:{ all -> 0x01b2 }
            r6.L$5 = r15     // Catch:{ all -> 0x01b2 }
            r6.L$6 = r9     // Catch:{ all -> 0x01b2 }
            r6.L$7 = r5     // Catch:{ all -> 0x01b2 }
            r6.L$8 = r4     // Catch:{ all -> 0x01b2 }
            r6.L$9 = r7     // Catch:{ all -> 0x01b2 }
            r17 = r3
            r3 = 2
            r6.label = r3     // Catch:{ all -> 0x01d2 }
            java.lang.Object r3 = r2.send(r7, r6)     // Catch:{ all -> 0x01d2 }
            if (r3 != r0) goto L_0x0163
            return r0
        L_0x0163:
            r3 = r23
            r18 = r2
            r2 = r6
            r6 = r9
            r19 = r12
            r9 = r13
            r13 = r14
            r14 = r15
            r15 = r1
            r12 = r4
            r4 = r8
            r8 = r10
            r10 = r16
            r1 = r21
            r20 = r11
            r11 = r7
            r7 = r20
        L_0x017b:
            r11 = r5
            r10 = r8
            r16 = r15
            r5 = r1
            r8 = r3
            r15 = r14
            r3 = r17
            r1 = r19
            r14 = r13
            r13 = r9
            r9 = r4
            r4 = r12
            r12 = r7
            r7 = r2
            r2 = r18
            r20 = r6
            r6 = r0
            r0 = r20
            goto L_0x01a3
        L_0x0194:
            r17 = r3
            r16 = r1
            r7 = r6
            r1 = r12
            r6 = r0
            r0 = r9
            r12 = r11
            r11 = r5
            r9 = r8
            r5 = r21
            r8 = r23
        L_0x01a3:
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r15 = r16
            goto L_0x00f7
        L_0x01b2:
            r0 = move-exception
            r17 = r3
            r4 = r21
            r3 = r23
            r7 = r11
            r9 = r13
            r13 = r14
            r14 = r15
            r15 = r1
            goto L_0x020b
        L_0x01c0:
            r17 = r3
            r23 = r7
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x01d2 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r15)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r2
        L_0x01d2:
            r0 = move-exception
            r4 = r21
            r3 = r23
            r7 = r11
            r9 = r13
            r13 = r14
            r14 = r15
            r15 = r1
            goto L_0x020b
        L_0x01dd:
            r0 = move-exception
            r17 = r3
            r23 = r7
            r4 = r21
            r3 = r23
            r7 = r11
            r9 = r13
            r13 = r14
            r14 = r15
            r15 = r1
            goto L_0x020b
        L_0x01ec:
            r0 = move-exception
            r17 = r3
            r3 = r7
            r7 = r11
            r9 = r12
            r12 = r16
            goto L_0x020b
        L_0x01f5:
            r0 = move-exception
            r16 = r1
            r17 = r3
            r3 = r7
            r7 = r11
            r9 = r12
            r12 = r16
            goto L_0x020b
        L_0x0200:
            r0 = move-exception
            r12 = r21
            r17 = r23
            r6 = r2
            r7 = r4
            r8 = r5
            r2 = r22
            r4 = r1
        L_0x020b:
            r1 = r0
            throw r0     // Catch:{ all -> 0x020e }
        L_0x020e:
            r0 = move-exception
            r5 = r0
            r10 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.filterTo(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object filterTo$$forInline(ReceiveChannel $this$filterTo, SendChannel destination, Function1 predicate, Continuation continuation) {
        SendChannel sendChannel = destination;
        Continuation continuation2 = continuation;
        ReceiveChannel $this$consume$iv$iv = $this$filterTo;
        Throwable cause$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation2);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it2 = it.next();
                    try {
                        if (((Boolean) predicate.invoke(it2)).booleanValue()) {
                            InlineMarker.mark(0);
                            sendChannel.send(it2, continuation2);
                            InlineMarker.mark(2);
                            InlineMarker.mark(1);
                        }
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return sendChannel;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    public static /* synthetic */ ReceiveChannel take$default(ReceiveChannel receiveChannel, int i, CoroutineContext coroutineContext, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.take(receiveChannel, i, coroutineContext);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> take(ReceiveChannel<? extends E> $this$take, int n, CoroutineContext context) {
        Intrinsics.checkParameterIsNotNull($this$take, "$this$take");
        Intrinsics.checkParameterIsNotNull(context, "context");
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, ChannelsKt.consumes($this$take), new ChannelsKt__Channels_commonKt$take$1($this$take, n, (Continuation) null), 2, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel takeWhile$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.takeWhile(receiveChannel, coroutineContext, function2);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> takeWhile(ReceiveChannel<? extends E> $this$takeWhile, CoroutineContext context, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        Intrinsics.checkParameterIsNotNull($this$takeWhile, "$this$takeWhile");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, ChannelsKt.consumes($this$takeWhile), new ChannelsKt__Channels_commonKt$takeWhile$1($this$takeWhile, predicate, (Continuation) null), 2, (Object) null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v10, resolved type: java.util.Map} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v1, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00f2 A[Catch:{ all -> 0x0143 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, K, V> java.lang.Object associate(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r23, kotlin.jvm.functions.Function1<? super E, ? extends kotlin.Pair<? extends K, ? extends V>> r24, kotlin.coroutines.Continuation<? super java.util.Map<K, ? extends V>> r25) {
        /*
            r1 = r25
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associate$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associate$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associate$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associate$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associate$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x008b
            if (r4 != r5) goto L_0x0083
            r4 = 0
            r7 = r4
            r8 = r6
            r9 = r6
            r10 = r4
            r11 = r4
            r12 = r6
            r13 = r6
            r14 = r4
            r15 = r6
            java.lang.Object r5 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            r16 = r0
            java.lang.Object r0 = r2.L$7
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            java.lang.Object r9 = r2.L$6
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            java.lang.Object r13 = r2.L$5
            r12 = r13
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r13 = r2.L$4
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            java.lang.Object r15 = r2.L$3
            r6 = r15
            java.util.Map r6 = (java.util.Map) r6
            java.lang.Object r15 = r2.L$2
            r8 = r15
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            r17 = r0
            java.lang.Object r0 = r2.L$0
            r18 = r0
            kotlinx.coroutines.channels.ReceiveChannel r18 = (kotlinx.coroutines.channels.ReceiveChannel) r18
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x007c }
            r23 = r1
            r1 = r12
            r0 = r17
            r17 = r18
            r12 = r9
            r9 = r7
            r7 = r4
            r4 = r16
            r16 = r14
            r14 = r10
            r10 = r5
            r5 = r2
            r2 = r15
            r15 = r11
            r11 = r8
            r8 = r6
            r6 = r3
            goto L_0x00ea
        L_0x007c:
            r0 = move-exception
            r5 = r2
            r2 = r15
            r17 = r18
            goto L_0x017a
        L_0x0083:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x008b:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = 0
            java.util.LinkedHashMap r0 = new java.util.LinkedHashMap
            r0.<init>()
            java.util.Map r0 = (java.util.Map) r0
            r8 = r23
            r5 = r0
            r7 = 0
            r13 = r8
            r11 = 0
            r12 = r13
            r14 = 0
            r9 = r6
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            r0 = r12
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r0.iterator()     // Catch:{ all -> 0x0173 }
            r15 = r12
            r12 = r9
            r9 = r7
            r7 = r4
            r4 = r16
            r16 = r14
            r14 = r11
            r11 = r8
            r8 = r5
            r5 = r2
            r2 = r24
            r21 = r1
            r1 = r23
            r23 = r21
            r22 = r6
            r6 = r3
            r3 = r13
            r13 = r22
        L_0x00c4:
            r5.L$0 = r1     // Catch:{ all -> 0x0162 }
            r5.L$1 = r2     // Catch:{ all -> 0x0162 }
            r5.L$2 = r11     // Catch:{ all -> 0x0162 }
            r5.L$3 = r8     // Catch:{ all -> 0x0162 }
            r5.L$4 = r3     // Catch:{ all -> 0x0162 }
            r5.L$5 = r15     // Catch:{ all -> 0x0162 }
            r5.L$6 = r12     // Catch:{ all -> 0x0162 }
            r5.L$7 = r0     // Catch:{ all -> 0x0162 }
            r5.L$8 = r10     // Catch:{ all -> 0x0162 }
            r17 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x0153 }
            java.lang.Object r1 = r10.hasNext(r5)     // Catch:{ all -> 0x0153 }
            if (r1 != r4) goto L_0x00e2
            return r4
        L_0x00e2:
            r21 = r3
            r3 = r1
            r1 = r15
            r15 = r14
            r14 = r13
            r13 = r21
        L_0x00ea:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0143 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0143 }
            if (r3 == 0) goto L_0x011f
            java.lang.Object r3 = r10.next()     // Catch:{ all -> 0x0143 }
            r24 = r3
            r18 = 0
            r25 = r0
            r0 = r24
            java.lang.Object r19 = r2.invoke(r0)     // Catch:{ all -> 0x0143 }
            kotlin.Pair r19 = (kotlin.Pair) r19     // Catch:{ all -> 0x0143 }
            r24 = r0
            java.lang.Object r0 = r19.getFirst()     // Catch:{ all -> 0x0143 }
            r20 = r2
            java.lang.Object r2 = r19.getSecond()     // Catch:{ all -> 0x0132 }
            r8.put(r0, r2)     // Catch:{ all -> 0x0132 }
            r0 = r25
            r3 = r13
            r13 = r14
            r14 = r15
            r2 = r20
            r15 = r1
            r1 = r17
            goto L_0x00c4
        L_0x011f:
            r25 = r0
            r20 = r2
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0132 }
            r2 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r2)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r2)
            return r8
        L_0x0132:
            r0 = move-exception
            r3 = r6
            r4 = r7
            r6 = r8
            r7 = r9
            r8 = r11
            r9 = r12
            r11 = r15
            r14 = r16
            r2 = r20
            r12 = r1
            r1 = r23
            goto L_0x017a
        L_0x0143:
            r0 = move-exception
            r20 = r2
            r3 = r6
            r4 = r7
            r6 = r8
            r7 = r9
            r8 = r11
            r9 = r12
            r11 = r15
            r14 = r16
            r12 = r1
            r1 = r23
            goto L_0x017a
        L_0x0153:
            r0 = move-exception
            r1 = r23
            r13 = r3
            r3 = r6
            r4 = r7
            r6 = r8
            r7 = r9
            r8 = r11
            r9 = r12
            r11 = r14
            r12 = r15
            r14 = r16
            goto L_0x017a
        L_0x0162:
            r0 = move-exception
            r17 = r1
            r1 = r23
            r13 = r3
            r3 = r6
            r4 = r7
            r6 = r8
            r7 = r9
            r8 = r11
            r9 = r12
            r11 = r14
            r12 = r15
            r14 = r16
            goto L_0x017a
        L_0x0173:
            r0 = move-exception
            r17 = r23
            r6 = r5
            r5 = r2
            r2 = r24
        L_0x017a:
            r9 = r0
            throw r0     // Catch:{ all -> 0x017d }
        L_0x017d:
            r0 = move-exception
            r10 = r0
            r15 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r15)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r9)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r15)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.associate(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object associate$$forInline(ReceiveChannel $this$associate, Function1 transform, Continuation continuation) {
        boolean z = false;
        Map destination$iv = new LinkedHashMap();
        ReceiveChannel $this$consume$iv$iv$iv = $this$associate;
        Throwable cause$iv$iv$iv = null;
        ReceiveChannel $this$consume$iv$iv = $this$consume$iv$iv$iv;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    try {
                        Pair pair = (Pair) transform.invoke(it.next());
                        ReceiveChannel $this$consume$iv$iv2 = $this$consume$iv$iv;
                        boolean z2 = z;
                        destination$iv.put(pair.getFirst(), pair.getSecond());
                        $this$consume$iv$iv = $this$consume$iv$iv2;
                        z = z2;
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv$iv = th;
                        Throwable cause$iv$iv$iv2 = e$iv$iv$iv;
                        throw e$iv$iv$iv;
                    }
                } else {
                    Function1 function1 = transform;
                    ReceiveChannel receiveChannel = $this$consume$iv$iv;
                    boolean z3 = z;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return destination$iv;
                }
            }
        } catch (Throwable th2) {
            e$iv$iv$iv = th2;
            Function1 function12 = transform;
            boolean z4 = z;
            Throwable cause$iv$iv$iv22 = e$iv$iv$iv;
            throw e$iv$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: java.util.Map} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v1, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00f2 A[Catch:{ all -> 0x0120 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, K> java.lang.Object associateBy(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r21, kotlin.jvm.functions.Function1<? super E, ? extends K> r22, kotlin.coroutines.Continuation<? super java.util.Map<K, ? extends E>> r23) {
        /*
            r1 = r23
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateBy$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateBy$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateBy$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateBy$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateBy$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x008b
            if (r4 != r5) goto L_0x0083
            r4 = 0
            r7 = r4
            r8 = r6
            r9 = r6
            r10 = r4
            r11 = r4
            r12 = r6
            r13 = r6
            r14 = r4
            r15 = r6
            java.lang.Object r5 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            r16 = r0
            java.lang.Object r0 = r2.L$7
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            java.lang.Object r9 = r2.L$6
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            java.lang.Object r13 = r2.L$5
            r12 = r13
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r13 = r2.L$4
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            java.lang.Object r15 = r2.L$3
            r6 = r15
            java.util.Map r6 = (java.util.Map) r6
            java.lang.Object r15 = r2.L$2
            r8 = r15
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            r17 = r0
            java.lang.Object r0 = r2.L$0
            r18 = r0
            kotlinx.coroutines.channels.ReceiveChannel r18 = (kotlinx.coroutines.channels.ReceiveChannel) r18
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x007c }
            r21 = r1
            r1 = r12
            r0 = r17
            r17 = r18
            r12 = r9
            r9 = r7
            r7 = r4
            r4 = r16
            r16 = r14
            r14 = r10
            r10 = r5
            r5 = r2
            r2 = r15
            r15 = r11
            r11 = r8
            r8 = r6
            r6 = r3
            goto L_0x00ea
        L_0x007c:
            r0 = move-exception
            r5 = r2
            r2 = r15
            r17 = r18
            goto L_0x0155
        L_0x0083:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x008b:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = 0
            java.util.LinkedHashMap r0 = new java.util.LinkedHashMap
            r0.<init>()
            java.util.Map r0 = (java.util.Map) r0
            r8 = r21
            r5 = r0
            r7 = 0
            r13 = r8
            r11 = 0
            r12 = r13
            r14 = 0
            r9 = r6
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            r0 = r12
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r0.iterator()     // Catch:{ all -> 0x014e }
            r15 = r12
            r12 = r9
            r9 = r7
            r7 = r4
            r4 = r16
            r16 = r14
            r14 = r11
            r11 = r8
            r8 = r5
            r5 = r2
            r2 = r22
            r19 = r1
            r1 = r21
            r21 = r19
            r20 = r6
            r6 = r3
            r3 = r13
            r13 = r20
        L_0x00c4:
            r5.L$0 = r1     // Catch:{ all -> 0x013d }
            r5.L$1 = r2     // Catch:{ all -> 0x013d }
            r5.L$2 = r11     // Catch:{ all -> 0x013d }
            r5.L$3 = r8     // Catch:{ all -> 0x013d }
            r5.L$4 = r3     // Catch:{ all -> 0x013d }
            r5.L$5 = r15     // Catch:{ all -> 0x013d }
            r5.L$6 = r12     // Catch:{ all -> 0x013d }
            r5.L$7 = r0     // Catch:{ all -> 0x013d }
            r5.L$8 = r10     // Catch:{ all -> 0x013d }
            r17 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x012e }
            java.lang.Object r1 = r10.hasNext(r5)     // Catch:{ all -> 0x012e }
            if (r1 != r4) goto L_0x00e2
            return r4
        L_0x00e2:
            r19 = r3
            r3 = r1
            r1 = r15
            r15 = r14
            r14 = r13
            r13 = r19
        L_0x00ea:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0120 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0120 }
            if (r3 == 0) goto L_0x010f
            java.lang.Object r3 = r10.next()     // Catch:{ all -> 0x0120 }
            r22 = r3
            r18 = 0
            r23 = r0
            r0 = r22
            java.lang.Object r3 = r2.invoke(r0)     // Catch:{ all -> 0x0120 }
            r8.put(r3, r0)     // Catch:{ all -> 0x0120 }
            r0 = r23
            r3 = r13
            r13 = r14
            r14 = r15
            r15 = r1
            r1 = r17
            goto L_0x00c4
        L_0x010f:
            r23 = r0
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0120 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r8
        L_0x0120:
            r0 = move-exception
            r3 = r6
            r4 = r7
            r6 = r8
            r7 = r9
            r8 = r11
            r9 = r12
            r11 = r15
            r14 = r16
            r12 = r1
            r1 = r21
            goto L_0x0155
        L_0x012e:
            r0 = move-exception
            r1 = r21
            r13 = r3
            r3 = r6
            r4 = r7
            r6 = r8
            r7 = r9
            r8 = r11
            r9 = r12
            r11 = r14
            r12 = r15
            r14 = r16
            goto L_0x0155
        L_0x013d:
            r0 = move-exception
            r17 = r1
            r1 = r21
            r13 = r3
            r3 = r6
            r4 = r7
            r6 = r8
            r7 = r9
            r8 = r11
            r9 = r12
            r11 = r14
            r12 = r15
            r14 = r16
            goto L_0x0155
        L_0x014e:
            r0 = move-exception
            r17 = r21
            r6 = r5
            r5 = r2
            r2 = r22
        L_0x0155:
            r9 = r0
            throw r0     // Catch:{ all -> 0x0158 }
        L_0x0158:
            r0 = move-exception
            r10 = r0
            r15 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r15)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r9)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r15)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.associateBy(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object associateBy$$forInline(ReceiveChannel $this$associateBy, Function1 keySelector, Continuation continuation) {
        Map destination$iv = new LinkedHashMap();
        ReceiveChannel $this$consume$iv$iv$iv = $this$associateBy;
        Throwable cause$iv$iv$iv = null;
        ReceiveChannel $this$consume$iv$iv = $this$consume$iv$iv$iv;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it$iv = it.next();
                    ReceiveChannel $this$consume$iv$iv2 = $this$consume$iv$iv;
                    try {
                        destination$iv.put(keySelector.invoke(it$iv), it$iv);
                        $this$consume$iv$iv = $this$consume$iv$iv2;
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv$iv = th;
                        Throwable cause$iv$iv$iv2 = e$iv$iv$iv;
                        try {
                            throw e$iv$iv$iv;
                        } catch (Throwable e$iv$iv$iv) {
                            Throwable th2 = e$iv$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = keySelector;
                    ReceiveChannel receiveChannel = $this$consume$iv$iv;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return destination$iv;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv$iv = th3;
            Function1 function12 = keySelector;
            Throwable cause$iv$iv$iv22 = e$iv$iv$iv;
            throw e$iv$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v7, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: java.util.Map} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v4, resolved type: kotlin.jvm.functions.Function1<? super E, ? extends K>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v1, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0103  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0111 A[Catch:{ all -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, K, V> java.lang.Object associateBy(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r22, kotlin.jvm.functions.Function1<? super E, ? extends K> r23, kotlin.jvm.functions.Function1<? super E, ? extends V> r24, kotlin.coroutines.Continuation<? super java.util.Map<K, ? extends V>> r25) {
        /*
            r1 = r25
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateBy$2
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateBy$2 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateBy$2) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateBy$2 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateBy$2
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x00a7
            if (r4 != r5) goto L_0x009f
            r4 = r6
            r7 = 0
            r8 = r7
            r9 = r7
            r10 = r6
            r11 = r6
            r12 = r6
            r13 = r7
            r14 = r7
            r15 = r6
            java.lang.Object r5 = r2.L$9
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            r16 = r0
            java.lang.Object r0 = r2.L$8
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            java.lang.Object r11 = r2.L$7
            r10 = r11
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r11 = r2.L$6
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r15 = r2.L$5
            r12 = r15
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r15 = r2.L$4
            r4 = r15
            java.util.Map r4 = (java.util.Map) r4
            java.lang.Object r15 = r2.L$3
            r6 = r15
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r15 = r2.L$2
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            r17 = r0
            java.lang.Object r0 = r2.L$1
            r18 = r0
            kotlin.jvm.functions.Function1 r18 = (kotlin.jvm.functions.Function1) r18
            java.lang.Object r0 = r2.L$0
            r19 = r0
            kotlinx.coroutines.channels.ReceiveChannel r19 = (kotlinx.coroutines.channels.ReceiveChannel) r19
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x008c }
            r22 = r1
            r1 = r11
            r0 = r17
            r11 = r7
            r17 = r14
            r7 = r3
            r14 = r10
            r10 = r6
            r6 = r2
            r2 = r18
            r18 = r19
            r20 = r8
            r8 = r4
            r4 = r12
            r12 = r20
            r21 = r9
            r9 = r5
            r5 = r16
            r16 = r13
            r13 = r21
            goto L_0x0109
        L_0x008c:
            r0 = move-exception
            r17 = r14
            r14 = r10
            r10 = r6
            r6 = r2
            r2 = r18
            r18 = r19
            r20 = r7
            r7 = r3
            r3 = r15
            r15 = r11
            r11 = r20
            goto L_0x0190
        L_0x009f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x00a7:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r8 = 0
            java.util.LinkedHashMap r0 = new java.util.LinkedHashMap
            r0.<init>()
            r4 = r0
            java.util.Map r4 = (java.util.Map) r4
            r5 = r22
            r7 = 0
            r12 = r5
            r13 = 0
            r11 = r12
            r14 = 0
            r10 = r6
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r0 = r11
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r9 = r0.iterator()     // Catch:{ all -> 0x0181 }
            r15 = r11
            r17 = r14
            r11 = r7
            r14 = r10
            r7 = r3
            r10 = r5
            r5 = r16
            r3 = r24
            r16 = r13
            r13 = r6
            r6 = r2
            r2 = r23
            r20 = r1
            r1 = r22
            r22 = r20
            r21 = r8
            r8 = r4
            r4 = r12
            r12 = r21
        L_0x00e3:
            r6.L$0 = r1     // Catch:{ all -> 0x0173 }
            r6.L$1 = r2     // Catch:{ all -> 0x0173 }
            r6.L$2 = r3     // Catch:{ all -> 0x0173 }
            r6.L$3 = r10     // Catch:{ all -> 0x0173 }
            r6.L$4 = r8     // Catch:{ all -> 0x0173 }
            r6.L$5 = r4     // Catch:{ all -> 0x0173 }
            r6.L$6 = r15     // Catch:{ all -> 0x0173 }
            r6.L$7 = r14     // Catch:{ all -> 0x0173 }
            r6.L$8 = r0     // Catch:{ all -> 0x0173 }
            r6.L$9 = r9     // Catch:{ all -> 0x0173 }
            r18 = r1
            r1 = 1
            r6.label = r1     // Catch:{ all -> 0x0167 }
            java.lang.Object r1 = r9.hasNext(r6)     // Catch:{ all -> 0x0167 }
            if (r1 != r5) goto L_0x0103
            return r5
        L_0x0103:
            r20 = r3
            r3 = r1
            r1 = r15
            r15 = r20
        L_0x0109:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0157 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0157 }
            if (r3 == 0) goto L_0x0134
            java.lang.Object r3 = r9.next()     // Catch:{ all -> 0x0157 }
            r23 = r3
            r19 = 0
            r24 = r0
            r0 = r23
            java.lang.Object r3 = r2.invoke(r0)     // Catch:{ all -> 0x0157 }
            r25 = r2
            java.lang.Object r2 = r15.invoke(r0)     // Catch:{ all -> 0x0147 }
            r8.put(r3, r2)     // Catch:{ all -> 0x0147 }
            r0 = r24
            r2 = r25
            r3 = r15
            r15 = r1
            r1 = r18
            goto L_0x00e3
        L_0x0134:
            r24 = r0
            r25 = r2
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0147 }
            r2 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r2)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r14)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r2)
            return r8
        L_0x0147:
            r0 = move-exception
            r2 = r25
            r3 = r15
            r13 = r16
            r15 = r1
            r1 = r22
            r20 = r12
            r12 = r4
            r4 = r8
            r8 = r20
            goto L_0x0190
        L_0x0157:
            r0 = move-exception
            r25 = r2
            r3 = r15
            r13 = r16
            r15 = r1
            r1 = r22
            r20 = r12
            r12 = r4
            r4 = r8
            r8 = r20
            goto L_0x0190
        L_0x0167:
            r0 = move-exception
            r1 = r22
            r13 = r16
            r20 = r12
            r12 = r4
            r4 = r8
            r8 = r20
            goto L_0x0190
        L_0x0173:
            r0 = move-exception
            r18 = r1
            r1 = r22
            r13 = r16
            r20 = r12
            r12 = r4
            r4 = r8
            r8 = r20
            goto L_0x0190
        L_0x0181:
            r0 = move-exception
            r18 = r22
            r6 = r2
            r15 = r11
            r17 = r14
            r2 = r23
            r11 = r7
            r14 = r10
            r7 = r3
            r10 = r5
            r3 = r24
        L_0x0190:
            r5 = r0
            throw r0     // Catch:{ all -> 0x0193 }
        L_0x0193:
            r0 = move-exception
            r9 = r0
            r14 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r14)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r14)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.associateBy(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object associateBy$$forInline(ReceiveChannel $this$associateBy, Function1 keySelector, Function1 valueTransform, Continuation continuation) {
        boolean z = false;
        Map destination$iv = new LinkedHashMap();
        ReceiveChannel $this$associateByTo$iv = $this$associateBy;
        ReceiveChannel $this$consume$iv$iv$iv = $this$associateByTo$iv;
        Throwable cause$iv$iv$iv = null;
        ReceiveChannel $this$consume$iv$iv = $this$consume$iv$iv$iv;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it$iv = it.next();
                    ReceiveChannel $this$consume$iv$iv2 = $this$consume$iv$iv;
                    try {
                        boolean z2 = z;
                        ReceiveChannel $this$associateByTo$iv2 = $this$associateByTo$iv;
                        destination$iv.put(keySelector.invoke(it$iv), valueTransform.invoke(it$iv));
                        $this$consume$iv$iv = $this$consume$iv$iv2;
                        z = z2;
                        $this$associateByTo$iv = $this$associateByTo$iv2;
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv$iv = th;
                        Throwable cause$iv$iv$iv2 = e$iv$iv$iv;
                        throw e$iv$iv$iv;
                    }
                } else {
                    Function1 function1 = keySelector;
                    ReceiveChannel receiveChannel = $this$consume$iv$iv;
                    boolean z3 = z;
                    ReceiveChannel receiveChannel2 = $this$associateByTo$iv;
                    Function1 function12 = valueTransform;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return destination$iv;
                }
            }
        } catch (Throwable th2) {
            e$iv$iv$iv = th2;
            Function1 function13 = keySelector;
            boolean z4 = z;
            ReceiveChannel receiveChannel3 = $this$associateByTo$iv;
            Function1 function14 = valueTransform;
            Throwable cause$iv$iv$iv22 = e$iv$iv$iv;
            throw e$iv$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00d0 A[Catch:{ all -> 0x0108 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, K, M extends java.util.Map<? super K, ? super E>> java.lang.Object associateByTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r19, M r20, kotlin.jvm.functions.Function1<? super E, ? extends K> r21, kotlin.coroutines.Continuation<? super M> r22) {
        /*
            r1 = r22
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateByTo$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateByTo$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateByTo$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateByTo$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateByTo$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L_0x0080
            if (r4 != r6) goto L_0x0078
            r4 = 0
            r7 = r4
            r8 = r4
            r9 = r4
            r10 = r5
            r11 = r5
            r12 = r5
            java.lang.Object r13 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r13 = (kotlinx.coroutines.channels.ChannelIterator) r13
            java.lang.Object r14 = r2.L$6
            r11 = r14
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r14 = r2.L$5
            r10 = r14
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r14 = r2.L$4
            r12 = r14
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r14 = r2.L$3
            r5 = r14
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r14 = r2.L$2
            kotlin.jvm.functions.Function1 r14 = (kotlin.jvm.functions.Function1) r14
            java.lang.Object r15 = r2.L$1
            java.util.Map r15 = (java.util.Map) r15
            java.lang.Object r6 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006c }
            r16 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r4
            r4 = r1
            r1 = r6
            r6 = r3
            r18 = r5
            r5 = r2
            r2 = r15
            r15 = r12
            r12 = r10
            r10 = r8
            r8 = r18
            goto L_0x00c8
        L_0x006c:
            r0 = move-exception
            r4 = r1
            r1 = r3
            r3 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r5
            r5 = r2
            r2 = r15
            goto L_0x0131
        L_0x0078:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0080:
            kotlin.ResultKt.throwOnFailure(r3)
            r8 = 0
            r4 = r19
            r7 = 0
            r12 = r4
            r9 = 0
            r10 = r5
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r5 = r12
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r11 = r5.iterator()     // Catch:{ all -> 0x0123 }
            r13 = r5
            r15 = r11
            r14 = r12
            r5 = r2
            r11 = r9
            r12 = r10
            r2 = r20
            r9 = r7
            r10 = r8
            r8 = r4
            r7 = r6
            r4 = r1
            r6 = r3
            r1 = r19
            r3 = r21
        L_0x00a5:
            r5.L$0 = r1     // Catch:{ all -> 0x011a }
            r5.L$1 = r2     // Catch:{ all -> 0x011a }
            r5.L$2 = r3     // Catch:{ all -> 0x011a }
            r5.L$3 = r8     // Catch:{ all -> 0x011a }
            r5.L$4 = r14     // Catch:{ all -> 0x011a }
            r5.L$5 = r12     // Catch:{ all -> 0x011a }
            r5.L$6 = r13     // Catch:{ all -> 0x011a }
            r5.L$7 = r15     // Catch:{ all -> 0x011a }
            r19 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x0113 }
            java.lang.Object r1 = r15.hasNext(r5)     // Catch:{ all -> 0x0113 }
            if (r1 != r0) goto L_0x00c1
            return r0
        L_0x00c1:
            r16 = r15
            r15 = r14
            r14 = r3
            r3 = r1
            r1 = r19
        L_0x00c8:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0108 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0108 }
            if (r3 == 0) goto L_0x00ef
            java.lang.Object r3 = r16.next()     // Catch:{ all -> 0x0108 }
            r19 = r3
            r17 = 0
            r20 = r0
            r0 = r19
            r19 = r1
            java.lang.Object r1 = r14.invoke(r0)     // Catch:{ all -> 0x00ff }
            r2.put(r1, r0)     // Catch:{ all -> 0x00ff }
            r1 = r19
            r0 = r20
            r3 = r14
            r14 = r15
            r15 = r16
            goto L_0x00a5
        L_0x00ef:
            r19 = r1
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00ff }
            r1 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            return r2
        L_0x00ff:
            r0 = move-exception
            r1 = r6
            r7 = r9
            r9 = r11
            r3 = r14
            r14 = r15
            r6 = r19
            goto L_0x0131
        L_0x0108:
            r0 = move-exception
            r19 = r1
            r1 = r6
            r7 = r9
            r9 = r11
            r3 = r14
            r14 = r15
            r6 = r19
            goto L_0x0131
        L_0x0113:
            r0 = move-exception
            r1 = r6
            r7 = r9
            r9 = r11
            r6 = r19
            goto L_0x0131
        L_0x011a:
            r0 = move-exception
            r19 = r1
            r1 = r6
            r7 = r9
            r9 = r11
            r6 = r19
            goto L_0x0131
        L_0x0123:
            r0 = move-exception
            r6 = r19
            r5 = r2
            r14 = r12
            r2 = r20
            r12 = r10
            r10 = r8
            r8 = r4
            r4 = r1
            r1 = r3
            r3 = r21
        L_0x0131:
            r11 = r0
            throw r0     // Catch:{ all -> 0x0134 }
        L_0x0134:
            r0 = move-exception
            r12 = r0
            r13 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r13)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r14, r11)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r13)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.associateByTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Map, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object associateByTo$$forInline(ReceiveChannel $this$associateByTo, Map destination, Function1 keySelector, Continuation continuation) {
        Map map = destination;
        ReceiveChannel $this$consume$iv$iv = $this$associateByTo;
        Throwable cause$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it2 = it.next();
                    try {
                        map.put(keySelector.invoke(it2), it2);
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = keySelector;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return map;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = keySelector;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v3, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00dd  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00eb A[Catch:{ all -> 0x0128 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, K, V, M extends java.util.Map<? super K, ? super V>> java.lang.Object associateByTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r21, M r22, kotlin.jvm.functions.Function1<? super E, ? extends K> r23, kotlin.jvm.functions.Function1<? super E, ? extends V> r24, kotlin.coroutines.Continuation<? super M> r25) {
        /*
            r1 = r25
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateByTo$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateByTo$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateByTo$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateByTo$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateByTo$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L_0x0092
            if (r4 != r6) goto L_0x008a
            r4 = r5
            r7 = 0
            r8 = r7
            r9 = r5
            r10 = r7
            r11 = r5
            r12 = r7
            java.lang.Object r13 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r13 = (kotlinx.coroutines.channels.ChannelIterator) r13
            java.lang.Object r14 = r2.L$7
            r11 = r14
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r14 = r2.L$6
            r5 = r14
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            java.lang.Object r14 = r2.L$5
            r9 = r14
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r14 = r2.L$4
            r4 = r14
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r14 = r2.L$3
            kotlin.jvm.functions.Function1 r14 = (kotlin.jvm.functions.Function1) r14
            java.lang.Object r15 = r2.L$2
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            java.lang.Object r6 = r2.L$1
            java.util.Map r6 = (java.util.Map) r6
            r16 = r0
            java.lang.Object r0 = r2.L$0
            r17 = r0
            kotlinx.coroutines.channels.ReceiveChannel r17 = (kotlinx.coroutines.channels.ReceiveChannel) r17
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x007a }
            r0 = r16
            r16 = r17
            r17 = r12
            r12 = r9
            r9 = r5
            r5 = r13
            r13 = r10
            r10 = r7
            r7 = r3
            r19 = r6
            r6 = r2
            r2 = r19
            r20 = r8
            r8 = r4
            r4 = r14
            r14 = r11
            r11 = r20
            goto L_0x00e3
        L_0x007a:
            r0 = move-exception
            r8 = r4
            r13 = r10
            r4 = r14
            r16 = r17
            r10 = r7
            r7 = r3
            r3 = r15
            r19 = r6
            r6 = r2
            r2 = r19
            goto L_0x014f
        L_0x008a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0092:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r7 = 0
            r4 = r21
            r12 = 0
            r9 = r4
            r10 = 0
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            r0 = r9
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r0.iterator()     // Catch:{ all -> 0x0141 }
            r14 = r0
            r11 = r6
            r13 = r10
            r15 = r12
            r0 = r16
            r6 = r2
            r10 = r7
            r12 = r9
            r2 = r22
            r7 = r3
            r9 = r5
            r5 = r8
            r3 = r23
            r8 = r4
            r4 = r24
            r19 = r1
            r1 = r21
            r21 = r19
        L_0x00bf:
            r6.L$0 = r1     // Catch:{ all -> 0x0138 }
            r6.L$1 = r2     // Catch:{ all -> 0x0138 }
            r6.L$2 = r3     // Catch:{ all -> 0x0138 }
            r6.L$3 = r4     // Catch:{ all -> 0x0138 }
            r6.L$4 = r8     // Catch:{ all -> 0x0138 }
            r6.L$5 = r12     // Catch:{ all -> 0x0138 }
            r6.L$6 = r9     // Catch:{ all -> 0x0138 }
            r6.L$7 = r14     // Catch:{ all -> 0x0138 }
            r6.L$8 = r5     // Catch:{ all -> 0x0138 }
            r16 = r1
            r1 = 1
            r6.label = r1     // Catch:{ all -> 0x0131 }
            java.lang.Object r1 = r5.hasNext(r6)     // Catch:{ all -> 0x0131 }
            if (r1 != r0) goto L_0x00dd
            return r0
        L_0x00dd:
            r17 = r15
            r15 = r3
            r3 = r1
            r1 = r21
        L_0x00e3:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0128 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0128 }
            if (r3 == 0) goto L_0x010f
            java.lang.Object r3 = r5.next()     // Catch:{ all -> 0x0128 }
            r21 = r3
            r18 = 0
            r22 = r0
            r0 = r21
            r21 = r1
            java.lang.Object r1 = r15.invoke(r0)     // Catch:{ all -> 0x011f }
            r23 = r3
            java.lang.Object r3 = r4.invoke(r0)     // Catch:{ all -> 0x011f }
            r2.put(r1, r3)     // Catch:{ all -> 0x011f }
            r0 = r22
            r3 = r15
            r1 = r16
            r15 = r17
            goto L_0x00bf
        L_0x010f:
            r21 = r1
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x011f }
            r1 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r9)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            return r2
        L_0x011f:
            r0 = move-exception
            r1 = r21
            r5 = r9
            r9 = r12
            r3 = r15
            r12 = r17
            goto L_0x014f
        L_0x0128:
            r0 = move-exception
            r21 = r1
            r5 = r9
            r9 = r12
            r3 = r15
            r12 = r17
            goto L_0x014f
        L_0x0131:
            r0 = move-exception
            r1 = r21
            r5 = r9
            r9 = r12
            r12 = r15
            goto L_0x014f
        L_0x0138:
            r0 = move-exception
            r16 = r1
            r1 = r21
            r5 = r9
            r9 = r12
            r12 = r15
            goto L_0x014f
        L_0x0141:
            r0 = move-exception
            r16 = r21
            r6 = r2
            r8 = r4
            r13 = r10
            r2 = r22
            r4 = r24
            r10 = r7
            r7 = r3
            r3 = r23
        L_0x014f:
            r5 = r0
            throw r0     // Catch:{ all -> 0x0152 }
        L_0x0152:
            r0 = move-exception
            r11 = r0
            r14 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r14)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r14)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.associateByTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Map, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object associateByTo$$forInline(ReceiveChannel $this$associateByTo, Map destination, Function1 keySelector, Function1 valueTransform, Continuation continuation) {
        Map map = destination;
        boolean z = false;
        ReceiveChannel $this$consume$iv$iv = $this$associateByTo;
        Throwable cause$iv$iv = null;
        ReceiveChannel $this$consume$iv = $this$consume$iv$iv;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it2 = it.next();
                    try {
                        ReceiveChannel $this$consume$iv2 = $this$consume$iv;
                        boolean z2 = z;
                        map.put(keySelector.invoke(it2), valueTransform.invoke(it2));
                        z = z2;
                        $this$consume$iv = $this$consume$iv2;
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        throw e$iv$iv;
                    }
                } else {
                    Function1 function1 = keySelector;
                    ReceiveChannel receiveChannel = $this$consume$iv;
                    boolean z3 = z;
                    Function1 function12 = valueTransform;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return map;
                }
            }
        } catch (Throwable th2) {
            e$iv$iv = th2;
            Function1 function13 = keySelector;
            boolean z4 = z;
            Function1 function14 = valueTransform;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00d1 A[Catch:{ all -> 0x0113 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, K, V, M extends java.util.Map<? super K, ? super V>> java.lang.Object associateTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r20, M r21, kotlin.jvm.functions.Function1<? super E, ? extends kotlin.Pair<? extends K, ? extends V>> r22, kotlin.coroutines.Continuation<? super M> r23) {
        /*
            r1 = r23
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateTo$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateTo$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateTo$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateTo$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$associateTo$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L_0x0081
            if (r4 != r6) goto L_0x0079
            r4 = 0
            r7 = r4
            r8 = r4
            r9 = r4
            r10 = r5
            r11 = r5
            r12 = r5
            java.lang.Object r13 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r13 = (kotlinx.coroutines.channels.ChannelIterator) r13
            java.lang.Object r14 = r2.L$6
            r11 = r14
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r14 = r2.L$5
            r10 = r14
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r14 = r2.L$4
            r12 = r14
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r14 = r2.L$3
            r5 = r14
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r14 = r2.L$2
            kotlin.jvm.functions.Function1 r14 = (kotlin.jvm.functions.Function1) r14
            java.lang.Object r15 = r2.L$1
            java.util.Map r15 = (java.util.Map) r15
            java.lang.Object r6 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006c }
            r16 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r4
            r4 = r1
            r1 = r6
            r6 = r3
            r19 = r5
            r5 = r2
            r2 = r15
            r15 = r12
            r12 = r10
            r10 = r8
            r8 = r19
            goto L_0x00c9
        L_0x006c:
            r0 = move-exception
            r4 = r1
            r19 = r5
            r5 = r2
            r2 = r15
            r15 = r12
            r12 = r10
            r10 = r8
            r8 = r19
            goto L_0x013d
        L_0x0079:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0081:
            kotlin.ResultKt.throwOnFailure(r3)
            r8 = 0
            r4 = r20
            r7 = 0
            r12 = r4
            r9 = 0
            r10 = r5
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r5 = r12
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r11 = r5.iterator()     // Catch:{ all -> 0x0130 }
            r13 = r5
            r15 = r11
            r14 = r12
            r5 = r2
            r11 = r9
            r12 = r10
            r2 = r21
            r9 = r7
            r10 = r8
            r8 = r4
            r7 = r6
            r4 = r1
            r6 = r3
            r1 = r20
            r3 = r22
        L_0x00a6:
            r5.L$0 = r1     // Catch:{ all -> 0x0125 }
            r5.L$1 = r2     // Catch:{ all -> 0x0125 }
            r5.L$2 = r3     // Catch:{ all -> 0x0125 }
            r5.L$3 = r8     // Catch:{ all -> 0x0125 }
            r5.L$4 = r14     // Catch:{ all -> 0x0125 }
            r5.L$5 = r12     // Catch:{ all -> 0x0125 }
            r5.L$6 = r13     // Catch:{ all -> 0x0125 }
            r5.L$7 = r15     // Catch:{ all -> 0x0125 }
            r20 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x011c }
            java.lang.Object r1 = r15.hasNext(r5)     // Catch:{ all -> 0x011c }
            if (r1 != r0) goto L_0x00c2
            return r0
        L_0x00c2:
            r16 = r15
            r15 = r14
            r14 = r3
            r3 = r1
            r1 = r20
        L_0x00c9:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0113 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0113 }
            if (r3 == 0) goto L_0x00fc
            java.lang.Object r3 = r16.next()     // Catch:{ all -> 0x0113 }
            r20 = r3
            r17 = 0
            r21 = r0
            r0 = r20
            java.lang.Object r18 = r14.invoke(r0)     // Catch:{ all -> 0x0113 }
            kotlin.Pair r18 = (kotlin.Pair) r18     // Catch:{ all -> 0x0113 }
            r20 = r0
            java.lang.Object r0 = r18.getFirst()     // Catch:{ all -> 0x0113 }
            r22 = r1
            java.lang.Object r1 = r18.getSecond()     // Catch:{ all -> 0x010c }
            r2.put(r0, r1)     // Catch:{ all -> 0x010c }
            r0 = r21
            r1 = r22
            r3 = r14
            r14 = r15
            r15 = r16
            goto L_0x00a6
        L_0x00fc:
            r22 = r1
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x010c }
            r1 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            return r2
        L_0x010c:
            r0 = move-exception
            r3 = r6
            r7 = r9
            r9 = r11
            r6 = r22
            goto L_0x013d
        L_0x0113:
            r0 = move-exception
            r22 = r1
            r3 = r6
            r7 = r9
            r9 = r11
            r6 = r22
            goto L_0x013d
        L_0x011c:
            r0 = move-exception
            r7 = r9
            r9 = r11
            r15 = r14
            r14 = r3
            r3 = r6
            r6 = r20
            goto L_0x013d
        L_0x0125:
            r0 = move-exception
            r20 = r1
            r7 = r9
            r9 = r11
            r15 = r14
            r14 = r3
            r3 = r6
            r6 = r20
            goto L_0x013d
        L_0x0130:
            r0 = move-exception
            r6 = r20
            r14 = r22
            r5 = r2
            r15 = r12
            r2 = r21
            r12 = r10
            r10 = r8
            r8 = r4
            r4 = r1
        L_0x013d:
            r1 = r0
            throw r0     // Catch:{ all -> 0x0140 }
        L_0x0140:
            r0 = move-exception
            r11 = r0
            r12 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r12)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r12)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.associateTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Map, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object associateTo$$forInline(ReceiveChannel $this$associateTo, Map destination, Function1 transform, Continuation continuation) {
        Map map = destination;
        ReceiveChannel $this$consume$iv$iv = $this$associateTo;
        Throwable cause$iv$iv = null;
        ReceiveChannel $this$consume$iv = $this$consume$iv$iv;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    try {
                        Pair pair = (Pair) transform.invoke(it.next());
                        map.put(pair.getFirst(), pair.getSecond());
                        $this$consume$iv = $this$consume$iv;
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = transform;
                    ReceiveChannel receiveChannel = $this$consume$iv;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return map;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = transform;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v4, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v16, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x010f A[Catch:{ all -> 0x0157 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, C extends kotlinx.coroutines.channels.SendChannel<? super E>> java.lang.Object toChannel(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r21, C r22, kotlin.coroutines.Continuation<? super C> r23) {
        /*
            r1 = r23
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toChannel$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toChannel$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toChannel$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toChannel$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toChannel$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 2
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x00c7
            r8 = 0
            if (r4 == r6) goto L_0x0084
            if (r4 != r5) goto L_0x007c
            r4 = r8
            r9 = r8
            r10 = r7
            r11 = r8
            r12 = r7
            r13 = r7
            r14 = r7
            r15 = r7
            java.lang.Object r10 = r2.L$8
            java.lang.Object r13 = r2.L$7
            java.lang.Object r5 = r2.L$6
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r2.L$5
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r12 = r2.L$4
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            java.lang.Object r15 = r2.L$3
            r7 = r15
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r15 = r2.L$2
            r14 = r15
            kotlinx.coroutines.channels.ReceiveChannel r14 = (kotlinx.coroutines.channels.ReceiveChannel) r14
            java.lang.Object r15 = r2.L$1
            kotlinx.coroutines.channels.SendChannel r15 = (kotlinx.coroutines.channels.SendChannel) r15
            r17 = r0
            java.lang.Object r0 = r2.L$0
            r18 = r0
            kotlinx.coroutines.channels.ReceiveChannel r18 = (kotlinx.coroutines.channels.ReceiveChannel) r18
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0075 }
            r0 = r6
            r16 = r11
            r6 = r3
            r11 = r7
            r3 = r1
            r7 = r4
            r4 = r17
            r1 = r18
            r19 = r5
            r5 = r2
            r2 = r15
            r15 = r13
            r13 = r12
            r12 = r9
            r9 = r19
            goto L_0x0146
        L_0x0075:
            r0 = move-exception
            r5 = r2
            r6 = r3
            r2 = r15
            r3 = r1
            goto L_0x016e
        L_0x007c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0084:
            r17 = r0
            r4 = r8
            r9 = r8
            r0 = r7
            r5 = r8
            r6 = r7
            r8 = r7
            java.lang.Object r10 = r2.L$6
            kotlinx.coroutines.channels.ChannelIterator r10 = (kotlinx.coroutines.channels.ChannelIterator) r10
            java.lang.Object r11 = r2.L$5
            r0 = r11
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            java.lang.Object r11 = r2.L$4
            r12 = r11
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            java.lang.Object r8 = r2.L$3
            r7 = r8
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r8 = r2.L$2
            r14 = r8
            kotlinx.coroutines.channels.ReceiveChannel r14 = (kotlinx.coroutines.channels.ReceiveChannel) r14
            java.lang.Object r6 = r2.L$1
            kotlinx.coroutines.channels.SendChannel r6 = (kotlinx.coroutines.channels.SendChannel) r6
            java.lang.Object r8 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x00be }
            r15 = r3
            r11 = r9
            r9 = r10
            r13 = 1
            r3 = r1
            r10 = r7
            r1 = r8
            r7 = r0
            r8 = r5
            r0 = r17
            r5 = r2
            r2 = r6
            r6 = r15
            goto L_0x0107
        L_0x00be:
            r0 = move-exception
            r5 = r2
            r2 = r6
            r18 = r8
            r6 = r3
            r3 = r1
            goto L_0x016e
        L_0x00c7:
            r17 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r14 = r21
            r9 = 0
            r4 = r14
            r5 = 0
            r12 = r7
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            r0 = r4
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r0.iterator()     // Catch:{ all -> 0x0164 }
            r10 = r4
            r8 = r6
            r11 = r9
            r4 = r17
            r6 = r3
            r9 = r7
            r3 = r1
            r7 = r5
            r1 = r21
            r5 = r2
            r2 = r22
        L_0x00e9:
            r5.L$0 = r1     // Catch:{ all -> 0x015d }
            r5.L$1 = r2     // Catch:{ all -> 0x015d }
            r5.L$2 = r14     // Catch:{ all -> 0x015d }
            r5.L$3 = r10     // Catch:{ all -> 0x015d }
            r5.L$4 = r12     // Catch:{ all -> 0x015d }
            r5.L$5 = r0     // Catch:{ all -> 0x015d }
            r5.L$6 = r9     // Catch:{ all -> 0x015d }
            r13 = 1
            r5.label = r13     // Catch:{ all -> 0x015d }
            java.lang.Object r15 = r9.hasNext(r5)     // Catch:{ all -> 0x015d }
            if (r15 != r4) goto L_0x0101
            return r4
        L_0x0101:
            r19 = r7
            r7 = r0
            r0 = r4
            r4 = r19
        L_0x0107:
            java.lang.Boolean r15 = (java.lang.Boolean) r15     // Catch:{ all -> 0x0157 }
            boolean r15 = r15.booleanValue()     // Catch:{ all -> 0x0157 }
            if (r15 == 0) goto L_0x014a
            java.lang.Object r15 = r9.next()     // Catch:{ all -> 0x0157 }
            r21 = r15
            r16 = 0
            r5.L$0 = r1     // Catch:{ all -> 0x0157 }
            r5.L$1 = r2     // Catch:{ all -> 0x0157 }
            r5.L$2 = r14     // Catch:{ all -> 0x0157 }
            r5.L$3 = r10     // Catch:{ all -> 0x0157 }
            r5.L$4 = r12     // Catch:{ all -> 0x0157 }
            r5.L$5 = r7     // Catch:{ all -> 0x0157 }
            r5.L$6 = r9     // Catch:{ all -> 0x0157 }
            r5.L$7 = r15     // Catch:{ all -> 0x0157 }
            r13 = r21
            r5.L$8 = r13     // Catch:{ all -> 0x0157 }
            r18 = r1
            r1 = 2
            r5.label = r1     // Catch:{ all -> 0x0153 }
            java.lang.Object r1 = r2.send(r13, r5)     // Catch:{ all -> 0x0153 }
            if (r1 != r0) goto L_0x0137
            return r0
        L_0x0137:
            r1 = r18
            r19 = r4
            r4 = r0
            r0 = r7
            r7 = r19
            r20 = r11
            r11 = r10
            r10 = r13
            r13 = r12
            r12 = r20
        L_0x0146:
            r10 = r11
            r11 = r12
            r12 = r13
            goto L_0x00e9
        L_0x014a:
            r18 = r1
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0153 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r12)
            return r2
        L_0x0153:
            r0 = move-exception
            r7 = r10
            r9 = r11
            goto L_0x016e
        L_0x0157:
            r0 = move-exception
            r18 = r1
            r7 = r10
            r9 = r11
            goto L_0x016e
        L_0x015d:
            r0 = move-exception
            r18 = r1
            r4 = r7
            r7 = r10
            r9 = r11
            goto L_0x016e
        L_0x0164:
            r0 = move-exception
            r18 = r21
            r6 = r3
            r7 = r4
            r4 = r5
            r3 = r1
            r5 = r2
            r2 = r22
        L_0x016e:
            r1 = r0
            throw r0     // Catch:{ all -> 0x0171 }
        L_0x0171:
            r0 = move-exception
            r8 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r1)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.toChannel(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00c7 A[Catch:{ all -> 0x00e9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, C extends java.util.Collection<? super E>> java.lang.Object toCollection(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r18, C r19, kotlin.coroutines.Continuation<? super C> r20) {
        /*
            r1 = r20
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toCollection$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toCollection$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toCollection$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toCollection$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toCollection$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x0079
            if (r4 != r5) goto L_0x0071
            r4 = 0
            r7 = r4
            r8 = r4
            r9 = r6
            r10 = r6
            r11 = r6
            java.lang.Object r12 = r2.L$6
            kotlinx.coroutines.channels.ChannelIterator r12 = (kotlinx.coroutines.channels.ChannelIterator) r12
            java.lang.Object r13 = r2.L$5
            r9 = r13
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r13 = r2.L$4
            r11 = r13
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            java.lang.Object r13 = r2.L$3
            r6 = r13
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r13 = r2.L$2
            r10 = r13
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r13 = r2.L$1
            java.util.Collection r13 = (java.util.Collection) r13
            java.lang.Object r14 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r14 = (kotlinx.coroutines.channels.ReceiveChannel) r14
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0065 }
            r15 = r12
            r12 = r9
            r9 = r7
            r7 = r3
            r17 = r4
            r4 = r1
            r1 = r14
            r14 = r11
            r11 = r8
            r8 = r6
            r6 = r2
            r2 = r13
            r13 = r10
            r10 = r17
            goto L_0x00bf
        L_0x0065:
            r0 = move-exception
            r4 = r2
            r12 = r10
            r2 = r13
            r10 = r8
            r8 = r7
            r7 = r6
            r6 = r3
            r3 = r1
            r1 = r14
            goto L_0x0102
        L_0x0071:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0079:
            kotlin.ResultKt.throwOnFailure(r3)
            r10 = r18
            r8 = 0
            r4 = r10
            r7 = 0
            r11 = r6
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            r6 = r4
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r6.iterator()     // Catch:{ all -> 0x00f6 }
            r13 = r11
            r14 = r12
            r11 = r6
            r12 = r10
            r6 = r3
            r10 = r8
            r3 = r1
            r8 = r7
            r1 = r18
            r7 = r4
            r4 = r2
            r2 = r19
        L_0x0099:
            r4.L$0 = r1     // Catch:{ all -> 0x00f3 }
            r4.L$1 = r2     // Catch:{ all -> 0x00f3 }
            r4.L$2 = r12     // Catch:{ all -> 0x00f3 }
            r4.L$3 = r7     // Catch:{ all -> 0x00f3 }
            r4.L$4 = r13     // Catch:{ all -> 0x00f3 }
            r4.L$5 = r11     // Catch:{ all -> 0x00f3 }
            r4.L$6 = r14     // Catch:{ all -> 0x00f3 }
            r4.label = r5     // Catch:{ all -> 0x00f3 }
            java.lang.Object r15 = r14.hasNext(r4)     // Catch:{ all -> 0x00f3 }
            if (r15 != r0) goto L_0x00b0
            return r0
        L_0x00b0:
            r17 = r4
            r4 = r3
            r3 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r17
        L_0x00bf:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x00e9 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x00e9 }
            if (r3 == 0) goto L_0x00e2
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x00e9 }
            r18 = r3
            r16 = 0
            r5 = r18
            r2.add(r5)     // Catch:{ all -> 0x00e9 }
            r3 = r4
            r4 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r5 = 1
            goto L_0x0099
        L_0x00e2:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00e9 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r14)
            return r2
        L_0x00e9:
            r0 = move-exception
            r3 = r4
            r4 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r10 = r11
            r12 = r13
            r11 = r14
            goto L_0x0102
        L_0x00f3:
            r0 = move-exception
            r11 = r13
            goto L_0x0102
        L_0x00f6:
            r0 = move-exception
            r6 = r3
            r12 = r10
            r3 = r1
            r10 = r8
            r1 = r18
            r8 = r7
            r7 = r4
            r4 = r2
            r2 = r19
        L_0x0102:
            r5 = r0
            throw r0     // Catch:{ all -> 0x0105 }
        L_0x0105:
            r0 = move-exception
            r9 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r5)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.toCollection(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final <E> Object toList(ReceiveChannel<? extends E> $this$toList, Continuation<? super List<? extends E>> $completion) {
        return ChannelsKt.toMutableList($this$toList, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <K, V> Object toMap(ReceiveChannel<? extends Pair<? extends K, ? extends V>> $this$toMap, Continuation<? super Map<K, ? extends V>> $completion) {
        return ChannelsKt.toMap($this$toMap, new LinkedHashMap(), $completion);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends kotlin.Pair<? extends K, ? extends V>>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends kotlin.Pair<? extends K, ? extends V>>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends kotlin.Pair<? extends K, ? extends V>>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00c7 A[Catch:{ all -> 0x00f5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <K, V, M extends java.util.Map<? super K, ? super V>> java.lang.Object toMap(kotlinx.coroutines.channels.ReceiveChannel<? extends kotlin.Pair<? extends K, ? extends V>> r19, M r20, kotlin.coroutines.Continuation<? super M> r21) {
        /*
            r1 = r21
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toMap$2
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toMap$2 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toMap$2) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toMap$2 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toMap$2
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x0079
            if (r4 != r5) goto L_0x0071
            r4 = 0
            r7 = r4
            r8 = r4
            r9 = r6
            r10 = r6
            r11 = r6
            java.lang.Object r12 = r2.L$6
            kotlinx.coroutines.channels.ChannelIterator r12 = (kotlinx.coroutines.channels.ChannelIterator) r12
            java.lang.Object r13 = r2.L$5
            r9 = r13
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r13 = r2.L$4
            r11 = r13
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            java.lang.Object r13 = r2.L$3
            r6 = r13
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r13 = r2.L$2
            r10 = r13
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r13 = r2.L$1
            java.util.Map r13 = (java.util.Map) r13
            java.lang.Object r14 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r14 = (kotlinx.coroutines.channels.ReceiveChannel) r14
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0065 }
            r15 = r12
            r12 = r9
            r9 = r7
            r7 = r3
            r18 = r4
            r4 = r1
            r1 = r14
            r14 = r11
            r11 = r8
            r8 = r6
            r6 = r2
            r2 = r13
            r13 = r10
            r10 = r18
            goto L_0x00bf
        L_0x0065:
            r0 = move-exception
            r4 = r2
            r12 = r10
            r2 = r13
            r10 = r8
            r8 = r7
            r7 = r6
            r6 = r3
            r3 = r1
            r1 = r14
            goto L_0x010e
        L_0x0071:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0079:
            kotlin.ResultKt.throwOnFailure(r3)
            r10 = r19
            r8 = 0
            r4 = r10
            r7 = 0
            r11 = r6
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            r6 = r4
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r6.iterator()     // Catch:{ all -> 0x0102 }
            r13 = r11
            r14 = r12
            r11 = r6
            r12 = r10
            r6 = r3
            r10 = r8
            r3 = r1
            r8 = r7
            r1 = r19
            r7 = r4
            r4 = r2
            r2 = r20
        L_0x0099:
            r4.L$0 = r1     // Catch:{ all -> 0x00ff }
            r4.L$1 = r2     // Catch:{ all -> 0x00ff }
            r4.L$2 = r12     // Catch:{ all -> 0x00ff }
            r4.L$3 = r7     // Catch:{ all -> 0x00ff }
            r4.L$4 = r13     // Catch:{ all -> 0x00ff }
            r4.L$5 = r11     // Catch:{ all -> 0x00ff }
            r4.L$6 = r14     // Catch:{ all -> 0x00ff }
            r4.label = r5     // Catch:{ all -> 0x00ff }
            java.lang.Object r15 = r14.hasNext(r4)     // Catch:{ all -> 0x00ff }
            if (r15 != r0) goto L_0x00b0
            return r0
        L_0x00b0:
            r18 = r4
            r4 = r3
            r3 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r18
        L_0x00bf:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x00f5 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x00f5 }
            if (r3 == 0) goto L_0x00ee
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x00f5 }
            r16 = r3
            kotlin.Pair r16 = (kotlin.Pair) r16     // Catch:{ all -> 0x00f5 }
            r17 = 0
            java.lang.Object r5 = r16.getFirst()     // Catch:{ all -> 0x00f5 }
            r19 = r0
            java.lang.Object r0 = r16.getSecond()     // Catch:{ all -> 0x00f5 }
            r2.put(r5, r0)     // Catch:{ all -> 0x00f5 }
            r0 = r19
            r3 = r4
            r4 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r5 = 1
            goto L_0x0099
        L_0x00ee:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00f5 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r14)
            return r2
        L_0x00f5:
            r0 = move-exception
            r3 = r4
            r4 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r10 = r11
            r12 = r13
            r11 = r14
            goto L_0x010e
        L_0x00ff:
            r0 = move-exception
            r11 = r13
            goto L_0x010e
        L_0x0102:
            r0 = move-exception
            r6 = r3
            r12 = r10
            r3 = r1
            r10 = r8
            r1 = r19
            r8 = r7
            r7 = r4
            r4 = r2
            r2 = r20
        L_0x010e:
            r5 = r0
            throw r0     // Catch:{ all -> 0x0111 }
        L_0x0111:
            r0 = move-exception
            r9 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r5)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.toMap(kotlinx.coroutines.channels.ReceiveChannel, java.util.Map, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object toMutableList(ReceiveChannel<? extends E> $this$toMutableList, Continuation<? super List<E>> $completion) {
        return ChannelsKt.toCollection($this$toMutableList, new ArrayList(), $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object toSet(ReceiveChannel<? extends E> $this$toSet, Continuation<? super Set<? extends E>> $completion) {
        return ChannelsKt.toMutableSet($this$toSet, $completion);
    }

    public static /* synthetic */ ReceiveChannel flatMap$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.flatMap(receiveChannel, coroutineContext, function2);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R> ReceiveChannel<R> flatMap(ReceiveChannel<? extends E> $this$flatMap, CoroutineContext context, Function2<? super E, ? super Continuation<? super ReceiveChannel<? extends R>>, ? extends Object> transform) {
        Intrinsics.checkParameterIsNotNull($this$flatMap, "$this$flatMap");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, ChannelsKt.consumes($this$flatMap), new ChannelsKt__Channels_commonKt$flatMap$1($this$flatMap, transform, (Continuation) null), 2, (Object) null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r22v5, resolved type: java.util.ArrayList} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v1, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00f2 A[Catch:{ all -> 0x0167 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, K> java.lang.Object groupBy(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r26, kotlin.jvm.functions.Function1<? super E, ? extends K> r27, kotlin.coroutines.Continuation<? super java.util.Map<K, ? extends java.util.List<? extends E>>> r28) {
        /*
            r1 = r28
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupBy$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupBy$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupBy$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupBy$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupBy$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x008b
            if (r4 != r5) goto L_0x0083
            r4 = 0
            r7 = r4
            r8 = r6
            r9 = r6
            r10 = r4
            r11 = r4
            r12 = r6
            r13 = r6
            r14 = r4
            r15 = r6
            java.lang.Object r5 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            r16 = r0
            java.lang.Object r0 = r2.L$7
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            java.lang.Object r9 = r2.L$6
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            java.lang.Object r13 = r2.L$5
            r12 = r13
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r13 = r2.L$4
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            java.lang.Object r15 = r2.L$3
            r6 = r15
            java.util.Map r6 = (java.util.Map) r6
            java.lang.Object r15 = r2.L$2
            r8 = r15
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            r17 = r0
            java.lang.Object r0 = r2.L$0
            r18 = r0
            kotlinx.coroutines.channels.ReceiveChannel r18 = (kotlinx.coroutines.channels.ReceiveChannel) r18
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x007c }
            r26 = r1
            r1 = r12
            r0 = r17
            r17 = r18
            r12 = r9
            r9 = r7
            r7 = r4
            r4 = r16
            r16 = r14
            r14 = r10
            r10 = r5
            r5 = r2
            r2 = r15
            r15 = r11
            r11 = r8
            r8 = r6
            r6 = r3
            goto L_0x00ea
        L_0x007c:
            r0 = move-exception
            r5 = r2
            r2 = r15
            r17 = r18
            goto L_0x019e
        L_0x0083:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x008b:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = 0
            java.util.LinkedHashMap r0 = new java.util.LinkedHashMap
            r0.<init>()
            java.util.Map r0 = (java.util.Map) r0
            r8 = r26
            r5 = r0
            r7 = 0
            r13 = r8
            r11 = 0
            r12 = r13
            r14 = 0
            r9 = r6
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            r0 = r12
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r0.iterator()     // Catch:{ all -> 0x0197 }
            r15 = r12
            r12 = r9
            r9 = r7
            r7 = r4
            r4 = r16
            r16 = r14
            r14 = r11
            r11 = r8
            r8 = r5
            r5 = r2
            r2 = r27
            r24 = r1
            r1 = r26
            r26 = r24
            r25 = r6
            r6 = r3
            r3 = r13
            r13 = r25
        L_0x00c4:
            r5.L$0 = r1     // Catch:{ all -> 0x0186 }
            r5.L$1 = r2     // Catch:{ all -> 0x0186 }
            r5.L$2 = r11     // Catch:{ all -> 0x0186 }
            r5.L$3 = r8     // Catch:{ all -> 0x0186 }
            r5.L$4 = r3     // Catch:{ all -> 0x0186 }
            r5.L$5 = r15     // Catch:{ all -> 0x0186 }
            r5.L$6 = r12     // Catch:{ all -> 0x0186 }
            r5.L$7 = r0     // Catch:{ all -> 0x0186 }
            r5.L$8 = r10     // Catch:{ all -> 0x0186 }
            r17 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x0177 }
            java.lang.Object r1 = r10.hasNext(r5)     // Catch:{ all -> 0x0177 }
            if (r1 != r4) goto L_0x00e2
            return r4
        L_0x00e2:
            r24 = r3
            r3 = r1
            r1 = r15
            r15 = r14
            r14 = r13
            r13 = r24
        L_0x00ea:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0167 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0167 }
            if (r3 == 0) goto L_0x0143
            java.lang.Object r3 = r10.next()     // Catch:{ all -> 0x0167 }
            r27 = r3
            r18 = 0
            r28 = r0
            r0 = r27
            java.lang.Object r19 = r2.invoke(r0)     // Catch:{ all -> 0x0167 }
            r27 = r19
            r19 = r8
            r20 = 0
            r21 = r2
            r2 = r27
            r27 = r3
            r3 = r19
            java.lang.Object r19 = r3.get(r2)     // Catch:{ all -> 0x0156 }
            if (r19 != 0) goto L_0x0129
            r22 = 0
            java.util.ArrayList r23 = new java.util.ArrayList     // Catch:{ all -> 0x0156 }
            r23.<init>()     // Catch:{ all -> 0x0156 }
            r22 = r23
            r23 = r4
            r4 = r22
            r3.put(r2, r4)     // Catch:{ all -> 0x0156 }
            r22 = r4
            goto L_0x012d
        L_0x0129:
            r23 = r4
            r22 = r19
        L_0x012d:
            java.util.List r22 = (java.util.List) r22     // Catch:{ all -> 0x0156 }
            r3 = r22
            r3.add(r0)     // Catch:{ all -> 0x0156 }
            r0 = r28
            r3 = r13
            r13 = r14
            r14 = r15
            r2 = r21
            r4 = r23
            r15 = r1
            r1 = r17
            goto L_0x00c4
        L_0x0143:
            r28 = r0
            r21 = r2
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0156 }
            r2 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r2)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r2)
            return r8
        L_0x0156:
            r0 = move-exception
            r3 = r6
            r4 = r7
            r6 = r8
            r7 = r9
            r8 = r11
            r9 = r12
            r11 = r15
            r14 = r16
            r2 = r21
            r12 = r1
            r1 = r26
            goto L_0x019e
        L_0x0167:
            r0 = move-exception
            r21 = r2
            r3 = r6
            r4 = r7
            r6 = r8
            r7 = r9
            r8 = r11
            r9 = r12
            r11 = r15
            r14 = r16
            r12 = r1
            r1 = r26
            goto L_0x019e
        L_0x0177:
            r0 = move-exception
            r1 = r26
            r13 = r3
            r3 = r6
            r4 = r7
            r6 = r8
            r7 = r9
            r8 = r11
            r9 = r12
            r11 = r14
            r12 = r15
            r14 = r16
            goto L_0x019e
        L_0x0186:
            r0 = move-exception
            r17 = r1
            r1 = r26
            r13 = r3
            r3 = r6
            r4 = r7
            r6 = r8
            r7 = r9
            r8 = r11
            r9 = r12
            r11 = r14
            r12 = r15
            r14 = r16
            goto L_0x019e
        L_0x0197:
            r0 = move-exception
            r17 = r26
            r6 = r5
            r5 = r2
            r2 = r27
        L_0x019e:
            r9 = r0
            throw r0     // Catch:{ all -> 0x01a1 }
        L_0x01a1:
            r0 = move-exception
            r10 = r0
            r15 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r15)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r9)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r15)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.groupBy(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object groupBy$$forInline(ReceiveChannel $this$groupBy, Function1 keySelector, Continuation continuation) {
        ReceiveChannel $this$groupByTo$iv;
        Object obj;
        boolean z = false;
        ReceiveChannel $this$groupByTo$iv2 = $this$groupBy;
        Map destination$iv = new LinkedHashMap();
        ReceiveChannel $this$consume$iv$iv$iv = $this$groupByTo$iv2;
        Throwable cause$iv$iv$iv = null;
        ReceiveChannel $this$consume$iv$iv = $this$consume$iv$iv$iv;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it$iv = it.next();
                    try {
                        ReceiveChannel $this$consume$iv$iv2 = $this$consume$iv$iv;
                        Object key$iv = keySelector.invoke(it$iv);
                        boolean z2 = z;
                        Map $this$getOrPut$iv$iv = destination$iv;
                        try {
                            Object key$iv2 = $this$getOrPut$iv$iv.get(key$iv);
                            if (key$iv2 == null) {
                                $this$groupByTo$iv = $this$groupByTo$iv2;
                                Object answer$iv$iv = new ArrayList();
                                $this$getOrPut$iv$iv.put(key$iv, answer$iv$iv);
                                obj = answer$iv$iv;
                            } else {
                                $this$groupByTo$iv = $this$groupByTo$iv2;
                                obj = key$iv2;
                            }
                            ((List) obj).add(it$iv);
                            z = z2;
                            $this$consume$iv$iv = $this$consume$iv$iv2;
                            $this$groupByTo$iv2 = $this$groupByTo$iv;
                            i = 1;
                        } catch (Throwable th) {
                            e$iv$iv$iv = th;
                            Throwable cause$iv$iv$iv2 = e$iv$iv$iv;
                            throw e$iv$iv$iv;
                        }
                    } catch (Throwable th2) {
                        e$iv$iv$iv = th2;
                        boolean z3 = z;
                        ReceiveChannel receiveChannel = $this$groupByTo$iv2;
                        Throwable cause$iv$iv$iv22 = e$iv$iv$iv;
                        throw e$iv$iv$iv;
                    }
                } else {
                    Function1 function1 = keySelector;
                    ReceiveChannel receiveChannel2 = $this$consume$iv$iv;
                    boolean z4 = z;
                    ReceiveChannel receiveChannel3 = $this$groupByTo$iv2;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return destination$iv;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv$iv = th3;
            Function1 function12 = keySelector;
            boolean z32 = z;
            ReceiveChannel receiveChannel4 = $this$groupByTo$iv2;
            Throwable cause$iv$iv$iv222 = e$iv$iv$iv;
            throw e$iv$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: java.util.ArrayList} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v7, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v4, resolved type: kotlin.jvm.functions.Function1<? super E, ? extends K>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v1, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00fc  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x010a A[Catch:{ all -> 0x0192 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, K, V> java.lang.Object groupBy(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r27, kotlin.jvm.functions.Function1<? super E, ? extends K> r28, kotlin.jvm.functions.Function1<? super E, ? extends V> r29, kotlin.coroutines.Continuation<? super java.util.Map<K, ? extends java.util.List<? extends V>>> r30) {
        /*
            r1 = r30
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupBy$2
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupBy$2 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupBy$2) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupBy$2 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupBy$2
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x00a0
            if (r4 != r5) goto L_0x0098
            r4 = r6
            r7 = 0
            r8 = r7
            r9 = r7
            r10 = r6
            r11 = r6
            r12 = r6
            r13 = r7
            r14 = r7
            r15 = r6
            java.lang.Object r5 = r2.L$9
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            r16 = r0
            java.lang.Object r0 = r2.L$8
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            java.lang.Object r11 = r2.L$7
            r10 = r11
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r11 = r2.L$6
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r15 = r2.L$5
            r12 = r15
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r15 = r2.L$4
            r4 = r15
            java.util.Map r4 = (java.util.Map) r4
            java.lang.Object r15 = r2.L$3
            r6 = r15
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r15 = r2.L$2
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            r17 = r0
            java.lang.Object r0 = r2.L$1
            r18 = r0
            kotlin.jvm.functions.Function1 r18 = (kotlin.jvm.functions.Function1) r18
            java.lang.Object r0 = r2.L$0
            r19 = r0
            kotlinx.coroutines.channels.ReceiveChannel r19 = (kotlinx.coroutines.channels.ReceiveChannel) r19
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x008c }
            r27 = r1
            r1 = r11
            r0 = r17
            r11 = r7
            r17 = r14
            r7 = r3
            r14 = r10
            r10 = r6
            r6 = r2
            r2 = r18
            r18 = r19
            r25 = r8
            r8 = r4
            r4 = r12
            r12 = r25
            r26 = r9
            r9 = r5
            r5 = r16
            r16 = r13
            r13 = r26
            goto L_0x0102
        L_0x008c:
            r0 = move-exception
            r17 = r14
            r14 = r10
            r10 = r6
            r6 = r2
            r2 = r18
            r18 = r19
            goto L_0x01d7
        L_0x0098:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x00a0:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r8 = 0
            java.util.LinkedHashMap r0 = new java.util.LinkedHashMap
            r0.<init>()
            r4 = r0
            java.util.Map r4 = (java.util.Map) r4
            r5 = r27
            r7 = 0
            r12 = r5
            r13 = 0
            r11 = r12
            r14 = 0
            r10 = r6
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r0 = r11
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r9 = r0.iterator()     // Catch:{ all -> 0x01cb }
            r15 = r11
            r17 = r14
            r11 = r7
            r14 = r10
            r7 = r3
            r10 = r5
            r5 = r16
            r3 = r29
            r16 = r13
            r13 = r6
            r6 = r2
            r2 = r28
            r25 = r1
            r1 = r27
            r27 = r25
            r26 = r8
            r8 = r4
            r4 = r12
            r12 = r26
        L_0x00dc:
            r6.L$0 = r1     // Catch:{ all -> 0x01b6 }
            r6.L$1 = r2     // Catch:{ all -> 0x01b6 }
            r6.L$2 = r3     // Catch:{ all -> 0x01b6 }
            r6.L$3 = r10     // Catch:{ all -> 0x01b6 }
            r6.L$4 = r8     // Catch:{ all -> 0x01b6 }
            r6.L$5 = r4     // Catch:{ all -> 0x01b6 }
            r6.L$6 = r15     // Catch:{ all -> 0x01b6 }
            r6.L$7 = r14     // Catch:{ all -> 0x01b6 }
            r6.L$8 = r0     // Catch:{ all -> 0x01b6 }
            r6.L$9 = r9     // Catch:{ all -> 0x01b6 }
            r18 = r1
            r1 = 1
            r6.label = r1     // Catch:{ all -> 0x01a3 }
            java.lang.Object r1 = r9.hasNext(r6)     // Catch:{ all -> 0x01a3 }
            if (r1 != r5) goto L_0x00fc
            return r5
        L_0x00fc:
            r25 = r3
            r3 = r1
            r1 = r15
            r15 = r25
        L_0x0102:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0192 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0192 }
            if (r3 == 0) goto L_0x016d
            java.lang.Object r3 = r9.next()     // Catch:{ all -> 0x0192 }
            r28 = r3
            r19 = 0
            r29 = r0
            r0 = r28
            java.lang.Object r20 = r2.invoke(r0)     // Catch:{ all -> 0x0192 }
            r28 = r20
            r30 = r8
            r20 = 0
            r21 = r2
            r2 = r28
            r28 = r3
            r3 = r30
            java.lang.Object r22 = r3.get(r2)     // Catch:{ all -> 0x015b }
            if (r22 != 0) goto L_0x0140
            r23 = 0
            java.util.ArrayList r24 = new java.util.ArrayList     // Catch:{ all -> 0x015b }
            r24.<init>()     // Catch:{ all -> 0x015b }
            r30 = r24
            r23 = r4
            r4 = r30
            r3.put(r2, r4)     // Catch:{ all -> 0x0182 }
            goto L_0x0144
        L_0x0140:
            r23 = r4
            r4 = r22
        L_0x0144:
            java.util.List r4 = (java.util.List) r4     // Catch:{ all -> 0x0182 }
            r3 = r4
            java.lang.Object r4 = r15.invoke(r0)     // Catch:{ all -> 0x0182 }
            r3.add(r4)     // Catch:{ all -> 0x0182 }
            r0 = r29
            r3 = r15
            r2 = r21
            r4 = r23
            r15 = r1
            r1 = r18
            goto L_0x00dc
        L_0x015b:
            r0 = move-exception
            r23 = r4
            r3 = r7
            r4 = r8
            r7 = r11
            r8 = r12
            r13 = r16
            r2 = r21
            r12 = r23
            r11 = r1
            r1 = r27
            goto L_0x01d7
        L_0x016d:
            r29 = r0
            r21 = r2
            r23 = r4
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0182 }
            r2 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r2)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r14)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r2)
            return r8
        L_0x0182:
            r0 = move-exception
            r3 = r7
            r4 = r8
            r7 = r11
            r8 = r12
            r13 = r16
            r2 = r21
            r12 = r23
            r11 = r1
            r1 = r27
            goto L_0x01d7
        L_0x0192:
            r0 = move-exception
            r21 = r2
            r23 = r4
            r3 = r7
            r4 = r8
            r7 = r11
            r8 = r12
            r13 = r16
            r12 = r23
            r11 = r1
            r1 = r27
            goto L_0x01d7
        L_0x01a3:
            r0 = move-exception
            r1 = r27
            r13 = r16
            r25 = r15
            r15 = r3
            r3 = r7
            r7 = r11
            r11 = r25
            r26 = r12
            r12 = r4
            r4 = r8
            r8 = r26
            goto L_0x01d7
        L_0x01b6:
            r0 = move-exception
            r18 = r1
            r1 = r27
            r13 = r16
            r25 = r15
            r15 = r3
            r3 = r7
            r7 = r11
            r11 = r25
            r26 = r12
            r12 = r4
            r4 = r8
            r8 = r26
            goto L_0x01d7
        L_0x01cb:
            r0 = move-exception
            r18 = r27
            r15 = r29
            r6 = r2
            r17 = r14
            r2 = r28
            r14 = r10
            r10 = r5
        L_0x01d7:
            r5 = r0
            throw r0     // Catch:{ all -> 0x01da }
        L_0x01da:
            r0 = move-exception
            r9 = r0
            r14 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r14)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r14)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.groupBy(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object groupBy$$forInline(ReceiveChannel $this$groupBy, Function1 keySelector, Function1 valueTransform, Continuation continuation) {
        ReceiveChannel $this$groupByTo$iv;
        Object obj;
        boolean z = false;
        Map destination$iv = new LinkedHashMap();
        ReceiveChannel $this$groupByTo$iv2 = $this$groupBy;
        ReceiveChannel $this$consume$iv$iv$iv = $this$groupByTo$iv2;
        Throwable cause$iv$iv$iv = null;
        ReceiveChannel $this$consume$iv$iv = $this$consume$iv$iv$iv;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it$iv = it.next();
                    try {
                        ReceiveChannel $this$consume$iv$iv2 = $this$consume$iv$iv;
                        Object key$iv = keySelector.invoke(it$iv);
                        boolean z2 = z;
                        Map $this$getOrPut$iv$iv = destination$iv;
                        try {
                            Object key$iv2 = $this$getOrPut$iv$iv.get(key$iv);
                            if (key$iv2 == null) {
                                $this$groupByTo$iv = $this$groupByTo$iv2;
                                Object answer$iv$iv = new ArrayList();
                                try {
                                    $this$getOrPut$iv$iv.put(key$iv, answer$iv$iv);
                                    obj = answer$iv$iv;
                                } catch (Throwable th) {
                                    e$iv$iv$iv = th;
                                    Throwable cause$iv$iv$iv2 = e$iv$iv$iv;
                                    throw e$iv$iv$iv;
                                }
                            } else {
                                $this$groupByTo$iv = $this$groupByTo$iv2;
                                obj = key$iv2;
                            }
                            Object obj2 = key$iv;
                            ((List) obj).add(valueTransform.invoke(it$iv));
                            z = z2;
                            $this$consume$iv$iv = $this$consume$iv$iv2;
                            $this$groupByTo$iv2 = $this$groupByTo$iv;
                            i = 1;
                        } catch (Throwable th2) {
                            e$iv$iv$iv = th2;
                            ReceiveChannel receiveChannel = $this$groupByTo$iv2;
                            Function1 function1 = valueTransform;
                            Throwable cause$iv$iv$iv22 = e$iv$iv$iv;
                            throw e$iv$iv$iv;
                        }
                    } catch (Throwable th3) {
                        e$iv$iv$iv = th3;
                        boolean z3 = z;
                        ReceiveChannel receiveChannel2 = $this$groupByTo$iv2;
                        Function1 function12 = valueTransform;
                        Throwable cause$iv$iv$iv222 = e$iv$iv$iv;
                        throw e$iv$iv$iv;
                    }
                } else {
                    Function1 function13 = keySelector;
                    ReceiveChannel receiveChannel3 = $this$consume$iv$iv;
                    boolean z4 = z;
                    ReceiveChannel receiveChannel4 = $this$groupByTo$iv2;
                    Function1 function14 = valueTransform;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return destination$iv;
                }
            }
        } catch (Throwable th4) {
            e$iv$iv$iv = th4;
            Function1 function15 = keySelector;
            boolean z32 = z;
            ReceiveChannel receiveChannel22 = $this$groupByTo$iv2;
            Function1 function122 = valueTransform;
            Throwable cause$iv$iv$iv2222 = e$iv$iv$iv;
            throw e$iv$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: java.util.ArrayList} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c3  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00d2 A[Catch:{ all -> 0x0142 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, K, M extends java.util.Map<? super K, java.util.List<E>>> java.lang.Object groupByTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r23, M r24, kotlin.jvm.functions.Function1<? super E, ? extends K> r25, kotlin.coroutines.Continuation<? super M> r26) {
        /*
            r1 = r26
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupByTo$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupByTo$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupByTo$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupByTo$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupByTo$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L_0x0082
            if (r4 != r6) goto L_0x007a
            r4 = 0
            r7 = r4
            r8 = r4
            r9 = r4
            r10 = r5
            r11 = r5
            r12 = r5
            java.lang.Object r13 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r13 = (kotlinx.coroutines.channels.ChannelIterator) r13
            java.lang.Object r14 = r2.L$6
            r11 = r14
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r14 = r2.L$5
            r10 = r14
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r14 = r2.L$4
            r12 = r14
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r14 = r2.L$3
            r5 = r14
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r14 = r2.L$2
            kotlin.jvm.functions.Function1 r14 = (kotlin.jvm.functions.Function1) r14
            java.lang.Object r15 = r2.L$1
            java.util.Map r15 = (java.util.Map) r15
            java.lang.Object r6 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006c }
            r16 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r4
            r4 = r1
            r1 = r6
            r6 = r3
            r22 = r5
            r5 = r2
            r2 = r15
            r15 = r12
            r12 = r10
            r10 = r8
            r8 = r22
            goto L_0x00ca
        L_0x006c:
            r0 = move-exception
            r20 = r1
            r22 = r5
            r5 = r2
            r2 = r15
            r15 = r12
            r12 = r10
            r10 = r8
            r8 = r22
            goto L_0x0173
        L_0x007a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0082:
            kotlin.ResultKt.throwOnFailure(r3)
            r8 = 0
            r4 = r23
            r7 = 0
            r12 = r4
            r9 = 0
            r10 = r5
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r5 = r12
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r11 = r5.iterator()     // Catch:{ all -> 0x0165 }
            r13 = r5
            r15 = r11
            r14 = r12
            r5 = r2
            r11 = r9
            r12 = r10
            r2 = r24
            r9 = r7
            r10 = r8
            r8 = r4
            r7 = r6
            r4 = r1
            r6 = r3
            r1 = r23
            r3 = r25
        L_0x00a7:
            r5.L$0 = r1     // Catch:{ all -> 0x0158 }
            r5.L$1 = r2     // Catch:{ all -> 0x0158 }
            r5.L$2 = r3     // Catch:{ all -> 0x0158 }
            r5.L$3 = r8     // Catch:{ all -> 0x0158 }
            r5.L$4 = r14     // Catch:{ all -> 0x0158 }
            r5.L$5 = r12     // Catch:{ all -> 0x0158 }
            r5.L$6 = r13     // Catch:{ all -> 0x0158 }
            r5.L$7 = r15     // Catch:{ all -> 0x0158 }
            r23 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x014d }
            java.lang.Object r1 = r15.hasNext(r5)     // Catch:{ all -> 0x014d }
            if (r1 != r0) goto L_0x00c3
            return r0
        L_0x00c3:
            r16 = r15
            r15 = r14
            r14 = r3
            r3 = r1
            r1 = r23
        L_0x00ca:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0142 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0142 }
            if (r3 == 0) goto L_0x0129
            java.lang.Object r3 = r16.next()     // Catch:{ all -> 0x0142 }
            r23 = r3
            r17 = 0
            r24 = r0
            r0 = r23
            java.lang.Object r18 = r14.invoke(r0)     // Catch:{ all -> 0x0142 }
            r23 = r18
            r25 = r2
            r18 = 0
            r26 = r1
            r1 = r23
            r23 = r3
            r3 = r25
            java.lang.Object r19 = r3.get(r1)     // Catch:{ all -> 0x011f }
            if (r19 != 0) goto L_0x0108
            r20 = 0
            java.util.ArrayList r21 = new java.util.ArrayList     // Catch:{ all -> 0x011f }
            r21.<init>()     // Catch:{ all -> 0x011f }
            r25 = r21
            r20 = r4
            r4 = r25
            r3.put(r1, r4)     // Catch:{ all -> 0x013b }
            goto L_0x010c
        L_0x0108:
            r20 = r4
            r4 = r19
        L_0x010c:
            java.util.List r4 = (java.util.List) r4     // Catch:{ all -> 0x013b }
            r3 = r4
            r3.add(r0)     // Catch:{ all -> 0x013b }
            r0 = r24
            r1 = r26
            r3 = r14
            r14 = r15
            r15 = r16
            r4 = r20
            goto L_0x00a7
        L_0x011f:
            r0 = move-exception
            r20 = r4
            r3 = r6
            r7 = r9
            r9 = r11
            r6 = r26
            goto L_0x0173
        L_0x0129:
            r26 = r1
            r20 = r4
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x013b }
            r1 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            return r2
        L_0x013b:
            r0 = move-exception
            r3 = r6
            r7 = r9
            r9 = r11
            r6 = r26
            goto L_0x0173
        L_0x0142:
            r0 = move-exception
            r26 = r1
            r20 = r4
            r3 = r6
            r7 = r9
            r9 = r11
            r6 = r26
            goto L_0x0173
        L_0x014d:
            r0 = move-exception
            r20 = r4
            r7 = r9
            r9 = r11
            r15 = r14
            r14 = r3
            r3 = r6
            r6 = r23
            goto L_0x0173
        L_0x0158:
            r0 = move-exception
            r23 = r1
            r20 = r4
            r7 = r9
            r9 = r11
            r15 = r14
            r14 = r3
            r3 = r6
            r6 = r23
            goto L_0x0173
        L_0x0165:
            r0 = move-exception
            r6 = r23
            r14 = r25
            r20 = r1
            r5 = r2
            r15 = r12
            r2 = r24
            r12 = r10
            r10 = r8
            r8 = r4
        L_0x0173:
            r1 = r0
            throw r0     // Catch:{ all -> 0x0176 }
        L_0x0176:
            r0 = move-exception
            r4 = r0
            r11 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r11)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r11)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.groupByTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Map, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object groupByTo$$forInline(ReceiveChannel $this$groupByTo, Map destination, Function1 keySelector, Continuation continuation) {
        ReceiveChannel $this$consume$iv;
        Object obj;
        ReceiveChannel $this$consume$iv$iv = $this$groupByTo;
        Throwable cause$iv$iv = null;
        ReceiveChannel $this$consume$iv2 = $this$consume$iv$iv;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv2.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it2 = it.next();
                    try {
                        Object key = keySelector.invoke(it2);
                        Map $this$getOrPut$iv = destination;
                        Object value$iv = $this$getOrPut$iv.get(key);
                        if (value$iv == null) {
                            $this$consume$iv = $this$consume$iv2;
                            Object answer$iv = new ArrayList();
                            $this$getOrPut$iv.put(key, answer$iv);
                            obj = answer$iv;
                        } else {
                            $this$consume$iv = $this$consume$iv2;
                            obj = value$iv;
                        }
                        ((List) obj).add(it2);
                        $this$consume$iv2 = $this$consume$iv;
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = keySelector;
                    ReceiveChannel receiveChannel = $this$consume$iv2;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return destination;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = keySelector;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: java.util.ArrayList} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v3, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v16, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00d9  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00e7 A[Catch:{ all -> 0x0154 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, K, V, M extends java.util.Map<? super K, java.util.List<V>>> java.lang.Object groupByTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r25, M r26, kotlin.jvm.functions.Function1<? super E, ? extends K> r27, kotlin.jvm.functions.Function1<? super E, ? extends V> r28, kotlin.coroutines.Continuation<? super M> r29) {
        /*
            r1 = r29
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupByTo$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupByTo$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupByTo$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupByTo$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$groupByTo$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L_0x008e
            if (r4 != r6) goto L_0x0086
            r4 = r5
            r7 = 0
            r8 = r7
            r9 = r5
            r10 = r7
            r11 = r5
            r12 = r7
            java.lang.Object r13 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r13 = (kotlinx.coroutines.channels.ChannelIterator) r13
            java.lang.Object r14 = r2.L$7
            r11 = r14
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r14 = r2.L$6
            r5 = r14
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            java.lang.Object r14 = r2.L$5
            r9 = r14
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r14 = r2.L$4
            r4 = r14
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r14 = r2.L$3
            kotlin.jvm.functions.Function1 r14 = (kotlin.jvm.functions.Function1) r14
            java.lang.Object r15 = r2.L$2
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            java.lang.Object r6 = r2.L$1
            java.util.Map r6 = (java.util.Map) r6
            r16 = r0
            java.lang.Object r0 = r2.L$0
            r17 = r0
            kotlinx.coroutines.channels.ReceiveChannel r17 = (kotlinx.coroutines.channels.ReceiveChannel) r17
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x007a }
            r0 = r16
            r16 = r17
            r17 = r12
            r12 = r9
            r9 = r5
            r5 = r13
            r13 = r10
            r10 = r7
            r7 = r3
            r23 = r6
            r6 = r2
            r2 = r23
            r24 = r8
            r8 = r4
            r4 = r14
            r14 = r11
            r11 = r24
            goto L_0x00df
        L_0x007a:
            r0 = move-exception
            r8 = r4
            r4 = r14
            r16 = r17
            r23 = r6
            r6 = r2
            r2 = r23
            goto L_0x0182
        L_0x0086:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x008e:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r7 = 0
            r4 = r25
            r12 = 0
            r9 = r4
            r10 = 0
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            r0 = r9
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r0.iterator()     // Catch:{ all -> 0x0177 }
            r14 = r0
            r11 = r6
            r13 = r10
            r15 = r12
            r0 = r16
            r6 = r2
            r10 = r7
            r12 = r9
            r2 = r26
            r7 = r3
            r9 = r5
            r5 = r8
            r3 = r27
            r8 = r4
            r4 = r28
            r23 = r1
            r1 = r25
            r25 = r23
        L_0x00bb:
            r6.L$0 = r1     // Catch:{ all -> 0x016a }
            r6.L$1 = r2     // Catch:{ all -> 0x016a }
            r6.L$2 = r3     // Catch:{ all -> 0x016a }
            r6.L$3 = r4     // Catch:{ all -> 0x016a }
            r6.L$4 = r8     // Catch:{ all -> 0x016a }
            r6.L$5 = r12     // Catch:{ all -> 0x016a }
            r6.L$6 = r9     // Catch:{ all -> 0x016a }
            r6.L$7 = r14     // Catch:{ all -> 0x016a }
            r6.L$8 = r5     // Catch:{ all -> 0x016a }
            r16 = r1
            r1 = 1
            r6.label = r1     // Catch:{ all -> 0x015f }
            java.lang.Object r1 = r5.hasNext(r6)     // Catch:{ all -> 0x015f }
            if (r1 != r0) goto L_0x00d9
            return r0
        L_0x00d9:
            r17 = r15
            r15 = r3
            r3 = r1
            r1 = r25
        L_0x00df:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0154 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0154 }
            if (r3 == 0) goto L_0x0139
            java.lang.Object r3 = r5.next()     // Catch:{ all -> 0x0154 }
            r25 = r3
            r18 = 0
            r26 = r0
            r0 = r25
            java.lang.Object r19 = r15.invoke(r0)     // Catch:{ all -> 0x0154 }
            r25 = r19
            r27 = r2
            r19 = 0
            r28 = r1
            r1 = r25
            r25 = r3
            r3 = r27
            java.lang.Object r20 = r3.get(r1)     // Catch:{ all -> 0x0149 }
            if (r20 != 0) goto L_0x011d
            r21 = 0
            java.util.ArrayList r22 = new java.util.ArrayList     // Catch:{ all -> 0x0149 }
            r22.<init>()     // Catch:{ all -> 0x0149 }
            r27 = r22
            r29 = r5
            r5 = r27
            r3.put(r1, r5)     // Catch:{ all -> 0x0149 }
            goto L_0x0121
        L_0x011d:
            r29 = r5
            r5 = r20
        L_0x0121:
            java.util.List r5 = (java.util.List) r5     // Catch:{ all -> 0x0149 }
            r3 = r5
            java.lang.Object r5 = r4.invoke(r0)     // Catch:{ all -> 0x0149 }
            r3.add(r5)     // Catch:{ all -> 0x0149 }
            r0 = r26
            r25 = r28
            r5 = r29
            r3 = r15
            r1 = r16
            r15 = r17
            goto L_0x00bb
        L_0x0139:
            r28 = r1
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0149 }
            r1 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r9)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            return r2
        L_0x0149:
            r0 = move-exception
            r1 = r28
            r3 = r7
            r5 = r9
            r7 = r10
            r9 = r12
            r10 = r13
            r12 = r17
            goto L_0x0182
        L_0x0154:
            r0 = move-exception
            r28 = r1
            r3 = r7
            r5 = r9
            r7 = r10
            r9 = r12
            r10 = r13
            r12 = r17
            goto L_0x0182
        L_0x015f:
            r0 = move-exception
            r1 = r25
            r5 = r9
            r9 = r12
            r12 = r15
            r15 = r3
            r3 = r7
            r7 = r10
            r10 = r13
            goto L_0x0182
        L_0x016a:
            r0 = move-exception
            r16 = r1
            r1 = r25
            r5 = r9
            r9 = r12
            r12 = r15
            r15 = r3
            r3 = r7
            r7 = r10
            r10 = r13
            goto L_0x0182
        L_0x0177:
            r0 = move-exception
            r16 = r25
            r15 = r27
            r6 = r2
            r8 = r4
            r2 = r26
            r4 = r28
        L_0x0182:
            r5 = r0
            throw r0     // Catch:{ all -> 0x0185 }
        L_0x0185:
            r0 = move-exception
            r11 = r0
            r13 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r13)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r13)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.groupByTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Map, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object groupByTo$$forInline(ReceiveChannel $this$groupByTo, Map destination, Function1 keySelector, Function1 valueTransform, Continuation continuation) {
        ReceiveChannel $this$consume$iv;
        Object obj;
        boolean z = false;
        ReceiveChannel $this$consume$iv$iv = $this$groupByTo;
        Throwable cause$iv$iv = null;
        ReceiveChannel $this$consume$iv2 = $this$consume$iv$iv;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv2.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it2 = it.next();
                    try {
                        Object key = keySelector.invoke(it2);
                        Map $this$getOrPut$iv = destination;
                        Object value$iv = $this$getOrPut$iv.get(key);
                        if (value$iv == null) {
                            try {
                                $this$consume$iv = $this$consume$iv2;
                                Object answer$iv = new ArrayList();
                                $this$getOrPut$iv.put(key, answer$iv);
                                obj = answer$iv;
                            } catch (Throwable th) {
                                e$iv$iv = th;
                                Function1 function1 = valueTransform;
                                boolean z2 = z;
                                Throwable cause$iv$iv2 = e$iv$iv;
                                try {
                                    throw e$iv$iv;
                                } catch (Throwable e$iv$iv) {
                                    Throwable th2 = e$iv$iv;
                                    InlineMarker.finallyStart(1);
                                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                                    InlineMarker.finallyEnd(1);
                                    throw th2;
                                }
                            }
                        } else {
                            $this$consume$iv = $this$consume$iv2;
                            obj = value$iv;
                        }
                        boolean z3 = z;
                        ((List) obj).add(valueTransform.invoke(it2));
                        z = z3;
                        $this$consume$iv2 = $this$consume$iv;
                        i = 1;
                    } catch (Throwable th3) {
                        e$iv$iv = th3;
                        Throwable cause$iv$iv22 = e$iv$iv;
                        throw e$iv$iv;
                    }
                } else {
                    Function1 function12 = keySelector;
                    Function1 function13 = valueTransform;
                    ReceiveChannel receiveChannel = $this$consume$iv2;
                    boolean z4 = z;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return destination;
                }
            }
        } catch (Throwable th4) {
            e$iv$iv = th4;
            Function1 function14 = keySelector;
            Function1 function15 = valueTransform;
            boolean z5 = z;
            Throwable cause$iv$iv222 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    public static /* synthetic */ ReceiveChannel map$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.map(receiveChannel, coroutineContext, function2);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R> ReceiveChannel<R> map(ReceiveChannel<? extends E> $this$map, CoroutineContext context, Function2<? super E, ? super Continuation<? super R>, ? extends Object> transform) {
        Intrinsics.checkParameterIsNotNull($this$map, "$this$map");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, ChannelsKt.consumes($this$map), new ChannelsKt__Channels_commonKt$map$1($this$map, transform, (Continuation) null), 2, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel mapIndexed$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.mapIndexed(receiveChannel, coroutineContext, function3);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R> ReceiveChannel<R> mapIndexed(ReceiveChannel<? extends E> $this$mapIndexed, CoroutineContext context, Function3<? super Integer, ? super E, ? super Continuation<? super R>, ? extends Object> transform) {
        Intrinsics.checkParameterIsNotNull($this$mapIndexed, "$this$mapIndexed");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, ChannelsKt.consumes($this$mapIndexed), new ChannelsKt__Channels_commonKt$mapIndexed$1($this$mapIndexed, transform, (Continuation) null), 2, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel mapIndexedNotNull$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.mapIndexedNotNull(receiveChannel, coroutineContext, function3);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R> ReceiveChannel<R> mapIndexedNotNull(ReceiveChannel<? extends E> $this$mapIndexedNotNull, CoroutineContext context, Function3<? super Integer, ? super E, ? super Continuation<? super R>, ? extends Object> transform) {
        Intrinsics.checkParameterIsNotNull($this$mapIndexedNotNull, "$this$mapIndexedNotNull");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        return ChannelsKt.filterNotNull(ChannelsKt.mapIndexed($this$mapIndexedNotNull, context, transform));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v11, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v2, resolved type: kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super E, ? extends R>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v1, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0108  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x012b A[Catch:{ all -> 0x01c2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, R, C extends java.util.Collection<? super R>> java.lang.Object mapIndexedNotNullTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r29, C r30, kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super E, ? extends R> r31, kotlin.coroutines.Continuation<? super C> r32) {
        /*
            r1 = r32
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedNotNullTo$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedNotNullTo$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedNotNullTo$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedNotNullTo$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedNotNullTo$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            r7 = 0
            if (r4 == 0) goto L_0x009e
            if (r4 != r5) goto L_0x0096
            r4 = r6
            r8 = r6
            r9 = r6
            r10 = r7
            r11 = r7
            r12 = r7
            r13 = r6
            r14 = r6
            r15 = r7
            r16 = r7
            r17 = r7
            java.lang.Object r5 = r2.L$9
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r7 = r2.L$8
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r11 = r2.L$7
            r10 = r11
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r11 = r2.L$6
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r15 = r2.L$5
            r12 = r15
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r15 = r2.L$4
            kotlin.jvm.internal.Ref$IntRef r15 = (kotlin.jvm.internal.Ref.IntRef) r15
            java.lang.Object r6 = r2.L$3
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            r17 = r0
            java.lang.Object r0 = r2.L$2
            r19 = r0
            kotlin.jvm.functions.Function2 r19 = (kotlin.jvm.functions.Function2) r19
            java.lang.Object r0 = r2.L$1
            r20 = r0
            java.util.Collection r20 = (java.util.Collection) r20
            java.lang.Object r0 = r2.L$0
            r21 = r0
            kotlinx.coroutines.channels.ReceiveChannel r21 = (kotlinx.coroutines.channels.ReceiveChannel) r21
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0089 }
            r29 = r1
            r1 = r10
            r0 = r17
            r18 = r21
            r16 = 0
            r10 = r6
            r17 = r9
            r6 = r19
            r9 = r5
            r19 = r13
            r5 = r3
            r13 = r7
            r7 = r2
            r2 = r20
            r20 = r14
            r14 = r8
            r8 = 0
            goto L_0x0123
        L_0x0089:
            r0 = move-exception
            r7 = r2
            r5 = r15
            r2 = r20
            r18 = r21
            r15 = r10
            r10 = r6
            r6 = r19
            goto L_0x020a
        L_0x0096:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x009e:
            r17 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r8 = 0
            r6 = r29
            r4 = 0
            kotlin.jvm.internal.Ref$IntRef r0 = new kotlin.jvm.internal.Ref$IntRef
            r0.<init>()
            r5 = 0
            r0.element = r5
            r15 = r0
            r12 = r6
            r13 = 0
            r11 = r12
            r14 = 0
            r0 = 0
            r10 = r0
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r7 = r11
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r16 = r7.iterator()     // Catch:{ all -> 0x01ff }
            r25 = r1
            r1 = r29
            r29 = r25
            r26 = r2
            r2 = r30
            r30 = r3
            r3 = r31
            r31 = r4
            r4 = r11
            r11 = r7
            r7 = r15
            r15 = r10
            r10 = r5
            r5 = r26
            r27 = r12
            r12 = r0
            r0 = r17
            r17 = r14
            r14 = r9
            r9 = r6
            r6 = r27
            r28 = r13
            r13 = r8
            r8 = r16
            r16 = r28
        L_0x00e8:
            r5.L$0 = r1     // Catch:{ all -> 0x01e7 }
            r5.L$1 = r2     // Catch:{ all -> 0x01e7 }
            r5.L$2 = r3     // Catch:{ all -> 0x01e7 }
            r5.L$3 = r9     // Catch:{ all -> 0x01e7 }
            r5.L$4 = r7     // Catch:{ all -> 0x01e7 }
            r5.L$5 = r6     // Catch:{ all -> 0x01e7 }
            r5.L$6 = r4     // Catch:{ all -> 0x01e7 }
            r5.L$7 = r15     // Catch:{ all -> 0x01e7 }
            r5.L$8 = r11     // Catch:{ all -> 0x01e7 }
            r5.L$9 = r8     // Catch:{ all -> 0x01e7 }
            r18 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x01d1 }
            java.lang.Object r1 = r8.hasNext(r5)     // Catch:{ all -> 0x01d1 }
            if (r1 != r0) goto L_0x0108
            return r0
        L_0x0108:
            r19 = r16
            r20 = r17
            r16 = r10
            r17 = r14
            r10 = r9
            r14 = r13
            r9 = r8
            r13 = r11
            r8 = r12
            r11 = r4
            r12 = r6
            r4 = r31
            r6 = r3
            r3 = r30
            r25 = r5
            r5 = r1
            r1 = r15
            r15 = r7
            r7 = r25
        L_0x0123:
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch:{ all -> 0x01c2 }
            boolean r5 = r5.booleanValue()     // Catch:{ all -> 0x01c2 }
            if (r5 == 0) goto L_0x019f
            java.lang.Object r5 = r9.next()     // Catch:{ all -> 0x01c2 }
            r30 = r5
            r21 = 0
            r31 = r0
            kotlin.collections.IndexedValue r0 = new kotlin.collections.IndexedValue     // Catch:{ all -> 0x01c2 }
            r32 = r3
            int r3 = r15.element     // Catch:{ all -> 0x018f }
            r22 = r4
            int r4 = r3 + 1
            r15.element = r4     // Catch:{ all -> 0x01b2 }
            r4 = r30
            r0.<init>(r3, r4)     // Catch:{ all -> 0x01b2 }
            r3 = r8
            r8 = r16
            r16 = 0
            int r23 = r0.component1()     // Catch:{ all -> 0x01b2 }
            r8 = r23
            java.lang.Object r23 = r0.component2()     // Catch:{ all -> 0x01b2 }
            r3 = r23
            r30 = r0
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r8)     // Catch:{ all -> 0x01b2 }
            java.lang.Object r0 = r6.invoke(r0, r3)     // Catch:{ all -> 0x01b2 }
            if (r0 == 0) goto L_0x016c
            r23 = 0
            boolean r24 = r2.add(r0)     // Catch:{ all -> 0x01b2 }
            kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r24)     // Catch:{ all -> 0x01b2 }
        L_0x016c:
            r0 = r31
            r30 = r32
            r5 = r7
            r4 = r11
            r11 = r13
            r13 = r14
            r7 = r15
            r14 = r17
            r16 = r19
            r17 = r20
            r31 = r22
            r15 = r1
            r1 = r18
            r25 = r12
            r12 = r3
            r3 = r6
            r6 = r25
            r26 = r10
            r10 = r8
            r8 = r9
            r9 = r26
            goto L_0x00e8
        L_0x018f:
            r0 = move-exception
            r22 = r4
            r3 = r32
            r8 = r14
            r5 = r15
            r13 = r19
            r14 = r20
            r15 = r1
            r1 = r29
            goto L_0x020a
        L_0x019f:
            r32 = r3
            r22 = r4
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x01b2 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r2
        L_0x01b2:
            r0 = move-exception
            r3 = r32
            r8 = r14
            r5 = r15
            r13 = r19
            r14 = r20
            r4 = r22
            r15 = r1
            r1 = r29
            goto L_0x020a
        L_0x01c2:
            r0 = move-exception
            r32 = r3
            r22 = r4
            r8 = r14
            r5 = r15
            r13 = r19
            r14 = r20
            r15 = r1
            r1 = r29
            goto L_0x020a
        L_0x01d1:
            r0 = move-exception
            r1 = r29
            r11 = r4
            r12 = r6
            r10 = r9
            r8 = r13
            r13 = r16
            r14 = r17
            r4 = r31
            r6 = r3
            r3 = r30
            r25 = r7
            r7 = r5
            r5 = r25
            goto L_0x020a
        L_0x01e7:
            r0 = move-exception
            r18 = r1
            r1 = r29
            r11 = r4
            r12 = r6
            r10 = r9
            r8 = r13
            r13 = r16
            r14 = r17
            r4 = r31
            r6 = r3
            r3 = r30
            r25 = r7
            r7 = r5
            r5 = r25
            goto L_0x020a
        L_0x01ff:
            r0 = move-exception
            r18 = r29
            r7 = r2
            r5 = r15
            r2 = r30
            r15 = r10
            r10 = r6
            r6 = r31
        L_0x020a:
            r9 = r0
            throw r0     // Catch:{ all -> 0x020d }
        L_0x020d:
            r0 = move-exception
            r15 = r0
            r16 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r16)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r9)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r16)
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.mapIndexedNotNullTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapIndexedNotNullTo$$forInline(ReceiveChannel $this$mapIndexedNotNullTo, Collection destination, Function2 transform, Continuation continuation) {
        IndexedValue $dstr$index$element;
        int index;
        ReceiveChannel $this$consumeEachIndexed$iv;
        Collection collection = destination;
        int $i$f$mapIndexedNotNullTo = 0;
        ReceiveChannel $this$consumeEachIndexed$iv2 = $this$mapIndexedNotNullTo;
        int index$iv = 0;
        ReceiveChannel $this$consume$iv$iv$iv = $this$consumeEachIndexed$iv2;
        Throwable cause$iv$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv$iv.iterator();
            int i2 = 0;
            int i3 = 0;
            while (true) {
                InlineMarker.mark(i2);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    int index$iv2 = index$iv + 1;
                    int $i$f$mapIndexedNotNullTo2 = $i$f$mapIndexedNotNullTo;
                    Object it$iv = it.next();
                    try {
                        $dstr$index$element = new IndexedValue(index$iv, it$iv);
                        int i4 = i3;
                        index = $dstr$index$element.component1();
                        Object obj = it$iv;
                        $this$consumeEachIndexed$iv = $this$consumeEachIndexed$iv2;
                    } catch (Throwable th) {
                        e$iv$iv$iv = th;
                        ReceiveChannel receiveChannel = $this$consumeEachIndexed$iv2;
                        Function2 function2 = transform;
                        int i5 = index$iv2;
                        Throwable cause$iv$iv$iv2 = e$iv$iv$iv;
                        try {
                            throw e$iv$iv$iv;
                        } catch (Throwable e$iv$iv$iv) {
                            Throwable th2 = e$iv$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                    try {
                        Object it2 = transform.invoke(Integer.valueOf(index), $dstr$index$element.component2());
                        if (it2 != null) {
                            collection.add(it2);
                        }
                        i3 = index;
                        index$iv = index$iv2;
                        $i$f$mapIndexedNotNullTo = $i$f$mapIndexedNotNullTo2;
                        $this$consumeEachIndexed$iv2 = $this$consumeEachIndexed$iv;
                        i = 1;
                        i2 = 0;
                    } catch (Throwable th3) {
                        e$iv$iv$iv = th3;
                        int i6 = index$iv2;
                        Throwable cause$iv$iv$iv22 = e$iv$iv$iv;
                        throw e$iv$iv$iv;
                    }
                } else {
                    ReceiveChannel receiveChannel2 = $this$consumeEachIndexed$iv2;
                    Function2 function22 = transform;
                    try {
                        Unit unit = Unit.INSTANCE;
                        InlineMarker.finallyStart(1);
                        ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv);
                        InlineMarker.finallyEnd(1);
                        return collection;
                    } catch (Throwable th4) {
                        e$iv$iv$iv = th4;
                        Throwable cause$iv$iv$iv222 = e$iv$iv$iv;
                        throw e$iv$iv$iv;
                    }
                }
            }
        } catch (Throwable th5) {
            e$iv$iv$iv = th5;
            ReceiveChannel receiveChannel3 = $this$consumeEachIndexed$iv2;
            Function2 function23 = transform;
            Throwable cause$iv$iv$iv2222 = e$iv$iv$iv;
            throw e$iv$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v16, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v12, resolved type: kotlin.jvm.internal.Ref$IntRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v38, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v21, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v40, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v18, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v41, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v4, resolved type: kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super E, ? extends R>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v43, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r29v4, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0177  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0197 A[Catch:{ all -> 0x02c9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002a  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, R, C extends kotlinx.coroutines.channels.SendChannel<? super R>> java.lang.Object mapIndexedNotNullTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r32, C r33, kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super E, ? extends R> r34, kotlin.coroutines.Continuation<? super C> r35) {
        /*
            r1 = r35
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedNotNullTo$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedNotNullTo$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedNotNullTo$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedNotNullTo$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedNotNullTo$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 2
            r6 = 1
            r7 = 0
            r8 = 0
            if (r4 == 0) goto L_0x0113
            if (r4 == r6) goto L_0x00b5
            if (r4 != r5) goto L_0x00ad
            r4 = r7
            r9 = r8
            r10 = r8
            r11 = r8
            r12 = r7
            r13 = r7
            r14 = r8
            r15 = r8
            r16 = r8
            r17 = r7
            r18 = r8
            r19 = r7
            r20 = r7
            r21 = r8
            r22 = r7
            r23 = r7
            r24 = r8
            r25 = r8
            java.lang.Object r5 = r2.L$14
            java.lang.Object r6 = r2.L$13
            r16 = r0
            int r0 = r2.I$0
            r19 = r0
            java.lang.Object r0 = r2.L$12
            kotlin.collections.IndexedValue r0 = (kotlin.collections.IndexedValue) r0
            java.lang.Object r15 = r2.L$11
            java.lang.Object r14 = r2.L$10
            r18 = r0
            java.lang.Object r0 = r2.L$9
            kotlinx.coroutines.channels.ChannelIterator r0 = (kotlinx.coroutines.channels.ChannelIterator) r0
            r26 = r0
            java.lang.Object r0 = r2.L$8
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            java.lang.Object r10 = r2.L$7
            r9 = r10
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            java.lang.Object r10 = r2.L$6
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            r24 = r0
            java.lang.Object r0 = r2.L$5
            r11 = r0
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r0 = r2.L$4
            r25 = r0
            kotlin.jvm.internal.Ref$IntRef r25 = (kotlin.jvm.internal.Ref.IntRef) r25
            java.lang.Object r0 = r2.L$3
            r8 = r0
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r0 = r2.L$2
            r27 = r0
            kotlin.jvm.functions.Function2 r27 = (kotlin.jvm.functions.Function2) r27
            java.lang.Object r0 = r2.L$1
            r28 = r0
            kotlinx.coroutines.channels.SendChannel r28 = (kotlinx.coroutines.channels.SendChannel) r28
            java.lang.Object r0 = r2.L$0
            r29 = r0
            kotlinx.coroutines.channels.ReceiveChannel r29 = (kotlinx.coroutines.channels.ReceiveChannel) r29
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x00a2 }
            r0 = r19
            r19 = r17
            r17 = r15
            r15 = r26
            goto L_0x022e
        L_0x00a2:
            r0 = move-exception
            r19 = r7
            r13 = r27
            r14 = r28
            r7 = r29
            goto L_0x0317
        L_0x00ad:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x00b5:
            r16 = r0
            r22 = r7
            r4 = r7
            r0 = r7
            r5 = r8
            r6 = r8
            r9 = r8
            r12 = r7
            r10 = r7
            r11 = r8
            r13 = r8
            r14 = r8
            java.lang.Object r15 = r2.L$9
            kotlinx.coroutines.channels.ChannelIterator r15 = (kotlinx.coroutines.channels.ChannelIterator) r15
            java.lang.Object r8 = r2.L$8
            r6 = r8
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r8 = r2.L$7
            r5 = r8
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            java.lang.Object r8 = r2.L$6
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r11 = r2.L$5
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r9 = r2.L$4
            r25 = r9
            kotlin.jvm.internal.Ref$IntRef r25 = (kotlin.jvm.internal.Ref.IntRef) r25
            java.lang.Object r9 = r2.L$3
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r13 = r2.L$2
            kotlin.jvm.functions.Function2 r13 = (kotlin.jvm.functions.Function2) r13
            java.lang.Object r14 = r2.L$1
            kotlinx.coroutines.channels.SendChannel r14 = (kotlinx.coroutines.channels.SendChannel) r14
            java.lang.Object r7 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x010b }
            r23 = r0
            r32 = r1
            r33 = r3
            r0 = r6
            r1 = r11
            r18 = 0
            r6 = r4
            r11 = r8
            r8 = r9
            r9 = r10
            r4 = r25
            r10 = r5
            r5 = r2
            r2 = r16
            r16 = r12
            r12 = 0
            goto L_0x018f
        L_0x010b:
            r0 = move-exception
            r19 = r10
            r10 = r8
            r8 = r9
            r9 = r5
            goto L_0x0317
        L_0x0113:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = 0
            r8 = r32
            r22 = 0
            kotlin.jvm.internal.Ref$IntRef r0 = new kotlin.jvm.internal.Ref$IntRef
            r0.<init>()
            r5 = 0
            r0.element = r5
            r25 = r0
            r11 = r8
            r12 = 0
            r10 = r11
            r7 = 0
            r0 = 0
            r9 = r0
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            r6 = r10
            r13 = 0
            kotlinx.coroutines.channels.ChannelIterator r14 = r6.iterator()     // Catch:{ all -> 0x030e }
            r15 = r11
            r17 = r13
            r11 = r8
            r13 = r9
            r8 = r6
            r9 = r7
            r6 = r25
            r7 = r4
            r4 = r14
            r14 = r10
            r10 = r5
            r5 = r2
            r2 = r33
            r33 = r3
            r3 = r34
            r30 = r1
            r1 = r32
            r32 = r30
            r31 = r12
            r12 = r0
            r0 = r16
            r16 = r31
        L_0x0157:
            r5.L$0 = r1     // Catch:{ all -> 0x02f6 }
            r5.L$1 = r2     // Catch:{ all -> 0x02f6 }
            r5.L$2 = r3     // Catch:{ all -> 0x02f6 }
            r5.L$3 = r11     // Catch:{ all -> 0x02f6 }
            r5.L$4 = r6     // Catch:{ all -> 0x02f6 }
            r5.L$5 = r15     // Catch:{ all -> 0x02f6 }
            r5.L$6 = r14     // Catch:{ all -> 0x02f6 }
            r5.L$7 = r13     // Catch:{ all -> 0x02f6 }
            r5.L$8 = r8     // Catch:{ all -> 0x02f6 }
            r5.L$9 = r4     // Catch:{ all -> 0x02f6 }
            r18 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x02e0 }
            java.lang.Object r1 = r4.hasNext(r5)     // Catch:{ all -> 0x02e0 }
            if (r1 != r0) goto L_0x0177
            return r0
        L_0x0177:
            r23 = r17
            r30 = r2
            r2 = r0
            r0 = r8
            r8 = r11
            r11 = r14
            r14 = r30
            r31 = r3
            r3 = r1
            r1 = r15
            r15 = r4
            r4 = r6
            r6 = r7
            r7 = r18
            r18 = r10
            r10 = r13
            r13 = r31
        L_0x018f:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x02c9 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x02c9 }
            if (r3 == 0) goto L_0x02a0
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x02c9 }
            r34 = r3
            r17 = 0
            r35 = r6
            kotlin.collections.IndexedValue r6 = new kotlin.collections.IndexedValue     // Catch:{ all -> 0x028a }
            r19 = r9
            int r9 = r4.element     // Catch:{ all -> 0x0276 }
            r20 = r2
            int r2 = r9 + 1
            r4.element = r2     // Catch:{ all -> 0x0276 }
            r2 = r34
            r6.<init>(r9, r2)     // Catch:{ all -> 0x0276 }
            r9 = r12
            r12 = r18
            r18 = 0
            int r24 = r6.component1()     // Catch:{ all -> 0x0276 }
            r12 = r24
            java.lang.Object r24 = r6.component2()     // Catch:{ all -> 0x0276 }
            r9 = r24
            r34 = r6
            java.lang.Integer r6 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r12)     // Catch:{ all -> 0x0276 }
            java.lang.Object r6 = r13.invoke(r6, r9)     // Catch:{ all -> 0x0276 }
            if (r6 == 0) goto L_0x0244
            r24 = 0
            r5.L$0 = r7     // Catch:{ all -> 0x0276 }
            r5.L$1 = r14     // Catch:{ all -> 0x0276 }
            r5.L$2 = r13     // Catch:{ all -> 0x0276 }
            r5.L$3 = r8     // Catch:{ all -> 0x0276 }
            r5.L$4 = r4     // Catch:{ all -> 0x0276 }
            r5.L$5 = r1     // Catch:{ all -> 0x0276 }
            r5.L$6 = r11     // Catch:{ all -> 0x0276 }
            r5.L$7 = r10     // Catch:{ all -> 0x0276 }
            r5.L$8 = r0     // Catch:{ all -> 0x0276 }
            r5.L$9 = r15     // Catch:{ all -> 0x0276 }
            r5.L$10 = r3     // Catch:{ all -> 0x0276 }
            r5.L$11 = r2     // Catch:{ all -> 0x0276 }
            r25 = r0
            r0 = r34
            r5.L$12 = r0     // Catch:{ all -> 0x0276 }
            r5.I$0 = r12     // Catch:{ all -> 0x0276 }
            r5.L$13 = r9     // Catch:{ all -> 0x0276 }
            r5.L$14 = r6     // Catch:{ all -> 0x0276 }
            r34 = r0
            r0 = 2
            r5.label = r0     // Catch:{ all -> 0x0276 }
            java.lang.Object r0 = r14.send(r6, r5)     // Catch:{ all -> 0x0276 }
            r26 = r1
            r1 = r20
            if (r0 != r1) goto L_0x0205
            return r1
        L_0x0205:
            r29 = r7
            r0 = r12
            r27 = r13
            r28 = r14
            r12 = r16
            r20 = r18
            r7 = r19
            r13 = r24
            r24 = r25
            r18 = r34
            r16 = r1
            r14 = r3
            r25 = r4
            r19 = r17
            r1 = r32
            r3 = r33
            r4 = r35
            r17 = r2
            r2 = r5
            r5 = r6
            r6 = r9
            r9 = r10
            r10 = r11
            r11 = r26
        L_0x022e:
            r5 = r2
            r13 = r9
            r26 = r11
            r2 = r28
            r9 = r7
            r11 = r8
            r8 = r24
            r7 = r4
            r4 = r15
            r15 = r10
            r10 = r0
            r0 = r16
            r16 = r12
            r12 = r6
            r6 = r25
            goto L_0x0266
        L_0x0244:
            r25 = r0
            r26 = r1
            r1 = r20
            r0 = r1
            r6 = r4
            r29 = r7
            r27 = r13
            r4 = r15
            r1 = r32
            r7 = r35
            r13 = r10
            r15 = r11
            r10 = r12
            r11 = r8
            r12 = r9
            r9 = r19
            r8 = r25
            r19 = r17
            r17 = r2
            r2 = r14
            r14 = r3
            r3 = r33
        L_0x0266:
            r32 = r1
            r33 = r3
            r14 = r15
            r17 = r23
            r15 = r26
            r3 = r27
            r1 = r29
            goto L_0x0157
        L_0x0276:
            r0 = move-exception
            r26 = r1
            r1 = r32
            r3 = r33
            r25 = r4
            r2 = r5
            r9 = r10
            r10 = r11
            r12 = r16
            r11 = r26
            r4 = r35
            goto L_0x0317
        L_0x028a:
            r0 = move-exception
            r26 = r1
            r19 = r9
            r1 = r32
            r3 = r33
            r25 = r4
            r2 = r5
            r9 = r10
            r10 = r11
            r12 = r16
            r11 = r26
            r4 = r35
            goto L_0x0317
        L_0x02a0:
            r25 = r0
            r26 = r1
            r35 = r6
            r19 = r9
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x02b7 }
            r1 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r10)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            return r14
        L_0x02b7:
            r0 = move-exception
            r1 = r32
            r3 = r33
            r25 = r4
            r2 = r5
            r9 = r10
            r10 = r11
            r12 = r16
            r11 = r26
            r4 = r35
            goto L_0x0317
        L_0x02c9:
            r0 = move-exception
            r26 = r1
            r35 = r6
            r19 = r9
            r1 = r32
            r3 = r33
            r25 = r4
            r2 = r5
            r9 = r10
            r10 = r11
            r12 = r16
            r11 = r26
            r4 = r35
            goto L_0x0317
        L_0x02e0:
            r0 = move-exception
            r1 = r32
            r25 = r6
            r4 = r7
            r19 = r9
            r8 = r11
            r9 = r13
            r10 = r14
            r11 = r15
            r12 = r16
            r7 = r18
            r14 = r2
            r13 = r3
            r2 = r5
            r3 = r33
            goto L_0x0317
        L_0x02f6:
            r0 = move-exception
            r18 = r1
            r1 = r32
            r25 = r6
            r4 = r7
            r19 = r9
            r8 = r11
            r9 = r13
            r10 = r14
            r11 = r15
            r12 = r16
            r7 = r18
            r14 = r2
            r13 = r3
            r2 = r5
            r3 = r33
            goto L_0x0317
        L_0x030e:
            r0 = move-exception
            r14 = r33
            r13 = r34
            r19 = r7
            r7 = r32
        L_0x0317:
            r5 = r0
            throw r0     // Catch:{ all -> 0x031a }
        L_0x031a:
            r0 = move-exception
            r6 = r0
            r9 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r9)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r9)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.mapIndexedNotNullTo(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapIndexedNotNullTo$$forInline(ReceiveChannel $this$mapIndexedNotNullTo, SendChannel destination, Function2 transform, Continuation continuation) {
        IndexedValue $dstr$index$element;
        int index;
        ReceiveChannel $this$consumeEachIndexed$iv;
        SendChannel sendChannel = destination;
        Continuation continuation2 = continuation;
        int $i$f$mapIndexedNotNullTo = 0;
        ReceiveChannel $this$consumeEachIndexed$iv2 = $this$mapIndexedNotNullTo;
        int index$iv = 0;
        ReceiveChannel $this$consume$iv$iv$iv = $this$consumeEachIndexed$iv2;
        Throwable cause$iv$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv$iv.iterator();
            int i2 = 0;
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation2);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    int index$iv2 = index$iv + 1;
                    int $i$f$mapIndexedNotNullTo2 = $i$f$mapIndexedNotNullTo;
                    Object it$iv = it.next();
                    try {
                        $dstr$index$element = new IndexedValue(index$iv, it$iv);
                        int i3 = i2;
                        index = $dstr$index$element.component1();
                        Object obj = it$iv;
                        $this$consumeEachIndexed$iv = $this$consumeEachIndexed$iv2;
                    } catch (Throwable th) {
                        e$iv$iv$iv = th;
                        ReceiveChannel receiveChannel = $this$consumeEachIndexed$iv2;
                        Function2 function2 = transform;
                        int i4 = index$iv2;
                        Throwable cause$iv$iv$iv2 = e$iv$iv$iv;
                        try {
                            throw e$iv$iv$iv;
                        } catch (Throwable e$iv$iv$iv) {
                            Throwable th2 = e$iv$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                    try {
                        Object it2 = transform.invoke(Integer.valueOf(index), $dstr$index$element.component2());
                        if (it2 != null) {
                            InlineMarker.mark(0);
                            sendChannel.send(it2, continuation2);
                            InlineMarker.mark(2);
                            InlineMarker.mark(1);
                        }
                        i2 = index;
                        index$iv = index$iv2;
                        $i$f$mapIndexedNotNullTo = $i$f$mapIndexedNotNullTo2;
                        $this$consumeEachIndexed$iv2 = $this$consumeEachIndexed$iv;
                        i = 1;
                    } catch (Throwable th3) {
                        e$iv$iv$iv = th3;
                        int i5 = index$iv2;
                        Throwable cause$iv$iv$iv22 = e$iv$iv$iv;
                        throw e$iv$iv$iv;
                    }
                } else {
                    ReceiveChannel receiveChannel2 = $this$consumeEachIndexed$iv2;
                    Function2 function22 = transform;
                    try {
                        Unit unit = Unit.INSTANCE;
                        InlineMarker.finallyStart(1);
                        ChannelsKt.cancelConsumed($this$consume$iv$iv$iv, cause$iv$iv$iv);
                        InlineMarker.finallyEnd(1);
                        return sendChannel;
                    } catch (Throwable th4) {
                        e$iv$iv$iv = th4;
                        Throwable cause$iv$iv$iv222 = e$iv$iv$iv;
                        throw e$iv$iv$iv;
                    }
                }
            }
        } catch (Throwable th5) {
            e$iv$iv$iv = th5;
            ReceiveChannel receiveChannel3 = $this$consumeEachIndexed$iv2;
            Function2 function23 = transform;
            Throwable cause$iv$iv$iv2222 = e$iv$iv$iv;
            throw e$iv$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: kotlin.jvm.internal.Ref$IntRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v1, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00e5  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00f3 A[Catch:{ all -> 0x0128 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, R, C extends java.util.Collection<? super R>> java.lang.Object mapIndexedTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r20, C r21, kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super E, ? extends R> r22, kotlin.coroutines.Continuation<? super C> r23) {
        /*
            r1 = r23
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedTo$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedTo$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedTo$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedTo$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedTo$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 0
            r7 = 1
            if (r4 == 0) goto L_0x008f
            if (r4 != r7) goto L_0x0087
            r4 = r6
            r8 = r5
            r9 = r5
            r10 = r6
            r11 = r5
            r12 = r6
            r13 = r6
            java.lang.Object r14 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r14 = (kotlinx.coroutines.channels.ChannelIterator) r14
            java.lang.Object r15 = r2.L$7
            r12 = r15
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r15 = r2.L$6
            r13 = r15
            java.lang.Throwable r13 = (java.lang.Throwable) r13
            java.lang.Object r15 = r2.L$5
            r10 = r15
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r15 = r2.L$4
            r4 = r15
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r15 = r2.L$3
            r6 = r15
            kotlin.jvm.internal.Ref$IntRef r6 = (kotlin.jvm.internal.Ref.IntRef) r6
            java.lang.Object r15 = r2.L$2
            kotlin.jvm.functions.Function2 r15 = (kotlin.jvm.functions.Function2) r15
            java.lang.Object r7 = r2.L$1
            java.util.Collection r7 = (java.util.Collection) r7
            r16 = r0
            java.lang.Object r0 = r2.L$0
            r17 = r0
            kotlinx.coroutines.channels.ReceiveChannel r17 = (kotlinx.coroutines.channels.ReceiveChannel) r17
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x007a }
            r20 = r1
            r1 = r13
            r0 = r16
            r16 = r17
            r13 = r11
            r11 = r9
            r9 = r6
            r6 = r3
            r18 = r5
            r5 = r2
            r2 = r7
            r7 = r4
            r4 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r18
            goto L_0x00eb
        L_0x007a:
            r0 = move-exception
            r9 = r6
            r12 = r10
            r16 = r17
            r6 = r3
            r10 = r8
            r3 = r15
            r8 = r5
            r5 = r2
            r2 = r7
            goto L_0x014e
        L_0x0087:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x008f:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r8 = 0
            kotlin.jvm.internal.Ref$IntRef r0 = new kotlin.jvm.internal.Ref$IntRef
            r0.<init>()
            r0.element = r5
            r4 = r0
            r5 = r20
            r7 = 0
            r10 = r5
            r11 = 0
            r13 = r6
            java.lang.Throwable r13 = (java.lang.Throwable) r13
            r0 = r10
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r9 = r0.iterator()     // Catch:{ all -> 0x0140 }
            r14 = r0
            r12 = r10
            r15 = r13
            r0 = r16
            r10 = r8
            r13 = r11
            r11 = r6
            r8 = r7
            r6 = r3
            r7 = r5
            r3 = r22
            r5 = r2
            r2 = r21
            r18 = r1
            r1 = r20
            r20 = r18
            r19 = r9
            r9 = r4
            r4 = r19
        L_0x00c7:
            r5.L$0 = r1     // Catch:{ all -> 0x0137 }
            r5.L$1 = r2     // Catch:{ all -> 0x0137 }
            r5.L$2 = r3     // Catch:{ all -> 0x0137 }
            r5.L$3 = r9     // Catch:{ all -> 0x0137 }
            r5.L$4 = r7     // Catch:{ all -> 0x0137 }
            r5.L$5 = r12     // Catch:{ all -> 0x0137 }
            r5.L$6 = r15     // Catch:{ all -> 0x0137 }
            r5.L$7 = r14     // Catch:{ all -> 0x0137 }
            r5.L$8 = r4     // Catch:{ all -> 0x0137 }
            r16 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x0130 }
            java.lang.Object r1 = r4.hasNext(r5)     // Catch:{ all -> 0x0130 }
            if (r1 != r0) goto L_0x00e5
            return r0
        L_0x00e5:
            r18 = r3
            r3 = r1
            r1 = r15
            r15 = r18
        L_0x00eb:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0128 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0128 }
            if (r3 == 0) goto L_0x011a
            java.lang.Object r3 = r4.next()     // Catch:{ all -> 0x0128 }
            r21 = r3
            r17 = 0
            r22 = r0
            int r0 = r9.element     // Catch:{ all -> 0x0128 }
            r23 = r3
            int r3 = r0 + 1
            r9.element = r3     // Catch:{ all -> 0x0128 }
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)     // Catch:{ all -> 0x0128 }
            r3 = r21
            java.lang.Object r0 = r15.invoke(r0, r3)     // Catch:{ all -> 0x0128 }
            r2.add(r0)     // Catch:{ all -> 0x0128 }
            r0 = r22
            r3 = r15
            r15 = r1
            r1 = r16
            goto L_0x00c7
        L_0x011a:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0128 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r2
        L_0x0128:
            r0 = move-exception
            r4 = r7
            r11 = r13
            r3 = r15
            r13 = r1
            r1 = r20
            goto L_0x014e
        L_0x0130:
            r0 = move-exception
            r1 = r20
            r4 = r7
            r11 = r13
            r13 = r15
            goto L_0x014e
        L_0x0137:
            r0 = move-exception
            r16 = r1
            r1 = r20
            r4 = r7
            r11 = r13
            r13 = r15
            goto L_0x014e
        L_0x0140:
            r0 = move-exception
            r16 = r20
            r6 = r3
            r9 = r4
            r4 = r5
            r12 = r10
            r3 = r22
            r5 = r2
            r10 = r8
            r2 = r21
            r8 = r7
        L_0x014e:
            r7 = r0
            throw r0     // Catch:{ all -> 0x0151 }
        L_0x0151:
            r0 = move-exception
            r13 = r0
            r14 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r14)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r7)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r14)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.mapIndexedTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapIndexedTo$$forInline(ReceiveChannel $this$mapIndexedTo, Collection destination, Function2 transform, Continuation continuation) {
        Collection collection = destination;
        int index = 0;
        ReceiveChannel $this$consume$iv$iv = $this$mapIndexedTo;
        Throwable cause$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    int index2 = index + 1;
                    try {
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Function2 function2 = transform;
                        int i2 = index2;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                    try {
                        collection.add(transform.invoke(Integer.valueOf(index), it.next()));
                        index = index2;
                        i = 1;
                    } catch (Throwable th3) {
                        e$iv$iv = th3;
                        int i22 = index2;
                        Throwable cause$iv$iv22 = e$iv$iv;
                        throw e$iv$iv;
                    }
                } else {
                    Function2 function22 = transform;
                    try {
                        Unit unit = Unit.INSTANCE;
                        InlineMarker.finallyStart(1);
                        ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                        InlineMarker.finallyEnd(1);
                        return collection;
                    } catch (Throwable th4) {
                        e$iv$iv = th4;
                        Throwable cause$iv$iv222 = e$iv$iv;
                        throw e$iv$iv;
                    }
                }
            }
        } catch (Throwable th5) {
            e$iv$iv = th5;
            Function2 function23 = transform;
            Throwable cause$iv$iv2222 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v14, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v12, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: kotlin.jvm.internal.Ref$IntRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v10, resolved type: kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super E, ? extends R>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v28, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v15, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v29, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v20, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v18, resolved type: kotlin.jvm.internal.Ref$IntRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v31, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v10, resolved type: kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super E, ? extends R>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v33, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v0, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00f7  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0143  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0159 A[Catch:{ all -> 0x0206 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002a  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, R, C extends kotlinx.coroutines.channels.SendChannel<? super R>> java.lang.Object mapIndexedTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r23, C r24, kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super E, ? extends R> r25, kotlin.coroutines.Continuation<? super C> r26) {
        /*
            r1 = r26
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedTo$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedTo$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedTo$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedTo$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapIndexedTo$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 2
            r6 = 0
            r7 = 1
            r8 = 0
            if (r4 == 0) goto L_0x00f7
            if (r4 == r7) goto L_0x009f
            if (r4 != r5) goto L_0x0097
            r4 = r8
            r9 = r6
            r10 = r8
            r11 = r8
            r12 = r6
            r13 = r8
            r14 = r6
            r15 = r8
            r16 = r8
            r17 = r6
            java.lang.Object r10 = r2.L$10
            java.lang.Object r11 = r2.L$9
            java.lang.Object r5 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r7 = r2.L$7
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r15 = r2.L$6
            java.lang.Throwable r15 = (java.lang.Throwable) r15
            r16 = r0
            java.lang.Object r0 = r2.L$5
            r13 = r0
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            java.lang.Object r0 = r2.L$4
            r4 = r0
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r0 = r2.L$3
            r8 = r0
            kotlin.jvm.internal.Ref$IntRef r8 = (kotlin.jvm.internal.Ref.IntRef) r8
            java.lang.Object r0 = r2.L$2
            r19 = r0
            kotlin.jvm.functions.Function2 r19 = (kotlin.jvm.functions.Function2) r19
            java.lang.Object r0 = r2.L$1
            r20 = r0
            kotlinx.coroutines.channels.SendChannel r20 = (kotlinx.coroutines.channels.SendChannel) r20
            java.lang.Object r0 = r2.L$0
            r21 = r0
            kotlinx.coroutines.channels.ReceiveChannel r21 = (kotlinx.coroutines.channels.ReceiveChannel) r21
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x008d }
            r0 = r7
            r18 = r15
            r7 = r2
            r15 = r9
            r2 = r20
            r9 = r4
            r20 = r17
            r4 = r1
            r17 = r14
            r1 = r21
            r14 = r8
            r8 = r3
            r3 = 2
            r22 = r11
            r11 = r5
            r5 = r16
            r16 = r13
            r13 = r22
            goto L_0x01b3
        L_0x008d:
            r0 = move-exception
            r6 = r2
            r5 = r4
            r2 = r20
            r12 = r21
            r4 = r1
            goto L_0x0238
        L_0x0097:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x009f:
            r16 = r0
            r0 = r8
            r9 = r6
            r4 = r6
            r5 = r8
            r14 = r6
            r7 = r8
            r10 = r8
            r17 = r6
            r6 = r8
            java.lang.Object r8 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r8 = (kotlinx.coroutines.channels.ChannelIterator) r8
            java.lang.Object r11 = r2.L$7
            r7 = r11
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r11 = r2.L$6
            r15 = r11
            java.lang.Throwable r15 = (java.lang.Throwable) r15
            java.lang.Object r10 = r2.L$5
            r13 = r10
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            java.lang.Object r5 = r2.L$4
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r0 = r2.L$3
            r6 = r0
            kotlin.jvm.internal.Ref$IntRef r6 = (kotlin.jvm.internal.Ref.IntRef) r6
            java.lang.Object r0 = r2.L$2
            r10 = r0
            kotlin.jvm.functions.Function2 r10 = (kotlin.jvm.functions.Function2) r10
            java.lang.Object r0 = r2.L$1
            r11 = r0
            kotlinx.coroutines.channels.SendChannel r11 = (kotlinx.coroutines.channels.SendChannel) r11
            java.lang.Object r0 = r2.L$0
            r12 = r0
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x00ee }
            r23 = r1
            r1 = r15
            r0 = r16
            r15 = r14
            r14 = r13
            r13 = r4
            r4 = r3
            r3 = r10
            r10 = r9
            r9 = r7
            r7 = r4
            r22 = r6
            r6 = r2
            r2 = r11
            r11 = r22
            goto L_0x0151
        L_0x00ee:
            r0 = move-exception
            r4 = r1
            r8 = r6
            r19 = r10
            r6 = r2
            r2 = r11
            goto L_0x0238
        L_0x00f7:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r9 = 0
            kotlin.jvm.internal.Ref$IntRef r0 = new kotlin.jvm.internal.Ref$IntRef
            r0.<init>()
            r0.element = r6
            r4 = r0
            r5 = r23
            r17 = 0
            r13 = r5
            r14 = 0
            r15 = r8
            java.lang.Throwable r15 = (java.lang.Throwable) r15
            r0 = r13
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r0.iterator()     // Catch:{ all -> 0x022e }
            r11 = r4
            r8 = r5
            r10 = r7
            r12 = r9
            r5 = r16
            r4 = r1
            r7 = r3
            r9 = r6
            r1 = r23
            r3 = r25
            r6 = r2
            r2 = r24
        L_0x0125:
            r6.L$0 = r1     // Catch:{ all -> 0x0222 }
            r6.L$1 = r2     // Catch:{ all -> 0x0222 }
            r6.L$2 = r3     // Catch:{ all -> 0x0222 }
            r6.L$3 = r11     // Catch:{ all -> 0x0222 }
            r6.L$4 = r8     // Catch:{ all -> 0x0222 }
            r6.L$5 = r13     // Catch:{ all -> 0x0222 }
            r6.L$6 = r15     // Catch:{ all -> 0x0222 }
            r6.L$7 = r0     // Catch:{ all -> 0x0222 }
            r6.L$8 = r10     // Catch:{ all -> 0x0222 }
            r16 = r1
            r1 = 1
            r6.label = r1     // Catch:{ all -> 0x0218 }
            java.lang.Object r1 = r10.hasNext(r6)     // Catch:{ all -> 0x0218 }
            if (r1 != r5) goto L_0x0143
            return r5
        L_0x0143:
            r23 = r4
            r4 = r1
            r1 = r15
            r15 = r14
            r14 = r13
            r13 = r9
            r9 = r0
            r0 = r5
            r5 = r8
            r8 = r10
            r10 = r12
            r12 = r16
        L_0x0151:
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ all -> 0x0206 }
            boolean r4 = r4.booleanValue()     // Catch:{ all -> 0x0206 }
            if (r4 == 0) goto L_0x01e6
            java.lang.Object r4 = r8.next()     // Catch:{ all -> 0x0206 }
            r24 = r4
            r16 = 0
            r25 = r7
            int r7 = r11.element     // Catch:{ all -> 0x01d5 }
            r26 = r10
            int r10 = r7 + 1
            r11.element = r10     // Catch:{ all -> 0x01c6 }
            java.lang.Integer r7 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r7)     // Catch:{ all -> 0x01c6 }
            r10 = r24
            java.lang.Object r7 = r3.invoke(r7, r10)     // Catch:{ all -> 0x01c6 }
            r6.L$0 = r12     // Catch:{ all -> 0x01c6 }
            r6.L$1 = r2     // Catch:{ all -> 0x01c6 }
            r6.L$2 = r3     // Catch:{ all -> 0x01c6 }
            r6.L$3 = r11     // Catch:{ all -> 0x01c6 }
            r6.L$4 = r5     // Catch:{ all -> 0x01c6 }
            r6.L$5 = r14     // Catch:{ all -> 0x01c6 }
            r6.L$6 = r1     // Catch:{ all -> 0x01c6 }
            r6.L$7 = r9     // Catch:{ all -> 0x01c6 }
            r6.L$8 = r8     // Catch:{ all -> 0x01c6 }
            r6.L$9 = r4     // Catch:{ all -> 0x01c6 }
            r6.L$10 = r10     // Catch:{ all -> 0x01c6 }
            r19 = r3
            r3 = 2
            r6.label = r3     // Catch:{ all -> 0x01fa }
            java.lang.Object r7 = r2.send(r7, r6)     // Catch:{ all -> 0x01fa }
            if (r7 != r0) goto L_0x0197
            return r0
        L_0x0197:
            r18 = r1
            r7 = r6
            r1 = r12
            r12 = r13
            r6 = r16
            r20 = r17
            r13 = r4
            r16 = r14
            r17 = r15
            r4 = r23
            r15 = r26
            r14 = r11
            r11 = r8
            r8 = r25
            r22 = r5
            r5 = r0
            r0 = r9
            r9 = r22
        L_0x01b3:
            r6 = r7
            r7 = r8
            r8 = r9
            r10 = r11
            r9 = r12
            r11 = r14
            r12 = r15
            r13 = r16
            r14 = r17
            r15 = r18
            r3 = r19
            r17 = r20
            goto L_0x0125
        L_0x01c6:
            r0 = move-exception
            r19 = r3
            r4 = r23
            r3 = r25
            r9 = r26
            r8 = r11
            r13 = r14
            r14 = r15
            r15 = r1
            goto L_0x0238
        L_0x01d5:
            r0 = move-exception
            r19 = r3
            r26 = r10
            r4 = r23
            r3 = r25
            r9 = r26
            r8 = r11
            r13 = r14
            r14 = r15
            r15 = r1
            goto L_0x0238
        L_0x01e6:
            r19 = r3
            r25 = r7
            r26 = r10
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x01fa }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r14, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r2
        L_0x01fa:
            r0 = move-exception
            r4 = r23
            r3 = r25
            r9 = r26
            r8 = r11
            r13 = r14
            r14 = r15
            r15 = r1
            goto L_0x0238
        L_0x0206:
            r0 = move-exception
            r19 = r3
            r25 = r7
            r26 = r10
            r4 = r23
            r3 = r25
            r9 = r26
            r8 = r11
            r13 = r14
            r14 = r15
            r15 = r1
            goto L_0x0238
        L_0x0218:
            r0 = move-exception
            r19 = r3
            r3 = r7
            r5 = r8
            r8 = r11
            r9 = r12
            r12 = r16
            goto L_0x0238
        L_0x0222:
            r0 = move-exception
            r16 = r1
            r19 = r3
            r3 = r7
            r5 = r8
            r8 = r11
            r9 = r12
            r12 = r16
            goto L_0x0238
        L_0x022e:
            r0 = move-exception
            r12 = r23
            r19 = r25
            r6 = r2
            r8 = r4
            r2 = r24
            r4 = r1
        L_0x0238:
            r1 = r0
            throw r0     // Catch:{ all -> 0x023b }
        L_0x023b:
            r0 = move-exception
            r7 = r0
            r10 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r13, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.mapIndexedTo(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapIndexedTo$$forInline(ReceiveChannel $this$mapIndexedTo, SendChannel destination, Function2 transform, Continuation continuation) {
        SendChannel sendChannel = destination;
        Continuation continuation2 = continuation;
        int index = 0;
        ReceiveChannel $this$consume$iv$iv = $this$mapIndexedTo;
        Throwable cause$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation2);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    int index2 = index + 1;
                    try {
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Function2 function2 = transform;
                        int i2 = index2;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                    try {
                        Object invoke = transform.invoke(Integer.valueOf(index), it.next());
                        InlineMarker.mark(0);
                        sendChannel.send(invoke, continuation2);
                        InlineMarker.mark(2);
                        InlineMarker.mark(1);
                        index = index2;
                        i = 1;
                    } catch (Throwable th3) {
                        e$iv$iv = th3;
                        int i22 = index2;
                        Throwable cause$iv$iv22 = e$iv$iv;
                        throw e$iv$iv;
                    }
                } else {
                    Function2 function22 = transform;
                    try {
                        Unit unit = Unit.INSTANCE;
                        InlineMarker.finallyStart(1);
                        ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                        InlineMarker.finallyEnd(1);
                        return sendChannel;
                    } catch (Throwable th4) {
                        e$iv$iv = th4;
                        Throwable cause$iv$iv222 = e$iv$iv;
                        throw e$iv$iv;
                    }
                }
            }
        } catch (Throwable th5) {
            e$iv$iv = th5;
            Function2 function23 = transform;
            Throwable cause$iv$iv2222 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    public static /* synthetic */ ReceiveChannel mapNotNull$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.mapNotNull(receiveChannel, coroutineContext, function2);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R> ReceiveChannel<R> mapNotNull(ReceiveChannel<? extends E> $this$mapNotNull, CoroutineContext context, Function2<? super E, ? super Continuation<? super R>, ? extends Object> transform) {
        Intrinsics.checkParameterIsNotNull($this$mapNotNull, "$this$mapNotNull");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        return ChannelsKt.filterNotNull(ChannelsKt.map($this$mapNotNull, context, transform));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00d1 A[Catch:{ all -> 0x010a }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, R, C extends java.util.Collection<? super R>> java.lang.Object mapNotNullTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r21, C r22, kotlin.jvm.functions.Function1<? super E, ? extends R> r23, kotlin.coroutines.Continuation<? super C> r24) {
        /*
            r1 = r24
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapNotNullTo$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapNotNullTo$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapNotNullTo$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapNotNullTo$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapNotNullTo$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L_0x0081
            if (r4 != r6) goto L_0x0079
            r4 = 0
            r7 = r4
            r8 = r4
            r9 = r4
            r10 = r5
            r11 = r5
            r12 = r5
            java.lang.Object r13 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r13 = (kotlinx.coroutines.channels.ChannelIterator) r13
            java.lang.Object r14 = r2.L$6
            r11 = r14
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r14 = r2.L$5
            r10 = r14
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r14 = r2.L$4
            r12 = r14
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r14 = r2.L$3
            r5 = r14
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r14 = r2.L$2
            kotlin.jvm.functions.Function1 r14 = (kotlin.jvm.functions.Function1) r14
            java.lang.Object r15 = r2.L$1
            java.util.Collection r15 = (java.util.Collection) r15
            java.lang.Object r6 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006c }
            r16 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r4
            r4 = r1
            r1 = r6
            r6 = r3
            r20 = r5
            r5 = r2
            r2 = r15
            r15 = r12
            r12 = r10
            r10 = r8
            r8 = r20
            goto L_0x00c9
        L_0x006c:
            r0 = move-exception
            r4 = r1
            r1 = r6
            r6 = r3
            r3 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r5
            r5 = r2
            r2 = r15
            goto L_0x012a
        L_0x0079:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0081:
            kotlin.ResultKt.throwOnFailure(r3)
            r8 = 0
            r4 = r21
            r7 = 0
            r12 = r4
            r9 = 0
            r10 = r5
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r5 = r12
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r11 = r5.iterator()     // Catch:{ all -> 0x011c }
            r13 = r5
            r15 = r11
            r14 = r12
            r5 = r2
            r11 = r9
            r12 = r10
            r2 = r22
            r9 = r7
            r10 = r8
            r8 = r4
            r7 = r6
            r4 = r1
            r6 = r3
            r1 = r21
            r3 = r23
        L_0x00a6:
            r5.L$0 = r1     // Catch:{ all -> 0x0116 }
            r5.L$1 = r2     // Catch:{ all -> 0x0116 }
            r5.L$2 = r3     // Catch:{ all -> 0x0116 }
            r5.L$3 = r8     // Catch:{ all -> 0x0116 }
            r5.L$4 = r14     // Catch:{ all -> 0x0116 }
            r5.L$5 = r12     // Catch:{ all -> 0x0116 }
            r5.L$6 = r13     // Catch:{ all -> 0x0116 }
            r5.L$7 = r15     // Catch:{ all -> 0x0116 }
            r21 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x0110 }
            java.lang.Object r1 = r15.hasNext(r5)     // Catch:{ all -> 0x0110 }
            if (r1 != r0) goto L_0x00c2
            return r0
        L_0x00c2:
            r16 = r15
            r15 = r14
            r14 = r3
            r3 = r1
            r1 = r21
        L_0x00c9:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x010a }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x010a }
            if (r3 == 0) goto L_0x00fc
            java.lang.Object r3 = r16.next()     // Catch:{ all -> 0x010a }
            r21 = r3
            r17 = 0
            r22 = r0
            r0 = r21
            java.lang.Object r18 = r14.invoke(r0)     // Catch:{ all -> 0x010a }
            if (r18 == 0) goto L_0x00f3
            r21 = r18
            r18 = 0
            r23 = r0
            r0 = r21
            boolean r19 = r2.add(r0)     // Catch:{ all -> 0x010a }
            kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r19)     // Catch:{ all -> 0x010a }
            goto L_0x00f5
        L_0x00f3:
            r23 = r0
        L_0x00f5:
            r0 = r22
            r3 = r14
            r14 = r15
            r15 = r16
            goto L_0x00a6
        L_0x00fc:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x010a }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r2
        L_0x010a:
            r0 = move-exception
            r7 = r9
            r9 = r11
            r3 = r14
            r14 = r15
            goto L_0x012a
        L_0x0110:
            r0 = move-exception
            r1 = r21
            r7 = r9
            r9 = r11
            goto L_0x012a
        L_0x0116:
            r0 = move-exception
            r21 = r1
            r7 = r9
            r9 = r11
            goto L_0x012a
        L_0x011c:
            r0 = move-exception
            r5 = r2
            r6 = r3
            r14 = r12
            r2 = r22
            r3 = r23
            r12 = r10
            r10 = r8
            r8 = r4
            r4 = r1
            r1 = r21
        L_0x012a:
            r11 = r0
            throw r0     // Catch:{ all -> 0x012d }
        L_0x012d:
            r0 = move-exception
            r12 = r0
            r13 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r13)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r14, r11)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r13)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.mapNotNullTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapNotNullTo$$forInline(ReceiveChannel $this$mapNotNullTo, Collection destination, Function1 transform, Continuation continuation) {
        Collection collection = destination;
        ReceiveChannel $this$consume$iv$iv = $this$mapNotNullTo;
        Throwable cause$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    try {
                        Object it2 = transform.invoke(it.next());
                        if (it2 != null) {
                            collection.add(it2);
                        }
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = transform;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return collection;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = transform;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v6, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v5, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v11, resolved type: kotlin.jvm.functions.Function1<? super E, ? extends R>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v26, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v9, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v31, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v32, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v33, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v11, resolved type: kotlin.jvm.functions.Function1<? super E, ? extends R>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v35, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v2, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0124  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0137 A[Catch:{ all -> 0x01e8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, R, C extends kotlinx.coroutines.channels.SendChannel<? super R>> java.lang.Object mapNotNullTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r23, C r24, kotlin.jvm.functions.Function1<? super E, ? extends R> r25, kotlin.coroutines.Continuation<? super C> r26) {
        /*
            r1 = r26
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapNotNullTo$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapNotNullTo$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapNotNullTo$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapNotNullTo$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapNotNullTo$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 2
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x00de
            r8 = 0
            if (r4 == r6) goto L_0x008a
            if (r4 != r5) goto L_0x0082
            r4 = r8
            r9 = r8
            r10 = r8
            r11 = r7
            r12 = r7
            r13 = r8
            r14 = r7
            r15 = r8
            r16 = r7
            r17 = r7
            r18 = r7
            java.lang.Object r7 = r2.L$10
            java.lang.Object r11 = r2.L$9
            java.lang.Object r12 = r2.L$8
            java.lang.Object r5 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r2.L$6
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            r16 = r0
            java.lang.Object r0 = r2.L$5
            r14 = r0
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            java.lang.Object r0 = r2.L$4
            r17 = r0
            kotlinx.coroutines.channels.ReceiveChannel r17 = (kotlinx.coroutines.channels.ReceiveChannel) r17
            java.lang.Object r0 = r2.L$3
            r18 = r0
            kotlinx.coroutines.channels.ReceiveChannel r18 = (kotlinx.coroutines.channels.ReceiveChannel) r18
            java.lang.Object r0 = r2.L$2
            r19 = r0
            kotlin.jvm.functions.Function1 r19 = (kotlin.jvm.functions.Function1) r19
            java.lang.Object r0 = r2.L$1
            r20 = r0
            kotlinx.coroutines.channels.SendChannel r20 = (kotlinx.coroutines.channels.SendChannel) r20
            java.lang.Object r0 = r2.L$0
            r21 = r0
            kotlinx.coroutines.channels.ReceiveChannel r21 = (kotlinx.coroutines.channels.ReceiveChannel) r21
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0076 }
            r0 = r16
            goto L_0x018a
        L_0x0076:
            r0 = move-exception
            r6 = r2
            r8 = r4
            r12 = r17
            r2 = r20
            r11 = r21
            r4 = r1
            goto L_0x0217
        L_0x0082:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x008a:
            r16 = r0
            r4 = r8
            r9 = r8
            r13 = r8
            r0 = r7
            r5 = r8
            r6 = r7
            r8 = r7
            java.lang.Object r10 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r10 = (kotlinx.coroutines.channels.ChannelIterator) r10
            java.lang.Object r11 = r2.L$6
            r6 = r11
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r11 = r2.L$5
            r14 = r11
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            java.lang.Object r0 = r2.L$4
            r17 = r0
            kotlinx.coroutines.channels.ReceiveChannel r17 = (kotlinx.coroutines.channels.ReceiveChannel) r17
            java.lang.Object r0 = r2.L$3
            r18 = r0
            kotlinx.coroutines.channels.ReceiveChannel r18 = (kotlinx.coroutines.channels.ReceiveChannel) r18
            java.lang.Object r0 = r2.L$2
            r7 = r0
            kotlin.jvm.functions.Function1 r7 = (kotlin.jvm.functions.Function1) r7
            java.lang.Object r0 = r2.L$1
            r8 = r0
            kotlinx.coroutines.channels.SendChannel r8 = (kotlinx.coroutines.channels.SendChannel) r8
            java.lang.Object r0 = r2.L$0
            r11 = r0
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x00d3 }
            r23 = r1
            r15 = r5
            r5 = r10
            r0 = r16
            r12 = r17
            r1 = r18
            r10 = r9
            r9 = r6
            r6 = r2
            r2 = r8
            r8 = r4
            r4 = r3
            r3 = r7
            r7 = r4
            goto L_0x012f
        L_0x00d3:
            r0 = move-exception
            r6 = r2
            r19 = r7
            r2 = r8
            r12 = r17
            r8 = r4
            r4 = r1
            goto L_0x0217
        L_0x00de:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r9 = 0
            r18 = r23
            r4 = 0
            r17 = r18
            r13 = 0
            r14 = r7
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            r0 = r17
            r5 = 0
            kotlinx.coroutines.channels.ChannelIterator r6 = r0.iterator()     // Catch:{ all -> 0x020b }
            r7 = r3
            r8 = r4
            r10 = r6
            r11 = r9
            r12 = r17
            r15 = r18
            r3 = r25
            r4 = r1
            r6 = r2
            r9 = r5
            r5 = r16
            r1 = r23
            r2 = r24
        L_0x0108:
            r6.L$0 = r1     // Catch:{ all -> 0x01ff }
            r6.L$1 = r2     // Catch:{ all -> 0x01ff }
            r6.L$2 = r3     // Catch:{ all -> 0x01ff }
            r6.L$3 = r15     // Catch:{ all -> 0x01ff }
            r6.L$4 = r12     // Catch:{ all -> 0x01ff }
            r6.L$5 = r14     // Catch:{ all -> 0x01ff }
            r6.L$6 = r0     // Catch:{ all -> 0x01ff }
            r6.L$7 = r10     // Catch:{ all -> 0x01ff }
            r16 = r1
            r1 = 1
            r6.label = r1     // Catch:{ all -> 0x01f5 }
            java.lang.Object r1 = r10.hasNext(r6)     // Catch:{ all -> 0x01f5 }
            if (r1 != r5) goto L_0x0124
            return r5
        L_0x0124:
            r23 = r4
            r4 = r1
            r1 = r15
            r15 = r9
            r9 = r0
            r0 = r5
            r5 = r10
            r10 = r11
            r11 = r16
        L_0x012f:
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ all -> 0x01e8 }
            boolean r4 = r4.booleanValue()     // Catch:{ all -> 0x01e8 }
            if (r4 == 0) goto L_0x01cd
            java.lang.Object r4 = r5.next()     // Catch:{ all -> 0x01e8 }
            r24 = r4
            r16 = 0
            r25 = r7
            r7 = r24
            java.lang.Object r17 = r3.invoke(r7)     // Catch:{ all -> 0x01c2 }
            if (r17 == 0) goto L_0x01a9
            r24 = r17
            r17 = 0
            r6.L$0 = r11     // Catch:{ all -> 0x01c2 }
            r6.L$1 = r2     // Catch:{ all -> 0x01c2 }
            r6.L$2 = r3     // Catch:{ all -> 0x01c2 }
            r6.L$3 = r1     // Catch:{ all -> 0x01c2 }
            r6.L$4 = r12     // Catch:{ all -> 0x01c2 }
            r6.L$5 = r14     // Catch:{ all -> 0x01c2 }
            r6.L$6 = r9     // Catch:{ all -> 0x01c2 }
            r6.L$7 = r5     // Catch:{ all -> 0x01c2 }
            r6.L$8 = r4     // Catch:{ all -> 0x01c2 }
            r6.L$9 = r7     // Catch:{ all -> 0x01c2 }
            r18 = r1
            r1 = r24
            r6.L$10 = r1     // Catch:{ all -> 0x019f }
            r19 = r3
            r3 = 2
            r6.label = r3     // Catch:{ all -> 0x01e1 }
            java.lang.Object r3 = r2.send(r1, r6)     // Catch:{ all -> 0x01e1 }
            if (r3 != r0) goto L_0x0173
            return r0
        L_0x0173:
            r3 = r25
            r20 = r2
            r2 = r6
            r6 = r9
            r9 = r10
            r21 = r11
            r10 = r16
            r11 = r7
            r7 = r1
            r1 = r23
            r22 = r12
            r12 = r4
            r4 = r8
            r8 = r17
            r17 = r22
        L_0x018a:
            r7 = r2
            r8 = r3
            r10 = r5
            r11 = r9
            r3 = r19
            r2 = r20
            r5 = r1
            r9 = r4
            r4 = r12
            r12 = r17
            r1 = r21
            r22 = r6
            r6 = r0
            r0 = r22
            goto L_0x01b8
        L_0x019f:
            r0 = move-exception
            r19 = r3
            r4 = r23
            r3 = r25
            r9 = r10
            goto L_0x0217
        L_0x01a9:
            r18 = r1
            r19 = r3
            r7 = r6
            r1 = r11
            r6 = r0
            r0 = r9
            r11 = r10
            r10 = r5
            r9 = r8
            r5 = r23
            r8 = r25
        L_0x01b8:
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r15
            r15 = r18
            goto L_0x0108
        L_0x01c2:
            r0 = move-exception
            r18 = r1
            r19 = r3
            r4 = r23
            r3 = r25
            r9 = r10
            goto L_0x0217
        L_0x01cd:
            r18 = r1
            r19 = r3
            r25 = r7
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x01e1 }
            r1 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r14)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            return r2
        L_0x01e1:
            r0 = move-exception
            r4 = r23
            r3 = r25
            r9 = r10
            goto L_0x0217
        L_0x01e8:
            r0 = move-exception
            r18 = r1
            r19 = r3
            r25 = r7
            r4 = r23
            r3 = r25
            r9 = r10
            goto L_0x0217
        L_0x01f5:
            r0 = move-exception
            r19 = r3
            r3 = r7
            r9 = r11
            r18 = r15
            r11 = r16
            goto L_0x0217
        L_0x01ff:
            r0 = move-exception
            r16 = r1
            r19 = r3
            r3 = r7
            r9 = r11
            r18 = r15
            r11 = r16
            goto L_0x0217
        L_0x020b:
            r0 = move-exception
            r11 = r23
            r19 = r25
            r6 = r2
            r8 = r4
            r12 = r17
            r2 = r24
            r4 = r1
        L_0x0217:
            r1 = r0
            throw r0     // Catch:{ all -> 0x021a }
        L_0x021a:
            r0 = move-exception
            r5 = r0
            r7 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r7)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r7)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.mapNotNullTo(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapNotNullTo$$forInline(ReceiveChannel $this$mapNotNullTo, SendChannel destination, Function1 transform, Continuation continuation) {
        SendChannel sendChannel = destination;
        Continuation continuation2 = continuation;
        ReceiveChannel $this$consume$iv$iv = $this$mapNotNullTo;
        Throwable cause$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation2);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    try {
                        Object it2 = transform.invoke(it.next());
                        if (it2 != null) {
                            InlineMarker.mark(0);
                            sendChannel.send(it2, continuation2);
                            InlineMarker.mark(2);
                            InlineMarker.mark(1);
                        }
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = transform;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return sendChannel;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = transform;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00d0 A[Catch:{ all -> 0x0108 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, R, C extends java.util.Collection<? super R>> java.lang.Object mapTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r19, C r20, kotlin.jvm.functions.Function1<? super E, ? extends R> r21, kotlin.coroutines.Continuation<? super C> r22) {
        /*
            r1 = r22
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapTo$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapTo$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapTo$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapTo$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapTo$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L_0x0080
            if (r4 != r6) goto L_0x0078
            r4 = 0
            r7 = r4
            r8 = r4
            r9 = r4
            r10 = r5
            r11 = r5
            r12 = r5
            java.lang.Object r13 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r13 = (kotlinx.coroutines.channels.ChannelIterator) r13
            java.lang.Object r14 = r2.L$6
            r11 = r14
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r14 = r2.L$5
            r10 = r14
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r14 = r2.L$4
            r12 = r14
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r14 = r2.L$3
            r5 = r14
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r14 = r2.L$2
            kotlin.jvm.functions.Function1 r14 = (kotlin.jvm.functions.Function1) r14
            java.lang.Object r15 = r2.L$1
            java.util.Collection r15 = (java.util.Collection) r15
            java.lang.Object r6 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006c }
            r16 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r4
            r4 = r1
            r1 = r6
            r6 = r3
            r18 = r5
            r5 = r2
            r2 = r15
            r15 = r12
            r12 = r10
            r10 = r8
            r8 = r18
            goto L_0x00c8
        L_0x006c:
            r0 = move-exception
            r4 = r1
            r1 = r3
            r3 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r5
            r5 = r2
            r2 = r15
            goto L_0x0131
        L_0x0078:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0080:
            kotlin.ResultKt.throwOnFailure(r3)
            r8 = 0
            r4 = r19
            r7 = 0
            r12 = r4
            r9 = 0
            r10 = r5
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r5 = r12
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r11 = r5.iterator()     // Catch:{ all -> 0x0123 }
            r13 = r5
            r15 = r11
            r14 = r12
            r5 = r2
            r11 = r9
            r12 = r10
            r2 = r20
            r9 = r7
            r10 = r8
            r8 = r4
            r7 = r6
            r4 = r1
            r6 = r3
            r1 = r19
            r3 = r21
        L_0x00a5:
            r5.L$0 = r1     // Catch:{ all -> 0x011a }
            r5.L$1 = r2     // Catch:{ all -> 0x011a }
            r5.L$2 = r3     // Catch:{ all -> 0x011a }
            r5.L$3 = r8     // Catch:{ all -> 0x011a }
            r5.L$4 = r14     // Catch:{ all -> 0x011a }
            r5.L$5 = r12     // Catch:{ all -> 0x011a }
            r5.L$6 = r13     // Catch:{ all -> 0x011a }
            r5.L$7 = r15     // Catch:{ all -> 0x011a }
            r19 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x0113 }
            java.lang.Object r1 = r15.hasNext(r5)     // Catch:{ all -> 0x0113 }
            if (r1 != r0) goto L_0x00c1
            return r0
        L_0x00c1:
            r16 = r15
            r15 = r14
            r14 = r3
            r3 = r1
            r1 = r19
        L_0x00c8:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0108 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0108 }
            if (r3 == 0) goto L_0x00ef
            java.lang.Object r3 = r16.next()     // Catch:{ all -> 0x0108 }
            r19 = r3
            r17 = 0
            r20 = r0
            r0 = r19
            r19 = r1
            java.lang.Object r1 = r14.invoke(r0)     // Catch:{ all -> 0x00ff }
            r2.add(r1)     // Catch:{ all -> 0x00ff }
            r1 = r19
            r0 = r20
            r3 = r14
            r14 = r15
            r15 = r16
            goto L_0x00a5
        L_0x00ef:
            r19 = r1
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00ff }
            r1 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            return r2
        L_0x00ff:
            r0 = move-exception
            r1 = r6
            r7 = r9
            r9 = r11
            r3 = r14
            r14 = r15
            r6 = r19
            goto L_0x0131
        L_0x0108:
            r0 = move-exception
            r19 = r1
            r1 = r6
            r7 = r9
            r9 = r11
            r3 = r14
            r14 = r15
            r6 = r19
            goto L_0x0131
        L_0x0113:
            r0 = move-exception
            r1 = r6
            r7 = r9
            r9 = r11
            r6 = r19
            goto L_0x0131
        L_0x011a:
            r0 = move-exception
            r19 = r1
            r1 = r6
            r7 = r9
            r9 = r11
            r6 = r19
            goto L_0x0131
        L_0x0123:
            r0 = move-exception
            r6 = r19
            r5 = r2
            r14 = r12
            r2 = r20
            r12 = r10
            r10 = r8
            r8 = r4
            r4 = r1
            r1 = r3
            r3 = r21
        L_0x0131:
            r11 = r0
            throw r0     // Catch:{ all -> 0x0134 }
        L_0x0134:
            r0 = move-exception
            r12 = r0
            r13 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r13)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r14, r11)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r13)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.mapTo(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapTo$$forInline(ReceiveChannel $this$mapTo, Collection destination, Function1 transform, Continuation continuation) {
        Collection collection = destination;
        ReceiveChannel $this$consume$iv$iv = $this$mapTo;
        Throwable cause$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    try {
                        collection.add(transform.invoke(it.next()));
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = transform;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return collection;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = transform;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v14, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v12, resolved type: kotlin.jvm.functions.Function1<? super E, ? extends R>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v14, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v27, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v17, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v28, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v9, resolved type: kotlin.jvm.functions.Function1<? super E, ? extends R>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r20v0, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00e8  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0141 A[Catch:{ all -> 0x01c8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, R, C extends kotlinx.coroutines.channels.SendChannel<? super R>> java.lang.Object mapTo(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r22, C r23, kotlin.jvm.functions.Function1<? super E, ? extends R> r24, kotlin.coroutines.Continuation<? super C> r25) {
        /*
            r1 = r25
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapTo$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapTo$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapTo$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapTo$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$mapTo$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 2
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x00e8
            r8 = 0
            if (r4 == r6) goto L_0x0095
            if (r4 != r5) goto L_0x008d
            r4 = r8
            r9 = r8
            r10 = r8
            r11 = r7
            r12 = r7
            r13 = r8
            r14 = r7
            r15 = r7
            r16 = r7
            java.lang.Object r11 = r2.L$9
            java.lang.Object r12 = r2.L$8
            java.lang.Object r5 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r2.L$6
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r15 = r2.L$5
            r14 = r15
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            java.lang.Object r15 = r2.L$4
            kotlinx.coroutines.channels.ReceiveChannel r15 = (kotlinx.coroutines.channels.ReceiveChannel) r15
            r16 = r0
            java.lang.Object r0 = r2.L$3
            r7 = r0
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r0 = r2.L$2
            r18 = r0
            kotlin.jvm.functions.Function1 r18 = (kotlin.jvm.functions.Function1) r18
            java.lang.Object r0 = r2.L$1
            r19 = r0
            kotlinx.coroutines.channels.SendChannel r19 = (kotlinx.coroutines.channels.SendChannel) r19
            java.lang.Object r0 = r2.L$0
            r20 = r0
            kotlinx.coroutines.channels.ReceiveChannel r20 = (kotlinx.coroutines.channels.ReceiveChannel) r20
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0083 }
            r0 = r6
            r17 = r14
            r6 = r2
            r14 = r12
            r2 = r19
            r12 = r11
            r19 = r15
            r11 = r5
            r15 = r9
            r5 = r16
            r9 = r8
            r16 = r13
            r8 = r4
            r13 = r7
            r4 = r1
            r7 = r3
            r1 = r20
            r3 = 2
            goto L_0x018a
        L_0x0083:
            r0 = move-exception
            r6 = r2
            r8 = r4
            r2 = r19
            r12 = r20
            r4 = r1
            goto L_0x01f8
        L_0x008d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0095:
            r16 = r0
            r4 = r8
            r9 = r8
            r13 = r8
            r0 = r7
            r5 = r8
            r6 = r7
            r8 = r7
            java.lang.Object r10 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r10 = (kotlinx.coroutines.channels.ChannelIterator) r10
            java.lang.Object r11 = r2.L$6
            r6 = r11
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r11 = r2.L$5
            r14 = r11
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            java.lang.Object r0 = r2.L$4
            r15 = r0
            kotlinx.coroutines.channels.ReceiveChannel r15 = (kotlinx.coroutines.channels.ReceiveChannel) r15
            java.lang.Object r0 = r2.L$3
            r7 = r0
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r0 = r2.L$2
            r8 = r0
            kotlin.jvm.functions.Function1 r8 = (kotlin.jvm.functions.Function1) r8
            java.lang.Object r0 = r2.L$1
            r11 = r0
            kotlinx.coroutines.channels.SendChannel r11 = (kotlinx.coroutines.channels.SendChannel) r11
            java.lang.Object r0 = r2.L$0
            r12 = r0
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x00df }
            r22 = r1
            r1 = r15
            r0 = r16
            r15 = r14
            r14 = r13
            r13 = r9
            r9 = r6
            r6 = r2
            r2 = r11
            r11 = r7
            r7 = r3
            r3 = r8
            r8 = r4
            r4 = r7
            r21 = r10
            r10 = r5
            r5 = r21
            goto L_0x0139
        L_0x00df:
            r0 = move-exception
            r6 = r2
            r18 = r8
            r2 = r11
            r8 = r4
            r4 = r1
            goto L_0x01f8
        L_0x00e8:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r9 = 0
            r4 = r22
            r5 = 0
            r15 = r4
            r13 = 0
            r14 = r7
            java.lang.Throwable r14 = (java.lang.Throwable) r14
            r0 = r15
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r7 = r0.iterator()     // Catch:{ all -> 0x01ed }
            r11 = r4
            r8 = r5
            r10 = r7
            r12 = r9
            r5 = r16
            r4 = r1
            r7 = r3
            r9 = r6
            r1 = r22
            r3 = r24
            r6 = r2
            r2 = r23
        L_0x010d:
            r6.L$0 = r1     // Catch:{ all -> 0x01e2 }
            r6.L$1 = r2     // Catch:{ all -> 0x01e2 }
            r6.L$2 = r3     // Catch:{ all -> 0x01e2 }
            r6.L$3 = r11     // Catch:{ all -> 0x01e2 }
            r6.L$4 = r15     // Catch:{ all -> 0x01e2 }
            r6.L$5 = r14     // Catch:{ all -> 0x01e2 }
            r6.L$6 = r0     // Catch:{ all -> 0x01e2 }
            r6.L$7 = r10     // Catch:{ all -> 0x01e2 }
            r16 = r1
            r1 = 1
            r6.label = r1     // Catch:{ all -> 0x01d9 }
            java.lang.Object r1 = r10.hasNext(r6)     // Catch:{ all -> 0x01d9 }
            if (r1 != r5) goto L_0x0129
            return r5
        L_0x0129:
            r22 = r4
            r4 = r1
            r1 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r16
            r21 = r9
            r9 = r0
            r0 = r5
            r5 = r10
            r10 = r21
        L_0x0139:
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ all -> 0x01c8 }
            boolean r4 = r4.booleanValue()     // Catch:{ all -> 0x01c8 }
            if (r4 == 0) goto L_0x01a7
            java.lang.Object r4 = r5.next()     // Catch:{ all -> 0x01c8 }
            r23 = r4
            r16 = 0
            r24 = r7
            r7 = r23
            r23 = r8
            java.lang.Object r8 = r3.invoke(r7)     // Catch:{ all -> 0x0197 }
            r6.L$0 = r12     // Catch:{ all -> 0x0197 }
            r6.L$1 = r2     // Catch:{ all -> 0x0197 }
            r6.L$2 = r3     // Catch:{ all -> 0x0197 }
            r6.L$3 = r11     // Catch:{ all -> 0x0197 }
            r6.L$4 = r1     // Catch:{ all -> 0x0197 }
            r6.L$5 = r15     // Catch:{ all -> 0x0197 }
            r6.L$6 = r9     // Catch:{ all -> 0x0197 }
            r6.L$7 = r5     // Catch:{ all -> 0x0197 }
            r6.L$8 = r4     // Catch:{ all -> 0x0197 }
            r6.L$9 = r7     // Catch:{ all -> 0x0197 }
            r18 = r3
            r3 = 2
            r6.label = r3     // Catch:{ all -> 0x01bb }
            java.lang.Object r8 = r2.send(r8, r6)     // Catch:{ all -> 0x01bb }
            if (r8 != r0) goto L_0x0173
            return r0
        L_0x0173:
            r8 = r23
            r19 = r1
            r1 = r12
            r17 = r15
            r12 = r7
            r15 = r13
            r7 = r24
            r13 = r11
            r11 = r5
            r5 = r0
            r0 = r9
            r9 = r10
            r10 = r16
            r16 = r14
            r14 = r4
            r4 = r22
        L_0x018a:
            r10 = r11
            r11 = r13
            r12 = r15
            r13 = r16
            r14 = r17
            r3 = r18
            r15 = r19
            goto L_0x010d
        L_0x0197:
            r0 = move-exception
            r18 = r3
            r4 = r22
            r8 = r23
            r3 = r24
            r7 = r11
            r9 = r13
            r13 = r14
            r14 = r15
            r15 = r1
            goto L_0x01f8
        L_0x01a7:
            r18 = r3
            r24 = r7
            r23 = r8
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x01bb }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r15)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r2
        L_0x01bb:
            r0 = move-exception
            r4 = r22
            r8 = r23
            r3 = r24
            r7 = r11
            r9 = r13
            r13 = r14
            r14 = r15
            r15 = r1
            goto L_0x01f8
        L_0x01c8:
            r0 = move-exception
            r18 = r3
            r24 = r7
            r23 = r8
            r4 = r22
            r3 = r24
            r7 = r11
            r9 = r13
            r13 = r14
            r14 = r15
            r15 = r1
            goto L_0x01f8
        L_0x01d9:
            r0 = move-exception
            r18 = r3
            r3 = r7
            r7 = r11
            r9 = r12
            r12 = r16
            goto L_0x01f8
        L_0x01e2:
            r0 = move-exception
            r16 = r1
            r18 = r3
            r3 = r7
            r7 = r11
            r9 = r12
            r12 = r16
            goto L_0x01f8
        L_0x01ed:
            r0 = move-exception
            r12 = r22
            r18 = r24
            r6 = r2
            r7 = r4
            r8 = r5
            r2 = r23
            r4 = r1
        L_0x01f8:
            r1 = r0
            throw r0     // Catch:{ all -> 0x01fb }
        L_0x01fb:
            r0 = move-exception
            r5 = r0
            r10 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.mapTo(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapTo$$forInline(ReceiveChannel $this$mapTo, SendChannel destination, Function1 transform, Continuation continuation) {
        SendChannel sendChannel = destination;
        Continuation continuation2 = continuation;
        ReceiveChannel $this$consume$iv$iv = $this$mapTo;
        Throwable cause$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation2);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    try {
                        Object invoke = transform.invoke(it.next());
                        InlineMarker.mark(0);
                        sendChannel.send(invoke, continuation2);
                        InlineMarker.mark(2);
                        InlineMarker.mark(1);
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = transform;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return sendChannel;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = transform;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    public static /* synthetic */ ReceiveChannel withIndex$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.withIndex(receiveChannel, coroutineContext);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<IndexedValue<E>> withIndex(ReceiveChannel<? extends E> $this$withIndex, CoroutineContext context) {
        Intrinsics.checkParameterIsNotNull($this$withIndex, "$this$withIndex");
        Intrinsics.checkParameterIsNotNull(context, "context");
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, ChannelsKt.consumes($this$withIndex), new ChannelsKt__Channels_commonKt$withIndex$1($this$withIndex, (Continuation) null), 2, (Object) null);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> distinct(ReceiveChannel<? extends E> $this$distinct) {
        Intrinsics.checkParameterIsNotNull($this$distinct, "$this$distinct");
        return distinctBy$default($this$distinct, (CoroutineContext) null, new ChannelsKt__Channels_commonKt$distinct$1((Continuation) null), 1, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel distinctBy$default(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.distinctBy(receiveChannel, coroutineContext, function2);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, K> ReceiveChannel<E> distinctBy(ReceiveChannel<? extends E> $this$distinctBy, CoroutineContext context, Function2<? super E, ? super Continuation<? super K>, ? extends Object> selector) {
        Intrinsics.checkParameterIsNotNull($this$distinctBy, "$this$distinctBy");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, ChannelsKt.consumes($this$distinctBy), new ChannelsKt__Channels_commonKt$distinctBy$1($this$distinctBy, selector, (Continuation) null), 2, (Object) null);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object toMutableSet(ReceiveChannel<? extends E> $this$toMutableSet, Continuation<? super Set<E>> $completion) {
        return ChannelsKt.toCollection($this$toMutableSet, new LinkedHashSet(), $completion);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00c3 A[Catch:{ all -> 0x0108 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object all(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r20, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r21, kotlin.coroutines.Continuation<? super java.lang.Boolean> r22) {
        /*
            r1 = r22
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$all$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$all$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$all$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$all$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$all$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 0
            r7 = 1
            if (r4 == 0) goto L_0x0079
            if (r4 != r7) goto L_0x0071
            r4 = r6
            r8 = r6
            r9 = r6
            r10 = r5
            r11 = r5
            r12 = r5
            r13 = r5
            java.lang.Object r14 = r2.L$6
            kotlinx.coroutines.channels.ChannelIterator r14 = (kotlinx.coroutines.channels.ChannelIterator) r14
            java.lang.Object r15 = r2.L$5
            r6 = r15
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r15 = r2.L$4
            r9 = r15
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            java.lang.Object r15 = r2.L$3
            r4 = r15
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r15 = r2.L$2
            r8 = r15
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            java.lang.Object r5 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0065 }
            r7 = r4
            r4 = r1
            r1 = r5
            r5 = r2
            r2 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r6
            r6 = r3
            goto L_0x00bb
        L_0x0065:
            r0 = move-exception
            r6 = r4
            r4 = r2
            r2 = r15
            r19 = r3
            r3 = r1
            r1 = r5
            r5 = r19
            goto L_0x0123
        L_0x0071:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0079:
            kotlin.ResultKt.throwOnFailure(r3)
            r11 = 0
            r8 = r20
            r13 = 0
            r4 = r8
            r12 = 0
            r9 = r6
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            r5 = r4
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r5.iterator()     // Catch:{ all -> 0x011a }
            r15 = r10
            r14 = r13
            r10 = r9
            r13 = r12
            r9 = r8
            r12 = r11
            r8 = r5
            r11 = r6
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
            r1 = r20
            r2 = r21
        L_0x009c:
            r4.L$0 = r1     // Catch:{ all -> 0x0113 }
            r4.L$1 = r2     // Catch:{ all -> 0x0113 }
            r4.L$2 = r9     // Catch:{ all -> 0x0113 }
            r4.L$3 = r6     // Catch:{ all -> 0x0113 }
            r4.L$4 = r10     // Catch:{ all -> 0x0113 }
            r4.L$5 = r8     // Catch:{ all -> 0x0113 }
            r4.L$6 = r15     // Catch:{ all -> 0x0113 }
            r4.label = r7     // Catch:{ all -> 0x0113 }
            java.lang.Object r7 = r15.hasNext(r4)     // Catch:{ all -> 0x0113 }
            if (r7 != r0) goto L_0x00b3
            return r0
        L_0x00b3:
            r19 = r4
            r4 = r3
            r3 = r7
            r7 = r6
            r6 = r5
            r5 = r19
        L_0x00bb:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0108 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0108 }
            if (r3 == 0) goto L_0x00f6
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x0108 }
            r20 = r3
            r17 = 0
            r21 = r0
            r0 = r20
            java.lang.Object r18 = r2.invoke(r0)     // Catch:{ all -> 0x0108 }
            java.lang.Boolean r18 = (java.lang.Boolean) r18     // Catch:{ all -> 0x0108 }
            boolean r18 = r18.booleanValue()     // Catch:{ all -> 0x0108 }
            if (r18 != 0) goto L_0x00ec
            r16 = 0
            java.lang.Boolean r15 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r16)     // Catch:{ all -> 0x0108 }
            r0 = 2
            kotlin.jvm.internal.InlineMarker.finallyStart(r0)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r10)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r0)
            return r15
        L_0x00ec:
            r16 = 0
            r0 = r21
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = 1
            goto L_0x009c
        L_0x00f6:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0108 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r10)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
            return r0
        L_0x0108:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r8 = r9
            r9 = r10
            r11 = r12
            r12 = r13
            r13 = r14
            goto L_0x0123
        L_0x0113:
            r0 = move-exception
            r8 = r9
            r9 = r10
            r11 = r12
            r12 = r13
            r13 = r14
            goto L_0x0123
        L_0x011a:
            r0 = move-exception
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
            r1 = r20
            r2 = r21
        L_0x0123:
            r7 = r0
            throw r0     // Catch:{ all -> 0x0126 }
        L_0x0126:
            r0 = move-exception
            r9 = r0
            r10 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r7)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.all(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object all$$forInline(ReceiveChannel $this$all, Function1 predicate, Continuation continuation) {
        ReceiveChannel $this$consume$iv$iv = $this$all;
        Throwable cause$iv$iv = null;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            do {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    try {
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return true;
                }
            } while (((Boolean) predicate.invoke(it.next())).booleanValue());
            InlineMarker.finallyStart(2);
            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
            InlineMarker.finallyEnd(2);
            return false;
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object any(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r10, kotlin.coroutines.Continuation<? super java.lang.Boolean> r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$any$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$any$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$any$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$any$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$any$1
            r0.<init>(r11)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            r5 = 0
            if (r3 == 0) goto L_0x004e
            if (r3 != r4) goto L_0x0046
            r2 = r5
            r3 = 0
            r4 = r3
            r6 = r5
            java.lang.Object r7 = r0.L$3
            r5 = r7
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r7 = r0.L$2
            r6 = r7
            java.lang.Throwable r6 = (java.lang.Throwable) r6
            java.lang.Object r7 = r0.L$1
            r2 = r7
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            java.lang.Object r7 = r0.L$0
            r10 = r7
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0044 }
            r4 = r1
            goto L_0x006f
        L_0x0044:
            r4 = move-exception
            goto L_0x0077
        L_0x0046:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x004e:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r10
            r6 = 0
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            r7 = r3
            r8 = 0
            kotlinx.coroutines.channels.ChannelIterator r9 = r7.iterator()     // Catch:{ all -> 0x0073 }
            r0.L$0 = r10     // Catch:{ all -> 0x0073 }
            r0.L$1 = r3     // Catch:{ all -> 0x0073 }
            r0.L$2 = r5     // Catch:{ all -> 0x0073 }
            r0.L$3 = r7     // Catch:{ all -> 0x0073 }
            r0.label = r4     // Catch:{ all -> 0x0073 }
            java.lang.Object r4 = r9.hasNext(r0)     // Catch:{ all -> 0x0073 }
            if (r4 != r2) goto L_0x006d
            return r2
        L_0x006d:
            r2 = r3
            r6 = r5
        L_0x006f:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r6)
            return r4
        L_0x0073:
            r4 = move-exception
            r2 = r3
            r3 = r6
            r6 = r5
        L_0x0077:
            r5 = r4
            throw r4     // Catch:{ all -> 0x007a }
        L_0x007a:
            r4 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.any(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00c3 A[Catch:{ all -> 0x0107 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object any(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r19, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r20, kotlin.coroutines.Continuation<? super java.lang.Boolean> r21) {
        /*
            r1 = r21
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$any$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$any$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$any$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$any$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$any$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 0
            r7 = 1
            if (r4 == 0) goto L_0x0079
            if (r4 != r7) goto L_0x0071
            r4 = r6
            r8 = r6
            r9 = r6
            r10 = r5
            r11 = r5
            r12 = r5
            r13 = r5
            java.lang.Object r14 = r2.L$6
            kotlinx.coroutines.channels.ChannelIterator r14 = (kotlinx.coroutines.channels.ChannelIterator) r14
            java.lang.Object r15 = r2.L$5
            r6 = r15
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r15 = r2.L$4
            r9 = r15
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            java.lang.Object r15 = r2.L$3
            r4 = r15
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r15 = r2.L$2
            r8 = r15
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            java.lang.Object r5 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0065 }
            r7 = r4
            r4 = r1
            r1 = r5
            r5 = r2
            r2 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r6
            r6 = r3
            goto L_0x00bb
        L_0x0065:
            r0 = move-exception
            r6 = r4
            r4 = r2
            r2 = r15
            r18 = r3
            r3 = r1
            r1 = r5
            r5 = r18
            goto L_0x0122
        L_0x0071:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0079:
            kotlin.ResultKt.throwOnFailure(r3)
            r11 = 0
            r8 = r19
            r13 = 0
            r4 = r8
            r12 = 0
            r9 = r6
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            r5 = r4
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r5.iterator()     // Catch:{ all -> 0x0119 }
            r15 = r10
            r14 = r13
            r10 = r9
            r13 = r12
            r9 = r8
            r12 = r11
            r8 = r5
            r11 = r6
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
            r1 = r19
            r2 = r20
        L_0x009c:
            r4.L$0 = r1     // Catch:{ all -> 0x0112 }
            r4.L$1 = r2     // Catch:{ all -> 0x0112 }
            r4.L$2 = r9     // Catch:{ all -> 0x0112 }
            r4.L$3 = r6     // Catch:{ all -> 0x0112 }
            r4.L$4 = r10     // Catch:{ all -> 0x0112 }
            r4.L$5 = r8     // Catch:{ all -> 0x0112 }
            r4.L$6 = r15     // Catch:{ all -> 0x0112 }
            r4.label = r7     // Catch:{ all -> 0x0112 }
            java.lang.Object r7 = r15.hasNext(r4)     // Catch:{ all -> 0x0112 }
            if (r7 != r0) goto L_0x00b3
            return r0
        L_0x00b3:
            r18 = r4
            r4 = r3
            r3 = r7
            r7 = r6
            r6 = r5
            r5 = r18
        L_0x00bb:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0107 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0107 }
            if (r3 == 0) goto L_0x00f4
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x0107 }
            r19 = r3
            r16 = 0
            r20 = r0
            r0 = r19
            java.lang.Object r17 = r2.invoke(r0)     // Catch:{ all -> 0x0107 }
            java.lang.Boolean r17 = (java.lang.Boolean) r17     // Catch:{ all -> 0x0107 }
            boolean r17 = r17.booleanValue()     // Catch:{ all -> 0x0107 }
            if (r17 == 0) goto L_0x00eb
            r15 = 1
            java.lang.Boolean r15 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r15)     // Catch:{ all -> 0x0107 }
            r0 = 2
            kotlin.jvm.internal.InlineMarker.finallyStart(r0)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r10)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r0)
            return r15
        L_0x00eb:
            r0 = r20
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = 1
            goto L_0x009c
        L_0x00f4:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0107 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r10)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            r0 = 0
            java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r0)
            return r0
        L_0x0107:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r8 = r9
            r9 = r10
            r11 = r12
            r12 = r13
            r13 = r14
            goto L_0x0122
        L_0x0112:
            r0 = move-exception
            r8 = r9
            r9 = r10
            r11 = r12
            r12 = r13
            r13 = r14
            goto L_0x0122
        L_0x0119:
            r0 = move-exception
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
            r1 = r19
            r2 = r20
        L_0x0122:
            r7 = r0
            throw r0     // Catch:{ all -> 0x0125 }
        L_0x0125:
            r0 = move-exception
            r9 = r0
            r10 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r7)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.any(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object any$$forInline(ReceiveChannel $this$any, Function1 predicate, Continuation continuation) {
        ReceiveChannel $this$consume$iv$iv = $this$any;
        Throwable cause$iv$iv = null;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            do {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    try {
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return false;
                }
            } while (!((Boolean) predicate.invoke(it.next())).booleanValue());
            InlineMarker.finallyStart(2);
            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
            InlineMarker.finallyEnd(2);
            return true;
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: kotlin.jvm.internal.Ref$IntRef} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00c9 A[Catch:{ all -> 0x00f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object count(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r20, kotlin.coroutines.Continuation<? super java.lang.Integer> r21) {
        /*
            r1 = r21
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$count$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$count$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$count$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$count$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$count$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            r7 = 0
            if (r4 == 0) goto L_0x0077
            if (r4 != r5) goto L_0x006f
            r4 = r7
            r8 = r6
            r9 = r6
            r10 = r7
            r11 = r7
            r12 = r7
            java.lang.Object r13 = r2.L$6
            kotlinx.coroutines.channels.ChannelIterator r13 = (kotlinx.coroutines.channels.ChannelIterator) r13
            java.lang.Object r14 = r2.L$5
            r10 = r14
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r14 = r2.L$4
            r12 = r14
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            java.lang.Object r14 = r2.L$3
            r7 = r14
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r14 = r2.L$2
            r11 = r14
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r14 = r2.L$1
            r4 = r14
            kotlin.jvm.internal.Ref$IntRef r4 = (kotlin.jvm.internal.Ref.IntRef) r4
            java.lang.Object r14 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r14 = (kotlinx.coroutines.channels.ReceiveChannel) r14
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0067 }
            r15 = r13
            r13 = r11
            r11 = r6
            r6 = r3
            r19 = r2
            r2 = r1
            r1 = r14
            r14 = r12
            r12 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r4
            r4 = r19
            goto L_0x00c1
        L_0x0067:
            r0 = move-exception
            r6 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r14
            goto L_0x010a
        L_0x006f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0077:
            kotlin.ResultKt.throwOnFailure(r3)
            kotlin.jvm.internal.Ref$IntRef r4 = new kotlin.jvm.internal.Ref$IntRef
            r4.<init>()
            r4.element = r6
            r11 = r20
            r9 = 0
            r6 = r11
            r8 = 0
            r12 = r7
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            r7 = r6
            r10 = 0
            kotlinx.coroutines.channels.ChannelIterator r13 = r7.iterator()     // Catch:{ all -> 0x0102 }
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r7
            r7 = r6
            r6 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r20
        L_0x009b:
            r3.L$0 = r1     // Catch:{ all -> 0x00fe }
            r3.L$1 = r6     // Catch:{ all -> 0x00fe }
            r3.L$2 = r12     // Catch:{ all -> 0x00fe }
            r3.L$3 = r7     // Catch:{ all -> 0x00fe }
            r3.L$4 = r13     // Catch:{ all -> 0x00fe }
            r3.L$5 = r11     // Catch:{ all -> 0x00fe }
            r3.L$6 = r14     // Catch:{ all -> 0x00fe }
            r3.label = r5     // Catch:{ all -> 0x00fe }
            java.lang.Object r15 = r14.hasNext(r3)     // Catch:{ all -> 0x00fe }
            if (r15 != r0) goto L_0x00b2
            return r0
        L_0x00b2:
            r19 = r4
            r4 = r3
            r3 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r19
        L_0x00c1:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x00f4 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x00f4 }
            if (r3 == 0) goto L_0x00e7
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x00f4 }
            r16 = r3
            r17 = 0
            int r5 = r7.element     // Catch:{ all -> 0x00f4 }
            r18 = 1
            int r5 = r5 + 1
            r7.element = r5     // Catch:{ all -> 0x00f4 }
            r3 = r4
            r4 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r5 = r18
            goto L_0x009b
        L_0x00e7:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00f4 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r14)
            int r0 = r7.element
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)
            return r0
        L_0x00f4:
            r0 = move-exception
            r3 = r4
            r4 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r11 = r13
            r12 = r14
            goto L_0x010a
        L_0x00fe:
            r0 = move-exception
            r11 = r12
            r12 = r13
            goto L_0x010a
        L_0x0102:
            r0 = move-exception
            r7 = r6
            r6 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r20
        L_0x010a:
            r5 = r0
            throw r0     // Catch:{ all -> 0x010d }
        L_0x010d:
            r0 = move-exception
            r10 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r5)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.count(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v9, resolved type: kotlin.jvm.internal.Ref$IntRef} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c9  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00e2 A[Catch:{ all -> 0x012c }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object count(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r20, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r21, kotlin.coroutines.Continuation<? super java.lang.Integer> r22) {
        /*
            r1 = r22
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$count$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$count$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$count$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$count$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$count$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 0
            r7 = 1
            if (r4 == 0) goto L_0x0082
            if (r4 != r7) goto L_0x007a
            r4 = r5
            r8 = r6
            r9 = r5
            r10 = r6
            r11 = r5
            r12 = r6
            r13 = r6
            java.lang.Object r14 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r14 = (kotlinx.coroutines.channels.ChannelIterator) r14
            java.lang.Object r15 = r2.L$6
            r12 = r15
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r15 = r2.L$5
            r10 = r15
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r15 = r2.L$4
            r13 = r15
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            java.lang.Object r15 = r2.L$3
            r6 = r15
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r15 = r2.L$2
            r8 = r15
            kotlin.jvm.internal.Ref$IntRef r8 = (kotlin.jvm.internal.Ref.IntRef) r8
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            java.lang.Object r7 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006c }
            r17 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r4
            r4 = r1
            r1 = r7
            r7 = r5
            r5 = r2
            r2 = r15
            r15 = r13
            r13 = r11
            r11 = r9
            r9 = r6
            r6 = r3
            goto L_0x00da
        L_0x006c:
            r0 = move-exception
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r6
            r6 = r5
            r5 = r3
            r3 = r1
            r1 = r7
            r7 = r4
            r4 = r2
            r2 = r15
            goto L_0x014f
        L_0x007a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0082:
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = 0
            kotlin.jvm.internal.Ref$IntRef r7 = new kotlin.jvm.internal.Ref$IntRef
            r7.<init>()
            r7.element = r5
            r8 = r7
            r5 = r20
            r7 = 0
            r13 = r5
            r9 = 0
            r10 = r6
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r6 = r13
            r11 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r6.iterator()     // Catch:{ all -> 0x0142 }
            r15 = r12
            r14 = r13
            r13 = r6
            r12 = r11
            r6 = r4
            r11 = r10
            r4 = r2
            r10 = r9
            r2 = r21
            r9 = r8
            r8 = r5
            r5 = r3
            r3 = r1
            r1 = r20
        L_0x00ad:
            r4.L$0 = r1     // Catch:{ all -> 0x013d }
            r4.L$1 = r2     // Catch:{ all -> 0x013d }
            r4.L$2 = r9     // Catch:{ all -> 0x013d }
            r4.L$3 = r8     // Catch:{ all -> 0x013d }
            r4.L$4 = r14     // Catch:{ all -> 0x013d }
            r4.L$5 = r11     // Catch:{ all -> 0x013d }
            r4.L$6 = r13     // Catch:{ all -> 0x013d }
            r4.L$7 = r15     // Catch:{ all -> 0x013d }
            r20 = r1
            r1 = 1
            r4.label = r1     // Catch:{ all -> 0x0138 }
            java.lang.Object r1 = r15.hasNext(r4)     // Catch:{ all -> 0x0138 }
            if (r1 != r0) goto L_0x00c9
            return r0
        L_0x00c9:
            r17 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r20
        L_0x00da:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x012c }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x012c }
            if (r3 == 0) goto L_0x0118
            java.lang.Object r3 = r17.next()     // Catch:{ all -> 0x012c }
            r20 = r3
            r18 = 0
            r21 = r0
            r0 = r20
            java.lang.Object r19 = r2.invoke(r0)     // Catch:{ all -> 0x012c }
            java.lang.Boolean r19 = (java.lang.Boolean) r19     // Catch:{ all -> 0x012c }
            boolean r19 = r19.booleanValue()     // Catch:{ all -> 0x012c }
            if (r19 == 0) goto L_0x0105
            r19 = r0
            int r0 = r10.element     // Catch:{ all -> 0x012c }
            r16 = 1
            int r0 = r0 + 1
            r10.element = r0     // Catch:{ all -> 0x012c }
            goto L_0x0107
        L_0x0105:
            r19 = r0
        L_0x0107:
            r0 = r21
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r15 = r17
            goto L_0x00ad
        L_0x0118:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x012c }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            int r0 = r10.element
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)
            return r0
        L_0x012c:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r13 = r15
            goto L_0x014f
        L_0x0138:
            r0 = move-exception
            r1 = r20
            r13 = r14
            goto L_0x014f
        L_0x013d:
            r0 = move-exception
            r20 = r1
            r13 = r14
            goto L_0x014f
        L_0x0142:
            r0 = move-exception
            r6 = r4
            r11 = r10
            r4 = r2
            r10 = r9
            r2 = r21
            r9 = r8
            r8 = r5
            r5 = r3
            r3 = r1
            r1 = r20
        L_0x014f:
            r11 = r0
            throw r0     // Catch:{ all -> 0x0152 }
        L_0x0152:
            r0 = move-exception
            r12 = r0
            r14 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r14)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r13, r11)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r14)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.count(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object count$$forInline(ReceiveChannel $this$count, Function1 predicate, Continuation continuation) {
        int count = 0;
        ReceiveChannel $this$consume$iv$iv = $this$count;
        Throwable cause$iv$iv = null;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    try {
                        if (((Boolean) predicate.invoke(it.next())).booleanValue()) {
                            count++;
                        }
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return Integer.valueOf(count);
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: kotlin.jvm.internal.Ref$ObjectRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v1, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00e7 A[Catch:{ all -> 0x0115 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, R> java.lang.Object fold(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r19, R r20, kotlin.jvm.functions.Function2<? super R, ? super E, ? extends R> r21, kotlin.coroutines.Continuation<? super R> r22) {
        /*
            r1 = r22
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$fold$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$fold$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$fold$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$fold$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$fold$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x0087
            if (r4 != r5) goto L_0x007f
            r4 = r6
            r7 = 0
            r8 = r7
            r9 = r7
            r10 = r6
            r11 = r7
            r12 = r6
            r13 = r6
            java.lang.Object r14 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r14 = (kotlinx.coroutines.channels.ChannelIterator) r14
            java.lang.Object r15 = r2.L$7
            r12 = r15
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r15 = r2.L$6
            r13 = r15
            java.lang.Throwable r13 = (java.lang.Throwable) r13
            java.lang.Object r15 = r2.L$5
            r10 = r15
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r15 = r2.L$4
            r4 = r15
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r15 = r2.L$3
            r6 = r15
            kotlin.jvm.internal.Ref$ObjectRef r6 = (kotlin.jvm.internal.Ref.ObjectRef) r6
            java.lang.Object r15 = r2.L$2
            kotlin.jvm.functions.Function2 r15 = (kotlin.jvm.functions.Function2) r15
            java.lang.Object r5 = r2.L$1
            r16 = r0
            java.lang.Object r0 = r2.L$0
            r17 = r0
            kotlinx.coroutines.channels.ReceiveChannel r17 = (kotlinx.coroutines.channels.ReceiveChannel) r17
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0075 }
            r19 = r1
            r1 = r13
            r0 = r16
            r16 = r17
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r4
            r4 = r5
            r5 = r2
            r2 = r15
            r15 = r12
            r12 = r10
            r10 = r8
            r8 = r6
            r6 = r3
            goto L_0x00df
        L_0x0075:
            r0 = move-exception
            r9 = r7
            r16 = r17
            r7 = r4
            r4 = r5
            r5 = r2
            r2 = r15
            goto L_0x013c
        L_0x007f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0087:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r8 = 0
            kotlin.jvm.internal.Ref$ObjectRef r0 = new kotlin.jvm.internal.Ref$ObjectRef
            r0.<init>()
            r4 = r20
            r0.element = r4
            r5 = r0
            r7 = r19
            r9 = 0
            r10 = r7
            r11 = 0
            r13 = r6
            java.lang.Throwable r13 = (java.lang.Throwable) r13
            r0 = r10
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r0.iterator()     // Catch:{ all -> 0x0135 }
            r14 = r0
            r15 = r13
            r0 = r16
            r13 = r11
            r11 = r6
            r6 = r3
            r3 = r12
            r12 = r10
            r10 = r8
            r8 = r5
            r5 = r2
            r2 = r21
            r18 = r1
            r1 = r19
            r19 = r18
        L_0x00ba:
            r5.L$0 = r1     // Catch:{ all -> 0x0129 }
            r5.L$1 = r4     // Catch:{ all -> 0x0129 }
            r5.L$2 = r2     // Catch:{ all -> 0x0129 }
            r5.L$3 = r8     // Catch:{ all -> 0x0129 }
            r5.L$4 = r7     // Catch:{ all -> 0x0129 }
            r5.L$5 = r12     // Catch:{ all -> 0x0129 }
            r5.L$6 = r15     // Catch:{ all -> 0x0129 }
            r5.L$7 = r14     // Catch:{ all -> 0x0129 }
            r5.L$8 = r3     // Catch:{ all -> 0x0129 }
            r16 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x011f }
            java.lang.Object r1 = r3.hasNext(r5)     // Catch:{ all -> 0x011f }
            if (r1 != r0) goto L_0x00d8
            return r0
        L_0x00d8:
            r18 = r3
            r3 = r1
            r1 = r15
            r15 = r14
            r14 = r18
        L_0x00df:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0115 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0115 }
            if (r3 == 0) goto L_0x0105
            java.lang.Object r3 = r14.next()     // Catch:{ all -> 0x0115 }
            r20 = r3
            r17 = 0
            r21 = r0
            T r0 = r8.element     // Catch:{ all -> 0x0115 }
            r22 = r3
            r3 = r20
            java.lang.Object r0 = r2.invoke(r0, r3)     // Catch:{ all -> 0x0115 }
            r8.element = r0     // Catch:{ all -> 0x0115 }
            r0 = r21
            r3 = r14
            r14 = r15
            r15 = r1
            r1 = r16
            goto L_0x00ba
        L_0x0105:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0115 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            T r0 = r8.element
            return r0
        L_0x0115:
            r0 = move-exception
            r3 = r6
            r6 = r8
            r8 = r10
            r10 = r12
            r11 = r13
            r13 = r1
            r1 = r19
            goto L_0x013c
        L_0x011f:
            r0 = move-exception
            r1 = r19
            r3 = r6
            r6 = r8
            r8 = r10
            r10 = r12
            r11 = r13
            r13 = r15
            goto L_0x013c
        L_0x0129:
            r0 = move-exception
            r16 = r1
            r1 = r19
            r3 = r6
            r6 = r8
            r8 = r10
            r10 = r12
            r11 = r13
            r13 = r15
            goto L_0x013c
        L_0x0135:
            r0 = move-exception
            r16 = r19
            r6 = r5
            r5 = r2
            r2 = r21
        L_0x013c:
            r12 = r0
            throw r0     // Catch:{ all -> 0x013f }
        L_0x013f:
            r0 = move-exception
            r13 = r0
            r14 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r14)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r14)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.fold(kotlinx.coroutines.channels.ReceiveChannel, java.lang.Object, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object fold$$forInline(ReceiveChannel $this$fold, Object initial, Function2 operation, Continuation continuation) {
        Object accumulator = initial;
        ReceiveChannel $this$consume$iv$iv = $this$fold;
        Throwable cause$iv$iv = null;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    try {
                        accumulator = operation.invoke(accumulator, it.next());
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function2 function2 = operation;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return accumulator;
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function2 function22 = operation;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v13, resolved type: kotlin.jvm.internal.Ref$ObjectRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: kotlin.jvm.internal.Ref$IntRef} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0093  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00f0  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0106 A[Catch:{ all -> 0x0155 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, R> java.lang.Object foldIndexed(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r21, R r22, kotlin.jvm.functions.Function3<? super java.lang.Integer, ? super R, ? super E, ? extends R> r23, kotlin.coroutines.Continuation<? super R> r24) {
        /*
            r1 = r24
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$foldIndexed$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$foldIndexed$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$foldIndexed$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$foldIndexed$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$foldIndexed$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x0093
            if (r4 != r6) goto L_0x008b
            r4 = r7
            r8 = r5
            r9 = r7
            r10 = r5
            r11 = r5
            r12 = r7
            r13 = r7
            r14 = r7
            java.lang.Object r15 = r2.L$9
            kotlinx.coroutines.channels.ChannelIterator r15 = (kotlinx.coroutines.channels.ChannelIterator) r15
            java.lang.Object r6 = r2.L$8
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r9 = r2.L$7
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            java.lang.Object r13 = r2.L$6
            r12 = r13
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r13 = r2.L$5
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            java.lang.Object r14 = r2.L$4
            r4 = r14
            kotlin.jvm.internal.Ref$ObjectRef r4 = (kotlin.jvm.internal.Ref.ObjectRef) r4
            java.lang.Object r14 = r2.L$3
            r7 = r14
            kotlin.jvm.internal.Ref$IntRef r7 = (kotlin.jvm.internal.Ref.IntRef) r7
            java.lang.Object r14 = r2.L$2
            kotlin.jvm.functions.Function3 r14 = (kotlin.jvm.functions.Function3) r14
            java.lang.Object r1 = r2.L$1
            r16 = r0
            java.lang.Object r0 = r2.L$0
            r17 = r0
            kotlinx.coroutines.channels.ReceiveChannel r17 = (kotlinx.coroutines.channels.ReceiveChannel) r17
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0079 }
            r21 = r24
            r0 = r16
            r16 = r17
            r17 = r11
            r11 = r8
            r8 = r5
            r5 = r1
            r1 = r12
            r12 = r9
            r9 = r6
            r6 = r2
            r2 = r14
            r14 = r10
            r10 = r7
            r7 = r4
            r4 = r3
            goto L_0x00fe
        L_0x0079:
            r0 = move-exception
            r10 = r7
            r16 = r17
            r7 = r4
            r4 = r1
            r1 = r24
            r20 = r5
            r5 = r2
            r2 = r14
            r14 = r11
            r11 = r8
            r8 = r20
            goto L_0x0182
        L_0x008b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0093:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r8 = 0
            kotlin.jvm.internal.Ref$IntRef r0 = new kotlin.jvm.internal.Ref$IntRef
            r0.<init>()
            r0.element = r5
            r1 = r0
            kotlin.jvm.internal.Ref$ObjectRef r0 = new kotlin.jvm.internal.Ref$ObjectRef
            r0.<init>()
            r4 = r22
            r0.element = r4
            r5 = r0
            r13 = r21
            r11 = 0
            r12 = r13
            r6 = 0
            r9 = r7
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            r0 = r12
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r0.iterator()     // Catch:{ all -> 0x0175 }
            r22 = r3
            r14 = r11
            r15 = r12
            r3 = r13
            r13 = r7
            r11 = r8
            r12 = r9
            r9 = r0
            r7 = r5
            r8 = r6
            r6 = r10
            r0 = r16
            r10 = r1
            r5 = r2
            r1 = r21
            r2 = r23
            r21 = r24
        L_0x00d0:
            r5.L$0 = r1     // Catch:{ all -> 0x016a }
            r5.L$1 = r4     // Catch:{ all -> 0x016a }
            r5.L$2 = r2     // Catch:{ all -> 0x016a }
            r5.L$3 = r10     // Catch:{ all -> 0x016a }
            r5.L$4 = r7     // Catch:{ all -> 0x016a }
            r5.L$5 = r3     // Catch:{ all -> 0x016a }
            r5.L$6 = r15     // Catch:{ all -> 0x016a }
            r5.L$7 = r12     // Catch:{ all -> 0x016a }
            r5.L$8 = r9     // Catch:{ all -> 0x016a }
            r5.L$9 = r6     // Catch:{ all -> 0x016a }
            r16 = r1
            r1 = 1
            r5.label = r1     // Catch:{ all -> 0x0161 }
            java.lang.Object r1 = r6.hasNext(r5)     // Catch:{ all -> 0x0161 }
            if (r1 != r0) goto L_0x00f0
            return r0
        L_0x00f0:
            r17 = r14
            r14 = r13
            r13 = r3
            r3 = r22
            r20 = r4
            r4 = r1
            r1 = r15
            r15 = r6
            r6 = r5
            r5 = r20
        L_0x00fe:
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ all -> 0x0155 }
            boolean r4 = r4.booleanValue()     // Catch:{ all -> 0x0155 }
            if (r4 == 0) goto L_0x0137
            java.lang.Object r4 = r15.next()     // Catch:{ all -> 0x0155 }
            r22 = r4
            r18 = 0
            r23 = r0
            int r0 = r10.element     // Catch:{ all -> 0x0155 }
            r24 = r3
            int r3 = r0 + 1
            r10.element = r3     // Catch:{ all -> 0x0149 }
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)     // Catch:{ all -> 0x0149 }
            T r3 = r7.element     // Catch:{ all -> 0x0149 }
            r19 = r4
            r4 = r22
            java.lang.Object r0 = r2.invoke(r0, r3, r4)     // Catch:{ all -> 0x0149 }
            r7.element = r0     // Catch:{ all -> 0x0149 }
            r0 = r23
            r22 = r24
            r4 = r5
            r5 = r6
            r3 = r13
            r13 = r14
            r6 = r15
            r14 = r17
            r15 = r1
            r1 = r16
            goto L_0x00d0
        L_0x0137:
            r24 = r3
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0149 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r1, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            T r0 = r7.element
            return r0
        L_0x0149:
            r0 = move-exception
            r3 = r24
            r4 = r5
            r5 = r6
            r9 = r12
            r14 = r17
            r12 = r1
            r1 = r21
            goto L_0x0182
        L_0x0155:
            r0 = move-exception
            r24 = r3
            r4 = r5
            r5 = r6
            r9 = r12
            r14 = r17
            r12 = r1
            r1 = r21
            goto L_0x0182
        L_0x0161:
            r0 = move-exception
            r1 = r21
            r13 = r3
            r9 = r12
            r12 = r15
            r3 = r22
            goto L_0x0182
        L_0x016a:
            r0 = move-exception
            r16 = r1
            r1 = r21
            r13 = r3
            r9 = r12
            r12 = r15
            r3 = r22
            goto L_0x0182
        L_0x0175:
            r0 = move-exception
            r16 = r21
            r10 = r1
            r7 = r5
            r14 = r11
            r1 = r24
            r5 = r2
            r11 = r8
            r2 = r23
            r8 = r6
        L_0x0182:
            r6 = r0
            throw r0     // Catch:{ all -> 0x0185 }
        L_0x0185:
            r0 = move-exception
            r9 = r0
            r15 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r15)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r6)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r15)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.foldIndexed(kotlinx.coroutines.channels.ReceiveChannel, java.lang.Object, kotlin.jvm.functions.Function3, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object foldIndexed$$forInline(ReceiveChannel $this$foldIndexed, Object initial, Function3 operation, Continuation continuation) {
        int index = 0;
        Object accumulator = initial;
        ReceiveChannel $this$consume$iv$iv = $this$foldIndexed;
        Throwable cause$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    int index2 = index + 1;
                    try {
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Function3 function3 = operation;
                        int i2 = index2;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                    try {
                        accumulator = operation.invoke(Integer.valueOf(index), accumulator, it.next());
                        index = index2;
                        i = 1;
                    } catch (Throwable th3) {
                        e$iv$iv = th3;
                        int i22 = index2;
                        Throwable cause$iv$iv22 = e$iv$iv;
                        throw e$iv$iv;
                    }
                } else {
                    Function3 function32 = operation;
                    try {
                        Unit unit = Unit.INSTANCE;
                        InlineMarker.finallyStart(1);
                        ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                        InlineMarker.finallyEnd(1);
                        return accumulator;
                    } catch (Throwable th4) {
                        e$iv$iv = th4;
                        Throwable cause$iv$iv222 = e$iv$iv;
                        throw e$iv$iv;
                    }
                }
            }
        } catch (Throwable th5) {
            e$iv$iv = th5;
            Function3 function33 = operation;
            Throwable cause$iv$iv2222 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v17, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v21, resolved type: java.lang.Comparable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v14, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x009d  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00d4  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00df A[SYNTHETIC, Splitter:B:42:0x00df] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010a  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x011f A[Catch:{ all -> 0x014f }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, R extends java.lang.Comparable<? super R>> java.lang.Object maxBy(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r19, kotlin.jvm.functions.Function1<? super E, ? extends R> r20, kotlin.coroutines.Continuation<? super E> r21) {
        /*
            r1 = r21
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$maxBy$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$maxBy$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$maxBy$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$maxBy$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$maxBy$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 2
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x009d
            r8 = 0
            if (r4 == r6) goto L_0x006d
            if (r4 != r5) goto L_0x0065
            r4 = r7
            r9 = r7
            r10 = r8
            r11 = r7
            r12 = r7
            r13 = r7
            r14 = r8
            java.lang.Object r15 = r2.L$7
            r12 = r15
            java.lang.Comparable r12 = (java.lang.Comparable) r12
            java.lang.Object r13 = r2.L$6
            java.lang.Object r15 = r2.L$5
            r11 = r15
            kotlinx.coroutines.channels.ChannelIterator r11 = (kotlinx.coroutines.channels.ChannelIterator) r11
            java.lang.Object r15 = r2.L$4
            r7 = r15
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r15 = r2.L$3
            r4 = r15
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            java.lang.Object r15 = r2.L$2
            r9 = r15
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            java.lang.Object r5 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0060 }
            r6 = r5
            r5 = r4
            r4 = r3
            goto L_0x0117
        L_0x0060:
            r0 = move-exception
            r13 = r5
            r12 = r15
            goto L_0x0167
        L_0x0065:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x006d:
            r4 = r7
            r5 = r7
            r9 = r8
            r10 = r7
            r14 = r8
            r11 = r7
            java.lang.Object r12 = r2.L$5
            r10 = r12
            kotlinx.coroutines.channels.ChannelIterator r10 = (kotlinx.coroutines.channels.ChannelIterator) r10
            java.lang.Object r12 = r2.L$4
            r11 = r12
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r12 = r2.L$3
            r4 = r12
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            java.lang.Object r12 = r2.L$2
            r5 = r12
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r12 = r2.L$1
            kotlin.jvm.functions.Function1 r12 = (kotlin.jvm.functions.Function1) r12
            java.lang.Object r13 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0099 }
            r15 = r3
            r17 = r9
            r9 = r5
            r5 = r17
            goto L_0x00cc
        L_0x0099:
            r0 = move-exception
            r9 = r5
            goto L_0x0167
        L_0x009d:
            kotlin.ResultKt.throwOnFailure(r3)
            r14 = 0
            r9 = r19
            r8 = 0
            r4 = r7
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            r11 = r9
            r5 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r11.iterator()     // Catch:{ all -> 0x015d }
            r12 = r19
            r2.L$0 = r12     // Catch:{ all -> 0x015b }
            r13 = r20
            r2.L$1 = r13     // Catch:{ all -> 0x0159 }
            r2.L$2 = r9     // Catch:{ all -> 0x0159 }
            r2.L$3 = r4     // Catch:{ all -> 0x0159 }
            r2.L$4 = r11     // Catch:{ all -> 0x0159 }
            r2.L$5 = r10     // Catch:{ all -> 0x0159 }
            r2.label = r6     // Catch:{ all -> 0x0159 }
            java.lang.Object r15 = r10.hasNext(r2)     // Catch:{ all -> 0x0159 }
            if (r15 != r0) goto L_0x00c7
            return r0
        L_0x00c7:
            r17 = r13
            r13 = r12
            r12 = r17
        L_0x00cc:
            java.lang.Boolean r15 = (java.lang.Boolean) r15     // Catch:{ all -> 0x0157 }
            boolean r15 = r15.booleanValue()     // Catch:{ all -> 0x0157 }
            if (r15 != 0) goto L_0x00df
            r0 = 3
            kotlin.jvm.internal.InlineMarker.finallyStart(r0)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r4)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r0)
            return r7
        L_0x00df:
            java.lang.Object r7 = r10.next()     // Catch:{ all -> 0x0157 }
            java.lang.Object r15 = r12.invoke(r7)     // Catch:{ all -> 0x0157 }
            java.lang.Comparable r15 = (java.lang.Comparable) r15     // Catch:{ all -> 0x0157 }
            r17 = r10
            r10 = r5
            r5 = r7
            r7 = r11
            r11 = r17
        L_0x00f0:
            r2.L$0 = r13     // Catch:{ all -> 0x0155 }
            r2.L$1 = r12     // Catch:{ all -> 0x0155 }
            r2.L$2 = r9     // Catch:{ all -> 0x0155 }
            r2.L$3 = r4     // Catch:{ all -> 0x0155 }
            r2.L$4 = r7     // Catch:{ all -> 0x0155 }
            r2.L$5 = r11     // Catch:{ all -> 0x0155 }
            r2.L$6 = r5     // Catch:{ all -> 0x0155 }
            r2.L$7 = r15     // Catch:{ all -> 0x0155 }
            r6 = 2
            r2.label = r6     // Catch:{ all -> 0x0155 }
            java.lang.Object r6 = r11.hasNext(r2)     // Catch:{ all -> 0x0155 }
            if (r6 != r0) goto L_0x010a
            return r0
        L_0x010a:
            r17 = r4
            r4 = r3
            r3 = r6
            r6 = r13
            r13 = r5
            r5 = r17
            r18 = r15
            r15 = r12
            r12 = r18
        L_0x0117:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x014f }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x014f }
            if (r3 == 0) goto L_0x0144
            java.lang.Object r3 = r11.next()     // Catch:{ all -> 0x014f }
            java.lang.Object r16 = r15.invoke(r3)     // Catch:{ all -> 0x014f }
            java.lang.Comparable r16 = (java.lang.Comparable) r16     // Catch:{ all -> 0x014f }
            r19 = r16
            r20 = r0
            r0 = r19
            int r16 = r12.compareTo(r0)     // Catch:{ all -> 0x014f }
            if (r16 >= 0) goto L_0x0137
            r13 = r3
            r12 = r0
        L_0x0137:
            r0 = r20
            r3 = r4
            r4 = r5
            r5 = r13
            r13 = r6
            r6 = 1
            r17 = r15
            r15 = r12
            r12 = r17
            goto L_0x00f0
        L_0x0144:
            r0 = 2
            kotlin.jvm.internal.InlineMarker.finallyStart(r0)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r0)
            return r13
        L_0x014f:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r13 = r6
            r12 = r15
            goto L_0x0167
        L_0x0155:
            r0 = move-exception
            goto L_0x0167
        L_0x0157:
            r0 = move-exception
            goto L_0x0167
        L_0x0159:
            r0 = move-exception
            goto L_0x0162
        L_0x015b:
            r0 = move-exception
            goto L_0x0160
        L_0x015d:
            r0 = move-exception
            r12 = r19
        L_0x0160:
            r13 = r20
        L_0x0162:
            r17 = r13
            r13 = r12
            r12 = r17
        L_0x0167:
            r4 = r0
            throw r0     // Catch:{ all -> 0x016a }
        L_0x016a:
            r0 = move-exception
            r5 = r0
            r6 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r6)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r4)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.maxBy(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: finally extract failed */
    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object maxBy$$forInline(ReceiveChannel $this$maxBy, Function1 selector, Continuation continuation) {
        Throwable cause$iv;
        int i;
        Function1 function1 = selector;
        Continuation continuation2 = continuation;
        ReceiveChannel $this$consume$iv = $this$maxBy;
        Object maxElem = null;
        Throwable th = null;
        try {
            ChannelIterator iterator = $this$consume$iv.iterator();
            InlineMarker.mark(0);
            Object hasNext = iterator.hasNext(continuation2);
            InlineMarker.mark(1);
            if (!((Boolean) hasNext).booleanValue()) {
                i = 3;
                InlineMarker.finallyStart(3);
            } else {
                maxElem = iterator.next();
                Comparable maxValue = (Comparable) function1.invoke(maxElem);
                while (true) {
                    InlineMarker.mark(0);
                    Object hasNext2 = iterator.hasNext(continuation2);
                    InlineMarker.mark(1);
                    if (!((Boolean) hasNext2).booleanValue()) {
                        break;
                    }
                    Object e = iterator.next();
                    Comparable v = (Comparable) function1.invoke(e);
                    if (maxValue.compareTo(v) < 0) {
                        maxElem = e;
                        maxValue = v;
                    }
                }
                i = 2;
                InlineMarker.finallyStart(2);
            }
            ChannelsKt.cancelConsumed($this$consume$iv, th);
            InlineMarker.finallyEnd(i);
            return maxElem;
        } catch (Throwable e$iv) {
            Throwable th2 = e$iv;
            InlineMarker.finallyStart(1);
            ChannelsKt.cancelConsumed($this$consume$iv, cause$iv);
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v8, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v14, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v11, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v18, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x009b  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00d4  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00d8 A[SYNTHETIC, Splitter:B:42:0x00d8] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x010f A[Catch:{ all -> 0x0128 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object maxWith(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r17, java.util.Comparator<? super E> r18, kotlin.coroutines.Continuation<? super E> r19) {
        /*
            r1 = r19
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$maxWith$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$maxWith$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$maxWith$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$maxWith$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$maxWith$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 2
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x009b
            r8 = 0
            if (r4 == r6) goto L_0x006b
            if (r4 != r5) goto L_0x0063
            r4 = r7
            r6 = r8
            r9 = r7
            r10 = r7
            r11 = r7
            java.lang.Object r10 = r2.L$6
            java.lang.Object r12 = r2.L$5
            r9 = r12
            kotlinx.coroutines.channels.ChannelIterator r9 = (kotlinx.coroutines.channels.ChannelIterator) r9
            java.lang.Object r12 = r2.L$4
            r4 = r12
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r12 = r2.L$3
            r7 = r12
            java.lang.Throwable r7 = (java.lang.Throwable) r7
            java.lang.Object r12 = r2.L$2
            r11 = r12
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r12 = r2.L$1
            java.util.Comparator r12 = (java.util.Comparator) r12
            java.lang.Object r13 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x005f }
            r14 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r4
            r4 = r3
            goto L_0x0107
        L_0x005f:
            r0 = move-exception
            r14 = r12
            goto L_0x0140
        L_0x0063:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x006b:
            r4 = r7
            r6 = r8
            r9 = r7
            r10 = r7
            r11 = r7
            java.lang.Object r12 = r2.L$5
            r9 = r12
            kotlinx.coroutines.channels.ChannelIterator r9 = (kotlinx.coroutines.channels.ChannelIterator) r9
            java.lang.Object r12 = r2.L$4
            r4 = r12
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r12 = r2.L$3
            r11 = r12
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            java.lang.Object r12 = r2.L$2
            r10 = r12
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r12 = r2.L$1
            java.util.Comparator r12 = (java.util.Comparator) r12
            java.lang.Object r13 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0095 }
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r6
            r6 = r3
            goto L_0x00cc
        L_0x0095:
            r0 = move-exception
            r7 = r11
            r14 = r12
            r11 = r10
            goto L_0x0140
        L_0x009b:
            kotlin.ResultKt.throwOnFailure(r3)
            r11 = r17
            r4 = 0
            r8 = r7
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            r9 = r11
            r10 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r9.iterator()     // Catch:{ all -> 0x0139 }
            r13 = r17
            r2.L$0 = r13     // Catch:{ all -> 0x0137 }
            r14 = r18
            r2.L$1 = r14     // Catch:{ all -> 0x0135 }
            r2.L$2 = r11     // Catch:{ all -> 0x0135 }
            r2.L$3 = r8     // Catch:{ all -> 0x0135 }
            r2.L$4 = r9     // Catch:{ all -> 0x0135 }
            r2.L$5 = r12     // Catch:{ all -> 0x0135 }
            r2.label = r6     // Catch:{ all -> 0x0135 }
            java.lang.Object r6 = r12.hasNext(r2)     // Catch:{ all -> 0x0135 }
            if (r6 != r0) goto L_0x00c4
            return r0
        L_0x00c4:
            r16 = r8
            r8 = r4
            r4 = r9
            r9 = r12
            r12 = r11
            r11 = r16
        L_0x00cc:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch:{ all -> 0x0130 }
            boolean r6 = r6.booleanValue()     // Catch:{ all -> 0x0130 }
            if (r6 != 0) goto L_0x00d8
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r11)
            return r7
        L_0x00d8:
            java.lang.Object r6 = r9.next()     // Catch:{ all -> 0x0130 }
            r7 = r11
            r11 = r12
            r16 = r10
            r10 = r6
            r6 = r8
            r8 = r16
        L_0x00e4:
            r2.L$0 = r13     // Catch:{ all -> 0x012e }
            r2.L$1 = r14     // Catch:{ all -> 0x012e }
            r2.L$2 = r11     // Catch:{ all -> 0x012e }
            r2.L$3 = r7     // Catch:{ all -> 0x012e }
            r2.L$4 = r4     // Catch:{ all -> 0x012e }
            r2.L$5 = r9     // Catch:{ all -> 0x012e }
            r2.L$6 = r10     // Catch:{ all -> 0x012e }
            r2.label = r5     // Catch:{ all -> 0x012e }
            java.lang.Object r12 = r9.hasNext(r2)     // Catch:{ all -> 0x012e }
            if (r12 != r0) goto L_0x00fb
            return r0
        L_0x00fb:
            r16 = r4
            r4 = r3
            r3 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r16
        L_0x0107:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0128 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0128 }
            if (r3 == 0) goto L_0x0123
            java.lang.Object r3 = r10.next()     // Catch:{ all -> 0x0128 }
            int r15 = r14.compare(r11, r3)     // Catch:{ all -> 0x0128 }
            if (r15 >= 0) goto L_0x011a
            r11 = r3
        L_0x011a:
            r3 = r4
            r4 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            goto L_0x00e4
        L_0x0123:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r8)
            return r11
        L_0x0128:
            r0 = move-exception
            r3 = r4
            r6 = r7
            r7 = r8
            r11 = r12
            goto L_0x0140
        L_0x012e:
            r0 = move-exception
            goto L_0x0140
        L_0x0130:
            r0 = move-exception
            r6 = r8
            r7 = r11
            r11 = r12
            goto L_0x0140
        L_0x0135:
            r0 = move-exception
            goto L_0x013e
        L_0x0137:
            r0 = move-exception
            goto L_0x013c
        L_0x0139:
            r0 = move-exception
            r13 = r17
        L_0x013c:
            r14 = r18
        L_0x013e:
            r6 = r4
            r7 = r8
        L_0x0140:
            r4 = r0
            throw r0     // Catch:{ all -> 0x0143 }
        L_0x0143:
            r0 = move-exception
            r5 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.maxWith(kotlinx.coroutines.channels.ReceiveChannel, java.util.Comparator, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v17, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v21, resolved type: java.lang.Comparable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v14, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x009d  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00d4  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00df A[SYNTHETIC, Splitter:B:42:0x00df] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010a  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x011f A[Catch:{ all -> 0x014f }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E, R extends java.lang.Comparable<? super R>> java.lang.Object minBy(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r19, kotlin.jvm.functions.Function1<? super E, ? extends R> r20, kotlin.coroutines.Continuation<? super E> r21) {
        /*
            r1 = r21
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$minBy$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$minBy$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$minBy$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$minBy$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$minBy$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 2
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x009d
            r8 = 0
            if (r4 == r6) goto L_0x006d
            if (r4 != r5) goto L_0x0065
            r4 = r7
            r9 = r7
            r10 = r8
            r11 = r7
            r12 = r7
            r13 = r7
            r14 = r8
            java.lang.Object r15 = r2.L$7
            r12 = r15
            java.lang.Comparable r12 = (java.lang.Comparable) r12
            java.lang.Object r13 = r2.L$6
            java.lang.Object r15 = r2.L$5
            r11 = r15
            kotlinx.coroutines.channels.ChannelIterator r11 = (kotlinx.coroutines.channels.ChannelIterator) r11
            java.lang.Object r15 = r2.L$4
            r7 = r15
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r15 = r2.L$3
            r4 = r15
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            java.lang.Object r15 = r2.L$2
            r9 = r15
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            java.lang.Object r5 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0060 }
            r6 = r5
            r5 = r4
            r4 = r3
            goto L_0x0117
        L_0x0060:
            r0 = move-exception
            r13 = r5
            r12 = r15
            goto L_0x0167
        L_0x0065:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x006d:
            r4 = r7
            r5 = r7
            r9 = r8
            r10 = r7
            r14 = r8
            r11 = r7
            java.lang.Object r12 = r2.L$5
            r10 = r12
            kotlinx.coroutines.channels.ChannelIterator r10 = (kotlinx.coroutines.channels.ChannelIterator) r10
            java.lang.Object r12 = r2.L$4
            r11 = r12
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r12 = r2.L$3
            r4 = r12
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            java.lang.Object r12 = r2.L$2
            r5 = r12
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r12 = r2.L$1
            kotlin.jvm.functions.Function1 r12 = (kotlin.jvm.functions.Function1) r12
            java.lang.Object r13 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0099 }
            r15 = r3
            r17 = r9
            r9 = r5
            r5 = r17
            goto L_0x00cc
        L_0x0099:
            r0 = move-exception
            r9 = r5
            goto L_0x0167
        L_0x009d:
            kotlin.ResultKt.throwOnFailure(r3)
            r14 = 0
            r9 = r19
            r8 = 0
            r4 = r7
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            r11 = r9
            r5 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r11.iterator()     // Catch:{ all -> 0x015d }
            r12 = r19
            r2.L$0 = r12     // Catch:{ all -> 0x015b }
            r13 = r20
            r2.L$1 = r13     // Catch:{ all -> 0x0159 }
            r2.L$2 = r9     // Catch:{ all -> 0x0159 }
            r2.L$3 = r4     // Catch:{ all -> 0x0159 }
            r2.L$4 = r11     // Catch:{ all -> 0x0159 }
            r2.L$5 = r10     // Catch:{ all -> 0x0159 }
            r2.label = r6     // Catch:{ all -> 0x0159 }
            java.lang.Object r15 = r10.hasNext(r2)     // Catch:{ all -> 0x0159 }
            if (r15 != r0) goto L_0x00c7
            return r0
        L_0x00c7:
            r17 = r13
            r13 = r12
            r12 = r17
        L_0x00cc:
            java.lang.Boolean r15 = (java.lang.Boolean) r15     // Catch:{ all -> 0x0157 }
            boolean r15 = r15.booleanValue()     // Catch:{ all -> 0x0157 }
            if (r15 != 0) goto L_0x00df
            r0 = 3
            kotlin.jvm.internal.InlineMarker.finallyStart(r0)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r4)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r0)
            return r7
        L_0x00df:
            java.lang.Object r7 = r10.next()     // Catch:{ all -> 0x0157 }
            java.lang.Object r15 = r12.invoke(r7)     // Catch:{ all -> 0x0157 }
            java.lang.Comparable r15 = (java.lang.Comparable) r15     // Catch:{ all -> 0x0157 }
            r17 = r10
            r10 = r5
            r5 = r7
            r7 = r11
            r11 = r17
        L_0x00f0:
            r2.L$0 = r13     // Catch:{ all -> 0x0155 }
            r2.L$1 = r12     // Catch:{ all -> 0x0155 }
            r2.L$2 = r9     // Catch:{ all -> 0x0155 }
            r2.L$3 = r4     // Catch:{ all -> 0x0155 }
            r2.L$4 = r7     // Catch:{ all -> 0x0155 }
            r2.L$5 = r11     // Catch:{ all -> 0x0155 }
            r2.L$6 = r5     // Catch:{ all -> 0x0155 }
            r2.L$7 = r15     // Catch:{ all -> 0x0155 }
            r6 = 2
            r2.label = r6     // Catch:{ all -> 0x0155 }
            java.lang.Object r6 = r11.hasNext(r2)     // Catch:{ all -> 0x0155 }
            if (r6 != r0) goto L_0x010a
            return r0
        L_0x010a:
            r17 = r4
            r4 = r3
            r3 = r6
            r6 = r13
            r13 = r5
            r5 = r17
            r18 = r15
            r15 = r12
            r12 = r18
        L_0x0117:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x014f }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x014f }
            if (r3 == 0) goto L_0x0144
            java.lang.Object r3 = r11.next()     // Catch:{ all -> 0x014f }
            java.lang.Object r16 = r15.invoke(r3)     // Catch:{ all -> 0x014f }
            java.lang.Comparable r16 = (java.lang.Comparable) r16     // Catch:{ all -> 0x014f }
            r19 = r16
            r20 = r0
            r0 = r19
            int r16 = r12.compareTo(r0)     // Catch:{ all -> 0x014f }
            if (r16 <= 0) goto L_0x0137
            r13 = r3
            r12 = r0
        L_0x0137:
            r0 = r20
            r3 = r4
            r4 = r5
            r5 = r13
            r13 = r6
            r6 = 1
            r17 = r15
            r15 = r12
            r12 = r17
            goto L_0x00f0
        L_0x0144:
            r0 = 2
            kotlin.jvm.internal.InlineMarker.finallyStart(r0)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r0)
            return r13
        L_0x014f:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r13 = r6
            r12 = r15
            goto L_0x0167
        L_0x0155:
            r0 = move-exception
            goto L_0x0167
        L_0x0157:
            r0 = move-exception
            goto L_0x0167
        L_0x0159:
            r0 = move-exception
            goto L_0x0162
        L_0x015b:
            r0 = move-exception
            goto L_0x0160
        L_0x015d:
            r0 = move-exception
            r12 = r19
        L_0x0160:
            r13 = r20
        L_0x0162:
            r17 = r13
            r13 = r12
            r12 = r17
        L_0x0167:
            r4 = r0
            throw r0     // Catch:{ all -> 0x016a }
        L_0x016a:
            r0 = move-exception
            r5 = r0
            r6 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r6)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r4)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.minBy(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: finally extract failed */
    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object minBy$$forInline(ReceiveChannel $this$minBy, Function1 selector, Continuation continuation) {
        Throwable cause$iv;
        int i;
        Function1 function1 = selector;
        Continuation continuation2 = continuation;
        ReceiveChannel $this$consume$iv = $this$minBy;
        Object minElem = null;
        Throwable th = null;
        try {
            ChannelIterator iterator = $this$consume$iv.iterator();
            InlineMarker.mark(0);
            Object hasNext = iterator.hasNext(continuation2);
            InlineMarker.mark(1);
            if (!((Boolean) hasNext).booleanValue()) {
                i = 3;
                InlineMarker.finallyStart(3);
            } else {
                minElem = iterator.next();
                Comparable minValue = (Comparable) function1.invoke(minElem);
                while (true) {
                    InlineMarker.mark(0);
                    Object hasNext2 = iterator.hasNext(continuation2);
                    InlineMarker.mark(1);
                    if (!((Boolean) hasNext2).booleanValue()) {
                        break;
                    }
                    Object e = iterator.next();
                    Comparable v = (Comparable) function1.invoke(e);
                    if (minValue.compareTo(v) > 0) {
                        minElem = e;
                        minValue = v;
                    }
                }
                i = 2;
                InlineMarker.finallyStart(2);
            }
            ChannelsKt.cancelConsumed($this$consume$iv, th);
            InlineMarker.finallyEnd(i);
            return minElem;
        } catch (Throwable e$iv) {
            Throwable th2 = e$iv;
            InlineMarker.finallyStart(1);
            ChannelsKt.cancelConsumed($this$consume$iv, cause$iv);
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v8, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v14, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v11, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v13, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v10, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v18, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x009b  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00d4  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00d8 A[SYNTHETIC, Splitter:B:42:0x00d8] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x010f A[Catch:{ all -> 0x0128 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object minWith(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r17, java.util.Comparator<? super E> r18, kotlin.coroutines.Continuation<? super E> r19) {
        /*
            r1 = r19
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$minWith$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$minWith$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$minWith$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$minWith$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$minWith$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 2
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x009b
            r8 = 0
            if (r4 == r6) goto L_0x006b
            if (r4 != r5) goto L_0x0063
            r4 = r7
            r6 = r8
            r9 = r7
            r10 = r7
            r11 = r7
            java.lang.Object r10 = r2.L$6
            java.lang.Object r12 = r2.L$5
            r9 = r12
            kotlinx.coroutines.channels.ChannelIterator r9 = (kotlinx.coroutines.channels.ChannelIterator) r9
            java.lang.Object r12 = r2.L$4
            r4 = r12
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r12 = r2.L$3
            r7 = r12
            java.lang.Throwable r7 = (java.lang.Throwable) r7
            java.lang.Object r12 = r2.L$2
            r11 = r12
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r12 = r2.L$1
            java.util.Comparator r12 = (java.util.Comparator) r12
            java.lang.Object r13 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x005f }
            r14 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r4
            r4 = r3
            goto L_0x0107
        L_0x005f:
            r0 = move-exception
            r14 = r12
            goto L_0x0140
        L_0x0063:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x006b:
            r4 = r7
            r6 = r8
            r9 = r7
            r10 = r7
            r11 = r7
            java.lang.Object r12 = r2.L$5
            r9 = r12
            kotlinx.coroutines.channels.ChannelIterator r9 = (kotlinx.coroutines.channels.ChannelIterator) r9
            java.lang.Object r12 = r2.L$4
            r4 = r12
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r12 = r2.L$3
            r11 = r12
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            java.lang.Object r12 = r2.L$2
            r10 = r12
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r12 = r2.L$1
            java.util.Comparator r12 = (java.util.Comparator) r12
            java.lang.Object r13 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0095 }
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r6
            r6 = r3
            goto L_0x00cc
        L_0x0095:
            r0 = move-exception
            r7 = r11
            r14 = r12
            r11 = r10
            goto L_0x0140
        L_0x009b:
            kotlin.ResultKt.throwOnFailure(r3)
            r11 = r17
            r4 = 0
            r8 = r7
            java.lang.Throwable r8 = (java.lang.Throwable) r8
            r9 = r11
            r10 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r9.iterator()     // Catch:{ all -> 0x0139 }
            r13 = r17
            r2.L$0 = r13     // Catch:{ all -> 0x0137 }
            r14 = r18
            r2.L$1 = r14     // Catch:{ all -> 0x0135 }
            r2.L$2 = r11     // Catch:{ all -> 0x0135 }
            r2.L$3 = r8     // Catch:{ all -> 0x0135 }
            r2.L$4 = r9     // Catch:{ all -> 0x0135 }
            r2.L$5 = r12     // Catch:{ all -> 0x0135 }
            r2.label = r6     // Catch:{ all -> 0x0135 }
            java.lang.Object r6 = r12.hasNext(r2)     // Catch:{ all -> 0x0135 }
            if (r6 != r0) goto L_0x00c4
            return r0
        L_0x00c4:
            r16 = r8
            r8 = r4
            r4 = r9
            r9 = r12
            r12 = r11
            r11 = r16
        L_0x00cc:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch:{ all -> 0x0130 }
            boolean r6 = r6.booleanValue()     // Catch:{ all -> 0x0130 }
            if (r6 != 0) goto L_0x00d8
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r11)
            return r7
        L_0x00d8:
            java.lang.Object r6 = r9.next()     // Catch:{ all -> 0x0130 }
            r7 = r11
            r11 = r12
            r16 = r10
            r10 = r6
            r6 = r8
            r8 = r16
        L_0x00e4:
            r2.L$0 = r13     // Catch:{ all -> 0x012e }
            r2.L$1 = r14     // Catch:{ all -> 0x012e }
            r2.L$2 = r11     // Catch:{ all -> 0x012e }
            r2.L$3 = r7     // Catch:{ all -> 0x012e }
            r2.L$4 = r4     // Catch:{ all -> 0x012e }
            r2.L$5 = r9     // Catch:{ all -> 0x012e }
            r2.L$6 = r10     // Catch:{ all -> 0x012e }
            r2.label = r5     // Catch:{ all -> 0x012e }
            java.lang.Object r12 = r9.hasNext(r2)     // Catch:{ all -> 0x012e }
            if (r12 != r0) goto L_0x00fb
            return r0
        L_0x00fb:
            r16 = r4
            r4 = r3
            r3 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r16
        L_0x0107:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0128 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0128 }
            if (r3 == 0) goto L_0x0123
            java.lang.Object r3 = r10.next()     // Catch:{ all -> 0x0128 }
            int r15 = r14.compare(r11, r3)     // Catch:{ all -> 0x0128 }
            if (r15 <= 0) goto L_0x011a
            r11 = r3
        L_0x011a:
            r3 = r4
            r4 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            goto L_0x00e4
        L_0x0123:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r8)
            return r11
        L_0x0128:
            r0 = move-exception
            r3 = r4
            r6 = r7
            r7 = r8
            r11 = r12
            goto L_0x0140
        L_0x012e:
            r0 = move-exception
            goto L_0x0140
        L_0x0130:
            r0 = move-exception
            r6 = r8
            r7 = r11
            r11 = r12
            goto L_0x0140
        L_0x0135:
            r0 = move-exception
            goto L_0x013e
        L_0x0137:
            r0 = move-exception
            goto L_0x013c
        L_0x0139:
            r0 = move-exception
            r13 = r17
        L_0x013c:
            r14 = r18
        L_0x013e:
            r6 = r4
            r7 = r8
        L_0x0140:
            r4 = r0
            throw r0     // Catch:{ all -> 0x0143 }
        L_0x0143:
            r0 = move-exception
            r5 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.minWith(kotlinx.coroutines.channels.ReceiveChannel, java.util.Comparator, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0079 A[Catch:{ all -> 0x0045 }] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x007a A[Catch:{ all -> 0x0045 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object none(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r12, kotlin.coroutines.Continuation<? super java.lang.Boolean> r13) {
        /*
            boolean r0 = r13 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$none$1
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$none$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$none$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$none$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$none$1
            r0.<init>(r13)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            r5 = 0
            r6 = 0
            if (r3 == 0) goto L_0x004f
            if (r3 != r4) goto L_0x0047
            r2 = r6
            r3 = r5
            r7 = r6
            r8 = r5
            java.lang.Object r9 = r0.L$3
            r6 = r9
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r9 = r0.L$2
            r7 = r9
            java.lang.Throwable r7 = (java.lang.Throwable) r7
            java.lang.Object r9 = r0.L$1
            r2 = r9
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            java.lang.Object r9 = r0.L$0
            r12 = r9
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0045 }
            r10 = r1
            goto L_0x0071
        L_0x0045:
            r3 = move-exception
            goto L_0x0087
        L_0x0047:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x004f:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r12
            r8 = 0
            r7 = r6
            java.lang.Throwable r7 = (java.lang.Throwable) r7
            r6 = r3
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r6.iterator()     // Catch:{ all -> 0x0083 }
            r0.L$0 = r12     // Catch:{ all -> 0x0083 }
            r0.L$1 = r3     // Catch:{ all -> 0x0083 }
            r0.L$2 = r7     // Catch:{ all -> 0x0083 }
            r0.L$3 = r6     // Catch:{ all -> 0x0083 }
            r0.label = r4     // Catch:{ all -> 0x0083 }
            java.lang.Object r10 = r10.hasNext(r0)     // Catch:{ all -> 0x0083 }
            if (r10 != r2) goto L_0x006f
            return r2
        L_0x006f:
            r2 = r3
            r3 = r9
        L_0x0071:
            java.lang.Boolean r10 = (java.lang.Boolean) r10     // Catch:{ all -> 0x0045 }
            boolean r9 = r10.booleanValue()     // Catch:{ all -> 0x0045 }
            if (r9 != 0) goto L_0x007a
            goto L_0x007b
        L_0x007a:
            r4 = r5
        L_0x007b:
            java.lang.Boolean r4 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4)     // Catch:{ all -> 0x0045 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r7)
            return r4
        L_0x0083:
            r2 = move-exception
            r11 = r3
            r3 = r2
            r2 = r11
        L_0x0087:
            r4 = r3
            throw r3     // Catch:{ all -> 0x008a }
        L_0x008a:
            r3 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.none(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00c3 A[Catch:{ all -> 0x0108 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object none(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r20, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r21, kotlin.coroutines.Continuation<? super java.lang.Boolean> r22) {
        /*
            r1 = r22
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$none$3
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$none$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$none$3) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$none$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$none$3
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 0
            r7 = 1
            if (r4 == 0) goto L_0x0079
            if (r4 != r7) goto L_0x0071
            r4 = r6
            r8 = r6
            r9 = r6
            r10 = r5
            r11 = r5
            r12 = r5
            r13 = r5
            java.lang.Object r14 = r2.L$6
            kotlinx.coroutines.channels.ChannelIterator r14 = (kotlinx.coroutines.channels.ChannelIterator) r14
            java.lang.Object r15 = r2.L$5
            r6 = r15
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r15 = r2.L$4
            r9 = r15
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            java.lang.Object r15 = r2.L$3
            r4 = r15
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r15 = r2.L$2
            r8 = r15
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            java.lang.Object r5 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0065 }
            r7 = r4
            r4 = r1
            r1 = r5
            r5 = r2
            r2 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r6
            r6 = r3
            goto L_0x00bb
        L_0x0065:
            r0 = move-exception
            r6 = r4
            r4 = r2
            r2 = r15
            r19 = r3
            r3 = r1
            r1 = r5
            r5 = r19
            goto L_0x0123
        L_0x0071:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0079:
            kotlin.ResultKt.throwOnFailure(r3)
            r11 = 0
            r8 = r20
            r13 = 0
            r4 = r8
            r12 = 0
            r9 = r6
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            r5 = r4
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r5.iterator()     // Catch:{ all -> 0x011a }
            r15 = r10
            r14 = r13
            r10 = r9
            r13 = r12
            r9 = r8
            r12 = r11
            r8 = r5
            r11 = r6
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
            r1 = r20
            r2 = r21
        L_0x009c:
            r4.L$0 = r1     // Catch:{ all -> 0x0113 }
            r4.L$1 = r2     // Catch:{ all -> 0x0113 }
            r4.L$2 = r9     // Catch:{ all -> 0x0113 }
            r4.L$3 = r6     // Catch:{ all -> 0x0113 }
            r4.L$4 = r10     // Catch:{ all -> 0x0113 }
            r4.L$5 = r8     // Catch:{ all -> 0x0113 }
            r4.L$6 = r15     // Catch:{ all -> 0x0113 }
            r4.label = r7     // Catch:{ all -> 0x0113 }
            java.lang.Object r7 = r15.hasNext(r4)     // Catch:{ all -> 0x0113 }
            if (r7 != r0) goto L_0x00b3
            return r0
        L_0x00b3:
            r19 = r4
            r4 = r3
            r3 = r7
            r7 = r6
            r6 = r5
            r5 = r19
        L_0x00bb:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0108 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0108 }
            if (r3 == 0) goto L_0x00f6
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x0108 }
            r20 = r3
            r17 = 0
            r21 = r0
            r0 = r20
            java.lang.Object r18 = r2.invoke(r0)     // Catch:{ all -> 0x0108 }
            java.lang.Boolean r18 = (java.lang.Boolean) r18     // Catch:{ all -> 0x0108 }
            boolean r18 = r18.booleanValue()     // Catch:{ all -> 0x0108 }
            if (r18 == 0) goto L_0x00ec
            r16 = 0
            java.lang.Boolean r15 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r16)     // Catch:{ all -> 0x0108 }
            r0 = 2
            kotlin.jvm.internal.InlineMarker.finallyStart(r0)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r10)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r0)
            return r15
        L_0x00ec:
            r16 = 0
            r0 = r21
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = 1
            goto L_0x009c
        L_0x00f6:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0108 }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r10)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
            return r0
        L_0x0108:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r8 = r9
            r9 = r10
            r11 = r12
            r12 = r13
            r13 = r14
            goto L_0x0123
        L_0x0113:
            r0 = move-exception
            r8 = r9
            r9 = r10
            r11 = r12
            r12 = r13
            r13 = r14
            goto L_0x0123
        L_0x011a:
            r0 = move-exception
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
            r1 = r20
            r2 = r21
        L_0x0123:
            r7 = r0
            throw r0     // Catch:{ all -> 0x0126 }
        L_0x0126:
            r0 = move-exception
            r9 = r0
            r10 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r7)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.none(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object none$$forInline(ReceiveChannel $this$none, Function1 predicate, Continuation continuation) {
        ReceiveChannel $this$consume$iv$iv = $this$none;
        Throwable cause$iv$iv = null;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            do {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    try {
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return true;
                }
            } while (!((Boolean) predicate.invoke(it.next())).booleanValue());
            InlineMarker.finallyStart(2);
            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
            InlineMarker.finallyEnd(2);
            return false;
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v0, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v1, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v1, resolved type: ? extends E} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v2, resolved type: ? extends E} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v1, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v2, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: ? extends E} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v3, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v6, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v14, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00c6 A[Catch:{ all -> 0x008c }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00e1  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00f5 A[Catch:{ all -> 0x010f }] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0117 A[SYNTHETIC, Splitter:B:55:0x0117] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <S, E extends S> java.lang.Object reduce(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r18, kotlin.jvm.functions.Function2<? super S, ? super E, ? extends S> r19, kotlin.coroutines.Continuation<? super S> r20) {
        /*
            r1 = r20
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$reduce$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$reduce$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$reduce$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$reduce$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$reduce$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 2
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x008f
            r8 = 0
            if (r4 == r6) goto L_0x0066
            if (r4 != r5) goto L_0x005e
            r4 = r7
            r9 = r7
            r10 = r8
            r11 = r7
            r12 = r7
            r13 = r8
            java.lang.Object r12 = r2.L$6
            java.lang.Object r14 = r2.L$5
            r11 = r14
            kotlinx.coroutines.channels.ChannelIterator r11 = (kotlinx.coroutines.channels.ChannelIterator) r11
            java.lang.Object r14 = r2.L$4
            r7 = r14
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r14 = r2.L$3
            r4 = r14
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            java.lang.Object r14 = r2.L$2
            r9 = r14
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r14 = r2.L$1
            kotlin.jvm.functions.Function2 r14 = (kotlin.jvm.functions.Function2) r14
            java.lang.Object r15 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r15 = (kotlinx.coroutines.channels.ReceiveChannel) r15
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0059 }
            r6 = r4
            r4 = r3
            goto L_0x00ed
        L_0x0059:
            r0 = move-exception
            r12 = r14
            r14 = r15
            goto L_0x012f
        L_0x005e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0066:
            r4 = r7
            r9 = r7
            r10 = r8
            r11 = r7
            r13 = r8
            java.lang.Object r12 = r2.L$5
            r11 = r12
            kotlinx.coroutines.channels.ChannelIterator r11 = (kotlinx.coroutines.channels.ChannelIterator) r11
            java.lang.Object r12 = r2.L$4
            r7 = r12
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r12 = r2.L$3
            r4 = r12
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            java.lang.Object r12 = r2.L$2
            r9 = r12
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r12 = r2.L$1
            kotlin.jvm.functions.Function2 r12 = (kotlin.jvm.functions.Function2) r12
            java.lang.Object r14 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r14 = (kotlinx.coroutines.channels.ReceiveChannel) r14
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x008c }
            r15 = r3
            goto L_0x00be
        L_0x008c:
            r0 = move-exception
            goto L_0x012f
        L_0x008f:
            kotlin.ResultKt.throwOnFailure(r3)
            r13 = 0
            r9 = r18
            r8 = 0
            r4 = r7
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            r7 = r9
            r10 = 0
            kotlinx.coroutines.channels.ChannelIterator r11 = r7.iterator()     // Catch:{ all -> 0x0125 }
            r12 = r18
            r2.L$0 = r12     // Catch:{ all -> 0x0123 }
            r14 = r19
            r2.L$1 = r14     // Catch:{ all -> 0x0121 }
            r2.L$2 = r9     // Catch:{ all -> 0x0121 }
            r2.L$3 = r4     // Catch:{ all -> 0x0121 }
            r2.L$4 = r7     // Catch:{ all -> 0x0121 }
            r2.L$5 = r11     // Catch:{ all -> 0x0121 }
            r2.label = r6     // Catch:{ all -> 0x0121 }
            java.lang.Object r15 = r11.hasNext(r2)     // Catch:{ all -> 0x0121 }
            if (r15 != r0) goto L_0x00b9
            return r0
        L_0x00b9:
            r16 = r14
            r14 = r12
            r12 = r16
        L_0x00be:
            java.lang.Boolean r15 = (java.lang.Boolean) r15     // Catch:{ all -> 0x008c }
            boolean r15 = r15.booleanValue()     // Catch:{ all -> 0x008c }
            if (r15 == 0) goto L_0x0117
            java.lang.Object r15 = r11.next()     // Catch:{ all -> 0x008c }
        L_0x00ca:
            r2.L$0 = r14     // Catch:{ all -> 0x0115 }
            r2.L$1 = r12     // Catch:{ all -> 0x0115 }
            r2.L$2 = r9     // Catch:{ all -> 0x0115 }
            r2.L$3 = r4     // Catch:{ all -> 0x0115 }
            r2.L$4 = r7     // Catch:{ all -> 0x0115 }
            r2.L$5 = r11     // Catch:{ all -> 0x0115 }
            r2.L$6 = r15     // Catch:{ all -> 0x0115 }
            r2.label = r5     // Catch:{ all -> 0x0115 }
            java.lang.Object r6 = r11.hasNext(r2)     // Catch:{ all -> 0x0115 }
            if (r6 != r0) goto L_0x00e1
            return r0
        L_0x00e1:
            r16 = r4
            r4 = r3
            r3 = r6
            r6 = r16
            r17 = r14
            r14 = r12
            r12 = r15
            r15 = r17
        L_0x00ed:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x010f }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x010f }
            if (r3 == 0) goto L_0x0104
            java.lang.Object r3 = r11.next()     // Catch:{ all -> 0x010f }
            java.lang.Object r3 = r14.invoke(r12, r3)     // Catch:{ all -> 0x010f }
            r12 = r14
            r14 = r15
            r15 = r3
            r3 = r4
            r4 = r6
            r6 = 1
            goto L_0x00ca
        L_0x0104:
            kotlin.jvm.internal.InlineMarker.finallyStart(r5)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r6)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r5)
            return r12
        L_0x010f:
            r0 = move-exception
            r3 = r4
            r4 = r6
            r12 = r14
            r14 = r15
            goto L_0x012f
        L_0x0115:
            r0 = move-exception
            goto L_0x012f
        L_0x0117:
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x008c }
            java.lang.String r5 = "Empty channel can't be reduced."
            r0.<init>(r5)     // Catch:{ all -> 0x008c }
            java.lang.Throwable r0 = (java.lang.Throwable) r0     // Catch:{ all -> 0x008c }
            throw r0     // Catch:{ all -> 0x008c }
        L_0x0121:
            r0 = move-exception
            goto L_0x012a
        L_0x0123:
            r0 = move-exception
            goto L_0x0128
        L_0x0125:
            r0 = move-exception
            r12 = r18
        L_0x0128:
            r14 = r19
        L_0x012a:
            r16 = r14
            r14 = r12
            r12 = r16
        L_0x012f:
            r4 = r0
            throw r0     // Catch:{ all -> 0x0132 }
        L_0x0132:
            r0 = move-exception
            r5 = r0
            r6 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r6)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r4)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.reduce(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object reduce$$forInline(ReceiveChannel $this$reduce, Function2 operation, Continuation continuation) {
        Throwable cause$iv;
        ReceiveChannel $this$consume$iv = $this$reduce;
        Throwable th = null;
        try {
            ChannelIterator iterator = $this$consume$iv.iterator();
            InlineMarker.mark(0);
            Object hasNext = iterator.hasNext(continuation);
            InlineMarker.mark(1);
            if (((Boolean) hasNext).booleanValue()) {
                Object accumulator = iterator.next();
                while (true) {
                    InlineMarker.mark(0);
                    Object hasNext2 = iterator.hasNext(continuation);
                    InlineMarker.mark(1);
                    if (((Boolean) hasNext2).booleanValue()) {
                        accumulator = operation.invoke(accumulator, iterator.next());
                    } else {
                        InlineMarker.finallyStart(2);
                        ChannelsKt.cancelConsumed($this$consume$iv, th);
                        InlineMarker.finallyEnd(2);
                        return accumulator;
                    }
                }
            } else {
                throw new UnsupportedOperationException("Empty channel can't be reduced.");
            }
        } catch (Throwable e$iv) {
            InlineMarker.finallyStart(1);
            ChannelsKt.cancelConsumed($this$consume$iv, cause$iv);
            InlineMarker.finallyEnd(1);
            throw e$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v1, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v1, resolved type: ? extends E} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v2, resolved type: ? extends E} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v0, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v4, resolved type: ? extends E} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v2, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v17, resolved type: kotlinx.coroutines.channels.ChannelIterator<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v8, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v14, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00a3  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00d5 A[Catch:{ all -> 0x0145 }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010e A[Catch:{ all -> 0x0133 }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x013b A[SYNTHETIC, Splitter:B:56:0x013b] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <S, E extends S> java.lang.Object reduceIndexed(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r18, kotlin.jvm.functions.Function3<? super java.lang.Integer, ? super S, ? super E, ? extends S> r19, kotlin.coroutines.Continuation<? super S> r20) {
        /*
            r1 = r20
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$reduceIndexed$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$reduceIndexed$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$reduceIndexed$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$reduceIndexed$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$reduceIndexed$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 2
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x00a3
            r8 = 0
            if (r4 == r6) goto L_0x006a
            if (r4 != r5) goto L_0x0062
            r4 = r7
            r9 = r7
            r10 = r8
            r11 = r7
            r12 = r7
            r13 = r8
            r14 = r8
            java.lang.Object r12 = r2.L$6
            int r13 = r2.I$0
            java.lang.Object r15 = r2.L$5
            r11 = r15
            kotlinx.coroutines.channels.ChannelIterator r11 = (kotlinx.coroutines.channels.ChannelIterator) r11
            java.lang.Object r15 = r2.L$4
            r7 = r15
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r15 = r2.L$3
            r4 = r15
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            java.lang.Object r15 = r2.L$2
            r9 = r15
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function3 r15 = (kotlin.jvm.functions.Function3) r15
            java.lang.Object r5 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x005d }
            r6 = r5
            r5 = r4
            r4 = r3
            goto L_0x0106
        L_0x005d:
            r0 = move-exception
            r11 = r5
            r12 = r15
            goto L_0x0150
        L_0x0062:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x006a:
            r4 = r7
            r5 = r7
            r9 = r8
            r10 = r7
            r14 = r8
            java.lang.Object r11 = r2.L$5
            r10 = r11
            kotlinx.coroutines.channels.ChannelIterator r10 = (kotlinx.coroutines.channels.ChannelIterator) r10
            java.lang.Object r11 = r2.L$4
            r7 = r11
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r11 = r2.L$3
            r4 = r11
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            java.lang.Object r11 = r2.L$2
            r5 = r11
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r11 = r2.L$1
            kotlin.jvm.functions.Function3 r11 = (kotlin.jvm.functions.Function3) r11
            java.lang.Object r12 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x009a }
            r13 = r3
            r16 = r9
            r9 = r5
            r5 = r16
            r17 = r12
            r12 = r11
            r11 = r17
            goto L_0x00cd
        L_0x009a:
            r0 = move-exception
            r9 = r5
            r16 = r12
            r12 = r11
            r11 = r16
            goto L_0x0150
        L_0x00a3:
            kotlin.ResultKt.throwOnFailure(r3)
            r14 = 0
            r9 = r18
            r8 = 0
            r4 = r7
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            r7 = r9
            r5 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r7.iterator()     // Catch:{ all -> 0x014b }
            r11 = r18
            r2.L$0 = r11     // Catch:{ all -> 0x0149 }
            r12 = r19
            r2.L$1 = r12     // Catch:{ all -> 0x0147 }
            r2.L$2 = r9     // Catch:{ all -> 0x0147 }
            r2.L$3 = r4     // Catch:{ all -> 0x0147 }
            r2.L$4 = r7     // Catch:{ all -> 0x0147 }
            r2.L$5 = r10     // Catch:{ all -> 0x0147 }
            r2.label = r6     // Catch:{ all -> 0x0147 }
            java.lang.Object r13 = r10.hasNext(r2)     // Catch:{ all -> 0x0147 }
            if (r13 != r0) goto L_0x00cd
            return r0
        L_0x00cd:
            java.lang.Boolean r13 = (java.lang.Boolean) r13     // Catch:{ all -> 0x0145 }
            boolean r13 = r13.booleanValue()     // Catch:{ all -> 0x0145 }
            if (r13 == 0) goto L_0x013b
            r13 = 1
            java.lang.Object r15 = r10.next()     // Catch:{ all -> 0x0145 }
            r16 = r10
            r10 = r5
            r5 = r16
        L_0x00df:
            r2.L$0 = r11     // Catch:{ all -> 0x0139 }
            r2.L$1 = r12     // Catch:{ all -> 0x0139 }
            r2.L$2 = r9     // Catch:{ all -> 0x0139 }
            r2.L$3 = r4     // Catch:{ all -> 0x0139 }
            r2.L$4 = r7     // Catch:{ all -> 0x0139 }
            r2.L$5 = r5     // Catch:{ all -> 0x0139 }
            r2.I$0 = r13     // Catch:{ all -> 0x0139 }
            r2.L$6 = r15     // Catch:{ all -> 0x0139 }
            r6 = 2
            r2.label = r6     // Catch:{ all -> 0x0139 }
            java.lang.Object r6 = r5.hasNext(r2)     // Catch:{ all -> 0x0139 }
            if (r6 != r0) goto L_0x00f9
            return r0
        L_0x00f9:
            r16 = r4
            r4 = r3
            r3 = r6
            r6 = r11
            r11 = r5
            r5 = r16
            r17 = r15
            r15 = r12
            r12 = r17
        L_0x0106:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0133 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0133 }
            if (r3 == 0) goto L_0x0128
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r13)     // Catch:{ all -> 0x0133 }
            int r13 = r13 + 1
            r18 = r0
            java.lang.Object r0 = r11.next()     // Catch:{ all -> 0x0133 }
            java.lang.Object r0 = r15.invoke(r3, r12, r0)     // Catch:{ all -> 0x0133 }
            r3 = r4
            r4 = r5
            r5 = r11
            r12 = r15
            r15 = r0
            r11 = r6
            r6 = 1
            r0 = r18
            goto L_0x00df
        L_0x0128:
            r0 = 2
            kotlin.jvm.internal.InlineMarker.finallyStart(r0)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r0)
            return r12
        L_0x0133:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r11 = r6
            r12 = r15
            goto L_0x0150
        L_0x0139:
            r0 = move-exception
            goto L_0x0150
        L_0x013b:
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x0145 }
            java.lang.String r6 = "Empty channel can't be reduced."
            r0.<init>(r6)     // Catch:{ all -> 0x0145 }
            java.lang.Throwable r0 = (java.lang.Throwable) r0     // Catch:{ all -> 0x0145 }
            throw r0     // Catch:{ all -> 0x0145 }
        L_0x0145:
            r0 = move-exception
            goto L_0x0150
        L_0x0147:
            r0 = move-exception
            goto L_0x0150
        L_0x0149:
            r0 = move-exception
            goto L_0x014e
        L_0x014b:
            r0 = move-exception
            r11 = r18
        L_0x014e:
            r12 = r19
        L_0x0150:
            r4 = r0
            throw r0     // Catch:{ all -> 0x0153 }
        L_0x0153:
            r0 = move-exception
            r5 = r0
            r6 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r6)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r4)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.reduceIndexed(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function3, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object reduceIndexed$$forInline(ReceiveChannel $this$reduceIndexed, Function3 operation, Continuation continuation) {
        Throwable cause$iv;
        ReceiveChannel $this$consume$iv = $this$reduceIndexed;
        Throwable th = null;
        try {
            ChannelIterator iterator = $this$consume$iv.iterator();
            InlineMarker.mark(0);
            Object hasNext = iterator.hasNext(continuation);
            InlineMarker.mark(1);
            if (((Boolean) hasNext).booleanValue()) {
                int index = 1;
                Object accumulator = iterator.next();
                while (true) {
                    InlineMarker.mark(0);
                    Object hasNext2 = iterator.hasNext(continuation);
                    InlineMarker.mark(1);
                    if (((Boolean) hasNext2).booleanValue()) {
                        Integer valueOf = Integer.valueOf(index);
                        index++;
                        accumulator = operation.invoke(valueOf, accumulator, iterator.next());
                    } else {
                        InlineMarker.finallyStart(2);
                        ChannelsKt.cancelConsumed($this$consume$iv, th);
                        InlineMarker.finallyEnd(2);
                        return accumulator;
                    }
                }
            } else {
                throw new UnsupportedOperationException("Empty channel can't be reduced.");
            }
        } catch (Throwable e$iv) {
            InlineMarker.finallyStart(1);
            ChannelsKt.cancelConsumed($this$consume$iv, cause$iv);
            InlineMarker.finallyEnd(1);
            throw e$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v11, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v12, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v10, resolved type: kotlin.jvm.internal.Ref$IntRef} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00e1 A[Catch:{ all -> 0x0130 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object sumBy(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r19, kotlin.jvm.functions.Function1<? super E, java.lang.Integer> r20, kotlin.coroutines.Continuation<? super java.lang.Integer> r21) {
        /*
            r1 = r21
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$sumBy$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$sumBy$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$sumBy$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$sumBy$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$sumBy$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            r7 = 0
            if (r4 == 0) goto L_0x0081
            if (r4 != r6) goto L_0x0079
            r4 = r5
            r8 = r7
            r9 = r5
            r10 = r7
            r11 = r5
            r12 = r7
            r13 = r7
            java.lang.Object r14 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r14 = (kotlinx.coroutines.channels.ChannelIterator) r14
            java.lang.Object r15 = r2.L$6
            r12 = r15
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r15 = r2.L$5
            r10 = r15
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r15 = r2.L$4
            r13 = r15
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            java.lang.Object r15 = r2.L$3
            r7 = r15
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r15 = r2.L$2
            r8 = r15
            kotlin.jvm.internal.Ref$IntRef r8 = (kotlin.jvm.internal.Ref.IntRef) r8
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            java.lang.Object r6 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006c }
            r16 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r5
            r5 = r2
            r2 = r15
            r15 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r4
            r4 = r1
            r1 = r6
            r6 = r3
            goto L_0x00d9
        L_0x006c:
            r0 = move-exception
            r11 = r9
            r12 = r10
            r9 = r7
            r10 = r8
            r7 = r4
            r8 = r5
            r4 = r2
            r5 = r3
            r2 = r15
            r3 = r1
            goto L_0x0160
        L_0x0079:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0081:
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = 0
            kotlin.jvm.internal.Ref$IntRef r6 = new kotlin.jvm.internal.Ref$IntRef
            r6.<init>()
            r6.element = r5
            r8 = r6
            r5 = r19
            r6 = 0
            r13 = r5
            r9 = 0
            r10 = r7
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r7 = r13
            r11 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r7.iterator()     // Catch:{ all -> 0x0152 }
            r15 = r12
            r14 = r13
            r13 = r7
            r12 = r11
            r7 = r4
            r11 = r10
            r4 = r2
            r10 = r9
            r2 = r20
            r9 = r8
            r8 = r5
            r5 = r3
            r3 = r1
            r1 = r19
        L_0x00ac:
            r4.L$0 = r1     // Catch:{ all -> 0x0145 }
            r4.L$1 = r2     // Catch:{ all -> 0x0145 }
            r4.L$2 = r9     // Catch:{ all -> 0x0145 }
            r4.L$3 = r8     // Catch:{ all -> 0x0145 }
            r4.L$4 = r14     // Catch:{ all -> 0x0145 }
            r4.L$5 = r11     // Catch:{ all -> 0x0145 }
            r4.L$6 = r13     // Catch:{ all -> 0x0145 }
            r4.L$7 = r15     // Catch:{ all -> 0x0145 }
            r19 = r1
            r1 = 1
            r4.label = r1     // Catch:{ all -> 0x013a }
            java.lang.Object r1 = r15.hasNext(r4)     // Catch:{ all -> 0x013a }
            if (r1 != r0) goto L_0x00c8
            return r0
        L_0x00c8:
            r16 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r19
        L_0x00d9:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0130 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0130 }
            if (r3 == 0) goto L_0x0112
            java.lang.Object r3 = r16.next()     // Catch:{ all -> 0x0130 }
            r19 = r3
            r17 = 0
            r20 = r0
            int r0 = r10.element     // Catch:{ all -> 0x0130 }
            r21 = r1
            r1 = r19
            java.lang.Object r18 = r2.invoke(r1)     // Catch:{ all -> 0x0128 }
            java.lang.Number r18 = (java.lang.Number) r18     // Catch:{ all -> 0x0128 }
            int r18 = r18.intValue()     // Catch:{ all -> 0x0128 }
            int r0 = r0 + r18
            r10.element = r0     // Catch:{ all -> 0x0128 }
            r0 = r20
            r1 = r21
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r15 = r16
            goto L_0x00ac
        L_0x0112:
            r21 = r1
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0128 }
            r1 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            int r0 = r10.element
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r0)
            return r0
        L_0x0128:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r5 = r6
            r13 = r15
            r6 = r21
            goto L_0x0160
        L_0x0130:
            r0 = move-exception
            r21 = r1
            r3 = r4
            r4 = r5
            r5 = r6
            r13 = r15
            r6 = r21
            goto L_0x0160
        L_0x013a:
            r0 = move-exception
            r12 = r11
            r13 = r14
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r19
            goto L_0x0160
        L_0x0145:
            r0 = move-exception
            r19 = r1
            r12 = r11
            r13 = r14
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r19
            goto L_0x0160
        L_0x0152:
            r0 = move-exception
            r7 = r6
            r11 = r9
            r12 = r10
            r6 = r19
            r9 = r5
            r10 = r8
            r5 = r3
            r8 = r4
            r3 = r1
            r4 = r2
            r2 = r20
        L_0x0160:
            r1 = r0
            throw r0     // Catch:{ all -> 0x0163 }
        L_0x0163:
            r0 = move-exception
            r12 = r0
            r14 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r14)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r13, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r14)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.sumBy(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object sumBy$$forInline(ReceiveChannel $this$sumBy, Function1 selector, Continuation continuation) {
        int sum = 0;
        ReceiveChannel $this$consume$iv$iv = $this$sumBy;
        Throwable cause$iv$iv = null;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    try {
                        sum += ((Number) selector.invoke(it.next())).intValue();
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = selector;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return Integer.valueOf(sum);
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = selector;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v11, resolved type: java.lang.Throwable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v12, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v10, resolved type: kotlin.jvm.internal.Ref$DoubleRef} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00e7 A[Catch:{ all -> 0x0137 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object sumByDouble(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r22, kotlin.jvm.functions.Function1<? super E, java.lang.Double> r23, kotlin.coroutines.Continuation<? super java.lang.Double> r24) {
        /*
            r1 = r24
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$sumByDouble$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$sumByDouble$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$sumByDouble$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$sumByDouble$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$sumByDouble$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x0084
            if (r4 != r5) goto L_0x007c
            r4 = 0
            r7 = r4
            r8 = r6
            r9 = r4
            r10 = r6
            r11 = r4
            r12 = r6
            r13 = r6
            java.lang.Object r14 = r2.L$7
            kotlinx.coroutines.channels.ChannelIterator r14 = (kotlinx.coroutines.channels.ChannelIterator) r14
            java.lang.Object r15 = r2.L$6
            r12 = r15
            kotlinx.coroutines.channels.ReceiveChannel r12 = (kotlinx.coroutines.channels.ReceiveChannel) r12
            java.lang.Object r15 = r2.L$5
            r10 = r15
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            java.lang.Object r15 = r2.L$4
            r13 = r15
            kotlinx.coroutines.channels.ReceiveChannel r13 = (kotlinx.coroutines.channels.ReceiveChannel) r13
            java.lang.Object r15 = r2.L$3
            r6 = r15
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r15 = r2.L$2
            r8 = r15
            kotlin.jvm.internal.Ref$DoubleRef r8 = (kotlin.jvm.internal.Ref.DoubleRef) r8
            java.lang.Object r15 = r2.L$1
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            java.lang.Object r5 = r2.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006f }
            r16 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r6
            r6 = r3
            r21 = r4
            r4 = r1
            r1 = r5
            r5 = r2
            r2 = r15
            r15 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r21
            goto L_0x00df
        L_0x006f:
            r0 = move-exception
            r11 = r9
            r12 = r10
            r9 = r7
            r10 = r8
            r7 = r4
            r8 = r6
            r4 = r2
            r6 = r3
            r2 = r15
            r3 = r1
            goto L_0x0168
        L_0x007c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0084:
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = 0
            kotlin.jvm.internal.Ref$DoubleRef r5 = new kotlin.jvm.internal.Ref$DoubleRef
            r5.<init>()
            r7 = 0
            r5.element = r7
            r8 = r5
            r5 = r22
            r7 = 0
            r13 = r5
            r9 = 0
            r10 = r6
            java.lang.Throwable r10 = (java.lang.Throwable) r10
            r6 = r13
            r11 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r6.iterator()     // Catch:{ all -> 0x015a }
            r15 = r12
            r14 = r13
            r13 = r6
            r12 = r11
            r6 = r4
            r11 = r10
            r4 = r2
            r10 = r9
            r2 = r23
            r9 = r8
            r8 = r7
            r7 = r5
            r5 = r3
            r3 = r1
            r1 = r22
        L_0x00b2:
            r4.L$0 = r1     // Catch:{ all -> 0x014c }
            r4.L$1 = r2     // Catch:{ all -> 0x014c }
            r4.L$2 = r9     // Catch:{ all -> 0x014c }
            r4.L$3 = r7     // Catch:{ all -> 0x014c }
            r4.L$4 = r14     // Catch:{ all -> 0x014c }
            r4.L$5 = r11     // Catch:{ all -> 0x014c }
            r4.L$6 = r13     // Catch:{ all -> 0x014c }
            r4.L$7 = r15     // Catch:{ all -> 0x014c }
            r22 = r1
            r1 = 1
            r4.label = r1     // Catch:{ all -> 0x0140 }
            java.lang.Object r1 = r15.hasNext(r4)     // Catch:{ all -> 0x0140 }
            if (r1 != r0) goto L_0x00ce
            return r0
        L_0x00ce:
            r16 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r22
        L_0x00df:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0137 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0137 }
            if (r3 == 0) goto L_0x011a
            java.lang.Object r3 = r16.next()     // Catch:{ all -> 0x0137 }
            r22 = r3
            r17 = 0
            r23 = r0
            r24 = r1
            double r0 = r10.element     // Catch:{ all -> 0x0130 }
            r18 = r3
            r3 = r22
            java.lang.Object r19 = r2.invoke(r3)     // Catch:{ all -> 0x0130 }
            java.lang.Number r19 = (java.lang.Number) r19     // Catch:{ all -> 0x0130 }
            double r19 = r19.doubleValue()     // Catch:{ all -> 0x0130 }
            double r0 = r0 + r19
            r10.element = r0     // Catch:{ all -> 0x0130 }
            r0 = r23
            r1 = r24
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r15 = r16
            goto L_0x00b2
        L_0x011a:
            r24 = r1
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0130 }
            r1 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r15, r12)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            double r0 = r10.element
            java.lang.Double r0 = kotlin.coroutines.jvm.internal.Boxing.boxDouble(r0)
            return r0
        L_0x0130:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r13 = r15
            r5 = r24
            goto L_0x0168
        L_0x0137:
            r0 = move-exception
            r24 = r1
            r3 = r4
            r4 = r5
            r13 = r15
            r5 = r24
            goto L_0x0168
        L_0x0140:
            r0 = move-exception
            r12 = r11
            r13 = r14
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r22
            goto L_0x0168
        L_0x014c:
            r0 = move-exception
            r22 = r1
            r12 = r11
            r13 = r14
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r22
            goto L_0x0168
        L_0x015a:
            r0 = move-exception
            r6 = r3
            r11 = r9
            r12 = r10
            r3 = r1
            r9 = r7
            r10 = r8
            r7 = r4
            r8 = r5
            r5 = r22
            r4 = r2
            r2 = r23
        L_0x0168:
            r1 = r0
            throw r0     // Catch:{ all -> 0x016b }
        L_0x016b:
            r0 = move-exception
            r12 = r0
            r14 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r14)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r13, r1)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r14)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.sumByDouble(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object sumByDouble$$forInline(ReceiveChannel $this$sumByDouble, Function1 selector, Continuation continuation) {
        double sum = 0.0d;
        ReceiveChannel $this$consume$iv$iv = $this$sumByDouble;
        Throwable cause$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    try {
                        sum += ((Number) selector.invoke(it.next())).doubleValue();
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = selector;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return Double.valueOf(sum);
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = selector;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> requireNoNulls(ReceiveChannel<? extends E> $this$requireNoNulls) {
        Intrinsics.checkParameterIsNotNull($this$requireNoNulls, "$this$requireNoNulls");
        return map$default($this$requireNoNulls, (CoroutineContext) null, new ChannelsKt__Channels_commonKt$requireNoNulls$1($this$requireNoNulls, (Continuation) null), 1, (Object) null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: java.util.ArrayList} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v8, resolved type: java.util.ArrayList} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: kotlinx.coroutines.channels.ReceiveChannel<? extends E>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00e1  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00ef A[Catch:{ all -> 0x012c }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object partition(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r21, kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r22, kotlin.coroutines.Continuation<? super kotlin.Pair<? extends java.util.List<? extends E>, ? extends java.util.List<? extends E>>> r23) {
        /*
            r1 = r23
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$partition$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$partition$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$partition$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$partition$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$partition$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x008b
            if (r4 != r5) goto L_0x0083
            r4 = r6
            r7 = r6
            r8 = 0
            r9 = r8
            r10 = r6
            r11 = r8
            r12 = r6
            r13 = r6
            r14 = r8
            java.lang.Object r15 = r2.L$8
            kotlinx.coroutines.channels.ChannelIterator r15 = (kotlinx.coroutines.channels.ChannelIterator) r15
            java.lang.Object r5 = r2.L$7
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            java.lang.Object r12 = r2.L$6
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            java.lang.Object r13 = r2.L$5
            r10 = r13
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r13 = r2.L$4
            r4 = r13
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r13 = r2.L$3
            r6 = r13
            java.util.ArrayList r6 = (java.util.ArrayList) r6
            java.lang.Object r13 = r2.L$2
            r7 = r13
            java.util.ArrayList r7 = (java.util.ArrayList) r7
            java.lang.Object r13 = r2.L$1
            kotlin.jvm.functions.Function1 r13 = (kotlin.jvm.functions.Function1) r13
            r16 = r0
            java.lang.Object r0 = r2.L$0
            r17 = r0
            kotlinx.coroutines.channels.ReceiveChannel r17 = (kotlinx.coroutines.channels.ReceiveChannel) r17
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0076 }
            r0 = r16
            r16 = r17
            r17 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r6
            r6 = r4
            r4 = r2
            r2 = r13
            r13 = r11
            r11 = r9
            r9 = r7
            r7 = r5
            r5 = r3
            goto L_0x00e7
        L_0x0076:
            r0 = move-exception
            r15 = r14
            r16 = r17
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r6
            r6 = r4
            r4 = r2
            r2 = r13
            goto L_0x014f
        L_0x0083:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x008b:
            r16 = r0
            kotlin.ResultKt.throwOnFailure(r3)
            r14 = 0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r7 = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r4 = r0
            r5 = r21
            r8 = 0
            r10 = r5
            r11 = 0
            r12 = r6
            java.lang.Throwable r12 = (java.lang.Throwable) r12
            r0 = r10
            r6 = 0
            kotlinx.coroutines.channels.ChannelIterator r9 = r0.iterator()     // Catch:{ all -> 0x0143 }
            r13 = r11
            r15 = r14
            r11 = r6
            r14 = r12
            r6 = r5
            r12 = r10
            r5 = r3
            r10 = r8
            r3 = r9
            r8 = r4
            r9 = r7
            r7 = r0
            r4 = r2
            r0 = r16
            r2 = r22
            r20 = r1
            r1 = r21
            r21 = r20
        L_0x00c3:
            r4.L$0 = r1     // Catch:{ all -> 0x013a }
            r4.L$1 = r2     // Catch:{ all -> 0x013a }
            r4.L$2 = r9     // Catch:{ all -> 0x013a }
            r4.L$3 = r8     // Catch:{ all -> 0x013a }
            r4.L$4 = r6     // Catch:{ all -> 0x013a }
            r4.L$5 = r12     // Catch:{ all -> 0x013a }
            r4.L$6 = r14     // Catch:{ all -> 0x013a }
            r4.L$7 = r7     // Catch:{ all -> 0x013a }
            r4.L$8 = r3     // Catch:{ all -> 0x013a }
            r16 = r1
            r1 = 1
            r4.label = r1     // Catch:{ all -> 0x0133 }
            java.lang.Object r1 = r3.hasNext(r4)     // Catch:{ all -> 0x0133 }
            if (r1 != r0) goto L_0x00e1
            return r0
        L_0x00e1:
            r17 = r15
            r15 = r3
            r3 = r1
            r1 = r21
        L_0x00e7:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x012c }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x012c }
            if (r3 == 0) goto L_0x0119
            java.lang.Object r3 = r15.next()     // Catch:{ all -> 0x012c }
            r21 = r3
            r18 = 0
            r22 = r0
            r0 = r21
            java.lang.Object r19 = r2.invoke(r0)     // Catch:{ all -> 0x012c }
            java.lang.Boolean r19 = (java.lang.Boolean) r19     // Catch:{ all -> 0x012c }
            boolean r19 = r19.booleanValue()     // Catch:{ all -> 0x012c }
            if (r19 == 0) goto L_0x010b
            r9.add(r0)     // Catch:{ all -> 0x012c }
            goto L_0x010e
        L_0x010b:
            r8.add(r0)     // Catch:{ all -> 0x012c }
        L_0x010e:
            r0 = r22
            r21 = r1
            r3 = r15
            r1 = r16
            r15 = r17
            goto L_0x00c3
        L_0x0119:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x012c }
            r3 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r14)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            kotlin.Pair r0 = new kotlin.Pair
            r0.<init>(r9, r8)
            return r0
        L_0x012c:
            r0 = move-exception
            r3 = r5
            r7 = r9
            r11 = r13
            r15 = r17
            goto L_0x014f
        L_0x0133:
            r0 = move-exception
            r1 = r21
            r3 = r5
            r7 = r9
            r11 = r13
            goto L_0x014f
        L_0x013a:
            r0 = move-exception
            r16 = r1
            r1 = r21
            r3 = r5
            r7 = r9
            r11 = r13
            goto L_0x014f
        L_0x0143:
            r0 = move-exception
            r16 = r21
            r6 = r5
            r15 = r14
            r14 = r12
            r12 = r10
            r10 = r8
            r8 = r4
            r4 = r2
            r2 = r22
        L_0x014f:
            r5 = r0
            throw r0     // Catch:{ all -> 0x0152 }
        L_0x0152:
            r0 = move-exception
            r9 = r0
            r13 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r13)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r12, r5)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r13)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.partition(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object partition$$forInline(ReceiveChannel $this$partition, Function1 predicate, Continuation continuation) {
        ArrayList first = new ArrayList();
        ArrayList second = new ArrayList();
        ReceiveChannel $this$consume$iv$iv = $this$partition;
        Throwable cause$iv$iv = null;
        int i = 1;
        try {
            ChannelIterator it = $this$consume$iv$iv.iterator();
            while (true) {
                InlineMarker.mark(0);
                Object hasNext = it.hasNext(continuation);
                InlineMarker.mark(i);
                if (((Boolean) hasNext).booleanValue()) {
                    Object it2 = it.next();
                    try {
                        if (((Boolean) predicate.invoke(it2)).booleanValue()) {
                            first.add(it2);
                        } else {
                            second.add(it2);
                        }
                        i = 1;
                    } catch (Throwable th) {
                        e$iv$iv = th;
                        Throwable cause$iv$iv2 = e$iv$iv;
                        try {
                            throw e$iv$iv;
                        } catch (Throwable e$iv$iv) {
                            Throwable th2 = e$iv$iv;
                            InlineMarker.finallyStart(1);
                            ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv2);
                            InlineMarker.finallyEnd(1);
                            throw th2;
                        }
                    }
                } else {
                    Function1 function1 = predicate;
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consume$iv$iv, cause$iv$iv);
                    InlineMarker.finallyEnd(1);
                    return new Pair(first, second);
                }
            }
        } catch (Throwable th3) {
            e$iv$iv = th3;
            Function1 function12 = predicate;
            Throwable cause$iv$iv22 = e$iv$iv;
            throw e$iv$iv;
        }
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R> ReceiveChannel<Pair<E, R>> zip(ReceiveChannel<? extends E> $this$zip, ReceiveChannel<? extends R> other) {
        Intrinsics.checkParameterIsNotNull($this$zip, "$this$zip");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return zip$default($this$zip, other, (CoroutineContext) null, ChannelsKt__Channels_commonKt$zip$1.INSTANCE, 2, (Object) null);
    }

    public static /* synthetic */ ReceiveChannel zip$default(ReceiveChannel receiveChannel, ReceiveChannel receiveChannel2, CoroutineContext coroutineContext, Function2 function2, int i, Object obj) {
        if ((i & 2) != 0) {
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.zip(receiveChannel, receiveChannel2, coroutineContext, function2);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R, V> ReceiveChannel<V> zip(ReceiveChannel<? extends E> $this$zip, ReceiveChannel<? extends R> other, CoroutineContext context, Function2<? super E, ? super R, ? extends V> transform) {
        Intrinsics.checkParameterIsNotNull($this$zip, "$this$zip");
        Intrinsics.checkParameterIsNotNull(other, "other");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        return ProduceKt.produce$default(GlobalScope.INSTANCE, context, 0, ChannelsKt.consumesAll($this$zip, other), new ChannelsKt__Channels_commonKt$zip$2($this$zip, other, transform, (Continuation) null), 2, (Object) null);
    }
}
