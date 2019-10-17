package muchbeer.raum.com.data.service;


import java.util.ArrayList;

import muchbeer.raum.com.data.model.Movie;
import muchbeer.raum.com.data.model.MovieDbResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static muchbeer.raum.com.data.utility.Constants.API_KEY_REQUEST_PARAM;
import static muchbeer.raum.com.data.utility.Constants.LANGUAGE_REQUEST_PARAM;
import static muchbeer.raum.com.data.utility.Constants.PAGE_REQUEST_PARAM;

public interface MovieDataService {

    @GET("movie/popular")
    Call<MovieDbResponse> getPopularMovies(@Query("api_key") String apiKey);

    //This one is paging Retrofit
    @GET("movie/popular")
    Call<MovieDbResponse> getPopularMoviesWithPaging(@Query("api_key") String apiKey,
                                                     @Query(LANGUAGE_REQUEST_PARAM) String language,
                                                     @Query("page") long page);

    @GET(".")
    Call<ArrayList<Movie>> getMovies(@Query(API_KEY_REQUEST_PARAM) String apiKey,
                                     @Query(LANGUAGE_REQUEST_PARAM) String language,
                                     @Query(PAGE_REQUEST_PARAM) int page);
}
