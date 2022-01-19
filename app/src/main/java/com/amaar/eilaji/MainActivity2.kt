package com.amaar.eilaji

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val navHostFragment = findNavController(R.id.fragment)
        val appBar = AppBarConfiguration(setOf(R.id.homePageFragment, R.id.addFragment, R.id.profileFragment))
        setupActionBarWithNavController(navHostFragment,appBar)
        bottomNavigation.setupWithNavController(navHostFragment)

    }
}