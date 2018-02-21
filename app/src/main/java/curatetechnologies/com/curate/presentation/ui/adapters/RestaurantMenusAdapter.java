package curatetechnologies.com.curate.presentation.ui.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.model.MenuModel;
import curatetechnologies.com.curate.presentation.ui.views.listeners.RecyclerViewClickListener;

/**
 * Created by mremondi on 2/21/18.
 */

public class RestaurantMenusAdapter extends RecyclerView.Adapter<RestaurantMenusAdapter.ViewHolder>{

    List<MenuModel> restaurantMenus;
    private RecyclerViewClickListener mListener;

    public RestaurantMenusAdapter(List<MenuModel> menus, RecyclerViewClickListener listener){
        this.restaurantMenus = menus;
        this.mListener = listener;
    }

    public void updateData(List<MenuModel> newData) {
        this.restaurantMenus = newData;
        this.notifyDataSetChanged();
    }

    @Override
    public RestaurantMenusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_restaurant_menu_view_holder, parent, false);
        RestaurantMenusAdapter.ViewHolder vh = new RestaurantMenusAdapter.ViewHolder(view, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RestaurantMenusAdapter.ViewHolder holder, int position) {
        MenuModel menu = this.restaurantMenus.get(position);
        holder.bindData(menu);
    }

    @Override
    public int getItemCount() {
        Log.d("MENU COUNT", ""+restaurantMenus.size());
        return this.restaurantMenus.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;

        View view;
        @BindView(R.id.fragment_restaurant_menu_view_holder_menu_name)
        TextView menuName;

        public ViewHolder(View menuRow, RecyclerViewClickListener listener){
            super(menuRow);
            this.mListener = listener;
            ButterKnife.bind(this, menuRow);
            this.view = menuRow;
            menuRow.setOnClickListener(this);
        }

        public void bindData(MenuModel menu){
            menuName.setText(menu.getName());
        }

        // -- BEGIN View.OnClickListener methods
        @Override
        public void onClick(View v) {
            mListener.onClick(view, getAdapterPosition());
        }
        // -- END View.OnClickListener methods
    }
}