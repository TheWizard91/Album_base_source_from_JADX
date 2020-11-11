package com.thewizard91.thealbumproject.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.thewizard91.thealbumproject.C2521R;
import com.thewizard91.thealbumproject.models.comments.CommentsModel;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import p017de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String blogPostId;
    private List<CommentsModel> commentsModelList;
    /* access modifiers changed from: private */
    public Context context;
    private FirebaseAuth firebaseAuth;
    /* access modifiers changed from: private */
    public FirebaseFirestore firebaseFirestore;

    public CommentsAdapter(List<CommentsModel> commentsModelList2, String blogPostId2) {
        this.commentsModelList = commentsModelList2;
        this.blogPostId = blogPostId2;
    }

    public CommentsAdapter(List<CommentsModel> commentsModelList2) {
        this.commentsModelList = commentsModelList2;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(C2521R.C2526layout.comment_section, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
        this.context = parent.getContext();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
        return new CommentsViewHolder(view);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        boolean z = false;
        holder.setIsRecyclable(false);
        CommentsViewHolder commentsViewHolder = (CommentsViewHolder) holder;
        String currentUserId = ((FirebaseUser) Objects.requireNonNull(this.firebaseAuth.getCurrentUser())).getUid();
        String currentUserIdAndCommentsId = this.commentsModelList.get(position).CommentsModelId;
        String commentUserId = this.commentsModelList.get(position).getUserId();
        setUserData(commentsViewHolder, commentUserId);
        if (commentUserId == null) {
            z = true;
        }
        Log.d("commentUserIdIs", String.valueOf(z));
        commentsViewHolder.setDate(DateFormat.format("MM/dd/yyyy", new Date(this.commentsModelList.get(position).getTimestamp().getTime())).toString());
        refreshAdapter(this.commentsModelList);
        commentsViewHolder.setCommentPosted(this.commentsModelList.get(position).getCommentText());
        commentsViewHolder.setThumbsUpImageURI(this.commentsModelList.get(position).getThumbsUpImageUri());
        setLikesCount(commentsViewHolder, this.blogPostId, currentUserIdAndCommentsId);
        setLikes(commentsViewHolder, this.blogPostId, currentUserIdAndCommentsId, currentUserId);
        addAndDeleteLikes(commentsViewHolder, this.blogPostId, currentUserIdAndCommentsId, currentUserId);
        deleteCommentsAndLikes(commentsViewHolder, currentUserIdAndCommentsId, currentUserId);
        replayComments(commentsViewHolder, this.blogPostId, currentUserIdAndCommentsId, currentUserId);
    }

    private void replayComments(CommentsViewHolder commentsViewHolder, String blogPostId2, String commentsId, String currentUserId) {
    }

    private void deleteCommentsAndLikes(CommentsViewHolder commentsViewHolder, final String currentUserIdAndCommentsId, final String currentUserId) {
        commentsViewHolder.deleteButtonView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CommentsAdapter.this.deleteLikesOfSelectedComment(currentUserIdAndCommentsId, currentUserId);
                CommentsAdapter.this.deleteSelectedComments(currentUserIdAndCommentsId);
                Toast.makeText(CommentsAdapter.this.context, "Comment Deleted!", 0).show();
            }
        });
        refreshAdapter(this.commentsModelList);
    }

    private void refreshAdapter(List<CommentsModel> commentsModelList2) {
        new CommentsAdapter(commentsModelList2).notifyDataSetChanged();
    }

    /* access modifiers changed from: private */
    public void deleteLikesOfSelectedComment(String currentUserIdAndCommentsId, String currentUserId) {
        this.firebaseFirestore.collection("Posts").document(this.blogPostId).collection("Comments").document(currentUserIdAndCommentsId).collection("Likes").document(currentUserId).delete();
    }

    /* access modifiers changed from: private */
    public void deleteSelectedComments(String currentUserIdAndCommentsId) {
        this.firebaseFirestore.collection("Posts").document(this.blogPostId).collection("Comments").document(currentUserIdAndCommentsId).delete();
    }

    private void addAndDeleteLikes(CommentsViewHolder commentsViewHolder, final String blogPostId2, final String currentUserIdAndCommentsId, final String currentUserId) {
        commentsViewHolder.thumbsUpView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CommentsAdapter.this.firebaseFirestore.collection("Posts").document(blogPostId2).collection("Comments").document(currentUserIdAndCommentsId).collection("Likes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (!task.getResult().exists()) {
                            Map<String, Object> commentsLikes = new HashMap<>();
                            commentsLikes.put("timestamp", FieldValue.serverTimestamp());
                            CommentsAdapter.this.firebaseFirestore.collection("Posts").document(blogPostId2).collection("Comments").document(currentUserIdAndCommentsId).collection("Likes").document(currentUserId).set(commentsLikes);
                            Toast.makeText(CommentsAdapter.this.context, "Added a like to ", 0).show();
                            return;
                        }
                        CommentsAdapter.this.firebaseFirestore.collection("Posts").document(blogPostId2).collection("Comments").document(currentUserIdAndCommentsId).collection("Likes").document(currentUserId).delete();
                        Toast.makeText(CommentsAdapter.this.context, "Removed a like to ", 0).show();
                    }
                });
            }
        });
    }

    private void setLikes(final CommentsViewHolder commentsViewHolder, String blogPostId2, String currentUserIdAndCommentsId, String currentUserId) {
        this.firebaseFirestore.collection("Posts").document(blogPostId2).collection("Comments").document(currentUserIdAndCommentsId).collection("Likes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<CommentsAdapter> cls = CommentsAdapter.class;
            }

            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (documentSnapshot == null) {
                    throw new AssertionError();
                } else if (documentSnapshot.exists()) {
                    commentsViewHolder.thumbsUpView.setImageDrawable(CommentsAdapter.this.context.getDrawable(C2521R.C2523drawable.sharp_favorite_black_24dp));
                } else {
                    commentsViewHolder.thumbsUpView.setImageDrawable(CommentsAdapter.this.context.getDrawable(C2521R.C2523drawable.baseline_favorite_border_black_24dp));
                }
            }
        });
    }

    private void setLikesCount(final CommentsViewHolder commentsViewHolder, String blogPostId2, String currentUserIdAndCommentsId) {
        this.firebaseFirestore.collection("Posts").document(blogPostId2).collection("Comments").document(currentUserIdAndCommentsId).collection("Likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<CommentsAdapter> cls = CommentsAdapter.class;
            }

            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (queryDocumentSnapshots == null) {
                    throw new AssertionError();
                } else if (!queryDocumentSnapshots.isEmpty()) {
                    commentsViewHolder.updateLikesCounts(queryDocumentSnapshots.size());
                }
            }
        });
    }

    private void setUserData(final CommentsViewHolder commentsViewHolder, String commentUserId) {
        this.firebaseFirestore.collection("Users").document(commentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    commentsViewHolder.setUserImageUriAndUsername(task.getResult().getString("profile_name_of"), task.getResult().getString("profile_image"));
                    return;
                }
                commentsViewHolder.updateLikesCounts(0);
            }
        });
    }

    public int getItemCount() {
        return this.commentsModelList.size();
    }

    private class CommentsViewHolder extends RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        public CircleImageView deleteButtonView = ((CircleImageView) this.view.findViewById(C2521R.C2524id.comments_clear));
        /* access modifiers changed from: private */
        public ImageView thumbsUpView;
        private View view;

        public CommentsViewHolder(View view2) {
            super(view2);
            this.view = view2;
            this.thumbsUpView = (ImageView) view2.findViewById(C2521R.C2524id.thumb_up);
        }

        public void setCommentPosted(String commentPosted) {
            ((TextView) this.view.findViewById(C2521R.C2524id.comment)).setText(commentPosted);
        }

        public void setUserImageUriAndUsername(String username, String userImageUri) {
            ((TextView) this.view.findViewById(C2521R.C2524id.comments_username)).setText("Posted By: " + username);
            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder((int) C2521R.C2523drawable.baseline_account_circle_black_24dp);
            Glide.with(CommentsAdapter.this.context).applyDefaultRequestOptions(placeholderOption).load(userImageUri).into((ImageView) (CircleImageView) this.view.findViewById(C2521R.C2524id.comments_user_image));
        }

        public void setDate(String date) {
            ((TextView) this.view.findViewById(C2521R.C2524id.comments_date)).setText("Posted On: " + date);
        }

        public void setThumbsUpImageURI(String thumpsUpImageURI) {
            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder((int) C2521R.C2523drawable.baseline_favorite_border_black_24dp);
            Glide.with(CommentsAdapter.this.context).applyDefaultRequestOptions(placeholderOption).load(thumpsUpImageURI).into((ImageView) this.view.findViewById(C2521R.C2524id.thumb_up));
        }

        public void updateLikesCounts(int count) {
            ((TextView) this.view.findViewById(C2521R.C2524id.thumb_up_like_count)).setText(count + "Likes");
        }
    }
}
