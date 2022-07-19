package com.example.pokedex.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.ui.adatpter.PokemonListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val pokemonListAdapter: PokemonListAdapter = PokemonListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        lifecycleScope.launchWhenStarted {
            viewModel.fetchPokemonList().collectLatest { response ->
                binding.apply {
                    pokemonListAdapter.addLoadStateListener {loadStates->
                        progressBar.isVisible=loadStates.refresh is LoadState.Loading
                    }
                    vm = viewModel
                    lifecycleOwner = this@MainActivity
                    recyclerView.adapter = pokemonListAdapter
                    pokemonListAdapter.submitData(response)
                }
            }
        }


    }

}




