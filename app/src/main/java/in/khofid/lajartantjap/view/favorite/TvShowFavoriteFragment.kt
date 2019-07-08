package `in`.khofid.lajartantjap.view.favorite

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.adapter.TvAdapter
import `in`.khofid.lajartantjap.model.TvShow
import `in`.khofid.lajartantjap.presenter.TvShowPresenter
import `in`.khofid.lajartantjap.utils.Constants
import `in`.khofid.lajartantjap.utils.hide
import `in`.khofid.lajartantjap.utils.show
import `in`.khofid.lajartantjap.view.tv.TvDetailActivity
import `in`.khofid.lajartantjap.view.tv.TvShowView
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_tv_show.view.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.startActivity
import java.util.*
import kotlin.collections.ArrayList

const val FAV_TV_STATE = "favorite_tv_state"

class TvShowFavoriteFragment: Fragment(), TvShowView {

    private var tvShow: MutableList<TvShow> = arrayListOf()
    private lateinit var rootView: View
    private lateinit var presenter: TvShowPresenter
    private lateinit var adapter: TvAdapter
    private lateinit var lang: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_tv_show, container, false)

        presenter = TvShowPresenter(requireContext(), this)
        adapter = TvAdapter(rootView.context, tvShow, presenter.getListFavoriteTvShow()){
            startActivity<TvDetailActivity>("tv" to it)
        }

        lang = Locale.getDefault().language
        rootView.rvTvShows.layoutManager = LinearLayoutManager(activity)
        rootView.rvTvShows.adapter = adapter


        val oldLang = savedInstanceState?.getString(Constants.LANG_STATE)

        if(savedInstanceState != null && oldLang == lang) {
            val saved: ArrayList<TvShow> = savedInstanceState.getParcelableArrayList(FAV_TV_STATE)
            loadTvShows(saved.toList())
        } else presenter.getFavoriteTvShows()

        return rootView

    }

    override fun showLoading() {
        rootView.progress.show()
    }

    override fun hideLoading() {
        rootView.progress.hide()
    }

    override fun loadTvShows(data: List<TvShow>) {
        tvShow.clear()
        tvShow.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun tvShowNotFound() {
        rootView.frameLayout.snackbar(R.string.data_not_found).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(FAV_TV_STATE, ArrayList<TvShow>(tvShow))
        outState.putString(Constants.LANG_STATE, lang)
    }
}