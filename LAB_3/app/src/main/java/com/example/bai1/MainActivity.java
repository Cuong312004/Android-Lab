package com.example.bai1;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
        private DbAdapter dbAdapter;
        private Cursor cursor;
        private List<String> users;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            dbAdapter = new DbAdapter(this);
            dbAdapter.open();
            dbAdapter.deleteAllUsers();
            for (int i = 0; i < 10; i++) {
                if(i%2==0)
                    dbAdapter.createUser("Lưu Quốc Cường " + i);
                else
                    dbAdapter.createUser("Hoàng " + i);
            }
            users = getData();
            showData();
        }
        private List<String> getData() {
            List<String> users = new ArrayList<>();
            cursor = dbAdapter.getAllUsers();
            while (cursor.moveToNext()) {
                users.add(cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_NAME)));
            }
            return users;
        }
        private void showData() {
            ListView lvUser = (ListView) findViewById(R.id.lv_user);
            ArrayAdapter<String> userAdapter = new
                    ArrayAdapter<String>(MainActivity.this, R.layout.item_user, users);
            lvUser.setAdapter(userAdapter);
        }
    }