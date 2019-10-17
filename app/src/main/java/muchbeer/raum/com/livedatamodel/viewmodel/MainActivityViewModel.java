package muchbeer.raum.com.livedatamodel.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;
import java.util.concurrent.Executor;

import muchbeer.raum.com.data.db.MovieDao;
import muchbeer.raum.com.data.model.Movie;
import muchbeer.raum.com.data.repository.MovieRepository;
import muchbeer.raum.com.data.repository.MovieRepositoryImpl;
import muchbeer.raum.com.data.repository.MovieRepositoryInterface;
import muchbeer.raum.com.data.repository.datasource.RemoteDataSource;


public class MainActivityViewModel extends AndroidViewModel {
//paging stuff
private MovieDao concertDao;
    public final LiveData<PagedList<Movie>> movieListPaging;

    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    private MovieRepositoryInterface mMovieRepositoryInterface;

      public LiveData<List<Movie>> getAllMovieLocalData() {
        return mMovieRepositoryInterface.getMovieData();
    }

    public LiveData<String> getErrorUpdates() {
        return mMovieRepositoryInterface.getErrorStream();
    }

    public MainActivityViewModel(@NonNull Application application,MovieDao concertDao) {
        super(application);

        this.concertDao = concertDao;
       mMovieRepositoryInterface = MovieRepositoryImpl.create(application);

        movieListPaging = (new LivePagedListBuilder<Integer, Movie>(concertDao.getAllMovieOnPaging(), 50)).build();
    }


    public LiveData<PagedList<Movie>> getMoviesPagedList() {
        return movieListPaging;
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