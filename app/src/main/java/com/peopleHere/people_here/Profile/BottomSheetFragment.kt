package com.peopleHere.people_here.Profile
import TabLayoutVPAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.peopleHere.people_here.databinding.DialogCourseManageBinding

class CalendarBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: DialogCourseManageBinding? = null
    private val tabList = arrayListOf("참여 차단", "참여 가능으로 설정")
    private lateinit var date : String
    private var month : Int = 0
    private var year : Int = 0
    private var share : Boolean = false
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogCourseManageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        date = arguments?.getString("date") ?: ""
        month = arguments?.getInt("month") ?: 0
        year = arguments?.getInt("year") ?: 0

        binding.tvDialogMonth.text = month.toString()
        binding.tvDialogDay.text = date

        val adapter = TabLayoutVPAdapter(requireActivity())
        binding.vpCalendar.adapter = adapter
        adapter.setDateData(date, month, year)

        TabLayoutMediator(binding.tlEnterDate, binding.vpCalendar) { tab, position ->
            tab.text = tabList[position]
        }.attach()

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(date: String, month: Int, year: Int): CalendarBottomSheetFragment {
            return CalendarBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString("date", date)
                    putInt("month", month)
                    putInt("year", year)
                }
            }
        }
    }
}
