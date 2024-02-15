package com.peopleHere.people_here.SignUp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.play.integrity.internal.i
import com.peopleHere.people_here.ForCommunicate
import com.peopleHere.people_here.R
import com.peopleHere.people_here.databinding.FragmentBirthBinding
import com.peopleHere.people_here.databinding.FragmentSelectSexBinding


class SelectSexFragment(callback:ForCommunicate) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSelectSexBinding
    val callBack:ForCommunicate

    init {
        callBack=callback
    }
    var isSelected1: Boolean = false
    var isSelected2: Boolean = false
    var isSelected3: Boolean = false

    private var communicateListener: ForCommunicate? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSelectSexBinding.inflate(layoutInflater)


        binding.btnChoose.setOnClickListener {
            if (isSelected1 || isSelected2 || isSelected3) {//이때 눌리게
                if(isSelected1){
                    callBack.selected("여성")
                }
                else if(isSelected2){
                    callBack.selected("남성")
                }
                else if(isSelected3){
                    callBack.selected("선택 안 함")
                }
                dismiss()
            }
        }
        LocationSelect()

        return binding.root
    }

    private fun LocationSelect() {
        //RadioButton처럼 해놔야함
        val gray3 = ContextCompat.getColor(binding.root.context, R.color.Gray3)
        val orange3 = ContextCompat.getColor(binding.root.context, R.color.Orange3)
        //순회하면서 지켜보게 어케함?

        binding.cvFemale.setOnClickListener {
            if (!isSelected1) {
                binding.btnFemale.setImageResource(R.drawable.radio_check_ok)//ok로 바꿈
                binding.cvFemale.setStrokeColor(orange3)
                binding.btnMale.setImageResource(R.drawable.radio_check_no)//ok로 바꿈
                binding.cvMale.setStrokeColor(gray3)
                binding.btnNo.setImageResource(R.drawable.radio_check_no)//ok로 바꿈
                binding.cvNo.setStrokeColor(gray3)//ㅋㅋ나머지 다 츃소되게 ㅋㅋㅋ
                binding.btnChoose.setBackgroundResource(R.drawable.making_tour_button_next)

                isSelected1 = true
                isSelected2 = false
                isSelected3 = false
                //클릭 될때만 나머지꺼 다 false 되게 하기
            } else {
                binding.btnFemale.setImageResource(R.drawable.radio_check_no)//ok로 바꿈
                binding.cvFemale.setStrokeColor(gray3)
                isSelected1 = false
                binding.btnChoose.setBackgroundResource(R.drawable.making_tour_button_close)

            }
        }




        binding.cvMale.setOnClickListener {
            if (!isSelected2) {
                binding.btnMale.setImageResource(R.drawable.radio_check_ok)//ok로 바꿈
                binding.cvMale.setStrokeColor(orange3)
                binding.btnFemale.setImageResource(R.drawable.radio_check_no)//ok로 바꿈
                binding.cvFemale.setStrokeColor(gray3)
                binding.btnNo.setImageResource(R.drawable.radio_check_no)//ok로 바꿈
                binding.cvNo.setStrokeColor(gray3)//ㅋㅋ나머지 다 츃소되게 ㅋㅋㅋ
                binding.btnChoose.setBackgroundResource(R.drawable.making_tour_button_next)

                isSelected2 = true
                isSelected1 = false
                isSelected3 = false
            } else {
                binding.btnMale.setImageResource(R.drawable.radio_check_no)//ok로 바꿈
                binding.cvMale.setStrokeColor(gray3)
                isSelected2 = false

                binding.btnChoose.setBackgroundResource(R.drawable.making_tour_button_close)
            }
        }
        binding.cvNo.setOnClickListener {
            if (!isSelected3) {
                binding.btnNo.setImageResource(R.drawable.radio_check_ok)//ok로 바꿈
                binding.cvNo.setStrokeColor(orange3)
                binding.btnMale.setImageResource(R.drawable.radio_check_no)//ok로 바꿈
                binding.cvMale.setStrokeColor(gray3)
                binding.btnFemale.setImageResource(R.drawable.radio_check_no)//ok로 바꿈
                binding.cvFemale.setStrokeColor(gray3)
                binding.btnChoose.setBackgroundResource(R.drawable.making_tour_button_next)

                isSelected3 = true
                isSelected2 = false
                isSelected1 = false
            } else {
                binding.btnNo.setImageResource(R.drawable.radio_check_no)//ok로 바꿈
                binding.cvNo.setStrokeColor(gray3)

                binding.btnChoose.setBackgroundResource(R.drawable.making_tour_button_close)
                isSelected3 = false
            }
        }
    }


}