package muchbeer.raum.com.data.repository.paging;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import muchbeer.raum.com.data.repository.datasource.RemoteDataSource;
import muchbeer.raum.com.data.repository.datasource.RemoteDataSourcePaging;
import muchbeer.raum.com.data.service.MovieDataService;


//all the above code is the paging from udemy instruction
public class RemoteDataSourceFactory extends DataSource.Factory {

    private RemoteDataSource movieDataSource;
    private MovieDataService movieDataService;
    private Application application;
  //  private MutableLiveData<RemoteDataSource> mutableLiveData;


    private MutableLiveData<RemoteDataSourcePaging> mutableLiveData;
    private RemoteDataSourcePaging moviesPageKeyedDataSource;

    public RemoteDataSourceFactory() {

        this.mutableLiveData=new MutableLiveData<>();

        moviesPageKeyedDataSource = new RemoteDataSourcePaging();


    }


    @NonNull
    @Override
    public DataSource create() {
        mutableLiveData.postValue(moviesPageKeyedDataSource);
        return moviesPageKeyedDataSource;
    }

    public MutableLiveData<RemoteDataSourcePaging> getMutableLiveData() {
        return mutableLiveData;
    }
}
