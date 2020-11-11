package com.thewizard91.thealbumproject.fragments.toplevelfragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.thewizard91.thealbumproject.C2521R;
import com.thewizard91.thealbumproject.adapters.BlogPostAdapter;
import com.thewizard91.thealbumproject.models.post.BlogPostImageModel;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private RecyclerView blogListView;
    /* access modifiers changed from: private */
    public BlogPostAdapter blogPostAdapter;
    /* access modifiers changed from: private */
    public BlogPostImageModel blogPostImageModel;
    /* access modifiers changed from: private */
    public List<BlogPostImageModel> blogPostImageModelList;
    /* access modifiers changed from: private */
    public Context context;
    private FirebaseFirestore firebaseFirestore;
    /* access modifiers changed from: private */
    public boolean isTheFirstPageLoaded = true;
    /* access modifiers changed from: private */
    public DocumentSnapshot lastVisiblePost;
    private ActionBar mainActivityActionBar;
    private FloatingActionButton mainActivityFloatingActionButton;
    /* access modifiers changed from: private */
    public String postId;
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(C2521R.C2526layout.fragment_home, container, false);
        this.view = inflate;
        this.blogListView = (RecyclerView) inflate.findViewById(C2521R.C2524id.home_fragment_recycler);
        this.context = container.getContext();
        this.blogPostImageModelList = new ArrayList();
        this.blogPostAdapter = new BlogPostAdapter(this.blogPostImageModelList);
        this.blogListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.blogListView.setAdapter(this.blogPostAdapter);
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            createTheFirstQuery();
            showContentAsTheUserScrolls();
            return this.view;
        }
        throw new AssertionError();
    }

    public void onStart() {
        super.onStart();
    }

    public void onDetach() {
        super.onDetach();
        hideMainActivityFloatingActionButton();
    }

    public void onAttach(Context context2) {
        super.onAttach(context2);
        Log.d("ContextAdded?", context2.toString());
    }

    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d("Am I inside?", "MSG");
        hideMainActivityFloatingActionButton();
        Log.d("Still outside?", "MSG");
    }

    public void switchFragment(View view2, String blogPostId, String currentUserId) {
        ((FragmentActivity) view2.getContext()).getSupportFragmentManager().beginTransaction().replace(C2521R.C2524id.content_frame, new CommentsFragment(blogPostId, currentUserId)).commit();
    }

    private void createTheFirstQuery() {
        this.firebaseFirestore.collection("Posts").orderBy("timestamp", Query.Direction.DESCENDING).limit(2).addSnapshotListener(new EventListener<QuerySnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<HomeFragment> cls = HomeFragment.class;
            }

            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (queryDocumentSnapshots == null) {
                    throw new AssertionError();
                } else if (queryDocumentSnapshots.isEmpty()) {
                    Toast.makeText(HomeFragment.this.context, "There is no post yet.", 0).show();
                } else {
                    DocumentSnapshot unused = HomeFragment.this.lastVisiblePost = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                    for (DocumentChange document : queryDocumentSnapshots.getDocumentChanges()) {
                        if (document.getType() == DocumentChange.Type.ADDED) {
                            String unused2 = HomeFragment.this.postId = document.getDocument().getId();
                            BlogPostImageModel unused3 = HomeFragment.this.blogPostImageModel = (BlogPostImageModel) ((BlogPostImageModel) document.getDocument().toObject(BlogPostImageModel.class)).withId(HomeFragment.this.postId);
                            HomeFragment.this.blogPostImageModelList.add(HomeFragment.this.blogPostImageModel);
                            HomeFragment.this.blogPostAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    private void showContentAsTheUserScrolls() {
        this.blogListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    String postDescription = HomeFragment.this.lastVisiblePost.getString("description");
                    boolean unused = HomeFragment.this.isTheFirstPageLoaded = true;
                    Toast.makeText(HomeFragment.this.context, postDescription, 0).show();
                    HomeFragment.this.loadMorePosts();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadMorePosts() {
        this.firebaseFirestore.collection("Posts").orderBy("timestamp", Query.Direction.DESCENDING).startAfter(this.lastVisiblePost).limit(2).addSnapshotListener(new EventListener<QuerySnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<HomeFragment> cls = HomeFragment.class;
            }

            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (queryDocumentSnapshots == null) {
                    throw new AssertionError();
                } else if (!queryDocumentSnapshots.isEmpty()) {
                    if (HomeFragment.this.isTheFirstPageLoaded) {
                        DocumentSnapshot unused = HomeFragment.this.lastVisiblePost = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                    }
                    for (DocumentChange documents : queryDocumentSnapshots.getDocumentChanges()) {
                        if (documents.getType() == DocumentChange.Type.ADDED) {
                            String unused2 = HomeFragment.this.postId = documents.getDocument().getId();
                            BlogPostImageModel unused3 = HomeFragment.this.blogPostImageModel = (BlogPostImageModel) ((BlogPostImageModel) documents.getDocument().toObject(BlogPostImageModel.class)).withId(HomeFragment.this.postId);
                            HomeFragment.this.blogPostImageModelList.add(HomeFragment.this.blogPostImageModelList.size(), HomeFragment.this.blogPostImageModel);
                            HomeFragment.this.blogPostAdapter.notifyDataSetChanged();
                        }
                    }
                    boolean unused4 = HomeFragment.this.isTheFirstPageLoaded = false;
                }
            }
        });
    }

    public void hideMainActivityFloatingActionButton() {
        this.mainActivityFloatingActionButton.hide();
    }

    public void setMainActivityFloatingActionButton(FloatingActionButton floatingActionButton) {
        this.mainActivityFloatingActionButton = floatingActionButton;
    }
}
