package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
        return v;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void displayRestaurant(RestaurantModel restaurant) {
        Glide.with(this).load(restaurant.getLogoURL()).into(ivRestaurantLogo);

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
