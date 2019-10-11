package muchbeer.raum.com.livedatamodel.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import muchbeer.raum.com.data.model.Movie;
import muchbeer.raum.com.livedatamodel.R;
import muchbeer.raum.com.livedatamodel.adapter.MovieAdapter;
import muchbeer.raum.com.livedatamodel.databinding.ActivityMainBinding;

import muchbeer.raum.com.livedatamodel.screen.MainScreen;
import muchbeer.raum.com.livedatamodel.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements MainScreen {


    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private ArrayList<Movie> moviesMain;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MainActivityViewModel mainActivityViewModel;
    private ActivityMainBinding activityMainBinding;
    private final static int DATA_FETCHING_INTERVAL=5*1000; //5 seconds
    private long mLastFetchedDataTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("TMDB Popular Movies Today");

        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        getPopularMovies();

        swipeRefreshLayout = activityMainBinding.swRefresh;
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (System.currentTimeMillis() - mLastFetchedDataTimeStamp < DATA_FETCHING_INTERVAL) {
                    Log.d(LOG_TAG, "\tNot fetching from network because interval didn't reach");
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                } else if(System.currentTimeMillis() == DATA_FETCHING_INTERVAL) {
                    Log.d(LOG_TAG, "\tclose the refresh");
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
                getPopularMovies();
            }
        });
    }

    public void getPopularMovies() {
       // mViewModel.getCoinsMarketData().observe(this, dataObserver);
       // mViewModel.getErrorUpdates().observe(this, errorObserver);
       // mViewModel.fetchData();

        //swipeRefreshLayout.setRefreshing(false);

        mainActivityViewModel.getAllMovieLocalData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesMain = (ArrayList<Movie>) movies;
                showOnRecyclerView();
            }
        });

        mainActivityViewModel.getErrorUpdates().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorObserver) {
                setError(errorObserver);
            }
        });

        mainActivityViewModel.fetchData();
       /* mainActivityViewModel.getAllMoview().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesMain = (ArrayList<Movie>) movies;
                showOnRecyclerView();
            }
        });*/
    }

    private void showErrorToast(String error) {
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_LONG).show();
    }

    private void showOnRecyclerView() {

        recyclerView = activityMainBinding.rvMovies;
        movieAdapter = new MovieAdapter(this, moviesMain);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {


            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));


        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(List<Movie> data) {
        //This is when you don't need Binding adapter
      /*  mLastFetchedDataTimeStamp=System.currentTimeMillis();
        mAdapter.setItems(data);
        mSwipeRefreshLayout.setRefreshing(false);*/
    }

    @Override
    public void setError(String msg) {
        showErrorToast(msg);
    }
}
