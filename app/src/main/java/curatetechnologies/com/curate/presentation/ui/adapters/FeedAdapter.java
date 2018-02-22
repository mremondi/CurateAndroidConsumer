package curatetechnologies.com.curate.presentation.ui.adapters;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.model.PostModel;

/**
 * Created by mremondi on 2/22/18.
 */

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<PostModel> mPosts;

    public FeedAdapter(List<PostModel> posts){
        mPosts = posts;
    }

    public void updateData(List<PostModel> newData) {
        this.mPosts = newData;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                view = (CardView) LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.fragment_feed_rating_post_view_holder,
                                parent,
                                false);
                viewHolder = new RatingPostViewHolder(view);
                break;
            case 1:
                view = (CardView) LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.fragment_feed_image_post_view_holder,
                                parent,
                                false);
                viewHolder = new ImagePostViewHolder(view);
                break;
            case 2:
                view = (CardView) LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.fragment_feed_order_post_view_holder,
                                parent,
                                false);
                viewHolder = new OrderPostViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:
                RatingPostViewHolder ratingPostViewHolder = (RatingPostViewHolder) holder;
                ratingPostViewHolder.bindData(mPosts.get(position));
                break;
            case 1:
                ImagePostViewHolder imagePostViewHolder = (ImagePostViewHolder) holder;
                imagePostViewHolder.bindData(mPosts.get(position));
                break;
            case 2:
                OrderPostViewHolder orderPostViewHolder = (OrderPostViewHolder) holder;
                orderPostViewHolder.bindData(mPosts.get(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        String postType = mPosts.get(position).getPostType();
        if (postType.equals("Rating")){
            return 0;
        } else if (postType.equals("ImageRating")){
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return this.mPosts.size();
    }


    public static class RatingPostViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rating_post_item_name)
        TextView tvItemName;
        @BindView(R.id.rating_post_restaurant_name)
        TextView tvRestaurantName;
        @BindView(R.id.rating_post_user_picture)
        ImageView ivUserPicture;
        @BindView(R.id.rating_post_username)
        TextView tvUsername;
        @BindView(R.id.rating_post_time)
        TextView tvTime;
        @BindView(R.id.rating_post_liked_text)
        TextView tvLiked;
        @BindView(R.id.rating_post_liked_image)
        ImageView ivLiked;

        CardView view;
        public RatingPostViewHolder(CardView postRow){
            super(postRow);
            ButterKnife.bind(this, postRow);
            this.view = postRow;
        }

        public void bindData(PostModel post){
            if (post.getUserPicture() != null){
                Glide.with(view).load(post.getImageURL()).into(ivUserPicture);
            }
            tvItemName.setText(post.getItemName());
            tvRestaurantName.setText(post.getRestaurantName());
            tvUsername.setText(post.getUsername());
            tvTime.setText(post.getTime());
            String likeDislike = post.getRating() ? "Liked": "Disliked";
            tvLiked.setText(likeDislike);
            Drawable likeDislikeImage = post.getRating() ?
                    view.getResources().getDrawable( R.drawable.liked) :
                    view.getResources().getDrawable(R.drawable.disliked);
            ivLiked.setImageDrawable(likeDislikeImage);
        }
    }

    public static class ImagePostViewHolder extends RecyclerView.ViewHolder {

        CardView view;
        public ImagePostViewHolder(CardView postRow){
            super(postRow);
            ButterKnife.bind(this, postRow);
            this.view = postRow;
        }

        public void bindData(PostModel post){

        }
    }

    public static class OrderPostViewHolder extends RecyclerView.ViewHolder {

        CardView view;
        public OrderPostViewHolder(CardView postRow){
            super(postRow);
            ButterKnife.bind(this, postRow);
            this.view = postRow;
        }

        public void bindData(PostModel post){

        }
    }
}