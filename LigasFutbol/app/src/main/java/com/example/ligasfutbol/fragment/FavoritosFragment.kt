package com.example.ligasfutbol.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ligasfutbol.MainActivity
import com.example.ligasfutbol.R
import com.example.ligasfutbol.adapter.EquiposAdapter
import com.example.ligasfutbol.adapter.FavoritosAdapter
import com.example.ligasfutbol.databinding.FragmentEquiposBinding
import com.example.ligasfutbol.databinding.FragmentFavoritosBinding
import com.example.ligasfutbol.model.Equipo
import com.google.firebase.auth.FirebaseAuth

class FavoritosFragment : Fragment() {
    private lateinit var binding: FragmentFavoritosBinding
    private lateinit var adapterFavoritos: FavoritosAdapter
    private lateinit var listaFavoritos: ArrayList<Equipo>
    private lateinit var auth: FirebaseAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritosBinding.inflate(layoutInflater, container, false)
        listaFavoritos = ArrayList()

        setupRecyclerView()
        setupMenu()
        return binding.root
    }

    private fun setupRecyclerView() {
        val activity = requireActivity() as MainActivity

        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.title = "Favoritos"

        adapterFavoritos = FavoritosAdapter(activity.listaFavoritos, requireContext())
        binding.recyclerFavoritos.adapter = adapterFavoritos
        binding.recyclerFavoritos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    // Menú.
    private fun setupMenu() {
        requireActivity().addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.item_home -> {
                        findNavController().navigate(R.id.action_favoritosFragment_to_homeFragment)
                        true
                    }

                    R.id.item_favoritos -> {
                        // IR A FAVORITOS
                        true
                    }

                    R.id.item_logout -> {
                        // LOGOUT
                        auth.signOut()
                        findNavController().navigate(R.id.action_favoritosFragment_to_mainFragment)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }
}