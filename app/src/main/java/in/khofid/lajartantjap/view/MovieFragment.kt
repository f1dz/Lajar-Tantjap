package `in`.khofid.lajartantjap.view


import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.model.Movie
import `in`.khofid.lajartantjap.presenter.MoviePresenter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

class MovieFragment : Fragment(), MovieView {

    private var movies: MutableList<Movie> = arrayListOf()
    private lateinit var rootView: View
    private lateinit var presenter: MoviePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_movie, container, false)

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
        movies.addAll(data)
    }

    override fun movieNotFound() {
        Toast.makeText(context, "Data not found", Toast.LENGTH_SHORT).show()
    }
}
