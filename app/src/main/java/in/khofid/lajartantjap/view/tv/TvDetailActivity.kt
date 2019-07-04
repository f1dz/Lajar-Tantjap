package `in`.khofid.lajartantjap.view.tv

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.model.TvShow
import `in`.khofid.lajartantjap.utils.Constants
import `in`.khofid.lajartantjap.utils.getYear
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tv_detail.*

class TvDetailActivity : AppCompatActivity() {

    private var tvShow: TvShow = TvShow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_detail)

        tvShow = intent.getParcelableExtra("tv")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = tvShow.name

        tvName.text = tvShow.name
        tvYear.text = tvShow.first_air_date?.getYear()
        ratingBar.rating = tvShow.vote_average/2
        Picasso.get().load(Constants.IMG_URL + tvShow.backdrop_path).into(imgBackdrop)
        Picasso.get().load(Constants.IMG_URL + tvShow.poster_path).into(imgPoster)
        tvDescription.text = tvShow.overview
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
