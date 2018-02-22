package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.presentation.presenters.FeedContract;
import curatetechnologies.com.curate.presentation.presenters.FeedPresenter;
import curatetechnologies.com.curate.storage.LocationRepository;
import curatetechnologies.com.curate.storage.PostRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;

/**
 * Created by mremondi on 2/22/18.
 */

public class FeedFragment extends Fragment implements FeedContract.View {
    Unbinder unbinder;
    private FeedContract mFeedPresenter;

    // -- BEGIN Fragment methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feed, container, false);

        unbinder = ButterKnife.bind(this, v);

        mFeedPresenter = new FeedPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new PostRepository());

        mFeedPresenter.getPostsByLocation(20, getLocation(), getRadius());
        return v;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private Location getLocation(){
        return LocationRepository.getInstance(getContext()).getLastLocation();
    }

    private Float getRadius(){
        return LocationRepository.getInstance(getContext()).getRadius();
    }

    @Override
    public void displayPosts(List<PostModel> posts) {
        Log.d("POST 0", posts.get(0).getItemName());
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
