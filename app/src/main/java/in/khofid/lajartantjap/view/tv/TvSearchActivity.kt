package `in`.khofid.lajartantjap.view.tv

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.adapter.TvAdapter
import `in`.khofid.lajartantjap.api.ApiRepository
import `in`.khofid.lajartantjap.model.TvShow
import `in`.khofid.lajartantjap.presenter.TvShowPresenter
import `in`.khofid.lajartantjap.utils.getLanguageFormat
import `in`.khofid.lajartantjap.utils.hide
import `in`.khofid.lajartantjap.utils.show
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_tv_search.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.collections.ArrayList

class TvSearchActivity : AppCompatActivity(), TvShowView, SearchView.OnQueryTextListener {

    private var tvShow: MutableList<TvShow> = mutableListOf()
    private lateinit var presenter: TvShowPresenter
    private lateinit var adapter: TvAdapter
    private lateinit var lang: String
    private lateinit var menuItem: MenuItem
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(getString(R.string.search_title))

        presenter = TvShowPresenter(this, this, ApiRepository(), Gson(), "search")
        adapter = TvAdapter(baseContext, tvShow, presenter.getListFavoriteTvShow()){
            startActivity<TvDetailActivity>("tv" to it)
        }

        lang = Locale.getDefault().language
        rv_tv.layoutManager = LinearLayoutManager(this)
        rv_tv.adapter = adapter

        if(savedInstanceState != null){
            val saved : ArrayList<TvShow> = savedInstanceState.getParcelableArrayList(TV_STATE)
            loadTvShows(saved.toList())
        }
    }

    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.hide()
    }

    override fun loadTvShows(data: List<TvShow>) {
        tvShow.clear()
        tvShow.addAll(data)
        adapter.notifyDataSetChanged()
        tvDataNotFound.hide()
        noInternet.hide()
    }

    override fun tvShowNotFound() {
        tvShow.clear()
        adapter.notifyDataSetChanged()
    }

    override fun noInternet() {
        noInternet.show()
        container.snackbar(getString(R.string.no_internet)).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(TV_STATE, ArrayList<TvShow>(tvShow))
        outState.putString(LANG_STATE, lang)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        menuItem = menu.findItem(R.id.search)

        searchView = menuItem.actionView as SearchView
        searchView.isIconified = false
        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        if(query.length >= 3) {
            presenter.getTvShowList(lang.getLanguageFormat(), query)
        } else {
            tvShow.clear()
            adapter.notifyDataSetChanged()
        }

        return true
    }
}
