package com.example.ligasfutbol.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ligasfutbol.databinding.ItemRecyclerEquiposBinding
import com.example.ligasfutbol.databinding.ItemRecyclerListaligasBinding
import com.example.ligasfutbol.model.Equipo
import com.example.ligasfutbol.model.Liga
import com.google.android.material.snackbar.Snackbar

class EquiposAdapter(var listaEquipos: ArrayList<Equipo>, var contexto: Context, val equipoAnadidoFavorito: (Equipo) -> Unit) : RecyclerView.Adapter<EquiposAdapter.EquiposHolder>() {
    inner class EquiposHolder(var binding: ItemRecyclerEquiposBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EquiposHolder {
        val binding: ItemRecyclerEquiposBinding = ItemRecyclerEquiposBinding.inflate(
            LayoutInflater.from(contexto), parent, false)
        return EquiposHolder(binding)
    }

    override fun onBindViewHolder(
        holder: EquiposHolder,
        position: Int
    ) {
        val equipo = listaEquipos[position]

        holder.binding.nombreEquipoRecycler.text = equipo.strTeam
        Glide.with(contexto).load(equipo.strBadge).into(holder.binding.escudoEquipoRecycler)

        holder.binding.btnAnadirFavoritosRecycler.setOnClickListener {
            equipoAnadidoFavorito(equipo)
            Snackbar.make(it, "¡${equipo.strTeam} añadido a favoritos!", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return listaEquipos.size
    }

    fun anadirFavorito(equipo: Equipo) {
        listaEquipos.add(equipo)
        notifyItemInserted(listaEquipos.size - 1)
    }

    fun anadirEquipo(equipo: Equipo) {
        listaEquipos.add(equipo)
        notifyItemInserted(listaEquipos.size -1)
    }
}