package com.thewizard91.thealbumproject.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.actions.SearchIntents;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thewizard91.thealbumproject.C2521R;
import com.thewizard91.thealbumproject.fragments.toplevelfragments.AccountFragment;
import com.thewizard91.thealbumproject.fragments.toplevelfragments.FolderFragment;
import com.thewizard91.thealbumproject.fragments.toplevelfragments.HomeFragment;
import com.thewizard91.thealbumproject.fragments.toplevelfragments.NotificationFragment;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    /* access modifiers changed from: private */
    public AccountFragment accountFragment;
    /* access modifiers changed from: private */
    public ActionBar actionBar;
    private BottomNavigationView bottomNavigationView;
    private FirebaseUser currentUser;
    private String current_user_id;
    private FirebaseFirestore firebaseFirestore;
    /* access modifiers changed from: private */
    public FloatingActionButton floatingActionButton;
    /* access modifiers changed from: private */
    public FolderFragment folderFragment;
    private FragmentTransaction fragmentTransaction;
    /* access modifiers changed from: private */
    public HomeFragment homeFragment;
    private Toolbar mainToolbar;
    /* access modifiers changed from: private */
    public NotificationFragment notificationFragment;
    private SearchView searchWidget;
    private FirebaseAuth userAuthorized;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2521R.C2526layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(C2521R.C2524id.main_toolbar);
        this.mainToolbar = toolbar;
        setSupportActionBar(toolbar);
        this.mainToolbar.setNavigationIcon((Drawable) null);
        ActionBar supportActionBar = getSupportActionBar();
        this.actionBar = supportActionBar;
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(true);
            this.actionBar.setTitle((CharSequence) "Home");
            Intent intent = getIntent();
            if ("android.intent.action.SEARCH".equals(intent.getAction())) {
                String query = intent.getStringExtra(SearchIntents.EXTRA_QUERY);
                if (query != null) {
                    doMySearch(query);
                } else {
                    throw new AssertionError();
                }
            }
            this.floatingActionButton = (FloatingActionButton) findViewById(C2521R.C2524id.floatingActionButton);
            setFloatingButton();
            this.userAuthorized = FirebaseAuth.getInstance();
            this.firebaseFirestore = FirebaseFirestore.getInstance();
            this.currentUser = this.userAuthorized.getCurrentUser();
            this.bottomNavigationView = (BottomNavigationView) findViewById(C2521R.C2524id.main_bottom_nav);
            this.fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (this.currentUser != null) {
                this.current_user_id = ((FirebaseUser) Objects.requireNonNull(this.userAuthorized.getCurrentUser())).getUid();
                HomeFragment homeFragment2 = new HomeFragment();
                this.homeFragment = homeFragment2;
                replaceFragment(homeFragment2);
                this.homeFragment.setMainActivityFloatingActionButton(this.floatingActionButton);
                this.notificationFragment = new NotificationFragment();
                this.accountFragment = new AccountFragment();
                this.folderFragment = new FolderFragment();
                fragmentsMenu();
                return;
            }
            sendToLoginActivity();
            return;
        }
        throw new AssertionError();
    }

    private void doMySearch(String query) {
        String s = null;
        for (int i = 0; i <= query.length(); i++) {
            s = s + query.indexOf(i);
        }
        Log.d("sIs", s);
    }

    /* access modifiers changed from: protected */
    public void onResumeFragments() {
        super.onResumeFragments();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Toast.makeText(this, "" + item, 0).show();
        if (id != 16908332) {
            if (item.toString().equals("Logout")) {
                logout();
            }
            if (item.toString().equals("Settings")) {
                sendToAccountSettingsActivity();
            }
            return super.onOptionsItemSelected(item);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return true;
        } else {
            replaceFragment(this.homeFragment);
            return true;
        }
    }

    private void sendToAccountSettingsActivity() {
        startActivity(new Intent(this, AccountSettingsActivity.class));
    }

    private void logout() {
        this.userAuthorized.signOut();
        sendToLoginActivity();
    }

    private void setFloatingButton() {
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MainActivity.this.homeFragment.isVisible()) {
                    MainActivity.this.sendToNewPostActivity();
                } else if (MainActivity.this.folderFragment.isVisible()) {
                    MainActivity.this.sendToAddContent();
                } else {
                    MainActivity.this.sendToAddNewImagesToExistingAlbum();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendToAddNewImagesToExistingAlbum() {
        startActivity(new Intent(this, AddMoreImagesToExistingAlbum.class));
    }

    /* access modifiers changed from: private */
    public void sendToNewPostActivity() {
        startActivity(new Intent(this, NewPostActivity.class));
    }

    /* access modifiers changed from: private */
    public void sendToAddContent() {
        startActivity(new Intent(this, AddAlbumsActivity.class));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(C2521R.C2527menu.settings_menu, menu);
        return true;
    }

    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            sendToLoginActivity();
        } else {
            this.firebaseFirestore.collection("Users").document(this.current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                public void onComplete(Task<DocumentSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "IMAGE ERROR: " + ((Exception) Objects.requireNonNull(task.getException())).getMessage(), 0).show();
                    } else if (!task.getResult().exists()) {
                        MainActivity.this.sendToLoginActivity();
                    }
                }
            });
        }
    }

    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        String fragmentName = getTheRightFragment(fragment);
        if (fragmentName.equals("MapsFragment") || fragmentName.equals("CommentsFragment")) {
            if (fragmentName.equals("MapsFragment")) {
                this.actionBar.setTitle((CharSequence) "Map");
            } else {
                this.actionBar.setTitle((CharSequence) "Comments");
            }
            this.actionBar.setHomeButtonEnabled(true);
            this.actionBar.setHomeAsUpIndicator((int) C2521R.C2523drawable.baseline_arrow_back_ios_white_24dp);
            this.actionBar.setDisplayHomeAsUpEnabled(true);
            this.homeFragment.onDetach();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Pressed", 0).show();
        replaceFragment(this.homeFragment);
    }

    private String getTheRightFragment(Fragment fragment) {
        char[] fragmentCharArray = fragment.toString().toCharArray();
        StringBuilder subString = new StringBuilder();
        for (char c : fragmentCharArray) {
            if (Character.isAlphabetic(c)) {
                subString.append(c);
                if (subString.toString().equals("MapsFragment")) {
                    return subString.toString();
                }
                if (subString.toString().equals("CommentsFragment")) {
                    return subString.toString();
                }
            }
        }
        return "Error";
    }

    private void fragmentsMenu() {
        this.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case C2521R.C2524id.bottom_action_account /*2131230835*/:
                        MainActivity mainActivity = MainActivity.this;
                        mainActivity.replaceFragment(mainActivity.accountFragment);
                        MainActivity.this.floatingActionButton.setVisibility(4);
                        MainActivity.this.actionBar.setTitle((CharSequence) "Account");
                        return true;
                    case C2521R.C2524id.bottom_action_home /*2131230836*/:
                        MainActivity mainActivity2 = MainActivity.this;
                        mainActivity2.replaceFragment(mainActivity2.homeFragment);
                        MainActivity.this.floatingActionButton.show();
                        MainActivity.this.floatingActionButton.setVisibility(0);
                        MainActivity.this.actionBar.setTitle((CharSequence) "Home");
                        return true;
                    case C2521R.C2524id.bottom_action_notification /*2131230837*/:
                        MainActivity mainActivity3 = MainActivity.this;
                        mainActivity3.replaceFragment(mainActivity3.notificationFragment);
                        MainActivity.this.floatingActionButton.setVisibility(4);
                        MainActivity.this.actionBar.setTitle((CharSequence) "Notification");
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendToLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    /* access modifiers changed from: private */
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        this.fragmentTransaction = beginTransaction;
        beginTransaction.replace(C2521R.C2524id.content_frame, fragment);
        if (fragment == this.homeFragment) {
            this.fragmentTransaction.addToBackStack("HomeFragment");
        }
        this.fragmentTransaction.commit();
    }
}
