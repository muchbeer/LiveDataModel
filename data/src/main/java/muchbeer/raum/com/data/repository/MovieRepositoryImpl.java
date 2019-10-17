package muchbeer.raum.com.data.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.room.RoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import muchbeer.raum.com.data.model.Movie;
import muchbeer.raum.com.data.repository.datasource.LocalDataSource;
import muchbeer.raum.com.data.repository.datasource.RemoteDataSource;


public class MovieRepositoryImpl implements MovieRepositoryInterface {

    private static final String TAG = MovieRepositoryImpl.class.getSimpleName();
    private ExecutorService mExecutor = Executors.newFixedThreadPool(5);
    private RemoteDataSource mRemoteDataSource;
    private LocalDataSource mLocalDataSource;

    MediatorLiveData<List<Movie>> mMovieDataMerger = new MediatorLiveData<>();
    MediatorLiveData<String> mMovieErrorMerger = new MediatorLiveData<>();

    public MovieRepositoryImpl(RemoteDataSource mRemoteDataSource, final LocalDataSource mLocalDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;

        mMovieDataMerger.addSource(this.mRemoteDataSource.getMovieDataStream(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(final List<Movie> onlineMovies) {
                //This is the Live data needed to be put.

                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "mDataMerger\tmRemoteDataSource onChange invoked");
                        Log.d(TAG, "The data collected from the server: " + onlineMovies);
                        mLocalDataSource.insertMoviesOnline2Local(onlineMovies);
                        mMovieDataMerger.postValue(onlineMovies);
                        // List<Movie> list = mMapper.mapEntityToModel(entities);
                        mMovieDataMerger.postValue(onlineMovies);


                    }
                });
            }
        }  );

        mMovieDataMerger.addSource(this.mLocalDataSource.getMovieDataStream(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> localMovies) {
                Log.d(TAG, "mDataMerger\tmLocalDataSource onChange invoked");
                Log.d(TAG, "Local Stored: " + localMovies);
                mMovieDataMerger.postValue(localMovies);
            }
        });

        mMovieErrorMerger.addSource(this.mRemoteDataSource.getErrorStream(), new Observer<String>() {
            @Override
            public void onChanged(String loadError) {
                Log.d(TAG, "mDataMerger\tmRemote on Error Remote Fetch onChange invoked");
                mMovieErrorMerger.postValue(loadError);
            }
        });

        mMovieErrorMerger.addSource(this.mLocalDataSource.getErrorStream(), new Observer<String>() {
            @Override
            public void onChanged(String erorString) {
                mMovieErrorMerger.setValue(erorString);
            }
        });

    }


    public static MovieRepositoryInterface create(Application application) {
        final RemoteDataSource remoteDataSource = new RemoteDataSource(application);
        final LocalDataSource localDataSource = new LocalDataSource(application);

        return new MovieRepositoryImpl(remoteDataSource, localDataSource);
    }

    @Override
    public LiveData<List<Movie>> getMovieData() {
        return mMovieDataMerger ;
    }

    @Override
    public LiveData<String> getErrorStream() {
        return mMovieErrorMerger;
    }

    @Override
    public void fetchData() {
        mRemoteDataSource.fetch();
    }
}