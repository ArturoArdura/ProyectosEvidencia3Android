package com.arturo.act9aarturo;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EpisodeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_detail);

        ImageButton btnClose = findViewById(R.id.btn_close_episode);
        btnClose.setOnClickListener(v -> finish());

        TextView codeTextView = findViewById(R.id.detail_episode_code);
        TextView nameTextView = findViewById(R.id.detail_episode_name);
        TextView airDateTextView = findViewById(R.id.detail_episode_air_date);

        String name = getIntent().getStringExtra("name");
        String code = getIntent().getStringExtra("code");
        String airDate = getIntent().getStringExtra("air_date");

        codeTextView.setText(code);
        nameTextView.setText(name);
        airDateTextView.setText("Aired on: " + airDate);
    }
}