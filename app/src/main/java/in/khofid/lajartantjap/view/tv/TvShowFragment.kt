package `in`.khofid.lajartantjap.view.tv


import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.adapter.TvAdapter
import `in`.khofid.lajartantjap.model.TvShow
import `in`.khofid.lajartantjap.presenter.TvShowPresenter
import `in`.khofid.lajartantjap.utils.getLanguageFormat
import `in`.khofid.lajartantjap.utils.hide
import `in`.khofid.lajartantjap.utils.show
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.fragment_tv_show.view.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.startActivity
import java.util.*


const val TV_STATE = "state"
const val LANG_STATE = "lang"

class TvShowFragment : Fragment(), TvShowView {

    private lateinit var rootView: View
    private lateinit var adapter: TvAdapter
    private var tvShows: MutableList<TvShow> = mutableListOf()
    private lateinit var presenter: TvShowPresenter
    private lateinit var lang: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_tv_show, container, false)

        lang = Locale.getDefault().language

        presenter = TvShowPresenter(requireContext(), this)
        adapter = TvAdapter(rootView.context, tvShows, presenter.getListFavoriteTvShow()) {
            startActivity<TvDetailActivity>("tv" to it)
        }

        rootView.rvTvShows.layoutManager = LinearLayoutManager(activity)
        rootView.rvTvShows.adapter = adapter


        val oldLang = savedInstanceState?.getString(LANG_STATE)

        if (savedInstanceState != null && lang == oldLang) {
            val saved: ArrayList<TvShow> = savedInstanceState.getParcelableArrayList(TV_STATE)
            loadTvShows(saved.toList())
        } else
            presenter.getTvShowList(lang.getLanguageFormat())

        return rootView
    }

    override fun showLoading() {
        rootView.progress.show()
    }

    override fun hideLoading() {
        rootView.progress.hide()
    }

    override fun loadTvShows(data: List<TvShow>) {
        tvShows.clear()
        tvShows.addAll(data)
        adapter.notifyDataSetChanged()
        rootView.tvDataNotFound.hide()
        rootView.noInternet.hide()
    }

    override fun tvShowNotFound() {
        tvShows.clear()
        adapter.notifyDataSetChanged()
        rootView.tvDataNotFound.show()
    }

    override fun noInternet() {
        rootView.noInternet.show()
        rootView.snackbar(getString(R.string.no_internet))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(TV_STATE, ArrayList<TvShow>(tvShows))
        outState.putString(LANG_STATE, lang)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_button, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.search -> {
                startActivity<TvSearchActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
