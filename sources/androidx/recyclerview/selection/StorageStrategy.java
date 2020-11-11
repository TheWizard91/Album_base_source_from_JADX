package androidx.recyclerview.selection;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class StorageStrategy<K> {
    static final String SELECTION_ENTRIES = "androidx.recyclerview.selection.entries";
    static final String SELECTION_KEY_TYPE = "androidx.recyclerview.selection.type";
    private final Class<K> mType;

    public abstract Bundle asBundle(Selection<K> selection);

    public abstract Selection<K> asSelection(Bundle bundle);

    public StorageStrategy(Class<K> type) {
        Preconditions.checkArgument(type != null);
        this.mType = type;
    }

    /* access modifiers changed from: package-private */
    public String getKeyTypeName() {
        return this.mType.getCanonicalName();
    }

    public static <K extends Parcelable> StorageStrategy<K> createParcelableStorage(Class<K> type) {
        return new ParcelableStorageStrategy(type);
    }

    public static StorageStrategy<String> createStringStorage() {
        return new StringStorageStrategy();
    }

    public static StorageStrategy<Long> createLongStorage() {
        return new LongStorageStrategy();
    }

    private static class StringStorageStrategy extends StorageStrategy<String> {
        StringStorageStrategy() {
            super(String.class);
        }

        public Selection<String> asSelection(Bundle state) {
            ArrayList<String> stored;
            String keyType = state.getString(StorageStrategy.SELECTION_KEY_TYPE, (String) null);
            if (keyType == null || !keyType.equals(getKeyTypeName()) || (stored = state.getStringArrayList(StorageStrategy.SELECTION_ENTRIES)) == null) {
                return null;
            }
            Selection<String> selection = new Selection<>();
            selection.mSelection.addAll(stored);
            return selection;
        }

        public Bundle asBundle(Selection<String> selection) {
            Bundle bundle = new Bundle();
            bundle.putString(StorageStrategy.SELECTION_KEY_TYPE, getKeyTypeName());
            ArrayList<String> value = new ArrayList<>(selection.size());
            value.addAll(selection.mSelection);
            bundle.putStringArrayList(StorageStrategy.SELECTION_ENTRIES, value);
            return bundle;
        }
    }

    private static class LongStorageStrategy extends StorageStrategy<Long> {
        LongStorageStrategy() {
            super(Long.class);
        }

        public Selection<Long> asSelection(Bundle state) {
            long[] stored;
            String keyType = state.getString(StorageStrategy.SELECTION_KEY_TYPE, (String) null);
            if (keyType == null || !keyType.equals(getKeyTypeName()) || (stored = state.getLongArray(StorageStrategy.SELECTION_ENTRIES)) == null) {
                return null;
            }
            Selection<Long> selection = new Selection<>();
            for (long key : stored) {
                selection.mSelection.add(Long.valueOf(key));
            }
            return selection;
        }

        public Bundle asBundle(Selection<Long> selection) {
            Bundle bundle = new Bundle();
            bundle.putString(StorageStrategy.SELECTION_KEY_TYPE, getKeyTypeName());
            long[] value = new long[selection.size()];
            int i = 0;
            Iterator<Long> it = selection.iterator();
            while (it.hasNext()) {
                value[i] = it.next().longValue();
                i++;
            }
            bundle.putLongArray(StorageStrategy.SELECTION_ENTRIES, value);
            return bundle;
        }
    }

    private static class ParcelableStorageStrategy<K extends Parcelable> extends StorageStrategy<K> {
        ParcelableStorageStrategy(Class<K> type) {
            super(type);
            Preconditions.checkArgument(Parcelable.class.isAssignableFrom(type));
        }

        public Selection<K> asSelection(Bundle state) {
            ArrayList<K> stored;
            String keyType = state.getString(StorageStrategy.SELECTION_KEY_TYPE, (String) null);
            if (keyType == null || !keyType.equals(getKeyTypeName()) || (stored = state.getParcelableArrayList(StorageStrategy.SELECTION_ENTRIES)) == null) {
                return null;
            }
            Selection<K> selection = new Selection<>();
            selection.mSelection.addAll(stored);
            return selection;
        }

        public Bundle asBundle(Selection<K> selection) {
            Bundle bundle = new Bundle();
            bundle.putString(StorageStrategy.SELECTION_KEY_TYPE, getKeyTypeName());
            ArrayList<K> value = new ArrayList<>(selection.size());
            value.addAll(selection.mSelection);
            bundle.putParcelableArrayList(StorageStrategy.SELECTION_ENTRIES, value);
            return bundle;
        }
    }
}
