package com.jeremy94.portfolio_diary_v2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService2 {
    @GET("weather")
    Call<CurrentData> getCurrentData(@Query("q") String q,
                                     @Query("mode") String mode,
                                     @Query("units") String units,
                                     @Query("appid") String appid);

}
