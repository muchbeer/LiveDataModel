package muchbeer.raum.com.livedatamodel.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import muchbeer.raum.com.livedatamodel.R;
import muchbeer.raum.com.livedatamodel.service.MovieDataService;
import muchbeer.raum.com.livedatamodel.service.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
        public MovieDataService movieDataService;
    ArrayList<Movie> moview;
        public Application application;
        MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();

//Does not add the PageList implementation that is why we remove the MovieDataService
    public MovieRepository(Application application) {
       this.application = application;



    }


    public MutableLiveData<List<Movie>> getMutableLiveData() {

        movieDataService = RetrofitInstance.getService();
        Call<MovieDbResponse> call = movieDataService.getPopularMovies(application.getApplicationContext().getString(R.string.apiKey));
        call.enqueue(new Callback<MovieDbResponse>() {
            @Override
            public void onResponse(Call<MovieDbResponse> call, Response<MovieDbResponse> response) {
                MovieDbResponse movieDbResponse = response.body();
                if(movieDbResponse !=null && movieDbResponse.getMovies()!=null) {

                    moview = (ArrayList<Movie>) movieDbResponse.getMovies();
                    mutableLiveData.setValue(moview);

                }
            }

            @Override
            public void onFailure(Call<MovieDbResponse> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
