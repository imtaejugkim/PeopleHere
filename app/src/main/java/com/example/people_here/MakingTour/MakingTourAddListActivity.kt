package com.example.people_here.MakingTour

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.people_here.Data.MakingTourAddListData
import com.example.people_here.R
import com.example.people_here.databinding.ActivityMakingTourAddListBinding
import com.example.people_here.databinding.DialogMakingTourAddListSequenceBinding

class MakingTourAddListActivity : AppCompatActivity() {
    lateinit var binding: ActivityMakingTourAddListBinding
    private var addListAdapter: MakingTourAddListAdapter? = null
    private var addListData: ArrayList<MakingTourAddListData> = arrayListOf()
    private var isEditMode: Boolean = false
    private var sequenceDialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakingTourAddListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initDummyData()
        setupItemTouchHelper()

        binding.btnMakingTourAddListEditSequence.setOnClickListener {
            initEditSequence()
        }

        binding.btnMakingTourAddListFinishSequence.setOnClickListener {
            initEditSequence()
        }

        binding.btnAddListNext.setOnClickListener {
            val intent = Intent(this@MakingTourAddListActivity, MakingTourTimeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (isEditMode) {
            showExitEditDialog()
        } else {
            super.onBackPressed()
        }
    }

    private fun showExitEditDialog() {
        val dlgBinding = DialogMakingTourAddListSequenceBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
        sequenceDialog = dialogBuilder.setView(dlgBinding.root).show()

        val density = resources.displayMetrics.density
        val widthPx = (272 * density).toInt()
        val heightPx = (160 * density).toInt()

        sequenceDialog?.window?.setLayout(widthPx, heightPx)
        sequenceDialog?.window?.setGravity(Gravity.CENTER)
        sequenceDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dlgBinding.btnKeepGoing.setOnClickListener {
            sequenceDialog?.dismiss()
        }

        dlgBinding.btnExit.setOnClickListener {
            sequenceDialog?.dismiss()
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
                return isEditMode  // edit mode 일때만 드래그
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
        binding.rvMakingTourAddListPlace.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
    }
}