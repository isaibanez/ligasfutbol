package com.example.ligasfutbol.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ligasfutbol.R
import com.example.ligasfutbol.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
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
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // Para ir a la pantalla de registro.
        binding.btnRegistrarLogin.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_registroFragment)
        }

        // Para iniciar sesión.
        binding.btnLogin.setOnClickListener {
            auth.signInWithEmailAndPassword(binding.editEmailLogin.text.toString(), binding.editPassLogin.text.toString()).addOnCompleteListener {
                if(it.isSuccessful) {
                    findNavController().navigate(R.id.action_mainFragment_to_homeFragment)
                } else {
                    Snackbar.make(binding.root, "¡Ups! El email y/o la contraseña no son válidos.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}