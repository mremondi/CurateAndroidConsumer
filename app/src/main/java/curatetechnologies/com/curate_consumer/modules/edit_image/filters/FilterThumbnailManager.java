package curatetechnologies.com.curate_consumer.modules.edit_image.filters;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mremondi on 4/4/18.
 */

public final class FilterThumbnailManager {

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    private static List<FilterThumbnail> filterThumbs = new ArrayList<>(10);
    private static List<FilterThumbnail> processedThumbs = new ArrayList<>(10);

    private FilterThumbnailManager() {
    }

    public static void addThumb(FilterThumbnail thumbnailItem) {
        filterThumbs.add(thumbnailItem);
    }

    public static List<FilterThumbnail> processThumbs(Context context) {
        for (FilterThumbnail thumb : filterThumbs) {
            // scaling down the image

            thumb.image = Bitmap.createScaledBitmap(thumb.image, thumb.image.getWidth()*2, thumb.image.getHeight()*2, false)
                    .copy(Bitmap.Config.ARGB_8888, true);
            thumb.image = thumb.filter.processFilter(thumb.image);
            processedThumbs.add(thumb);
        }
        return processedThumbs;
    }

    public static void clearThumbs() {
        filterThumbs = new ArrayList<>();
        processedThumbs = new ArrayList<>();
    }
}