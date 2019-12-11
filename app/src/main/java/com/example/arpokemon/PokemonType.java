package com.example.arpokemon;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arpokemon.Adapter.PokemonListAdapter;
import com.example.arpokemon.Common.Common;
import com.example.arpokemon.Common.ItemOffSetDecoration;
import com.example.arpokemon.Model.Pokemon;
import com.example.arpokemon.Retrofit.IPokemonDex;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonType extends Fragment {
    RecyclerView pokemon_list_recycler_view;
    private PokemonListAdapter adapter,search_adapter;
    List<String> last_suggest = new ArrayList<>();

    MaterialSearchBar searchBar;
    static PokemonType instance;

    public static PokemonType getInstance() {
        if (instance == null)
            instance = new PokemonType();
        return instance;
    }

    public PokemonType() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pokemon_type, container, false);
        pokemon_list_recycler_view = (RecyclerView)view.findViewById(R.id.pokemon_list_recyclerview);
        pokemon_list_recycler_view.setHasFixedSize(true);
        pokemon_list_recycler_view.setLayoutManager(new GridLayoutManager(getActivity(),2));
        ItemOffSetDecoration itemOffSetDecoration = new ItemOffSetDecoration(getActivity(),R.dimen.spacing);
        pokemon_list_recycler_view.addItemDecoration(itemOffSetDecoration);

        //Setup Search Bar
        searchBar = (MaterialSearchBar)view.findViewById(R.id.search_bar);
        searchBar.setHint("Enter Pokemon Name");
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for (String search:last_suggest)
                {
                    if (search.toLowerCase().contains(searchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                searchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    pokemon_list_recycler_view.setAdapter(adapter); //Return Default Adapter
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
        return view;
    }
    private void startSearch(CharSequence text) {
        if (Common.commonPokemonList.size() > 0)
        {
            List<Pokemon> result = new ArrayList<>();
            for (Pokemon pokemon:Common.commonPokemonList)
                if (pokemon.getName().toLowerCase().contains(text.toString().toLowerCase()))
                    result.add(pokemon);
            search_adapter = new PokemonListAdapter(getActivity(),result);
            pokemon_list_recycler_view.setAdapter(search_adapter);
        }
    }

}
