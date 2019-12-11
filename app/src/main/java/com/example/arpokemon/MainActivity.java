package com.example.arpokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.arpokemon.Common.Common;
import com.example.arpokemon.Model.Pokemon;

public class MainActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;

    BroadcastReceiver showDetail = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
             if (intent.getAction().toString().equals(Common.KEY_ENABLE_HOME))
             {
                 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                 getSupportActionBar().setDisplayShowHomeEnabled(true);

                 //Replace Fragments
                 Fragment detailFragment = PokemonDetail.getInstance();
                 int position = intent.getIntExtra("position",-1);
                 Bundle bundle = new Bundle();
                 bundle.putInt("position",position);
                 detailFragment.setArguments(bundle);
                 FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                 fragmentTransaction.replace(R.id.list_pokemon_fragment,detailFragment);
                 fragmentTransaction.addToBackStack("detail");
                 fragmentTransaction.commit();

                 //Set pokemon name for Toolbar
                 Pokemon pokemon = Common.commonPokemonList.get(position);
                 toolbar.setTitle(pokemon.getName());
             }
        }
    };

    BroadcastReceiver showEvolution = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().toString().equals(Common.KEY_NUM_EVOLUATION))
            {
                //Replace Fragments
                Fragment detailFragment = PokemonDetail.getInstance();
                Bundle bundle = new Bundle();
                String num = intent.getStringExtra("num");
                bundle.putString("num",num);
                detailFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(detailFragment);
                fragmentTransaction.replace(R.id.list_pokemon_fragment,detailFragment);
                fragmentTransaction.addToBackStack("detail");
                fragmentTransaction.commit();

                //Set pokemon name for Toolbar
                Pokemon pokemon = Common.findPokemonByNum(num);
                toolbar.setTitle(pokemon.getName());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("POKEMON LIST");
        setSupportActionBar(toolbar);

        //Register BroadCast
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showDetail,new IntentFilter(Common.KEY_ENABLE_HOME));
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showEvolution,new IntentFilter(Common.KEY_NUM_EVOLUATION));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                toolbar.setTitle("POKEMON LIST");
                //Clear All
                getSupportFragmentManager().popBackStack("detail", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportActionBar().setDisplayShowHomeEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                break;
                default:
                    break;
        }
        return true;
    }
}
