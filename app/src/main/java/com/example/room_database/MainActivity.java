package com.example.room_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.room_database.Database.AppDatabase;
import com.example.room_database.Database.Dao.AllDao;
import com.example.room_database.Database.Entity.UserModel;

public class MainActivity extends AppCompatActivity {

    EditText nimLogin, passLogin;
    Button btnLogin, btnRegister;

    // SharedPreferences
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "auth_pref";
    private static final String KEY_NAME = "name";
    private static final String KEY_NIM = "nim";
    private static final String KEY_ISADMIN = "is_admin";
    private static final String KEY_IDUSER = "idUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nimLogin = findViewById(R.id.nim_login);
        passLogin = findViewById(R.id.pass_login);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_reg);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        // cek sharedPreferences
        String is_admin = sharedPreferences.getString(KEY_ISADMIN, null);
        if(is_admin != null) {
            if(Boolean.parseBoolean(is_admin) == true) {
                Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(getApplicationContext(), UserHomeActivity.class);
                startActivity(intent);
                finish();
            }
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nimUser = nimLogin.getText().toString();
                String pass = passLogin.getText().toString();

                if(nimUser.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Semua Bidang Wajib Diisi!", Toast.LENGTH_LONG).show();
                }
                else {
                    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
                    AllDao allDao = appDatabase.getMyDao();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserModel userModel = allDao.login(nimUser, pass);

                            if(userModel == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid Login :(", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            else {
                                String nameUser = userModel.getName();
                                String idUser = String.valueOf(userModel.getId());
                                String nimUser = userModel.getNim();
                                String is_admin = String.valueOf(userModel.getIs_admin());

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(KEY_NAME, nameUser);
                                editor.putString(KEY_IDUSER, idUser);
                                editor.putString(KEY_NIM, nimUser);
                                editor.putString(KEY_ISADMIN, is_admin);
                                editor.apply();

                                if(Boolean.parseBoolean(is_admin) == true) {
                                    Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Selamat Datang, " + nameUser, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Intent intent = new Intent(getApplicationContext(), UserHomeActivity.class);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Selamat Datang, " + nameUser, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    }).start();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}