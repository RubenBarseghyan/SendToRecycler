package com.example.ruben.sendtorecycler;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<User> users;

    public UsersAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_user, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder viewHolder, int i) {
        User currrentUser = users.get(viewHolder.getAdapterPosition());
        viewHolder.image.setImageURI(Uri.parse(currrentUser.getImageUri()));

        viewHolder.nameTextView.setText((currrentUser.getName()+ " " +currrentUser.getLastName()));
        viewHolder.dateTextView.setText(currrentUser.getDate().toString());


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView nameTextView;
        public TextView dateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageId);
           nameTextView= itemView.findViewById(R.id.nameTextView);
           dateTextView= itemView.findViewById(R.id.dateTextView);

        }
    }
}
