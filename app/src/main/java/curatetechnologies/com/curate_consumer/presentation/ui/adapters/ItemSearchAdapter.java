package curatetechnologies.com.curate_consumer.presentation.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.presentation.ui.views.listeners.RecyclerViewClickListener;
import curatetechnologies.com.curate_consumer.presentation.ui.views.subclasses.RoundedCornerTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

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
        @BindView(R.id.item_search_view_holder_item_restaurant)
        TextView itemRestaurant;
        @BindView(R.id.item_search_view_holder_item_availble_for_order)
        TextView itemAvailable;
        @BindView(R.id.item_search_view_holder_item_distance)
        TextView itemDistance;
        @BindView(R.id.item_search_view_holder_item_price)
        TextView itemPrice;
        @BindView(R.id.item_search_view_holder_item_rating_pie)
        PieChart ratingPie;

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
                Glide.with(view)
                        .load(item.getImageURL())
                        .apply(bitmapTransform(new MultiTransformation(
                                new CenterCrop(), new RoundedCornerTransformation(45, 0,
                                RoundedCornerTransformation.CornerType.ALL))))
                        .into(itemImage);
            }
            itemName.setText(item.getName());
            itemRestaurant.setText(item.getRestaurantName());
            itemDistance.setText(item.getDistance_in_mi());
            itemPrice.setText(item.getPrice());

            // greater than 0 because testing equivalence in floats/doubles is weird
            if (item.getRating()>0){
                ratingPie.setVisibility(View.VISIBLE);
                configurePieChart(item);
            } else{
                ratingPie.setVisibility(View.INVISIBLE);
            }

            if (item.getRestaurantStripeId() != null && item.isItemAvailable()){
                itemAvailable.setTextColor(view.getResources().getColor(R.color.selectedGreen));
                itemAvailable.setText("Available for Order");
            } else if (item.getRestaurantStripeId() != null && !item.isItemAvailable()){
                itemAvailable.setTextColor(view.getResources().getColor(R.color.colorAccentLight));
                itemAvailable.setText("Item Currently Unavailable");
            } else {
                itemAvailable.setTextColor(view.getResources().getColor(R.color.colorAccentLight));
                itemAvailable.setText("Ordering Not Enabled");
            }
        }

        private void configurePieChart(ItemModel item) {
            List<PieEntry> entries = calculateRatingEntries(item.getRating());
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
            double percentRating = item.getRating() * 100;
            ratingPie.setCenterText(String.format("%.0f", percentRating));
            ratingPie.setData(data);
            ratingPie.invalidate(); // refresh
        }

        // -- BEGIN View.OnClickListener methods
        @Override
        public void onClick(View v) {
            mListener.onClick(view, getAdapterPosition());
        }
        // -- END View.OnClickListener methods


        private List<PieEntry> calculateRatingEntries(Double rating) {
            float ratingf = rating.floatValue();
            List<PieEntry> entries = new ArrayList<PieEntry>();
            entries.add(new PieEntry(1.0f - ratingf));
            entries.add(new PieEntry(ratingf));
            return entries;
        }
    }
}
