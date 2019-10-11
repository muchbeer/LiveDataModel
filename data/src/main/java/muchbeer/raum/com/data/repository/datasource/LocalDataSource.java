package muchbeer.raum.com.data.repository.datasource;

import android.app.Application;
import android.content.Context;
import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import muchbeer.raum.com.data.db.RoomDb;
import muchbeer.raum.com.data.model.Movie;

public class LocalDataSource implements DataSource<List<Movie>> {

    private final RoomDb mDb;
    private final MutableLiveData<String> mError=new MutableLiveData<>();

    public LocalDataSource(Context mContextApplication) {
        mDb = RoomDb.getDatabase(mContextApplication);
    }

    @Override
    public LiveData<List<Movie>> getMovieDataStream() {
        return mDb.movieDao().getAllMoviesLive();
    }

    @Override
    public LiveData<String> getErrorStream() {
        return mError;
    }

    public void insertMoviesOnline2Local(List<Movie> movies) {
        try {
            mDb.movieDao().insertMovies(movies);
        }catch(Exception e)
        {
            e.printStackTrace();
            mError.postValue(e.getMessage());
        }
    }

    public List<Movie> getALlCoinsOnceConnectionFail() {
        return mDb.movieDao().getAllMovieLocal();
    }
}
