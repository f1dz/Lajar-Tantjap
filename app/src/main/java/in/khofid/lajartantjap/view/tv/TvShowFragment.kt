package `in`.khofid.lajartantjap.view.tv


import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.adapter.TvAdapter
import `in`.khofid.lajartantjap.model.TvShow
import `in`.khofid.lajartantjap.presenter.TvShowPresenter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_tv_show.view.*

class TvShowFragment : Fragment(), TvShowView {

    private lateinit var rootView: View
    private lateinit var adapter: TvAdapter
    private var tvShows: MutableList<TvShow> = mutableListOf()
    private lateinit var presenter: TvShowPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_tv_show, container, false)

        adapter = TvAdapter(rootView.context, tvShows) {
            Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
        }

        rootView.rvTvShows.layoutManager = LinearLayoutManager(activity)
        rootView.rvTvShows.adapter = adapter

        presenter = TvShowPresenter(this)
        presenter.getTvShowList()

        return rootView
    }

    override fun showLoading() {
        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
    }

    override fun hideLoading() {
        Toast.makeText(context, "Data Loaded", Toast.LENGTH_SHORT).show()
    }

    override fun loadTvShows(data: List<TvShow>) {
        tvShows.clear()
        tvShows.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
