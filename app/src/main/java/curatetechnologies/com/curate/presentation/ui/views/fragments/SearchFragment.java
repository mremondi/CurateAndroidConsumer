package curatetechnologies.com.curate.presentation.ui.views.fragments;


import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.presentation.presenters.SearchContract;
import curatetechnologies.com.curate.presentation.presenters.SearchPresenter;
import curatetechnologies.com.curate.presentation.ui.adapters.ItemSearchAdapter;
import curatetechnologies.com.curate.presentation.ui.adapters.RestaurantSearchAdapter;
import curatetechnologies.com.curate.presentation.ui.views.listeners.RecyclerViewClickListener;
import curatetechnologies.com.curate.storage.ItemRepository;
import curatetechnologies.com.curate.storage.RestaurantRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;


public class SearchFragment extends Fragment implements SearchPresenter.View {

    enum SearchType{
        ITEM_SEARCH,
        RESTAURANT_SEARCH
    }

    private SearchContract mSearchPresenter;

    private SearchType searchType = SearchType.ITEM_SEARCH;

    Unbinder unbinder;

    @BindView(R.id.fragment_search_search_bar)
    SearchView searchView;
    @BindView(R.id.search_results)
    RecyclerView searchResults;
    @BindView(R.id.fragment_search_item_button)
    Button btnItem;
    @BindView(R.id.fragment_search_restaurant_button)
    Button btnRestaurant;

    @OnClick(R.id.fragment_search_item_button) void onItemButtonClick(){
        searchType = SearchType.ITEM_SEARCH;
        btnItem.setSelected(true);
        btnRestaurant.setSelected(false);
        searchResults.setAdapter(null);
    }

    @OnClick(R.id.fragment_search_restaurant_button) void onRestaurantButtonClick(){
        searchType = SearchType.RESTAURANT_SEARCH;
        btnRestaurant.setSelected(true);
        btnItem.setSelected(false);
        searchResults.setAdapter(null);
    }

    // -- BEGIN: Fragment methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        unbinder = ButterKnife.bind(this, v);

        mSearchPresenter = new SearchPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new ItemRepository(),
                new RestaurantRepository());

        // SET DEFAULTS
        searchType = SearchType.ITEM_SEARCH;
        btnItem.setSelected(true);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Make request
                switch (searchType) {
                    case ITEM_SEARCH:
                        mSearchPresenter.searchItems(query);
                        break;
                    case RESTAURANT_SEARCH:
                        mSearchPresenter.searchRestaurants(query);
                }

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // DO NOTHING FOR NOW, LATER WE CAN AUTOFILL RESULTS
                return false;
            }
        });
        searchResults.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        return v;

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    // -- END: Fragment methods

    // -- BEGIN: BaseView methods
    @Override
    public void showProgress() {
        Log.d("SHOW PROGRESS", "Retrieving...");
    }

    @Override
    public void hideProgress() {
        Log.d("HIDE PROGRESS", "Retrieved!");
    }

    @Override
    public void showError(String message) {
        Log.d("SHOW ERROR", message);
    }
    // -- END: BaseView methods

    // -- BEGIN: SearchContract.View methods
    @Override
    public void displayItems(final List<ItemModel> items) {
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                // TODO: SWITCH THIS BACK TO JUST PASS THE WHOLE ITEM
                Integer itemId = items.get(position).getId();
                Fragment itemFragment = new ItemFragment();

                Bundle bundle = new Bundle();
                bundle.putInt(ItemFragment.ITEM_ID, itemId);
                itemFragment.setArguments(bundle);

                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.content_frame, itemFragment);
                transaction.commit();
            }
        };
        searchResults.setAdapter(new ItemSearchAdapter(items, listener));
    }

    @Override
    public void displayRestaurants(List<RestaurantModel> restaurants) {
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
        searchResults.setAdapter(new RestaurantSearchAdapter(restaurants, listener));
    }

    // -- END: SearchPresenter.View methods
}
