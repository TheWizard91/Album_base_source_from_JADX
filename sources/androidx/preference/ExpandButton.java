package androidx.preference;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

final class ExpandButton extends Preference {
    private long mId;

    ExpandButton(Context context, List<Preference> collapsedPreferences, long parentId) {
        super(context);
        initLayout();
        setSummary(collapsedPreferences);
        this.mId = 1000000 + parentId;
    }

    private void initLayout() {
        setLayoutResource(C2211R.C2216layout.expand_button);
        setIcon(C2211R.C2213drawable.ic_arrow_down_24dp);
        setTitle(C2211R.string.expand_button_title);
        setOrder(999);
    }

    private void setSummary(List<Preference> collapsedPreferences) {
        CharSequence summary = null;
        List<PreferenceGroup> parents = new ArrayList<>();
        for (Preference preference : collapsedPreferences) {
            CharSequence title = preference.getTitle();
            if ((preference instanceof PreferenceGroup) && !TextUtils.isEmpty(title)) {
                parents.add((PreferenceGroup) preference);
            }
            if (parents.contains(preference.getParent())) {
                if (preference instanceof PreferenceGroup) {
                    parents.add((PreferenceGroup) preference);
                }
            } else if (!TextUtils.isEmpty(title)) {
                if (summary == null) {
                    summary = title;
                } else {
                    summary = getContext().getString(C2211R.string.summary_collapsed_preference_list, new Object[]{summary, title});
                }
            }
        }
        setSummary(summary);
    }

    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        holder.setDividerAllowedAbove(false);
    }

    /* access modifiers changed from: package-private */
    public long getId() {
        return this.mId;
    }
}
