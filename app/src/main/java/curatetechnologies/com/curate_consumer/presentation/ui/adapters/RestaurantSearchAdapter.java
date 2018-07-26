package curatetechnologies.com.curate_consumer.presentation.ui.adapters;

import android.graphics.Color;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.presentation.ui.views.listeners.RecyclerViewClickListener;
import curatetechnologies.com.curate_consumer.presentation.ui.views.subclasses.RoundedCornerTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

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
        @BindView(R.id.restaurant_search_view_holder_restaurant_ordering_enabled)
        TextView orderingEnabled;
        @BindView(R.id.restaurant_search_view_holder_distance)
        TextView distance;
        @BindView(R.id.restaurant_search_view_holder_restaurant_rating_pie)
        PieChart ratingPie;

        public ViewHolder(CardView searchRow, RecyclerViewClickListener listener){
            super(searchRow);
            this.mListener = listener;
            ButterKnife.bind(this, searchRow);
            this.view = searchRow;
            searchRow.setOnClickListener(this);
        }

        public void bindData(RestaurantModel restaurant){
            if (restaurant.getLogoURL() != null){
                Glide.with(view)
                        .load(restaurant.getLogoURL())
                        .into(restaurantLogo);
            } else {

            }
            restaurantName.setText(restaurant.getName());

            if (restaurant.getStripeID() != null && !restaurant.getStripeID().equals("undefined") &&
                    !restaurant.getStripeID().equals("")) {
                orderingEnabled.setTextColor(view.getResources().getColor(R.color.selectedGreen));
                orderingEnabled.setText("Ordering Enabled");
            } else {
                orderingEnabled.setTextColor(view.getResources().getColor(R.color.colorAccentLight));
                orderingEnabled.setText("Ordering Not Enabled");
            }
            distance.setText(restaurant.getDistance_in_mi());

            if (restaurant.getRating()>0){
                ratingPie.setVisibility(View.VISIBLE);
                configurePieChart(restaurant);
            } else{
                ratingPie.setVisibility(View.INVISIBLE);
            }
        }

        // -- BEGIN View.OnClickListener methods
        @Override
        public void onClick(View v) {
            mListener.onClick(view, getAdapterPosition());
        }
        // -- END View.OnClickListener methods

        private void configurePieChart(RestaurantModel restaurantModel) {
            List<PieEntry> entries = calculateRatingEntries(restaurantModel.getRating());
            PieDataSet set = new PieDataSet(entries, "");
            set.setDrawIcons(false);
            set.setDrawValues(false);
            List<Integer> colors = new ArrayList<Integer>();
            colors.add(Color.LTGRAY);
            colors.add(view.getResources().getColor(R.color.selectedGreen));
            set.setColors(colors);
            set.setSelectionShift(0);
            PieData data = new PieData(set);

            ratingPie.setRotationEnabled(false);
            ratingPie.setHighlightPerTapEnabled(false);
            ratingPie.getLegend().setEnabled(false);
            ratingPie.setUsePercentValues(false);
            ratingPie.setDrawSlicesUnderHole(false);
            ratingPie.setDrawHoleEnabled(true);
            ratingPie.setHoleRadius(80f);
            ratingPie.getDescription().setEnabled(false);
            ratingPie.setDrawCenterText(true);
            double percentRating = restaurantModel.getRating() * 100;
            ratingPie.setCenterText(String.format("%.0f", percentRating));
            ratingPie.setData(data);
            ratingPie.invalidate(); // refresh
        }

        private List<PieEntry> calculateRatingEntries(Double rating) {
            float ratingf = rating.floatValue();
            List<PieEntry> entries = new ArrayList<PieEntry>();
            entries.add(new PieEntry(1.0f - ratingf));
            entries.add(new PieEntry(ratingf));
            return entries;
        }
    }
}