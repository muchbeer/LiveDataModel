package muchbeer.raum.com.livedatamodel.service;

import muchbeer.raum.com.livedatamodel.model.MovieDbResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataService {

    @GET("movie/popular")
    Call<MovieDbResponse> getPopularMovies(@Query("api_key") String apiKey);
}
