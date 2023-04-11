package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import basedonnées.bdd2;
import basedonnées.bdd2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Favories extends AppCompatActivity {
    private ListView list;
    private bdd2 db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favories);

        BottomNavigationView item = findViewById(R.id.nav);
        ImageView ajout = findViewById(R.id.imageView2);
        list = findViewById(R.id.listf);
        item.setSelectedItemId(R.id.favories);

        item.setOnNavigationItemSelectedListener(item1 -> {
            switch (item1.getItemId()) {
                case R.id.recents:
                    Intent intent1 = new Intent(Favories.this, Recents.class);
                    startActivity(intent1);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.contacts:
                    Intent intent2 = new Intent(Favories.this, Contact.class);
                    startActivity(intent2);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.keypad:
                    Intent intent3 = new Intent(Favories.this, MainActivity.class);
                    startActivity(intent3);
                    overridePendingTransition(0, 0);
                    return true;
                default:
                    return false;
            }
        });

        ajout.setOnClickListener(view -> {
            Intent intent = new Intent(Favories.this, contacttofav.class);
            startActivity(intent);
        });

        get();
    }

    private void get() {
        db = new bdd2(this);
        ArrayList<favor> listxy = db.affer();
        adapter_fav adapter = new adapter_fav(this, R.layout.list_fav, listxy);
        list.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        get();
    }
}

