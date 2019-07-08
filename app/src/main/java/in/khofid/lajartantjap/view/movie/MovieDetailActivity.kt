package `in`.khofid.lajartantjap.view.movie

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.model.Movie
import `in`.khofid.lajartantjap.presenter.MovieDetailPresenter
import `in`.khofid.lajartantjap.utils.Constants
import `in`.khofid.lajartantjap.utils.getYear
import `in`.khofid.lajartantjap.view.common.DetailView
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.jetbrains.anko.design.snackbar

class MovieDetailActivity : AppCompatActivity(), DetailView {

    private lateinit var movie: Movie
    private lateinit var presenter: MovieDetailPresenter
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movie = intent.getParcelableExtra("movie")

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = movie.title
        }

        tvTitle.text = movie.title
        tvYear.text = movie.release_date?.getYear()
        ratingBar.rating = movie.vote_average / 2
        Picasso.get().load(Constants.IMG_URL + movie.backdrop_path).into(imgBackdrop)
        Picasso.get().load(Constants.IMG_URL + movie.poster_path).into(imgPoster)
        tvDescription.text = movie.overview

        presenter = MovieDetailPresenter(this)

        favoriteState()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        favoriteState()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (presenter.isFavorited(movie))
                    presenter.removeFromFavorite(movie)
                else
                    presenter.addToFavorite(movie)

                favoriteState()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun favoriteState() {
        if (presenter.isFavorited(movie))
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    override fun onFavorited() {
        favoriteState()
        scrollView.snackbar(getString(R.string.added_to_favorite)).show()
    }

    override fun onRemoved() {
        favoriteState()
        scrollView.snackbar(getString(R.string.removed_from_favorite)).show()
    }

}
