package com.example.citycycle.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.citycycle.R;
import com.example.citycycle.database.DatabaseHelper;
import com.example.citycycle.models.Cycle;
import com.example.citycycle.models.User;

public class CycleViewActivity extends AppCompatActivity {
    private Cycle cycle ;
    public CycleViewActivity(Cycle cycle){
        this.cycle = cycle;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cycle_view);

    }
}
