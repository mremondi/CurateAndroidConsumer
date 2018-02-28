package curatetechnologies.com.curate.presentation.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.presentation.ui.views.fragments.ItemFragment;
import curatetechnologies.com.curate.presentation.ui.views.fragments.RestaurantFragment;
import curatetechnologies.com.curate.presentation.ui.views.subclasses.RoundedCornerTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.bumptech.glide.request.RequestOptions.circleCropTransform;

/**
 * Created by mremondi on 2/28/18.
 */

public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<PostModel> mPosts;

    public ProfileAdapter(List<PostModel> posts, Context context) {
        mPosts = posts;
        mContext = context;
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
                        .inflate(R.layout.rating_post_view_holder,
                                parent,
                                false);
                viewHolder = new ProfileAdapter.RatingPostViewHolder(view);
                break;
            case 1:
                view = (CardView) LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.image_post_view_holder,
                                parent,
                                false);
                viewHolder = new ProfileAdapter.ImagePostViewHolder(view);
                break;
            case 2:
                view = (CardView) LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.order_post_view_holder,
                                parent,
                                false);
                viewHolder = new ProfileAdapter.OrderPostViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:
                ProfileAdapter.RatingPostViewHolder ratingPostViewHolder = (ProfileAdapter.RatingPostViewHolder) holder;
                ratingPostViewHolder.bindData(mPosts.get(position), mContext);
                break;
            case 1:
                ProfileAdapter.ImagePostViewHolder imagePostViewHolder = (ProfileAdapter.ImagePostViewHolder) holder;
                imagePostViewHolder.bindData(mPosts.get(position), mContext);
                break;
            case 2:
                ProfileAdapter.OrderPostViewHolder orderPostViewHolder = (ProfileAdapter.OrderPostViewHolder) holder;
                orderPostViewHolder.bindData(mPosts.get(position), mContext);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        String postType = mPosts.get(position).getPostType();
        if (postType.equals("Rating")) {
            return 0;
        } else if (postType.equals("ImageRating")) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return this.mPosts.size();
    }


    public static class RatingPostViewHolder extends ProfileAdapter.PostViewHolder {

        public RatingPostViewHolder(CardView postRow) {
            super(postRow);
            ButterKnife.bind(this, postRow);
            this.view = postRow;
        }

        public void bindData(PostModel post, Context context) {
            super.bindData(post, context);
        }
    }

    public static class ImagePostViewHolder extends ProfileAdapter.PostViewHolder {

        @BindView(R.id.fragment_image_post_item_image)
        ImageView ivItemImage;
        @BindView(R.id.fragment_feed_image_post_description)
        TextView tvDescription;

        public ImagePostViewHolder(CardView postRow) {
            super(postRow);
        }

        public void bindData(PostModel post, Context context) {
            super.bindData(post, context);
            if (post.getImageURL() != null) {
                Glide.with(view)
                        .load(post.getImageURL())
                        .apply(bitmapTransform(new MultiTransformation(
                                new CenterCrop(), new RoundedCornerTransformation(45, 0,
                                RoundedCornerTransformation.CornerType.ALL))))
                        .into(ivItemImage);
            } else {
                ivItemImage.setImageDrawable(null);
            }
            tvDescription.setText(post.getDescription());
        }
    }

    public static class OrderPostViewHolder extends ProfileAdapter.PostViewHolder {

        public OrderPostViewHolder(CardView postRow) {
            super(postRow);
        }

        public void bindData(PostModel post, Context context) {
            super.bindData(post, context);
        }
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
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

        public PostViewHolder(CardView postRow) {
            super(postRow);
            ButterKnife.bind(this, postRow);
            this.view = postRow;
        }

        public void bindData(final PostModel post, final Context context) {
            if (post.getUserPicture() != null) {
                Glide.with(view)
                        .load(post.getUserPicture())
                        .apply(circleCropTransform())
                        .into(ivUserPicture);
            } else {
                ivUserPicture.setImageDrawable(null);
            }
            tvItemName.setText(post.getItemName());
            tvRestaurantName.setText(post.getRestaurantName());
            tvUsername.setText(post.getUsername());
            tvTime.setText(post.getTime());
            String likeDislike = post.getRating() ? "Liked" : "Disliked";
            tvLiked.setText(likeDislike);
            Drawable likeDislikeImage = post.getRating() ?
                    view.getResources().getDrawable(R.drawable.liked) :
                    view.getResources().getDrawable(R.drawable.disliked);
            ivLiked.setImageDrawable(likeDislikeImage);
            tvItemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer itemId = post.getItemId();
                    Fragment itemFragment = new ItemFragment();

                    Bundle bundle = new Bundle();
                    bundle.putInt(ItemFragment.ITEM_ID, itemId);
                    itemFragment.setArguments(bundle);

                    android.support.v4.app.FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                    fm.beginTransaction()
                            .add(itemFragment, "ITEM")
                            .addToBackStack("ITEM")
                            .replace(R.id.content_frame, itemFragment)
                            .commit();

                }
            });
            tvRestaurantName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer restaurantId = post.getRestaurantId();
                    Fragment restaurantFragment = new RestaurantFragment();

                    Bundle bundle = new Bundle();
                    bundle.putInt(RestaurantFragment.RESTAURANT_ID, restaurantId);
                    restaurantFragment.setArguments(bundle);

                    android.support.v4.app.FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                    fm.beginTransaction()
                            .add(restaurantFragment, "RESTAURANT")
                            .addToBackStack("RESTAURANT")
                            .replace(R.id.content_frame, restaurantFragment)
                            .commit();
                }
            });
        }

    }
}