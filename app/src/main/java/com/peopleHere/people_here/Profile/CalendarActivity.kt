package com.peopleHere.people_here.Profile

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.peopleHere.people_here.Data.CalendarData
import com.peopleHere.people_here.Local.getJwt
import com.peopleHere.people_here.R
import com.peopleHere.people_here.Remote.AddTourDateView
import com.peopleHere.people_here.Remote.AuthService
import com.peopleHere.people_here.Remote.TourTimeData
import com.peopleHere.people_here.Remote.UpcomingDateResponse
import com.peopleHere.people_here.Remote.UpcomingDateView
import com.peopleHere.people_here.databinding.ActivityCalendarBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarActivity : AppCompatActivity() , UpcomingDateView , AddTourDateView{
    lateinit var binding : ActivityCalendarBinding
    private var calendarList : ArrayList<CalendarData> = arrayListOf()
    private var monthAdapter : MonthAdapter ?= null
    private var dateAdapter : DateAdapter ?= null
    private var calendarDialog : Dialog?= null
    private var tourId : Int = 0
    private var tourName : String ?= null
    private var upcomingData : ArrayList<UpcomingDateResponse> = arrayListOf()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var lastClickedDate: String? = null
    private var lastClickedTime : TourTimeData?= null
    private val tabList = arrayListOf("참여 차단", "참여 가능으로 설정")
    private val tabStates = booleanArrayOf(true, true)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initBottomSheet()

        tabList.forEachIndexed { index, title ->
            val tab = binding.tlEnterDate.newTab()
            val tabView = LayoutInflater.from(this).inflate(R.layout.custom_text_view, null) as TextView
            tabView.text = title
            tab.customView = tabView
            binding.tlEnterDate.addTab(tab)

            // 초기 탭 선택 설정
            val firstTab = binding.tlEnterDate.getTabAt(0)
            binding.tlEnterDate.selectTab(firstTab)
            setTabColor(firstTab, true)
        }

        tourId = intent.getIntExtra("tourId",0)
        tourName = intent.getStringExtra("tourName")
        initDataManager(tourId)
        Log.d("tourId",tourId.toString())

        binding.rvWeek.layoutManager = GridLayoutManager(this,7)
        binding.rvWeek.adapter = WeekAdapter(arrayListOf("일","월","화","수","목","금","토"))
        binding.tvTitle.text = tourName

        binding.btnBack.setOnClickListener {
            finish()
        }

    }
    private fun initRecyclerView(upcomingData : ArrayList<UpcomingDateResponse>) {
        val cal = Calendar.getInstance()
        val currentYear = cal.get(Calendar.YEAR)
        val currentMonth = cal.get(Calendar.MONTH) + 1 // Calendar.MONTH는 0부터 시작하므로 +1

        // 현재 월부터 12개월간의 데이터 생성
        for (i in 0 until 12) {
            val month = (currentMonth + i) % 12
            val year = currentYear + (currentMonth + i) / 12
            val dayList = calculateDays(year, month)

            calendarList.add(CalendarData(if (month == 0) year - 1 else year,
                if (month == 0) 12 else month,
                dayList))

        }

        monthAdapter = MonthAdapter(calendarList, upcomingData, this@CalendarActivity, object : DateAdapter.OnDateClickListener {
            override fun onDateClick(date: String, month: Int, year: Int, status: String?, time: String?) {
                lastClickedDate = "$year-${(month).toString().padStart(2, '0')}-${date.padStart(2, '0')}"

                updateTabStatus(status, time)

                if (status == "AVAILABLE") {
                    binding.tlEnterDate.getTabAt(1)?.select()

                    // 시간 설정
                    val displayTime = if (time != null) {
                        // 시간 포맷 변환 로직 추가
                        val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                        val outputFormat = SimpleDateFormat("a hh:mm", Locale.getDefault())
                        val date = inputFormat.parse(time)
                        date?.let {
                            outputFormat.format(it)
                        } ?: "시간협의"
                    } else {
                        "여행자의 시간에 맞출 수 있어요"
                    }
                    binding.tvExistTime.text = displayTime
                } else {
                    binding.tlEnterDate.getTabAt(0)?.select()
                    binding.tvExistTime.text = "여행자의 시간에 맞출 수 있어요"
                }
                // BottomSheet를 보여주는 로직
                showCalendarDialog(date, month, year, time)
            }
        })

        binding.rvMonth.adapter = monthAdapter
        binding.rvMonth.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)

    }

    private fun updateTabStatus(status: String?, time: String?) {
        Log.d("status",status.toString())
        Log.d("time",time.toString())

        binding.tlEnterDate.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        initAddTourManager(tourId, lastClickedDate!!, null)
                    }
                    1 -> {
                        val timeData = lastClickedTime
                        initAddTourManager(tourId, lastClickedDate!!, timeData)
                    }
                }
                updateTabTexts(tab.position)
                setTabColor(tab,true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                setTabColor(tab,false)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun updateTabTexts(selectedTabIndex: Int) {
        for (i in 0 until binding.tlEnterDate.tabCount) {

            val tab0 = binding.tlEnterDate.getTabAt(0)
            val tab1 = binding.tlEnterDate.getTabAt(1)
            val textView1 = tab0?.customView as? TextView
            val textView2 = tab1?.customView as? TextView

            if (selectedTabIndex == 0) {
                textView1?.text = "참여 차단"
                textView2?.text = "참여 가능으로 설정"
            } else {
                textView1?.text = "참여 차단으로 변경"
                textView2?.text = "참여 가능"
            }
        }
    }


    private fun calculateDays(year: Int, month: Int) : ArrayList<String> {
        val dayList = ArrayList<String>()
        val cal = Calendar.getInstance()

        cal.set(year, month - 1, 1)
        val startDayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
        for (i in 1 until startDayOfWeek) {
            dayList.add("") // 공백으로 채움
        }

        val lastDay = cal.getActualMaximum(Calendar.DATE)
        for (i in 1..lastDay) {
            dayList.add(i.toString())
        }

        return dayList
    }

    private fun initBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.clBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetBehavior.peekHeight = 0
    }

    private fun showCalendarDialog(date: String, month: Int, year: Int, time: String?) {
        showBottomSheet(date, month, year, time)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }


    private fun showBottomSheet(date: String, month: Int, year: Int, time: String?) {
        binding.tvDialogMonth.text = month.toString()
        binding.tvDialogDay.text = date

        val displayMetrics = resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels

        val peekHeightRatio = 64.0 / 800.0
        bottomSheetBehavior.peekHeight = (screenHeight * peekHeightRatio).toInt()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

    }

    private fun setTabColor(tab: TabLayout.Tab?, isSelected: Boolean) {
        binding.tvExistTime.text = tabList[tab!!.position]
        val tabView = (binding.tlEnterDate.getChildAt(0) as ViewGroup).getChildAt(tab.position) as View

        tab.customView?.let { view ->

            val textView = view as TextView
            if (isSelected) {
                tabView.background = ResourcesCompat.getDrawable(resources, R.drawable.rectangle_line_orange3_12, null)
                textView.setTextColor(ContextCompat.getColor(this, R.color.Orange5))
            } else {
                textView.setTextColor(ContextCompat.getColor(this, R.color.Gray5))
                tabView.background = null
                binding.tvExistTime.setTextColor(ContextCompat.getColor(this, R.color.Gray5))
            }
        }
    }

    override fun UpcomingDateLoading() {}

    override fun UpcomingDateSuccess(content: ArrayList<UpcomingDateResponse>) {
        upcomingData = content
        Log.d("받은 데이터", upcomingData.toString())

        initRecyclerView(upcomingData)
    }

    override fun UpcomingDateFailure(status: Int, message: String) {
        Log.d("Calendar Activity 통신 오류1", status.toString())
        Log.d("Calendar Activity 통신 오류2", message)
    }

    private fun initDataManager(tourId : Int) {
        val token = getJwt()
        Log.d("token",token)
        if(token.isNotEmpty()){
            val authService = AuthService(this)
            authService.setUpcomingDateView(this)
            Log.d("tourId",tourId.toString())
            authService.upcomingDateInfo(tourId)
        }else{
            Log.d("token 오류","token 오류")
        }
    }

    override fun AddTourDateLoading() {}

    override fun AddTourDateSuccess() {
        Log.d("통신성공!","통신성공")
    }

    override fun AddTourDateFailure(status: Int, message: String) {}

    private fun initAddTourManager(tourId: Int, date: String, time: TourTimeData?) {
        val token = getJwt()
        Log.d("token",token)
        if(token.isNotEmpty()){
            val authService = AuthService(this)
            authService.setAddTourDateView(this)
            Log.d("tourId",tourId.toString())
            authService.addTourDateInfo(tourId, date, time)
        }else{
            Log.d("token 오류","token 오류")
        }
    }

}