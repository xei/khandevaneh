package ir.tvnasim.khandevaneh.helper.webapi;

/**
 * @author H. Hosseinkhani
 *         <p>
 *         All rights reserved for Digikala corporation.
 */
public final class WebApiHelper {

    public static final String ENDPOINT_HOME = "getHomeViewModelResponse.json";
    public static final String ENDPOINT_PRODUCT = "getProductDetailsViewModelResponse.json";
    public static final String ENDPOINT_BRAND = "getBrandDetailsViewModelResponse.json";
    public static final String ENDPOINT_BRAND_LIST = "getBrandListViewModelResponse.json";
    public static final String ENDPOINT_PRODUCT_LIST="getProductsListViewModelResponse.json";
    public static final String ENDPOINT_PRODUCTS="getProducts.json";

    public static final String ENDPOINT_LOGIN = "Account/Login";
    private static final String ENDPOINT_LOGOUT = "Account/Logout";
    private static final String ENDPOINT_GET_SHIPMENTS = "Order/OrderList";
    private static final String ENDPOINT_CONFIRM_SHIPMENTS_BATCH = "Driver/DriverConfirm";
    private static final String ENDPOINT_GET_SHIPMENT = "Order/OrderItem";
    private static final String ENDPOINT_FULL_DELIVER = "Driver/CustomerConfirm";
    private static final String ENDPOINT_PARTIAL_DELIVER = "Driver/HalfReturn";
    private static final String ENDPOINT_REJECT_SHIPMENT = "Driver/FullReturn";
    private static final String ENDPOINT_MISSED_CALL = "Driver/MissCall";
    private static final String ENDPOINT_LEFT_SHIPMENT = "Driver/LeftOrder";
    private static final String ENDPOINT_SYNC_SHIPMENTS_WITH_BO = "Driver/Sync";
    private static final String ENDPOINT_FINALIZE_SHIPMENTS_BATCH = "Driver/Finalize";

//    public static MockApiRequest<HomeViewModel> getHomeViewModel(String categoryId, String requestTag, MockApiRequest.WebApiListener<HomeViewModel> webApiListener, MockApiRequest.LoadRequests fragment) {
//
//        return new MockApiRequest<>(
//                Request.Method.GET,
//                ENDPOINT_HOME,
//                null,
//                new TypeToken<MockApiRequest.WebApiResponse<HomeViewModel>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                fragment
//        );
//    }
//
//    public static MockApiRequest<ProductDetailsViewModel> getProductDetailsViewModel(String categoryId, String requestTag, MockApiRequest.WebApiListener<ProductDetailsViewModel> webApiListener, MockApiRequest.LoadRequests fragment) {
//
//        return new MockApiRequest<>(
//                Request.Method.GET,
//                ENDPOINT_PRODUCT,
//                null,
//                new TypeToken<MockApiRequest.WebApiResponse<ProductDetailsViewModel>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                fragment
//        );
//    }
//
//    public static MockApiRequest<BrandDetailsViewModel> getBrandDetailsViewModel(String id, String requestTag, MockApiRequest.WebApiListener <BrandDetailsViewModel> webApiListener, MockApiRequest.LoadRequests fragment) {
//
//        return new MockApiRequest<>(
//                Request.Method.GET,
//                ENDPOINT_BRAND,
//                null,
//                new TypeToken<MockApiRequest.WebApiResponse<BrandDetailsViewModel>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                fragment
//        );
//    }
//
//    public static MockApiRequest<ArrayList<BrandListViewModel>> getBrandListViewModel(int pageNo, String requestTag, MockApiRequest.WebApiListener<ArrayList<BrandListViewModel>> webApiListener, MockApiRequest.LoadRequests fragment) {
//
//        return new MockApiRequest<>(
//                Request.Method.GET,
//                ENDPOINT_BRAND_LIST,
//                null,
//                new TypeToken<MockApiRequest.WebApiResponse<ArrayList<BrandListViewModel>>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                fragment
//        );
//    }
//
//    public static MockApiRequest<ProductListViewModel> getProductListViewModel(String requestTag, MockApiRequest.WebApiListener<ProductListViewModel> webApiListener, MockApiRequest.LoadRequests fragment){
//        return new MockApiRequest<>(
//                Request.Method.GET,
//                ENDPOINT_PRODUCT_LIST,
//                null,
//                new TypeToken<MockApiRequest.WebApiResponse<ProductListViewModel>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                fragment
//        );
//    }
//
//    public static MockApiRequest<ProductsViewModel> getProducts(int pageNo, String requestTag, MockApiRequest.WebApiListener<ProductsViewModel> webApiListener, MockApiRequest.LoadRequests loadRequests){
//        return new MockApiRequest<>(
//                Request.Method.GET,
//                ENDPOINT_PRODUCTS,
//                null,
//                new TypeToken<MockApiRequest.WebApiResponse<ProductsViewModel>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                loadRequests
//        );
//    }



//    public static WebApiRequest<LoginResult> makeLoginRequest(String key, String password, String requestTag, WebApiRequest.WebApiListener<LoginResult> webApiListener, WebApiRequest.LoadRequests fragment) {
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("Username", key);
//        params.put("Password", password);
//
//        return new WebApiRequest<>(
//                Method.POST,
//                ENDPOINT_LOGIN,
//                params,
//                new TypeToken<WebApiRequest.WebApiResponse<LoginResult>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                fragment
//        );
//    }
//
//    public static WebApiRequest<Boolean> makeLogoutRequest(String deliveryId, String requestTag, WebApiRequest.WebApiListener<Boolean> webApiListener, WebApiRequest.LoadRequests fragment) {
//        return new WebApiRequest<>(
//                Method.POST,
//                ENDPOINT_LOGOUT + '?' + "userId" + '=' + deliveryId,
//                null,
//                new TypeToken<WebApiRequest.WebApiResponse<Boolean>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                fragment
//        );
//    }
//
//    public static WebApiRequest<Batch> makeGetBatchRequest(String deliveryId, String requestTag, WebApiRequest.WebApiListener<Batch> webApiListener, WebApiRequest.LoadRequests fragment) {
//        return new WebApiRequest<>(
//                Method.POST,
//                ENDPOINT_GET_SHIPMENTS + '?' + "userId" + '=' + deliveryId,
//                null,
//                new TypeToken<WebApiRequest.WebApiResponse<Batch>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                fragment
//        );
//    }
//
//    public static WebApiRequest<Boolean> makeConfirmBatchRequest(String batchId, String requestTag, WebApiRequest.WebApiListener<Boolean> webApiListener, WebApiRequest.LoadRequests fragment) {
//        return new WebApiRequest<>(
//                Method.POST,
//                ENDPOINT_CONFIRM_SHIPMENTS_BATCH + '?' + "packId" + '=' + batchId,
//                null,
//                new TypeToken<WebApiRequest.WebApiResponse<Boolean>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                fragment
//        );
//    }
//
//    public static WebApiRequest<ArrayList<Shipment>> makeGetShipment(String orderId, String requestTag, WebApiRequest.WebApiListener<ArrayList<Shipment>> webApiListener, WebApiRequest.LoadRequests fragment) {
//        return new WebApiRequest<>(
//                Method.POST,
//                ENDPOINT_GET_SHIPMENT + '?' + "orderId" + '=' + orderId,
//                null,
//                new TypeToken<WebApiRequest.WebApiResponse<ArrayList<Shipment>>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                fragment
//        );
//    }
//
//    public static WebApiRequest<Boolean> makeFullDeliverRequest(String shipmentId, String questionID, int answer, String signature, LatLong latLong, String requestTag, WebApiRequest.WebApiListener<Boolean> webApiListener, WebApiRequest.LoadRequests fragment) {
//
//        String lat = "";
//        String lng = "";
//        if (latLong != null) {
//            lat = String.valueOf(latLong.getLatitude());
//            lng = String.valueOf(latLong.getLongitude());
//        }
//
//        HashMap<String, String> param = new HashMap<>();
//        param.put("OrderId", shipmentId);
//        param.put("Signature", signature);
//        param.put("Latitude", lat);
//        param.put("Longitude", lng);
//        param.put("Answer", String.valueOf(answer));
//        param.put("QuestionID", questionID);
//
//        return new WebApiRequest<>(
//                Method.POST,
//                ENDPOINT_FULL_DELIVER,
//                param,
//                new TypeToken<WebApiRequest.WebApiResponse<Boolean>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                fragment
//        );
//    }
//
//    public static void makePartialDeliverRequest(String shipmentId, String questionID, int answer, ArrayList<ReturnedProduct> returnedProducts, String signature, LatLong latLong, String requestTag, final Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
//        Type paramType = new TypeToken<PartialDeliver>() {
//        }.getType();
//
//        Gson gson = new GsonBuilder()
//                .setExclusionStrategies(new ExclusionStrategy() {
//                    @Override
//                    public boolean shouldSkipField(FieldAttributes f) {
//                        return f.getDeclaringClass().equals(RealmObject.class);
//                    }
//
//                    @Override
//                    public boolean shouldSkipClass(Class<?> clazz) {
//                        return false;
//                    }
//                }).create();
//
//        String lat = "";
//        String lng = "";
//        if (latLong != null) {
//            lat = String.valueOf(latLong.getLatitude());
//            lng = String.valueOf(latLong.getLongitude());
//        }
//
//        PartialDeliver partialDeliver = new PartialDeliver();
//        partialDeliver.setOrderId(shipmentId);
//        partialDeliver.setOrderItems(returnedProducts);
//        partialDeliver.setSignature(signature);
//        partialDeliver.setLatitude(lat);
//        partialDeliver.setLongitude(lng);
//        partialDeliver.setAnswer(answer);
//        partialDeliver.setQuestionID(questionID);
//
//        MCXIAOKEJsonObjectRequest request = new MCXIAOKEJsonObjectRequest(Method.POST,
//                AppConfig.URL_WEBAPI + ENDPOINT_PARTIAL_DELIVER,
//                gson.toJson(partialDeliver, paramType),
//                responseListener, errorListener) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("AuthToken", Util.getDeliveryAuthToken(App.getApplication()));
//                headers.put("AppVersion", String.valueOf(Util.getAppVersionCode(App.getApplication())));
//                return headers;
//            }
//
//            @Override
//            public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
//                return super.setRetryPolicy(new DefaultRetryPolicy(
//                        AppConfig.postRequestsInitialTimeoutMs,
//                        AppConfig.postRequestsRetriesNumber,
//                        AppConfig.postRequestsBackoffMultiplier));
//            }
//        };
//
//        VolleyHelper.addToRequestQueue(request);
//    }
//
//    public static WebApiRequest<Boolean> makeRejectShipmentRequest(ReturnedShipment returnedShipment, LatLong latLong, String requestTag, WebApiRequest.WebApiListener<Boolean> webApiListener, WebApiRequest.LoadRequests fragment) {
//
//        String lat = "";
//        String lng = "";
//        if (latLong != null) {
//            lat = String.valueOf(latLong.getLatitude());
//            lng = String.valueOf(latLong.getLongitude());
//        }
//
//        HashMap<String, String> param = new HashMap<>();
//        param.put("OrderId", returnedShipment.getShipmentId());
//        param.put("ReasonId", returnedShipment.getReasonId());
//        param.put("Latitude", lat);
//        param.put("Longitude", lng);
//
//        return new WebApiRequest<>(
//                Method.POST,
//                ENDPOINT_REJECT_SHIPMENT,
//                param,
//                new TypeToken<WebApiRequest.WebApiResponse<Boolean>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                fragment
//        );
//    }
//
//    public static WebApiRequest<Boolean> makeMissedCallRequest(String shipmentId, LatLong latLong, String requestTag, WebApiRequest.WebApiListener<Boolean> webApiListener, WebApiRequest.LoadRequests fragment) {
//
//        String lat = "";
//        String lng = "";
//        if (latLong != null) {
//            lat = String.valueOf(latLong.getLatitude());
//            lng = String.valueOf(latLong.getLongitude());
//        }
//
//        HashMap<String, String> param = new HashMap<>();
//        param.put("OrderId", shipmentId);
//        param.put("Latitude", lat);
//        param.put("Longitude", lng);
//
//        return new WebApiRequest<>(
//                Method.POST,
//                ENDPOINT_MISSED_CALL,
//                param,
//                new TypeToken<WebApiRequest.WebApiResponse<Boolean>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                fragment
//        );
//    }
//
//    public static WebApiRequest<Boolean> makeLeftShipmentRequest(String orderId, String requestTag, WebApiRequest.WebApiListener<Boolean> webApiListener, WebApiRequest.LoadRequests fragment) {
//
//        return new WebApiRequest<>(
//                Method.POST,
//                ENDPOINT_LEFT_SHIPMENT + "?orderId=" + orderId,
//                null,
//                new TypeToken<WebApiRequest.WebApiResponse<Boolean>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                fragment
//        );
//    }
//
//    public static void makeSyncShipmentsWithBackOfficeRequest(String deliveryId, String batchId, List<Shipment> shipments, final Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
//        Gson gson = new GsonBuilder()
//                .setExclusionStrategies(new ExclusionStrategy() {
//                    @Override
//                    public boolean shouldSkipField(FieldAttributes f) {
//                        return f.getDeclaringClass().equals(RealmObject.class);
//                    }
//
//                    @Override
//                    public boolean shouldSkipClass(Class<?> clazz) {
//                        return false;
//                    }
//                }).create();
//
//        Type paramType = new TypeToken<ArrayList<SyncCandidateShipment>>() {
//        }.getType();
//
//
//        ArrayList<SyncCandidateShipment> syncCandidateShipments = new ArrayList<>();
//        for (Shipment shipment : shipments) {
//            SyncCandidateShipment syncCandidateShipment = new SyncCandidateShipment();
//            syncCandidateShipment.setOrderId(shipment.getOrderId());
//            syncCandidateShipment.setDeliveryDateTime(shipment.getConfirmTime());
//            syncCandidateShipment.setReasonId(shipment.getRejectReasonId());
//            syncCandidateShipment.setStatus(shipment.getStatus());
//            syncCandidateShipment.setSignature(shipment.getSignature());
//            syncCandidateShipment.setQuestionID(shipment.getQuestionID());
//            syncCandidateShipment.setAnswer(shipment.getAnswer());
//            syncCandidateShipment.setLatitude(shipment.getConfirmLocation().getLatitude());
//            syncCandidateShipment.setLongitude(shipment.getConfirmLocation().getLongitude());
//
//            ArrayList<NonRealmReturnedProduct> returnedProducts = new ArrayList<>();
//            for (ReturnedProduct returnedProduct : shipment.getReturnedProducts()) {
//                returnedProducts.add(new NonRealmReturnedProduct(returnedProduct.getOrderItemId(), returnedProduct.getReasonId(), returnedProduct.getCount()));
//            }
//            syncCandidateShipment.setOrderItems(returnedProducts);
//            syncCandidateShipment.setUserId(deliveryId);
//            syncCandidateShipment.setPackId(batchId);
//            syncCandidateShipments.add(syncCandidateShipment);
//        }
//
//        MCXIAOKEJsonObjectRequest request = new MCXIAOKEJsonObjectRequest(Method.POST,
//                AppConfig.URL_WEBAPI + ENDPOINT_SYNC_SHIPMENTS_WITH_BO,
//                gson.toJson(syncCandidateShipments, paramType),
//                responseListener, errorListener) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("AuthToken", Util.getDeliveryAuthToken(App.getApplication()));
//                headers.put("AppVersion", String.valueOf(Util.getAppVersionCode(App.getApplication())));
//                return headers;
//            }
//
//            @Override
//            public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
//                return super.setRetryPolicy(new DefaultRetryPolicy(
//                        AppConfig.postRequestsInitialTimeoutMs,
//                        AppConfig.postRequestsRetriesNumber,
//                        AppConfig.postRequestsBackoffMultiplier));
//            }
//        };
//
//        VolleyHelper.addToRequestQueue(request);
//    }
//
//    public static WebApiRequest<Batch> makeFinalizeBatchRequest(String batchId, String requestTag, WebApiRequest.WebApiListener<Boolean> webApiListener, WebApiRequest.LoadRequests fragment) {
//        return new WebApiRequest<>(
//                Method.POST,
//                ENDPOINT_FINALIZE_SHIPMENTS_BATCH + '?' + "packId" + '=' + batchId,
//                null,
//                new TypeToken<WebApiRequest.WebApiResponse<Boolean>>() {
//                }.getType(),
//                requestTag,
//                webApiListener,
//                fragment
//        );
//    }

}