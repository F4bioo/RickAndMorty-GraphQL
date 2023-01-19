package com.fappslab.rickandmortygraphql.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.fappslab.rickandmortygraphql.libraries.arch.viewbinding.viewBinding
import com.fappslab.rickandmortygraphql.databinding.ActivityMainBinding
import com.fappslab.rickandmortygraphql.libraries.design.extention.setDarkTheme
import com.fappslab.rickandmortygraphql.navigation.HomeNavigation
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private val homeNavigation: HomeNavigation by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDarkTheme()
        setContentView(binding.root)

        supportFragmentManager.commit {
            replace(binding.containerFragment.id, homeNavigation.create())
        }
    }
}
