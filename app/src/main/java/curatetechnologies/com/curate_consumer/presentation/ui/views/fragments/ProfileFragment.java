package curatetechnologies.com.curate_consumer.presentation.ui.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import curatetechnologies.com.curate_consumer.presentation.presenters.ProfileContract;
import curatetechnologies.com.curate_consumer.presentation.presenters.ProfilePresenter;
import curatetechnologies.com.curate_consumer.presentation.ui.adapters.ProfileAdapter;
import curatetechnologies.com.curate_consumer.storage.PostRepository;
import curatetechnologies.com.curate_consumer.storage.UserRepository;
import curatetechnologies.com.curate_consumer.threading.MainThreadImpl;

/**
 * Created by mremondi on 2/28/18.
 */

public class ProfileFragment extends Fragment implements ProfileContract.View {
    Unbinder unbinder;
    private ProfileContract mProfilePresenter;

    @BindView(R.id.fragment_profile_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.fragment_profile_recycler_view)
    RecyclerView profileRecyclerView;

    // -- BEGIN Fragment methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        unbinder = ButterKnife.bind(this, v);
        profileRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        mProfilePresenter = new ProfilePresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new PostRepository());

        Integer userId = getUserId();
        if (userId == 0){
            // USER NOT LOGGED IN, SHOW ERROR MESSAGE
        } else {
            mProfilePresenter.getUserPosts(20, getUserId());
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private Integer getUserId(){
        return UserRepository.getInstance(getContext()).getCurrentUser().getId();
    }

    @Override
    public void displayPosts(List<PostModel> posts) {
        profileRecyclerView.setAdapter(new ProfileAdapter(posts, getContext() ));
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
