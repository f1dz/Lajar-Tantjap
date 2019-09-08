package `in`.khofid.lajartantjap.widget

import `in`.khofid.lajartantjap.R
import `in`.khofid.lajartantjap.model.Movie
import `in`.khofid.lajartantjap.utils.Constants
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.squareup.picasso.Picasso

class MovieRemoteViewsFactory(val context: Context): RemoteViewsService.RemoteViewsFactory {

    private var mWidgetItems: MutableList<Movie> = mutableListOf()
    private lateinit var presenter: MovieWidgetPresenter

    override fun onCreate() {
        presenter = MovieWidgetPresenter(context)
        mWidgetItems.addAll(presenter.getListFavoriteMovies())
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(p0: Int): Long = 0

    override fun onDataSetChanged() {
        mWidgetItems.clear()
        mWidgetItems.addAll(presenter.getListFavoriteMovies())
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val movie = mWidgetItems.get(position)
        var rv = RemoteViews(context.packageName, R.layout.widget_item)
        val bitmap = Picasso.get().load(Constants.IMG_URL + movie.backdrop_path).get()

        rv.setImageViewBitmap(R.id.imageView, bitmap)
        rv.setTextViewText(R.id.movieTitle, movie.title)

        val extras = Bundle()
        extras.putInt(MovieWidget.EXTRA_ITEM, position)
        var fillIntent = Intent()
        fillIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillIntent)

        return rv
    }

    override fun getCount(): Int = mWidgetItems.count()

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
    }
}