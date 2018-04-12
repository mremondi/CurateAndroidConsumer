package curatetechnologies.com.curate.presentation.ui.views.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import curatetechnologies.com.curate.R;
import curatetechnologies.com.curate.domain.executor.ThreadExecutor;
import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.presentation.presenters.EditImageContract;
import curatetechnologies.com.curate.presentation.presenters.EditImagePresenter;
import curatetechnologies.com.curate.presentation.ui.views.filters.FilterThumbnailManager;
import curatetechnologies.com.curate.presentation.ui.views.filters.FilterThumbnailAdapter;
import curatetechnologies.com.curate.presentation.ui.views.filters.FilterThumbnail;
import curatetechnologies.com.curate.presentation.ui.views.filters.FilterThumbnailCallback;
import curatetechnologies.com.curate.storage.PostRepository;
import curatetechnologies.com.curate.storage.UserRepository;
import curatetechnologies.com.curate.threading.MainThreadImpl;

public class EditImageActivity extends AppCompatActivity implements EditImageContract.View, FilterThumbnailCallback{

    public static final String IMAGE_URI = "IMAGE_URI";
    public static final String IMAGE_GALLERY_PATH = "IMAGE_GALLERY_PATH";
    public static final String ITEM_ID = "ITEM_ID";
    public static final String RESTAURANT_ID = "RESTAURANT_ID";


    private Bitmap mBitmap;
    private int mItemId;
    private int mRestaurantId;
    private boolean mRating;

    private EditImagePresenter mEditImagePresenter;

    @BindView(R.id.fragment_item_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.activity_edit_image_image_view)
    ImageView imageView;

    @BindView(R.id.activity_edit_image_filters)
    RecyclerView filterListView;

    @BindView(R.id.activity_edit_image_description)
    EditText descriptionTextView;

    @BindView(R.id.activity_edit_image_thumbs_up)
    Button likeButton;
    @BindView(R.id.activity_edit_image_thumbs_down)
    Button dislikeButton;

    @OnClick(R.id.activity_edit_image_thumbs_down) void dislikeClick(){
        mRating = false;
        dislikeButton.setBackground(getResources().getDrawable(R.drawable.thumbs_down_black));
        likeButton.setBackground(getResources().getDrawable(R.drawable.thumbs_up));
    }

    @OnClick(R.id.activity_edit_image_thumbs_up) void likeClick(){
        mRating = true;
        likeButton.setBackground(getResources().getDrawable(R.drawable.thumbs_up_black));
        dislikeButton.setBackground(getResources().getDrawable(R.drawable.thumbs_down));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        mEditImagePresenter = new EditImagePresenter(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new PostRepository());

        Bundle extras = getIntent().getExtras();
        Uri imageUri = (Uri) extras.get(IMAGE_URI);
        mItemId = extras.getInt(ITEM_ID);
        mRestaurantId = extras.getInt(RESTAURANT_ID);

        String path = extras.getString(IMAGE_GALLERY_PATH);

        if (imageUri != null){
            mBitmap = getBitmap(imageUri.getPath());
        } else {
            mBitmap = getBitmap(path);
        }

        imageView.setImageBitmap(Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth(), mBitmap.getHeight(), false));
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
                FilterThumbnail t1 = new FilterThumbnail();
                FilterThumbnail t2 = new FilterThumbnail();
                FilterThumbnail t3 = new FilterThumbnail();
                FilterThumbnail t4 = new FilterThumbnail();
                FilterThumbnail t5 = new FilterThumbnail();
                FilterThumbnail t6 = new FilterThumbnail();
                Bitmap thumbImage = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth()/5, mBitmap.getHeight()/5, false);

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

    @OnClick(R.id.activity_edit_image_cancel_button) void cancelClick(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.activity_edit_image_post_button) void postClick(){
        String jwt = UserRepository.getInstance(getApplicationContext()).getCurrentUser().getCurateToken();
        String path = saveImage(mBitmap, "curate_post.jpeg");
        mEditImagePresenter.postImagePost(constructPost(path), jwt);
    }

    private String saveImage(Bitmap finalBitmap, String image_name) {
        //return Utils.insertImage(getContentResolver(), finalBitmap, "CURATE_POST", image_name );
        try {
            File f = new File(getCacheDir(), image_name);
            f.createNewFile();

            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return f.getAbsolutePath();
        } catch (IOException e){
            return "";
        }
    }

    private PostModel constructPost(String path){
        String postType = PostModel.IMAGE_POST;
        String description = descriptionTextView.getText().toString();

        int userId = UserRepository.getInstance(getApplicationContext()).getCurrentUser().getId();
        return new PostModel(0, postType, mRestaurantId, mItemId, description, mRating, 0, 0,
                "", "", userId, "", "", "", "", 0.0, path);
    }

    @Override
    public void onThumbnailClick(Filter filter) {
        Bitmap mutableBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth(), mBitmap.getHeight(),false)
                .copy(Bitmap.Config.ARGB_8888, true);
        imageView.setImageBitmap(filter.processFilter(mutableBitmap));
    }

    private Bitmap getBitmap(String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }

            Bitmap b = null;
            in = getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();

                double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x, (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            ExifInterface ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap rotatedBitmap = null;
            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(b, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(b, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(b, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = b;
            }
            return rotatedBitmap;
        } catch (IOException e) {
            Log.e("", e.getMessage(), e);
            return null;
        }
    }

    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    public void postSuccessful() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(MainActivity.GOTO_FRAGMENT_TAG, MainActivity.FEED_FRAGMENT_TAG);
        startActivity(i);
        finish();

    }

    // -- BEGIN BaseView methods
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Log.d("SHOW ERROR", message);
    }
    // -- END BaseView methods
}
