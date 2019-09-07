package `in`.khofid.lajartantjap.view.movie

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.adapter.MovieAdapter
import `in`.khofid.lajartantjap.api.ApiRepository
import `in`.khofid.lajartantjap.model.Movie
import `in`.khofid.lajartantjap.presenter.MoviePresenter
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
import kotlinx.android.synthetic.main.activity_movie_search.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.collections.ArrayList

class MovieSearchActivity : AppCompatActivity(), MovieView, SearchView.OnQueryTextListener {

    private var movies: MutableList<Movie> = mutableListOf()
    private lateinit var presenter: MoviePresenter
    private lateinit var adapter: MovieAdapter
    private lateinit var lang: String
    private lateinit var menuItem: MenuItem
    private lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(getString(R.string.search_title))

        presenter = MoviePresenter(this, this, ApiRepository(), Gson(), "search")
        adapter = MovieAdapter(baseContext, movies, presenter.getListFavoriteMovie()){
            startActivity<MovieDetailActivity>("movie" to it)
        }

        lang = Locale.getDefault().language
        rv_movies.layoutManager = LinearLayoutManager(this)
        rv_movies.adapter = adapter

        if(savedInstanceState != null) {
            val saved: ArrayList<Movie> = savedInstanceState.getParcelableArrayList(MOVIE_STATE)
            loadMovies(saved.toList())
        }

    }

    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.hide()
    }

    override fun loadMovies(data: List<Movie>) {
        movies.clear()
        movies.addAll(data)
        adapter.notifyDataSetChanged()
        tvDataNotFound.hide()
        noInternet.hide()
    }

    override fun movieNotFound() {
        movies.clear()
        adapter.notifyDataSetChanged()
        tvDataNotFound.show()
    }

    override fun noInternet() {
        noInternet.show()
        container.snackbar(getString(R.string.no_internet)).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(MOVIE_STATE, ArrayList<Movie>(movies))
        outState.putString(LANG_STATE, lang)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        menuItem = menu.findItem(R.id.search)

        searchView = menuItem.actionView as SearchView
        searchView.isIconified = false
        searchView.setOnQueryTextListener(this)

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String): Boolean {
        if(query.length >= 3){
            presenter.getMovieList(lang.getLanguageFormat(), query)
        } else {
            movies.clear()
            adapter.notifyDataSetChanged()
        }

        return true
    }
}
