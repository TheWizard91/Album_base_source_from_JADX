package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public abstract class ImmutableSortedMultiset<E> extends ImmutableSortedMultisetFauxverideShim<E> implements SortedMultiset<E> {
    @LazyInit
    transient ImmutableSortedMultiset<E> descendingMultiset;

    public abstract ImmutableSortedSet<E> elementSet();

    public abstract ImmutableSortedMultiset<E> headMultiset(E e, BoundType boundType);

    public abstract ImmutableSortedMultiset<E> tailMultiset(E e, BoundType boundType);

    /* renamed from: of */
    public static <E> ImmutableSortedMultiset<E> m1204of() {
        return RegularImmutableSortedMultiset.NATURAL_EMPTY_MULTISET;
    }

    /* renamed from: of */
    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> m1205of(E element) {
        return new RegularImmutableSortedMultiset((RegularImmutableSortedSet) ImmutableSortedSet.m1218of(element), new long[]{0, 1}, 0, 1);
    }

    /* renamed from: of */
    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> m1206of(E e1, E e2) {
        return copyOf(Ordering.natural(), Arrays.asList(new Comparable[]{e1, e2}));
    }

    /* renamed from: of */
    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> m1207of(E e1, E e2, E e3) {
        return copyOf(Ordering.natural(), Arrays.asList(new Comparable[]{e1, e2, e3}));
    }

    /* renamed from: of */
    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> m1208of(E e1, E e2, E e3, E e4) {
        return copyOf(Ordering.natural(), Arrays.asList(new Comparable[]{e1, e2, e3, e4}));
    }

    /* renamed from: of */
    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> m1209of(E e1, E e2, E e3, E e4, E e5) {
        return copyOf(Ordering.natural(), Arrays.asList(new Comparable[]{e1, e2, e3, e4, e5}));
    }

    /* renamed from: of */
    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> m1210of(E e1, E e2, E e3, E e4, E e5, E e6, E... remaining) {
        List<E> all = Lists.newArrayListWithCapacity(remaining.length + 6);
        Collections.addAll(all, new Comparable[]{e1, e2, e3, e4, e5, e6});
        Collections.addAll(all, remaining);
        return copyOf(Ordering.natural(), all);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> copyOf(E[] elements) {
        return copyOf(Ordering.natural(), Arrays.asList(elements));
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Iterable<? extends E> elements) {
        return copyOf(Ordering.natural(), elements);
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Iterator<? extends E> elements) {
        return copyOf(Ordering.natural(), elements);
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> comparator, Iterator<? extends E> elements) {
        Preconditions.checkNotNull(comparator);
        return new Builder(comparator).addAll((Iterator) elements).build();
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> comparator, Iterable<? extends E> elements) {
        if (elements instanceof ImmutableSortedMultiset) {
            ImmutableSortedMultiset<E> multiset = (ImmutableSortedMultiset) elements;
            if (comparator.equals(multiset.comparator())) {
                if (multiset.isPartialView()) {
                    return copyOfSortedEntries(comparator, multiset.entrySet().asList());
                }
                return multiset;
            }
        }
        return new Builder(comparator).addAll((Iterable) elements).build();
    }

    public static <E> ImmutableSortedMultiset<E> copyOfSorted(SortedMultiset<E> sortedMultiset) {
        return copyOfSortedEntries(sortedMultiset.comparator(), Lists.newArrayList(sortedMultiset.entrySet()));
    }

    private static <E> ImmutableSortedMultiset<E> copyOfSortedEntries(Comparator<? super E> comparator, Collection<Multiset.Entry<E>> entries) {
        if (entries.isEmpty()) {
            return emptyMultiset(comparator);
        }
        ImmutableList.Builder<E> elementsBuilder = new ImmutableList.Builder<>(entries.size());
        long[] cumulativeCounts = new long[(entries.size() + 1)];
        int i = 0;
        for (Multiset.Entry<E> entry : entries) {
            elementsBuilder.add((Object) entry.getElement());
            cumulativeCounts[i + 1] = cumulativeCounts[i] + ((long) entry.getCount());
            i++;
        }
        return new RegularImmutableSortedMultiset(new RegularImmutableSortedSet(elementsBuilder.build(), comparator), cumulativeCounts, 0, entries.size());
    }

    static <E> ImmutableSortedMultiset<E> emptyMultiset(Comparator<? super E> comparator) {
        if (Ordering.natural().equals(comparator)) {
            return RegularImmutableSortedMultiset.NATURAL_EMPTY_MULTISET;
        }
        return new RegularImmutableSortedMultiset(comparator);
    }

    ImmutableSortedMultiset() {
    }

    public final Comparator<? super E> comparator() {
        return elementSet().comparator();
    }

    public ImmutableSortedMultiset<E> descendingMultiset() {
        ImmutableSortedMultiset<E> result = this.descendingMultiset;
        if (result != null) {
            return result;
        }
        ImmutableSortedMultiset<E> emptyMultiset = isEmpty() ? emptyMultiset(Ordering.from(comparator()).reverse()) : new DescendingImmutableSortedMultiset<>(this);
        this.descendingMultiset = emptyMultiset;
        return emptyMultiset;
    }

    @Deprecated
    public final Multiset.Entry<E> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final Multiset.Entry<E> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    public ImmutableSortedMultiset<E> subMultiset(E lowerBound, BoundType lowerBoundType, E upperBound, BoundType upperBoundType) {
        Preconditions.checkArgument(comparator().compare(lowerBound, upperBound) <= 0, "Expected lowerBound <= upperBound but %s > %s", (Object) lowerBound, (Object) upperBound);
        return tailMultiset(lowerBound, lowerBoundType).headMultiset(upperBound, upperBoundType);
    }

    public static <E> Builder<E> orderedBy(Comparator<E> comparator) {
        return new Builder<>(comparator);
    }

    public static <E extends Comparable<?>> Builder<E> reverseOrder() {
        return new Builder<>(Ordering.natural().reverse());
    }

    public static <E extends Comparable<?>> Builder<E> naturalOrder() {
        return new Builder<>(Ordering.natural());
    }

    public static class Builder<E> extends ImmutableMultiset.Builder<E> {
        private final Comparator<? super E> comparator;
        private int[] counts = new int[4];
        E[] elements = ((Object[]) new Object[4]);
        private boolean forceCopyElements;
        private int length;

        public Builder(Comparator<? super E> comparator2) {
            super(true);
            this.comparator = (Comparator) Preconditions.checkNotNull(comparator2);
        }

        private void maintenance() {
            int i = this.length;
            E[] eArr = this.elements;
            if (i == eArr.length) {
                dedupAndCoalesce(true);
            } else if (this.forceCopyElements) {
                this.elements = Arrays.copyOf(eArr, eArr.length);
            }
            this.forceCopyElements = false;
        }

        private void dedupAndCoalesce(boolean maybeExpand) {
            int i = this.length;
            if (i != 0) {
                E[] sortedElements = Arrays.copyOf(this.elements, i);
                Arrays.sort(sortedElements, this.comparator);
                int uniques = 1;
                for (int i2 = 1; i2 < sortedElements.length; i2++) {
                    if (this.comparator.compare(sortedElements[uniques - 1], sortedElements[i2]) < 0) {
                        sortedElements[uniques] = sortedElements[i2];
                        uniques++;
                    }
                }
                Arrays.fill(sortedElements, uniques, this.length, (Object) null);
                if (maybeExpand) {
                    int i3 = uniques * 4;
                    int i4 = this.length;
                    if (i3 > i4 * 3) {
                        sortedElements = Arrays.copyOf(sortedElements, IntMath.saturatedAdd(i4, (i4 / 2) + 1));
                    }
                }
                int[] sortedCounts = new int[sortedElements.length];
                for (int i5 = 0; i5 < this.length; i5++) {
                    int index = Arrays.binarySearch(sortedElements, 0, uniques, this.elements[i5], this.comparator);
                    int[] iArr = this.counts;
                    if (iArr[i5] >= 0) {
                        sortedCounts[index] = sortedCounts[index] + iArr[i5];
                    } else {
                        sortedCounts[index] = ~iArr[i5];
                    }
                }
                this.elements = sortedElements;
                this.counts = sortedCounts;
                this.length = uniques;
            }
        }

        public Builder<E> add(E element) {
            return addCopies((Object) element, 1);
        }

        public Builder<E> add(E... elements2) {
            for (E element : elements2) {
                add((Object) element);
            }
            return this;
        }

        public Builder<E> addCopies(E element, int occurrences) {
            Preconditions.checkNotNull(element);
            CollectPreconditions.checkNonnegative(occurrences, "occurrences");
            if (occurrences == 0) {
                return this;
            }
            maintenance();
            E[] eArr = this.elements;
            int i = this.length;
            eArr[i] = element;
            this.counts[i] = occurrences;
            this.length = i + 1;
            return this;
        }

        public Builder<E> setCount(E element, int count) {
            Preconditions.checkNotNull(element);
            CollectPreconditions.checkNonnegative(count, "count");
            maintenance();
            E[] eArr = this.elements;
            int i = this.length;
            eArr[i] = element;
            this.counts[i] = ~count;
            this.length = i + 1;
            return this;
        }

        public Builder<E> addAll(Iterable<? extends E> elements2) {
            if (elements2 instanceof Multiset) {
                for (Multiset.Entry<? extends E> entry : ((Multiset) elements2).entrySet()) {
                    addCopies((Object) entry.getElement(), entry.getCount());
                }
            } else {
                for (E e : elements2) {
                    add((Object) e);
                }
            }
            return this;
        }

        public Builder<E> addAll(Iterator<? extends E> elements2) {
            while (elements2.hasNext()) {
                add((Object) elements2.next());
            }
            return this;
        }

        private void dedupAndCoalesceAndDeleteEmpty() {
            dedupAndCoalesce(false);
            int size = 0;
            int i = 0;
            while (true) {
                int i2 = this.length;
                if (i < i2) {
                    int[] iArr = this.counts;
                    if (iArr[i] > 0) {
                        E[] eArr = this.elements;
                        eArr[size] = eArr[i];
                        iArr[size] = iArr[i];
                        size++;
                    }
                    i++;
                } else {
                    Arrays.fill(this.elements, size, i2, (Object) null);
                    Arrays.fill(this.counts, size, this.length, 0);
                    this.length = size;
                    return;
                }
            }
        }

        public ImmutableSortedMultiset<E> build() {
            dedupAndCoalesceAndDeleteEmpty();
            int i = this.length;
            if (i == 0) {
                return ImmutableSortedMultiset.emptyMultiset(this.comparator);
            }
            RegularImmutableSortedSet<E> elementSet = (RegularImmutableSortedSet) ImmutableSortedSet.construct(this.comparator, i, this.elements);
            long[] cumulativeCounts = new long[(this.length + 1)];
            for (int i2 = 0; i2 < this.length; i2++) {
                cumulativeCounts[i2 + 1] = cumulativeCounts[i2] + ((long) this.counts[i2]);
            }
            this.forceCopyElements = true;
            return new RegularImmutableSortedMultiset(elementSet, cumulativeCounts, 0, this.length);
        }
    }

    private static final class SerializedForm<E> implements Serializable {
        final Comparator<? super E> comparator;
        final int[] counts;
        final E[] elements;

        SerializedForm(SortedMultiset<E> multiset) {
            this.comparator = multiset.comparator();
            int n = multiset.entrySet().size();
            this.elements = (Object[]) new Object[n];
            this.counts = new int[n];
            int i = 0;
            for (Multiset.Entry<E> entry : multiset.entrySet()) {
                this.elements[i] = entry.getElement();
                this.counts[i] = entry.getCount();
                i++;
            }
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            int n = this.elements.length;
            Builder<E> builder = new Builder<>(this.comparator);
            for (int i = 0; i < n; i++) {
                builder.addCopies((Object) this.elements[i], this.counts[i]);
            }
            return builder.build();
        }
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new SerializedForm(this);
    }
}
