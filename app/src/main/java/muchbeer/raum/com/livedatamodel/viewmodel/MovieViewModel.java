package muchbeer.raum.com.livedatamodel.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import muchbeer.raum.com.data.model.Movie;
import muchbeer.raum.com.data.repository.MovieRepositoryImpl;
import muchbeer.raum.com.data.repository.MovieRepositoryInterface;

public class MovieViewModel extends AndroidViewModel {

    private static final String TAG = MovieViewModel.class.getSimpleName();
    private MovieRepositoryInterface mMovieRepositoryInterface;

    public LiveData<List<Movie>> getMovieDataModel() {
        return mMovieRepositoryInterface.getMovieData();
    }

    public LiveData<String> getErrorUpdates() {
        return mMovieRepositoryInterface.getErrorStream();
    }


    public MovieViewModel(@NonNull Application application) {
        super(application);

        mMovieRepositoryInterface = MovieRepositoryImpl.create(application);
    }

    @Override
    protected void onCleared() {
        Log.d(TAG, "onCleared() called");
        super.onCleared();
    }


    public void fetchData() {
        mMovieRepositoryInterface.fetchData();
    }


}
