package curatetechnologies.com.curate.presentation.ui.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.stripe.android.view.CardInputWidget;


import butterknife.BindView;
import curatetechnologies.com.curate.R;

public class GetCardActivity extends AppCompatActivity {

    @BindView(R.id.card_input_widget)
    CardInputWidget cardInputWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_card);
    }
}
