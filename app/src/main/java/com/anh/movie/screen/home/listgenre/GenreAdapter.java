package com.anh.movie.screen.home.listgenre;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.anh.movie.R;
import com.anh.movie.data.model.Genre;
import com.anh.movie.databinding.ItemGenreBinding;
import java.util.List;

/**
 * Created by anh on 12/3/2017.
 */

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.BindingHolder> {
    private List<Genre> mGenres;
    private OnItemClickListener mOnItemClickListener;

    public GenreAdapter(List<Genre> genres, OnItemClickListener onItemClickListener) {
        mGenres = genres;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemGenreBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_genre, parent, false);
        return new BindingHolder(binding, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bind(mGenres.get(position));
    }

    @Override
    public int getItemCount() {
        return mGenres != null ? mGenres.size() : 0;
    }

    public void updateAdapter(List<Genre> genres) {
        if (genres == null) {
            return;
        }
        mGenres.addAll(genres);
        notifyDataSetChanged();
    }

    /**
     * OnRecyclerViewItemClickListener
     */
    public interface OnItemClickListener {
        void onItemClick(Genre genre);
    }

    /**
     * class holder
     */
    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemGenreBinding mBinding;
        private OnItemClickListener mOnItemClickListener;

        public BindingHolder(ItemGenreBinding binding, OnItemClickListener onItemClickListener) {
            super(binding.getRoot());
            mBinding = binding;
            mOnItemClickListener = onItemClickListener;
        }

        public void bind(Genre genre) {
            if (genre != null) {
                mBinding.setViewModel(genre);
                mBinding.setListenner(mOnItemClickListener);
                mBinding.executePendingBindings();
            }
        }
    }
}
