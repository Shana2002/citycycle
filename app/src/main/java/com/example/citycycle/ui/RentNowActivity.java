package com.example.citycycle.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.citycycle.R;
import com.example.citycycle.database.DatabaseHelper;
import com.example.citycycle.models.Cycle;
import com.example.citycycle.models.Station;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RentNowActivity extends AppCompatActivity {

    DatabaseHelper db;
    int cycleId;
    boolean canRent = false;
    TextView startLoc ;
    TextView bikeName ;
    Spinner endLoc ;
    EditText startTime ;
    EditText endTime ;
    TextView price ;
    TextView rentButton;
    final Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_now);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_rent_now_view),(v, insets)->{
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left,0,systemBars.right,systemBars.bottom);
            return insets;
        });

        cycleId = getIntent().getIntExtra("cycle_id",1);

        db = new DatabaseHelper(this);
        Cycle cycle = db.getCycle(null,null,null,null,true,cycleId).get(0);
        List<Station> stationList =  db.getStations();

        startLoc = findViewById(R.id.start_loc);
        bikeName = findViewById(R.id.bike_name);
        endLoc = findViewById(R.id.end_loc_spinner);
        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_date_select);
        price = findViewById(R.id.amount);
        rentButton = findViewById(R.id.rent_btn);

        // assigning
        startLoc.setText(cycle.station);
        bikeName.setText(cycle.title);
        locationAddSpinner(stationList,endLoc);
        price.setText("LKR 0");
        startTime.setOnClickListener(v -> showDatePicker(startTime,this));

        // Set up date picker for "To Date"
        endTime.setOnClickListener(v -> showDatePicker(endTime,this));

        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canRent){

                }else{
                    Toast.makeText(RentNowActivity.this, "This cycle not available for that time range. Choose different date time or another cycle", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void locationAddSpinner(List<Station> stationList,Spinner spinner){
        List<String> stationNames = new ArrayList<>();
//        stationNames.add("Not Selected");
        for (Station station : stationList) {
            stationNames.add(station.location);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                stationNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void showDatePicker(final EditText editText, Context context) {
        new DatePickerDialog(
                context,
                (view, year, month, dayOfMonth) -> {
                    // Set selected date to the EditText
                    calendar.set(year, month, dayOfMonth);
                    String format = "yyyy-MM-dd"; // Desired format
                    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
                    editText.setText(sdf.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
        checkFields();
    }

    private void checkFields(){
        if (startTime.getText().toString().isEmpty() || endTime.getText().toString().isEmpty()){
            canRent = false;
            setRentButtonColor();
        }
        else{
            List<Cycle> cycles = db.getCycle(null,null,startTime.getText().toString(),endTime.getText().toString(),true,cycleId);
            if (cycles.isEmpty()){
                Toast.makeText(this, "This cycle not available for that time range", Toast.LENGTH_SHORT).show();
                canRent = false;
                setRentButtonColor();
            }
            else {
                canRent = true;
                setRentButtonColor();
            }
        }
    }
    private void setRentButtonColor(){
        if (canRent){
            rentButton.setBackgroundResource(R.drawable.primary_button);
        }
        else{
            rentButton.setBackgroundResource(R.drawable.button_disable);
        }
    }
}
