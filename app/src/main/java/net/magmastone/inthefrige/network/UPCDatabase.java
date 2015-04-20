package net.magmastone.inthefrige.network;

import java.util.List;

import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by alex on 2/28/15.
 */
public interface UPCDatabase {


    @GET("/slist")
    List<UPCItem> shoppinglist();

    @GET("/")
    UPCItem getItem(@Query("upc") String upcCode);
    @FormUrlEncoded
    @POST("/")
    UPCItem postItem(@Field("upc") String upc, @Field("name") String name, @Field("type") String type, @Field("pic") String pic, @Field("expiry") String expiry);
    @GET("/status")
    FRGItem getStatus(@Query("upc") String upcCode);
    @FormUrlEncoded
    @POST("/checkin")
    FRGItem postFrg(@Field("upc") String upc, @Field("uname") String name, @Field("quant") String quan, @Field("expiry") String exp);
    @GET("/checkin")
    FRGItem remItem(@Query("upc") String upc, @Query("uname") String name);

    @GET("/frgData")
    List<FRGItem> frgItems();

    @GET("/frgupcData")
    List<UPCItem> frgupcItems();


    @FormUrlEncoded
    @POST("/slist")
    Response postListItem(@Field("upc") String upc);

    @GET("/rec")
    List<RECItem> getRecs();
}
