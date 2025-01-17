package com.example.citycycle.ui;

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
import com.example.citycycle.helpers.VerticalSpaceItemDecoration;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
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
        Log.d("test","hello");
        //   database helper
        DatabaseHelper db = new DatabaseHelper(requireContext());
        SQLiteDatabase sql = db.getReadableDatabase();

        // recycle veiws
        RecyclerView recyclerViewPromotion = view.findViewById(R.id.promtion_view);
        RecyclerView recyclerViewCycle = view.findViewById(R.id.cycle_view);

        // promotion view setup
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPromotion.setLayoutManager(layoutManager);
        PromotionViewAdapterHorizontal adapter = new PromotionViewAdapterHorizontal(db.getPromotions(),requireContext());
        recyclerViewPromotion.setAdapter(adapter);

        Log.d("test",String.valueOf(db.getCycle(null,null,null,true).size()));
        // cycle view setup
        LinearLayoutManager layoutManagerCycle = new LinearLayoutManager(requireContext());
        recyclerViewCycle.setLayoutManager(layoutManagerCycle);

//        int verticalPaddingInPixels = (int) (500 * getResources().getDisplayMetrics().density);
//
//// Add customized ItemDecoration
//        recyclerViewCycle.addItemDecoration(new VerticalSpaceItemDecoration(verticalPaddingInPixels));

// Set adapter

        CycleAdapter cycleAdapter = new CycleAdapter(db.getCycle(null,null,null,true),requireContext());
        recyclerViewCycle.setAdapter(cycleAdapter);
    }

}
