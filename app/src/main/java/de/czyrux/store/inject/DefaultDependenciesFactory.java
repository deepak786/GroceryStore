package de.czyrux.store.inject;

import android.content.Context;
import android.util.Log;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;

import de.czyrux.store.R;
import de.czyrux.store.core.data.sources.InMemoryCartDataSource;
import de.czyrux.store.core.data.sources.InMemoryProductDataSource;
import de.czyrux.store.core.data.util.TimeDelayer;
import de.czyrux.store.core.domain.cart.CartDataSource;
import de.czyrux.store.core.domain.cart.CartService;
import de.czyrux.store.core.domain.cart.CartStore;
import de.czyrux.store.core.domain.product.ProductDataSource;
import de.czyrux.store.core.domain.product.ProductService;
import de.czyrux.store.tracking.TrackingDispatcher;

public class DefaultDependenciesFactory implements DependenciesFactory {

    private final TimeDelayer timeDelayer;
    private final CartStore cartStore;

    public DefaultDependenciesFactory() {
        timeDelayer = new TimeDelayer();
        cartStore = new CartStore();
    }

    @Override
    public CartService createCartService() {
        CartDataSource cartDataSource = new InMemoryCartDataSource(timeDelayer);
        return new CartService(cartDataSource, cartStore);
    }

    @Override
    public ProductService createProductService() {
        ProductDataSource productDataSource = new InMemoryProductDataSource(timeDelayer);
        return new ProductService(productDataSource);
    }

    @Override
    public CartStore createCartStore() {
        return cartStore;
    }

    @Override
    public TrackingDispatcher createTracking(Context context) {
        synchronized (this) {

            // Init GA - uncomment after GA setup
//            Tracker tracker = GoogleAnalytics.getInstance(context)
//                    .newTracker(R.xml.global_tracker);

            // Init FA
            FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);

            // Init Flurry - uncomment after proper setup
//            FlurryAgent.setLogEnabled(true);
//            FlurryAgent.setLogLevel(Log.VERBOSE);
//            FlurryAgent.setVersionName("1.0");
//            FlurryAgent.init(context, TrackingDispatcher.FLURRY_APIKEY);

            return new TrackingDispatcher(null, firebaseAnalytics);
        }
    }
}
