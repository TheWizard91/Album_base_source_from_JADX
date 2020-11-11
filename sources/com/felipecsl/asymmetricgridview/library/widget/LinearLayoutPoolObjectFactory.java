package com.felipecsl.asymmetricgridview.library.widget;

import android.content.Context;
import android.util.AttributeSet;

public class LinearLayoutPoolObjectFactory implements PoolObjectFactory<IcsLinearLayout> {
    private final Context context;

    public LinearLayoutPoolObjectFactory(Context context2) {
        this.context = context2;
    }

    public IcsLinearLayout createObject() {
        return new IcsLinearLayout(this.context, (AttributeSet) null);
    }
}
