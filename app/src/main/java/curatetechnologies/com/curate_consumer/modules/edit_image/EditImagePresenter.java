package curatetechnologies.com.curate_consumer.modules.edit_image;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.CreatePostInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.CreatePostInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.PostModelRepository;

/**
 * Created by mremondi on 4/10/18.
 */

public class EditImagePresenter extends AbstractPresenter implements EditImageContract,
        CreatePostInteractor.Callback{


    private EditImageContract.View mView;
    private PostModelRepository mPostRepository;

    public EditImagePresenter(Executor executor, MainThread mainThread,
                              View view, PostModelRepository postModelRepository) {
        super(executor, mainThread);
        mView = view;
        mPostRepository = postModelRepository;
    }
    // -- BEGIN: EditImageContract methods
    @Override
    public void postImagePost(PostModel postModel, String jwt) {

        mView.showProgress();
        CreatePostInteractor postInteractor = new CreatePostInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mPostRepository,
                jwt,
                postModel

        );
        postInteractor.execute();
    }
    // -- END: EditImageContract methods


    @Override
    public void onCreatePostSuccess() {
        mView.hideProgress();
        mView.postSuccessful();
    }

    @Override
    public void onCreatePostFailed(String error) {
        mView.hideProgress();
        mView.showError(error);
    }
}
