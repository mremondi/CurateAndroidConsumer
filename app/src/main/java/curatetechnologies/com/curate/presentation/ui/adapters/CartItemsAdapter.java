package curatetechnologies.com.curate.presentation.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.presentation.ui.views.listeners.RecyclerViewClickListener;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by mremondi on 2/26/18.
 */

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder>{

    List<ItemModel> orderItems;

    public CartItemsAdapter(List<ItemModel> orderItems){
        this.orderItems = orderItems;
    }

    public void updateData(List<ItemModel> newData) {
        this.orderItems = newData;
        this.notifyDataSetChanged();
    }

    @Override
    public CartItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout view =  (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_view_holder, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CartItemsAdapter.ViewHolder holder, int position) {
        ItemModel item = this.orderItems.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return this.orderItems.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;

        @BindView(R.id.cart_item_view_holder_item_name)
        TextView itemName;
        @BindView(R.id.cart_item_view_holder_item_description)
        TextView itemDescription;
        @BindView(R.id.cart_item_view_holder_item_price)
        TextView itemPrice;

        RelativeLayout view;

        public ViewHolder(RelativeLayout itemRow){
            super(itemRow);
            ButterKnife.bind(this, itemRow);
            this.view = itemRow;
        }

        public void bindData(ItemModel item){
            itemName.setText(item.getName());
            itemDescription.setText(item.getDescription());
            itemPrice.setText(item.getPrice());
        }

        // -- BEGIN View.OnClickListener methods
        @Override
        public void onClick(View v) {
            mListener.onClick(view, getAdapterPosition());
        }
        // -- END View.OnClickListener methods
    }
}
