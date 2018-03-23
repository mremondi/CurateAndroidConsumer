package curatetechnologies.com.curate.presentation.ui.views.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.manager.CartManager;
import curatetechnologies.com.curate.presentation.ui.views.activities.LoginActivity;
import curatetechnologies.com.curate.storage.UserRepository;

/**
 * Created by mremondi on 3/23/18.
 */

public class EmptyCartFragment extends Fragment {
    Unbinder unbinder;

    @BindView(R.id.fragment_empty_cart_message)
    TextView tvMessage;
    @BindView(R.id.fragment_empty_cart_register_button)
    Button btnRegister;
    @BindView(R.id.fragment_empty_cart_find_items_button)
    Button btnFindItems;

    @OnClick(R.id.fragment_empty_cart_find_items_button) void onFindItemsClick(){
        android.support.v4.app.FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fragment = new SearchFragment();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    @OnClick(R.id.fragment_empty_cart_register_button) void onRegisterClick(){
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_empty_cart, container, false);
        unbinder = ButterKnife.bind(this, v);

        if (UserRepository.getInstance(getContext()).getCurrentUser() == null){
            tvMessage.setText("You need to register to place an order");
            btnFindItems.setEnabled(false);
            btnFindItems.setVisibility(View.GONE);
            btnRegister.setVisibility(View.VISIBLE);
            btnRegister.setEnabled(true);
        } else if (CartManager.getInstance().isEmpty()){
            tvMessage.setText("Looks like your cart is empty! Find something to eat first");
            btnRegister.setVisibility(View.GONE);
            btnRegister.setEnabled(false);
            btnFindItems.setEnabled(true);
            btnFindItems.setVisibility(View.VISIBLE);
        }

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
