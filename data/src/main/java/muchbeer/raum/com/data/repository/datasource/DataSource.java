package muchbeer.raum.com.data.repository.datasource;

import androidx.lifecycle.LiveData;

public interface DataSource<T> {

    LiveData<T> getMovieDataStream();
    LiveData<String> getErrorStream();

}
