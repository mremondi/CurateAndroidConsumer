package curatetechnologies.com.curate_consumer.modules.rate_previous_order;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.domain.model.OrderModel;
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.modules.rate_previous_order.RatePreviousOrderContract;
import curatetechnologies.com.curate_consumer.modules.rate_previous_order.RatePreviousOrderPresenter;
import curatetechnologies.com.curate_consumer.storage.OrderRepository;
import curatetechnologies.com.curate_consumer.storage.PostRepository;
import curatetechnologies.com.curate_consumer.storage.UserRepository;
import curatetechnologies.com.curate_consumer.threading.MainThreadImpl;

/**
 * Created by mremondi on 4/12/18.
 */

public class RatePreviousOrderDialog extends Dialog implements RatePreviousOrderContract.View {

    RatePreviousOrderContract mRatePreviousOrderPresenter;

    private OrderModel mOrder;
    private ItemModel mItem;
    private Boolean mLike;

    @BindView(R.id.dialog_rate_order_restaurant_name)
    TextView restaurantName;
    @BindView(R.id.dialog_rate_order_item_name)
    TextView itemName;
    @BindView(R.id.dialog_rate_order_item_image)
    ImageView itemImage;
    @BindView(R.id.dialog_rate_order_item_description)
    TextView itemDescription;

    @BindView(R.id.dialog_rate_order_like)
    Button btnLike;
    @BindView(R.id.dialog_rate_order_dislike)
    Button btnDislike;

    public RatePreviousOrderDialog(@NonNull Context context, OrderModel orderModel) {
        super(context);
        mOrder = orderModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_rate_previous_order);

        mRatePreviousOrderPresenter = new RatePreviousOrderPresenter(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new PostRepository(),
                new OrderRepository());

        ButterKnife.bind(this);

        restaurantName.setText(mOrder.getRestaurantName());

        mItem = mOrder.getOrderItems().get(0);
        itemName.setText(mItem.getName());
        itemDescription.setText(mItem.getDescription());
        Glide.with(getContext()).load(mItem.getImageURL()).into(itemImage);

    }

    @OnClick(R.id.dialog_rate_order_dislike) void onDislike(){
        if (mLike == null){
            mLike = false;
        } else{
            mLike = !mLike;
        }
        updateRatingButtons();
        UserModel user = UserRepository
                .getInstance(getContext())
                .getCurrentUser();
        mRatePreviousOrderPresenter.createRatingPost(user.getCurateToken(),
                new PostModel(0, "RATING", mItem.getRestaurantId(),
                        mItem.getId(), "", false, 0, 0, "",
                        "", user.getId(), user.getUsername(), user.getProfilePictureURL(),
                        mItem.getName(), mItem.getRestaurantName(), 0.0, null),
                getContext()
        );
    }

    @OnClick(R.id.dialog_rate_order_like) void onLike(){
        if (mLike == null){
            mLike = true;
        } else{
            mLike = !mLike;
        }
        updateRatingButtons();

        UserModel user = UserRepository
                .getInstance(getContext())
                .getCurrentUser();
        mRatePreviousOrderPresenter.createRatingPost(user.getCurateToken(),
                new PostModel(0, "RATING", mItem.getRestaurantId(),
                        mItem.getId(), "", true, 0, 0, "",
                        "", user.getId(), user.getUsername(), user.getProfilePictureURL(),
                        mItem.getName(), mItem.getRestaurantName(), 0.0, null),
                getContext()
        );
    }

    private void updateRatingButtons(){
        if (mLike) {
            btnLike.setBackground(getContext().getResources().getDrawable(R.drawable.thumbs_up_black));
            btnDislike.setBackground(getContext().getResources().getDrawable(R.drawable.thumbs_down));
        } else{
            btnLike.setBackground(getContext().getResources().getDrawable(R.drawable.thumbs_up));
            btnDislike.setBackground(getContext().getResources().getDrawable(R.drawable.thumbs_down_black));
        }
    }

    @Override
    public void postCreatedSuccessfully() {
        // destroy order

        dismiss();
    }

    // -- BEGIN BASEVIEW CONTRACT METHODS


    @Override
    public boolean isActive() {
        return isShowing();
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
