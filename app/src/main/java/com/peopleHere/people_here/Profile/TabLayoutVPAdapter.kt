import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.peopleHere.people_here.Profile.BlockEnjoyFragment
import com.peopleHere.people_here.Profile.PossibleEnjoyFragment

class TabLayoutVPAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private var date: String = ""
    private var month: Int = 0
    private var year: Int = 0

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BlockEnjoyFragment().apply {
                arguments = Bundle().apply {
                    putString("date", date)
                    putInt("month", month)
                    putInt("year", year)
                }
            }
            else -> PossibleEnjoyFragment().apply {
                arguments = Bundle().apply {
                    putString("date", date)
                    putInt("month", month)
                    putInt("year", year)
                }
            }
        }
    }

    fun setDateData(date: String, month: Int, year: Int) {
        this.date = date
        this.month = month
        this.year = year
    }
}