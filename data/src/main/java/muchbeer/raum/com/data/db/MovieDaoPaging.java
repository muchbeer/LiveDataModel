package muchbeer.raum.com.data.db;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import muchbeer.raum.com.data.model.Movie;

@Dao
public interface MovieDaoPaging {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);

    @Query("SELECT * FROM movietbl")
    List<Movie> getMovies();

    @Query("DELETE FROM movietbl")
    abstract void deleteAllMovies();

    @Query("SELECT * FROM movietbl")
    LiveData<List<Movie>> getAllMoviesLive();

    @Query("SELECT * FROM movietbl")
    List<Movie> getAllMovieLocal();

    // object, with position-based loading under the hood.
    @Query("SELECT * FROM movietbl")
    DataSource.Factory<Integer, Movie> getAllMovieOnPaging();
}
