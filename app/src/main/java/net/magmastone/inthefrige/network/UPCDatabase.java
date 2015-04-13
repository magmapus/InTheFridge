package net.magmastone.inthefrige.network;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by alex on 2/28/15.
 */
public interface UPCDatabase {
    @GET("/")
    UPCItem getItem(@Query("upc") String upcCode);
    @FormUrlEncoded
    @POST("/")
    UPCItem postItem(@Field("upc") String upc, @Field("name") String name, @Field("type") String type, @Field("pic") String pic, @Field("expiry") String expiry);
}
