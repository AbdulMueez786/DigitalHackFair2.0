package com.example.digitalhackfair20.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalhackfair20.R;
import com.example.digitalhackfair20.model.report;
import com.example.digitalhackfair20.model.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReportRvAdapter extends RecyclerView.Adapter<ReportRvAdapter.MyViewHolder> implements Filterable {
    List<report> report_ls;
    private List<report> report_ls_copy;
    Context c;

    public ReportRvAdapter(List<report> ls, Context c) {
        this.c = c;
        this.report_ls = ls;
        this.report_ls_copy = new ArrayList<>(ls);//copy of our main list
    }

    @NonNull
    @Override
    public ReportRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.report_row, parent, false);
        return new ReportRvAdapter.MyViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull final ReportRvAdapter.MyViewHolder holder, final int position) {

        FirebaseDatabase.getInstance().
                getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot1) {
                for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                    user u = snapshot1.getValue(user.class);
                    if (u.getId().matches(report_ls.get(position).getCriminal_id())) {
                        holder.username.setText(u.getName());
                    }
                    if (u.getId().matches(report_ls.get(position).getVictim_id())) {
                        holder.reported_by.setText(u.getName());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        Picasso.get().load(report_ls.get(position).getProfile())
                .into(holder.profile);
        holder.report_message.setText(report_ls.get(position).getDetail());


    }

    @Override
    public int getItemCount() {
        return report_ls.size();
    }

    public void addlist() {
        report_ls_copy = new ArrayList<report>(report_ls);
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<report> filteredList = new ArrayList<>();

            System.out.println("Hjhjfhjsdhjsfdh" + constraint);

            if (constraint.equals(null) || constraint.length() == 0) {
                filteredList.addAll(report_ls_copy);
                System.out.println("Working_______________");
                System.out.println(report_ls_copy);
            } else {
                System.out.println("Else Block");
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (report item : report_ls_copy) {
                    System.out.println(filterPattern);
                    System.out.println("");
                    if (item.getDetail().toLowerCase().contains(filterPattern)) {
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
            report_ls.clear();
            System.out.println("Result");
            report_ls.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profile;
        private TextView username, report_message, reported_by;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile);
            username = itemView.findViewById(R.id.username);
            report_message = itemView.findViewById(R.id.report_message);
            reported_by = itemView.findViewById(R.id.reported_by);
        }
    }
}


