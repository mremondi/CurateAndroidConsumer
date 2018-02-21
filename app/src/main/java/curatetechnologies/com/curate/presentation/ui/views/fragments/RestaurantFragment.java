package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.presentation.presenters.RestaurantContract;
import curatetechnologies.com.curate.presentation.presenters.RestaurantPresenter;
import curatetechnologies.com.curate.presentation.ui.adapters.RestaurantMenusAdapter;
import curatetechnologies.com.curate.presentation.ui.views.listeners.RecyclerViewClickListener;
import curatetechnologies.com.curate.storage.RestaurantRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;

/**
 * Created by mremondi on 2/21/18.
 */

public class RestaurantFragment extends Fragment implements RestaurantContract.View {

    public static final String RESTAURANT_ID = "itemId";

    private RestaurantPresenter mRestaurantPresenter;
    Unbinder unbinder;

    @BindView(R.id.fragment_restaurant_logo)
    ImageView ivRestaurantLogo;
    @BindView(R.id.fragment_restaurant_menu_recyclerview)
    RecyclerView menuRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant, container, false);

        unbinder = ButterKnife.bind(this, v);

        Integer restaurantId = getArguments().getInt(RESTAURANT_ID);

        mRestaurantPresenter = new RestaurantPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new RestaurantRepository());

        mRestaurantPresenter.getRestaurantById(restaurantId);
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return v;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void displayRestaurant(RestaurantModel restaurant) {
        Glide.with(this).load(restaurant.getLogoURL()).into(ivRestaurantLogo);
        Log.d("MENU 0", restaurant.getMenus().get(0).getName());

        // set up recyclerview
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                // TODO: SWITCH THIS BACK TO JUST PASS THE WHOLE ITEM
//                Integer itemId = items.get(position).getId();
//                Fragment itemFragment = new ItemFragment();
//
//                Bundle bundle = new Bundle();
//                bundle.putInt(ItemFragment.ITEM_ID, itemId);
//                itemFragment.setArguments(bundle);
//
//                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction = fm.beginTransaction();
//                transaction.replace(R.id.content_frame, itemFragment);
//                transaction.commit();
            }
        };
        menuRecyclerView.setAdapter(new RestaurantMenusAdapter(restaurant.getMenus(), listener));

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }
}
