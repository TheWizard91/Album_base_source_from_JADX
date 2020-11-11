package com.thewizard91.thealbumproject.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.thewizard91.thealbumproject.C2521R;
import com.thewizard91.thealbumproject.fragments.maps.MapsFragment;
import com.thewizard91.thealbumproject.fragments.toplevelfragments.HomeFragment;
import com.thewizard91.thealbumproject.models.post.BlogPostImageModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import p017de.hdodenhof.circleimageview.CircleImageView;

public class BlogPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /* access modifiers changed from: private */
    public List<BlogPostImageModel> blogPostImageModelList;
    /* access modifiers changed from: private */
    public Context context;
    private FirebaseAuth firebaseAuth;
    /* access modifiers changed from: private */
    public FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;

    public interface CallBackMethodHelperForSetCurrentHolderImageURICallback {
        void onCallback(String str);
    }

    public BlogPostAdapter(List<BlogPostImageModel> blogPostImageModels) {
        this.blogPostImageModelList = blogPostImageModels;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(C2521R.C2526layout.blog_post, parent, false);
        myView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
        this.context = parent.getContext();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.storageReference = FirebaseStorage.getInstance().getReference();
        return new ViewHolderOfBlogPost(myView);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        ViewHolderOfBlogPost holderOfBlogPost = (ViewHolderOfBlogPost) holder;
        String blogPostId = this.blogPostImageModelList.get(position).BlogPostImageModelId;
        String currentUserId = ((FirebaseUser) Objects.requireNonNull(this.firebaseAuth.getCurrentUser())).getUid();
        String postsUserId = this.blogPostImageModelList.get(position).getUserId();
        String description = this.blogPostImageModelList.get(position).getDescription();
        holderOfBlogPost.setDescription(description);
        setUsernameAndImage(holderOfBlogPost, postsUserId);
        holderOfBlogPost.setImageURIAndThumbnailURI(this.blogPostImageModelList.get(position).getImageURI(), this.blogPostImageModelList.get(position).getThumbnailURI());
        holderOfBlogPost.setTime(DateFormat.format("MM/dd/yyyy", new Date(this.blogPostImageModelList.get(position).getTimestamp().getTime())).toString());
        setLikesCount(holderOfBlogPost, blogPostId);
        setLikes(holderOfBlogPost, blogPostId, currentUserId);
        addOrDeleteLikes(holderOfBlogPost, blogPostId, currentUserId, description);
        clickOnCommentsImage(holderOfBlogPost, blogPostId, currentUserId);
        clickOnLocationImage(holderOfBlogPost, blogPostId);
        setLocation(holderOfBlogPost, blogPostId);
        clickOnDeletePostImage(holderOfBlogPost, blogPostId);
        setCommentsCount(holderOfBlogPost, blogPostId);
        holderOfBlogPost.zoom(position);
    }

    private void setLocation(final ViewHolderOfBlogPost holderOfBlogPost, String blogPostId) {
        this.firebaseFirestore.collection("Posts").document(blogPostId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(Task<DocumentSnapshot> task) {
                Map<String, Object> map;
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists() && (map = documentSnapshot.getData()) != null) {
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        if (FirebaseAnalytics.Param.LOCATION.equals(entry.getKey())) {
                            String location = entry.getValue().toString();
                            holderOfBlogPost.setLocationInViewer("Click To Open Maps");
                            holderOfBlogPost.sendUserToMapFragment(location);
                        }
                    }
                }
            }
        });
    }

    private void clickOnLocationImage(ViewHolderOfBlogPost holderOfBlogPost, final String blogPostId) {
        holderOfBlogPost.blogLocationButtonView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BlogPostAdapter.this.firebaseFirestore.collection("Post").document(blogPostId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(BlogPostAdapter.this.context, "This Post Does Not Contain A Location, Sorry.", 0).show();
                        } else {
                            Toast.makeText(BlogPostAdapter.this.context, "This image has no location", 0).show();
                        }
                    }
                });
            }
        });
    }

    private void setCommentsCount(final ViewHolderOfBlogPost holderOfBlogPost, String blogPostId) {
        this.firebaseFirestore.collection("Posts").document(blogPostId).collection("Comments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<BlogPostAdapter> cls = BlogPostAdapter.class;
            }

            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (queryDocumentSnapshots == null) {
                    throw new AssertionError();
                } else if (!queryDocumentSnapshots.isEmpty()) {
                    holderOfBlogPost.updateCommentsCount(queryDocumentSnapshots.size());
                } else {
                    holderOfBlogPost.updateCommentsCount(0);
                }
            }
        });
    }

    private void clickOnDeletePostImage(ViewHolderOfBlogPost holderOfBlogPost, final String blogPostId) {
        holderOfBlogPost.deletePostView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BlogPostAdapter.this.setCurrentHolderImageURI(blogPostId, new CallBackMethodHelperForSetCurrentHolderImageURICallback() {
                    public void onCallback(String postImageURI) {
                        BlogPostAdapter.this.deleteImageFromFirebaseStorageForCommonPosts(postImageURI);
                        BlogPostAdapter.this.deleteImageFromFirebaseStoreForUserAccessOnly(postImageURI);
                        BlogPostAdapter.this.deleteImageFromFirebaseFirestone(blogPostId);
                    }
                });
                BlogPostAdapter blogPostAdapter = BlogPostAdapter.this;
                blogPostAdapter.refreshAdapter(blogPostAdapter.blogPostImageModelList);
                Toast.makeText(BlogPostAdapter.this.context, "Post Deleted", 0).show();
            }
        });
    }

    /* access modifiers changed from: private */
    public void deleteImageFromFirebaseFirestone(String blogPostId) {
        this.firebaseFirestore.collection("Posts").document(blogPostId).delete();
    }

    /* access modifiers changed from: private */
    public void deleteImageFromFirebaseStoreForUserAccessOnly(String postImageURI) {
        this.storageReference.child("storage_of: t/").child("post_images").child(postImageURI).delete();
    }

    /* access modifiers changed from: private */
    public void deleteImageFromFirebaseStorageForCommonPosts(String postImageURI) {
        this.storageReference.child("post_images_for_everyone_to_see/").child(postImageURI).delete();
    }

    /* access modifiers changed from: private */
    public void setCurrentHolderImageURI(String blogPostId, final CallBackMethodHelperForSetCurrentHolderImageURICallback callBackMethodHelperForSetCurrentHolderImageURICallback) {
        this.firebaseFirestore.collection("Posts").document(blogPostId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(Task<DocumentSnapshot> task) {
                Map<String, Object> map;
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists() && (map = documentSnapshot.getData()) != null) {
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        if ("imageURI".equals(entry.getKey())) {
                            String currentHolderImageURI = entry.getValue().toString();
                            String imageIdJPG = currentHolderImageURI.substring(currentHolderImageURI.indexOf("%2Fpost_images%2F"));
                            callBackMethodHelperForSetCurrentHolderImageURICallback.onCallback(imageIdJPG.substring(17, imageIdJPG.indexOf("?")));
                            return;
                        }
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void refreshAdapter(List<BlogPostImageModel> blogPostImageModelList2) {
        new BlogPostAdapter(blogPostImageModelList2).notifyDataSetChanged();
    }

    private void clickOnCommentsImage(ViewHolderOfBlogPost holderOfBlogPost, final String blogPostId, final String currentUserId) {
        holderOfBlogPost.blogCommentButtonView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BlogPostAdapter.this.sendToCommentsFragment(view, blogPostId, currentUserId);
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendToCommentsFragment(View view, String blogPostId, String currentUserId) {
        new HomeFragment().switchFragment(view, blogPostId, currentUserId);
    }

    private void addOrDeleteLikes(ViewHolderOfBlogPost holderOfBlogPost, final String blogPostId, final String currentUserId, final String description) {
        holderOfBlogPost.blogLikeButtonView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BlogPostAdapter.this.firebaseFirestore.collection("Posts").document(blogPostId).collection("Likes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (!task.getResult().exists()) {
                            Map<String, Object> likestMap = new HashMap<>();
                            likestMap.put("timestamp", FieldValue.serverTimestamp());
                            BlogPostAdapter.this.firebaseFirestore.collection("Posts").document(blogPostId).collection("Likes").document(currentUserId).set(likestMap);
                            Toast.makeText(BlogPostAdapter.this.context, "Added a like to " + description, 1).show();
                            return;
                        }
                        BlogPostAdapter.this.firebaseFirestore.collection("Posts").document(blogPostId).collection("Likes").document(currentUserId).delete();
                        Toast.makeText(BlogPostAdapter.this.context, "Removed a like to " + description, 1).show();
                    }
                });
            }
        });
    }

    private void setLikes(final ViewHolderOfBlogPost h, String blogPostId, String currentUserId) {
        this.firebaseFirestore.collection("Posts").document(blogPostId).collection("Likes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<BlogPostAdapter> cls = BlogPostAdapter.class;
            }

            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (documentSnapshot == null) {
                    throw new AssertionError();
                } else if (documentSnapshot.exists()) {
                    h.blogLikeButtonView.setImageDrawable(BlogPostAdapter.this.context.getDrawable(C2521R.C2523drawable.sharp_favorite_black_24dp));
                } else {
                    h.blogLikeButtonView.setImageDrawable(BlogPostAdapter.this.context.getDrawable(C2521R.C2523drawable.baseline_favorite_border_black_24dp));
                }
            }
        });
    }

    private void setLikesCount(final ViewHolderOfBlogPost h, String blogPostId) {
        this.firebaseFirestore.collection("Posts").document(blogPostId).collection("Likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<BlogPostAdapter> cls = BlogPostAdapter.class;
            }

            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (queryDocumentSnapshots == null) {
                    throw new AssertionError();
                } else if (!queryDocumentSnapshots.isEmpty()) {
                    h.updateLikesCount(queryDocumentSnapshots.size());
                } else {
                    h.updateLikesCount(0);
                }
            }
        });
    }

    private void setUsernameAndImage(final ViewHolderOfBlogPost h, String postsUserId) {
        this.firebaseFirestore.collection("Users").document(String.valueOf(postsUserId)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    h.setUsernameAndUserProfileImage(task.getResult().getString("profile_name_of"), task.getResult().getString("profile_image"));
                }
            }
        });
    }

    public int getItemCount() {
        return this.blogPostImageModelList.size();
    }

    private class ViewHolderOfBlogPost extends RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        public ImageView blogCommentButtonView = ((ImageView) this.view.findViewById(C2521R.C2524id.new_post_comment));
        /* access modifiers changed from: private */
        public ImageView blogLikeButtonView;
        /* access modifiers changed from: private */
        public ImageView blogLocationButtonView = ((ImageView) this.view.findViewById(C2521R.C2524id.new_post_location_thmbnail));
        /* access modifiers changed from: private */
        public CircleImageView deletePostView = ((CircleImageView) this.view.findViewById(C2521R.C2524id.post_clear));
        private View view;

        public ViewHolderOfBlogPost(View view2) {
            super(view2);
            this.view = view2;
            this.blogLikeButtonView = (ImageView) view2.findViewById(C2521R.C2524id.new_post_likes_thumb);
            Fresco.initialize(BlogPostAdapter.this.context);
        }

        public void setDescription(String description) {
            ((TextView) this.view.findViewById(C2521R.C2524id.new_post_description)).setText(description);
        }

        public void setTime(String date) {
            ((TextView) this.view.findViewById(C2521R.C2524id.new_post_date)).setText(date);
        }

        public void setUsername(String userName) {
            ((TextView) this.view.findViewById(C2521R.C2524id.new_post_username_text)).setText(userName);
        }

        public void updateLikesCount(int count) {
            ((TextView) this.view.findViewById(C2521R.C2524id.new_post_likes)).setText(count + "");
        }

        public void setImageURIAndThumbnailURI(String imageURI, String thumbnailURI) {
            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder((int) C2521R.C2523drawable.image_placeholder);
            Glide.with(BlogPostAdapter.this.context).applyDefaultRequestOptions(placeholderOption).load(imageURI).thumbnail(Glide.with(BlogPostAdapter.this.context).load(thumbnailURI)).into((ImageView) this.view.findViewById(C2521R.C2524id.new_post_image));
        }

        public void setUsernameAndUserProfileImage(String username, String userProfileImage) {
            ((TextView) this.view.findViewById(C2521R.C2524id.new_post_username_text)).setText(username);
            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder((int) C2521R.C2523drawable.baseline_account_circle_black_24dp);
            Glide.with(BlogPostAdapter.this.context).applyDefaultRequestOptions(placeholderOption).load(userProfileImage).into((ImageView) (CircleImageView) this.view.findViewById(C2521R.C2524id.new_post_user_image));
        }

        public void zoom(final int position) {
            ((ImageView) this.view.findViewById(C2521R.C2524id.new_post_image)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    List<String> uris = new ArrayList<>();
                    for (int i = 0; i < BlogPostAdapter.this.blogPostImageModelList.size(); i++) {
                        uris.add(((BlogPostImageModel) BlogPostAdapter.this.blogPostImageModelList.get(i)).getImageURI());
                    }
                    new ImageViewer.Builder(BlogPostAdapter.this.context, uris).setStartPosition(position).hideStatusBar(false).allowZooming(true).allowSwipeToDismiss(true).show();
                }
            });
        }

        public void sendUserToMapFragment(final String location) {
            this.blogLocationButtonView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(C2521R.C2524id.content_frame, new MapsFragment(location)).commit();
                }
            });
        }

        public void updateCommentsCount(int numberOfComments) {
            ((TextView) this.view.findViewById(C2521R.C2524id.post_blog_comments_counter)).setText(numberOfComments + "");
        }

        public void setLocationInViewer(String location) {
            ((TextView) this.view.findViewById(C2521R.C2524id.location_text)).setText(location);
        }
    }
}
