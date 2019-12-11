package com.example.arpokemon.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arpokemon.Common.Common;
import com.example.arpokemon.Interface.IItemClickListener;
import com.example.arpokemon.Model.Evolution;
import com.example.arpokemon.R;
import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;

import java.util.ArrayList;
import java.util.List;

public class PokemonEvoluationAdapter extends RecyclerView.Adapter<PokemonEvoluationAdapter.MyViewHolder> {

    Context context;
    List<Evolution> evolutions;


    public PokemonEvoluationAdapter(Context context, List<Evolution> evolutions) {
        this.context = context;
        if (evolutions != null)
            this.evolutions = evolutions;
        else
            this.evolutions = new ArrayList<>();
    }

    @NonNull
    @Override
    public PokemonEvoluationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.chip_item,parent,false);
        return new PokemonEvoluationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonEvoluationAdapter.MyViewHolder holder, int position) {
        holder.chip.setChipText(evolutions.get(position).getName());
        holder.chip.changeBackgroundColor(
                Common.getColorByType(
                        Common.findPokemonByNum(
                                evolutions.get(position).getNum()
                        ).getType()
                        .get(0)
                )
        );

    }

    @Override
    public int getItemCount() {
        return evolutions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        Chip chip;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = (Chip)itemView.findViewById(R.id.chip);
            chip.setOnChipClickListener(new OnChipClickListener() {
                @Override
                public void onChipClick(View v) {
                    LocalBroadcastManager.getInstance(context)
                            .sendBroadcast(new Intent(Common.KEY_NUM_EVOLUATION).putExtra("num",evolutions.get(getAdapterPosition()).getNum()));
                }
            });
        }
    }
}

