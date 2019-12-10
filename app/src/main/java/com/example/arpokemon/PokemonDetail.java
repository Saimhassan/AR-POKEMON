package com.example.arpokemon;


import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.arpokemon.Adapter.PokemonTypeAdapter;
import com.example.arpokemon.Common.Common;
import com.example.arpokemon.Model.Pokemon;


/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonDetail extends Fragment {

    ImageView pokemon_img;
    TextView pokemon_name,pokemon_height,pokemon_weight;
    RecyclerView recycler_type,recycler_weakness,recycler_next_evoluation,recycler_previous_evoluation;

    static PokemonDetail instance;

    public static PokemonDetail getInstance() {
        if (instance == null)
            instance = new PokemonDetail();
        return instance;
    }

    public PokemonDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_pokemon_detail, container, false);
        Pokemon pokemon;
        //Get Position from Argument
        if (getArguments().get("num") == null)
            pokemon = Common.commonPokemonList.get(getArguments().getInt("position"));
        else
            pokemon = null;
        pokemon_img = (ImageView)itemView.findViewById(R.id.pokemon_detail_image);
        pokemon_name = (TextView)itemView.findViewById(R.id.name);
        pokemon_height = (TextView)itemView.findViewById(R.id.height);
        pokemon_weight = (TextView)itemView.findViewById(R.id.weight);

        recycler_type = (RecyclerView)itemView.findViewById(R.id.recycler_type);
        recycler_type.setHasFixedSize(true);
        recycler_type.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        recycler_weakness = (RecyclerView)itemView.findViewById(R.id.recycler_weakness);
        recycler_weakness.setHasFixedSize(true);
        recycler_weakness.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        recycler_previous_evoluation = (RecyclerView)itemView.findViewById(R.id.recycler_prev_evolution);
        recycler_previous_evoluation.setHasFixedSize(true);
        recycler_previous_evoluation.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        recycler_next_evoluation = (RecyclerView)itemView.findViewById(R.id.recycler_next_evolution);
        recycler_next_evoluation.setHasFixedSize(true);
        recycler_next_evoluation.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        setDetailPokemon(pokemon);

        return itemView;
    }

    private void setDetailPokemon(Pokemon pokemon) {
        //Load Image
        Glide.with(getActivity()).load(pokemon.getImg()).into(pokemon_img);
        pokemon_name.setText(pokemon.getName());
        pokemon_weight.setText("Weight: "+pokemon.getWeight());
        pokemon_height.setText("Height: "+pokemon.getHeight());

        //Set Type
        PokemonTypeAdapter adapter = new PokemonTypeAdapter(getActivity(),pokemon.getType());
        recycler_type.setAdapter(adapter);


    }


}
