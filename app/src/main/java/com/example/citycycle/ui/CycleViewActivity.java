package com.example.citycycle.ui;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.citycycle.R;
import com.example.citycycle.database.DatabaseHelper;
import com.example.citycycle.models.Cycle;


public class CycleViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cycle_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cycle_activity_view),(v, insets)->{
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left,0,systemBars.right,systemBars.bottom);
            return insets;
        });

        int cycleId = getIntent().getIntExtra("cycle_id",1);

        DatabaseHelper db = new DatabaseHelper(this);
        Cycle cycle = db.getCycle(null,null,null,null,true,cycleId).get(0);
        Log.d("test",cycle.title);

        // Variables to Assign Values
        TextView title =  findViewById(R.id.title);
        TextView location = findViewById(R.id.location);
        TextView description = findViewById(R.id.description);
        TextView rent_btn = findViewById(R.id.rent_btn);


        title.setText(cycle.title);
        location.setText(cycle.station);
        description.setText(cycle.description);


        rent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CycleViewActivity.this, RentNowActivity.class);
                intent.putExtra("cycle_id",cycle.cycleId);
                startActivity(intent);
            }
        });
    }
}
