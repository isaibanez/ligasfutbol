package com.example.ligasfutbol.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ligasfutbol.R
import com.example.ligasfutbol.databinding.FragmentRegistroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class RegistroFragment : Fragment() {
    private lateinit var binding: FragmentRegistroBinding
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
        binding = FragmentRegistroBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // Para registrar los datos de un nuevo usuario.
        binding.btnRegistrar.setOnClickListener {
            auth.createUserWithEmailAndPassword(binding.editEmailRegistro.text.toString(), binding.editPassRegistro.text.toString()).addOnCompleteListener {
                if(it.isSuccessful) {
                    Snackbar.make(binding.root, "¡Registro completado con éxito!", Snackbar.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registroFragment_to_mainFragment)
                } else {
                    Snackbar.make(binding.root, "¡Ups! No se puede registrar ese usuario.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}