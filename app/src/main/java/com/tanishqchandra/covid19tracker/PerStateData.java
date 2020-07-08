package com.tanishqchandra.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;

import static com.tanishqchandra.covid19tracker.Statewise.STATE_ACTIVE;
import static com.tanishqchandra.covid19tracker.Statewise.STATE_CONFIRMED;
import static com.tanishqchandra.covid19tracker.Statewise.STATE_DECEASED;
import static com.tanishqchandra.covid19tracker.Statewise.STATE_NAME;
import static com.tanishqchandra.covid19tracker.Statewise.STATE_NEW_CONFIRMED;
import static com.tanishqchandra.covid19tracker.Statewise.STATE_NEW_RECOVERED;
import static com.tanishqchandra.covid19tracker.Statewise.STATE_RECOVERED;
import static com.tanishqchandra.covid19tracker.Statewise.STATE_LAST_UPDATE;
import static com.tanishqchandra.covid19tracker.Statewise.STATE_NEW_DECEASED;

public class PerStateData extends AppCompatActivity {
    TextView textView_state_confirmed, textView_inc_confirmed, textView_state_recovered, textView_inc_recovered, textView_state_deceased,textView_inc_deceased, textView_active, textView_state_tested, textView_inc_tested, textView_date, textView_time, textView_Update;
    String stateName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_state_data);

        Intent intent = getIntent();
        stateName = intent.getStringExtra(STATE_NAME);
        String stateConfirmed = intent.getStringExtra(STATE_CONFIRMED);
        String stateActive = intent.getStringExtra(STATE_ACTIVE);
        String stateDeceased = intent.getStringExtra(STATE_DECEASED);
        String stateNewConfirmed = intent.getStringExtra(STATE_NEW_CONFIRMED);
        String stateNewRecovered = intent.getStringExtra(STATE_NEW_RECOVERED);
        String stateNewDeceased = intent.getStringExtra(STATE_NEW_DECEASED);
        String stateLastUpdate = intent.getStringExtra(STATE_LAST_UPDATE);
        String stateRecovery = intent.getStringExtra(STATE_RECOVERED);

        Objects.requireNonNull(getSupportActionBar()).setTitle(stateName);
        textView_state_confirmed = findViewById(R.id.state_confirmed);
        textView_active = findViewById(R.id.state_active);
        textView_state_recovered = findViewById(R.id.state_recovered);
        textView_state_deceased = findViewById(R.id.state_deceased);
        textView_Update = findViewById(R.id.state_time);
        textView_inc_confirmed = findViewById(R.id.inc_state_confirmed);
        textView_inc_recovered = findViewById(R.id.inc_state_recovered);
        textView_inc_deceased = findViewById(R.id.state_deceased);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        MainActivity object = new MainActivity();
        String formatDate = object.formatDate(stateLastUpdate, 0);
        textView_state_confirmed.setText(stateConfirmed);
        textView_active.setText(stateActive);
        textView_state_deceased.setText(stateDeceased);
        textView_Update.setText(formatDate);
        textView_inc_confirmed.setText("+" + stateNewConfirmed);
        textView_inc_recovered.setText("+" + stateNewRecovered);
        textView_inc_deceased.setText("+" + stateNewDeceased);
        textView_state_recovered.setText(stateRecovery);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}