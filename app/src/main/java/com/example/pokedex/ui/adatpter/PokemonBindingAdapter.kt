package com.example.pokedex.ui.adatpter

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.model.Pokemon

object PokemonBindingAdapter {
    @JvmStatic
    @BindingAdapter("item")
    fun fetch(view: RecyclerView, items:List<Pokemon>?){
        // 어댑터 최초 연결
        if(view.adapter == null) {
            val adapter = PokemonListAdapter()
            view.adapter = adapter
        }

        val myAdapter = view.adapter as PokemonListAdapter

        // 자동 갱신
        myAdapter.submitList(items)
    }

    @JvmStatic
    @BindingAdapter("set_name")
    fun setDate(text : TextView, name:String){
        text.text=name
    }

    @JvmStatic
    @BindingAdapter("image")
    fun setImage(view : AppCompatImageView, url:String){
        val context=view.context
        Glide.with(context)
            .load(url)
            .fitCenter()
            .into(view)

    }



}