package `in`.khofid.lajartantjap.view.movie

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.db.AppDatabase
import `in`.khofid.lajartantjap.model.Movie
import `in`.khofid.lajartantjap.presenter.MovieDetailPresenter
import `in`.khofid.lajartantjap.utils.Constants
import `in`.khofid.lajartantjap.utils.getYear
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity(), MovieDetailView {

    private lateinit var db: AppDatabase
    private lateinit var movie: Movie
    private lateinit var presenter: MovieDetailPresenter
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        db = AppDatabase.getDatabase(application)

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
                if (presenter.isFavorited(movie)) presenter.removeFromFavorite(movie) else presenter.addToFavorite(movie)
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
        Toast.makeText(this, "Movie favorited", Toast.LENGTH_SHORT).show()
    }

    override fun onRemoved() {
        favoriteState()
        Toast.makeText(this, "Movie removed from favorite", Toast.LENGTH_SHORT).show()
    }

}
