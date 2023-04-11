package com.example.myapplication;

import android.content.pm.PackageManager;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import basedonnées.bdd2;

public class Contact extends AppCompatActivity {
    private bdd2 db;
    private ListView list;
    private ArrayList<listcontact> cont;
    private adapter adapte;
    private SearchView search;


String number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); // the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }
        BottomNavigationView item = findViewById(R.id.nav);
        search = findViewById(R.id.search);
        item.setSelectedItemId(R.id.contacts);
        item.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.keypad:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.recents:
                        Intent intent1 = new Intent(getApplicationContext(), Recents.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.favories:
                        Intent intent2 = new Intent(getApplicationContext(), Favories.class);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        ImageView ba = findViewById(R.id.btn);
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Ajouter_contacts.class);
                startActivity(intent);
            }
        });
        db = new bdd2(this);
        list = findViewById(R.id.list);
        cont = db.affiche();
        adapte = new adapter(this, R.layout.item_list, cont);
        list.setAdapter(adapte);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                Intent intent = new Intent(getApplicationContext(), infocontact.class);
                intent.putExtra("idc", click.id);
                intent.putExtra("nom", click.nom);
                intent.putExtra("prenom", click.prenom);
                intent.putExtra("tele", click.tele);
                intent.putExtra("email", click.email);
                intent.putExtra("adresse", click.adrss);
                intent.putExtra("image", click.image);
                intent.putExtra("vnum",click.numc);
                startActivity(intent);
            }
        });
    }

 /*   private void findByname(String query) {
        db = new bdd2(this);

        ArrayList<listcontact> f = db.afficherby(query);
        if (f != null) {
            adapte = new adapter(this, R.layout.item_list, f);
            list.setAdapter(adapte);
        }
    }*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.mnadap, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;
          number = cont.get(pos).tele;
       String  namey = cont.get(pos).nom;
        String prenom = cont.get(pos).prenom;
        int id = cont.get(pos).id;
        db = new bdd2(this);

        switch (item.getItemId()) {
            case R.id.appel:
                Intent x = getIntent();
                int new_id = x.getIntExtra("id", 0);
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                 String formatted = currentDateTime.format(formatter);

                db.inserer(formatted, id, number,namey,prenom);

                mcall(number);
                break;
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("vous etes sùr pour supprimer ce contact");
                builder.setTitle("Confirmation");
                builder.setPositiveButton("Oui", (dialogInterface, i) -> {
                    db.supprimer(id);
                    Intent intent = new Intent(this, Contact.class);
                    startActivity(intent);
                });
                builder.setNegativeButton("Non", (dialogInterface, i) -> dialogInterface.dismiss());
                builder.setNeutralButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                AlertDialog build = builder.create();
                build.show();
                break;
            case R.id.update:
                Intent intent = new Intent(this, modif.class);
                intent.putExtra("id", cont.get(pos).id);
                intent.putExtra("nom", cont.get(pos).nom);
                intent.putExtra("prenom", cont.get(pos).prenom);
                intent.putExtra("tele", cont.get(pos).tele);
                intent.putExtra("email", cont.get(pos).email);
                intent.putExtra("adrsse", cont.get(pos).adrss);
                intent.putExtra("image", cont.get(pos).image);
                intent.putExtra("vnum",cont.get(pos).numc);
                intent.putExtra("ismodif", false);
                startActivity(intent);
                break;
            case R.id.message:
                Intent msgIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + number));
                msgIntent.putExtra("sms_body", " ");
                startActivity(msgIntent);
                break;
            case R.id.share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, namey + " " + prenom);
                shareIntent.putExtra(Intent.EXTRA_TEXT, number);
                startActivity(Intent.createChooser(shareIntent, "share the Contact"));
                break;
        }

        return super.onContextItemSelected(item);
    }




    private void mcall(String number) {
        String numer = number;

        if (numer.trim().length() > 5) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numer));
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "reseigner un numero > 5", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0) {
                mcall(number);
            } else {
                Toast.makeText(this, "non", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}


