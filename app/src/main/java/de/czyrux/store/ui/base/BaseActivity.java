package de.czyrux.store.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.czyrux.store.inject.Injector;
import de.czyrux.store.tracking.TrackingDispatcher;
import de.czyrux.store.tracking.TrackingScreenView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity {

    private final CompositeSubscription subscriptions = new CompositeSubscription();
    private Unbinder unbinder;
    private TrackingDispatcher trackingDispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        unbinder = ButterKnife.bind(this);
        setupDependencies();
    }

    protected void setupDependencies() {
        trackingDispatcher = Injector.tracking();
    }

    @LayoutRes
    protected abstract int layoutId();

    @Override
    protected void onResume() {
        super.onResume();
        trackScreenName();
    }

    @Override
    public void onStop() {
        super.onStop();
        subscriptions.clear();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    protected void trackScreenName() {
        TrackingScreenView screenView = new TrackingScreenView(getScreenName());
        trackingDispatcher.sendScreenView(screenView);
    }

    protected final void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    protected abstract String getScreenName();
}
