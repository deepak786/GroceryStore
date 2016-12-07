package de.czyrux.store.inject;

import android.content.Context;

import de.czyrux.store.core.domain.cart.CartService;
import de.czyrux.store.core.domain.cart.CartStore;
import de.czyrux.store.core.domain.product.ProductService;
import de.czyrux.store.tracking.TrackingDispatcher;

public interface DependenciesFactory {
    CartService createCartService();

    ProductService createProductService();

    CartStore createCartStore();

    TrackingDispatcher createTracking(Context context);
}
