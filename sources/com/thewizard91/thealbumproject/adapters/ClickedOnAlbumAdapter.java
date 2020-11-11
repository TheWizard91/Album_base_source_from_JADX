package com.thewizard91.thealbumproject.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.thewizard91.thealbumproject.C2521R;
import com.thewizard91.thealbumproject.models.choosenalbum.SingleImageModel;
import java.util.ArrayList;
import java.util.List;

public class ClickedOnAlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /* access modifiers changed from: private */
    public Context context;
    /* access modifiers changed from: private */
    public List<SingleImageModel> listOfImagesOfChosenAlbum;

    /* renamed from: p */
    ViewGroup f802p;
    View view;

    public ClickedOnAlbumAdapter(List<SingleImageModel> listOfImagesOfChosenAlbum2) {
        this.listOfImagesOfChosenAlbum = listOfImagesOfChosenAlbum2;
    }

    public ClickedOnAlbumAdapter(Context context2, AsymmetricGridView asymmetryListView, List<SingleImageModel> list) {
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        this.f802p = parent;
        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        FirebaseAuth instance2 = FirebaseAuth.getInstance();
        if (viewType == 0) {
            this.view = LayoutInflater.from(parent.getContext()).inflate(C2521R.C2526layout.single_album, parent, false);
            return new ViewHolder1(this.view);
        } else if (viewType != 1) {
            return null;
        } else {
            this.view = LayoutInflater.from(parent.getContext()).inflate(C2521R.C2526layout.zoom_layout, parent, false);
            return new ViewHolder2(this.view);
        }
    }

    public int getItemViewType(int position) {
        int type = this.listOfImagesOfChosenAlbum.get(position).getType();
        if (type == 0) {
            return 0;
        }
        if (type != 1) {
            return -1;
        }
        return 1;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        SingleImageModel model = this.listOfImagesOfChosenAlbum.get(position);
        String imageUri = this.listOfImagesOfChosenAlbum.get(position).getImageUri();
        if (model != null) {
            int type = model.getType();
            if (type == 0) {
                ((ViewHolder1) holder).setImageURI(Uri.parse(imageUri));
                ((ViewHolder1) holder).zoom(position);
            } else if (type == 1) {
                ((ViewHolder2) holder).setImageURI(Uri.parse(imageUri));
            }
        }
    }

    public int getItemCount() {
        return this.listOfImagesOfChosenAlbum.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        public ImageView imageView;

        public ViewHolder1(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(C2521R.C2524id.single_image);
            Fresco.initialize(ClickedOnAlbumAdapter.this.context);
        }

        public void setImageURI(Uri imageURI) {
            RequestOptions placeholder = new RequestOptions();
            placeholder.placeholder((int) C2521R.C2523drawable.image_placeholder);
            Glide.with(ClickedOnAlbumAdapter.this.context).applyDefaultRequestOptions(placeholder).load(imageURI).into(this.imageView);
        }

        public void zoom(final int position) {
            this.imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    List<String> uris = new ArrayList<>();
                    for (int i = 0; i < ClickedOnAlbumAdapter.this.listOfImagesOfChosenAlbum.size(); i++) {
                        uris.add(((SingleImageModel) ClickedOnAlbumAdapter.this.listOfImagesOfChosenAlbum.get(i)).getImageUri());
                    }
                    new ImageViewer.Builder(ClickedOnAlbumAdapter.this.context, uris).setStartPosition(position).hideStatusBar(true).allowZooming(true).allowSwipeToDismiss(true).show();
                    Toast.makeText(ClickedOnAlbumAdapter.this.context, "Clicked" + ViewHolder1.this.imageView.toString(), 1).show();
                }
            });
        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private View zoomImageLayoutView;

        static {
            Class<ClickedOnAlbumAdapter> cls = ClickedOnAlbumAdapter.class;
        }

        public ViewHolder2(View itemView) {
            super(itemView);
            this.zoomImageLayoutView = itemView;
        }

        public void setImageURI(Uri imageURI) {
            ImageView imageView = (ImageView) this.zoomImageLayoutView.findViewById(C2521R.C2524id.zoom_image);
            new RequestOptions().placeholder((int) C2521R.C2523drawable.baseline_insert_photo_black_24dp);
            Log.d("imageViewIsTrue", String.valueOf(imageView == null));
            Log.d("zoomImageLayoutView", this.zoomImageLayoutView.toString());
            Log.d("imageURIIs", imageURI.toString());
            if (imageView == null) {
                throw new AssertionError();
            }
        }
    }
}
