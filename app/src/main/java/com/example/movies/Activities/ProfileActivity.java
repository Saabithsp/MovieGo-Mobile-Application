package com.example.movies.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movies.Database.User;
import com.example.movies.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextView textViewName, textViewEmail, textViewPassword;
    Button logoutButton,deleteButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        textViewName = findViewById(R.id.showNameTxt);
        textViewEmail = findViewById(R.id.showEmailTxt);
        textViewPassword = findViewById(R.id.showPswdTxt);

        logoutButton = findViewById(R.id.logoutBtn);
        deleteButton = findViewById(R.id.deleteBtn);

        retrieveUserData("Movies");


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUserDocument();
            }
        });
    }

    private void deleteUserDocument() {
        DocumentReference docRef = db.collection("MovieGo").document("Movies");
        docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivity.this, "Document deleted successfully", Toast.LENGTH_SHORT).show();
                logoutUser();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this, "Error deleting document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logoutUser() {
        mAuth.signOut();
        Toast.makeText(ProfileActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    private void retrieveUserData(String userId) {
        DocumentReference docRef = db.collection("MovieGo").document("Movies");
       docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        User user = document.toObject(User.class);

                        if (user != null) {
                            textViewName.setText(user.getName());
                            textViewEmail.setText(user.getEmail());
                            textViewPassword.setText(user.getPassword());
                        } else {
                            Toast.makeText(ProfileActivity.this, "User data is null", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ProfileActivity.this, "No such document", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed with: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
