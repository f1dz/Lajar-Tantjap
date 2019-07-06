package `in`.khofid.lajartantjap.presenter

import `in`.khofid.lajartantjap.api.ApiRepository
import `in`.khofid.lajartantjap.api.TheMovieDatabaseApi
import `in`.khofid.lajartantjap.model.TvResponse
import `in`.khofid.lajartantjap.view.tv.TvShowView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TvShowPresenter(
    private var view: TvShowView,
    private var apiRepository: ApiRepository = ApiRepository(),
    private var gson: Gson = Gson()
) {
    fun getTvShowList(language: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheMovieDatabaseApi.getPopularTvShows(language)).await(),
                TvResponse::class.java
            )
            if (data.results.size > 0)
                view.loadTvShows(data.results)
            else
                view.tvShowNotFound()
            view.hideLoading()
        }
    }
}