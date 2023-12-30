package com.example.sql_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewKhata extends AppCompatActivity {

    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_khata);
        tvResult = findViewById(R.id.tvResult);

        KhaataDB db = new KhaataDB(this);
        db.open();
        tvResult.setText(db.readAllKhaatas());
        db.close();

    }
}