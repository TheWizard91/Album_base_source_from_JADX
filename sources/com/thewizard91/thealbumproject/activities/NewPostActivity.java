package com.thewizard91.thealbumproject.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.thewizard91.thealbumproject.C2521R;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import p018id.zelory.compressor.Compressor;

public class NewPostActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public Bitmap compressedImageFile;
    private FirebaseFirestore firebaseFirestore;
    private Button newPostButton;
    /* access modifiers changed from: private */
    public EditText newPostDescription;
    /* access modifiers changed from: private */
    public Uri newPostImageURI;
    private ImageView newPostImageView;
    /* access modifiers changed from: private */
    public EditText newPostLocation;
    /* access modifiers changed from: private */
    public ProgressBar newPostProgressBar;
    /* access modifiers changed from: private */
    public String randomName;
    /* access modifiers changed from: private */
    public StorageReference storageReference;
    /* access modifiers changed from: private */
    public String userId;
    /* access modifiers changed from: private */
    public String username;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C2521R.C2526layout.activity_new_post);
        this.newPostProgressBar = (ProgressBar) findViewById(C2521R.C2524id.new_post_progressbar);
        this.newPostImageView = (ImageView) findViewById(C2521R.C2524id.new_post_image);
        this.newPostDescription = (EditText) findViewById(C2521R.C2524id.new_post_description);
        this.newPostLocation = (EditText) findViewById(C2521R.C2524id.location_id);
        this.newPostButton = (Button) findViewById(C2521R.C2524id.new_post_button);
        this.storageReference = FirebaseStorage.getInstance().getReference();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            this.userId = currentUser.getUid();
            setUsername();
            selectImageToPost();
            sendNewPost();
            return;
        }
        sendToLoginActivity();
    }

    private void sendToLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
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
                        String unused = NewPostActivity.this.username = list.get(0);
                    }
                }
            }
        });
    }

    private void selectImageToPost() {
        this.newPostImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NewPostActivity.this.pickTheImageAndCropIt();
            }
        });
    }

    /* access modifiers changed from: private */
    public void pickTheImageAndCropIt() {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setMinCropResultSize(512, 512).setAspectRatio(1, 1).start(this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 203) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == -1) {
                Uri uri = result.getUri();
                this.newPostImageURI = uri;
                this.newPostImageView.setImageURI(uri);
            } else if (resultCode == 204) {
                Toast.makeText(this, "" + result.getError(), 0).show();
            }
        }
    }

    private void sendNewPost() {
        this.newPostButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String description = NewPostActivity.this.newPostDescription.getText().toString();
                final String location = NewPostActivity.this.newPostLocation.getText().toString();
                if (!TextUtils.isEmpty(description) && NewPostActivity.this.newPostImageURI != null) {
                    String unused = NewPostActivity.this.randomName = UUID.randomUUID().toString();
                    NewPostActivity.this.storageReference.child("storage_of: " + NewPostActivity.this.username).child("post_images").child(NewPostActivity.this.randomName + ".jpg").putFile(NewPostActivity.this.newPostImageURI).addOnCompleteListener((OnCompleteListener) new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        public void onComplete(final Task<UploadTask.TaskSnapshot> task) {
                            final String[] downloadUri = {null};
                            if (task.isSuccessful()) {
                                try {
                                    Bitmap unused = NewPostActivity.this.compressedImageFile = new Compressor(NewPostActivity.this).setMaxHeight(100).setMaxWidth(100).setQuality(2).compressToBitmap(new File((String) Objects.requireNonNull(NewPostActivity.this.newPostImageURI.getPath())));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                NewPostActivity.this.compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                                NewPostActivity.this.storageReference.child("post_images_for_everyone_to_see/").child(NewPostActivity.this.randomName + ".jpg").putBytes(byteArrayOutputStream.toByteArray()).addOnSuccessListener((OnSuccessListener) new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    String downloadThumbURI;

                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    public void onSuccess(Uri uri) {
                                                        downloadUri[0] = uri.toString();
                                                        NewPostActivity.this.makeTheMap(downloadUri[0], description, NewPostActivity.this.userId, FieldValue.serverTimestamp(), C25641.this.downloadThumbURI, location);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void makeTheMap(String s, String description, String currentUserId, FieldValue serverTimestamp, String downloadThumbURI, String location) {
        Map<String, Object> newPostMap = new HashMap<>();
        newPostMap.put("imageURI", s);
        newPostMap.put("description", description);
        newPostMap.put("thumbnailURI", downloadThumbURI);
        newPostMap.put("userId", currentUserId);
        newPostMap.put("timestamp", serverTimestamp);
        newPostMap.put("username", this.username);
        newPostMap.put(FirebaseAnalytics.Param.LOCATION, location);
        addPostToEveryUserFirebaseFireStoreSpace(newPostMap);
        addPostToUniqueUserFirebaseFireStoreSpace(newPostMap);
    }

    private void addPostToEveryUserFirebaseFireStoreSpace(Map<String, Object> newPostMap) {
        this.firebaseFirestore.collection("Posts").add(newPostMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            public void onComplete(Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    NewPostActivity.this.newPostProgressBar.setVisibility(0);
                    Toast.makeText(NewPostActivity.this, "The Post Was Added", 0).show();
                    NewPostActivity.this.sendToMainActivity();
                }
            }
        });
    }

    private void addPostToUniqueUserFirebaseFireStoreSpace(Map<String, Object> newPostMap) {
        this.firebaseFirestore.collection("Gallery").document("gallery_document_of:" + this.userId).collection("gallery_collection_of:" + this.userId).document("images_from_posts").collection("posts").add(newPostMap);
        this.firebaseFirestore.collection("Gallery").document("gallery_document_of:" + this.userId).collection("images").add(newPostMap);
    }

    /* access modifiers changed from: private */
    public void sendToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
