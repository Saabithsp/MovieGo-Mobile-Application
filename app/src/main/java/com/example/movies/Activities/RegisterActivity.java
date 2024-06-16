
package com.example.movies.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movies.Database.User;
import com.example.movies.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class RegisterActivity extends AppCompatActivity {
    private EditText emailEdt,passEdt,dobEdt, nameEdt,genderEdt;
    private Button registerBtn;
    ProgressBar progressBar;
    TextView asklogin;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        //dobEdt = findViewById(R.id.editTextDOB);
        //genderEdt = findViewById(R.id.editTextGender);
        nameEdt = findViewById(R.id.editTextName);
        emailEdt = findViewById(R.id.editTextEmail);
        passEdt = findViewById(R.id.editTextPassword);

        registerBtn = findViewById(R.id.rgstr_btn);
        progressBar = findViewById(R.id.pogressBar4);

        asklogin = findViewById(R.id.logintxt);
        asklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String email,password,name,dob,gender;
                    String documentId = "BookNestRecords";
                    email = String.valueOf(emailEdt.getText());
                    name = String.valueOf(nameEdt.getText());
                    password = String.valueOf(passEdt.getText());
                    //dob = dobEdt.getText().toString().trim();
                    //gender = genderEdt.getText().toString().trim()

                    progressBar.setVisibility(View.VISIBLE);

                    if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                        Toast.makeText(RegisterActivity.this, "Please fill your Email and password", Toast.LENGTH_SHORT).show();
                    }

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null) {
                                            updateFirestoreDocument(documentId, email, name, password);
                                            user.sendEmailVerification()
                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                         Toast.makeText(RegisterActivity.this, "Email sent.",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                 }
                                           });
                                        }
                                        Toast.makeText(RegisterActivity.this, "Account Created",
                                            Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

            });
        }
    public void updateFirestoreDocument(String documentId, String email, String name, String password) {
        // Create a reference to the document with the specified ID
        DocumentReference docRef = db.collection("BookNest").document(documentId);

        // Create a Java object to hold the data
        User user = new User( email, name, password);

        // Update the document
        docRef.set(user)
                .addOnSuccessListener(aVoid -> {
                    // Document successfully updated
                    System.out.println("Document updated successfully!");
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                    System.out.println("Error updating document: " + e.getMessage());
                });
    }
}