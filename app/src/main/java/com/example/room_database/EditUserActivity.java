package com.example.room_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.room_database.Database.AppDatabase;
import com.example.room_database.Database.Entity.UserModel;

public class EditUserActivity extends AppCompatActivity {

    EditText nameEdit, nimEdit, passEdit;
    TextView textName;
    Button btnSave;
    Switch switch1;

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        nameEdit = findViewById(R.id.name_edit);
        nimEdit = findViewById(R.id.nim_edit);
        passEdit = findViewById(R.id.pass_edit);
        switch1 = findViewById(R.id.switch1);
        btnSave = findViewById(R.id.btn_save);

        database = AppDatabase.getAppDatabase(getApplicationContext());

        Intent intent = getIntent();
        String nim = intent.getStringExtra("nim");

        UserModel userModel =database.getMyDao().checkNim(nim);
        nameEdit.setText(userModel.getName());
        nimEdit.setText(userModel.getNim());
        passEdit.setText(userModel.getPassword());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameUser = nameEdit.getText().toString();
                String nimUser = nimEdit.getText().toString();
                String password = passEdit.getText().toString();
                Boolean isAdmin = switch1.isChecked();

                if(nameUser.isEmpty() || nimUser.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Semua Bidang Wajib Diisi!", Toast.LENGTH_LONG).show();
                }
                else {
                   database.getMyDao().updateUser(nameUser, nimUser, password, isAdmin);
                   Toast.makeText(getApplicationContext(), "User Berhasil Di Update!", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }
}