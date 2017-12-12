package com.anh.movie.screen.moviedetail.review;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.anh.movie.R;
import com.anh.movie.data.model.Crew;
import com.anh.movie.data.model.Review;
import com.anh.movie.databinding.ItemReviewBinding;
import java.util.List;

/**
 * Created by anh on 12/5/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.BindingHolder> {
    private List<Review> mReviews;
    private OnItemClickListener mOnItemClickListener;

    public ReviewAdapter(List<Review> reviews, OnItemClickListener onItemClickListener) {
        mReviews = reviews;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemReviewBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_review, parent, false);
        return new BindingHolder(binding, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bind(mReviews.get(position));
    }

    @Override
    public int getItemCount() {
        return mReviews != null ? mReviews.size() : 0;
    }

    public void updateAdapter(List<Review> reviews) {
        if (reviews == null) {
            return;
        }
        mReviews.addAll(reviews);
        notifyDataSetChanged();
    }

    /**
     * OnRecyclerViewItemClickListener
     */
    public interface OnItemClickListener {
        void onItemClick(Review review);
    }

    /**
     * class holder
     */
    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemReviewBinding mBinding;
        private OnItemClickListener mOnItemClickListener;

        public BindingHolder(ItemReviewBinding binding, OnItemClickListener onItemClickListener) {
            super(binding.getRoot());
            mBinding = binding;
            mOnItemClickListener = onItemClickListener;
        }

        public void bind(Review review) {
            if (review != null) {
                mBinding.setViewModel(review);
                mBinding.setListenner(mOnItemClickListener);
                mBinding.executePendingBindings();
            }
        }
    }
}

