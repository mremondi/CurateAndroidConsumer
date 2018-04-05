package curatetechnologies.com.curate.presentation.ui.views.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.presentation.ui.views.filters.FilterThumbnailManager;
import curatetechnologies.com.curate.presentation.ui.views.filters.FilterThumbnailAdapter;
import curatetechnologies.com.curate.presentation.ui.views.filters.FilterThumbnail;
import curatetechnologies.com.curate.presentation.ui.views.filters.FilterThumbnailCallback;

public class EditImageActivity extends AppCompatActivity implements FilterThumbnailCallback{

    public static final String IMAGE_TAG = "IMAGE";
    Bitmap bitmap;

    @BindView(R.id.activity_edit_image_image_view)
    ImageView imageView;

    @BindView(R.id.activity_edit_image_filters)
    RecyclerView filterListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray(IMAGE_TAG);

        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false));
        initHorizontalList();
    }

    private void initHorizontalList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);
        filterListView.setLayoutManager(layoutManager);
        filterListView.setHasFixedSize(true);
        bindDataToAdapter();
    }

    private void bindDataToAdapter() {
        final Context context = this.getApplication();
        Handler handler = new Handler();
        final Activity activity = this;
        Runnable r = new Runnable() {
            public void run() {
                Bitmap thumbImage = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false);
                FilterThumbnail t1 = new FilterThumbnail();
                FilterThumbnail t2 = new FilterThumbnail();
                FilterThumbnail t3 = new FilterThumbnail();
                FilterThumbnail t4 = new FilterThumbnail();
                FilterThumbnail t5 = new FilterThumbnail();
                FilterThumbnail t6 = new FilterThumbnail();

                t1.image = thumbImage;
                t2.image = thumbImage;
                t3.image = thumbImage;
                t4.image = thumbImage;
                t5.image = thumbImage;
                t6.image = thumbImage;
                FilterThumbnailManager.clearThumbs();
                FilterThumbnailManager.addThumb(t1); // Original Image

                t2.filter = SampleFilters.getStarLitFilter();
                FilterThumbnailManager.addThumb(t2);

                t3.filter = SampleFilters.getBlueMessFilter();
                FilterThumbnailManager.addThumb(t3);

                t4.filter = SampleFilters.getAweStruckVibeFilter();
                FilterThumbnailManager.addThumb(t4);

                t5.filter = SampleFilters.getLimeStutterFilter();
                FilterThumbnailManager.addThumb(t5);

                t6.filter = SampleFilters.getNightWhisperFilter();
                FilterThumbnailManager.addThumb(t6);

                List<FilterThumbnail> thumbs = FilterThumbnailManager.processThumbs(context);

                FilterThumbnailAdapter adapter = new FilterThumbnailAdapter(thumbs, (FilterThumbnailCallback) activity);
                filterListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        handler.post(r);
    }

    @Override
    public void onThumbnailClick(Filter filter) {
        Bitmap mutableBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(),false)
                .copy(Bitmap.Config.ARGB_8888, true);
        imageView.setImageBitmap(filter.processFilter(mutableBitmap));
    }
}
