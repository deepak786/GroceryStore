package de.czyrux.store.ui.catalog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import de.czyrux.store.R;
import de.czyrux.store.core.domain.cart.CartProduct;
import de.czyrux.store.core.domain.cart.CartProductFactory;
import de.czyrux.store.core.domain.cart.CartService;
import de.czyrux.store.core.domain.product.Product;
import de.czyrux.store.core.domain.product.ProductResponse;
import de.czyrux.store.core.domain.product.ProductService;
import de.czyrux.store.inject.Injector;
import de.czyrux.store.tracking.ThrottleTrackingBus;
import de.czyrux.store.tracking.ThrottleTrackingBus.VisibleState;
import de.czyrux.store.tracking.TrackingDispatcher;
import de.czyrux.store.tracking.TrackingEvent;
import de.czyrux.store.ui.base.BaseFragment;
import de.czyrux.store.util.RxUtil;

import static de.czyrux.store.tracking.TrackingDispatcher.KEY_ACTION;
import static de.czyrux.store.tracking.TrackingDispatcher.KEY_CATEGORY;
import static de.czyrux.store.tracking.TrackingDispatcher.KEY_LABEL;

public class CatalogFragment extends BaseFragment implements CatalogListener {

    private static final String TAG = CatalogFragment.class.getName();

    @BindView(R.id.catalog_emptyView)
    View emptyView;

    @BindView(R.id.catalog_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.catalog_recyclerview)
    RecyclerView recyclerView;

    private ProductService productService;

    private CartService cartService;

    private TrackingDispatcher tracking;

    private ThrottleTrackingBus trackingBus;

    private LinearLayoutManager lenearLayoutManager;

    public static CatalogFragment newInstance() {
        CatalogFragment fragment = new CatalogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public CatalogFragment() {
    }

    @Override
    protected int layoutId() {
        return R.layout.catalog_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productService = Injector.productService();
        cartService = Injector.cartService();
        tracking = Injector.tracking();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lenearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(lenearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                final VisibleState visibleStateFinal = new VisibleState(lenearLayoutManager.findFirstCompletelyVisibleItemPosition(), lenearLayoutManager.findLastCompletelyVisibleItemPosition());
                trackingBus.postViewEvent(visibleStateFinal);
            }
        });

    }

    void onTrackViewResponse(VisibleState visibleState) {
        Log.d(TAG, "Received to be tracked: " + visibleState.toString());
        TrackingEvent trackingEvent = new TrackingEvent()
                .put(KEY_ACTION, getString(R.string.tracking_view_catalog_items))
                .put(KEY_CATEGORY, getString(R.string.tracking_cat_interaction))
                .put(KEY_LABEL, visibleState.toString());
        tracking.sendEvent(trackingEvent);
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressBar();
        addSubscritiption(productService.getAllCatalog()
                .compose(RxUtil.applyStandardSchedulers())
                .subscribe(this::onProductResponse, RxUtil.logError()));
    }

    @Override
    public void onResume() {
        super.onResume();
        trackingBus = new ThrottleTrackingBus(this::onTrackViewResponse, RxUtil.logError());
    }

    @Override
    public void onPause() {
        super.onPause();
        trackingBus.unsubscribe();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        recyclerView.setAdapter(null);
    }

    private void onProductResponse(ProductResponse productResponse) {
        hideProgressbar();
        if (productResponse.isEmpty()) {
            showEmptyCatalog();
        } else {
            showCatalog(productResponse);
        }
    }

    private void showCatalog(ProductResponse productResponse) {
        emptyView.setVisibility(View.GONE);
        CatalogAdapter adapter = new CatalogAdapter(this);
        adapter.setProductList(productResponse.getProducts());
        recyclerView.setAdapter(adapter);
    }

    private void showEmptyCatalog() {
        emptyView.setVisibility(View.VISIBLE);
    }

    private void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onProductClicked(Product product) {
        CartProduct cartProduct = CartProductFactory.newCartProduct(product, 1);
        addSubscritiption(cartService.addProduct(cartProduct)
                .compose(RxUtil.applyStandardSchedulers())
                .subscribe(RxUtil.emptyObserver())
        );

        TrackingEvent trackingEvent = new TrackingEvent()
                .put(KEY_ACTION, getString(R.string.tracking_cat_interaction))
                .put(KEY_CATEGORY, getString(R.string.tracking_cat_to_cart_add))
                .put(KEY_LABEL, product.sku);
        tracking.sendEvent(trackingEvent);

        Toast.makeText(getContext(), "Adding to cart..." + product.title, Toast.LENGTH_SHORT).show();
    }
}