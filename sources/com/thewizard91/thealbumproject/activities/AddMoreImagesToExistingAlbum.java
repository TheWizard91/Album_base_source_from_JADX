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
import androidx.appcompat.app.AppCompatActivity;
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import p018id.zelory.compressor.Compressor;

public class AddMoreImagesToExistingAlbum extends AppCompatActivity {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Button addImageButton;
    /* access modifiers changed from: private */
    public ImageView addImageView;
    private FirebaseFirestore firebaseFirestore;
    /* access modifiers changed from: private */
    public Uri imageUri;
    /* access modifiers changed from: private */
    public ProgressBar progressBar;
    /* access modifiers changed from: private */
    public TextView selectedAlbum;
    /* access modifiers changed from: private */
    public StorageReference storageReference;
    private String userId;
    /* access modifiers changed from: private */
    public String username;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2521R.C2526layout.activity_add_more_images_to_existing_album);
        this.progressBar = (ProgressBar) findViewById(C2521R.C2524id._add_image_to_existing_album_progressbar);
        this.selectedAlbum = (TextView) findViewById(C2521R.C2524id.insert_name_of_existing_album);
        this.addImageView = (ImageView) findViewById(C2521R.C2524id.insert_image_for_existing_album);
        this.addImageButton = (Button) findViewById(C2521R.C2524id.add_image_to_chosen_album);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            this.userId = currentUser.getUid();
            setUsername();
            triggerImageHandler();
            triggerButtonHandler();
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: private */
    public void pickImageToBeAdded() {
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
                this.addImageView.setImageURI(uri);
            } else if (resultCode == 204) {
                result.getError();
            }
        }
    }

    private void triggerButtonHandler() {
        this.addImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nameOfAlbumSelected = AddMoreImagesToExistingAlbum.this.selectedAlbum.getText().toString();
                String randomNameGeneratedForImage = UUID.randomUUID().toString();
                if (!TextUtils.isEmpty(nameOfAlbumSelected) && AddMoreImagesToExistingAlbum.this.addImageView != null) {
                    AddMoreImagesToExistingAlbum.this.storageReference.child("storage_of: " + AddMoreImagesToExistingAlbum.this.username).child(nameOfAlbumSelected).child("images").child(randomNameGeneratedForImage + ".jpg").putFile(AddMoreImagesToExistingAlbum.this.imageUri).addOnCompleteListener((OnCompleteListener) new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        public void onComplete(Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                try {
                                    new Compressor(AddMoreImagesToExistingAlbum.this).setMaxHeight(100).setMaxWidth(100).compressToBitmap(new File((String) Objects.requireNonNull(AddMoreImagesToExistingAlbum.this.imageUri.getPath())));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                AddMoreImagesToExistingAlbum.this.geUri(task);
                            }
                            AddMoreImagesToExistingAlbum.this.progressBar.setVisibility(0);
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void geUri(Task<UploadTask.TaskSnapshot> task) {
        final String[] downloadUri = {null};
        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    public void onSuccess(Uri uri) {
                        downloadUri[0] = uri.toString();
                        AddMoreImagesToExistingAlbum.this.makeTheMap(downloadUri[0], AddMoreImagesToExistingAlbum.this.selectedAlbum.getText().toString(), FieldValue.serverTimestamp());
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public void makeTheMap(String image_uri, String nameOfAlbumItBelongsTo, FieldValue date) {
        Map<String, Object> nameOfChosenAlbumMap = new HashMap<>();
        nameOfChosenAlbumMap.put("imageUri", image_uri);
        nameOfChosenAlbumMap.put("name_of_album_it_belongs", nameOfAlbumItBelongsTo);
        nameOfChosenAlbumMap.put("date", date);
        this.firebaseFirestore.collection("Gallery").document("gallery_document_of:" + this.userId).collection("images").document("images_of_" + this.selectedAlbum.getText().toString()).collection(nameOfAlbumItBelongsTo).document().set(nameOfChosenAlbumMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            public void onSuccess(Void aVoid) {
                AddMoreImagesToExistingAlbum.this.sendToMainActivity();
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Log.d("failed", "Error", e);
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void setUsername() {
        this.firebaseFirestore.collection("Users").document(this.userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {
                    List<String> list = new ArrayList<>();
                    Map<String, Object> map = documentSnapshot.getData();
                    if (map != null) {
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            list.add(entry.getValue().toString());
                        }
                        String unused = AddMoreImagesToExistingAlbum.this.username = list.get(0);
                    }
                }
            }
        });
    }

    private void triggerImageHandler() {
        this.addImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddMoreImagesToExistingAlbum.this.pickImageToBeAdded();
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
    }
}
