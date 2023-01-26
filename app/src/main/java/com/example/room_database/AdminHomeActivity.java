package com.example.room_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.room_database.Database.Adapter.UserModelAdapter;
import com.example.room_database.Database.AppDatabase;
import com.example.room_database.Database.Entity.UserModel;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {

    TextView textNama, textNimStatus;
    Button btnLogout, btnTambah;
    RelativeLayout relativeLayout;

    private RecyclerView recyclerView;
    private UserModelAdapter userModelAdapter;
    private List<UserModel> list = new ArrayList<>();

    private AppDatabase database;

    private AlertDialog.Builder dialog;

    // Handle SharedPreferences
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "auth_pref";
    private static final String KEY_NAME = "name";
    private static final String KEY_NIM = "nim";
    private static final String KEY_ISADMIN = "is_admin";
    private static final String KEY_IDUSER = "idUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        relativeLayout = findViewById(R.id.home);
        textNama = findViewById(R.id.textNama);
        textNimStatus = findViewById(R.id.textNimStatus);
        btnLogout = findViewById(R.id.btn_logout);
        recyclerView = findViewById(R.id.recycler_view);
        btnTambah = findViewById(R.id.btn_tambah);

        // get all data users
         database = AppDatabase.getAppDatabase(getApplicationContext());
         list.clear();
         list.addAll(database.getMyDao().getAllUsers());

        // ambil value sharedpref
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String namaUser = sharedPreferences.getString(KEY_NAME, null);
        String nimUser = sharedPreferences.getString(KEY_NIM, null);
        String is_admin = sharedPreferences.getString(KEY_ISADMIN, null);
        String idUser = sharedPreferences.getString(KEY_IDUSER, null);

        if(namaUser != null && nimUser != null) {
            textNama.setText("Halo, " + namaUser);
            textNimStatus.setText(nimUser + " | Admin");
        }

        userModelAdapter = new UserModelAdapter(getApplicationContext(), list);
        userModelAdapter.setDialog(new UserModelAdapter.Dialog() {
            @Override
            public void onClick(int position) {
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                dialog = new AlertDialog.Builder(AdminHomeActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch(i) {
                            case 0:
                                Intent intent = new Intent(AdminHomeActivity.this, EditUserActivity.class);
                                intent.putExtra("nim", list.get(position).getNim());
                                startActivity(intent);
                                break;
                            case 1:
                                UserModel userModel = list.get(position);
                                database.getMyDao().delete(userModel);
                                Toast.makeText(AdminHomeActivity.this, "Data User Berhasil Dihapus!", Toast.LENGTH_LONG).show();
                                onStart();
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userModelAdapter);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TambahUserActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // hapus key value shared
                editor.clear();
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Toast.makeText(getApplicationContext(), "Logout Berhasil!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
    }

    protected void onStart() {
        super.onStart();

        // agar langsung refresh ketika data baru masuk
        list.clear();
        list.addAll(database.getMyDao().getAllUsers());

        userModelAdapter.notifyDataSetChanged();
    }
}