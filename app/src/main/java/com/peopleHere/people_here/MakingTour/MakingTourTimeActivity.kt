package com.peopleHere.people_here.MakingTour

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.peopleHere.people_here.AddPicture.AddPictureActivity
import com.peopleHere.people_here.databinding.ActivityMakingTourTimeBinding

class MakingTourTimeActivity : AppCompatActivity() {
    lateinit var binding : ActivityMakingTourTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakingTourTimeBinding.inflate(layoutInflater)

        binding.btnMakingTourTimeNext.setOnClickListener {
            val intent = Intent(this, AddPictureActivity::class.java)
            startActivity(intent)
        }


        setContentView(binding.root)
    }
}