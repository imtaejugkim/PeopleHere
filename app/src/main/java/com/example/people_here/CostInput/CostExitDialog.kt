package com.example.people_here.CostInput

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.example.people_here.databinding.DialogCostExitBinding
import com.example.people_here.databinding.DialogHelpBinding

class CostExitDialog(context: Context) : Dialog(context) {
    private lateinit var binding : DialogCostExitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DialogCostExitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnExit.setOnClickListener {
            val intent = Intent(context, CostInputActivity::class.java)
            //한 번에 액티비티로 이동을 위해 사용
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            dismiss()

        }

        //배경투명
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}