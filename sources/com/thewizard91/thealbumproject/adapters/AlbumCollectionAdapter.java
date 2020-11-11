package com.thewizard91.thealbumproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thewizard91.thealbumproject.C2521R;
import com.thewizard91.thealbumproject.fragments.collective.CollectiveImagesOfAnAlbumFragment;
import com.thewizard91.thealbumproject.models.albums.AlbumModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AlbumCollectionAdapter extends RecyclerView.Adapter<ViewHolder> {
    /* access modifiers changed from: private */
    public Context context;
    /* access modifiers changed from: private */
    public FirebaseAuth firebaseAuth;
    /* access modifiers changed from: private */
    public FirebaseFirestore firebaseFirestore;
    private List<AlbumModel> listOfAlbums;

    public AlbumCollectionAdapter(List<AlbumModel> listOfAlbums2) {
        this.listOfAlbums = listOfAlbums2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(C2521R.C2526layout.album_collection, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
        this.context = parent.getContext();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        String str = this.listOfAlbums.get(position).AlbumId;
        String uid = ((FirebaseUser) Objects.requireNonNull(this.firebaseAuth.getCurrentUser())).getUid();
        holder.setAlbumImage(this.listOfAlbums.get(position).getImageUri());
        holder.setAlbumName(this.listOfAlbums.get(position).getName());
        holder.setAlbumDescription(this.listOfAlbums.get(position).getDescription());
        holder.switchFragment();
    }

    public int getItemCount() {
        return this.listOfAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private ImageView albumImageCoverView;
        /* access modifiers changed from: private */
        public TextView albumNameView;
        private View mView;
        private String userId;
        /* access modifiers changed from: private */
        public String username;

        static {
            Class<AlbumCollectionAdapter> cls = AlbumCollectionAdapter.class;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            FirebaseUser currentUser = AlbumCollectionAdapter.this.firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                this.userId = currentUser.getUid();
                setUsername();
                return;
            }
            throw new AssertionError();
        }

        public void setAlbumImage(String imageUri) {
            this.albumImageCoverView = (ImageView) this.mView.findViewById(C2521R.C2524id.album_front_image);
            RequestOptions placeholder = new RequestOptions();
            placeholder.placeholder((int) C2521R.C2523drawable.image_placeholder);
            Glide.with(AlbumCollectionAdapter.this.context).applyDefaultRequestOptions(placeholder).load(imageUri).into(this.albumImageCoverView);
        }

        public void setAlbumName(String albumName) {
            TextView textView = (TextView) this.mView.findViewById(C2521R.C2524id.album_name);
            this.albumNameView = textView;
            textView.setText(albumName);
        }

        public void setAlbumDescription(String albumDescription) {
            ((TextView) this.mView.findViewById(C2521R.C2524id.album_description)).setText(albumDescription);
        }

        /* access modifiers changed from: private */
        public void switchFragment() {
            this.albumImageCoverView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(C2521R.C2524id.content_frame, new CollectiveImagesOfAnAlbumFragment(ViewHolder.this.albumNameView.getText().toString())).commit();
                }
            });
        }

        private void setUsername() {
            AlbumCollectionAdapter.this.firebaseFirestore.collection("Users").document(this.userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                public void onComplete(Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        List<String> list = new ArrayList<>();
                        Map<String, Object> map = documentSnapshot.getData();
                        if (map != null) {
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                list.add(entry.getValue().toString());
                            }
                            String unused = ViewHolder.this.username = list.get(0);
                        }
                    }
                }
            });
        }
    }
}
