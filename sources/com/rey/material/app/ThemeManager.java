package com.rey.material.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.SparseArray;
import com.rey.material.C2500R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ThemeManager {
    private static final String KEY_THEME = "theme";
    private static final String PREF = "theme.pref";
    public static final int THEME_UNDEFINED = Integer.MIN_VALUE;
    private static volatile ThemeManager mInstance;
    private Context mContext;
    private int mCurrentTheme;
    private EventDispatcher mDispatcher;
    private SparseArray<int[]> mStyles = new SparseArray<>();
    private int mThemeCount;

    public interface EventDispatcher {
        void dispatchThemeChanged(int i);

        void registerListener(OnThemeChangedListener onThemeChangedListener);

        void unregisterListener(OnThemeChangedListener onThemeChangedListener);
    }

    public interface OnThemeChangedListener {
        void onThemeChanged(OnThemeChangedEvent onThemeChangedEvent);
    }

    public static int getStyleId(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, C2500R.styleable.ThemableView, defStyleAttr, defStyleRes);
        int styleId = a.getResourceId(C2500R.styleable.ThemableView_v_styleId, 0);
        a.recycle();
        return styleId;
    }

    public static void init(Context context, int totalTheme, int defaultTheme, EventDispatcher dispatcher) {
        getInstance().setup(context, totalTheme, defaultTheme, dispatcher);
    }

    public static ThemeManager getInstance() {
        if (mInstance == null) {
            synchronized (ThemeManager.class) {
                if (mInstance == null) {
                    mInstance = new ThemeManager();
                }
            }
        }
        return mInstance;
    }

    /* access modifiers changed from: protected */
    public void setup(Context context, int totalTheme, int defaultTheme, EventDispatcher dispatcher) {
        this.mContext = context;
        this.mDispatcher = dispatcher != null ? dispatcher : new SimpleDispatcher();
        this.mThemeCount = totalTheme;
        SharedPreferences pref = getSharedPreferences(this.mContext);
        if (pref != null) {
            this.mCurrentTheme = pref.getInt(KEY_THEME, defaultTheme);
        } else {
            this.mCurrentTheme = defaultTheme;
        }
        if (this.mCurrentTheme >= this.mThemeCount) {
            setCurrentTheme(defaultTheme);
        }
    }

    private int[] loadStyleList(Context context, int resId) {
        if (context == null) {
            return null;
        }
        TypedArray array = context.getResources().obtainTypedArray(resId);
        int[] result = new int[array.length()];
        for (int i = 0; i < result.length; i++) {
            result[i] = array.getResourceId(i, 0);
        }
        array.recycle();
        return result;
    }

    private int[] getStyleList(int styleId) {
        SparseArray<int[]> sparseArray = this.mStyles;
        if (sparseArray == null) {
            return null;
        }
        int[] list = sparseArray.get(styleId);
        if (list != null) {
            return list;
        }
        int[] list2 = loadStyleList(this.mContext, styleId);
        this.mStyles.put(styleId, list2);
        return list2;
    }

    private SharedPreferences getSharedPreferences(Context context) {
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences(PREF, 0);
    }

    private void dispatchThemeChanged(int theme) {
        EventDispatcher eventDispatcher = this.mDispatcher;
        if (eventDispatcher != null) {
            eventDispatcher.dispatchThemeChanged(theme);
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getCurrentTheme() {
        return this.mCurrentTheme;
    }

    public boolean setCurrentTheme(int theme) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread() || this.mCurrentTheme == theme) {
            return false;
        }
        this.mCurrentTheme = theme;
        SharedPreferences pref = getSharedPreferences(this.mContext);
        if (pref != null) {
            pref.edit().putInt(KEY_THEME, this.mCurrentTheme).apply();
        }
        dispatchThemeChanged(this.mCurrentTheme);
        return true;
    }

    public int getThemeCount() {
        return this.mThemeCount;
    }

    public int getCurrentStyle(int styleId) {
        return getStyle(styleId, this.mCurrentTheme);
    }

    public int getStyle(int styleId, int theme) {
        int[] styles = getStyleList(styleId);
        if (styles == null) {
            return 0;
        }
        return styles[theme];
    }

    public void registerOnThemeChangedListener(OnThemeChangedListener listener) {
        EventDispatcher eventDispatcher = this.mDispatcher;
        if (eventDispatcher != null) {
            eventDispatcher.registerListener(listener);
        }
    }

    public void unregisterOnThemeChangedListener(OnThemeChangedListener listener) {
        EventDispatcher eventDispatcher = this.mDispatcher;
        if (eventDispatcher != null) {
            eventDispatcher.unregisterListener(listener);
        }
    }

    public static class SimpleDispatcher implements EventDispatcher {
        ArrayList<WeakReference<OnThemeChangedListener>> mListeners = new ArrayList<>();

        public void registerListener(OnThemeChangedListener listener) {
            boolean exist = false;
            for (int i = this.mListeners.size() - 1; i >= 0; i--) {
                WeakReference<OnThemeChangedListener> ref = this.mListeners.get(i);
                if (ref.get() == null) {
                    this.mListeners.remove(i);
                } else if (ref.get() == listener) {
                    exist = true;
                }
            }
            if (!exist) {
                this.mListeners.add(new WeakReference(listener));
            }
        }

        public void unregisterListener(OnThemeChangedListener listener) {
            for (int i = this.mListeners.size() - 1; i >= 0; i--) {
                WeakReference<OnThemeChangedListener> ref = this.mListeners.get(i);
                if (ref.get() == null || ref.get() == listener) {
                    this.mListeners.remove(i);
                }
            }
        }

        public void dispatchThemeChanged(int theme) {
            OnThemeChangedEvent event = new OnThemeChangedEvent(theme);
            for (int i = this.mListeners.size() - 1; i >= 0; i--) {
                WeakReference<OnThemeChangedListener> ref = this.mListeners.get(i);
                if (ref.get() == null) {
                    this.mListeners.remove(i);
                } else {
                    ((OnThemeChangedListener) ref.get()).onThemeChanged(event);
                }
            }
        }
    }

    public static class OnThemeChangedEvent {
        public final int theme;

        public OnThemeChangedEvent(int theme2) {
            this.theme = theme2;
        }
    }
}
