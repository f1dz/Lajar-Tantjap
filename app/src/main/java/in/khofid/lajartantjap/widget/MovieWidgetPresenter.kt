package `in`.khofid.lajartantjap.widget

import `in`.khofid.lajartantjap.db.AppDatabase
import `in`.khofid.lajartantjap.model.Movie
import android.content.Context

class MovieWidgetPresenter(var context: Context) {
    val db = AppDatabase.getDatabase(context)

    fun getListFavoriteMovies(): List<Movie> {
        return db.movieDao().all
    }
}