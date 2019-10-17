package muchbeer.raum.com.data.repository.datasource;

import android.app.Application;
import android.content.Context;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import muchbeer.raum.com.data.db.RoomDbPaging;
import muchbeer.raum.com.data.model.Movie;

public class LocalDataSource  implements DataSource<List<Movie>> {

private final RoomDbPaging mDb;
private final MutableLiveData<String> mError=new MutableLiveData<>();

public LocalDataSource(Context mContextApplication) {
        mDb = RoomDbPaging.getDatabase(mContextApplication);
        }

@Override
public LiveData<List<Movie>> getMovieDataStream() {
        return mDb.movieDaoP().getAllMoviesLive();
        }

@Override
public LiveData<String> getErrorStream() {
        return mError;
        }

public void insertMoviesOnline2Local(List<Movie> movies) {
        try {
        mDb.movieDaoP().insertMovies(movies);
        }catch(Exception e)
        {
        e.printStackTrace();
        mError.postValue(e.getMessage());
        }
        }


        }