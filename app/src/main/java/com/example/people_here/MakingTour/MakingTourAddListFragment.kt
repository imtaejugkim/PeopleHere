package com.example.people_here.MakingTour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.people_here.Data.MakingTourAddListData
import com.example.people_here.R
import com.example.people_here.databinding.FragmentMakingTourAddListBinding

class MakingTourAddListFragment : Fragment() {
    lateinit var binding : FragmentMakingTourAddListBinding
    private var addListAdapter : MakingTourAddListAdapter?= null
    private var addListData : ArrayList<MakingTourAddListData> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMakingTourAddListBinding.inflate(layoutInflater)

        initRecyclerView()
        //API 통신 시 데이터 수정
        initDummyData()

        binding.btnMakingTourAddListEditSequence.setOnClickListener{
            initEditSequence()

        }

        return binding.root
    }

    private fun initEditSequence() {
        binding.btnMakingTourAddListEditSequence.isGone = true
        binding.btnMakingTourAddListFinishSequence.isVisible = true

    }

    private fun initDummyData() {
        addListData.addAll(
            arrayListOf(
                MakingTourAddListData(1,R.drawable.img_example, "로니로티 건대점",0),
                MakingTourAddListData(2,R.drawable.img_example, "로니로티 홍대점",1),
                MakingTourAddListData(3,R.drawable.img_example, "로니로티 숙대점",1),
                MakingTourAddListData(4,R.drawable.img_example, "로니로티 성대점",1),
                MakingTourAddListData(5,R.drawable.img_example, "로니로티 이대점",1),
                MakingTourAddListData(6,R.drawable.img_example, "로니로티 연대점",1),
                MakingTourAddListData(7,R.drawable.img_example, "로니로티 고대점",1),
                MakingTourAddListData(8,R.drawable.img_example, "로니로티 중대점",2)
            )
        )
    }

    private fun initRecyclerView() {
        addListAdapter = MakingTourAddListAdapter(addListData)
        binding.rvMakingTourAddListPlace.adapter = addListAdapter
        binding.rvMakingTourAddListPlace.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
    }
}