package muchbeer.raum.com.data.repository.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import muchbeer.raum.com.data.db.MovieDao;
import muchbeer.raum.com.data.db.MovieDaoPaging;
import muchbeer.raum.com.data.model.Movie;

public class DBMoviesPageKeyedDataSource extends PageKeyedDataSource<Long, Movie> {

    public static final String TAG = DBMoviesPageKeyedDataSource.class.getSimpleName();
    private final MovieDaoPaging movieDaoP;

    public DBMoviesPageKeyedDataSource(MovieDaoPaging movieDaoP) {
        this.movieDaoP = movieDaoP;
    }

     @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, Movie> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);
        List<Movie> movies = movieDaoP.getMovies();
        if(movies.size() != 0) {
            callback.onResult(movies, (long)0, (long)1);
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {

    }
}
