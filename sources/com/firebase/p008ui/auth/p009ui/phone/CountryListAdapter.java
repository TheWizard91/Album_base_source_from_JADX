package com.firebase.p008ui.auth.p009ui.phone;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import com.firebase.p008ui.auth.C2354R;
import com.firebase.p008ui.auth.data.model.CountryInfo;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/* renamed from: com.firebase.ui.auth.ui.phone.CountryListAdapter */
final class CountryListAdapter extends ArrayAdapter<CountryInfo> implements SectionIndexer {
    private final HashMap<String, Integer> alphaIndex = new LinkedHashMap();
    private final HashMap<String, Integer> countryPosition = new LinkedHashMap();
    private String[] sections;

    public CountryListAdapter(Context context) {
        super(context, C2354R.C2359layout.fui_dgts_country_row, 16908308);
    }

    public void setData(List<CountryInfo> countries) {
        int index = 0;
        for (CountryInfo countryInfo : countries) {
            String key = countryInfo.getLocale().getDisplayCountry().substring(0, 1).toUpperCase(Locale.getDefault());
            if (!this.alphaIndex.containsKey(key)) {
                this.alphaIndex.put(key, Integer.valueOf(index));
            }
            this.countryPosition.put(countryInfo.getLocale().getDisplayCountry(), Integer.valueOf(index));
            index++;
            add(countryInfo);
        }
        this.sections = new String[this.alphaIndex.size()];
        this.alphaIndex.keySet().toArray(this.sections);
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.countryPosition.size();
    }

    public Object[] getSections() {
        return this.sections;
    }

    public int getPositionForSection(int index) {
        String[] strArr = this.sections;
        if (strArr == null || index <= 0) {
            return 0;
        }
        if (index >= strArr.length) {
            index = strArr.length - 1;
        }
        return this.alphaIndex.get(strArr[index]).intValue();
    }

    public int getSectionForPosition(int position) {
        if (this.sections == null) {
            return 0;
        }
        for (int i = 0; i < this.sections.length; i++) {
            if (getPositionForSection(i) > position) {
                return i - 1;
            }
        }
        return 0;
    }

    public int getPositionForCountry(String country) {
        Integer position = this.countryPosition.get(country);
        if (position == null) {
            return 0;
        }
        return position.intValue();
    }
}
