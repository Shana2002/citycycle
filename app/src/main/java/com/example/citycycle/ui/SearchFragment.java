package com.example.citycycle.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citycycle.R;
import com.example.citycycle.adapters.CycleAdapter;
import com.example.citycycle.adapters.PromotionViewAdapterHorizontal;
import com.example.citycycle.database.DatabaseHelper;
import com.example.citycycle.helpers.BikeType;
import com.example.citycycle.models.Cycle;
import com.example.citycycle.models.Station;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

public class SearchFragment extends Fragment {

    EditText fromDate, toDate;
    final Calendar calendar = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set padding to the fragment's root view based on system window insets
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

//         database helper
        DatabaseHelper db = new DatabaseHelper(requireContext());
        List<Station> stationList =  db.getStations();

        // type convert from enum
        List<String> bikeTypeList = new ArrayList<>();
        bikeTypeList.add("Not Selected");
        for (BikeType type: BikeType.values()){
            bikeTypeList.add(type.getDisplayName());
        }

//         varibles
        RecyclerView cycleView = view.findViewById(R.id.cycle_view);
        Spinner spinnerType = view.findViewById(R.id.spinner_type);
        Spinner spinnerLoc = view.findViewById(R.id.spinner_loc);
        TextView search = view.findViewById(R.id.search_button);
        fromDate = view.findViewById(R.id.from_date);
        toDate = view.findViewById(R.id.to_date);



        fromDate.setOnClickListener(v -> showDatePicker(fromDate,requireContext()));

        // Set up date picker for "To Date"
        toDate.setOnClickListener(v -> showDatePicker(toDate,requireContext()));

        locationAddSpinner(stationList,spinnerLoc);
        typeSpinner(bikeTypeList,spinnerType);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startDate = fromDate.getText().toString().trim();
                String endDate = toDate.getText().toString().trim();
                Integer location;
                String type;
                Boolean available;
                int selectPositionLocation = spinnerLoc.getSelectedItemPosition();
                if (selectPositionLocation >0 ){
                    location = stationList.get(selectPositionLocation-1).stationId;
                }else{
                    location = null;
                }

                int selectedPositionType = spinnerType.getSelectedItemPosition();

                if (selectedPositionType > 0){
                    type = bikeTypeList.get(selectedPositionType);
                }
                else{
                    type = null;
                }
                if ((!startDate.isEmpty() && endDate.isEmpty()) || (startDate.isEmpty() && !endDate.isEmpty()) ) {
                    Toast.makeText(requireContext(), "Select date", Toast.LENGTH_SHORT).show();
                    Log.d("test1", "error");
                    return;
                }
                if (startDate.isEmpty() && endDate.isEmpty()){
                    startDate = null;
                    endDate = null;
                }
                searchResult(db.getCycle(location,type,startDate,endDate,true,null),requireContext(),cycleView);
            }
        });
        searchResult(db.getCycle(null,null,null,null,true,null),requireContext(),cycleView);

    }

    public void locationAddSpinner(List<Station> stationList,Spinner spinner){
        List<String> stationNames = new ArrayList<>();
        stationNames.add("Not Selected");
        for (Station station : stationList) {
            stationNames.add(station.location);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                stationNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void typeSpinner(List<String> stringList,Spinner spinner){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                stringList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    public void searchResult(List<Cycle> cycleList, Context context,RecyclerView cycleView){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        cycleView.setLayoutManager(layoutManager);

        CycleAdapter cycleAdapter = new CycleAdapter(cycleList,context,cycle -> {
            Intent intent = new Intent(requireContext(), CycleViewActivity.class);
            intent.putExtra("cycle_id",cycle.cycleId);
            startActivity(intent);
        });
        cycleView.setAdapter(cycleAdapter);
    }

    private void showDatePicker(final EditText editText,Context context) {
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
    }
}
