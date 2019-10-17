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
import muchbeer.raum.com.livedatamodel.databinding.MovieListItemBinding;
import muchbeer.raum.com.livedatamodel.view.MovieActivity;

public class MovieAdapterPaging  extends PagedListAdapter<Movie, MovieAdapterPaging.MovieViewHolder> {

    private Context mcontext;
    private static String LOG_TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapterPaging(Context context ) {
        super(Movie.DIFF_CALLBACK);
        this.mcontext = context;
        }

    @NonNull
    @Override
    public MovieAdapterPaging.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieListItemBinding movieListItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.movie_list_item
                , parent
                ,false);

        return new MovieAdapterPaging.MovieViewHolder(movieListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterPaging.MovieViewHolder holder, int position) {
        Movie movie=getItem(position);

        Log.d(LOG_TAG, "The movie items are: " + movie);
        holder.movieListItemBinding.setMovie(movie);
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
                        Movie selctedMovie = getItem(position);

                        Intent intent=new Intent(mcontext, MovieActivity.class);
                        intent.putExtra("movie",selctedMovie);
                        mcontext.startActivity(intent);
                    }
                }
            });

        }
    }
}