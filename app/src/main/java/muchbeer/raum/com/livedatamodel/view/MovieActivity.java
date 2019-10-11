package muchbeer.raum.com.livedatamodel.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import muchbeer.raum.com.data.model.Movie;
import muchbeer.raum.com.livedatamodel.R;
import muchbeer.raum.com.livedatamodel.databinding.ActivityMovieBinding;


public class MovieActivity extends AppCompatActivity {

    private static final String LOG_TAG = MovieActivity.class.getSimpleName() ;
    private Movie movie;
    private ActivityMovieBinding activityMovieBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activityMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

        Intent intent = getIntent();

        if (intent.hasExtra("movie")) {

            movie = getIntent().getParcelableExtra("movie");
            activityMovieBinding.setMovie(movie);
            Log.i(LOG_TAG, "The reall data not shown are: " + movie);
            getSupportActionBar().setTitle(movie.getTitle());
          //  collapsingToolbarLayout.setTitle(movie.getTitle());

        }

    }
}
