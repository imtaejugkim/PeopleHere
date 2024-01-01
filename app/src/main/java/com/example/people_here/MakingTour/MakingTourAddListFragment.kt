package com.example.people_here.MakingTour

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.MakingTourAddListData
import com.example.people_here.R
import com.example.people_here.databinding.FragmentMakingTourAddListBinding
import java.util.Collections

class MakingTourAddListFragment : Fragment() {
    lateinit var binding : FragmentMakingTourAddListBinding
    private var addListAdapter : MakingTourAddListAdapter?= null
    private var addListData : ArrayList<MakingTourAddListData> = arrayListOf()
    private var isEditMode : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMakingTourAddListBinding.inflate(layoutInflater)

        initRecyclerView()
        //API 통신 시 데이터 수정
        initDummyData()
        setupItemTouchHelper()

        binding.btnMakingTourAddListEditSequence.setOnClickListener{
            initEditSequence()
        }

        binding.btnMakingTourAddListFinishSequence.setOnClickListener {
            initEditSequence()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEditMode) {
                    // 슌서 편집 시 다이얼로그
                    showExitEditDialog()
                } else {
                    // 일반 뒤로가기
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun showExitEditDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("편집 종료")
            setMessage("순서 편집을 종료하시겠습니까?")
            setPositiveButton("예") { dialog, which ->
                isEditMode = false
                // 필요한 경우 다른 UI 업데이트 로직 추가
                dialog.dismiss()
            }
            setNegativeButton("아니오") { dialog, which ->
                dialog.dismiss()
            }
            show()
        }
    }


    private fun initEditSequence() {
        isEditMode = !isEditMode
        addListAdapter?.setEditMode(isEditMode)

        if (isEditMode){
            binding.btnMakingTourAddListEditSequence.isGone = true
            binding.btnMakingTourAddListFinishSequence.isVisible = true
        }else{
            binding.btnMakingTourAddListEditSequence.isVisible = true
            binding.btnMakingTourAddListFinishSequence.isGone = true
        }
    }

    private fun setupItemTouchHelper() {
        val itemTouchHelperCallback = object : ItemTouchHelper.Callback() {
            override fun isLongPressDragEnabled(): Boolean {
                return isEditMode  // 에디트 모드일 때만 드래그 가능
            }

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlags, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                if (toPosition == addListData.size - 1) {
                    return false
                }

                addListAdapter?.onItemMove(fromPosition, toPosition)
                return true

            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // 추후 swipe 기능 추가 되면 추가
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvMakingTourAddListPlace)
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