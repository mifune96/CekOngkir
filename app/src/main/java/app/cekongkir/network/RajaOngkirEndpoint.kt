package app.cekongkir.network

import app.cekongkir.network.responses.CityResponse
import app.cekongkir.network.responses.CostResponse
import app.cekongkir.network.responses.SubdistrictResponse
import app.cekongkir.network.responses.WaybillResponse
import retrofit2.Response
import retrofit2.http.*

interface RajaOngkirEndpoint {

    @GET("city")
    suspend fun city() : Response<CityResponse>

    @GET("subdistrict")
    suspend fun subdistrict(
            @Query("city") city: String
    ) : Response<SubdistrictResponse>

    @FormUrlEncoded
    @POST("cost")
    suspend fun cost(
            @Field("origin") origin: String,
            @Field("originType") originType: String,
            @Field("destination") destination: String,
            @Field("destinationType") destinationType: String,
            @Field("weight") weight: String,
            @Field("courier") courier: String
    ) : Response<CostResponse>

    @FormUrlEncoded
    @POST("waybill")
    suspend fun waybill(
            @Field("waybill") waybill: String,
            @Field("courier") courier: String
    ) : Response<WaybillResponse>
}