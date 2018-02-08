package curatetechnologies.com.curate;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluelinelabs.conductor.Controller;

/**
 * Created by mremondi on 2/8/18.
 */

public class SearchController extends Controller {
    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.controller_search, container, false);
        ((TextView)view.findViewById(R.id.tv_title)).setText("Hello World");
        return view;
    }
}
