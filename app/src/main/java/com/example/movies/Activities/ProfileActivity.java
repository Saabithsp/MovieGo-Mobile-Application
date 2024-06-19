package com.example.movies.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movies.Database.User;
import com.example.movies.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextView textViewName, textViewEmail, textViewPassword;
    private Button logoutButton, deleteButton;
    private ImageView backBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        CollectionReference usersCollection = db.collection("MovieGo");
        String currentUserEmail = mAuth.getCurrentUser().getEmail();


        textViewName = findViewById(R.id.showNameTxt);
        textViewEmail = findViewById(R.id.showEmailTxt);
        textViewPassword = findViewById(R.id.showPswdTxt);

        logoutButton = findViewById(R.id.logoutBtn);
        deleteButton = findViewById(R.id.deleteBtn);

        initView();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUserDocument(currentUserEmail);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        retrieveUserData(currentUserEmail);
    }

    private void retrieveUserData(String userEmail) {
        Query query = db.collection("MovieGo").whereEqualTo("email", userEmail);

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);

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
                Toast.makeText(ProfileActivity.this, "No matching account found", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(ProfileActivity.this, "Error retrieving user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void deleteUserDocument(String userEmail) {
        Query query = db.collection("MovieGo").whereEqualTo("email", userEmail);

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);

                if (document.exists()) {
                    db.collection("MovieGo").document(document.getId())
                            .delete()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(ProfileActivity.this, "Document deleted successfully", Toast.LENGTH_SHORT).show();
                                deleteUserAccount();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(ProfileActivity.this, "Error deleting document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(ProfileActivity.this, "No such document", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ProfileActivity.this, "No matching account found", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(ProfileActivity.this, "Error retrieving document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    //deleting authentication
    private void deleteUserAccount() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(ProfileActivity.this, "User account deleted successfully", Toast.LENGTH_SHORT).show();
                        logoutUser(); // Logout user after account deletion
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(ProfileActivity.this, "Error deleting user account: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void logoutUser () {
        mAuth.signOut();
        Toast.makeText(ProfileActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void initView(){
        backBtn = findViewById(R.id.backBtn2);
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
