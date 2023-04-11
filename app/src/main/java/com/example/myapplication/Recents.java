package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.CursorWindow;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import basedonnées.bdd2;

public class Recents extends AppCompatActivity {

    private ArrayList<appel> aff;
    private bdd2 db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recents);

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        ListView listc = findViewById(R.id.list2);

        BottomNavigationView item = findViewById(R.id.nav);
        item.setSelectedItemId(R.id.recents);
        item.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.keypad:
                        Intent intent1 = new Intent(Recents.this, MainActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.contacts:
                        Intent intent2 = new Intent(Recents.this, Contact.class);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.favories:
                        Intent intent3 = new Intent(Recents.this, Favories.class);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        return true;
                    default:
                        return false;
                }
            }
        });

        db = new bdd2(this);
        aff = db.affi();

        adapter_call adapter = new adapter_call(getApplicationContext(), R.layout.recently, aff);
        listc.setAdapter(adapter);

        registerForContextMenu(listc);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.recent, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo x = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = x.position;
        int id = aff.get(pos).getId();
        switch (item.getItemId()) {
            case R.id.sup:
                db.delete(id);
                Intent intent = new Intent(Recents.this, Recents.class);
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

