package muchbeer.raum.com.livedatamodel.adapter;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import muchbeer.raum.com.data.model.Movie;
import muchbeer.raum.com.livedatamodel.R;
import muchbeer.raum.com.livedatamodel.databinding.ActivityMovieBinding;
import muchbeer.raum.com.livedatamodel.databinding.MovieListItemBinding;

import muchbeer.raum.com.livedatamodel.view.MovieActivity;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

private Context mcontext;
private ArrayList<Movie> movieArrayList;
private static String LOG_TAG = MovieAdapter.class.getSimpleName();

public MovieAdapter(Context context, ArrayList<Movie> movies ) {
        this.mcontext = context;
        this.movieArrayList = movies;
        }

@NonNull
@Override
public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieListItemBinding movieListItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
        , R.layout.movie_list_item
        , parent
        ,false);

        return new MovieViewHolder(movieListItemBinding);
        }

@Override
public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie=movieArrayList.get(position);

        Log.d(LOG_TAG, "The movie items are: " + movie);
        holder.movieListItemBinding.setMovie(movie);
        }

@Override
public int getItemCount() {
        return movieArrayList.size();
        }

public class MovieViewHolder extends RecyclerView.ViewHolder {

    private MovieListItemBinding movieListItemBinding;
    public MovieViewHolder(@NonNull MovieListItemBinding movieListItemBinding) {
        super(movieListItemBinding.getRoot());
        this.movieListItemBinding = movieListItemBinding;

        movieListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position=getAdapterPosition();
                if(position!=RecyclerView.NO_POSITION) {
                    Movie selctedMovie = movieArrayList.get(position);

                    Intent intent=new Intent(mcontext, MovieActivity.class);
                    intent.putExtra("movie",selctedMovie);
                    mcontext.startActivity(intent);
                }
            }
        });

    }
}
}