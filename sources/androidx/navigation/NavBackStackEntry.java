package androidx.navigation;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import java.util.UUID;

public final class NavBackStackEntry implements LifecycleOwner, ViewModelStoreOwner, HasDefaultViewModelProviderFactory, SavedStateRegistryOwner {
    private final Bundle mArgs;
    private final Context mContext;
    private ViewModelProvider.Factory mDefaultFactory;
    private final NavDestination mDestination;
    private Lifecycle.State mHostLifecycle;
    final UUID mId;
    private final LifecycleRegistry mLifecycle;
    private Lifecycle.State mMaxLifecycle;
    private NavControllerViewModel mNavControllerViewModel;
    private SavedStateHandle mSavedStateHandle;
    private final SavedStateRegistryController mSavedStateRegistryController;

    NavBackStackEntry(Context context, NavDestination destination, Bundle args, LifecycleOwner navControllerLifecycleOwner, NavControllerViewModel navControllerViewModel) {
        this(context, destination, args, navControllerLifecycleOwner, navControllerViewModel, UUID.randomUUID(), (Bundle) null);
    }

    NavBackStackEntry(Context context, NavDestination destination, Bundle args, LifecycleOwner navControllerLifecycleOwner, NavControllerViewModel navControllerViewModel, UUID uuid, Bundle savedState) {
        this.mLifecycle = new LifecycleRegistry(this);
        SavedStateRegistryController create = SavedStateRegistryController.create(this);
        this.mSavedStateRegistryController = create;
        this.mHostLifecycle = Lifecycle.State.CREATED;
        this.mMaxLifecycle = Lifecycle.State.RESUMED;
        this.mContext = context;
        this.mId = uuid;
        this.mDestination = destination;
        this.mArgs = args;
        this.mNavControllerViewModel = navControllerViewModel;
        create.performRestore(savedState);
        if (navControllerLifecycleOwner != null) {
            this.mHostLifecycle = navControllerLifecycleOwner.getLifecycle().getCurrentState();
        }
    }

    public NavDestination getDestination() {
        return this.mDestination;
    }

    public Bundle getArguments() {
        return this.mArgs;
    }

    public Lifecycle getLifecycle() {
        return this.mLifecycle;
    }

    /* access modifiers changed from: package-private */
    public void setMaxLifecycle(Lifecycle.State maxState) {
        this.mMaxLifecycle = maxState;
        updateState();
    }

    /* access modifiers changed from: package-private */
    public Lifecycle.State getMaxLifecycle() {
        return this.mMaxLifecycle;
    }

    /* access modifiers changed from: package-private */
    public void handleLifecycleEvent(Lifecycle.Event event) {
        this.mHostLifecycle = getStateAfter(event);
        updateState();
    }

    /* access modifiers changed from: package-private */
    public void updateState() {
        if (this.mHostLifecycle.ordinal() < this.mMaxLifecycle.ordinal()) {
            this.mLifecycle.setCurrentState(this.mHostLifecycle);
        } else {
            this.mLifecycle.setCurrentState(this.mMaxLifecycle);
        }
    }

    public ViewModelStore getViewModelStore() {
        NavControllerViewModel navControllerViewModel = this.mNavControllerViewModel;
        if (navControllerViewModel != null) {
            return navControllerViewModel.getViewModelStore(this.mId);
        }
        throw new IllegalStateException("You must call setViewModelStore() on your NavHostController before accessing the ViewModelStore of a navigation graph.");
    }

    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        if (this.mDefaultFactory == null) {
            this.mDefaultFactory = new SavedStateViewModelFactory((Application) this.mContext.getApplicationContext(), this, this.mArgs);
        }
        return this.mDefaultFactory;
    }

    public SavedStateRegistry getSavedStateRegistry() {
        return this.mSavedStateRegistryController.getSavedStateRegistry();
    }

    /* access modifiers changed from: package-private */
    public void saveState(Bundle outBundle) {
        this.mSavedStateRegistryController.performSave(outBundle);
    }

    public SavedStateHandle getSavedStateHandle() {
        if (this.mSavedStateHandle == null) {
            this.mSavedStateHandle = ((SavedStateViewModel) new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) new NavResultSavedStateFactory(this, (Bundle) null)).get(SavedStateViewModel.class)).getHandle();
        }
        return this.mSavedStateHandle;
    }

    /* renamed from: androidx.navigation.NavBackStackEntry$1 */
    static /* synthetic */ class C03421 {
        static final /* synthetic */ int[] $SwitchMap$androidx$lifecycle$Lifecycle$Event;

        static {
            int[] iArr = new int[Lifecycle.Event.values().length];
            $SwitchMap$androidx$lifecycle$Lifecycle$Event = iArr;
            try {
                iArr[Lifecycle.Event.ON_CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_STOP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_START.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_PAUSE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_RESUME.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_DESTROY.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_ANY.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    private static Lifecycle.State getStateAfter(Lifecycle.Event event) {
        switch (C03421.$SwitchMap$androidx$lifecycle$Lifecycle$Event[event.ordinal()]) {
            case 1:
            case 2:
                return Lifecycle.State.CREATED;
            case 3:
            case 4:
                return Lifecycle.State.STARTED;
            case 5:
                return Lifecycle.State.RESUMED;
            case 6:
                return Lifecycle.State.DESTROYED;
            default:
                throw new IllegalArgumentException("Unexpected event value " + event);
        }
    }

    private static class NavResultSavedStateFactory extends AbstractSavedStateViewModelFactory {
        NavResultSavedStateFactory(SavedStateRegistryOwner owner, Bundle defaultArgs) {
            super(owner, defaultArgs);
        }

        /* access modifiers changed from: protected */
        public <T extends ViewModel> T create(String key, Class<T> cls, SavedStateHandle handle) {
            return new SavedStateViewModel(handle);
        }
    }

    private static class SavedStateViewModel extends ViewModel {
        private SavedStateHandle mHandle;

        SavedStateViewModel(SavedStateHandle handle) {
            this.mHandle = handle;
        }

        public SavedStateHandle getHandle() {
            return this.mHandle;
        }
    }
}
