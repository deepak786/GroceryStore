Grocery Store Project
=======

Sample android application project to showcase different ideas or concepts.

## App

The app is a simulation of an e-commerce application with two main screens: a catalog of products and a shopping-cart. 

![App screenshot](/doc/app.png)

### Use cases

1. Clicking in a product would add the element to the Cart.
2. A small notification will be shown every time that something is added to the Cart.
3. From the Cart the products can be removed by clicking on them.
4. There would be a count indicator in one of the tabs (the cart one) with the number of items included in the Shopping Cart.
5. When something is added/removed from Cart the counter and content of the cart screen must be updated accordingly.
6. "About" screen can be opened by menu item click.
7. The following interaction events are sent to the analytics: add to cart, remove from cart, checkout.
8. The following screen view events are sent to the analytics: product list, cart, about.


## Concepts

### Application tracking
Shows how you can integrate Flurry, Google Analytics and Firebase Analytics. With Tracking Facade you can easily add more tracking SDKs.

**Full article**: [The key concepts of app tracking for developers](https://medium.com/@sergii/the-key-concepts-of-app-tracking-for-developers-a11bebf1e65e#.dq17d0p77)

Tracking Facade design:

![Tracking facade design](/doc/tracking_facade.png)

### Sharing state across the application

[Store](/app/src/main/java/de/czyrux/store/core/domain/Store.java) as a backbone for sharing state across the application.

**Full article**: [State propagation in Android with RxJava Subjects](https://medium.com/@czyrux/state-propagation-in-android-with-rxjava-subjects-81db49a0dd8e#.ylft5ryj5)

Flow updates for add2Cart operation:

![add2Cart Flow](/doc/add2Cart_flow.png)

## Additional setup

**Tracking demo**: you will need to setup Google Analytics, Firebase and Flurry accounts to be able to see tracking output.

* Google Analytics and Firebase Analytics: [Follow instructions](https://developers.google.com/mobile/add) to get your google-services.json configuration file and add it to the project.
* Flurry: [follow instructions](https://developer.yahoo.com/flurry/docs/analytics/gettingstarted/android/) to get your key and set it in [TrackingDispatcher](/app/src/main/java/de/czyrux/store/tracking/TrackingDispatcher.java).
* To see the data being sent by tracking tools you need to setup a proxy tool (e.g. [Charles](https://www.charlesproxy.com/) or [mitmproxy](https://mitmproxy.org/)) and add the corresponding SSL certificate on your test device.



## Version
1.0