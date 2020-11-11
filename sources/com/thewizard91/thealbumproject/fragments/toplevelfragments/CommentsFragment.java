package com.thewizard91.thealbumproject.fragments.toplevelfragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.thewizard91.thealbumproject.C2521R;
import com.thewizard91.thealbumproject.adapters.CommentsAdapter;
import com.thewizard91.thealbumproject.models.comments.CommentsModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CommentsFragment extends Fragment {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private String blogPostId;
    /* access modifiers changed from: private */
    public String commentId;
    /* access modifiers changed from: private */
    public TextView commentTextView;
    /* access modifiers changed from: private */
    public CommentsAdapter commentsAdapter;
    private RecyclerView commentsListView;
    /* access modifiers changed from: private */
    public CommentsModel commentsModel;
    /* access modifiers changed from: private */
    public List<CommentsModel> commentsModelList;
    /* access modifiers changed from: private */
    public Context context;
    /* access modifiers changed from: private */
    public String currentUserId;
    private FirebaseFirestore firebaseFirestore;
    /* access modifiers changed from: private */
    public Boolean isTheFirstPageOfCommentsLoaded = true;
    /* access modifiers changed from: private */
    public DocumentSnapshot lastVisibleComment;
    private ImageView sendCommentButtonView;

    public CommentsFragment() {
    }

    public CommentsFragment(String blogPostId2, String currentUserId2) {
        this.currentUserId = currentUserId2;
        this.blogPostId = blogPostId2;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(C2521R.C2526layout.fragment_comments, container, false);
        this.commentTextView = (TextView) view.findViewById(C2521R.C2524id.comments_space);
        this.sendCommentButtonView = (ImageView) view.findViewById(C2521R.C2524id.comments_send_button);
        this.commentsListView = (RecyclerView) view.findViewById(C2521R.C2524id.comments_recyclerview);
        this.context = container.getContext();
        this.commentsModelList = new ArrayList();
        this.commentsAdapter = new CommentsAdapter(this.commentsModelList, this.blogPostId);
        this.commentsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.commentsListView.setAdapter(this.commentsAdapter);
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            createTheFirstQuery();
            sendCommentToDatabase();
            return view;
        }
        throw new AssertionError();
    }

    public void onAttach(Context context2) {
        super.onAttach(context2);
    }

    public void onStart() {
        super.onStart();
        loadMoreCommentsAsTheUserScrollsDown();
    }

    private void loadMoreCommentsAsTheUserScrollsDown() {
        this.commentsListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(CommentsFragment.this.context, "Reached bottom" + CommentsFragment.this.lastVisibleComment.getString("commentText"), 0).show();
                    Boolean unused = CommentsFragment.this.isTheFirstPageOfCommentsLoaded = true;
                    CommentsFragment.this.loadMoreComments();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadMoreComments() {
        this.firebaseFirestore.collection("Posts").document(this.blogPostId).collection("Comments").orderBy("timestamp", Query.Direction.ASCENDING).startAfter(this.lastVisibleComment).limit(1).addSnapshotListener(new EventListener<QuerySnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<CommentsFragment> cls = CommentsFragment.class;
            }

            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (queryDocumentSnapshots == null) {
                    throw new AssertionError();
                } else if (!queryDocumentSnapshots.isEmpty()) {
                    if (CommentsFragment.this.isTheFirstPageOfCommentsLoaded.booleanValue()) {
                        DocumentSnapshot unused = CommentsFragment.this.lastVisibleComment = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                    }
                    for (DocumentChange documents : queryDocumentSnapshots.getDocumentChanges()) {
                        if (documents.getType() == DocumentChange.Type.ADDED) {
                            String unused2 = CommentsFragment.this.commentId = documents.getDocument().getId();
                            CommentsModel unused3 = CommentsFragment.this.commentsModel = (CommentsModel) ((CommentsModel) documents.getDocument().toObject(CommentsModel.class)).withId(CommentsFragment.this.commentId);
                            CommentsFragment.this.commentsModelList.add(CommentsFragment.this.commentsModelList.size(), CommentsFragment.this.commentsModel);
                            CommentsFragment.this.commentsAdapter.notifyDataSetChanged();
                        }
                    }
                    Boolean unused4 = CommentsFragment.this.isTheFirstPageOfCommentsLoaded = false;
                }
            }
        });
    }

    private void createTheFirstQuery() {
        this.firebaseFirestore.collection("Posts").document(this.blogPostId).collection("Comments").orderBy("timestamp", Query.Direction.ASCENDING).limit(1).addSnapshotListener(new EventListener<QuerySnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<CommentsFragment> cls = CommentsFragment.class;
            }

            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (queryDocumentSnapshots == null) {
                    throw new AssertionError();
                } else if (queryDocumentSnapshots.isEmpty()) {
                    Toast.makeText(CommentsFragment.this.context, "No comments on this post yet so be the first to comment!", 0).show();
                } else {
                    DocumentSnapshot unused = CommentsFragment.this.lastVisibleComment = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                    for (DocumentChange document : queryDocumentSnapshots.getDocumentChanges()) {
                        if (document.getType() == DocumentChange.Type.ADDED) {
                            String unused2 = CommentsFragment.this.commentId = document.getDocument().getId();
                            CommentsModel unused3 = CommentsFragment.this.commentsModel = (CommentsModel) ((CommentsModel) document.getDocument().toObject(CommentsModel.class)).withId(CommentsFragment.this.commentId);
                            CommentsFragment.this.commentsModelList.add(CommentsFragment.this.commentsModel);
                            CommentsFragment.this.commentsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    private void sendCommentToDatabase() {
        this.sendCommentButtonView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String comment = CommentsFragment.this.commentTextView.getText().toString();
                if (!TextUtils.isEmpty(comment)) {
                    CommentsFragment.this.makeTheMap(comment, FieldValue.serverTimestamp(), CommentsFragment.this.currentUserId);
                    new CommentsAdapter(CommentsFragment.this.commentsModelList);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void makeTheMap(String comment, FieldValue fieldValue, String currentUserId2) {
        Map<String, Object> newCommentMap = new HashMap<>();
        newCommentMap.put("commentText", comment);
        newCommentMap.put("timestamp", fieldValue);
        newCommentMap.put("userId", currentUserId2);
        this.firebaseFirestore.collection("Posts/" + this.blogPostId + "/Comments").document(this.currentUserId + ":" + UUID.randomUUID().toString()).set(newCommentMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(CommentsFragment.this.context, "There Was An Error Posting The Comment: " + task.getException(), 0).show();
                    return;
                }
                CommentsFragment.this.commentTextView.setText("");
                Toast.makeText(CommentsFragment.this.context, "Comment sent", 0).show();
            }
        });
    }
}
