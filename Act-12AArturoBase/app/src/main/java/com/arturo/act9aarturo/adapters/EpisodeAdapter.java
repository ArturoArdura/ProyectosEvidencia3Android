package com.arturo.act9aarturo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arturo.act9aarturo.EpisodeDetailActivity;
import com.arturo.act9aarturo.R;
import com.arturo.act9aarturo.models.Episode;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {

    private List<Episode> episodeList;
    private Context context;

    public EpisodeAdapter(Context context, List<Episode> episodeList) {
        this.context = context;
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode, parent, false);
        return new EpisodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        Episode episode = episodeList.get(position);
        holder.codeTextView.setText(episode.getEpisodeCode());
        holder.nameTextView.setText(episode.getName());
        holder.airDateTextView.setText(episode.getAirDate());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EpisodeDetailActivity.class);
            intent.putExtra("name", episode.getName());
            intent.putExtra("code", episode.getEpisodeCode());
            intent.putExtra("air_date", episode.getAirDate());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public static class EpisodeViewHolder extends RecyclerView.ViewHolder {
        TextView codeTextView;
        TextView nameTextView;
        TextView airDateTextView;

        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            codeTextView = itemView.findViewById(R.id.episode_code);
            nameTextView = itemView.findViewById(R.id.episode_name);
            airDateTextView = itemView.findViewById(R.id.episode_air_date);
        }
    }
}