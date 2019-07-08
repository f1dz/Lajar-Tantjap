package `in`.khofid.lajartantjap.presenter

import `in`.khofid.lajartantjap.db.AppDatabase
import `in`.khofid.lajartantjap.model.Movie
import `in`.khofid.lajartantjap.view.movie.MovieDetailView
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieDetailPresenter(var context: Context) {
    val db = AppDatabase.getDatabase(context)
    val view: MovieDetailView = context as MovieDetailView

    fun addToFavorite(movie: Movie){
        GlobalScope.launch(Dispatchers.Main) {
            db.movieDao().insert(movie)
            view.onFavorited()
        }
    }

    fun removeFromFavorite(movie: Movie){
        GlobalScope.launch(Dispatchers.Main) {
            db.movieDao().delete(movie)
            view.onRemoved()
        }
    }

    fun isFavorited(movie: Movie): Boolean {
        return db.movieDao().getById(movie.id) != null
    }
}