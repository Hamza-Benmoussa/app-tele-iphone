package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import basedonnées.bdd2;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private TextView number;
    private int newx;
    private ImageView image;
    private bdd2 db2 ;
    private FrameLayout call;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView item = findViewById(R.id.nav);

        item.setSelectedItemId(R.id.keypad);
        item.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.contacts:
                        Intent intent = new Intent(getApplicationContext(), Contact.class);
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

        number = findViewById(R.id.textView);
        image = findViewById(R.id.back);
        call = findViewById(R.id.frameLayout);
        Button btn1 = findViewById(R.id.button3);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button);
        Button btn4 = findViewById(R.id.button6);
        Button btn5 = findViewById(R.id.button5);
        Button btn6 = findViewById(R.id.button4);
        Button btn7 = findViewById(R.id.button7);
        Button btn8 = findViewById(R.id.button9);
        Button btn9 = findViewById(R.id.button8);
        Button btn0 = findViewById(R.id.button14);
        Button btn_ = findViewById(R.id.button13);
        Button btn_1 = findViewById(R.id.button15);

        db2 = new bdd2(this);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText(number.getText().toString() + "1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText(number.getText().toString() + "2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText(number.getText().toString() + "3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText(number.getText().toString() + "4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText(number.getText().toString() + "5");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText(number.getText().toString() + "6");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText(number.getText().toString() + "7");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText(number.getText().toString() + "8");
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText(number.getText().toString() + "9");
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText(number.getText().toString() + "0");
            }
        });

        btn_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText(number.getText().toString() + "#");
            }
        });

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText(number.getText().toString() + "*");
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number.getText().toString().length() > 0) {
                    StringBuilder stringBuilder = new StringBuilder(number.getText().toString());
                    stringBuilder.deleteCharAt(number.getText().length() - 1);
                    String newNumber = stringBuilder.toString();
                    number.setText(newNumber);
                }
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Intent x = getIntent();

               LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formatted = currentDateTime.format(formatter);

                db2.inserer(formatted,0,number.getText().toString(),"","");

                mcall(number);
            }
        });
    }
        private void mcall (TextView number){
            String numer = number.getText().toString();

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
        public void onRequestPermissionsResult ( int requestCode, String[] permissions,
        int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == 1) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mcall(number);
                } else {
                    Toast.makeText(this, "non", Toast.LENGTH_LONG).show();
                }
            }
        }

}
