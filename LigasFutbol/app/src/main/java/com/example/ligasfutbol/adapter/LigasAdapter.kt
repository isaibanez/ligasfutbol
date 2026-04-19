package com.example.ligasfutbol.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ligasfutbol.R
import com.example.ligasfutbol.databinding.ItemRecyclerListaligasBinding
import com.example.ligasfutbol.model.Liga

class LigasAdapter(var listaLigas: ArrayList<Liga>, var contexto: Context, var navController: NavController) : RecyclerView.Adapter<LigasAdapter.LigasHolder>() {
    inner class LigasHolder(val binding: ItemRecyclerListaligasBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LigasHolder {
        val binding: ItemRecyclerListaligasBinding = ItemRecyclerListaligasBinding.inflate(
            LayoutInflater.from(contexto), parent, false)
        return LigasHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LigasHolder,
        position: Int
    ) {
        val item: Liga = listaLigas[position]
        holder.binding.nombreLigaListaRecycler.text = item.strLeague

        // Obtener equipos del listado de ligas.
        holder.binding.root.setOnClickListener {
            val bundle = Bundle().apply {
                putString("idLiga", item.idLeague)
                putString("nombreLiga", item.strLeague)
            }
            navController.navigate(R.id.action_homeFragment_to_equiposFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return listaLigas.size
    }

    fun anadirLiga(liga: Liga) {
        listaLigas.add(liga)
        notifyItemInserted(listaLigas.size -1)
    }
}