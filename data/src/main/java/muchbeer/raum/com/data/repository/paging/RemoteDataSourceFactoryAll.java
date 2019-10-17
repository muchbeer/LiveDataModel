package muchbeer.raum.com.data.repository.paging;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import muchbeer.raum.com.data.repository.datasource.RemoteDataSourcePaging;
import muchbeer.raum.com.data.service.MovieDataService;

public class RemoteDataSourceFactoryAll extends DataSource.Factory {
    //  private RemoteDataSourcePaging moviesPageKeyedDataSource;
    //from udemy real stuff

    private RemoteDataSourcePaging movieDataSource;
    private MovieDataService movieDataService;
    private Application application;
    private MutableLiveData<RemoteDataSourcePaging> mutableLiveData;


    public RemoteDataSourceFactoryAll(MovieDataService movieDataService, Application application) {
this.movieDataService = movieDataService;
this.application= application;
        mutableLiveData=new MutableLiveData<>();
    }


    @NonNull
    @Override
    public DataSource create() {
        movieDataSource = new RemoteDataSourcePaging(movieDataService, application);
        mutableLiveData.postValue(movieDataSource);
        return movieDataSource;
       }

    public MutableLiveData<RemoteDataSourcePaging> getMutableLiveData() {
        return mutableLiveData;
    }

}
