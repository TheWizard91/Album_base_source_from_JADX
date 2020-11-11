package com.thewizard91.thealbumproject.fragments.toplevelfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.thewizard91.thealbumproject.adapters.AlbumCollectionAdapter;
import com.thewizard91.thealbumproject.models.albums.AlbumModel;
import java.util.ArrayList;
import java.util.List;

public class FolderFragment extends Fragment {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    /* access modifiers changed from: private */
    public AlbumCollectionAdapter albumCollectionAdapter;
    /* access modifiers changed from: private */
    public String albumId;
    /* access modifiers changed from: private */
    public List<AlbumModel> albumList;
    private RecyclerView albumListView;
    /* access modifiers changed from: private */
    public AlbumModel albumModel;
    /* access modifiers changed from: private */
    public Context context;
    private FirebaseFirestore firebaseFirestore;
    /* access modifiers changed from: private */
    public Boolean isFirstPageFirstLoad = true;
    /* access modifiers changed from: private */
    public DocumentSnapshot lastVisible;
    /* access modifiers changed from: private */
    public boolean reachedBottom;
    private String userId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(C2521R.C2526layout.fragment_list_of_albums, container, false);
        this.context = container.getContext();
        this.albumListView = (RecyclerView) view.findViewById(C2521R.C2524id.list_of_albums);
        this.albumList = new ArrayList();
        this.albumCollectionAdapter = new AlbumCollectionAdapter(this.albumList);
        this.albumListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.albumListView.setAdapter(this.albumCollectionAdapter);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            this.userId = currentUser.getUid();
            createFirsQuery();
            asTheUserScrolls();
            return view;
        }
        throw new AssertionError();
    }

    private void asTheUserScrolls() {
        this.albumListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean unused = FolderFragment.this.reachedBottom = !recyclerView.canScrollVertically(1);
                if (FolderFragment.this.reachedBottom) {
                    Toast.makeText(FolderFragment.this.context, "Reached end album is: " + FolderFragment.this.lastVisible.getString("description"), 0).show();
                    Boolean unused2 = FolderFragment.this.isFirstPageFirstLoad = true;
                    FolderFragment.this.loadMoreAlbums();
                }
            }
        });
    }

    private void createFirsQuery() {
        this.firebaseFirestore.collection("Gallery").document("gallery_document_of:" + this.userId).collection("galleries_image_cover").orderBy("timeStamp", Query.Direction.DESCENDING).limit(2).addSnapshotListener(new EventListener<QuerySnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<FolderFragment> cls = FolderFragment.class;
            }

            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(FolderFragment.this.context, "No Album has been created yet.", 0).show();
                    } else {
                        DocumentSnapshot unused = FolderFragment.this.lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                    }
                    for (DocumentChange document : queryDocumentSnapshots.getDocumentChanges()) {
                        if (document.getType() == DocumentChange.Type.ADDED) {
                            String unused2 = FolderFragment.this.albumId = document.getDocument().getId();
                            AlbumModel unused3 = FolderFragment.this.albumModel = (AlbumModel) ((AlbumModel) document.getDocument().toObject(AlbumModel.class)).withId(FolderFragment.this.albumId);
                            FolderFragment.this.albumList.add(FolderFragment.this.albumList.size(), FolderFragment.this.albumModel);
                            FolderFragment.this.albumCollectionAdapter.notifyDataSetChanged();
                        }
                    }
                    return;
                }
                throw new AssertionError();
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadMoreAlbums() {
        this.firebaseFirestore.collection("Gallery").document("gallery_document_of:" + this.userId).collection("galleries_image_cover").orderBy("timeStamp", Query.Direction.DESCENDING).startAfter(this.lastVisible).limit(2).addSnapshotListener(new EventListener<QuerySnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<FolderFragment> cls = FolderFragment.class;
            }

            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (queryDocumentSnapshots == null) {
                    throw new AssertionError();
                } else if (!queryDocumentSnapshots.isEmpty()) {
                    if (FolderFragment.this.isFirstPageFirstLoad.booleanValue()) {
                        DocumentSnapshot unused = FolderFragment.this.lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                    }
                    for (DocumentChange documents : queryDocumentSnapshots.getDocumentChanges()) {
                        if (documents.getType() == DocumentChange.Type.ADDED) {
                            String unused2 = FolderFragment.this.albumId = documents.getDocument().getId();
                            AlbumModel unused3 = FolderFragment.this.albumModel = (AlbumModel) ((AlbumModel) documents.getDocument().toObject(AlbumModel.class)).withId(FolderFragment.this.albumId);
                            FolderFragment.this.albumList.add(FolderFragment.this.albumList.size(), FolderFragment.this.albumModel);
                            FolderFragment.this.albumCollectionAdapter.notifyDataSetChanged();
                        }
                    }
                    Boolean unused4 = FolderFragment.this.isFirstPageFirstLoad = false;
                }
            }
        });
    }
}
