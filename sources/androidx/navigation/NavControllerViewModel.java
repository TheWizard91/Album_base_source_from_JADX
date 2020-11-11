package androidx.navigation;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

class NavControllerViewModel extends ViewModel {
    private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory() {
        public <T extends ViewModel> T create(Class<T> cls) {
            return new NavControllerViewModel();
        }
    };
    private final HashMap<UUID, ViewModelStore> mViewModelStores = new HashMap<>();

    NavControllerViewModel() {
    }

    static NavControllerViewModel getInstance(ViewModelStore viewModelStore) {
        return (NavControllerViewModel) new ViewModelProvider(viewModelStore, FACTORY).get(NavControllerViewModel.class);
    }

    /* access modifiers changed from: package-private */
    public void clear(UUID backStackEntryUUID) {
        ViewModelStore viewModelStore = this.mViewModelStores.remove(backStackEntryUUID);
        if (viewModelStore != null) {
            viewModelStore.clear();
        }
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
        for (ViewModelStore store : this.mViewModelStores.values()) {
            store.clear();
        }
        this.mViewModelStores.clear();
    }

    /* access modifiers changed from: package-private */
    public ViewModelStore getViewModelStore(UUID backStackEntryUUID) {
        ViewModelStore viewModelStore = this.mViewModelStores.get(backStackEntryUUID);
        if (viewModelStore != null) {
            return viewModelStore;
        }
        ViewModelStore viewModelStore2 = new ViewModelStore();
        this.mViewModelStores.put(backStackEntryUUID, viewModelStore2);
        return viewModelStore2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NavControllerViewModel{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("} ViewModelStores (");
        Iterator<UUID> viewModelStoreIterator = this.mViewModelStores.keySet().iterator();
        while (viewModelStoreIterator.hasNext()) {
            sb.append(viewModelStoreIterator.next());
            if (viewModelStoreIterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
