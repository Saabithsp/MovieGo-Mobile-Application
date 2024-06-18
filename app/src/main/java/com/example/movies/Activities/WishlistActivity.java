package com.example.movies.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.Adapters.WishlistAdapers;
import com.example.movies.Domain.Wish;
import com.example.movies.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private WishlistAdapers adapter;
    private ArrayList<Wish> wishList;
    ImageView favBtn;
    Button clsBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        db = FirebaseFirestore.getInstance();

//        favBtn = findViewById(R.id.imageView2);

    }
}