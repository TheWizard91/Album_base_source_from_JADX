package androidx.lifecycle;

import android.app.Application;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public final class SavedStateViewModelFactory extends ViewModelProvider.KeyedFactory {
    private static final Class<?>[] ANDROID_VIEWMODEL_SIGNATURE = {Application.class, SavedStateHandle.class};
    private static final Class<?>[] VIEWMODEL_SIGNATURE = {SavedStateHandle.class};
    private final Application mApplication;
    private final Bundle mDefaultArgs;
    private final ViewModelProvider.AndroidViewModelFactory mFactory;
    private final Lifecycle mLifecycle;
    private final SavedStateRegistry mSavedStateRegistry;

    public SavedStateViewModelFactory(Application application, SavedStateRegistryOwner owner) {
        this(application, owner, (Bundle) null);
    }

    public SavedStateViewModelFactory(Application application, SavedStateRegistryOwner owner, Bundle defaultArgs) {
        this.mSavedStateRegistry = owner.getSavedStateRegistry();
        this.mLifecycle = owner.getLifecycle();
        this.mDefaultArgs = defaultArgs;
        this.mApplication = application;
        this.mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application);
    }

    public <T extends ViewModel> T create(String key, Class<T> modelClass) {
        Constructor<T> constructor;
        T viewmodel;
        boolean isAndroidViewModel = AndroidViewModel.class.isAssignableFrom(modelClass);
        if (isAndroidViewModel) {
            constructor = findMatchingConstructor(modelClass, ANDROID_VIEWMODEL_SIGNATURE);
        } else {
            constructor = findMatchingConstructor(modelClass, VIEWMODEL_SIGNATURE);
        }
        if (constructor == null) {
            return this.mFactory.create(modelClass);
        }
        SavedStateHandleController controller = SavedStateHandleController.create(this.mSavedStateRegistry, this.mLifecycle, key, this.mDefaultArgs);
        if (isAndroidViewModel) {
            try {
                viewmodel = (ViewModel) constructor.newInstance(new Object[]{this.mApplication, controller.getHandle()});
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access " + modelClass, e);
            } catch (InstantiationException e2) {
                throw new RuntimeException("A " + modelClass + " cannot be instantiated.", e2);
            } catch (InvocationTargetException e3) {
                throw new RuntimeException("An exception happened in constructor of " + modelClass, e3.getCause());
            }
        } else {
            viewmodel = (ViewModel) constructor.newInstance(new Object[]{controller.getHandle()});
        }
        viewmodel.setTagIfAbsent("androidx.lifecycle.savedstate.vm.tag", controller);
        return viewmodel;
    }

    public <T extends ViewModel> T create(Class<T> modelClass) {
        String canonicalName = modelClass.getCanonicalName();
        if (canonicalName != null) {
            return create(canonicalName, modelClass);
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    private static <T> Constructor<T> findMatchingConstructor(Class<T> modelClass, Class<?>[] signature) {
        for (Constructor<?> constructor : modelClass.getConstructors()) {
            if (Arrays.equals(signature, constructor.getParameterTypes())) {
                return constructor;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void onRequery(ViewModel viewModel) {
        SavedStateHandleController.attachHandleIfNeeded(viewModel, this.mSavedStateRegistry, this.mLifecycle);
    }
}