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
import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.presentation.ui.views.listeners.RecyclerViewClickListener;

/**
 * Created by mremondi on 2/12/18.
 */

public class RestaurantSearchAdapter extends RecyclerView.Adapter<RestaurantSearchAdapter.ViewHolder>{

    List<RestaurantModel> searchResults;
    private RecyclerViewClickListener mListener;

    public RestaurantSearchAdapter(List<RestaurantModel> searchResults, RecyclerViewClickListener listener){
        this.searchResults = searchResults;
        this.mListener = listener;
    }

    public void updateData(List<RestaurantModel> newData) {
        this.searchResults = newData;
        this.notifyDataSetChanged();
    }

    @Override
    public RestaurantSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view =  (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_search_view_holder, parent, false);
        RestaurantSearchAdapter.ViewHolder vh = new RestaurantSearchAdapter.ViewHolder(view, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RestaurantSearchAdapter.ViewHolder holder, int position) {
        RestaurantModel restaurant = this.searchResults.get(position);
        holder.bindData(restaurant);
    }

    @Override
    public int getItemCount() {
        return this.searchResults.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;

        CardView view;
        @BindView(R.id.restaurant_search_view_holder_restaurant_logo)
        ImageView restaurantLogo;
        @BindView(R.id.restaurant_search_view_holder_restaurant_name)
        TextView restaurantName;

        public ViewHolder(CardView searchRow, RecyclerViewClickListener listener){
            super(searchRow);
            this.mListener = listener;
            ButterKnife.bind(this, searchRow);
            this.view = searchRow;
            searchRow.setOnClickListener(this);
        }

        public void bindData(RestaurantModel restaurant){
            if (restaurant.getLogoURL() != null){
                Glide.with(view).load(restaurant.getLogoURL()).into(restaurantLogo);
            } else {

            }
            restaurantName.setText(restaurant.getName());
        }

        // -- BEGIN View.OnClickListener methods
        @Override
        public void onClick(View v) {
            mListener.onClick(view, getAdapterPosition());
        }
        // -- END View.OnClickListener methods
    }
}