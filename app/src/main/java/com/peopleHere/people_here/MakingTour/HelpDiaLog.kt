package com.peopleHere.people_here.MakingTour

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.peopleHere.people_here.databinding.DialogHelpBinding

class HelpDiaLog(context: Context) : Dialog(context) {//커스텀 다이어 로그
    private lateinit var binding : DialogHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DialogHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOk.setOnClickListener {
            dismiss()//ok누르면 나가지게 하는

        }

        //배경투명
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


    }


}