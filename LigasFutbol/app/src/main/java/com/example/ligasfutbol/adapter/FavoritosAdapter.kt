package com.example.ligasfutbol.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ligasfutbol.databinding.ItemRecyclerEquiposBinding
import com.example.ligasfutbol.databinding.ItemRecyclerFavoritosBinding
import com.example.ligasfutbol.model.Equipo

class FavoritosAdapter (var listaFavoritos: ArrayList<Equipo>, var contexto: Context) : RecyclerView.Adapter<FavoritosAdapter.FavoritosHolder>(){
    inner class FavoritosHolder(var binding: ItemRecyclerFavoritosBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritosHolder {
        val binding: ItemRecyclerFavoritosBinding = ItemRecyclerFavoritosBinding.inflate(
            LayoutInflater.from(contexto), parent, false)
        return FavoritosHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FavoritosHolder,
        position: Int
    ) {
        val equipo = listaFavoritos[position]

        holder.binding.nombreEquipoFavoritosRecycler.text = equipo.strTeam
        Glide.with(contexto).load(equipo.strBadge).into(holder.binding.escudoEquipoFavoritosRecycler)
    }

    override fun getItemCount(): Int {
        return listaFavoritos.size
    }
}