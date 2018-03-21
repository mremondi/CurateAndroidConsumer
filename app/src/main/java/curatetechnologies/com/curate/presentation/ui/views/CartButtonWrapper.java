package curatetechnologies.com.curate.presentation.ui.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import curatetechnologies.com.curate.manager.CartManager;
import curatetechnologies.com.curate.presentation.ui.views.activities.LoginActivity;
import curatetechnologies.com.curate.storage.UserRepository;

/**
 * Created by mremondi on 3/21/18.
 */


/*
 * This class should:
 * allow the Cart to make changes to the UI,
 * tell customers whether they are able to add to the cart or ask them to register
 */

public class CartButtonWrapper {

    private static CartButtonWrapper instance;

    ImageButton mCartButton;
    TextView mItemCountTextView;

    private CartButtonWrapper() {
    }

    // synchronized just in case any background threads are trying to access at the same time
    public static synchronized CartButtonWrapper getInstance() {
        if (instance == null) {
            instance = new CartButtonWrapper();
        }
        return instance;
    }

    public void setUpCartUI(Fragment fragment, ImageButton cartButton, TextView tvItemCount){
        mCartButton = cartButton;
        mItemCountTextView = tvItemCount;
        updateCartButtonCount(CartManager.getInstance().getOrderItemCount());

        setOnClickAlert(fragment);
    }

    private void setOnClickAlert(final Fragment fragment) {
        mCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = fragment.getContext();
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(context);
                builder.setTitle("Feature Unavailable")
                        .setMessage("You need to be logged in to use this feature.")
                        .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(context, LoginActivity.class);
                                context.startActivity(i);
                                fragment.getActivity().finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    public void updateCartButtonCount(int count){
        if (mItemCountTextView != null) {
            if (count == 0) {
                if (mItemCountTextView.getVisibility() != View.GONE) {
                    mItemCountTextView.setVisibility(View.GONE);
                }
            } else {
                mItemCountTextView.setText(String.valueOf(count));
                if (mItemCountTextView.getVisibility() != View.VISIBLE) {
                    mItemCountTextView.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
