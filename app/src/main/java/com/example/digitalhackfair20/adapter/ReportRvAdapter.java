package com.example.digitalhackfair20.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalhackfair20.R;
import com.example.digitalhackfair20.model.task;

import java.util.ArrayList;
import java.util.List;

public class ReportRvAdapter extends RecyclerView.Adapter<ReportRvAdapter.MyViewHolder> implements Filterable {
    List<task> report_ls;
    private List<task> report_ls_copy;
    Context c;

    public ReportRvAdapter(List<task> ls, Context c) {
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


        //    holder.name.setText(user_ls.get(position).getName());
        //    holder.email.setText(user_ls.get(position).getEmail());


    }

    @Override
    public int getItemCount() {
        return report_ls.size();
    }

    public void addlist() {
        report_ls_copy = new ArrayList<task>(report_ls);
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<task> filteredList = new ArrayList<>();

            System.out.println("Hjhjfhjsdhjsfdh" + constraint);

            if (constraint.equals(null) || constraint.length() == 0) {
                filteredList.addAll(report_ls_copy);
                System.out.println("Working_______________");
                System.out.println(report_ls_copy);
            } else {
                System.out.println("Else Block");
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (task item : report_ls_copy) {
                    System.out.println(filterPattern);
                    System.out.println("");
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
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
        // TextView name, email;
        // LinearLayout l1;
        // CircleImageView user_pic;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //name = itemView.findViewById(R.id.name);
            //email = itemView.findViewById(R.id.email);
            //user_pic = itemView.findViewById(R.id.user_pic);
            //l1 = itemView.findViewById(R.id.l1);
        }
    }

}


