package com.example.movies.Activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.Adapters.WishlistAdapers;
import com.example.movies.Domain.Wish;
import com.example.movies.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private WishlistAdapers adapter;
    private ArrayList<Wish> wishList;
    private ImageView backBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        initView();

        wishList = new ArrayList<>();
        adapter = new WishlistAdapers(this, wishList);
        recyclerView.setAdapter(adapter);

        // Set OnClickListener for the back button
        backBtn.setOnClickListener(v -> {
            // Navigate back to MainActivity
            Intent intent = new Intent(WishlistActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Read wishlist data from Firestore
        readWishlistFromFirestore();

    }

    private void initView() {
        recyclerView = findViewById(R.id.wishRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        backBtn = findViewById(R.id.backBtn10);
    }

    private void readWishlistFromFirestore() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = mAuth.getCurrentUser().getEmail();
            Log.d("WishlistActivity", "userId: " + userId); // Logging userId

            // Create a reference to the cities collection
            CollectionReference citiesRef = db.collection("WishList");
            // Create a query against the collection.
            Query query = citiesRef.whereEqualTo("email", userId);

            db.collection("WishList").document(userId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    DocumentSnapshot document = task.getResult();
                    String documentId = document.getId();
                    Log.d("WishlistActivity", "documentId: " + documentId); // Logging documentId

                    if (document.exists()) {
                        String movieIDs = document.getString("movieID");
                        if (movieIDs != null && !movieIDs.isEmpty()) {
                            String[] movieIdArray = movieIDs.split(",");
                            for (String movieId : movieIdArray) {
                                Wish wish = new Wish();
                                wish.setId(Integer.parseInt(movieId.trim()));
                                wishList.add(wish);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(WishlistActivity.this, "No movies in wishlist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(WishlistActivity.this, "User document not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WishlistActivity.this, "Error fetching wishlist data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(WishlistActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }


}
