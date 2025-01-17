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
import com.example.citycycle.models.User;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signup),(v,insets)->{
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left,0,systemBars.right,systemBars.bottom);
            return insets;
        });

        DatabaseHelper db = new DatabaseHelper(this);
        EditText name = findViewById(R.id.name);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        TextView sign_button  = findViewById(R.id.signup_button);
        TextView login_btn = findViewById(R.id.login_btn);
        
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sign_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(SignupActivity.this, "All field required", Toast.LENGTH_SHORT).show();
                } else if (!email.getText().toString().matches(emailPattern)) {
                    Toast.makeText(SignupActivity.this, "Use valid email", Toast.LENGTH_SHORT).show();
                } else if (name.getText().toString().length()<5) {
                    Toast.makeText(SignupActivity.this, "Name at least 6 characters long", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().length()<5) {
                    Toast.makeText(SignupActivity.this, "Password at least 6 characters long ", Toast.LENGTH_SHORT).show();
                }else{
                    User logUser = new User();
                    logUser.setName(name.getText().toString());
                    logUser.setEmail(name.getText().toString());
                    logUser.setPassword(name.getText().toString());

                    if (db.insertUser(logUser)){
                        Toast.makeText(SignupActivity.this, "User created", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(SignupActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
