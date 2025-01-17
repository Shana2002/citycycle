package com.example.citycycle.ui;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.citycycle.MainActivity;
import com.example.citycycle.R;
import com.example.citycycle.database.DatabaseHelper;

import org.w3c.dom.Text;

public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);

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


        TextView start_button = findViewById(R.id.start_btn);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
