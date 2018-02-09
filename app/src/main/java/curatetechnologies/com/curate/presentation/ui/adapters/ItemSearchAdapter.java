package curatetechnologies.com.curate.presentation.ui.adapters;

import android.content.Context;
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

/**
 * Created by mremondi on 2/9/18.
 */

public class ItemSearchAdapter extends RecyclerView.Adapter<ItemSearchAdapter.ViewHolder> {

    List<ItemModel> searchResults;

    public ItemSearchAdapter(List<ItemModel> searchResults){
        this.searchResults = searchResults;
    }

    @Override
    public ItemSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view =  (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_view_holder, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ItemSearchAdapter.ViewHolder holder, int position) {
        ItemModel item = this.searchResults.get(position);

        if (item.getImageURL() != null){
            Glide.with(holder.view).load(item.getImageURL()).into(holder.itemImage);
        } else {

        }
        holder.itemName.setText(item.getName());
        holder.itemDescription.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return this.searchResults.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_search_view_holder_item_image)
        ImageView itemImage;
        @BindView(R.id.item_search_view_holder_item_name)
        TextView itemName;
        @BindView(R.id.item_search_view_holder_item_description)
        TextView itemDescription;

        CardView view;
        public ViewHolder(CardView searchRow){
            super(searchRow);
            ButterKnife.bind(this, searchRow);
            this.view = searchRow;
        }


        @Override
        public void onClick(View v) {
            // TODO: move to item view
        }
    }
}
