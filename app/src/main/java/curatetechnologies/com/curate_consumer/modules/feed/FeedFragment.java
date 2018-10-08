package curatetechnologies.com.curate_consumer.modules.feed;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.presentation.ui.adapters.FeedAdapter;
import curatetechnologies.com.curate_consumer.storage.LocationRepository;
import curatetechnologies.com.curate_consumer.storage.PostRepository;
import curatetechnologies.com.curate_consumer.threading.MainThreadImpl;

/**
 * Created by mremondi on 2/22/18.
 */

public class FeedFragment extends Fragment implements FeedContract.View {
    Unbinder unbinder;
    private FeedContract mFeedPresenter;

    private int progressStatus = 0;
    private Handler handler = new Handler();
    @BindView(R.id.fragment_feed_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.fragment_feed_recycler_view)
    RecyclerView feedRecyclerView;

    // -- BEGIN Fragment methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feed, container, false);

        unbinder = ButterKnife.bind(this, v);
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        mFeedPresenter = new FeedPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new PostRepository());

        mFeedPresenter.getPostsByLocation(20, getLocation(), getRadius());
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    // --- BEGIN Override of Activity methods in case Async network request happens when activity falls out of view
    //TODO: Check which of these
    @Override
    public void onStop() {
        super.onStop();
        mFeedPresenter.interruptThreadToCancelNetworkRequest();
    }

    @Override
    public void onPause() {
        super.onPause();
        mFeedPresenter.interruptThreadToCancelNetworkRequest();
    }

    ///////

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
        feedRecyclerView.setAdapter(new FeedAdapter(posts, getContext() ));
    }

    @Override
    public void showProgress() {
        // Start the lengthy operation in a background thread
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
