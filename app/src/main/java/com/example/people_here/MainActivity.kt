package com.example.people_here

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.people_here.MakingTour.MakingTourAddListFragment
import com.example.people_here.MyTour.MyTourFragment
import com.example.people_here.Profile.ProfileFragment
import com.example.people_here.Search.MainFragment
import com.example.people_here.WishList.WishFragment
import com.example.people_here.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("test","test")

        binding.navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MainFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_wish -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frm, WishFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_making_tour -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MyTourFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_my_tour -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MakingTourAddListFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ProfileFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener true
                }
            }
        }
        binding.navigation.selectedItemId = R.id.menu_home
    }
}
