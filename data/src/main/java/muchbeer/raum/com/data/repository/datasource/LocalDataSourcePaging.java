package muchbeer.raum.com.data.repository.datasource;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import muchbeer.raum.com.data.db.MovieDaoPaging;
import muchbeer.raum.com.data.db.RoomDb;
import muchbeer.raum.com.data.db.RoomDbPaging;
import muchbeer.raum.com.data.model.Movie;
import muchbeer.raum.com.data.repository.paging.DBMoviesPageKeyedDataSource;

public class LocalDataSourcePaging extends androidx.paging.DataSource.Factory implements DataSource{
    private RoomDbPaging mDb;
    private static final String TAG = LocalDataSourcePaging.class.getSimpleName();
    private DBMoviesPageKeyedDataSource moviesPageKeyedDataSource;

    public LocalDataSourcePaging(MovieDaoPaging dao) {
    //    mDb = RoomDbPaging.getDatabase(mContextApplication);
        moviesPageKeyedDataSource = new DBMoviesPageKeyedDataSource(dao);
    }

    private final MutableLiveData<String> mError=new MutableLiveData<>();


    @NonNull
    @Override
    public androidx.paging.DataSource create() {
        return moviesPageKeyedDataSource;
    }

    @Override
    public LiveData getMovieDataStream() {
        return mDb.movieDaoP().getAllMoviesLive();

    }

    @Override
    public LiveData<String> getErrorStream() {
        return mError;
    }



}
