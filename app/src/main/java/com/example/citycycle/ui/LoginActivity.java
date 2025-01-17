package com.example.citycycle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
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

import com.example.citycycle.MainActivity;
import com.example.citycycle.R;
import com.example.citycycle.database.DatabaseHelper;
import com.example.citycycle.helpers.LoginResult;
import com.example.citycycle.models.User;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        DatabaseHelper db = new DatabaseHelper(this);

//        varible decleration
        TextView sign_button = findViewById(R.id.sign_button);
        TextView login_button = findViewById(R.id.login_btn);
        EditText email = findViewById(R.id.email);
        EditText passwrod = findViewById(R.id.password);
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

//        sing button function
        sign_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

//      login button click
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty() || passwrod.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "All field required", Toast.LENGTH_SHORT).show();
                } else if (!email.getText().toString().matches(emailPattern)) {
                    Toast.makeText(LoginActivity.this, "Email type worng", Toast.LENGTH_SHORT).show();
                } else {
                    User loginUser = new User();
                    loginUser.setEmail(email.getText().toString());
                    loginUser.setPassword(passwrod.getText().toString());
                    LoginResult result = db.loginUser(loginUser);
                    if (result == LoginResult.ERROR) {
                        Toast.makeText(LoginActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    } else if (result == LoginResult.USER_NOT_FOUND) {
                        Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    } else if (result == LoginResult.INCORRECT_PASSWORD) {
                        Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    } else if (result==LoginResult.SUCCESS) {
                        Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }


            }
        });
    }
}
