package curatetechnologies.com.curate_consumer.modules.map;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import curatetechnologies.com.curate_consumer.R;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.modules.restaurant.RestaurantFragment;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MapPreviewBottomSheet  extends BottomSheetDialogFragment {

    private RestaurantModel mRestaurant;

    Unbinder unbinder;

    @BindView(R.id.fragment_map_preview_restaurant_logo)
    ImageView ivRestaurantLogo;
    @BindView(R.id.fragment_map_preview_restaurant_name)
    TextView tvRestaurantName;
    @BindView(R.id.fragment_map_preview_ordering_enabled)
    TextView tvOrderingEnabled;
    @BindView(R.id.fragment_map_preview_distance)
    TextView tvDistance;
    @BindView(R.id.fragment_map_preview_restaurant_description)
    TextView tvRestaurantDescription;

    public MapPreviewBottomSheet() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_preview_bottom_sheet, container, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
                Fragment restaurantFragment = new RestaurantFragment();

                Bundle bundle = new Bundle();
                bundle.putInt(RestaurantFragment.RESTAURANT_ID, mRestaurant.getId());
                restaurantFragment.setArguments(bundle);

                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .add(restaurantFragment, "RESTAURANT")
                        .addToBackStack("RESTAURANT")
                        .replace(R.id.content_frame, restaurantFragment)
                        .commit();
            }
        });

        unbinder = ButterKnife.bind(this, v);
        setUpPreview();
        return v;

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setRestaurant(RestaurantModel restaurant){
        mRestaurant = restaurant;
    }

    private void setUpPreview(){
        if (mRestaurant != null) {
            Glide.with(this)
                    .load(mRestaurant.getLogoURL())
                    .apply(bitmapTransform(new MultiTransformation(
                            new CenterCrop())))
                    .into(ivRestaurantLogo);
            tvRestaurantName.setText(mRestaurant.getName());
            if (mRestaurant.getStripeID() != null && !mRestaurant.getStripeID().equals("")){
                tvOrderingEnabled.setText("Ordering Enabled");
                tvOrderingEnabled.setTextColor(getResources().getColor(R.color.selectedGreen));
            } else {
                tvOrderingEnabled.setText("Ordering Not Enabled");
                tvOrderingEnabled.setTextColor(getResources().getColor(R.color.colorAccent));
            }
            tvDistance.setText(mRestaurant.getDistance_in_mi());
            tvDistance.setTextColor(getResources().getColor(R.color.activeBlue));
            //tvRestaurantDescription.setText(mRestaurant.getD);
        }
    }
}
