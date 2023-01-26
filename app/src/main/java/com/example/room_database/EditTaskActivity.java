package com.example.room_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.room_database.Database.AppDatabase;
import com.example.room_database.Database.Entity.TaskModel;

public class EditTaskActivity extends AppCompatActivity {

    EditText namaTask;
    Button btnSave;

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        namaTask = findViewById(R.id.name_edit);
        btnSave = findViewById(R.id.btn_save);

        database = AppDatabase.getAppDatabase(getApplicationContext());

        Intent intent = getIntent();
        String idTask = intent.getStringExtra("idTask");

        if (idTask != null) {
            TaskModel taskModel = database.getMyDao().getMyTaskById(Integer.parseInt(idTask));
            namaTask.setText(taskModel.getTaskName());
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTask = namaTask.getText().toString();

                if(nameTask.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nama Task Tidak Boleh Kosong!", Toast.LENGTH_LONG).show();
                }
                else {
                    database.getMyDao().updateTask(nameTask, Integer.parseInt(idTask));
                    Toast.makeText(getApplicationContext(), "Task Berhasil Di Update!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}