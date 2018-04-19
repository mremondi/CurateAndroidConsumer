package curatetechnologies.com.curate_consumer.modules.edit_image.filters;

import android.graphics.Bitmap;

import com.zomato.photofilters.imageprocessors.Filter;

/**
 * Created by mremondi on 4/4/18.
 */

public class FilterThumbnail {
    public Bitmap image;
    public Filter filter;

    public FilterThumbnail() {
        image = null;
        filter = new Filter();
    }
}
