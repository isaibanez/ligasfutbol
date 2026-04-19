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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.ligasfutbol.R
import com.example.ligasfutbol.adapter.LigasAdapter
import com.example.ligasfutbol.databinding.FragmentHomeBinding
import com.example.ligasfutbol.databinding.FragmentMainBinding
import com.example.ligasfutbol.model.Liga
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import androidx.core.view.MenuProvider
import androidx.core.view.MenuHost
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterLiga: LigasAdapter
    private lateinit var listaLigas: ArrayList<Liga>
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
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        listaLigas = ArrayList()

        setupRecyclerView()
        setupMenu()
        peticionJSONLigas()
        return binding.root
    }

    private fun setupRecyclerView() {
        val activity = requireActivity() as androidx.appcompat.app.AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.title = "Ligas de fútbol"

        adapterLiga = LigasAdapter(listaLigas, requireContext(), findNavController())
        binding.recyclerListaLigas.adapter = adapterLiga
        binding.recyclerListaLigas.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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
                        // IR A HOME
                        true
                    }

                    R.id.item_favoritos -> {
                        findNavController().navigate(R.id.action_homeFragment_to_favoritosFragment)
                        true
                    }

                    R.id.item_logout -> {
                        // LOGOUT
                        auth.signOut()
                        findNavController().navigate(R.id.action_homeFragment_to_mainFragment)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    private fun peticionJSONLigas() {
        val url = "https://www.thesportsdb.com/api/v1/json/123/all_leagues.php"

        val request: JsonObjectRequest = JsonObjectRequest(url, {
            Log.v("conexion", "Conexión OK")
            procesarRespuesta(it)
        }, {Log.v("conexion", "Conexión KO")
        })

        Volley.newRequestQueue(requireContext()).add(request)
    }

    private fun procesarRespuesta(param: JSONObject) {
        val gson = Gson()
        val ligaArray: JSONArray = param.getJSONArray("leagues")
        for(i in 0 .. ligaArray.length() -1) {
            val ligaJSON: JSONObject = ligaArray.getJSONObject(i)
            val liga: Liga = gson.fromJson(ligaJSON.toString(), Liga::class.java)

            adapterLiga.anadirLiga(liga)

            Log.v("conexion", "Liga: ${liga.strLeague} - Id: ${liga.idLeague}.")
        }
    }
}