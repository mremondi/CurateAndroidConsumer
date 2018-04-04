package curatetechnologies.com.curate.presentation.presenters;

import android.location.Location;
import android.util.Log;

import java.util.List;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.CreatePostInteractor;
import curatetechnologies.com.curate.domain.interactor.CreatePostInteractorImpl;
import curatetechnologies.com.curate.domain.interactor.GetItemByIdInteractor;
import curatetechnologies.com.curate.domain.interactor.GetItemByIdInteractorImpl;
import curatetechnologies.com.curate.domain.interactor.GetItemPostsInteractor;
import curatetechnologies.com.curate.domain.interactor.GetItemPostsInteractorImpl;
import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.storage.ItemModelRepository;
import curatetechnologies.com.curate.storage.PostModelRepository;

/**
 * Created by mremondi on 2/10/18.
 */

public class ItemPresenter extends AbstractPresenter implements ItemContract,
        GetItemByIdInteractor.Callback,
        GetItemPostsInteractor.Callback,
        CreatePostInteractor.Callback {

    private ItemContract.View mView;
    private ItemModelRepository mItemRepository;
    private PostModelRepository mPostRepository;

    public ItemPresenter(Executor executor, MainThread mainThread,
                         View view, ItemModelRepository itemModelRepository,
                         PostModelRepository postModelRepository) {
        super(executor, mainThread);
        mView = view;
        mItemRepository = itemModelRepository;
        mPostRepository = postModelRepository;
    }

    // -- BEGIN: ItemContract methods
    @Override
    public void getItemById(Integer itemId, Location location) {
        mView.showProgress();
        GetItemByIdInteractor itemInteractor = new GetItemByIdInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mItemRepository,
                itemId,
                location
        );
        itemInteractor.execute();
    }

    @Override
    public void getItemPosts(Integer limit, Integer itemId) {
        GetItemPostsInteractor restaurantPostsInteractor = new GetItemPostsInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mPostRepository,
                limit,
                itemId
        );
        restaurantPostsInteractor.execute();
    }

    @Override
    public void createRatingPost(String jwt, PostModel postModel) {
        mView.showProgress();
        CreatePostInteractor createPostInteractor = new CreatePostInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mPostRepository,
                jwt,
                postModel
        );
        createPostInteractor.execute();
    }

    // -- END: ItemContract methods

    public void onError(String message) {
        mView.showError(message);
    }

    // -- BEGIN: GetItemByIdInteractor.Callback methods
    @Override
    public void onGetItemByIdRetrieved(ItemModel item) {
        mView.hideProgress();
        mView.displayItem(item);
    }

    @Override
    public void onRetrievalFailed(String error) {
        mView.hideProgress();
        onError(error);
    }
    // -- END: GetItemByIdInteractor.Callback methods

    // -- BEGIN: GetItemPostsInteractor.Callback methods
    @Override
    public void onPostsRetrieved(List<PostModel> posts) {
        mView.hideProgress();
        mView.displayItemPosts(posts);
    }
    // -- END: GetItemPostsInteractor.Callback methods

    // -- BEGIN: CreatePostInteractor.Callback methods
    @Override
    public void onCreatePostSuccess() {
        mView.hideProgress();
        mView.postCreatedSuccessfully();
    }

    @Override
    public void onCreatePostFailed(String error) {
        mView.hideProgress();
        onError(error);
    }
    // -- END: CreatePostInteractor.Callback methods
}