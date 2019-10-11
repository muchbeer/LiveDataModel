package muchbeer.raum.com.livedatamodel.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import muchbeer.raum.com.data.model.Movie;
import muchbeer.raum.com.data.repository.MovieRepository;
import muchbeer.raum.com.data.repository.MovieRepositoryImpl;
import muchbeer.raum.com.data.repository.MovieRepositoryInterface;


public class MainActivityViewModel extends AndroidViewModel {


    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    private MovieRepositoryInterface mMovieRepositoryInterface;

   // private MovieRepository movieRepository;


    public LiveData<List<Movie>> getAllMovieLocalData() {
        return mMovieRepositoryInterface.getMovieData();
    }

    public LiveData<String> getErrorUpdates() {
        return mMovieRepositoryInterface.getErrorStream();
    }

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

       // movieRepository=new MovieRepository(application);
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
  /*  public LiveData<List<Movie>> getAllMoview() {
        return  movieRepository.getMutableLiveData();
    }*/
}
