package de.czyrux.store.inject;

import android.content.Context;

import de.czyrux.store.core.domain.cart.CartService;
import de.czyrux.store.core.domain.cart.CartStore;
import de.czyrux.store.core.domain.product.ProductService;
import de.czyrux.store.tracking.TrackingDispatcher;

/**
 * Class use as a centralize point for DI.
 */
public class Injector {
    private static Injector INSTANCE;
    private final CartService cartService;
    private final ProductService productService;
    private final CartStore cartStore;
    private final TrackingDispatcher tracking;

    public Injector(CartService cartService, ProductService productService, CartStore cartStore, TrackingDispatcher tracking) {
        this.cartService = cartService;
        this.productService = productService;
        this.cartStore = cartStore;
        this.tracking = tracking;
    }

    public static void using(DependenciesFactory factory, Context context) {
        CartService cartService = factory.createCartService();
        ProductService productService = factory.createProductService();
        CartStore cartStore = factory.createCartStore();
        TrackingDispatcher tracking = factory.createTracking(context);
        INSTANCE = new Injector(cartService, productService, cartStore, tracking);
    }

    private static Injector instance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("You need to setup Inject to use a valid DependenciesFactory");
        }
        return INSTANCE;
    }

    public static CartService cartService() {
        return instance().cartService;
    }

    public static ProductService productService() {
        return instance().productService;
    }

    public static CartStore cartStore() {
        return instance().cartStore;
    }

    public static TrackingDispatcher tracking() {
        return instance().tracking;
    }


}
