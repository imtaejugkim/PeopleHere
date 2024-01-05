package com.example.people_here.CostInput

import android.R.attr.button
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.people_here.IntroduceCaution.IntroduceActivity
import com.example.people_here.MakingTour.HelpDiaLog
import com.example.people_here.R
import com.example.people_here.databinding.FragmentCostBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.DecimalFormat


class CostFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCostBinding
    private var isclicked: Int = 0

    private val decimalFormat = DecimalFormat("#,###")//원화 유니코드 추가시 팅긴다..?
    private var result: String = ""

    //edittext부분
    private val watcher = object : TextWatcher {//3글짜씩 끊어서
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }



        override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(!TextUtils.isEmpty(charSequence.toString()) && charSequence.toString() != result){
                result = decimalFormat.format(charSequence.toString().replace(",","").toDouble())
                binding.etCostInput.setText(result);//이 스트링 집어넣음
                binding.etCostInput.setSelection(result.length);
            }
        }

        override fun afterTextChanged(p0: Editable?) {

        }

    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCostBinding.inflate(layoutInflater)


        binding.etCostInput.setText("33" + binding.etCostInput.text.toString())

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //임의로 테스트 위함

        //edittext부분->issue



        binding.etCostInput.addTextChangedListener(watcher)

        binding.ivOut.setOnClickListener {//팝업띄움
            val costexitDialog = CostExitDialog(requireContext())
            costexitDialog.show()
        }
        binding.cvBackground.setOnClickListener {//여기 눌리면 frame2로 화면 전환
           //frame전환
            Frame1to2()

            //TODO:버튼 처음 색 회색+추후에 기능 안되게도 해놓기

            ButtonOn()



            //이미지 바뀌게
            binding.ibCheck.setOnClickListener {//추가적으로 edit text에 +표시 붙게 해야함..
                if (isclicked == 0) {
                    binding.ibCheck.setImageResource(R.drawable.active_18)
                    isclicked = 1//클릭되면 1로 변경


                } else {
                    binding.ibCheck.setImageResource(R.drawable.inactive_18)
                    isclicked = 0//클릭다시하면 1로 변경
                }
            }

            binding.cvBackground2.setOnClickListener{
                //frame전환
                Frame2to1()
            }


            //TODO:임의로 넘어가게
            binding.btnKeep2.setOnClickListener {
                val intent = Intent(requireContext(), IntroduceActivity::class.java)//화면전환
                startActivity(intent)
            }

        }
        return binding.root
    }
    //frame1->2
    private fun Frame1to2() {
        binding.frame2.visibility = View.VISIBLE
        binding.frame1.visibility = View.INVISIBLE

    }
    //frame 2->1
    private fun Frame2to1() {
        binding.frame1.visibility = View.VISIBLE
        binding.frame2.visibility = View.INVISIBLE

    }


    //금액 입력하면 저장 버튼 활성화
    private fun ButtonOn() {
        binding.etCostInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length > 0) {//한글자 이상이면 검정색
                    binding.btnKeep2.setBackgroundResource(R.drawable.making_tour_button_next)
                } else {//클릭 불가능 하게도 설정하기
                    binding.btnKeep2.setBackgroundResource(R.drawable.making_tour_button_close)//설정 회색으로

                }
            }
        })
    }

}