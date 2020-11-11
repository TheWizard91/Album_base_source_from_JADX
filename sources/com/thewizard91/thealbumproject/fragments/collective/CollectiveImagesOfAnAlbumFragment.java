package com.thewizard91.thealbumproject.fragments.collective;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.thewizard91.thealbumproject.C2521R;
import com.thewizard91.thealbumproject.activities.AddMoreImagesToExistingAlbum;
import com.thewizard91.thealbumproject.adapters.ClickedOnAlbumAdapter;
import com.thewizard91.thealbumproject.models.choosenalbum.SingleImageModel;
import java.util.ArrayList;
import java.util.List;

public class CollectiveImagesOfAnAlbumFragment extends Fragment {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int NUMBEROFCOLUMNS = 3;
    private String albumName;
    private AsymmetricGridView asymmetryListView;
    /* access modifiers changed from: private */
    public ClickedOnAlbumAdapter clickedOnAlbumAdapter;
    /* access modifiers changed from: private */
    public Context context;
    private FirebaseFirestore firebaseFirestore;
    /* access modifiers changed from: private */
    public List<SingleImageModel> imageModelList;
    /* access modifiers changed from: private */
    public Boolean isFirstPageLoaded = true;
    /* access modifiers changed from: private */
    public DocumentSnapshot lastVisible;
    /* access modifiers changed from: private */
    public boolean reachedBottom;
    /* access modifiers changed from: private */
    public SingleImageModel singleImageModel;
    /* access modifiers changed from: private */
    public String singleImageModelId;
    private RecyclerView singleImageView;
    private String userId;
    private String username;

    public CollectiveImagesOfAnAlbumFragment() {
    }

    public CollectiveImagesOfAnAlbumFragment(String albumName2) {
        this.albumName = albumName2;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(C2521R.C2526layout.fragment_collective_images_of_an_album, container, false);
        this.context = container.getContext();
        this.singleImageView = (RecyclerView) view.findViewById(C2521R.C2524id.list_of_images);
        this.imageModelList = new ArrayList();
        this.clickedOnAlbumAdapter = new ClickedOnAlbumAdapter(this.imageModelList);
        this.singleImageView.setLayoutManager(new GridLayoutManager(this.context, 3));
        this.singleImageView.setAdapter(this.clickedOnAlbumAdapter);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            this.userId = currentUser.getUid();
            keepScrolling();
            createFirstQuery();
            return view;
        }
        throw new AssertionError();
    }

    private void keepScrolling() {
        this.singleImageView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean unused = CollectiveImagesOfAnAlbumFragment.this.reachedBottom = !recyclerView.canScrollVertically(1);
                if (CollectiveImagesOfAnAlbumFragment.this.reachedBottom) {
                    Toast.makeText(CollectiveImagesOfAnAlbumFragment.this.context, "Reached bottom, this image belongs to " + CollectiveImagesOfAnAlbumFragment.this.lastVisible.getString("name_of_album_it_belongs") + " album", 1).show();
                    CollectiveImagesOfAnAlbumFragment.this.loadMoreImages();
                }
            }
        });
    }

    private void createFirstQuery() {
        this.firebaseFirestore.collection("Gallery").document("gallery_document_of:" + this.userId).collection("images").document("images_of_" + this.albumName).collection(this.albumName).orderBy("date", Query.Direction.ASCENDING).limit(6).addSnapshotListener(new EventListener<QuerySnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<CollectiveImagesOfAnAlbumFragment> cls = CollectiveImagesOfAnAlbumFragment.class;
            }

            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(CollectiveImagesOfAnAlbumFragment.this.context, "No image has been added to this album yet.", 0).show();
                    } else {
                        DocumentSnapshot unused = CollectiveImagesOfAnAlbumFragment.this.lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                    }
                    for (DocumentChange document : queryDocumentSnapshots.getDocumentChanges()) {
                        if (document.getType() == DocumentChange.Type.ADDED) {
                            String unused2 = CollectiveImagesOfAnAlbumFragment.this.singleImageModelId = document.getDocument().getId();
                            SingleImageModel unused3 = CollectiveImagesOfAnAlbumFragment.this.singleImageModel = (SingleImageModel) ((SingleImageModel) document.getDocument().toObject(SingleImageModel.class)).withId(CollectiveImagesOfAnAlbumFragment.this.singleImageModelId);
                            CollectiveImagesOfAnAlbumFragment.this.imageModelList.add(CollectiveImagesOfAnAlbumFragment.this.singleImageModel);
                            CollectiveImagesOfAnAlbumFragment.this.clickedOnAlbumAdapter.notifyDataSetChanged();
                        }
                    }
                    return;
                }
                throw new AssertionError();
            }
        });
    }

    private void sendToAddMoreImagesToExistingAlbum() {
        startActivity(new Intent(this.context, AddMoreImagesToExistingAlbum.class));
    }

    /* access modifiers changed from: private */
    public void loadMoreImages() {
        this.firebaseFirestore.collection("Gallery").document("gallery_document_of:" + this.userId).collection("images").document("images_of_" + this.albumName).collection(this.albumName).orderBy("date", Query.Direction.ASCENDING).startAfter(this.lastVisible).limit(6).addSnapshotListener(new EventListener<QuerySnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<CollectiveImagesOfAnAlbumFragment> cls = CollectiveImagesOfAnAlbumFragment.class;
            }

            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (queryDocumentSnapshots == null) {
                    throw new AssertionError();
                } else if (!queryDocumentSnapshots.isEmpty()) {
                    DocumentSnapshot unused = CollectiveImagesOfAnAlbumFragment.this.lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                    for (DocumentChange documents : queryDocumentSnapshots.getDocumentChanges()) {
                        if (documents.getType() == DocumentChange.Type.ADDED) {
                            String unused2 = CollectiveImagesOfAnAlbumFragment.this.singleImageModelId = documents.getDocument().getId();
                            SingleImageModel unused3 = CollectiveImagesOfAnAlbumFragment.this.singleImageModel = (SingleImageModel) ((SingleImageModel) documents.getDocument().toObject(SingleImageModel.class)).withId(CollectiveImagesOfAnAlbumFragment.this.singleImageModelId);
                            if (CollectiveImagesOfAnAlbumFragment.this.isFirstPageLoaded.booleanValue()) {
                                CollectiveImagesOfAnAlbumFragment.this.imageModelList.add(CollectiveImagesOfAnAlbumFragment.this.singleImageModel);
                            } else {
                                CollectiveImagesOfAnAlbumFragment.this.imageModelList.add(CollectiveImagesOfAnAlbumFragment.this.imageModelList.size(), CollectiveImagesOfAnAlbumFragment.this.singleImageModel);
                            }
                        }
                    }
                    Boolean unused4 = CollectiveImagesOfAnAlbumFragment.this.isFirstPageLoaded = false;
                }
            }
        });
    }
}
