package com.example.pokedex.ui.adatpter

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

object PokemonBindingAdapter {

//    @JvmStatic
//    @BindingAdapter("item")
//     fun item(view: RecyclerView, pokemonData: PagingData<Pokemon>?) {
//        // 어댑터 최초 연결
//        if (view.adapter == null) {
//            val adapter = PokemonListAdapter()
//            view.adapter = adapter
//        }
//
//        val myAdapter = view.adapter as PokemonListAdapter
//
//
//    }

    @JvmStatic
    @BindingAdapter("set_name")
    fun setDate(text: TextView, name: String) {
        text.text = name
    }

    @JvmStatic
    @BindingAdapter("image")
    fun setImage(view: AppCompatImageView, url: String) {
        val context = view.context
        Glide.with(context)
            .load(url)
            .fitCenter()
            .into(view)
    }

//    @JvmStatic
//    @BindingAdapter("gone")
//    fun setVisibility(view: ProgressBar, pokemonData: PagingData<Pokemon>?) {
//        if (pokemonData != null)
//            view.visibility = View.INVISIBLE
//        else
//            view.visibility = View.VISIBLE
//    }


}