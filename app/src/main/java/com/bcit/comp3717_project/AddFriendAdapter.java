package com.bcit.comp3717_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddFriendAdapter extends RecyclerView.Adapter<AddedFriendViewHolder> {

    private List<User> userList;

    public AddFriendAdapter(List<User> users) {
        this.userList = users;
    }

    @NonNull
    @Override
    public AddedFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View addedFriendView = inflater.inflate(R.layout.item_friend, parent, false);

        AddedFriendViewHolder viewHolder = new AddedFriendViewHolder(addedFriendView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddedFriendViewHolder holder, int position) {
        User user = userList.get(position);

        TextView tvAddedFriendName = holder.nameTV;
        TextView tvAddedFriendEmail = holder.emailTV;

        final String addedFiendName = user.getName();
        final String addedFiendEmail = user.getEmail();

        tvAddedFriendName.setText(addedFiendName);
        tvAddedFriendEmail.setText(addedFiendEmail);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
