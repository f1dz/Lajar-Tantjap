package `in`.khofid.lajartantjap.presenter

import `in`.khofid.lajartantjap.db.AppDatabase
import `in`.khofid.lajartantjap.model.TvShow
import `in`.khofid.lajartantjap.view.common.DetailView
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TvShowDetailPresenter(var context: Context) {
    val db = AppDatabase.getDatabase(context)
    val view: DetailView = context as DetailView

    fun addToFavorite(tvShow: TvShow) {
        GlobalScope.launch(Dispatchers.Main) {
            db.tvShowDao().insert(tvShow)
            view.onFavorited()
        }
    }

    fun removeFromFavorite(tvShow: TvShow){
        GlobalScope.launch(Dispatchers.Main) {
            db.tvShowDao().delete(tvShow)
            view.onRemoved()
        }
    }

    fun isFavorited(tvShow: TvShow): Boolean = db.tvShowDao().getById(tvShow.id) != null
}