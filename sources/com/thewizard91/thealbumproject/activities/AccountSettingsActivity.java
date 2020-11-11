package com.thewizard91.thealbumproject.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.thewizard91.thealbumproject.C2521R;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import p018id.zelory.compressor.Compressor;

public class AccountSettingsActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public Button accountSettingsButton;
    /* access modifiers changed from: private */
    public ImageView accountSettingsImageView;
    /* access modifiers changed from: private */
    public EditText accountSettingsName;
    /* access modifiers changed from: private */
    public ProgressBar accountSettingsProgressbar;
    /* access modifiers changed from: private */
    public EditText description;
    /* access modifiers changed from: private */
    public FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    /* access modifiers changed from: private */
    public EditText interests;
    private String[] interestsLists;
    /* access modifiers changed from: private */
    public boolean isTheUserChanged = false;
    /* access modifiers changed from: private */
    public Uri mainImageUri;
    /* access modifiers changed from: private */
    public StorageReference storageReference;
    /* access modifiers changed from: private */
    public String user_id;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2521R.C2526layout.activity_account_settings);
        this.accountSettingsProgressbar = (ProgressBar) findViewById(C2521R.C2524id.account_settings_progressbar);
        this.accountSettingsImageView = (ImageView) findViewById(C2521R.C2524id.account_settings_user_image);
        this.accountSettingsName = (EditText) findViewById(C2521R.C2524id.account_settings_username);
        this.accountSettingsButton = (Button) findViewById(C2521R.C2524id.account_settings_button);
        this.description = (EditText) findViewById(C2521R.C2524id.userDescription);
        EditText editText = (EditText) findViewById(C2521R.C2524id.interests);
        this.interests = editText;
        getAllInterests(editText);
        this.mainImageUri = null;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.storageReference = FirebaseStorage.getInstance().getReference();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.user_id = ((FirebaseUser) Objects.requireNonNull(this.firebaseAuth.getCurrentUser())).getUid();
        firebaseFirestoneDeclaration();
        setAccountSettingsImageViewDeclaration();
        setAccountSettingsButtonDeclaration();
    }

    private void getAllInterests(EditText interests2) {
    }

    private void firebaseFirestoneDeclaration() {
        this.firebaseFirestore.collection("Users").document(this.user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(AccountSettingsActivity.this, "FIREBASE RETRIEVE ERROR: " + ((Exception) Objects.requireNonNull(task.getException())).getMessage(), 0).show();
                } else if (task.getResult().exists()) {
                    String name = task.getResult().getString("profile_name_of");
                    String image = task.getResult().getString("profile_image");
                    Uri unused = AccountSettingsActivity.this.mainImageUri = Uri.parse(image);
                    AccountSettingsActivity.this.accountSettingsName.setText(name);
                    RequestOptions placeholderRequest = new RequestOptions();
                    placeholderRequest.placeholder((int) C2521R.C2523drawable.default_user_image);
                    Glide.with((FragmentActivity) AccountSettingsActivity.this).setDefaultRequestOptions(placeholderRequest).load(image).into(AccountSettingsActivity.this.accountSettingsImageView);
                } else {
                    Toast.makeText(AccountSettingsActivity.this, "The Data does not exists", 0).show();
                }
                AccountSettingsActivity.this.accountSettingsButton.setEnabled(true);
            }
        });
    }

    private void setAccountSettingsButtonDeclaration() {
        this.accountSettingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String user_name = AccountSettingsActivity.this.accountSettingsName.getText().toString();
                String user_description = AccountSettingsActivity.this.description.getText().toString();
                String user_interests = AccountSettingsActivity.this.interests.getText().toString();
                String randomNameForUserProfileImage = UUID.randomUUID().toString();
                if (!TextUtils.isEmpty(user_name) && AccountSettingsActivity.this.mainImageUri != null) {
                    AccountSettingsActivity.this.accountSettingsProgressbar.setVisibility(0);
                    if (AccountSettingsActivity.this.isTheUserChanged) {
                        AccountSettingsActivity accountSettingsActivity = AccountSettingsActivity.this;
                        String unused = accountSettingsActivity.user_id = ((FirebaseUser) Objects.requireNonNull(accountSettingsActivity.firebaseAuth.getCurrentUser())).getUid();
                        StorageReference image_path_in_firebase = AccountSettingsActivity.this.storageReference.child("storage_of: " + user_name).child("profile_images").child(randomNameForUserProfileImage + ".jpg");
                        final StorageReference storageReference = image_path_in_firebase;
                        final String str = user_name;
                        final String str2 = user_description;
                        final String str3 = user_interests;
                        image_path_in_firebase.putFile(AccountSettingsActivity.this.mainImageUri).addOnCompleteListener((OnCompleteListener) new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            public void onComplete(Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {
                                    try {
                                        new Compressor(AccountSettingsActivity.this).setMaxWidth(100).setMaxHeight(100).setQuality(2).compressToBitmap(new File(storageReference.getPath()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    AccountSettingsActivity.this.storeFirestore(task, str, str2, str3);
                                    return;
                                }
                                Toast.makeText(AccountSettingsActivity.this, "IMAGE ERROR: " + ((Exception) Objects.requireNonNull(task.getException())).getMessage(), 0).show();
                                AccountSettingsActivity.this.accountSettingsProgressbar.setVisibility(4);
                            }
                        });
                        return;
                    }
                    AccountSettingsActivity.this.storeFirestore((Task<UploadTask.TaskSnapshot>) null, user_name, user_description, user_interests);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /* access modifiers changed from: private */
    public void storeFirestore(Task<UploadTask.TaskSnapshot> task, String user_name, String user_description, String user_interests) {
        String[] downloadUri = {null};
        if (task != null) {
            final String[] strArr = downloadUri;
            final String str = user_name;
            final String str2 = user_description;
            final String str3 = user_interests;
            task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        public void onSuccess(Uri uri) {
                            strArr[0] = uri.toString();
                            AccountSettingsActivity.this.makeTheMap(str, strArr[0], str2, str3);
                        }
                    });
                }
            });
        } else {
            downloadUri[0] = this.mainImageUri.toString();
        }
        makeTheMap(user_name, downloadUri[0], user_description, user_interests);
    }

    /* access modifiers changed from: private */
    public void makeTheMap(String user_name, String imageString, String user_description, String user_interests) {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("profile_name_of", user_name);
        userMap.put("profile_image", imageString);
        userMap.put("user_description", user_description);
        userMap.put("user_interests", user_interests);
        this.firebaseFirestore.collection("Users").document(this.user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    AccountSettingsActivity.this.sendToMainActivity();
                    Toast.makeText(AccountSettingsActivity.this, "The user settings are updated", 0).show();
                } else {
                    Toast.makeText(AccountSettingsActivity.this, "FIRESTORE ERROR" + ((Exception) Objects.requireNonNull(task.getException())).getMessage(), 0).show();
                }
                AccountSettingsActivity.this.accountSettingsProgressbar.setVisibility(4);
            }
        });
    }

    private void setAccountSettingsImageViewDeclaration() {
        this.accountSettingsImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < 23) {
                    AccountSettingsActivity.this.BringImagePicker();
                } else if (ContextCompat.checkSelfPermission(AccountSettingsActivity.this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
                    ActivityCompat.requestPermissions(AccountSettingsActivity.this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 1);
                    Toast.makeText(AccountSettingsActivity.this, "Permission Denied", 0).show();
                } else {
                    AccountSettingsActivity.this.BringImagePicker();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void BringImagePicker() {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 203) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == -1) {
                Uri uri = result.getUri();
                this.mainImageUri = uri;
                this.accountSettingsImageView.setImageURI(uri);
                this.isTheUserChanged = true;
            } else if (resultCode == 204) {
                result.getError();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
