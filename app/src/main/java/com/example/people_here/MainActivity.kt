package com.example.people_here

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.people_here.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)


        setContentView(binding.root)
    }
}