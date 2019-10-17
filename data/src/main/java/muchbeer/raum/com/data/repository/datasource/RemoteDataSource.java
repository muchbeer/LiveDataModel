package muchbeer.raum.com.data.repository.datasource;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import java.util.ArrayList;
import java.util.List;

import muchbeer.raum.com.data.R;
import muchbeer.raum.com.data.model.Movie;
import muchbeer.raum.com.data.model.MovieDbResponse;
import muchbeer.raum.com.data.service.MovieDataService;
import muchbeer.raum.com.data.service.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static muchbeer.raum.com.data.utility.Constants.API_KEY;
import static muchbeer.raum.com.data.utility.Constants.LANGUAGE;


//paging extends PageKeyedDataSource
public class RemoteDataSource  implements DataSource<List<Movie>> {

    private static final String LOG_TAG = RemoteDataSource.class.getSimpleName();
    public MovieDataService movieDataService;
    ArrayList<Movie> moview;
    private final MutableLiveData<String> mError=new MutableLiveData<>();
    private final MutableLiveData<List<Movie>> mDataApi=new MutableLiveData<>();
    public Application application;

    public RemoteDataSource(Application application) {
        this.application = application;
    }

    @Override
    public LiveData<List<Movie>> getMovieDataStream() {
        return mDataApi;
    }

    @Override
    public LiveData<String> getErrorStream() {
        return mError;
    }

    public void fetch() {
        movieDataService = RetrofitInstance.getService();

            //this is paging
        Call<MovieDbResponse> call =  movieDataService.getPopularMoviesWithPaging(API_KEY,
                LANGUAGE,
                1);
        //this below is without paging
       // Call<MovieDbResponse> call = movieDataService.getPopularMovies(application.getApplicationContext().getString(R.string.apiKey));
        call.enqueue(new Callback<MovieDbResponse>() {
            @Override
            public void onResponse(Call<MovieDbResponse> call, Response<MovieDbResponse> response) {
                MovieDbResponse movieDbResponse = response.body();
                if (movieDbResponse != null && movieDbResponse.getMovies() != null) {

                    moview = (ArrayList<Movie>) movieDbResponse.getMovies();
                    Log.d(LOG_TAG, "All the movies listed as : "+ moview);

                    mDataApi.setValue(moview);

                }
            }

            @Override
            public void onFailure(Call<MovieDbResponse> call, Throwable error) {
                Log.d(LOG_TAG, "Thread->" +
                        Thread.currentThread().getName() + "\tGot network error");
                mError.setValue(error.toString());
            }
        });

    }
}