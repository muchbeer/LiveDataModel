package muchbeer.raum.com.data;


import android.content.Context;

import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import moviebam.util.MovieEntityMatcher;
import muchbeer.raum.com.data.db.MovieDao;
import muchbeer.raum.com.data.db.RoomDb;
import muchbeer.raum.com.data.model.Movie;

import static moviebam.util.MovieEntityGenerator.createRandomEntity;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;


@RunWith(AndroidJUnit4.class)
public class MovieDaoTest {

    private static final String TAG = MovieDaoTest.class.getSimpleName();
    private final int NUM_OF_INSERT_MOVIE = 100;

    private RoomDb roomDb;
    private MovieDao movieDao;

    @Mock
    private Observer<List<Movie>> observer;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        Context context = InstrumentationRegistry.getTargetContext();
        roomDb = Room.inMemoryDatabaseBuilder(context, RoomDb.class)
                .allowMainThreadQueries().build();
        movieDao = roomDb.movieDao();
    }

    @After
    public void clean() throws Exception {
        roomDb.close();
    }

    private List<Movie> createRandomMovies() {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < NUM_OF_INSERT_MOVIE; i++)
            movies.add(createRandomEntity());
        return movies;
    }

    @Test
    public void insertCoinsEmpty() throws InterruptedException {
        List empty=new ArrayList();
        CountDownLatch latch = new CountDownLatch(1);
        movieDao.getAllMoviesLive().observeForever(observer);
        movieDao.insertMovies(new ArrayList<Movie>());
        latch.await(50, TimeUnit.MILLISECONDS);
        verify(observer).onChanged(empty);

    }

    @Test
    public void insertCoins() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        List<Movie> movies=createRandomMovies();
        movieDao.getAllMoviesLive().observeForever(observer);
        movieDao.insertMovies(movies);
        latch.await(1, TimeUnit.SECONDS);
        verify(observer,atLeastOnce()).onChanged((List<Movie>) argThat((Matcher<Object>) new MovieEntityMatcher(movies)));



    }
}
