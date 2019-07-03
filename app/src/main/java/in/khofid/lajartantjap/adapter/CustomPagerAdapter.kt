package `in`.khofid.lajartantjap.adapter

import `in`.khofid.lajartantjap.view.MovieFragment
import `in`.khofid.lajartantjap.view.TvShowFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class CustomPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val pages = listOf(MovieFragment(), TvShowFragment())

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Movies"
            1 -> "TV Shows"
            else -> "Movies"
        }
    }
}