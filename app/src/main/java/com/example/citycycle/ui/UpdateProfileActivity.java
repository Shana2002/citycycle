package com.example.citycycle.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.citycycle.R;
import com.example.citycycle.database.DatabaseHelper;
import com.example.citycycle.helpers.UserSession;
import com.example.citycycle.models.User;

public class UpdateProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_updateprofile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.update_profiles), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseHelper db = new DatabaseHelper(this);
        User currentUser = UserSession.getInstance().getUser();

        // Variables
        EditText name = findViewById(R.id.name);
        EditText mobile = findViewById(R.id.mobile);
        TextView updateBtn = findViewById(R.id.update_btn);

        name.setText(currentUser.getName());
        if (currentUser.getPhone() != null){
            mobile.setText(currentUser.getPhone());
        }

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().length() > 5){
                    if (mobile.getText().length() == 10){
                        currentUser.setName(name.getText().toString());
                        currentUser.setPhone(name.getText().toString());
                        db.updateUser(currentUser);
                    }
                    else{
                        Toast.makeText(UpdateProfileActivity.this, "Enter valid number", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(UpdateProfileActivity.this, "Name at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
