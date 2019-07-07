package `in`.khofid.lajartantjap.view.movie


import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.adapter.MovieAdapter
import `in`.khofid.lajartantjap.model.Movie
import `in`.khofid.lajartantjap.presenter.MoviePresenter
import `in`.khofid.lajartantjap.utils.getLanguageFormat
import `in`.khofid.lajartantjap.utils.hide
import `in`.khofid.lajartantjap.utils.show
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_movie.view.*
import org.jetbrains.anko.support.v4.startActivity
import java.util.*

const val STATE = "state"
const val LANG_STATE = "lang"

class MovieFragment : Fragment(), MovieView {

    private var movies: MutableList<Movie> = arrayListOf()
    private lateinit var rootView: View
    private lateinit var presenter: MoviePresenter
    private lateinit var adapter: MovieAdapter
    private lateinit var lang: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_movie, container, false)

        adapter = MovieAdapter(rootView.context, movies){
            startActivity<MovieDetailActivity>("movie" to it)
        }

        lang = Locale.getDefault().language
        rootView.rv_movies.layoutManager = LinearLayoutManager(activity)
        rootView.rv_movies.adapter = adapter

        presenter = MoviePresenter(this)

        val oldLang = savedInstanceState?.getString(LANG_STATE)

        if(savedInstanceState != null && oldLang == lang){
            val saved: ArrayList<Movie> = savedInstanceState.getParcelableArrayList(STATE)
            loadMovies(saved.toList())
        } else
            presenter.getMovieList(lang.getLanguageFormat())

        return rootView
    }

    override fun showLoading() {
        rootView.progress.show()
    }

    override fun hideLoading() {
        rootView.progress.hide()
    }

    override fun loadMovies(data: List<Movie>) {
        movies.clear()
        movies.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun movieNotFound() {
        Toast.makeText(context, getString(R.string.data_not_found), Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(STATE, ArrayList<Movie>(movies))
        outState.putString(LANG_STATE, lang)
    }
}
