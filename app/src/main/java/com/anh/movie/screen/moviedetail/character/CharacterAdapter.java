package com.anh.movie.screen.moviedetail.character;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.anh.movie.R;
import com.anh.movie.data.model.Actor;
import com.anh.movie.data.model.Character;
import com.anh.movie.databinding.ItemCharacterBinding;
import java.util.List;

/**
 * Created by anh on 12/5/2017.
 */

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.BindingHolder> {
    private List<Character> mCharacters;
    private OnItemClickListener mOnItemClickListener;

    public CharacterAdapter(List<Character> characters, OnItemClickListener onItemClickListener) {
        mCharacters = characters;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCharacterBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_character, parent, false);
        return new BindingHolder(binding, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bind(mCharacters.get(position));
    }

    @Override
    public int getItemCount() {
        return mCharacters != null ? mCharacters.size() : 0;
    }

    public void updateAdapter(List<Character> characters) {
        if (characters == null) {
            return;
        }
        mCharacters.addAll(characters);
        notifyDataSetChanged();
    }

    /**
     * OnRecyclerViewItemClickListener
     */
    public interface OnItemClickListener {
        void onItemClick(Actor actor);
    }

    /**
     * class holder
     */
    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemCharacterBinding mBinding;
        private OnItemClickListener mOnItemClickListener;

        public BindingHolder(ItemCharacterBinding binding,
                OnItemClickListener onItemClickListener) {
            super(binding.getRoot());
            mBinding = binding;
            mOnItemClickListener = onItemClickListener;
        }

        public void bind(Character character) {
            if (character != null) {
                mBinding.setViewModel(character);
                mBinding.setListenner(mOnItemClickListener);
                mBinding.executePendingBindings();
            }
        }
    }
}

