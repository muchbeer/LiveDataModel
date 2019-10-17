package muchbeer.raum.com.data.repository.datasource;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import muchbeer.raum.com.data.R;
import muchbeer.raum.com.data.model.Movie;
import muchbeer.raum.com.data.model.MovieDbResponse;
import muchbeer.raum.com.data.service.MovieDataService;
import muchbeer.raum.com.data.service.RetrofitInstance;
import muchbeer.raum.com.data.service.RetrofitInstancePaging;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static muchbeer.raum.com.data.utility.Constants.API_KEY;
import static muchbeer.raum.com.data.utility.Constants.LANGUAGE;

public class RemoteDataSourcePaging extends PageKeyedDataSource<Long, Movie> implements DataSource {

    private static final String LOG_TAG = RemoteDataSourcePaging.class.getSimpleName();
    public MovieDataService movieDataService;
    public Application application;
    ArrayList<Movie> moview;
    private final MutableLiveData<String> mError=new MutableLiveData<>();
    private  MutableLiveData<List<Movie>> mDataApi=new MutableLiveData<>();


    public RemoteDataSourcePaging(MovieDataService movieDataService, Application application) {
        this.movieDataService = movieDataService;
        this.application = application;
         }
    public RemoteDataSourcePaging() {

        mDataApi = new MutableLiveData();
        movieDataService = RetrofitInstancePaging.getClient();

    }




    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, Movie> callback) {
        Log.i(LOG_TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);
        // Call<ArrayList<Movie>> callBack = movieDataService.getMovies(API_KEY, LANGUAGE, 1);
        Call<MovieDbResponse> call =  movieDataService.getPopularMoviesWithPaging(API_KEY,
                LANGUAGE,
                1);

        call.enqueue(new Callback<MovieDbResponse>() {
            @Override
            public void onResponse(Call<MovieDbResponse> call, Response<MovieDbResponse> response) {

                MovieDbResponse movieDBResponse=response.body();
                ArrayList<Movie> movies = new ArrayList<>();

                if(movieDBResponse != null && movieDBResponse.getMovies() != null) {
                    movies = (ArrayList<Movie>) movieDBResponse.getMovies();
                    Log.d(LOG_TAG, "Collected data from the database is: " + movies);

                    // networkState.postValue(NetworkState.LOADED);
                    // response.body().forEach(moviesObservable::onNext);
                    callback.onResult(movies, null, (long) 2);
                }
                else {
                    Log.e(LOG_TAG + "API CALL WARNING", response.message());
                    //  networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));

                }
            }

            @Override
            public void onFailure(Call<MovieDbResponse> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                    Log.d(LOG_TAG, "The error message is: "+ errorMessage.toString());
                }
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {
        Log.i(LOG_TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);

              // Call<ArrayList<Movie>> callBack = movieDataService.getMovies(API_KEY, LANGUAGE, 1);
        Call<MovieDbResponse> call =  movieDataService.getPopularMoviesWithPaging(application.getApplicationContext()
                        .getString(R.string.apiKey),
                LANGUAGE,
                1);

        call.enqueue(new Callback<MovieDbResponse>() {
            @Override
            public void onResponse(Call<MovieDbResponse> call, Response<MovieDbResponse> response) {

                MovieDbResponse movieDBResponse=response.body();
                ArrayList<Movie> movies = new ArrayList<>();

                if(movieDBResponse != null && movieDBResponse.getMovies() != null) {
                    movies = (ArrayList<Movie>) movieDBResponse.getMovies();
                    Log.d(LOG_TAG, "Collected data from the database is: " + movies);

                    // networkState.postValue(NetworkState.LOADED);
                    //   response.body().forEach(moviesObservable::onNext);
                    //   callback.onResult(movies, Integer.toString(1), Integer.toString(2));
                    callback.onResult(movies, params.key+1);
                }
                else {
                    Log.e(LOG_TAG + "API CALL", response.message());
                    //  networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));

                }
            }

            @Override
            public void onFailure(Call<MovieDbResponse> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                    mError.setValue(errorMessage);
                } else {
                    errorMessage = t.getMessage();
                    Log.d(LOG_TAG, "The error message is: "+ errorMessage.toString());
                    mError.setValue("Problem is: " + errorMessage.toString());
                }
            }
        });
    }



    public void fetch() {
        movieDataService = RetrofitInstance.getService();
        Call<MovieDbResponse> call = movieDataService.getPopularMovies(application.getApplicationContext().getString(R.string.apiKey));
        call.enqueue(new Callback<MovieDbResponse>() {
            @Override
            public void onResponse(Call<MovieDbResponse> call, Response<MovieDbResponse> response) {
                MovieDbResponse movieDbResponse = response.body();
                if (movieDbResponse != null && movieDbResponse.getMovies() != null) {

                    moview = (ArrayList<Movie>) movieDbResponse.getMovies();
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

    @Override
    public LiveData getMovieDataStream() {
        return mDataApi;
    }

    @Override
    public LiveData<String> getErrorStream() {
        return mError;
    }
}
