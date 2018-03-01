package curatetechnologies.com.curate.presentation.ui.views.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import curatetechnologies.com.curate.R;

public class EditImageActivity extends AppCompatActivity {

    public static final String IMAGE_TAG = "IMAGE";

    @BindView(R.id.activity_edit_image_image_view)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray(IMAGE_TAG);

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bitmap);
    }
}
