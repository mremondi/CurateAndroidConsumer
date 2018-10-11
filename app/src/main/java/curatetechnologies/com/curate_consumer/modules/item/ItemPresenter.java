package curatetechnologies.com.curate_consumer.modules.item;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.CreatePostInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.CreatePostInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.GetItemByIdInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetItemByIdInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.GetItemPostsInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetItemPostsInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.ItemModelRepository;
import curatetechnologies.com.curate_consumer.storage.PostModelRepository;

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
    public void getItemById(Integer itemId, Location location, Float radius) {
        if (mView.isActive()) {
            mView.showProgress();
            GetItemByIdInteractor itemInteractor = new GetItemByIdInteractorImpl(
                    mExecutor,
                    mMainThread,
                    this,
                    mItemRepository,
                    itemId,
                    location,
                    radius
            );
            itemInteractor.execute();
        }
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
        if (mView.isActive()) {
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
    }

    // -- END: ItemContract methods

    // -- BEGIN: GetItemByIdInteractor.Callback methods
    @Override
    public void onGetItemByIdRetrieved(ItemModel item) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.displayItem(item);
        }
    }

    @Override
    public void onRetrievalFailed(String error) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.showError(error);
        }
    }
    // -- END: GetItemByIdInteractor.Callback methods

    // -- BEGIN: GetItemPostsInteractor.Callback methods
    @Override
    public void onPostsRetrieved(List<PostModel> posts) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.displayItemPosts(posts);
        }
    }
    // -- END: GetItemPostsInteractor.Callback methods

    // -- BEGIN: CreatePostInteractor.Callback methods
    @Override
    public void onCreatePostSuccess() {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.postCreatedSuccessfully();
        }
    }

    @Override
    public void onCreatePostFailed(String error) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.showError(error);
        }
    }
    // -- END: CreatePostInteractor.Callback methods
}