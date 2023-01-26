package com.example.room_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.room_database.Database.AppDatabase;
import com.example.room_database.Database.Dao.AllDao;
import com.example.room_database.Database.Entity.TaskModel;

public class TambahTaskActivity extends AppCompatActivity {

    EditText namaTask;
    Button btnSave;

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "auth_pref";
    private static final String KEY_IDUSER = "idUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_task);

        namaTask = findViewById(R.id.name_add);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTask = namaTask.getText().toString();

                if (nameTask.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nama Task Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                }
                else {
                    sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                    String idUser = sharedPreferences.getString(KEY_IDUSER, null);

                    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
                    AllDao allDao = appDatabase.getMyDao();

                    TaskModel taskModel = new TaskModel();

                    taskModel.setTaskName(nameTask);
                    taskModel.setUserId(Integer.parseInt(idUser));

                    allDao.insertTask(taskModel);
                    Toast.makeText(getApplicationContext(), "Task Baru Berhasil Ditambahkan!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}