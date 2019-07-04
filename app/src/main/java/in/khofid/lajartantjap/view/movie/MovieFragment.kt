package `in`.khofid.lajartantjap.view.movie


import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.adapter.MovieAdapter
import `in`.khofid.lajartantjap.model.Movie
import `in`.khofid.lajartantjap.presenter.MoviePresenter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_movie.view.*

class MovieFragment : Fragment(), MovieView {

    private var movies: MutableList<Movie> = arrayListOf()
    private lateinit var rootView: View
    private lateinit var presenter: MoviePresenter
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_movie, container, false)

        adapter = MovieAdapter(rootView.context, movies){
            Toast.makeText(rootView.context, it.title, Toast.LENGTH_SHORT).show()
        }

        rootView.rv_movies.layoutManager = LinearLayoutManager(activity)
        rootView.rv_movies.adapter = adapter


        presenter = MoviePresenter(this)
        presenter.getMovieList()

        return rootView
    }

    override fun showLoading() {
        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
    }

    override fun hideLoading() {
        Toast.makeText(context, "Data loaded", Toast.LENGTH_SHORT).show()
    }

    override fun loadMovies(data: List<Movie>) {
        movies.clear()
        movies.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun movieNotFound() {
        Toast.makeText(context, "Data not found", Toast.LENGTH_SHORT).show()
    }
}
