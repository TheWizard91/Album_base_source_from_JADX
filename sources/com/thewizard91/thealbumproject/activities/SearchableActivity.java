package com.thewizard91.thealbumproject.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.actions.SearchIntents;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.thewizard91.thealbumproject.C2521R;

public class SearchableActivity extends AppCompatActivity {
    private static final String JARGON = "jwhatever";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2521R.C2526layout.activity_searchable);
        Intent intent = getIntent();
        if ("android.intent.action.SEARCH".equals(intent.getAction())) {
            doMySearch(intent.getStringExtra(SearchIntents.EXTRA_QUERY));
        }
    }

    private void doMySearch(String query) {
    }

    private void pauseSomeStuff() {
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if ("android.intent.action.SEARCH".equals(intent.getAction())) {
            doMySearch(intent.getStringExtra(SearchIntents.EXTRA_QUERY));
        }
    }

    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putBoolean(JARGON, true);
        startSearch((String) null, false, appData, false);
        boolean z = appData.getBoolean(JARGON);
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C2521R.C2527menu.settings_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(C2521R.C2524id.app_bar_search).getActionView();
        searchView.setSearchableInfo(((SearchManager) getSystemService(FirebaseAnalytics.Event.SEARCH)).getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        return true;
    }
}
