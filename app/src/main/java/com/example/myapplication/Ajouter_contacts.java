package com.example.myapplication;

import android.database.CursorWindow;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

import basedonnées.bdd2;
import kotlin.jvm.internal.Intrinsics;

public class Ajouter_contacts extends AppCompatActivity {

    String tel;

    @SuppressLint("Safeparcel")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_contacts);

        bdd2 db;
        TextView btn = findViewById(R.id.textView8);
        EditText nom = findViewById(R.id.editTextTextPersonName);
        EditText y = findViewById(R.id.editTextTextPersonName2);
        EditText phone = findViewById(R.id.editTextPhone2);
        EditText email = findViewById(R.id.editTextTextEmailAddress);
        EditText x = findViewById(R.id.editTextTextPersonName4);
        TextView image = findViewById(R.id.textView9);
        ImageView image2 = findViewById(R.id.imageView);
        TextView cancel = findViewById(R.id.textView6);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itn = new Intent(Ajouter_contacts.this, Contact.class);
                startActivity(itn);
            }
        });

        db = new bdd2(this);

        CountryCodePicker cpp = findViewById(R.id.countyCodePicker);
        cpp.registerCarrierNumberEditText(phone);
        final Bitmap[] map = {null};

        ActivityResultLauncher<String> img = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<android.net.Uri>() {
                    @Override
                    public void onActivityResult(android.net.Uri data) {
                        try {
                            java.io.InputStream input = getContentResolver().openInputStream(data);
                            map[0] = BitmapFactory.decodeStream(input);
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
            public void onClick(View v) {
                img.launch("image/*");
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nom.getText().toString();
                String last = y.getText().toString();
                tel = phone.getText().toString();
                String mail = email.getText().toString();
                String are = x.getText().toString();

                byte[] imgablob = compress(getBytes(map[0]));

                if (name.isEmpty() || tel.isEmpty()) {
                    Toast.makeText(Ajouter_contacts.this, "Reiseigner tous les infos", Toast.LENGTH_LONG).show();
                } else {
                    db.insert(name, last, "+ " + cpp.getFullNumber(), mail, are, imgablob,tel);
                    Toast.makeText(Ajouter_contacts.this, "not ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Ajouter_contacts.this, Contact.class);
                    startActivity(intent);
                }
            }
        });
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

    public byte[] compress(byte[] bit) {
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




