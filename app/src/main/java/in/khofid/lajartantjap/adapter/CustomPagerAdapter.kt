package `in`.khofid.lajartantjap.adapter

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.view.movie.MovieFragment
import `in`.khofid.lajartantjap.view.tv.TvShowFragment
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class CustomPagerAdapter(fm: FragmentManager, var context: Context): FragmentPagerAdapter(fm) {

    private val pages = listOf(MovieFragment(), TvShowFragment())

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> context.getString(R.string.movies)
            1 -> context.getString(R.string.tv_shows)
            else -> context.getString(R.string.movies)
        }
    }
}