package muchbeer.raum.com.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import muchbeer.raum.com.data.model.Movie;

public interface MovieRepositoryInterface {

    LiveData<List<Movie>> getMovieData();
    LiveData<String> getErrorStream();
   // LiveData<Double> getTotalMarketCapStream();
    void fetchData();


}
