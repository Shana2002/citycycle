package com.example.citycycle;

import static android.content.ContentValues.TAG;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.citycycle.database.DatabaseHelper;
import com.example.citycycle.ui.HomeFragment;
import com.example.citycycle.ui.ProfileFragment;
import com.example.citycycle.ui.PromotionFragment;
import com.example.citycycle.ui.SearchFragment;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            // Initialize the database helper and get a writable database
            DatabaseHelper database = new DatabaseHelper(this);
            SQLiteDatabase db = database.getWritableDatabase();

            // Check if the database has been created and data is inserted
            Toast.makeText(this, "Database Created and Data Inserted", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            // Log the error and display a user-friendly message
            Log.e(TAG, "Error creating or accessing the database: " + e.getMessage(), e);
            Toast.makeText(this, "Error initializing the database. Please try again.", Toast.LENGTH_SHORT).show();
        }

        ImageView search = findViewById(R.id.nav_search);
        ImageView bike = findViewById(R.id.nav_bike);
        ImageView settings = findViewById(R.id.nav_settings);
        ImageView profile = findViewById(R.id.nav_profile);

        loadFragment(new HomeFragment());
        activeTab(bike);

        search.setOnClickListener(v -> {
            activeTab(search);
            loadFragment(new SearchFragment());
        });
        bike.setOnClickListener(v -> {
            activeTab(bike);
            loadFragment(new HomeFragment());
        });
        settings.setOnClickListener(v -> {
            activeTab(settings);
            loadFragment(new PromotionFragment());
        });
        profile.setOnClickListener(v -> {
            activeTab(profile);
            loadFragment(new ProfileFragment());
        });
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }

    private void activeTab(ImageView active){

        List<ImageView> imageViewList = Arrays.asList(findViewById(R.id.nav_search),findViewById(R.id.nav_bike),findViewById(R.id.nav_settings),findViewById(R.id.nav_profile));
        imageViewList.forEach((nav)->nav.setBackground(null));
        active.setBackgroundResource(R.drawable.nav_icon_background);
    }
}