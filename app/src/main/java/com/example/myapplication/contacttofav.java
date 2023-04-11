package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.CursorWindow;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;

import basedonnées.bdd2;

public class contacttofav extends AppCompatActivity {
    private ArrayList<listcontact> cont = new ArrayList<>();
    private String number;
    private androidx.appcompat.widget.SearchView search;
    private adapter adapte;
    private String namey;
    private String formatted;
    private bdd2 db;
    private ListView list;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacttofav);
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        search = findViewById(R.id.search);

        db = new bdd2(this);

        list = findViewById(R.id.list);
        cont = db.affiche();

        adapte = new adapter(this, R.layout.item_list, cont);
        list.setAdapter(adapte);

        search.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapte.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapte.getFilter().filter(newText);
                return false;
            }
        });

        registerForContextMenu(list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long idc) {
                listcontact click = cont.get(position);
                Intent intent = new Intent(getApplicationContext(), Favories.class);
                db.inser(click.id);
                startActivity(intent);
            }
        });


        TextView cancel = findViewById(R.id.textView20);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}

