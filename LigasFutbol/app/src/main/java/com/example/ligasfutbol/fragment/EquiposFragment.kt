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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.ligasfutbol.MainActivity
import com.example.ligasfutbol.R
import com.example.ligasfutbol.adapter.EquiposAdapter
import com.example.ligasfutbol.adapter.LigasAdapter
import com.example.ligasfutbol.databinding.FragmentEquiposBinding
import com.example.ligasfutbol.databinding.FragmentHomeBinding
import com.example.ligasfutbol.model.Equipo
import com.example.ligasfutbol.model.Liga
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class EquiposFragment : Fragment() {
    private lateinit var binding: FragmentEquiposBinding
    private lateinit var adapterEquipo: EquiposAdapter
    private lateinit var listaEquipos: ArrayList<Equipo>
    private var favoritos = ArrayList<Equipo>()
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
        binding = FragmentEquiposBinding.inflate(layoutInflater, container, false)
        listaEquipos = ArrayList()

        // Recogemos el argumento.
        val idLiga = arguments?.getString("idLiga") ?: ""
        val nombreLiga = arguments?.getString("nombreLiga") ?: ""

        setupRecyclerView(nombreLiga)
        setupMenu()
        peticionJSONEquipos(idLiga)
        return binding.root
    }

    private fun setupRecyclerView(nombreLiga: String) {
        val activity = requireActivity() as androidx.appcompat.app.AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.title = "Equipos de $nombreLiga"

        adapterEquipo = EquiposAdapter(listaEquipos, requireContext()) { equipo ->
            val activity = requireActivity() as MainActivity
            if (!activity.listaFavoritos.contains(equipo)) {
                activity.listaFavoritos.add(equipo)
                Log.v("favoritos", "Añadido ${equipo.strTeam} a favoritos.")
            }
        }
        binding.recyclerEquipos.adapter = adapterEquipo
        binding.recyclerEquipos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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
                        findNavController().navigate(R.id.action_equiposFragment_to_homeFragment)
                        true
                    }

                    R.id.item_favoritos -> {
                        findNavController().navigate(R.id.action_equiposFragment_to_favoritosFragment)
                        true
                    }

                    R.id.item_logout -> {
                        // LOGOUT
                        auth.signOut()
                        findNavController().navigate(R.id.action_equiposFragment_to_mainFragment)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    private fun peticionJSONEquipos(idLeague: String) {
        val url = "https://www.thesportsdb.com/api/v1/json/123/search_all_teams.php?id=$idLeague"
        Log.v("navegacion", "URL petición: $url")
        val request: JsonObjectRequest = JsonObjectRequest(url, {
            Log.v("conexion", "Conexión OK")
            procesarRespuesta(it)
        }, {Log.v("conexion", "Conexión KO")
        })

        Volley.newRequestQueue(requireContext()).add(request)
    }

    private fun procesarRespuesta(param: JSONObject) {
        val gson = Gson()
        val equipoArray: JSONArray = param.getJSONArray("teams")
        for(i in 0 .. equipoArray.length() -1) {
            val equipoJSON: JSONObject = equipoArray.getJSONObject(i)
            val equipo: Equipo = gson.fromJson(equipoJSON.toString(), Equipo::class.java)

            adapterEquipo.anadirFavorito(equipo)

            Log.v("conexion", "Equipo: ${equipo.strTeam}.")
        }
    }
}