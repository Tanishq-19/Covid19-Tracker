package com.tanishqchandra.covid19tracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.NumberFormat;
import java.util.Objects;

import static com.tanishqchandra.covid19tracker.Worldwise.COUNTRY_ACTIVE;
import static com.tanishqchandra.covid19tracker.Worldwise.COUNTRY_CONFIRMED;
import static com.tanishqchandra.covid19tracker.Worldwise.COUNTRY_DECEASED;
import static com.tanishqchandra.covid19tracker.Worldwise.COUNTRY_NAME;
import static com.tanishqchandra.covid19tracker.Worldwise.COUNTRY_NEW_CONFIRMED;
import static com.tanishqchandra.covid19tracker.Worldwise.COUNTRY_NEW_DECEASED;
import static com.tanishqchandra.covid19tracker.Worldwise.COUNTRY_RECOVERED;
import static com.tanishqchandra.covid19tracker.Worldwise.COUNTRY_TESTS;

public class PerCountryData extends AppCompatActivity {
    TextView perCountryConfirmed, perCountryActive, perCountryDeceased, perCountryNewConfirmed, perCountryTests, perCountryNewDeceased, perCountryRecovered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_country_data);

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        Intent intent = getIntent();
        String countryName = intent.getStringExtra(COUNTRY_NAME);
        String countryConfirmed = intent.getStringExtra(COUNTRY_CONFIRMED);
        String countryActive = intent.getStringExtra(COUNTRY_ACTIVE);
        String countryDeceased = intent.getStringExtra(COUNTRY_DECEASED);
        String countryRecovery = intent.getStringExtra(COUNTRY_RECOVERED);
        String countryNewConfirmed = intent.getStringExtra(COUNTRY_NEW_CONFIRMED);
        String countryNewDeceased = intent.getStringExtra(COUNTRY_NEW_DECEASED);
        String countryTests = intent.getStringExtra(COUNTRY_TESTS);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Objects.requireNonNull(getSupportActionBar()).setTitle(countryName);
        perCountryConfirmed = findViewById(R.id.country_confirmed);
        perCountryActive = findViewById(R.id.country_active);
        perCountryRecovered = findViewById(R.id.country_recovered);
        perCountryDeceased = findViewById(R.id.country_deceased);
        perCountryNewConfirmed = findViewById(R.id.inc_country_confirmed);
        perCountryTests = findViewById(R.id.country_tests);
        perCountryNewDeceased = findViewById(R.id.inc_country_deseased);

        String activeCopy = countryActive;
        String recoveredCopy = countryRecovery;
        String deceasedCopy = countryDeceased;

        int activeInt = Integer.parseInt(countryActive);
        countryActive = NumberFormat.getInstance().format(activeInt);

        int recoveredInt = Integer.parseInt(countryRecovery);
        countryRecovery = NumberFormat.getInstance().format(recoveredInt);

        int deceasedInt = Integer.parseInt(countryDeceased);
        countryDeceased = NumberFormat.getInstance().format(deceasedInt);

        int confirmedInt = Integer.parseInt(countryConfirmed);
        countryConfirmed = NumberFormat.getInstance().format(confirmedInt);

        int testsInt = Integer.parseInt(countryTests);
        countryTests = NumberFormat.getInstance().format(testsInt);


        perCountryConfirmed.setText(countryConfirmed);
        perCountryActive.setText(countryActive);
        perCountryDeceased.setText(countryDeceased);
        perCountryTests.setText(countryTests);
        perCountryNewConfirmed.setText("+" + countryNewConfirmed);
        perCountryNewDeceased.setText("+" + countryNewDeceased);
        perCountryRecovered.setText(countryRecovery);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
