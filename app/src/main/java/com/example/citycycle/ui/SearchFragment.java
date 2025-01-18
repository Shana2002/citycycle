package com.example.citycycle.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class SearchFragment extends Fragment {
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

//         varibles
        RecyclerView cycleView = view.findViewById(R.id.cycle_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        cycleView.setLayoutManager(layoutManager);

        CycleAdapter cycleAdapter = new CycleAdapter(db.getCycle(null,null,null,true),requireContext(),cycle -> {
            Intent intent = new Intent(requireContext(), CycleViewActivity.class);
            startActivity(intent);
        });
        cycleView.setAdapter(cycleAdapter);
    }
}
