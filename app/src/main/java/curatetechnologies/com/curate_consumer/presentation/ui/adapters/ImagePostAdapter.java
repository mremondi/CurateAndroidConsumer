package curatetechnologies.com.curate_consumer.presentation.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.presentation.ui.views.listeners.RecyclerViewClickListener;
import curatetechnologies.com.curate_consumer.presentation.ui.views.subclasses.RoundedCornerTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by mremondi on 3/1/18.
 */

public class ImagePostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<PostModel> mPosts;
    private RecyclerViewClickListener mPostListener;
    private RecyclerViewClickListener mAddPhotoListener;

    public ImagePostAdapter(List<PostModel> posts, RecyclerViewClickListener postlistener,
                            RecyclerViewClickListener addPhotoListener){
        this.mPosts = posts;
        this.mPostListener = postlistener;
        this.mAddPhotoListener = addPhotoListener;
    }

    public void updateData(List<PostModel> newData) {
        this.mPosts = newData;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_restaurant_photo_view_holder, parent, false);
                viewHolder = new ImagePostAdapter.ImageViewHolder(view, mPostListener);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_restaurant_add_photo_view_holder, parent, false);
                viewHolder = new ImagePostAdapter.AddImageViewHolder(view, mAddPhotoListener);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                imageViewHolder.bindData(mPosts.get(position).getImageURL());
                break;
            case 1:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mPosts.get(position) != null){
            return 0;
        } else {
            return 1;
        }
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;

        View view;

        @BindView(R.id.photo_view_holder_image_view)
        ImageView photoView;

        public ImageViewHolder(View photoCell, RecyclerViewClickListener listener){
            super(photoCell);
            this.mListener = listener;
            ButterKnife.bind(this, photoCell);
            this.view = photoCell;
            photoCell.setOnClickListener(this);
        }

        public void bindData(String photoUrl){
            Glide.with(view)
                    .load(photoUrl)
                    .apply(bitmapTransform(new MultiTransformation(
                            new CenterCrop(), new RoundedCornerTransformation(45, 0,
                            RoundedCornerTransformation.CornerType.ALL))))
                    .into(photoView);
        }

        // -- BEGIN View.OnClickListener methods
        @Override
        public void onClick(View v) {
            mListener.onClick(view, getAdapterPosition());
        }
        // -- END View.OnClickListener methods
    }

    public static class AddImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;

        View view;

        public AddImageViewHolder(View addPhotoCell, RecyclerViewClickListener listener){
            super(addPhotoCell);
            this.mListener = listener;
            ButterKnife.bind(this, addPhotoCell);
            this.view = addPhotoCell;
            addPhotoCell.setOnClickListener(this);
        }

        public void bindData(String photoUrl){

        }

        // -- BEGIN View.OnClickListener methods
        @Override
        public void onClick(View v) {
            mListener.onClick(view, getAdapterPosition());
        }
        // -- END View.OnClickListener methods
    }

}