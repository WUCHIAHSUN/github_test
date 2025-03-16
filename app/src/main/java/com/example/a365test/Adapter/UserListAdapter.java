package com.example.a365test.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a365test.API.Data.UsersListData;
import com.example.a365test.Interface.AdapterListener;
import com.example.a365test.databinding.CardDesignBinding;

public class UserListAdapter extends ListAdapter<UsersListData.UserList, UserListAdapter.UserViewHolder> {
    private final AdapterListener adapterListener;
    public UserListAdapter(AdapterListener adapterListener) {
        super(new UserDiffCallback());
        this.adapterListener = adapterListener;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private final CardDesignBinding binding;

        public UserViewHolder(CardDesignBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public CardDesignBinding getBinding() {
            return binding;
        }
    }

    // Create ViewHolder
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardDesignBinding binding = CardDesignBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

    // Bind data to ViewHolder
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UsersListData.UserList user = getItem(position); // Get current item
        holder.getBinding().tvLogin.setText(user.getLogin());
        holder.getBinding().tvSiteAdmin.setText(user.isSiteAdmin()? "true" : "false");
        holder.getBinding().setImageUrl(user.getAvatar_url());
        if (position == getItemCount() - 1) {
            holder.getBinding().loading.setVisibility(View.VISIBLE);
        } else {
            holder.getBinding().loading.setVisibility(View.GONE);
        }

        holder.getBinding().mainLayout.setOnClickListener(v -> {
            adapterListener.onPosClicked(position);
        });
    }

    // Get item count
    @Override
    public int getItemCount() {
        return getCurrentList().size(); // Use getCurrentList() to get the current data
    }
}

class UserDiffCallback extends DiffUtil.ItemCallback<UsersListData.UserList> {

    @Override
    public boolean areItemsTheSame(@NonNull UsersListData.UserList oldItem, @NonNull UsersListData.UserList newItem) {
        // Compare items based on the _id
        return oldItem.getId()== newItem.getId();
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull UsersListData.UserList oldItem, @NonNull UsersListData.UserList newItem) {
        // Compare the contents of the items
        return oldItem.equals(newItem);
    }
}
