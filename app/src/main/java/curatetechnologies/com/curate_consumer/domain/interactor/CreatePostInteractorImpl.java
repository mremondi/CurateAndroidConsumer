package curatetechnologies.com.curate_consumer.domain.interactor;

import android.util.Log;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.storage.PostModelRepository;

public class CreatePostInteractorImpl extends AbstractInteractor implements CreatePostInteractor {

    private CreatePostInteractor.Callback mCallback;
    private PostModelRepository mPostModelRepository;

    private PostModel mPost;
    private String mJwt;

    public CreatePostInteractorImpl(Executor threadExecutor,
                                    MainThread mainThread,
                                    CreatePostInteractor.Callback callback,
                                    PostModelRepository postModelRepository,
                                    String jwt,
                                    PostModel postModel) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mPostModelRepository = postModelRepository;
        mPost = postModel;
        mJwt = jwt;
    }

    private void notifyError() {
        Log.d("ERROR CREATING", "POST");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                //mCallback.onCreatePostFailed("Create Post Failed");
            }
        });
    }

    private void postSuccess() {
        Log.d("SUCCESS CREATING", "POST");
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onCreatePostSuccess();
            }
        });
    }


    @Override
    public void run() {
        Log.d("HERE", "RUN POST " + mPost.getPostType());
        // retrieve the message
        int insertId = mPostModelRepository.createPost(mJwt, mPost);

        // check if we have failed to retrieve our message
        if (insertId < 1) {
            // notify the failure on the main thread
            notifyError();

            return;
        }
        // we have retrieved our message, notify the UI on the main thread
        postSuccess();
    }
}