package com.example.arpokemon;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arpokemon.Adapter.PokemonListAdapter;
import com.example.arpokemon.Common.Common;
import com.example.arpokemon.Common.ItemOffSetDecoration;
import com.example.arpokemon.Model.Pokedex;
import com.example.arpokemon.Retrofit.IPokemonDex;
import com.example.arpokemon.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonList extends Fragment {

    IPokemonDex iPokemonDex;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView pokemon_list_recycler_view;


    static PokemonList instance;

    public static PokemonList getInstance() {
        if (instance == null)
            instance = new PokemonList();
        return instance;
    }

    public PokemonList() {
        Retrofit retrofit = RetrofitClient.getInstance();
        iPokemonDex = retrofit.create(IPokemonDex.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pokemon_list, container, false);
        pokemon_list_recycler_view = (RecyclerView)view.findViewById(R.id.pokemon_list_recyclerview);
        pokemon_list_recycler_view.setHasFixedSize(true);
        pokemon_list_recycler_view.setLayoutManager(new GridLayoutManager(getActivity(),2));
        ItemOffSetDecoration itemOffSetDecoration = new ItemOffSetDecoration(getActivity(),R.dimen.spacing);
        pokemon_list_recycler_view.addItemDecoration(itemOffSetDecoration);
        fetchData();
        return view;
    }

    private void fetchData() {
        compositeDisposable.add(iPokemonDex.getListPokemon()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Pokedex>() {
            @Override
            public void accept(Pokedex pokedex) throws Exception {
                Common.commonPokemonList = pokedex.getPokemon();
                PokemonListAdapter adapter = new PokemonListAdapter(getActivity(),Common.commonPokemonList);

                pokemon_list_recycler_view.setAdapter(adapter);
            }
        }));
    }

}
