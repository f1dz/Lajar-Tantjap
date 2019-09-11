package `in`.khofid.favorite.ui

import `in`.khofid.favorite.R
import `in`.khofid.favorite.model.Movie
import `in`.khofid.favorite.utils.Const
import `in`.khofid.lajartantjap.utils.getYear
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movie = intent.getParcelableExtra("movie")

        tvTitle.text = movie.title
        tvYear.text = movie.release_date?.getYear()
        ratingBar.rating = movie.vote_average / 2
        Picasso.get()
            .load(Const.IMG_URL + movie.backdrop_path)
            .placeholder(R.mipmap.empty_movie_landscape)
            .into(imgBackdrop)
        Picasso.get()
            .load(Const.IMG_URL + movie.poster_path)
            .placeholder(R.mipmap.empty_movie)
            .into(imgPoster)
        tvDescription.text = movie.overview


    }
}
