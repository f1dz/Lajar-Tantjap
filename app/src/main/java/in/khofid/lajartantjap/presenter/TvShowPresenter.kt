package `in`.khofid.lajartantjap.presenter

import `in`.khofid.lajartantjap.api.ApiRepository
import `in`.khofid.lajartantjap.api.TheMovieDatabaseApi
import `in`.khofid.lajartantjap.db.AppDatabase
import `in`.khofid.lajartantjap.model.TvResponse
import `in`.khofid.lajartantjap.model.TvShow
import `in`.khofid.lajartantjap.utils.Network
import `in`.khofid.lajartantjap.view.tv.TvShowView
import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TvShowPresenter(
    val context: Context,
    private var view: TvShowView,
    private var apiRepository: ApiRepository = ApiRepository(),
    private var gson: Gson = Gson(),
    private val sourceType: String = "main"
) {

    private val db = AppDatabase.getDatabase(context)

    fun getTvShowList(language: String, query: String? = null) {
        view.showLoading()

        var source = TheMovieDatabaseApi.getPopularTvShows(language)

        if(sourceType == "search")
            source = TheMovieDatabaseApi.searchTvShow(language, query)

        if(Network.internetAvailable(context))
            GlobalScope.launch(Dispatchers.Main) {
                val data = gson.fromJson(
                    apiRepository
                        .doRequest(source).await(),
                    TvResponse::class.java
                )
                if (data.results.isNotEmpty())
                    view.loadTvShows(data.results)
                else
                    view.tvShowNotFound()
                view.hideLoading()
            }
        else{
            view.noInternet()
            view.hideLoading()
        }
    }

    fun getFavoriteTvShows() {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = db.tvShowDao().all

            if(data.isNotEmpty()) view.loadTvShows(data) else view.tvShowNotFound()
            view.hideLoading()
        }
    }

    fun getListFavoriteTvShow(): List<TvShow> {
        return db.tvShowDao().all
    }
}