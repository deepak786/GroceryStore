package de.czyrux.store.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import de.czyrux.store.R;
import de.czyrux.store.inject.Injector;
import de.czyrux.store.tracking.TrackingDispatcher;
import de.czyrux.store.tracking.TrackingScreenView;
import de.czyrux.store.ui.base.BaseActivity;

/**
 * @author Sergii Zhuk
 *         Date: 02.12.2016
 *         Time: 23:14
 */


public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
        setupDependencies();
    }

    @Override
    protected int layoutId() {
        return R.layout.about_activity;
    }

    @Override
    protected String getScreenName() {
        return getString(R.string.screen_title_about);
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            // back button pressed
            onBackPressed();
        });
    }

}
