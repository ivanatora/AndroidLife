package com.example.ivanatora.life;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    protected void finances(View view){
        Intent intent = new Intent(this, FinanceActivity.class);
        startActivity(intent);
    }

    protected void daily_stats(View view){
        Intent intent = new Intent(this, DailyStatsActivity.class);
        startActivity(intent);
    }
}
