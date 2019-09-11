package `in`.khofid.lajartantjap.presenter

import `in`.khofid.lajartantjap.api.ApiRepository
import `in`.khofid.lajartantjap.api.TheMovieDatabaseApi
import `in`.khofid.lajartantjap.model.MovieResponse
import `in`.khofid.lajartantjap.utils.Network
import `in`.khofid.lajartantjap.view.setting.SettingCallback
import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SettingPresenter(
    val context: Context,
    private val view: SettingCallback,
    private val apiRepository: ApiRepository = ApiRepository(),
    private val gson: Gson = Gson()
) {
    fun getNewReleaseMovies(date: String) {
        if(Network.internetAvailable(context)) {
            GlobalScope.launch(Dispatchers.Main) {
                val data = gson.fromJson(apiRepository.doRequest(TheMovieDatabaseApi.getNewRelease(date)).await(),
                    MovieResponse::class.java)

                if(data.results.isNotEmpty())
                    view.getNewReleaseMovieList(context, data.results)
            }
        }
    }
}