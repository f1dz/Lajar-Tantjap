package `in`.khofid.lajartantjap.view.setting

import `in`.khofid.lajartantjap.model.Movie
import android.content.Context

interface SettingCallback {
    fun getNewReleaseMovieList(context: Context, movies: List<Movie>)
}