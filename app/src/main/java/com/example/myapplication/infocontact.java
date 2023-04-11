package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import basedonnées.bdd2;

public class infocontact extends AppCompatActivity {

    private String formatted;
    private TextView text;
    private TextView text1;
    private int newInt;
    private String tele;
    private String name;
    private String prenom;
    private TextView share;
    private TextView bluemess;
    private ImageView blueem;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_infocontact);
        bdd2 db;
        TextView ctn = findViewById(R.id.textView6);
        share = findViewById(R.id.textView13);
        blueem = findViewById(R.id.imageView13);
        bluemess = findViewById(R.id.textView16);
        FrameLayout sendem = findViewById(R.id.sendemail);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) FrameLayout videocall = findViewById(R.id.videocall);

        videocall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendem.setClickable(false);
                sendem.setFocusable(false);

            }
        });
        share.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, name + " " + prenom);
            intent.putExtra(Intent.EXTRA_TEXT, tele);
            startActivity(Intent.createChooser(intent, "share the Contact"));
        });
        ctn.setOnClickListener(view -> {
            Intent ite = new Intent(infocontact.this, Contact.class);
            startActivity(ite);
        });
        chek();
        FrameLayout mesage = findViewById(R.id.messe);
        mesage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + tele));
            intent.putExtra("sms_body", " ");
            startActivity(intent);
        });
        text1 = findViewById(R.id.textView14);
        text = findViewById(R.id.textView2);
        TextView emaix = findViewById(R.id.textView9);
        TextView ader = findViewById(R.id.textView4);
        ImageView image = findViewById(R.id.imageView70);
        TextView supprimer = findViewById(R.id.textView15);
        FrameLayout call = findViewById(R.id.call);
        TextView modifier = findViewById(R.id.textView7);
        Intent newid = getIntent();
        newInt = newid.getIntExtra("idc", 1);
        name = newid.getStringExtra("nom").toString();
        prenom = newid.getStringExtra("prenom").toString();
        tele = newid.getStringExtra("tele").toString();
        String email = newid.getStringExtra("email");
        String adresse = newid.getStringExtra("adresse");
        byte[] img = newid.getByteArrayExtra("image");
        String vnum = newid.getStringExtra("vnum");
        db = new bdd2(this);
        db.aff(newInt);
        text1.setText(name + "  " + prenom);
        text.setText(tele);
        emaix.setText(email);
        ader.setText(adresse);
        Bitmap bitmap = getBitmap(img);
        image.setClipToOutline(true);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (img == null) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.baseline_person_24w);
            image.setImageBitmap(bitmap);
        } else {
            image.setImageBitmap(bitmap);
        }
        if (img.equals("null")) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.baseline_person_24w);
            image.setImageBitmap(bitmap);
        } else {
            image.setImageBitmap(bitmap);
        }

        if (!TextUtils.isEmpty(email)) {
            bluemess.setTextColor(getResources().getColor(R.color.blue));
            blueem.setImageResource(R.drawable.bluemss);
            sendem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent inte = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto: " + email));
                    startActivity(Intent.createChooser(inte, "Send a email to"));
                }
            });
        } else {
            sendem.setClickable(false);
            sendem.setFocusable(false);
        }

        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder((Context)infocontact.this);
                builder.setMessage("vous etes sùr pour supprimer ce contact");
                builder.setTitle("Confirmation");
                builder.setPositiveButton("Oui", (dialogInterface, i) -> {
                    db.supprimer(newInt);
                    Intent intent = new Intent(getApplicationContext(), Contact.class);
                    startActivity(intent);
                });
                builder.setNegativeButton("Non", (dialogInterface, i) -> dialogInterface.dismiss());
                builder.setNeutralButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                AlertDialog build = builder.create();
                build.show();
            }
        });

        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), modif.class);
                intent.putExtra("id", newInt);
                intent.putExtra("nom", name.toString());
                intent.putExtra("prenom", prenom);
                intent.putExtra("tele", tele);
                intent.putExtra("email", email);
                intent.putExtra("adrsse", adresse);
                intent.putExtra("image", img);
                intent.putExtra("vnum",vnum);
                intent.putExtra("ismodif", false);
                startActivity(intent);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formatted = currentDateTime.format(formatter);
                db.inserer(formatted, newInt,tele,name,prenom);
                mcall();
            }
        });

        TextView ajouter_fav = (TextView) findViewById(R.id.textView12);
        ajouter_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.inser(newInt);
                Intent intent = new Intent(getApplicationContext(), Favories.class);
                startActivity(intent);
            }
        });
    }

        private Bitmap getBitmap ( byte[] byteArray){
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            return bitmap;
        }

        public void mcall () {
            String numer = text.getText().toString();

            if (numer.trim().length() > 5) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numer));
                    intent.putExtra("date", formatted);
                    intent.putExtra("nom", text1.getText().toString());
                    intent.putExtra("num", text.getText().toString());
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, "reseigner un numero > 5", Toast.LENGTH_LONG).show();
            }
        }
        @SuppressLint("MissingSuperCall")
        @Override
        public void onRequestPermissionsResult ( int requestCode, String[] permissions,
        int[] grantResults){
            if (requestCode == 1) {
                if (grantResults.length > 0) {
                    mcall();
                } else {
                    Toast.makeText(this, "non", Toast.LENGTH_LONG).show();
                }
            }
        }

        private void chek () {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS}, 1001);
            }
        }

}