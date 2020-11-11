package kotlin.collections;

import java.util.AbstractCollection;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMutableCollection;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b'\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0007\b\u0004¢\u0006\u0002\u0010\u0004J\u0015\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H&¢\u0006\u0002\u0010\b¨\u0006\t"}, mo33671d2 = {"Lkotlin/collections/AbstractMutableCollection;", "E", "", "Ljava/util/AbstractCollection;", "()V", "add", "", "element", "(Ljava/lang/Object;)Z", "kotlin-stdlib"}, mo33672k = 1, mo33673mv = {1, 1, 16})
/* compiled from: AbstractMutableCollection.kt */
public abstract class AbstractMutableCollection<E> extends AbstractCollection<E> implements Collection<E>, KMutableCollection {
    public abstract boolean add(E e);

    public abstract int getSize();

    protected AbstractMutableCollection() {
    }

    public final /* bridge */ int size() {
        return getSize();
    }
}
