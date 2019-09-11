package `in`.khofid.lajartantjap.view.tv

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.model.TvShow
import `in`.khofid.lajartantjap.presenter.TvShowDetailPresenter
import `in`.khofid.lajartantjap.utils.Constants
import `in`.khofid.lajartantjap.utils.getYear
import `in`.khofid.lajartantjap.view.common.DetailView
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tv_detail.*
import org.jetbrains.anko.design.snackbar

class TvDetailActivity : AppCompatActivity(), DetailView {

    private lateinit var tvShow: TvShow
    private lateinit var presenter: TvShowDetailPresenter
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_detail)

        tvShow = intent.getParcelableExtra("tv")
        presenter = TvShowDetailPresenter(this)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = tvShow.name
        }

        tvName.text = tvShow.name
        tvYear.text = tvShow.first_air_date?.getYear()
        ratingBar.rating = tvShow.vote_average/2
        Picasso.get().load(Constants.IMG_URL + tvShow.backdrop_path).into(imgBackdrop)
        Picasso.get().load(Constants.IMG_URL + tvShow.poster_path).into(imgPoster)
        tvDescription.text = tvShow.overview

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
                if(presenter.isFavorited(tvShow))
                    presenter.removeFromFavorite(tvShow)
                else
                    presenter.addToFavorite(tvShow)

                favoriteState()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun favoriteState() {
        if (presenter.isFavorited(tvShow))
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
