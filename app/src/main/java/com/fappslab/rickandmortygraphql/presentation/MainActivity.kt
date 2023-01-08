package com.fappslab.rickandmortygraphql.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.fappslab.rickandmortygraphql.R
import com.fappslab.rickandmortygraphql.arch.viewbinding.viewBinding
import com.fappslab.rickandmortygraphql.databinding.ActivityMainBinding
import com.fappslab.rickandmortygraphql.home.presentation.HomeFragment

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.commit {
            replace(R.id.container_fragment, HomeFragment.createFragment())
        }
    }
}
