package com.bcit.comp3717_project;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsViewHolder> {
    private List<Friends> friendsList;

    public FriendsAdapter(List<Friends> friends) {
        this.friendsList = friends;
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View friendView = inflater.inflate(R.layout.item_add_friends, parent, false);

        FriendsViewHolder friendsViewHolder = new FriendsViewHolder(friendView);
        return friendsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        TextView tvName = holder.nameTV;
        TextView tvEmail = holder.emailTV;
        TextView tvId = holder.idTV;

        Button addFriend = holder.addBtn;

        Friends friend = friendsList.get(position);

        final String fiendName = friend.getName();
        final String fiendEmail = friend.getEmail();
        final String fiendId = friend.getId();

        tvName.setText(fiendName);
        tvEmail.setText(fiendEmail);
        tvId.setText(fiendId);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myTag", fiendName+"Clicked");
                Intent intent = new Intent(v.getContext(), UserFriendsActivity.class);

                intent.putExtra("friendName", fiendName);
                intent.putExtra("fiendEmail", fiendEmail);

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }
}
