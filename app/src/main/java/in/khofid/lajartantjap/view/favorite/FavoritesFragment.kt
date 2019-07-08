package `in`.khofid.lajartantjap.view.favorite


import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.adapter.CustomPagerAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_favorites.view.*

class FavoritesFragment : Fragment() {

    lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorites, container, false)

        rootView.view_pager.adapter = CustomPagerAdapter(requireFragmentManager(), requireContext())
        rootView.tabs.setupWithViewPager(rootView.view_pager)

        return rootView
    }


}
