package muchbeer.raum.com.data.repository.datasource;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public interface DataSource<T> {

    LiveData<T> getMovieDataStream();
    LiveData<String> getErrorStream();

}
