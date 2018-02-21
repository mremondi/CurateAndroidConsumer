package curatetechnologies.com.curate.presentation.ui.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.presentation.ui.views.listeners.RecyclerViewClickListener;

/**
 * Created by mremondi on 2/9/18.
 */

public class ItemSearchAdapter extends RecyclerView.Adapter<ItemSearchAdapter.ViewHolder>{

    List<ItemModel> searchResults;
    private RecyclerViewClickListener mListener;

    public ItemSearchAdapter(List<ItemModel> searchResults, RecyclerViewClickListener listener){
        this.searchResults = searchResults;
        this.mListener = listener;
    }

    public void updateData(List<ItemModel> newData) {
        this.searchResults = newData;
        this.notifyDataSetChanged();
    }

    @Override
    public ItemSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view =  (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_view_holder, parent, false);
        ViewHolder vh = new ViewHolder(view, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ItemSearchAdapter.ViewHolder holder, int position) {
        ItemModel item = this.searchResults.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return this.searchResults.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;

        @BindView(R.id.item_search_view_holder_item_image)
        ImageView itemImage;
        @BindView(R.id.item_search_view_holder_item_name)
        TextView itemName;
        @BindView(R.id.item_search_view_holder_item_restaurant_name)
        TextView itemDescription;
        @BindView(R.id.item_search_view_holder_item_distance)
        TextView itemDistance;
        @BindView(R.id.item_search_view_holder_item_price)
        TextView itemPrice;
        @BindView(R.id.item_search_view_holder_item_rating)
        TextView itemRating;

        CardView view;
        public ViewHolder(CardView searchRow, RecyclerViewClickListener listener){
            super(searchRow);
            this.mListener = listener;
            ButterKnife.bind(this, searchRow);
            this.view = searchRow;
            searchRow.setOnClickListener(this);
        }

        public void bindData(ItemModel item){
            if (item.getImageURL() != null){
                Glide.with(view).load(item.getImageURL()).into(itemImage);
            }
            itemName.setText(item.getName());
            itemDescription.setText(item.getDescription());
            itemDistance.setText(item.getDistance_in_mi());
            itemPrice.setText(item.getPrice());
            itemRating.setText(item.getRating());
        }

        // -- BEGIN View.OnClickListener methods
        @Override
        public void onClick(View v) {
            mListener.onClick(view, getAdapterPosition());
        }
        // -- END View.OnClickListener methods
    }
}
