package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;

import basedonnées.bdd2;
import kotlin.jvm.internal.Intrinsics;

import com.hbb20.CountryCodePicker;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Objects;

public class modif extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif);
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        bdd2 db;

        TextView btn = findViewById(R.id.textView8);
        EditText nom = findViewById(R.id.editTextTextPersonName);
        EditText y = findViewById(R.id.editTextTextPersonName2);
        EditText phone = findViewById(R.id.editTextPhone2);
        EditText email = findViewById(R.id.editTextTextEmailAddress);
        EditText x = findViewById(R.id.editTextTextPersonName4);
        TextView image = findViewById(R.id.textView9);
        ImageView image2 = findViewById(R.id.imageView);
        CountryCodePicker cpp = findViewById(R.id.countyCodePicker);
        TextView ctn = findViewById(R.id.textView6);

        ctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ite = new Intent(modif.this, Contact.class);
                startActivity(ite);
            }
        });

        cpp.registerCarrierNumberEditText(phone);
        final Bitmap[] map = {null};
        db = new bdd2(this);

        ActivityResultLauncher<String> img = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(android.net.Uri data) {
                        try {
                            java.io.InputStream input = getContentResolver().openInputStream(data);

                            Objects.requireNonNull(map)[0] = BitmapFactory.decodeStream(input);
                            image2.setImageBitmap(map[0]);
                            image2.setClipToOutline(true);
                            image2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img.launch("image/*");
            }
        });

        Intent newi = getIntent();

        boolean isedit = newi.getBooleanExtra("ismodif", false);


        String namex = newi.getStringExtra("nom");
        String prenom = newi.getStringExtra("prenom");
        String emailx = newi.getStringExtra("email");
        String adrs = newi.getStringExtra("adrsse");
        String tele = newi.getStringExtra("tele");
        byte[] imagey = newi.getByteArrayExtra("image");
        String vnum =newi.getStringExtra("vnum");


        nom.setText(namex);
        y.setText(prenom);
        phone.setText(vnum);
        email.setText(emailx);
        x.setText(adrs);
        Bitmap bitmap = get(imagey);

        Intrinsics.checkNotNullExpressionValue(image2, "image2");
            image2.setImageBitmap(bitmap);
            image2.setClipToOutline(true);
            image2.setScaleType(ImageView.ScaleType.CENTER_CROP);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nom.getText().toString();
                String last = y.getText().toString();
                String tel = phone.getText().toString();
                String mail = email.getText().toString();
                String are = x.getText().toString();

                byte[] imgablob = compress(getBytes((map)[0]));
                if (imgablob.length == 0 ) {

                    Intrinsics.checkNotNull(bitmap);
                    Bitmap bitmap = get(imagey);
                    image2.setImageBitmap(bitmap);
                    imgablob = imagey;
                }
                int id = getIntent().getIntExtra("id", 0);


                if (name.isEmpty() || tel.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Reiseigner tous les infos", Toast.LENGTH_LONG).show();

                } else {

                    db.modifier(id, name, last, "+  " + cpp.getFullNumber(), mail, are, imgablob,tel);
                    Toast.makeText(getApplicationContext(), "is modif", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Contact.class);
                    startActivity(intent);
                }
            }
        });
    }


                private Bitmap get ( byte[] img){
                    Intrinsics.checkNotNull(img);
                    return BitmapFactory.decodeByteArray(img, 0, img.length);
                }

    private final byte[] getBytes(Bitmap map) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (map != null) {
            map.compress(Bitmap.CompressFormat.PNG, 0, (OutputStream)stream);
        }

        byte[] var10000 = stream.toByteArray();
        Intrinsics.checkNotNullExpressionValue(var10000, "stream.toByteArray()");
        return var10000;
    }

                public byte[] compress ( byte[] bit){
                    byte[] compress = bit;
                    while (compress.length > 500000) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(compress, 0, compress.length);
                        Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.8), (int) (bitmap.getHeight() * 0.8), true);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        compress = stream.toByteArray();
                    }
                    return compress;
                }
            }



