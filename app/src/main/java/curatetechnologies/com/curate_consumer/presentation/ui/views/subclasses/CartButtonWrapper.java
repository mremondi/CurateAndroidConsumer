package curatetechnologies.com.curate_consumer.presentation.ui.views.subclasses;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.modules.cart.CartFragment;
import curatetechnologies.com.curate_consumer.modules.search.SearchFragment;

/**
 * Created by mremondi on 4/22/18.
 */

public class CartButtonWrapper {

    FragmentManager mFragmentManager;
    RelativeLayout mCartBtnLayout;
    TextView mTvPrice;
    TextView mTvRestaurantName;
    ImageButton mCartButton;
    TextView mItemCountTextView;

    public CartButtonWrapper(final FragmentManager fragmentManager,
                             RelativeLayout cartBtnLayout,
                             TextView tvPrice,
                             TextView restaurantName,
                             ImageButton cartButton,
                             TextView tvItemCount) {

        mFragmentManager = fragmentManager;
        mTvRestaurantName = restaurantName;
        mTvPrice = tvPrice;
        mCartBtnLayout = cartBtnLayout;
        mCartButton = cartButton;
        mItemCountTextView = tvItemCount;


        mCartBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment cartFragment = new CartFragment();
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.replace(R.id.content_frame, cartFragment);
                transaction.commit();
            }
        });
    }

    public void updateCartButtonCount(String restaurantName, String price, int count) {
        mTvRestaurantName.setText(restaurantName);
        mTvPrice.setText(price);
        if (mItemCountTextView != null) {
            if (count == 0) {
                mCartBtnLayout.setVisibility(View.GONE);
                mItemCountTextView.setVisibility(View.GONE);
            } else {
                mItemCountTextView.setText(String.valueOf(count));
                mCartBtnLayout.setVisibility(View.VISIBLE);
                mItemCountTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}
