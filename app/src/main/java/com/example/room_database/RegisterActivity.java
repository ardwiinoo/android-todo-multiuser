package com.example.room_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.room_database.Database.AppDatabase;
import com.example.room_database.Database.Dao.AllDao;
import com.example.room_database.Database.Entity.UserModel;

public class RegisterActivity extends AppCompatActivity {

    EditText nameReg, nimReg, passReg;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameReg = findViewById(R.id.name_reg);
        nimReg = findViewById(R.id.nim_reg);
        passReg = findViewById(R.id.pass_reg);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameUser = nameReg.getText().toString();
                String nimUser = nimReg.getText().toString();
                String passUser = passReg.getText().toString();

                if(nameUser.isEmpty() || nimUser.isEmpty() || passUser.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Semua Bidang Wajib Diisi!", Toast.LENGTH_SHORT).show();
                }
                else {
                    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
                    AllDao allDao = appDatabase.getMyDao();

                    UserModel userModel1 = allDao.checkNim(nimUser);

                    if(userModel1 == null) {
                        // buat object user baru
                        UserModel userModel = new UserModel();

                        userModel.setName(nameUser);
                        userModel.setNim(nimUser);
                        userModel.setPassword(passUser);
                        userModel.setIs_admin(false);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                allDao.insertUser(userModel);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Register Berhasil!", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                            }
                        }).start();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Nim Sudah Digunakan!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}