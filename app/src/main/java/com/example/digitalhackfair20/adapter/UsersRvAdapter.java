package com.example.digitalhackfair20.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalhackfair20.R;
import com.example.digitalhackfair20.model.user;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersRvAdapter extends RecyclerView.Adapter<UsersRvAdapter.MyViewHolder> implements Filterable {
    List<user> user_ls;
    private List<user> user_ls_copy;
    Context c;

    public UsersRvAdapter(List<user> ls, Context c) {
        this.c = c;
        this.user_ls = ls;
        this.user_ls_copy = new ArrayList<>(ls);//copy of our main list
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.user_row, parent, false);
        return new MyViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        if (user_ls.get(position).getEmail().matches("admin@gmail.com") == false) {
            holder.name.setText(user_ls.get(position).getName());
            holder.email.setText(user_ls.get(position).getEmail());
            if (user_ls.get(position).getUser_profile().matches("") == false) {
                Picasso.get().load(user_ls.get(position).getUser_profile()).into(holder.user_pic);
            }
        }
        
    }

    @Override
    public int getItemCount() {
        return user_ls.size();
    }

    public void addlist() {
        user_ls_copy = new ArrayList<user>(user_ls);
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<user> filteredList = new ArrayList<>();

            System.out.println("Hjhjfhjsdhjsfdh" + constraint);

            if (constraint.equals(null) || constraint.length() == 0) {
                filteredList.addAll(user_ls_copy);
                System.out.println("Working_______________");
                System.out.println(user_ls_copy);
            } else {
                System.out.println("Else Block");
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (user item : user_ls_copy) {
                    System.out.println(filterPattern);
                    System.out.println("");
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            user_ls.clear();
            System.out.println("Result");
            user_ls.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, email;
        LinearLayout l1;
        CircleImageView user_pic;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            user_pic = itemView.findViewById(R.id.user_pic);
            l1 = itemView.findViewById(R.id.l1);
        }
    }

}

