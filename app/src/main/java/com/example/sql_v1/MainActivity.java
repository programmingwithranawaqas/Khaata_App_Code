package com.example.sql_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText etTitle, etDesc, etDate, etPrice;
    Button btnAddNewKhata, btnUpdateKhata, btnDeleteKhata, btnViewKhata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        KhaataDB db = new KhaataDB(MainActivity.this);

        btnAddNewKhata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                String price = etPrice.getText().toString().trim();

                db.open();
                db.insertKhata(title, desc, date, price);
                db.close();

                etTitle.setText("");
                etDesc.setText("");
                etPrice.setText("");
                etDate.setText("");
            }
        });
        btnUpdateKhata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.open();
                db.updateKhata("2", "Alo mirchain", "Sabzi", "12-11-2022", "500");
                db.close();
            }
        });

        btnDeleteKhata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.open();
                db.deleteKhata("1");
                db.close();
            }
        });

        btnViewKhata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewKhata.class);
                startActivity(intent);
            }
        });

    }
    private void init()
    {
        etTitle = findViewById(R.id.etTitle);
        etDesc = findViewById(R.id.etDesc);
        etDate = findViewById(R.id.etDate);
        etPrice = findViewById(R.id.etPrice);
        btnAddNewKhata = findViewById(R.id.btnAddNewKhata);
        btnDeleteKhata = findViewById(R.id.btnDeleteKhata);
        btnUpdateKhata = findViewById(R.id.btnUpdateKhata);
        btnViewKhata = findViewById(R.id.btnViewKhata);
    }
}