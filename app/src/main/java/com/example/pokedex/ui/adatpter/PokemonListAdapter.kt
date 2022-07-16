package com.example.pokedex.ui.adatpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.ItemPokemonBinding
import com.example.pokedex.model.Pokemon


class PokemonListAdapter(
) : ListAdapter<Pokemon, PokemonListAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPokemonBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_pokemon,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    //getItemCount() 오버라이딩 메서드 사라짐
    //override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)) //변경된 점 -> getItem(position) 메서드가 생겼다.
    }

    // 리스트 갱신
    override fun submitList(list: List<Pokemon>?) {
        super.submitList(list)
    }

    inner class ViewHolder (
        private val binding: ItemPokemonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pokemon) {
            binding.data = item
            binding.executePendingBindings()
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Pokemon>() {

            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon) =
                oldItem == newItem

        }
    }
}

