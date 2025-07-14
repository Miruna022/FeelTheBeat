package com.example.feelthebeat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.button.MaterialButton;


public class History extends AppCompatActivity {
    ImageButton btnReturn;
    MaterialButton btnPie;
    MaterialButton btnCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.history);

        btnReturn = findViewById(R.id.btnBack);
        btnPie = findViewById(R.id.btnPie);
        btnCalendar = findViewById(R.id.btnCalendar);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnPie.setSelected(true);
        btnCalendar.setSelected(false);
        loadFragment(new PieChartFragment());

        btnPie.setOnClickListener(view -> {
            btnPie.setSelected(true);
            btnCalendar.setSelected(false);
            loadFragment(new PieChartFragment());
        });

        btnCalendar.setOnClickListener(view -> {
            btnPie.setSelected(false);
            btnCalendar.setSelected(true);
            loadFragment(new CalendarFragment());
        });


    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit();
    }
}


