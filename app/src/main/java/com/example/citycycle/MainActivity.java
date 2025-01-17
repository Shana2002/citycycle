package com.example.citycycle;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.citycycle.ui.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        loadFragment(new HomeFragment());

        ImageView search = findViewById(R.id.nav_search);
        ImageView bike = findViewById(R.id.nav_bike);
        ImageView settings = findViewById(R.id.nav_settings);
        ImageView profile = findViewById(R.id.nav_profile);

        search.setOnClickListener(v -> loadFragment(new HomeFragment()));
        bike.setOnClickListener(v -> loadFragment(new HomeFragment()));
        settings.setOnClickListener(v -> loadFragment(new HomeFragment()));
        profile.setOnClickListener(v -> loadFragment(new HomeFragment()));
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }
}