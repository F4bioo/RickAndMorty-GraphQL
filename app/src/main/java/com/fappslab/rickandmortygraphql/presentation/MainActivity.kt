package com.fappslab.rickandmortygraphql.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fappslab.rickandmortygraphql.arch.viewbinding.viewBinding
import com.fappslab.rickandmortygraphql.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
