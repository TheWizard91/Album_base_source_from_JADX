package com.rey.material.drawable;

import android.graphics.drawable.LevelListDrawable;
import com.rey.material.app.ThemeManager;

public class ThemeDrawable extends LevelListDrawable implements ThemeManager.OnThemeChangedListener {
    private int mStyleId;

    public ThemeDrawable(int styleId) {
        this.mStyleId = styleId;
        if (styleId != 0) {
            ThemeManager.getInstance().registerOnThemeChangedListener(this);
            initDrawables();
        }
    }

    private void initDrawables() {
        ThemeManager themeManager = ThemeManager.getInstance();
        int count = themeManager.getThemeCount();
        for (int i = 0; i < count; i++) {
            addLevel(i, i, themeManager.getContext().getResources().getDrawable(themeManager.getStyle(this.mStyleId, i)));
        }
        setLevel(themeManager.getCurrentTheme());
    }

    public void onThemeChanged(ThemeManager.OnThemeChangedEvent event) {
        if (getLevel() != event.theme) {
            setLevel(event.theme);
        }
    }
}
