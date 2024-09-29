package com.example.habittracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitHolder> {
    private List<Habit> habits = new ArrayList<>();
    private HabitViewModel habitViewModel; // Reference to ViewModel

    public HabitAdapter(HabitViewModel habitViewModel) {
        this.habitViewModel = habitViewModel;
    }

    @NonNull
    @Override
    public HabitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_item, parent, false);
        return new HabitHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitHolder holder, int position) {
        Habit currentHabit = habits.get(position);
        holder.textViewName.setText(currentHabit.getName());
        holder.checkBox.setChecked(currentHabit.isCompleted());

        // Delete button click listener
        holder.deleteButton.setOnClickListener(v -> {
            removeHabit(position);
        });
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public void setHabits(List<Habit> habits) {
        this.habits = habits;
        notifyDataSetChanged();
    }

    public void removeHabit(int position) {
        if (position >= 0 && position < habits.size()) {
            Habit habitToDelete = habits.get(position);
            habits.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, habits.size());
            // Also delete from the database
            habitViewModel.delete(habitToDelete);
        }
    }

    class HabitHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private CheckBox checkBox;
        private Button deleteButton; // Declare delete button

        public HabitHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            checkBox = itemView.findViewById(R.id.checkbox_completed);
            deleteButton = itemView.findViewById(R.id.button_delete); // Initialize delete button
        }
    }
}
