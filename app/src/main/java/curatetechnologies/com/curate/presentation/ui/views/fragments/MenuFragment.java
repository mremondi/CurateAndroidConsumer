package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.domain.model.MenuModel;
import curatetechnologies.com.curate.domain.model.MenuSectionModel;
import curatetechnologies.com.curate.presentation.presenters.MenuContract;
import curatetechnologies.com.curate.presentation.presenters.MenuPresenter;
import curatetechnologies.com.curate.presentation.ui.adapters.MenuSection;
import curatetechnologies.com.curate.presentation.ui.views.listeners.RecyclerViewClickListener;
import curatetechnologies.com.curate.storage.MenuRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by mremondi on 2/23/18.
 */

public class MenuFragment extends Fragment implements MenuContract.View {

    public static final String MENU_ID = "menuId";
    Unbinder unbinder;

    private MenuContract mMenuPresenter;

    private MenuModel mMenu;

    @BindView(R.id.fragment_menu_title)
    TextView tvTitle;
    @BindView(R.id.fragment_restaurant_menu_recyclerview)
    RecyclerView menuRecyclerView;


    // -- BEGIN Fragment methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        unbinder = ButterKnife.bind(this, v);

        Integer menuId = getArguments().getInt(MENU_ID);

        mMenuPresenter = new MenuPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new MenuRepository());

        mMenuPresenter.getMenuById(menuId);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void displayMenu(MenuModel menu) {
        tvTitle.setText(menu.getName());

        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
        for (final MenuSectionModel menuSection: menu.getMenuSections()){
            RecyclerViewClickListener listener = new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                    // - 1 because the header row counts as a position item
                    Integer itemId = menuSection.getItems().get(position - 1).getId();
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
            sectionAdapter.addSection(new MenuSection(menuSection, sectionAdapter, listener));
        }

        menuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        menuRecyclerView.setAdapter(sectionAdapter);
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
