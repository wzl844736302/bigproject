package wzl.com.wproject.core;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import wzl.com.wproject.bean.Circle;
import wzl.com.wproject.bean.Evaluation;
import wzl.com.wproject.bean.FirstCate;
import wzl.com.wproject.bean.FootPrint;
import wzl.com.wproject.bean.Goodsdetails;
import wzl.com.wproject.bean.ImagesBan;
import wzl.com.wproject.bean.MyCircle;
import wzl.com.wproject.bean.Querys;
import wzl.com.wproject.bean.Search;
import wzl.com.wproject.bean.ShAddress;
import wzl.com.wproject.bean.ShopCar;
import wzl.com.wproject.bean.Wallet;
import wzl.com.wproject.bean.dingdan.DetalList;
import wzl.com.wproject.bean.shops.NewProducts;
import wzl.com.wproject.bean.Result;
import wzl.com.wproject.bean.User;

public interface ILogin {
    @POST("user/v1/login")
    @FormUrlEncoded
    Observable<Result<User>> login(@Field("phone")String mobile,
                                   @Field("pwd")String pass);
    @POST("user/v1/register")
    @FormUrlEncoded
    Observable<Result> register(@Field("phone")String mobile,
                                @Field("pwd")String pass);

    @GET("commodity/v1/bannerShow")
    Observable<Result<List<ImagesBan>>> register();

    @GET("commodity/v1/commodityList")
    Observable<Result<NewProducts>> rxxp();

    @GET("commodity/v1/findCommodityDetailsById")
    Observable<Result<Goodsdetails>> findCommodityDetailsById(@Header("userId") int userid,
                                                              @Header("sessionId") String sessionid,
                                                              @Query("commodityId") int commodityid);

    @GET("circle/v1/findCircleList")
    Observable<Result<List<Circle>>> findCircleList(@Header("userId") int userid,
                                                    @Header("sessionId") String sessionid,
                                                    @Query("page")int page,
                                                    @Query("count")int count);

    @GET("commodity/v1/findCommodityByKeyword")
    Observable<Result<List<Search>>> findCommodityByKeyword(@Query("keyword")String key,
                                                            @Query("page")int page,
                                                            @Query("count")int count);
    @GET("commodity/v1/CommodityCommentList")
    Observable<Result<List<Evaluation>>> CommodityCommentList(@Query("commodityId")int commod,
                                                              @Query("page")int page,
                                                              @Query("count")int count);

    /**
     * 查询钱包
     * @param userid
     * @param session
     * @param page
     * @param count
     * @return
     */
    @GET("user/verify/v1/findUserWallet")
    Observable<Result<Wallet>> findUserWallet(@Header("userId") int userid,
                                              @Header("sessionId")String session,
                                              @Query("page")int page,
                                              @Query("count")int count);

    /**
     * 查询一级商品类目
     * @return
     */
    @GET("commodity/v1/findFirstCategory")
    Observable<Result<List<FirstCate>>> findFirstCategory();

    /**
     * 我的足迹
     * @return
     */
    @GET("commodity/verify/v1/browseList")
    Observable<Result<List<FootPrint>>> browseList(@Header("userId")int userid,
                                                   @Header("sessionId")String sessionid,
                                                   @Query("page")int page,
                                                   @Query("count")int count);

    /**
     * 新增收货地址
     * @param userid
     * @param session
     * @param name
     * @param phone
     * @param address
     * @param code
     * @return
     */
    @POST("user/verify/v1/addReceiveAddress")
    @FormUrlEncoded
    Observable<Result> addReceiveAddress(@Header("userId")int userid,
                                         @Header("sessionId")String session,
                                         @Field("realName")String name,
                                         @Field("phone")String phone,
                                         @Field("address")String address,
                                         @Field("zipCode")String code);

    /**
     * 收货地址列表
     * @param userid
     * @param session
     * @return
     */
    @GET("user/verify/v1/receiveAddressList")
    Observable<Result<List<ShAddress>>> receiveAddressList(@Header("userId")int userid,
                                                           @Header("sessionId")String session);

    /**
     * 创建订单
     * @param userid
     * @param session
     * @param order
     * @param total
     * @param address
     * @return
     */
    @POST("order/verify/v1/createOrder")
    @FormUrlEncoded
    Observable<Result> createOrder(@Header("userId")int userid,
                                  @Header("sessionId")String session,
                                  @Field("orderInfo")String order,
                                  @Field("totalPrice")double total,
                                  @Field("addressId")int address);

    /**
     * 根据订单状态查询订单信息
     * @param userid
     * @param session
     * @param status
     * @param page
     * @param count
     * @return
     */
    @GET("order/verify/v1/findOrderListByStatus")
    Observable<Result<List<DetalList>>> findOrderListByStatus(@Header("userId")int userid,
                                                        @Header("sessionId")String session,
                                                        @Query("status")int status,
                                                        @Query("page")int page,
                                                        @Query("count")int count);

    /**
     * 同步购物车数据
     * @param userid
     * @param session
     * @param data
     * @return
     */
    @PUT("order/verify/v1/syncShoppingCart")
    @FormUrlEncoded
    Observable<Result> syncShoppingCart(@Header("userId")int userid,
                                      @Header("sessionId")String session,
                                      @Field("data")String data);

    /**
     * 查询购物车
     * @param userid
     * @param session
     * @return
     */
    @GET("order/verify/v1/findShoppingCart")
    Observable<Result<List<ShopCar>>> findShoppingCart(@Header("userId")int userid,
                                                       @Header("sessionId")String session);

    /**
     * 支付
     * @param userid
     * @param session
     * @param orderid
     * @param paytype
     * @return
     */
    @POST("order/verify/v1/pay")
    @FormUrlEncoded
    Observable<Result> pay(@Header("userId")int userid,
                           @Header("sessionId")String session,
                           @Field("orderId")String orderid,
                           @Field("payType")int paytype);

    /**
     * 圈子点赞
     * @param userid
     * @param session
     * @param cirlceid
     * @return
     */
    @POST("circle/verify/v1/addCircleGreat")
    @FormUrlEncoded
    Observable<Result> addCircleGreat(@Header("userId")int userid,
                                      @Header("sessionId")String session,
                                      @Field("circleId")int cirlceid);

    /**
     * 取消点赞
     * @param userid
     * @param session
     * @param cirlceid
     * @return
     */
    @DELETE("circle/verify/v1/cancelCircleGreat")
    Observable<Result> cancelCircleGreat(@Header("userId")int userid,
                                         @Header("sessionId")String session,
                                         @Query("circleId") int cirlceid);

    /**
     * 发布圈子
     * @param userId
     * @param sessionId
     * @param body
     * @return
     */
    @POST("circle/verify/v1/releaseCircle")
    Observable<Result> releaseCircle(@Header("userId") int userId,
                                     @Header("sessionId")String sessionId,
                                     @Body MultipartBody body);

    /**
     * 我的圈子
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("circle/verify/v1/findMyCircleById")
    Observable<Result<List<MyCircle>>> findMyCircleById(@Header("userId") int userId,
                                                        @Header("sessionId")String sessionId,
                                                        @Query("page")int page,
                                                        @Query("count")int count);

    /**
     * 收货
     * @param userId
     * @param sessionId
     * @param orderId
     * @return
     */
    @PUT("order/verify/v1/confirmReceipt")
    @FormUrlEncoded
    Observable<Result> confirmReceipt(@Header("userId") int userId,
                                      @Header("sessionId")String sessionId,
                                      @Field("orderId")String orderId);
}
