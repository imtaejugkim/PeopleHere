package com.peopleHere.people_here.MakingTour

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.peopleHere.people_here.AddPicture.AddPictureActivity
import com.peopleHere.people_here.databinding.ActivityMakingCourseFinishBinding
import com.peopleHere.people_here.databinding.ActivityMakingTourTimeBinding

class PreviewActivity : AppCompatActivity() {
    lateinit var binding : ActivityMakingCourseFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakingCourseFinishBinding.inflate(layoutInflater)

        binding.clPreview.setOnClickListener {
            val intent = Intent(this, PreviewActivity::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)
    }
}