package com.arturo.act9aarturo;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class CharacterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        ImageButton btnClose = findViewById(R.id.btn_close);
        btnClose.setOnClickListener(v -> finish());

        ImageView imageView = findViewById(R.id.detail_image);
        TextView nameTextView = findViewById(R.id.detail_name);
        TextView statusTextView = findViewById(R.id.detail_status);
        TextView speciesTextView = findViewById(R.id.detail_species);

        String name = getIntent().getStringExtra("name");
        String status = getIntent().getStringExtra("status");
        String species = getIntent().getStringExtra("species");
        String image = getIntent().getStringExtra("image");

        nameTextView.setText(name);
        statusTextView.setText("Status: " + status);
        speciesTextView.setText("Species: " + species);

        Glide.with(this)
                .load(image)
                .into(imageView);
    }
}