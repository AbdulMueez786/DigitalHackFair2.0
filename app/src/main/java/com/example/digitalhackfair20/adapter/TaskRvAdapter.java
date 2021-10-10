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
import com.example.digitalhackfair20.model.task;

import java.util.ArrayList;
import java.util.List;

public class TaskRvAdapter extends RecyclerView.Adapter<TaskRvAdapter.MyViewHolder> implements Filterable {
    List<task> task_ls;
    private List<task> task_ls_copy;
    Context c;

    public TaskRvAdapter(List<task> ls, Context c) {
        this.c = c;
        this.task_ls = ls;
        this.task_ls_copy = new ArrayList<>(ls);//copy of our main list
    }

    @NonNull
    @Override
    public TaskRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.task_row, parent, false);
        return new TaskRvAdapter.MyViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskRvAdapter.MyViewHolder holder, final int position) {

        holder.task_name.setText(task_ls.get(position).getTitle());
        holder.task_description.setText(task_ls.get(position).getDescription());
        holder.task_date.setText(task_ls.get(position).getDue_date());
        holder.task_time.setText("10/12/2021");

    }

    @Override
    public int getItemCount() {
        return task_ls.size();
    }

    public void addlist() {
        task_ls_copy = new ArrayList<task>(task_ls);
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
                filteredList.addAll(task_ls_copy);
                System.out.println("Working_______________");
                System.out.println(task_ls_copy);
            } else {
                System.out.println("Else Block");
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (task item : task_ls_copy) {
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
            task_ls.clear();
            System.out.println("Result");
            task_ls.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView task_name, task_description, task_date, task_time;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            task_name = itemView.findViewById(R.id.task_name);
            task_description = itemView.findViewById(R.id.task_description);
            task_date = itemView.findViewById(R.id.task_date);
            task_time = itemView.findViewById(R.id.task_time);
        }
    }

}