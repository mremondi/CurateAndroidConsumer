package curatetechnologies.com.curate.presentation.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.presentation.ui.views.listeners.RecyclerViewClickListener;

/**
 * Created by mremondi on 4/12/18.
 */

public class RatePreviousOrderRecyclerViewAdapter extends RecyclerView.Adapter<RatePreviousOrderRecyclerViewAdapter.ViewHolder> {

    List<ItemModel> orderItems;

    public RatePreviousOrderRecyclerViewAdapter(List<ItemModel> orderItems) {
        this.orderItems = orderItems;
    }

    public void updateData(List<ItemModel> newData) {
        this.orderItems = newData;
        this.notifyDataSetChanged();
    }

    @Override
    public RatePreviousOrderRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_rate_previous_order_item_view_holder, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RatePreviousOrderRecyclerViewAdapter.ViewHolder holder, int position) {
        ItemModel item = this.orderItems.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return this.orderItems.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dialog_rate_order_vh_item_name)
        TextView itemName;
        @BindView(R.id.dialog_rate_order_vh_thumbnail)
        ImageView itemThumbnail;
        @BindView(R.id.dialog_rate_order_vh_like)
        Button btnLike;
        @BindView(R.id.dialog_rate_order_vh_dislike)
        Button btnDislike;

        @OnClick(R.id.dialog_rate_order_vh_dislike) void dislikeClick(){
            // TODO: figure out a way to save the rating to the dialog
            // TODO: so we can execute a bunch of posts in the background...
        }

        @OnClick(R.id.dialog_rate_order_vh_like) void likeClick() {
            // TODO: figure out a way to save the rating to the dialog
            // TODO: so we can execute a bunch of posts in the background...

        }

        RelativeLayout view;

        public ViewHolder(RelativeLayout itemRow) {
            super(itemRow);
            ButterKnife.bind(this, itemRow);
            this.view = itemRow;
        }

        private void bindData(ItemModel item) {
            itemName.setText(item.getName());
            Glide.with(view).load(item.getImageURL()).into(itemThumbnail);
        }
    }
}
