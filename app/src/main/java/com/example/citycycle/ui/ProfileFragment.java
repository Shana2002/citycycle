package com.example.citycycle.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citycycle.R;
import com.example.citycycle.adapters.CycleAdapter;
import com.example.citycycle.database.DatabaseHelper;
import com.example.citycycle.helpers.UserSession;
import com.example.citycycle.models.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfileFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView userImage;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
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
        User currentUser = UserSession.getInstance().getUser();

        // Variables
        userImage = view.findViewById(R.id.userImage);
        TextView userText = view.findViewById(R.id.username);
        TextView userEmail = view.findViewById(R.id.user_email);
        TextView rentalHistoryBtn = view.findViewById(R.id.history_btn);
        TextView updateBtn = view.findViewById(R.id.update_profile_btn);
        TextView paymentOptBtn = view.findViewById(R.id.payment_button);
        TextView logoutBtn = view.findViewById(R.id.logout_btn);

        // assign vales
        userText.setText(currentUser.getName());
        userEmail.setText(currentUser.getEmail());
        if (currentUser.getImage() != null){
            userImage.setImageBitmap(currentUser.getImage());
        }

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        handleSelectedImage(imageUri);
                    }
                }
        );

        // Set click listener for the logout button to open the gallery
        logoutBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

    }

    private void handleSelectedImage(Uri imageUri) {
        try {
            // Convert URI to Bitmap
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);

            // Display selected image
            userImage.setImageBitmap(bitmap);

            // Convert Bitmap to byte array if needed (e.g., for database storage)
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] imageBlob = outputStream.toByteArray();

            // Save imageBlob to database or perform other actions
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
