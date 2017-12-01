package com.anh.movie.screen.home.listmovie;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.anh.movie.R;
import com.anh.movie.data.model.Movie;
import com.anh.movie.databinding.ItemMovieBinding;
import java.util.List;

/**
 * Created by anh on 11/30/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.BindingHolder>{
    private List<Movie> mMovies;
    private OnItemClickListener mOnItemClickListener;

    public MovieAdapter(List<Movie> movies, OnItemClickListener onItemClickListener) {
        mMovies = movies;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemMovieBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_movie, parent, false);
        return new BindingHolder(binding, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bind(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }

    public void updateAdapter(List<Movie> movies) {
        if (movies == null) {
            return;
        }
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    /**
     * OnRecyclerViewItemClickListener
     */
    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    /**
     * class holder
     */
    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemMovieBinding mBinding;
        private OnItemClickListener mOnItemClickListener;

        public BindingHolder(ItemMovieBinding binding,
                OnItemClickListener onItemClickListener) {
            super(binding.getRoot());
            mBinding = binding;
            mOnItemClickListener = onItemClickListener;
        }

        public void bind(Movie movie) {
            if (movie != null) {
                mBinding.setViewModel(movie);
                mBinding.setListenner(mOnItemClickListener);
                mBinding.executePendingBindings();
            }
        }
    }
}
