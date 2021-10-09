package com.example.digitalhackfair20.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalhackfair20.R;
import com.example.digitalhackfair20.model.CalendarEvent;

import java.text.SimpleDateFormat;
import java.util.List;

public class CalendarEventRvAdapter extends RecyclerView.Adapter<CalendarEventRvAdapter.MyViewHolder> {
    List<CalendarEvent> ls;
    Context c;

    public CalendarEventRvAdapter(List<CalendarEvent> ls, Context c) {
        this.c = c;
        this.ls = ls;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.event_row, parent, false);
        return new MyViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(ls.get(position).getName());
        holder.description.setText(ls.get(position).getDescription());

        SimpleDateFormat timeFromDate = new SimpleDateFormat("HH:mm a");

        holder.time.setText(timeFromDate.format(ls.get(position).getTimeAndDate()));

        holder.ce = ls.get(position);

        holder.barColor.setBackgroundColor(ls.get(position).getColorCode());

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, description, time;
        ImageView barColor;
        LinearLayout ll;
        CalendarEvent ce;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.event_name);
            description = itemView.findViewById(R.id.event_description);
            time = itemView.findViewById(R.id.event_time);
            barColor = itemView.findViewById(R.id.bar_color);
            ll = itemView.findViewById(R.id.ll);
        }
    }
}
