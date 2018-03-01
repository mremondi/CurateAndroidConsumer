package curatetechnologies.com.curate.presentation.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.model.MenuModel;
import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.presentation.ui.views.listeners.RecyclerViewClickListener;
import curatetechnologies.com.curate.presentation.ui.views.subclasses.RoundedCornerTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by mremondi on 3/1/18.
 */

public class RestaurantPhotosAdapter extends RecyclerView.Adapter<RestaurantPhotosAdapter.ViewHolder>{

    List<PostModel> mPosts;
    private RecyclerViewClickListener mListener;

    public RestaurantPhotosAdapter(List<PostModel> posts, RecyclerViewClickListener listener){
        this.mPosts = posts;
        this.mListener = listener;
    }

    public void updateData(List<PostModel> newData) {
        this.mPosts = newData;
        this.notifyDataSetChanged();
    }

    @Override
    public RestaurantPhotosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_restaurant_photo_view_holder, parent, false);
        RestaurantPhotosAdapter.ViewHolder vh = new RestaurantPhotosAdapter.ViewHolder(view, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RestaurantPhotosAdapter.ViewHolder holder, int position) {
        String photoUrl = mPosts.get(position).getImageURL();
        holder.bindData(photoUrl);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;

        View view;

        @BindView(R.id.photo_view_holder_image_view)
        ImageView photoView;

        public ViewHolder(View photoCell, RecyclerViewClickListener listener){
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

}