package com.example.room_database.Database.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room_database.Database.Entity.TaskModel;
import com.example.room_database.R;

import java.util.List;

public class TaskModelAdapter extends RecyclerView.Adapter<TaskModelAdapter.viewAdapter> {

    private List<TaskModel> list;
    private Context context;
    private Dialog dialog;

    public interface Dialog {
        void onClick(int position);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public TaskModelAdapter(Context context, List<TaskModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_task, parent, false);
        return new viewAdapter(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewAdapter holder, int position) {
        holder.name.setText(list.get(position).getTaskName());
        holder.id.setText("id task: " + Integer.toString(list.get(position).getIdTask()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

   class viewAdapter extends RecyclerView.ViewHolder {
        TextView name, id;

       public viewAdapter(@NonNull View itemView) {
           super(itemView);
           name = itemView.findViewById(R.id.nameTask);
           id = itemView.findViewById(R.id.idTask);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dialog != null) {
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
       }
   }
}