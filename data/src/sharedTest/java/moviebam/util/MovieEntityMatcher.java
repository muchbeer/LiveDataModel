package moviebam.util;

import org.mockito.ArgumentMatcher;

import java.util.List;

import muchbeer.raum.com.data.model.Movie;

public class MovieEntityMatcher implements ArgumentMatcher<List<Movie>> {
    private List<Movie> movies;

    public MovieEntityMatcher(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean matches(List<Movie> coins) {
        if (coins.size() == 0)
            return true;
        final int size = this.movies.size();
        for (int i = 0; i < size; i++)
            if (!(this.movies.get(i).getId()
                    .compareTo(coins.get(i).getId()) == 0))
                return false;
        return true;
    }

}
