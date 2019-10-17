package muchbeer.raum.com.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import muchbeer.raum.com.data.model.Movie;
import muchbeer.raum.com.data.repository.datasource.RemoteDataSource;
import muchbeer.raum.com.data.repository.paging.RemoteDataSourceFactory;
import muchbeer.raum.com.data.repository.paging.RemoteDataSourceFactoryAll;

public class MovieNetwork {

   public static final int LOADING_PAGE_SIZE = 20;

    final private static String TAG = MovieNetwork.class.getSimpleName();
    private static final int NUMBERS_OF_THREADS = 4;
    final private LiveData<PagedList<Movie>> moviesPaged;


    public MovieNetwork(RemoteDataSourceFactoryAll dataSourceFactory, PagedList.BoundaryCallback<Movie> boundaryCallback){
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(LOADING_PAGE_SIZE).setPageSize(LOADING_PAGE_SIZE).build();
   /*     networkState = Transformations.switchMap(dataSourceFactory.getNetworkStatus(),
                (Function<NetMoviesPageKeyedDataSource, LiveData<NetworkState>>)
                        NetMoviesPageKeyedDataSource::getNetworkState);*/
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        moviesPaged = livePagedListBuilder.
                setFetchExecutor(executor).
                setBoundaryCallback(boundaryCallback).
                build();

    }


    public LiveData<PagedList<Movie>> getPagedMovies(){
        return moviesPaged;
    }


}
