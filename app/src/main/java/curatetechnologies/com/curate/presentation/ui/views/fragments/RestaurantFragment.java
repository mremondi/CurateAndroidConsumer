package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

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

    private int progressStatus = 0;
    private Handler handler = new Handler();
    @BindView(R.id.fragment_restaurant_progress_bar)
    ProgressBar progressBar;

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
    public void displayRestaurant(final RestaurantModel restaurant) {
        Glide.with(this).load(restaurant.getLogoURL()).into(ivRestaurantLogo);

        // set up recyclerview
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Integer menuId = restaurant.getMenus().get(position).getId();
                Fragment menuFragment = new MenuFragment();

                Bundle bundle = new Bundle();
                bundle.putInt(MenuFragment.MENU_ID, menuId);
                menuFragment.setArguments(bundle);

                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .add(menuFragment, "MENU")
                        .addToBackStack("MENU")
                        .replace(R.id.content_frame, menuFragment)
                        .commit();
            }
        };
        menuRecyclerView.setAdapter(new RestaurantMenusAdapter(restaurant.getMenus(), listener));

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }
}
