package com.thewizard91.thealbumproject.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.thewizard91.thealbumproject.C2521R;
import com.thewizard91.thealbumproject.fragments.toplevelfragments.FolderFragment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import p018id.zelory.compressor.Compressor;

public class AddAlbumsActivity extends AppCompatActivity {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Button addAlbumButton;
    private Button addNewImagesToExistingAlbum;
    private TextView albumDescription;
    /* access modifiers changed from: private */
    public ProgressBar createANewAlbumProgressbar;
    private FirebaseFirestore firebaseFirestore;
    /* access modifiers changed from: private */
    public Uri imageUri;
    /* access modifiers changed from: private */
    public ImageView insertImage;
    /* access modifiers changed from: private */
    public TextView nameNewAlbum;
    /* access modifiers changed from: private */
    public StorageReference storageReference;
    private String userId;
    /* access modifiers changed from: private */
    public String username;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2521R.C2526layout.activity_add_content);
        setSupportActionBar((Toolbar) findViewById(C2521R.C2524id.main_toolbar));
        ((ActionBar) Objects.requireNonNull(getSupportActionBar())).setTitle((CharSequence) "Create A new Album");
        this.createANewAlbumProgressbar = (ProgressBar) findViewById(C2521R.C2524id.add_new_content_progressbar);
        this.insertImage = (ImageView) findViewById(C2521R.C2524id.create_a_new_album_insert_image_place_holder);
        this.nameNewAlbum = (TextView) findViewById(C2521R.C2524id.new_album_name);
        this.albumDescription = (TextView) findViewById(C2521R.C2524id.create_a_new_album_description);
        this.addAlbumButton = (Button) findViewById(C2521R.C2524id.create_a_new_album_button);
        this.addNewImagesToExistingAlbum = (Button) findViewById(C2521R.C2524id.or_add_new_images_to_existing_album);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            this.userId = currentUser.getUid();
            activateImageViewHandler();
            activateButtonHandler();
            addImagesToExistingAlbum();
            return;
        }
        throw new AssertionError();
    }

    private void addImagesToExistingAlbum() {
        this.addNewImagesToExistingAlbum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddAlbumsActivity.this.sendToAddMoreImagesToExistingAlbum();
            }
        });
    }

    private void activateImageViewHandler() {
        this.insertImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddAlbumsActivity.this.BringImagePicker();
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
                this.imageUri = uri;
                this.insertImage.setImageURI(uri);
            } else if (resultCode == 204) {
                result.getError();
            }
        }
    }

    private void activateButtonHandler() {
        getUserName();
        this.addAlbumButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String newAlbumName = AddAlbumsActivity.this.nameNewAlbum.getText().toString();
                String randomNameForFirstImageOfTheNewAlbum = UUID.randomUUID().toString();
                if (!TextUtils.isEmpty(newAlbumName) && AddAlbumsActivity.this.insertImage != null) {
                    AddAlbumsActivity.this.createANewAlbumProgressbar.setVisibility(0);
                    AddAlbumsActivity.this.storageReference.child("storage_of: " + AddAlbumsActivity.this.username).child(newAlbumName).child("galleries_image_cover").child(randomNameForFirstImageOfTheNewAlbum + ".jpg").putFile(AddAlbumsActivity.this.imageUri).addOnCompleteListener((OnCompleteListener) new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        public void onComplete(Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                try {
                                    new Compressor(AddAlbumsActivity.this).setMaxHeight(100).setMaxWidth(100).setQuality(2).compressToBitmap(new File((String) Objects.requireNonNull(AddAlbumsActivity.this.imageUri.getPath())));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                AddAlbumsActivity.this.getUri(task);
                            }
                        }
                    });
                }
            }
        });
    }

    private void getUserName() {
        this.firebaseFirestore.collection("Users").document(this.userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<String> list = new ArrayList<>();
                        Map<String, Object> map = document.getData();
                        if (map != null) {
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                list.add(entry.getValue().toString());
                            }
                            String unused = AddAlbumsActivity.this.username = list.get(0);
                        }
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void getUri(Task<UploadTask.TaskSnapshot> task) {
        final String[] downloadUri = {null};
        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    public void onSuccess(Uri uri) {
                        downloadUri[0] = uri.toString();
                        AddAlbumsActivity.this.makeTheMap(downloadUri[0], AddAlbumsActivity.this.getAlbumDescription(), AddAlbumsActivity.this.getNameOfNewAlbum(), FieldValue.serverTimestamp());
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public String getNameOfNewAlbum() {
        return this.nameNewAlbum.getText().toString();
    }

    /* access modifiers changed from: private */
    public void makeTheMap(String downloadUriString, String descriptionOfAlbum, String nameOfNewAlbum, FieldValue serverTimeStamp) {
        Map<String, Object> newAlbumMap = new HashMap<>();
        newAlbumMap.put("imageUri", downloadUriString);
        newAlbumMap.put("description", descriptionOfAlbum);
        newAlbumMap.put(AppMeasurementSdk.ConditionalUserProperty.NAME, nameOfNewAlbum);
        newAlbumMap.put("timeStamp", serverTimeStamp);
        this.firebaseFirestore.collection("Gallery").document("gallery_document_of:" + this.userId).collection("galleries_image_cover").document(nameOfNewAlbum).set(newAlbumMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            public void onSuccess(Void aVoid) {
                AddAlbumsActivity.this.sendToMainActivity();
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Log.d("failed", "Error", e);
            }
        });
    }

    private void sendToAlbumsFragment() {
        new FolderFragment();
    }

    /* access modifiers changed from: private */
    public void sendToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /* access modifiers changed from: private */
    public String getAlbumDescription() {
        return this.albumDescription.getText().toString();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
    }

    /* access modifiers changed from: private */
    public void sendToAddMoreImagesToExistingAlbum() {
        startActivity(new Intent(this, AddMoreImagesToExistingAlbum.class));
        finish();
    }
}
