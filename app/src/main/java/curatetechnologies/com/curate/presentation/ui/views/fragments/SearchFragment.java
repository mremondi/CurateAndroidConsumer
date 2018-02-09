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
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.presentation.presenters.ItemSearchPresenter;
import curatetechnologies.com.curate.presentation.presenters.ItemSearchPresenterImpl;
import curatetechnologies.com.curate.presentation.ui.BaseView;
import curatetechnologies.com.curate.presentation.ui.adapters.ItemSearchAdapter;
import curatetechnologies.com.curate.storage.ItemRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;


public class SearchFragment extends Fragment implements ItemSearchPresenterImpl.View {

    private ItemSearchPresenter mItemSearchPresenter;

    Unbinder unbinder;

    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.search_results)
    RecyclerView searchResults;

    @OnClick(R.id.btnSearch) void searchButtonClicked() {
        String query = etSearch.getText().toString();
        mItemSearchPresenter.searchItems(query);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        unbinder = ButterKnife.bind(this, v);

        searchResults.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        mItemSearchPresenter = new ItemSearchPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new ItemRepository());


        return v;

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showProgress() {
        Log.d("SHOW PROGRESS", "Retrieving...");
    }

    @Override
    public void hideProgress() {
        Toast.makeText(this.getActivity(), "Retrieved!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        Log.d("SHOW ERROR", message);
    }

    @Override
    public void displayItems(List<ItemModel> items) {
        searchResults.setAdapter(new ItemSearchAdapter(items));
    }
}
