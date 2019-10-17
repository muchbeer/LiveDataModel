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
import java.util.concurrent.Executors;

import muchbeer.raum.com.data.db.MovieDaoPaging;
import muchbeer.raum.com.data.model.Movie;
import muchbeer.raum.com.data.repository.MovieRepository;
import muchbeer.raum.com.data.repository.MovieRepositoryImpl;
import muchbeer.raum.com.data.repository.MovieRepositoryInterface;
import muchbeer.raum.com.data.repository.datasource.LocalDataSourcePaging;
import muchbeer.raum.com.data.repository.paging.DBMoviesPageKeyedDataSource;

public class MainActivityViewModelPaging extends AndroidViewModel {

    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    private MovieRepositoryInterface mMovieRepositoryInterface;
    private MovieDaoPaging concertDao;
    public final LiveData<PagedList<Movie>> movieListPaging;
    private Executor executor;
    LiveData<DBMoviesPageKeyedDataSource> movieDataSourceLiveData;

    public MainActivityViewModelPaging(@NonNull Application application, MovieDaoPaging concertDao) {
        super(application);

   this.concertDao = concertDao;
        mMovieRepositoryInterface = MovieRepository.create(application);
        LocalDataSourcePaging localDataSource = new LocalDataSourcePaging(concertDao);

       movieDataSourceLiveData = localDataSource.getMovieDataStream();

        PagedList.Config config = (new PagedList.Config.Builder())
                            .setEnablePlaceholders(true)
                            .setInitialLoadSizeHint(10)
                            .setPageSize(20)
                            .setPrefetchDistance(4)
                            .build();

        executor = Executors.newFixedThreadPool(5);


        movieListPaging = (new LivePagedListBuilder<Integer, Movie>(concertDao.getAllMovieOnPaging(), 50)).build();

     /*   movieListPaging = (new LivePagedListBuilder<Long, Movie>(localDataSource, config))
                                                .setFetchExecutor(executor)
                                          //      .setBoundaryCallback()
                                                .build();
*/
       /* ,config))
                                            .setFetchExecutor(executor)
                                          //  .setBoundaryCallback()
                                            .build();*/


     /*   private PagedList.BoundaryCallback<Movie> boundaryCallback = new PagedList.BoundaryCallback<Movie>() {
            @Override
            public void onZeroItemsLoaded() {
                super.onZeroItemsLoaded();
                liveDataMerger.addSource(database.getMovies(), value -> {
                    liveDataMerger.setValue(value);
                    liveDataMerger.removeSource(database.getMovies());
                });
            }
        };*/
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

    public LiveData<String> getErrorUpdates() {
        return mMovieRepositoryInterface.getErrorStream();
    }


}
