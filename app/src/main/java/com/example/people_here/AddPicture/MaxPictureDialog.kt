package com.example.people_here.AddPicture

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.people_here.CostInput.CostInputActivity
import com.example.people_here.databinding.DialogCostExitBinding
import com.example.people_here.databinding.DialogMaxPictureBinding

class MaxPictureDialog(context: Context) : Dialog(context) {
    private lateinit var binding : DialogMaxPictureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DialogMaxPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOk.setOnClickListener {

            dismiss()
        }

        //배경투명
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}