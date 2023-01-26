package com.example.room_database.Database.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room_database.Database.Entity.UserModel;
import com.example.room_database.R;

import java.util.List;

public class UserModelAdapter extends RecyclerView.Adapter<UserModelAdapter.viewAdapter> {
    private List<UserModel> list;
    private Context context;
    private Dialog dialog;

    public interface Dialog {
        void onClick(int position);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public UserModelAdapter(Context context, List<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
        return new viewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewAdapter holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.nim.setText(list.get(position).getNim());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewAdapter extends RecyclerView.ViewHolder {
        TextView name, nim;

        public viewAdapter(@NonNull View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.name);
            nim = itemView.findViewById(R.id.nim);

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
