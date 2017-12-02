package com.anh.movie.screen.moviedetail.crew;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.anh.movie.R;
import com.anh.movie.data.model.Crew;
import com.anh.movie.databinding.ItemCrewBinding;
import java.util.List;

/**
 * Created by anh on 12/5/2017.
 */

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.BindingHolder> {
    private List<Crew> mCrews;
    private OnItemClickListener mOnItemClickListener;

    public CrewAdapter(List<Crew> crews, OnItemClickListener onItemClickListener) {
        mCrews = crews;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCrewBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_crew, parent, false);
        return new BindingHolder(binding, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bind(mCrews.get(position));
    }

    @Override
    public int getItemCount() {
        return mCrews != null ? mCrews.size() : 0;
    }

    public void updateAdapter(List<Crew> crews) {
        if (crews == null) {
            return;
        }
        mCrews.addAll(crews);
        notifyDataSetChanged();
    }

    /**
     * OnRecyclerViewItemClickListener
     */
    public interface OnItemClickListener {
        void onItemClick(Crew crew);
    }

    /**
     * class holder
     */
    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemCrewBinding mBinding;
        private OnItemClickListener mOnItemClickListener;

        public BindingHolder(ItemCrewBinding binding, OnItemClickListener onItemClickListener) {
            super(binding.getRoot());
            mBinding = binding;
            mOnItemClickListener = onItemClickListener;
        }

        public void bind(Crew crew) {
            if (crew != null) {
                mBinding.setViewModel(crew);
                mBinding.setListenner(mOnItemClickListener);
                mBinding.executePendingBindings();
            }
        }
    }
}

