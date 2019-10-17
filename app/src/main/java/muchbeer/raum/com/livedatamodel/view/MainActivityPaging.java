package muchbeer.raum.com.livedatamodel.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import muchbeer.raum.com.data.model.Movie;
import muchbeer.raum.com.livedatamodel.R;
import muchbeer.raum.com.livedatamodel.adapter.MovieAdapter;
import muchbeer.raum.com.livedatamodel.adapter.MovieAdapterPaging;
import muchbeer.raum.com.livedatamodel.databinding.ActivityMainBinding;
import muchbeer.raum.com.livedatamodel.screen.MainScreen;

import muchbeer.raum.com.livedatamodel.viewmodel.MainActivityViewModelPaging;

public class MainActivityPaging   extends AppCompatActivity implements MainScreen {


    private static final String LOG_TAG = MainActivityPaging.class.getSimpleName();
    private PagedList<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapterPaging movieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MainActivityViewModelPaging mainActivityViewModel;
    private ActivityMainBinding activityMainBinding;
    private final static int DATA_FETCHING_INTERVAL=5*1000; //5 seconds
    private long mLastFetchedDataTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("TMDB Popular Movies Today");

        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModelPaging.class);

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

        mainActivityViewModel.getMoviesPagedList().observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> localmovies) {
                movies = localmovies;
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
    }

    private void showErrorToast(String error) {
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_LONG).show();
    }

    private void showOnRecyclerView() {

        recyclerView = activityMainBinding.rvMovies;
        movieAdapter = new MovieAdapterPaging(this);
        movieAdapter.submitList(movies);

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