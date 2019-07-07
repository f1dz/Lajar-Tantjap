package `in`.khofid.lajartantjap.view.movie

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.model.Movie
import `in`.khofid.lajartantjap.utils.Constants
import `in`.khofid.lajartantjap.utils.getYear
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movie: Movie = intent.getParcelableExtra("movie")

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = movie.title
        }

        tvTitle.text = movie.title
        tvYear.text = movie.release_date?.getYear()
        ratingBar.rating = movie.vote_average/2
        Picasso.get().load(Constants.IMG_URL + movie.backdrop_path).into(imgBackdrop)
        Picasso.get().load(Constants.IMG_URL + movie.poster_path).into(imgPoster)
        tvDescription.text = movie.overview
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


}
