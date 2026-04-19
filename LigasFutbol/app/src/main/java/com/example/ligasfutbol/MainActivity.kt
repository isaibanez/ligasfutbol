package com.example.ligasfutbol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ligasfutbol.databinding.ActivityMainBinding
import com.example.ligasfutbol.model.Equipo

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val listaFavoritos = ArrayList<Equipo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        }
    }
