package curatetechnologies.com.curate_consumer.presentation.ui.views.fragments;

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
import curatetechnologies.com.curate_consumer.domain.model.PostModel;
import curatetechnologies.com.curate_consumer.presentation.ui.views.subclasses.RoundedCornerTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by mremondi on 4/2/18.
 */

public class ItemPreviewBottomSheetFragment extends BottomSheetDialogFragment {
    public ItemPreviewBottomSheetFragment() {
        // Required empty public constructor
    }

    private PostModel mPost;
    Unbinder unbinder;

    @BindView(R.id.item_preview_item_image)
    ImageView postImage;
    @BindView(R.id.item_preview_item_name)
    TextView itemName;
    @BindView(R.id.item_preview_restaurant_name)
    TextView restaurantName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_preview_bottom_sheet, container, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
                Fragment itemFragment = new ItemFragment();

                Bundle bundle = new Bundle();
                bundle.putInt(ItemFragment.ITEM_ID, mPost.getItemId());
                itemFragment.setArguments(bundle);

                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .add(itemFragment, "ITEM")
                        .addToBackStack("ITEM")
                        .replace(R.id.content_frame, itemFragment)
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

    public void setPost(PostModel post){
        mPost = post;
    }

    private void setUpPreview(){
        if (mPost != null) {
            Glide.with(this)
                    .load(mPost.getImageURL())
                    .apply(bitmapTransform(new MultiTransformation(
                            new CenterCrop(), new RoundedCornerTransformation(45, 0,
                            RoundedCornerTransformation.CornerType.ALL))))
            .into(postImage);
            itemName.setText(mPost.getItemName());
            restaurantName.setText(mPost.getRestaurantName());
        }
    }
}
