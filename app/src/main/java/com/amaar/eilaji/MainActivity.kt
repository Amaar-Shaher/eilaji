package com.amaar.eilaji

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
   private lateinit var navController: NavController

//    private val homePageFragment = HomePageFragment()
//    private val addFragment = AddFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // replaceFragment(homePageFragment)
        findNavController(R.id.fragment).navigate(R.id.logInFragment)
        //       findNavController(R.id.homePageFragment).navigate(R.id.action_addFragment_to_homePageFragment)

     //   var bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)


      //  val navHostFragment = findNavController(R.id.fragment)
         //   supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
      //  navController = navHostFragment.navController
       // setupActionBarWithNavController(navController)
//        val appBar = AppBarConfiguration(setOf(R.id.homePageFragment, R.id.addFragment))
//        setupActionBarWithNavController(navHostFragment,appBar)
//
//
//        bottomNavigation.setupWithNavController(navHostFragment)



    }
//
//    private fun replaceFragment(fragment: Fragment) {
//        if (fragment != null) {
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragmentContainerView, fragment)
//            transaction.commit()
//        }
//    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}