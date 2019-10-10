package muchbeer.raum.com.livedatamodel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import muchbeer.raum.com.livedatamodel.model.Movie;
import muchbeer.raum.com.livedatamodel.model.MovieRepository;

public class MainActivityViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        movieRepository=new MovieRepository(application);
    }

    public LiveData<List<Movie>> getAllMoview() {
        return  movieRepository.getMutableLiveData();
    }
}
